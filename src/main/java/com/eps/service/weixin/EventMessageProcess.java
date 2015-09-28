package com.eps.service.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eps.service.weixin.domain.TextQMessage;
import com.eps.utils.HttpRespons;

/**
 * 事件信息处理
 * @author Hejunwei
 *
 */
public class EventMessageProcess implements IMessageProcess{
	Logger log = LoggerFactory.getLogger(EventMessageProcess.class);
	public void process(WeixinMessage msg) {
		log.info("开始处理事件信息,信息类型:[{}]",msg.getEvent());
		//订阅事件处理
		if(Contains.EVENT_TYPE_SUB.equals(msg.getEvent())){
			TextQMessage message = new TextQMessage();
			message.setTouser(msg.getFromUserName());
			message.setText("您好，欢迎关注畅先网。");
			HttpRespons res = WeixinService.sendMessage(message);
		}
		//退订事件处理
		if(Contains.EVENT_TYPE_UNSUB.equals(msg.getEvent())){
			
		}
	}
	public WeixinMessage createResponseMsg(WeixinMessage message) {
		WeixinMessage res = new WeixinMessage();
		res.setToUserName(message.getFromUserName());
		res.setFromUserName(message.getToUserName());
		if(Contains.EVENT_TYPE_SUB.equals(message.getEvent()))
			res.setContent("您好，欢迎关注畅先网。\n发送注册手机号码可查看孩子的家庭作业");
		else if(Contains.EVENT_TYPE_UNSUB.equals(message.getEvent()))
			res.setContent("感谢您的关注。");
		//自定义菜单推送事件
		else{
			String key = message.getEventKey();
			if(Contains.EVENT_CLICK_HW_KEY.equals(key)){
				res.setContent("请输入您在您孩子学校登记的监护人手机号码进行作业查询!");
			}
		}
		res.setMsgType(Contains.MSGTYPE_TEXT);
		res.setCreateTime(System.currentTimeMillis()+"");
		return res;
	}

}
