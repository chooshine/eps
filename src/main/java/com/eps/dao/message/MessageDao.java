package com.eps.dao.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.domain.Message;
import com.eps.utils.UStrMap;
@Repository
public class MessageDao extends BaseDao {
	@Value("${message.insertmessage}")
	private String insertMessage;
	
	@Value("${message.update}")
	private String update;
	
	@Value("${message.get.bymessageuserid}")
	private String getByMessageUserId;
	
	@Value("${message.get.bystate}")
	private String getByState;
	
	@Value("${message.get.all.bystate}")
	private String getAllByState;
	
	@Value("${message.update.state}")
	private String updateStateByMessageId;
	
	@Value("${message.update.reContent}")
	private String updateReContent;
	
	@Value("${message.get.usermessage}")
	private String getUserMessage;
	/**
	 * 增加留言信息，如果返回1代表向数据库成功插入数据
	 * @param sql
	 * @param m
	 * @return
	 */
	public int createMessage(Message m) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("message_user_id", m.getMessageUserId());
		params.put("message_content", m.getMessageContent());
		params.put("message_date", m.getMessageDate());
		params.put("message_ip", m.getMessageIp());
		return this.excute(insertMessage, params);
	}
	
	/**
	 * 根据留言编号更新留言信息，为留言添加回复的内容，并且更改留言状态
	 * @param m
	 */
	public int updateMessage(Message m){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("re_content", m.getReContent());
		params.put("re_user_id", m.getReUserId());
		params.put("re_date", m.getReDate());
		params.put("state", m.getState());
		params.put("message_id", m.getMessageId());
		return this.excute(update, params);
	}
	
	/**
	 * 查看所有未删除留言信息
	 * @param pageNo
	 * @return
	 */
	public Page queryAllMessageByState(int pageNo) {
		return pageQuery(getAllByState, pageNo, null);
	}
	
	/**
	 * 已注册用户查看自己的所有未被删除的留言
	 * @param message_user_id
	 * @param pageNo
	 * @return
	 */
	public Page queryMessageByUserId(int message_user_id, int pageNo){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("message_user_id", message_user_id);
		return pageQuery(getByMessageUserId, pageNo, params);
	}
	
	/**
	 * 查询某一状态的留言信息，比如查看已回复状态留言信息
	 * @param state
	 * @param pageNo
	 * @return
	 */
	public Page queryMessageByState(int state, int pageNo){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("state", state);
		return pageQuery(getByState, pageNo, params);
	}
	
	/**
	 * 根据留言信息编号，对垃圾留言进行更改状态为"2"
	 * @param messageId
	 * @param state
	 * @param m
	 * author:message
	 */
	public int updateMessageStateByMessageId(int messageId,int state){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("message_id", messageId);
		params.put("state", state);
		return this.excute(updateStateByMessageId, params);
	}
	
	/**
	 * 查询某个用户留言信息
	 */
	public Page getUserMessage(String userNo,int pageNo){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("USER_NO", userNo);
		params.put("luser_no", "%"+ userNo+"%");
		return pageQuery(getUserMessage, pageNo, params);
	}
	
}
