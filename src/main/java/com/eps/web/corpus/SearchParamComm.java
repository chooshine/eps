package com.eps.web.corpus;

import org.apache.commons.lang.ArrayUtils;

import com.eps.utils.StringHelper;

public class SearchParamComm {
	/**
	 * 搜索关键词列表
	 */
	private String[] keys;
	/**
	 * 左侧字符数
	 */
	private int leftNum = 100;
	/**
	 * 右侧字符数
	 */
	private int rightNum = 100;
	/**
	 * 发表时间前界
	 */
	private String startPublicationDate;
	/**
	 * 发表时间后界
	 */
	private String endPublicationDate;
	/**
	 * 关键位置关系
	 * 
	 */
	private String placeType;
	/**
	 * 搜索作品名称
	 */
	private String articleName;
    /**
     * 关键词间隔
     */
	private int intervalNum = 0;
	/**
	 * 作家列表
	 */
	private String[] authors;
	/**
	 * 语种
	 */
	private String language = "ja";
	
	/**
	 * 语料库类型,默认为新闻库
	 */
	private String repositoryType = "l_news";
	
	/**
	 * 查询数据库样本范围
	 */
	private int searchLimit=100;
	/**
	 * 是否是分页请求的查询
	 * 默认为不是分页请求
	 */
	private int isPageQuery = 0;
	
	private String key;
	
	private String keyTogether = "";
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
//		if(key.indexOf("([ |　])")!=-1){
		this.setKeys(key.replaceAll("([ |　])", "\\|").split("\\|"));
//		}else{
//			this.setKeys(new String[]{key});
//		}
		for(int i=0;i<keys.length;i++){
			String string = keys[i];
			String temp = StringHelper.replaceBlank(string);
			if(temp.equals("")){
				keys = (String[]) ArrayUtils.remove(keys, i);
				i--;
			}
		}
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public int getLeftNum() {
		return leftNum;
	}
	public void setLeftNum(int leftNum) {
		this.leftNum = leftNum;
	}
	
	public void setLeftNum(String leftNum){
		this.leftNum = Integer.valueOf(leftNum);
	}
	public int getRightNum() {
		return rightNum;
	}
	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}
	public void setRightNum(String rightNum){
		this.rightNum = Integer.valueOf(rightNum);
	}
	public String getStartPublicationDate() {
		return startPublicationDate;
	}
	public void setStartPublicationDate(String startPublicationDate) {
		this.startPublicationDate = startPublicationDate;
	}
	public String getEndPublicationDate() {
		return endPublicationDate;
	}
	public void setEndPublicationDate(String endPublicationDate) {
		this.endPublicationDate = endPublicationDate;
	}
	public String getPlaceType() {
		return placeType;
	}
	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public int getIntervalNum() {
		return intervalNum;
	}
	public void setIntervalNum(int intervalNum) {
		this.intervalNum = intervalNum;
	}
	public void setIntervalNum(String intervalNum){
		this.intervalNum = Integer.valueOf(intervalNum);
	}
	public String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRepositoryType() {
		return repositoryType;
	}
	public void setRepositoryType(String repositoryType) {
		this.repositoryType = repositoryType;
	}
	public int getIsPageQuery() {
		return isPageQuery;
	}
	public void setIsPageQuery(int isPageQuery) {
		this.isPageQuery = isPageQuery;
	}
	public String getKeyTogether() {
		return keyTogether;
	}
	public void setKeyTogether(String keyTogether) {
		
		this.keyTogether = keyTogether.replaceAll("\\s{1,}|,", "\\|");
	}
	public int getSearchLimit() {
		return searchLimit;
	}
	public void setSearchLimit(int searchLimit) {
		this.searchLimit = searchLimit;
	}
	
	
}
