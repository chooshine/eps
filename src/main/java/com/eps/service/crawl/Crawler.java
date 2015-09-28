package com.eps.service.crawl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eps.dao.crawl.CrawlDao;
import com.eps.service.crawl.bean.CrawlUrl;
import com.eps.service.crawl.impl.AsahiNews;

@Service
public class Crawler {
	//private UrlQueue queue = new UrlQueue();
	Logger logger = LoggerFactory.getLogger(Crawler.class);
	private final static int THREADNUM = 2;
	private AtomicLong count = new AtomicLong(1);
	private long parseNum = 0l;
	private final static long maxParaseNum = 100l;
	private LinkedList<CrawlUrl> queue = new LinkedList<CrawlUrl>();
	private LinkedList<CrawlUrl> queueSave = new LinkedList<CrawlUrl>();
	private List<CrawlUrl> queueOld = Collections.synchronizedList(new ArrayList<CrawlUrl>());
	private List<CrawlUrl> initUrlQueue = new ArrayList<CrawlUrl>();
	private int maxQueueSave = 1000;
	private static volatile boolean cancle = true;
	@Autowired
	private CrawlDao dao;
	private int threadNum = Crawler.THREADNUM;
	private long delayBetweenUrls = 4000l;
	@Value("${crawler.thread.num}")
	private int crawlerThreadNum;
	
	public Crawler(){
		//initUrlQueue = dao.getInitUrl();
	}
	
	/**
	 * 判断是否继续抓取
	 * @return
	 */
	private boolean continueCrawling(){
		//抓取新闻数量达到最大抓取数量时或者访问地址达到最大时停止本轮抓取
		if(parseNum >= maxParaseNum)return false;
		synchronized (queue) {
			logger.info("队列中元素个数：["+queue.size()+"]");
			if(queue.isEmpty()){
				List<CrawlUrl> queueList = dao.getQueueUrl();
				if(queueList.size() > 0 ){
					queue.addAll(queueList);
					queue.notifyAll();
				}else{
					synchronized (queueSave) {
						if(queueSave.size()>0){
							queue.addAll(queueSave);
							queue.notifyAll();
							queueSave.clear();
							queueSave.notifyAll();
						}else{
							queue.addAll(initUrlQueue);
						}
					}
				}
			}
		}
		return true;
	}
	/**
	 * 开始抓取
	 */
	public void crawl(){
		logger.info("本轮抓取开始");
		cancle = true;
		initUrlQueue = dao.getInitUrl();
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(crawlerThreadNum);
		for(int i=0;i<crawlerThreadNum;i++){
			executor.execute(new Worker());
		}
	//	queue.addAll(initUrlQueue);
		while(continueCrawling()){
			try {
				TimeUnit.MILLISECONDS.sleep(delayBetweenUrls);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			logger.info("队列中元素个数：["+queue.size()+"]");
			logger.info("已访问队列元素个数：[" + queueOld.size()+"]");
			synchronized (queueSave) {
				while(queueSave.size()>=maxQueueSave){
					dao.insertUrl(queueSave);
					queueSave.clear();
					queueSave.notifyAll();
				}
			}
		}
		cancle = false;
		executor.shutdown();
		if(queueSave.size()>0){
			dao.insertUrl(queueSave);
			queueSave.clear();
		}
//		crawlNum = 0l;
		parseNum = 0l;
		count.set(1);;
		long end = System.currentTimeMillis();
		logger.info("本轮抓取结束,用时：[{}]秒]",(end-start)/1000);
	}
	private boolean hasSave(CrawlUrl crawlUrl){
		synchronized (queueSave) {
			if(!initUrlQueue.contains(crawlUrl) &&
					!queueOld.contains(crawlUrl) &&
					!queueSave.contains(crawlUrl)/*&&
					dao.getUrlCount(crawlUrl) < 1 &&
					dao.getQueueUrlCount(crawlUrl) < 1*/){
				return true;
			}
		}
		return false;
	}
	private class Worker implements Runnable{
		
		public void run() {
			CrawlUrl url = null;
			AsahiNews news;
			while(cancle){
				synchronized (queue) {
					while(queue.isEmpty()){
						try {
							logger.info("线程[{}]挂起",Thread.currentThread().getName());
							queue.wait();
//							if(queue.isEmpty())queue.wait();;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					logger.info("线程[{}]工作",Thread.currentThread().getName());
					try {
						url = queue.remove();
					} catch (NoSuchElementException e) {
						 continue;
					}
				}
				logger.info("开始解析地址[{}]",url);
				//加入已访问队列
				queueOld.add(url);
				dao.deleteUrl(url.getUrl());
				if(dao.getUrlCount(url)>0) 
				{
					logger.info("地址：[{}]已解析过",url.getUrl());
					continue;
				}
				//抓取网页面内容,如成功抓取新闻则抓取数量+1
				try {
					news = new AsahiNews(url);
				} catch (Exception e1) {
					continue;
				}
				List<CrawlUrl> links = new ArrayList<CrawlUrl>();
				//如果抓取深度已到最大深度则不继续往下获取新的链接
				if(url.getLevel()< url.getMaxLevel()){
					links = news.extracLinks();
					logger.info("取得新地址数量：[{}]",links.size());
				}else{
					logger.info("该地址抓取深度已达最大，不再获取新的地址");
				}
				if(news.parser()){
					parseNum = count.get();
					dao.insertNews(news.getBean(), url.getTableName());
					dao.insertUrl(url.getUrl());
					logger.info("抓取到新闻：\n[{}]"+news.getBean());
				}else{
					logger.info("未抓取到新闻");
				}
				int n=0;
				for (CrawlUrl crawlUrl : links) {
					if(hasSave(crawlUrl)){
						synchronized (queueSave) {
							if(queueSave.size() >= maxQueueSave){
								try {
									queueSave.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							n++;
							queueSave.add(crawlUrl);//新链接入队
						}
					}else {
						continue;
					}
				}
				logger.info("取得有效地址数量：[{}]",n);
				logger.info("解析地址[{}]结束",url.getUrl());
			}
		}
		
	}
	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	
//	public static void main(String[] args) {
//		AtomicLong al = new AtomicLong(1);
//		for(int i=0;i<10;i++)
//			System.out.println(al.getAndAdd(1));
//	}
}
