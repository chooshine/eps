package com.eps.service.weixin.domain;

import com.eps.domain.BaseDomain;

@SuppressWarnings("serial")
public abstract class QMessage extends BaseDomain{

	private String touser;
	
	protected String msgtype;
	public QMessage(){
		setMsgtype();
	}
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	abstract void setMsgtype();
	
}
