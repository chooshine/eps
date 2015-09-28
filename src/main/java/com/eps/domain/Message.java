package com.eps.domain;

public class Message {
	
	/**
	 * 匿名留言人Id
	 */
	public static final int ANONYMOUS_USER = -1;
	
	/**
	 * 留言状态
	 */
	public static final int NO_REPLY = 0;
	public static final int REPLYED = 1;
	public static final int DELETED = 2;
	
	
	private int messageId;
	/**
	 * 留言发送者Id
	 */
	private int messageUserId;
	/**
	 * 留言内容
	 */
	private String messageContent;
	/**
	 * 留言日期
	 */
	private String messageDate;
	/**
	 * 留言回复人Id
	 */
	private int reUserId;
	/**
	 * 留言回复内容
	 */
	private String reContent;
	/**
	 * 留言回复日期
	 */
	private String reDate;
	/**
	 * 留言状态，未回复为0，已回复为1，已删除为2
	 */
	private int state;
	/**
	 * 留言者Ip地址
	 */
	private String messageIp;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getMessageUserId() {
		return messageUserId;
	}
	public void setMessageUserId(int messageUserId) {
		this.messageUserId = messageUserId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public int getReUserId() {
		return reUserId;
	}
	public void setReUserId(int reUserId) {
		this.reUserId = reUserId;
	}
	public String getReContent() {
		return reContent;
	}
	public void setReContent(String reContent) {
		this.reContent = reContent;
	}
	public String getReDate() {
		return reDate;
	}
	public void setReDate(String reDate) {
		this.reDate = reDate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessageIp() {
		return messageIp;
	}
	public void setMessageIp(String messageIp) {
		this.messageIp = messageIp;
	}
	
	
}
