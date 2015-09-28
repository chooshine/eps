package com.eps.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.message.MessageDao;
import com.eps.domain.Message;

@Service
public class LeaveMessageService {

	@Autowired
	MessageDao messageDao;
	/**
	 * 提交留言
	 * @param message
	 * @return
	 */
	public int submitMessage(Message message) {
		return messageDao.createMessage(message);
	}
	
	/**
	 * 查看所有未删除留言
	 */
	 public Page viewAllMessageByState(int pageNo){
		 return messageDao.queryAllMessageByState(pageNo);
	 }
	 
	 /**
	  * 查看已注册用户的所有未删除留言
	  */
	 public Page viewMessageByUserId(int message_user_id, int pageNo) {
		return messageDao.queryMessageByUserId(message_user_id, pageNo);
	 }
	 
	 /**
	  * 根据留言信息编号，对垃圾留言进行更改状态为"2"
	  */
	 public int updateMessageState(int messageId,int state){
		return messageDao.updateMessageStateByMessageId(messageId, state);
	 }
		
	 /**
	  * 查看已注册用户的所有留言
	  */
	 public Page getUserMessage(String userNo,int pageNo){
		return messageDao.getUserMessage(userNo, pageNo);
	 }
	 
	 /**
	  * 查询已回复或者未回复的留言信息
	  */
	 public Page getMessageByState(int state,int pageNo){
		return messageDao.queryMessageByState(state, pageNo);
	 }
		
	 /**
	  * 添加回复内容
	  */
	 public int updateMessage(Message message){
		return messageDao.updateMessage(message);
	 }
}
