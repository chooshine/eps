package com.eps.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.cons.MessageResourceKeys;
import com.eps.domain.User;
import com.eps.service.MessageService;
import com.eps.service.user.UserService;
import com.eps.utils.HttpHelper;
import com.eps.web.BaseController;

@Controller
public class RegistController extends BaseController{
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/regist.html", method=RequestMethod.GET)
	public String regist(ModelMap mm){
		mm.put("title", "免费注册");
		return "regist";
	}
	@RequestMapping(value="/regist.html", method=RequestMethod.POST)
	public String regist(HttpServletRequest request,User user,String checkcode,ModelMap mm){
		mm.put("title", "免费注册");
		if(!service.hashMatchCount(user.getUserNo())){
			mm.put(ERROR_MSG_KEY, MessageService.getMessage(MessageResourceKeys.REGIST_USERNAME_ERROR));
			mm.put("errortype", "1");
			mm.put("user", user);
			return "regist";
		}
		if(!service.hashMatchCount(user.getMailAddress())){
			mm.put(ERROR_MSG_KEY, MessageService.getMessage(MessageResourceKeys.REGIST_MAIL_ERROR));
			mm.put("errortype", "2");
			mm.put("user", user);
			return "regist";
		}
		if(!StringUtils.lowerCase((String) request.getSession().getAttribute(CHECKCODE)).equals(StringUtils.lowerCase(checkcode))){
			mm.put(ERROR_MSG_KEY, MessageService.getMessage(MessageResourceKeys.CHECKCODE_ERROR));
			mm.put("user", user);
			mm.put("errortype", "3");
			return "regist";
		}else{
			user.setLastVisit(new Date());
			user.setIp(request.getRemoteAddr());
			if(service.regist(user)){
				//mm.put(SUCCESS_MSG_KEY, MessageService.getMessage(MessageResourceKeys.REGIST_SUCCESS));
				//mm.put("title", "注册成功");
//				mm.remove("user");
				//mm.put("user", user);
				//return "alipay/submitForm";
				user = service.getUserByUserName(user.getUserNo());
				setSessionUser(request, user);
				request.getSession().setAttribute("prvVisit", user.getLastVisit());
				request.getSession().setAttribute("prvIp", user.getIp());
				return "redirect:/regist/success.html";
			}else{
				//mm.put("title", "免费注册");
				mm.put(ERROR_MSG_KEY, MessageService.getMessage(MessageResourceKeys.REGIST_ERROR));
				return "regist";
			}
		}
		//return "index";
	}
	
	@RequestMapping(value="/regist/success.html")
	public String registSuccess(ModelMap mm){
		mm.put("title", "注册成功");
		return "registSuccess";
	}
	@RequestMapping(value="/ajax/checkUserName.json")
	public String checkUserName(String username, ModelMap mm){
		//System.out.println(username);
		mm.put(SUCCESS_MSG_KEY, false);
		if(service.hashMatchCount(username)){
			mm.put(SUCCESS_MSG_KEY, true);
		}
		mm.put("userName", username);
		return "jsonView";
	}
	
	@RequestMapping(value="/agreement.html")
	public String agreement(ModelMap mm){
		mm.put("title", "注册协议");
		return "agreement";
	}
}
