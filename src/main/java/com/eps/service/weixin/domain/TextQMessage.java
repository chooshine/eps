package com.eps.service.weixin.domain;

import com.eps.service.weixin.Contains;

/**
 * 文本消息
 * @author heh144
 *
 */
@SuppressWarnings("serial")
public class TextQMessage extends QMessage{
	private Text text = new Text();
	
	public String getText() {
		return text.content;
	}

	public void setText(String content) {
		this.text.content = content;
	}
	
	@Override
	void setMsgtype() {
		msgtype = Contains.MSGTYPE_TEXT;
	}
	private class Text{
		String content;
	}


	
}
