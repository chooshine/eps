package com.eps.web.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.dao.Page;
import com.eps.domain.Message;
import com.eps.domain.User;
import com.eps.service.message.LeaveMessageService;
import com.eps.web.BaseController;
@Controller
public class MessageController extends BaseController {
	@Autowired 
	private LeaveMessageService ms;
	
	private Page page;
	
	/**
	 * 提交留言
	 * @param request
	 * @param mm
	 * @return jbb
	 */
	public ModelMap submitMessage(HttpServletRequest request, ModelMap mm) {
		Message message = new Message();
		int message_user_id = Message.ANONYMOUS_USER;
		String login_user_image = null;
		//从页面上获取用户id
		User user = this.getSessionUser(request);
		if(user != null) {
			message_user_id = (int)user.getUserId();
		}
		String message_content = (String)request.getParameter(("message_content"));
		String message_ip = request.getRemoteAddr();
		
		//获得当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String message_date = sdf.format(date);
		
		message.setMessageUserId(message_user_id);
		message.setMessageContent(message_content);
		message.setMessageIp(message_ip);
		message.setMessageDate(message_date);
		
		//如果提交成功，则调用查看所有留言方法
		if(ms.submitMessage(message) == 1) {
			page = ms.viewAllMessageByState(1);
			
			mm.put("page", page);
			mm.put("title", "留言板");
			mm.put("choice","viewAll");
			mm.put("tag","2");
		}
		return mm;
	}
	
	/**
	 * 查看所有留言
	 * @return jbb
	 */
	public ModelMap viewAllMessage(HttpServletRequest request,ModelMap mm) {
		
		String pageNo = request.getParameter("pageNo");
		if(pageNo!=null) {
			page = ms.viewAllMessageByState(Integer.parseInt(pageNo));
		} else {
			page = ms.viewAllMessageByState(1);
		}
		
		mm.put("choice", "viewAll");
		mm.put("page", page);
		mm.put("title", "留言板");
		mm.put("tag","2");
		return mm;
	}
	
	/**
	 * 已注册用户查看自己所有留言
	 * @return jbb
	 */
	public ModelMap viewMessageByUserId(HttpServletRequest request, ModelMap mm) {
		
		User user = this.getSessionUser(request);
		int message_user_id = (int) user.getUserId();
		String pageNo = request.getParameter("pageNo");
		if(pageNo!=null) {
			page = ms.viewMessageByUserId(message_user_id, Integer.parseInt(pageNo));
		} else {
			page = ms.viewMessageByUserId(message_user_id, 1);
		}
		
		mm.put("choice", "viewById");
		mm.put("page", page);
		mm.put("title", "留言板");
		mm.put("tag","1");
		return mm;
	}
	
	/**
	 * 根据得到的参数值进行跳转
	 * @return jbb
	 */
	@RequestMapping(value="/message/message.html")
	public String chooseMethod(HttpServletRequest request, ModelMap mm) {
		
		String choice = request.getParameter("choice");
		if(choice == null) {
			this.viewAllMessage(request, mm);
			return "message/message";
		}
		if(choice.equals("submitMessage")) {
			this.submitMessage(request, mm);
		} else if(choice.equals("viewAll")) {
			this.viewAllMessage(request, mm);
		} else if(choice.equals("viewById")) {
			this.viewMessageByUserId(request, mm);
		}
		
		return "message/message";
	}
	
	
	/**
	 * 添加回复内容
	 * @return lch
	 */
	@RequestMapping(value="/message/reply.json")
	public String updateMessage(ModelMap mm,HttpServletRequest request){
		Message message=new Message();
		User user = getSessionUser(request);
		int messageId=Integer.parseInt(request.getParameter("messageId"));
		String reContent=request.getParameter("reContent");
		
		//获得当前时间
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String re_date=sdf.format(date);
		
		message.setMessageId(messageId);
		message.setReUserId((int)user.getUserId());
		message.setReContent(reContent);
		message.setReDate(re_date);
		message.setState(Message.REPLYED);
		
		mm.put("messageId", messageId);	
		mm.put("reContent", reContent);	
		mm.put("reTime",re_date);
		mm.put("pageNo", 1);
		mm.put("state", Message.REPLYED);
		
		ms.updateMessage(message);
		
		return "jsonView";
	}
	
	
	/**
	 * 查看所有留言信息，但不包含已删除的留言
	 * @return lch
	 */
	@RequestMapping(value="/message/messageBack.html")
	public String getAllMessage(ModelMap mm,HttpServletRequest request){
			String pageNo=request.getParameter("pageNo");
		
				if(pageNo!=null){
					page=ms.viewAllMessageByState(Integer.parseInt(pageNo));
				}else{
					page=ms.viewAllMessageByState(1);
				}
					mm.put("pages", page);
					mm.put("title", "留言板");
			
			return "message/messageBack";
		
			
	}
	
	
	
	/**
	 * 根据留言信息编号，对垃圾留言进行更改状态为"2"
	 * @return lch
	 */
	@RequestMapping(value="/message/deleteMessage.json")
	public String updateStateByMessageId(ModelMap mm,HttpServletRequest request){
		int messageId=Integer.parseInt(request.getParameter("messageId"));
		//int state=Integer.parseInt(request.getParameter("state"));
		mm.put("message", ms.updateMessageState(messageId, Message.DELETED));
		return "jsonView";
	}
	

	/**
	 * 查看已注册用户的所有留言
	 * @return lch
	 */
	@RequestMapping(value="/message/getUserMessage.html")
	public String getMessageByUserId(ModelMap mm,HttpServletRequest request,String userNo){
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		page=ms.getUserMessage(userNo, pageNo);
		mm.put("pages", page);
		mm.put("title", "留言板");
		mm.put("userNo", userNo);
		return "message/searchMessage";
	}
	
	/**
	 * 查询某一状态的留言信息
	 * @return lch
	 */
	@RequestMapping(value="/message/replyState.html")
	public String getMessageByState(ModelMap mm,HttpServletRequest request){
		int state=Integer.parseInt(request.getParameter("state"));
		String pageNo = request.getParameter("pageNo");
		if(pageNo!=null){
			if(state==Message.NO_REPLY){
				page=ms.getMessageByState(Message.NO_REPLY, Integer.parseInt(request.getParameter("pageNo")));
				mm.put("pages", page);
				mm.put("title", "留言板");
				return "message/weiMessage";
			}else if(state==Message.REPLYED){
				page=ms.getMessageByState(Message.REPLYED, Integer.parseInt(request.getParameter("pageNo")));
				mm.put("pages", page);
				mm.put("title", "留言板");
				return "message/yiMessage";
			}else{
				page=ms.getMessageByState(Message.DELETED, Integer.parseInt(request.getParameter("pageNo")));
				mm.put("pages", page);
				mm.put("title", "留言板");
				return "message/delMessage";
			}
		}else{
			page=ms.getMessageByState(state, 1);
		}
		
		mm.put("pages", page);
		mm.put("title", "留言板");
		return "message/messageBack";
	}
}
