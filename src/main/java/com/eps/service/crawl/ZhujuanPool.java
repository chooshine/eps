package com.eps.service.crawl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eps.dao.crawl.ZhujuanDao;
import com.eps.mybatis.auto.entity.ZhujuanImages;
import com.eps.mybatis.auto.entity.ZhujuanQues;
import com.eps.mybatis.auto.entity.ZhujuanQuesWithBLOBs;
import com.eps.service.crawl.bean.Ques;
import com.eps.service.crawl.impl.ZhujuanParse;
import com.eps.utils.MapUtils;
import com.eps.utils.UStrMap;

public class ZhujuanPool {
	private static ExecutorService executor = Executors.newCachedThreadPool();
	//public static boolean cancle = true;
	/**
	 * 处理
	 * @param work {@link ZhujuanProcesser}工作对象
	 * @param isSync 同步/异步
	 */
	public static void execute(ZhujuanProcesser work,boolean isSync){
		if(isSync) executor.execute(work);
		else work.process();
	}
	public static void stop(){
		executor.shutdown();
	}
	public static class downloader implements ZhujuanProcesser{
		private Logger log = LoggerFactory.getLogger(downloader.class);
		private LinkedList<ZhujuanImages> urls;
		private ZhujuanCrawler service;
//		private boolean isAnswerAndParseImg = false;
		public downloader(LinkedList<ZhujuanImages> urls,ZhujuanCrawler service){
			this.urls = urls;
			this.service = service;
		}
		private boolean cancle = true;
//		public downloader(LinkedList<String> urls,ZhujuanCrawler service,boolean isQues){
//			this.urls = urls;
//			this.service = service;
//			this.isAnswerAndParseImg = isQues;
//		}
		@Override
		public void run() {
			process();
		}
		@Override
		public void process() {
			while(cancle){
				ZhujuanImages url = null;
				synchronized (urls) {
					while(urls.isEmpty()){
						try {
							urls.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try{
						url = urls.remove();
					} catch (NoSuchElementException e) {
						 continue;
					}
				}
//				if(isAnswerAndParseImg){
//				}else{
				if(url == null) continue;
				String path = url.getUrl();
				String savePath = path;
				if (path.startsWith("http")) {
//						get = new HttpGet(path);
					try {
						URI uri = new URI(path);
						savePath = uri.getPath();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				} else {
					//get = new HttpGet();
					path = ZhujuanCrawler.ZHUJUANHTTP + ZhujuanCrawler.ZHUJUANDOWNLOADHOST + "/" + path;
					//savePath = path;
				}
				if(service.downloadImg(path,savePath)){
					service.deleteImage(url);
					log.debug("删除图片:["+url.getUrl()+"]");
				}else{
					log.error("下载图片:[{}]失败!",url);
				}
//				}
			}
		}
		@Override
		public void stop() {
			this.cancle = false;
		}
		
	}
	public static class AnswerAndParseWorker implements ZhujuanProcesser{
		private Logger log = LoggerFactory.getLogger(Worker.class);
		private LinkedList<ZhujuanQues> ids;
		private ZhujuanCrawler service;
		private boolean cancle = true;
		public AnswerAndParseWorker(ZhujuanCrawler service,LinkedList<ZhujuanQues> ids){
			this.ids = ids;
			this.service = service;
		}
		@Override
		public void run() {
			process();
		}
		@Override
		public void process() {
			while(cancle){
				ZhujuanQues ques = null;
				synchronized (ids) {
					while(ids.isEmpty()){
						try {
							ids.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try{
						ques = ids.remove();
					} catch (NoSuchElementException e) {
						 continue;
					}
				}
				if(ques == null) continue;
				log.debug("开始下载题目:[{}]答案和解析图片",ques.getId());
				String answerPath = service.downloadAnswer(ques);
				String parsePath = service.downloadParse(ques);
				//log.info("答案:[{}]",answerPath);
				//log.info("解析:[{}]",parsePath);
				if(!StringUtils.isBlank(parsePath)|| !StringUtils.isBlank(answerPath)){
					ZhujuanQuesWithBLOBs q = new ZhujuanQuesWithBLOBs();
					q.setId(ques.getId());
					q.setAnswerpath(answerPath);
					q.setParsepath(parsePath);
					q.setFlag(1);
					service.updatQues(q);
				}
			}
		}
		public void stop(){
			this.cancle = false;
		}
	}
	public static class Worker implements ZhujuanProcesser {
		private Logger log = LoggerFactory.getLogger(Worker.class);
		private List<Map<String, Object>> source;
		private ZhujuanParse parser;
		private ZhujuanDao dao;

		public Worker(List source, ZhujuanParse parser, ZhujuanDao dao) {
			this.source = source;
			this.parser = parser;
			this.dao = dao;
		}
		public void process(){
			log.info("处理开始!");
			long start = System.currentTimeMillis();
//			List<UStrMap<Object>> list = new ArrayList<UStrMap<Object>>();
			for (Map<String, Object> map : source) {
				// log.info("{}",map);
				UStrMap<Object> item = UStrMap.newInstance();
				String categories = MapUtils.getValue(map, "Categories");
				String quesId = MapUtils.getValue(map, "ID");
				log.debug("开始处理题目[{}]",quesId);
				item.put("id", Integer.parseInt(quesId));
				List<UStrMap<Object>> cateques = parser.parseCategory(categories, Integer.parseInt(quesId));
				//插入category与question关系
				dao.insertCategoryQues(cateques);
				Map<String, Object> quesAbilityMap = MapUtils.getMap(map,
						"QuesAbility");
				String quesAbility = MapUtils.getValue(quesAbilityMap, "ID")
						+ "#" + MapUtils.getValue(quesAbilityMap, "Name");
				item.put("quesAbility", quesAbility);
				String quesAnswer = MapUtils.getValue(map, "QuesAnswer");
				item.put("quesanswer", quesAnswer);
				String quesBody = MapUtils.getValue(map, "QuesBody");
				item.put("quesbody", quesBody);
				String quesParse = MapUtils.getValue(map, "QuesParse");
				item.put("quesparse", quesParse);
				List<UStrMap<Object>> images = parser.parseImageUrl(quesBody);
				images.addAll(parser.parseImageUrl(quesAnswer));
				images.addAll(parser.parseImageUrl(quesParse));
				//插入题目所有图片
				dao.insertImageUrl(images);
				Map<String, Object> quesDiffMap = MapUtils.getMap(map,
						"QuesDiff");
				String quesDiff = MapUtils.getValue(quesDiffMap, "ID") + "#"
						+ MapUtils.getValue(quesDiffMap, "Name");
				item.put("quesdiff", quesDiff);
				Map<String, Object> quesTypeMap = MapUtils.getMap(map, "QuesType");
				String quesType = MapUtils.getValue(quesTypeMap, "ID") + "#"
						+ MapUtils.getValue(quesTypeMap, "Name");
				item.put("questype", quesType);
				String title = MapUtils.getValue(map, "Title");
				item.put("title", title);
				String time = MapUtils.getValue(map, "Time");
				time = time.substring(time.indexOf("(")+1, time.indexOf("+"));
				Date date = new Date(Long.parseLong(time));
				item.put("time", date);
//				list.add(item);
				try {
					dao.insertQues(item);
				} catch (Exception e) {
					log.debug("题目：[{}]已存在",quesId);
				}
				log.debug("题目[{}]处理完成",quesId);
			}
			log.info("处理完成,耗时[{}]秒!",(System.currentTimeMillis() - start)/1000);
		}
		public void run() {
			process();
		}
		@Override
		public void stop() {
		}
	}
	public static class QuesParser implements ZhujuanProcesser{
		private LinkedList<Ques> quess;
		@Override
		public void run() {
			process();
		}

		@Override
		public void process() {
			
		}

		@Override
		public void stop() {
			
		}
	}
}
