package com.eps.service.weixin.domain;


/**
 * 语音消息
 * @author hejunwei
 *
 */
@SuppressWarnings("serial")
public class VoiceQMessage extends QMessage{
	private Media voice = new Media();
	
	public String getVoice() {
		return voice.getMedia_id();
	}

	public void setVoice(String media_Id) {
		voice.setMedia_id(media_Id);
	}

	@Override
	void setMsgtype() {
		msgtype = "voice";
	}

}
