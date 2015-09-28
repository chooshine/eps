package com.eps.service.weixin.domain;

import com.eps.service.weixin.Contains;

/**
 * 图片消息
 * @author hejunwei
 *
 */
@SuppressWarnings("serial")
public class ImageQMessage extends QMessage{
	private Media image = new Media();
	
	public String getImage() {
		return image.getMedia_id();
	}

	public void setImage(String mediaId) {
		image.setMedia_id(mediaId);
	}

	@Override
	void setMsgtype() {
		msgtype = Contains.MSGTYPE_IMAGE;
	}

}
