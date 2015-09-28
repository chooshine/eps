package com.eps.domain.ques;

import java.util.List;
import java.util.Map;

import com.eps.utils.UStrMap;

public class Option {
	private int optionId;
	private String optionNo;
	private boolean isRight;
	private List<Content> contents;
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public String getOptionNo() {
		return optionNo;
	}
	public void setOptionNo(String optionNo) {
		this.optionNo = optionNo;
	}
	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	public List<Content> getContents() {
		return contents;
	}
	public void setContents(List<Content> content) {
		this.contents = content;
	}
	/**
	 * 将字符串转化为List<Content> 并将图片返回
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Image> setContents(String content){
		UStrMap<Object> map = Content.parseContent(content);
		this.contents = (List<Content>) map.get(Content.TYPE_TEXT);
		return (Map<String, Image>) map.get(Content.TYPE_IMG);
	}
	
}
