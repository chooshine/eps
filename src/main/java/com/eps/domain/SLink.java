package com.eps.domain;

import java.util.Date;



public class SLink {
	private int linkId;
	private int userId;
	private String linkNo;
	private Date createDate;
	private int linkStatus;
	private Date useDate;
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLinkNo() {
		return linkNo;
	}
	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getLinkStatus() {
		return linkStatus;
	}
	public void setLinkStatus(int linkStatus) {
		this.linkStatus = linkStatus;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	
}
