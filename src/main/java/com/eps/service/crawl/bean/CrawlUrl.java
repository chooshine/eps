package com.eps.service.crawl.bean;

import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.NodeFilter;

import com.eps.domain.BaseDomain;
import com.eps.utils.UStrMap;

public class CrawlUrl extends BaseDomain{
	/**
	 * 链接地址
	 */
	//private String url;
	
	private URL url; //地址
	/**
	 * 地址层级
	 */
	private int level; //当前地址深度
	
	private int authorId;//名称
	
	private String titleFilter;//标题过滤器
	
	private String contentFilter; //内容过滤器
	
	private String dateFilter; //日期过滤器
	
	private String dateFormat; //日期格式
	
	private String filterReg; //地址过滤正则表达式
	
	private int maxLevel; //抓取最大深度
	private String tableName; //抓取内容存储表格名称
	
	private String charSet; //字符编码格式
	public CrawlUrl(){}
	public CrawlUrl(String url,int level){
		try {
			this.url = new URL(url);
			this.level = level;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 克隆除URL和level的相关信息
	 */
	public CrawlUrl clone(){
		CrawlUrl newUrl = new CrawlUrl();
		newUrl.setAuthorId(this.getAuthorId());
		newUrl.setContentFilter(this.getContentFilter());
		newUrl.setDateFilter(this.getDateFilter());
		newUrl.setDateFormat(this.getDateFormat());
		newUrl.setFilterReg(this.getFilterReg());
		newUrl.setMaxLevel(this.getMaxLevel());
		newUrl.setTitleFilter(this.getTitleFilter());
		newUrl.setTableName(this.getTableName());
		newUrl.setCharSet(this.charSet);
		return newUrl;
	}
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("authorid", this.authorId);
		map.put("url", this.getUrl());
		map.put("level", this.level);
		map.put("maxlevel", this.maxLevel);
		map.put("titletag", this.titleFilter);
		map.put("contenttag", this.contentFilter);
		map.put("datetag", this.dateFilter);
		map.put("dateFormat", this.dateFormat);
		map.put("filterReg", this.filterReg);
		map.put("tableName", this.tableName);
		map.put("charSet", this.charSet);
		return map;
	}
	public String getUrl() {
		return url.toString();
	}
	
	public URL getURL(){
		return url;
	}
	public void setUrl(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public String getTitleFilter() {
		return titleFilter;
	}
	public void setTitleFilter(String titleFilter) {
		this.titleFilter = titleFilter;
	}
	public String getContentFilter() {
		return contentFilter;
	}
	public void setContentFilter(String contentFilter) {
		this.contentFilter = contentFilter;
	}
	public String getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getFilterReg() {
		return filterReg;
	}
	public void setFilterReg(String filterReg) {
		this.filterReg = filterReg;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	@Override
	public boolean equals(Object obj) {
		CrawlUrl cu = (CrawlUrl) obj;
		return cu.getUrl().equals(this.getUrl());
	}
	@Override
	public int hashCode() {
		return getUrl().hashCode();
	}
	
	
}
