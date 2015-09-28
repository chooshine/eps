package com.eps.service.weixin;

public interface IMessageProcess {
	
	/**
	 * 异步回复
	 * @param message
	 */
	public void process(WeixinMessage message);
	
	/**
	 * 直接回复
	 * @param message
	 * @return
	 */
	public WeixinMessage createResponseMsg(WeixinMessage message);
}
