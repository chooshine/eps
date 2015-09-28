package com.eps.service.weixin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eps.service.weixin.domain.QMessage;
import com.eps.service.weixin.domain.TextQMessage;
import com.eps.utils.HttpHelper;
import com.eps.utils.HttpRespons;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class WeixinService {
	private final static int NTHREADS = 3;
	private final ExecutorService fixedThreadPool;
	static Logger log = LoggerFactory.getLogger(WeixinService.class);
	
	public WeixinService(){
		fixedThreadPool = Executors.newFixedThreadPool(NTHREADS);
	}
	public void execute(WeixinThread r){
		log.info("加入执行队列:[{}]",r);
		fixedThreadPool.execute(r);
	}
	
	
	public static IMessageProcess createProcess(String msgType){
		if(Contains.MSGTYPE_EVENT.equals(msgType)){
			return new EventMessageProcess();
		}
		return new SimpleMessageProcess();
	}
	
	public static HttpRespons sendMessage(QMessage message){
		Gson gson = new GsonBuilder().create();
		String send = gson.toJson(message);
		log.info("发送信息:[{}]",send);
		try {
			HttpRespons res = HttpHelper.sendPost(Contains.SENDURL, send);
			log.info("发送信息成功,返回内容：[{}]",res.getContent());
			return res;
		} catch (IOException e) {
			log.error("发送信息失败",e);
		}
		return null;
	}
	
	/**
	 * 解析xml
	 * <xml>
 	 * <ToUserName><![CDATA[toUser]]></ToUserName>
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 
	 * <CreateTime>1348831860</CreateTime>
 	 * <MsgType><![CDATA[text]]></MsgType>
	 * <Content><![CDATA[this is a test]]></Content>
	 * <MsgId>1234567890123456</MsgId>
	 * </xml>
	 */
	public static WeixinMessage parseXml(String xmlStr){
		log.info("开始解析接收的xml:[{}]",xmlStr);
		WeixinMessage msg = new WeixinMessage();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			Element root = doc.getRootElement();
			Iterator iterator = root.elementIterator();
			while(iterator.hasNext()){
				Element node = (Element) iterator.next();
				String nodeName = node.getName();
				String nodeText = node.getTextTrim();
				Method method = WeixinMessage.class.getMethod("set"+nodeName, String.class);
				method.invoke(msg, nodeText);
			}
			log.info("解析完成:[{}]",msg);
		} catch (Exception e) {
			log.error("解析出错",e);
		}
		
		return msg;
	}
//	public static void main(String[] args) {
//		TextQMessage msg = new TextQMessage();
//		msg.setText("回复测试");
//		msg.setTouser("oVzfUsz5uOfJ-Ebd2b35emB2vD-Y");
//		Gson gson = new GsonBuilder().create();
//		String send = gson.toJson(msg);
//		System.out.println(send);
//	}
}
