package com.eps.service.weixin.domain;

/**
 * 视频消息
 * @author hejunwei
 *
 */
@SuppressWarnings("serial")
public class VideoQMessage extends QMessage{
	
	private Video video;
	@Override
	void setMsgtype() {
		msgtype = "video";
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}

}
