package com.eps.service.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eps.domain.BaseDomain;

@SuppressWarnings("serial")
public class WeixinThread extends BaseDomain implements Runnable {
	Logger log = LoggerFactory.getLogger(WeixinThread.class);
	private String xmlStr;
	public WeixinThread(String xml){
		this.xmlStr = xml;
	}
	public void run() {
		WeixinMessage msg = WeixinService.parseXml(xmlStr);
		IMessageProcess process = WeixinService.createProcess(msg.getMsgType());
		process.process(msg);
	}
	
	public String getXmlStr() {
		return xmlStr;
	}
	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}
	
}
