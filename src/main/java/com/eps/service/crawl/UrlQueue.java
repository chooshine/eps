package com.eps.service.crawl;

import java.util.LinkedList;
import java.util.List;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eps.service.crawl.bean.CrawlUrl;

/**
 * 地址队列
 * @author hejunwei
 *
 */
public class UrlQueue {
	/**
	 * 未访问队列
	 */
	private static LinkedList<CrawlUrl> queue = new LinkedList<CrawlUrl>();
	
	//private static Set<String> queueSet = new HashSet<String>();
	
	
	/**
	 * 已访问队列
	 */
	private static LinkedList<String> visitedUrlQueue = new LinkedList<String>();
	
	private final static int visitedQueueSize = 100000;
	/**
	 * 获取下一个未访问地址
	 * @return
	 */ 
	public static CrawlUrl getNextUrl(){
		CrawlUrl nextUrl = null;
		while((nextUrl == null) && (!queue.isEmpty())){
			CrawlUrl url = remove();
		//	queueSet.remove(url.getUrl());
			//未存入库的地址及未在已访问队列中的地址为有效地址
			if(!visitedUrlQueue.contains(url.getUrl())){
				nextUrl = url;
			}
		}
		return nextUrl;
	}
	
	/**
	 * 加入未访问队列，加入前会判断未访问队列及已访问队列中是否已存在该地址，如不存在则加入队列中
	 * @param url
	 */
	public static void addUrl(CrawlUrl url){
		//if(!this.contians(url) && !visitedUrlQueue.contains(url.getUrl())){
			queue.add(url);
			//queueSet.add(url.getUrl());
		//}
	}
	/**
	 * 将已访问过的地址加入已访问队列
	 * @param url
	 */
	public static void addVisitedUrl(CrawlUrl url) {
		if(visitedUrlQueue.size() >= visitedQueueSize){
			visitedUrlQueue.removeFirst();
		}
		visitedUrlQueue.add(url.getUrl());
    }
	
	/**
	 * 清空已访问队列
	 */
	public static void cleanVisitedQueue(){
		visitedUrlQueue.clear();
	}
	/**
	 * 判断地址是否在未访问队列及访问队列中存在
	 * @param url
	 * @return
	 */
	public static boolean contians(CrawlUrl url){
		return queue.contains(url.getUrl())&& visitedUrlQueue.contains(url.getUrl());
	}
	
	/**
	 * 未访问队列是否为空
	 * @return
	 */
	public static boolean isEmpty(){
		return queue.isEmpty();
	}
	
	/**
	 * 将地址从未访问队列中移除
	 * @param url
	 */
	public static void removeUrl(CrawlUrl url){
		queue.remove(url);
//		queueSet.remove(url.getUrl());
	}
	
	/**
	 * 将地址列表加入未访问队列
	 * @param list
	 */
	public static void addAll(List<CrawlUrl> list){
		for (CrawlUrl crawlUrl : list) {
			addUrl(crawlUrl);
		}
	}
	/**
	 * 去除未访问队列的队头并返回该地址
	 */
	public static CrawlUrl remove(){
		return queue.removeFirst();
	}
	
	public static int getQueueCount(){
		return queue.size();
	}
	public static int getVisitedQueueCount(){
		return visitedUrlQueue.size();
	}
//	public static void saveQueue(String savepath){
//		synchronized (queue) {
//			File filePath = new File(savepath);
//			if (!filePath.exists())
//				filePath.mkdirs();
//			FileOutputStream fo = null;
//			OutputStreamWriter fw = null;
//			try {
//				File file = new File(savepath + File.separator + "urlQueue.xml");
//				fo = new FileOutputStream(file,false);
//				fw = new OutputStreamWriter(fo, "utf-8");
//				fw.write("<ROOT>");
//				for(int i=0;i<queue.size();i++){
//					CrawlUrl url = queue.get(i);
//					fw.append("<URLITEM>");
//					fw.append("<URL>").append(url.getUrl()).append("</URL>");
//					fw.append("<LEVEL>").append(String.valueOf(url.getLevel())).append("</LEVEL>");
//					fw.append("<MAXLEVEL>").append(String.valueOf(url.getMaxLevel())).append("</MAXLEVEL>");
//					fw.append("<FILTERREG>").append(HttpHelper.regulateXMLStr(url.getFilterReg())).append("</FILTERREG>");
//					fw.append("<TITLEFILTER>").append(url.getTitleFilter()).append("</TITLEFILTER>");
//					fw.append("<CONTENTFILTER>").append(url.getContentFilter()).append("</CONTENTFILTER>");
//					fw.append("<DATEFILTER>").append(url.getDateFilter()).append("</DATEFILTER>");
//					fw.append("<DATEFORMAT>").append(url.getDateFormat()).append("</DATEFORMAT>");
//					fw.append("<TABLENAME>").append(url.getTableName()).append("</TABLENAME>");
//					fw.append("<CHARSET>").append(url.getCharSet()).append("</CHARSET>");
//					fw.append("<AUTHORID>").append(String.valueOf(url.getAuthorId())).append("</AUTHORID>");
//					fw.append("</URLITEM>");
//				}
//				fw.append("</ROOT>");
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally{
//				try {
//					if(fw!=null)fw.close();
//					if(fo!=null)fo.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//		}
//	}
	
//	public static void initQueue(String filePath){
//		try {
//			File file = new File(filePath + File.separator + "urlQueue.xml");
//			if(file.exists()){
//				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//				DocumentBuilder builder = factory.newDocumentBuilder();
//				Document doc = builder.parse(file);
//				Element root = doc.getDocumentElement();
//				NodeList list = root.getChildNodes();
//				for(int i=0;i<list.getLength();i++){
//					Node node = list.item(i);
//					addUrl(createUrl(node));
//				}
//			}
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	private static CrawlUrl createUrl(Node node){
		NodeList list = node.getChildNodes();
		CrawlUrl url = new CrawlUrl();
		for (int i = 0; i < list.getLength(); i++) {
			Node item = list.item(i);
			String nodeName = item.getNodeName();
			String value = null;
			if(item.getFirstChild()!=null)
				value = item.getFirstChild().getNodeValue();
			if("URL".equals(nodeName)){
				url.setUrl(value);
			}
			if("LEVEL".equals(nodeName)){
				url.setLevel(Integer.parseInt(value));
			}
			if("MAXLEVEL".equals(nodeName)){
				url.setMaxLevel(Integer.parseInt(value));
			}
			if("FILTERREG".equals(nodeName)){
				url.setFilterReg(value);
			}
			if("TITLEFILTER".equals(nodeName)){
				url.setTitleFilter(value);
			}
			if("CONTENTFILTER".equals(nodeName)){
				url.setContentFilter(value);
			}
			if("DATEFILTER".equals(nodeName)){
				url.setDateFilter(value);
			}
			if("DATEFORMAT".equals(nodeName)){
				url.setDateFormat(value);
			}
			if("TABLENAME".equals(nodeName)){
				url.setTableName(value);
			}
			if("CHARSET".equals(nodeName)){
				url.setCharSet(value);
			}
			if("AUTHORID".equals(nodeName)){
				url.setAuthorId(Integer.parseInt(value));
			}
		}
		return url;
	}
	
}
