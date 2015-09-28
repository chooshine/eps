package com.eps.service.weixin;


import com.eps.domain.BaseDomain;

@SuppressWarnings("serial")
public class WeixinMessage extends BaseDomain{
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String content;
	private String msgType;
	private String event;
	private String eventKey;
	private String ticket;
	private String msgId;

	public WeixinMessage(){}
	public WeixinMessage(String to,String from, String msgType,String content){
		this.toUserName = to;
		this.fromUserName = from;
		this.createTime = System.currentTimeMillis()+"";
		this.msgType = msgType;
		this.content = content;
	}
	public WeixinMessage(String to,String from,String msgType){
		this.toUserName = to;
		this.fromUserName = from;
		this.createTime = System.currentTimeMillis()+"";
		this.msgType = msgType;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * 构造回复xml结构，回复类型text
	 * @return
	 */
	public String createTextXmlMessage(){
		StringBuffer sb = new StringBuffer("<xml>");
		sb.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(createTime).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		sb.append("<Content><![CDATA[").append(content).append("]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}
}
