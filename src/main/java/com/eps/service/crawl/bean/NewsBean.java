package com.eps.service.crawl.bean;

public class NewsBean {
	private String title;
	private int authorId;
	private String date;
	private String content;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "title:["+this.title+"]\ncontent:["+this.content+"]\ndate:["+this.date+"]\nurl:["+this.url+"]";
	}
	
	
}
