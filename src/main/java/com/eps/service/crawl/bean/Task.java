package com.eps.service.crawl.bean;

public class Task {
	private int id;
	private int categoryId;
	private String type;
	private int bankId;
	private int flag;
	private int pageNo = 1;
	
	public Task(){}
	public Task(int id, int categoryId, String type, int bankId){
		this.id  = id;
		this.categoryId = categoryId;
		this.type = type;
		this.bankId = bankId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
