package com.eps.service.weixin.domain;

import com.eps.domain.BaseDomain;

@SuppressWarnings("serial")
public class Video extends BaseDomain {
	private String meida_id;
	private String thumb_media_id;
	private String title;
	private String description;
	public String getMeida_id() {
		return meida_id;
	}
	public void setMeida_id(String meida_id) {
		this.meida_id = meida_id;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
