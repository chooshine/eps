package com.eps.web.user;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.cons.CommonConstant;
import com.eps.cons.MessageResourceKeys;
import com.eps.domain.User;
import com.eps.encrypt.Encrypt_MD5;
import com.eps.service.MessageService;
import com.eps.service.user.UserService;
import com.eps.utils.HttpHelper;
import com.eps.web.BaseController;
import com.eps.web.CookieUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class LoginController extends BaseController {
	@Autowired
	private UserService service;
//	@Autowired
//	private MessageSource message;
	
	@RequestMapping(value="/login.html", method=RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response, String username,String password, /*String checkcode,*/boolean isCookie, ModelMap mm){
		if(!service.hasMatchCount(username, Encrypt_MD5.encrypt(password))){
			mm.put(ERROR_MSG_KEY, MessageService.getMessage(MessageResourceKeys.LOGIN_ERROR));
			mm.put("title", "登陆");
			return "login";
		}else{
			User user = service.getUserByUserName(username);
			if(user.getLocked() == User.LOCKED){
				mm.put(ERROR_MSG_KEY, MessageService.getMessage(MessageResourceKeys.USER_LOCKED));
				mm.put("title", "登陆");
				return "login";
			}
			request.getSession().setAttribute("prvVisit", user.getLastVisit());
			request.getSession().setAttribute("prvIp", user.getIp());
			user.setLastVisit(new Date());
			user.setIp(request.getRemoteAddr());
			//登陆用户数量及IP验证 start
			if(user.getIsEnterpriseUser() == 1){ //集体帐号用户限制同一帐号登陆数量及IP地址
				if(MapStatStore.getUserSessionCount(user)>= user.getSessionNum()){
					mm.put(ERROR_MSG_KEY, "该用户线程数已超标");
					mm.put("title", "登陆");
					return "login";
				}
				if(!user.hasInIpLimit()){
					mm.put(ERROR_MSG_KEY, "IP地址不在允许访问范围内");
					mm.put("title", "登陆");
					return "login";
				}
			}else{                       //普通用户先进先出
				if(MapStatStore.getUserSessionCount(user)> 0){
					Set<HttpSession> sessions = MapStatStore.USERSESSIONS.get(user.getUserId());
					for (HttpSession session : sessions) {
						session.invalidate();
					}
				}
			}
			MapStatStore.login(user, request.getSession());
			//end
			service.loginSuccess(user);
			setSessionUser(request, user);
			if(isCookie){
				CookieUtils.addCookie(response, CommonConstant.USER_CONTEXT, user.getUserNo());
			}else{
				CookieUtils.deleteCookie(request, response, CommonConstant.USER_CONTEXT);
			}
			String toUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);
			if(StringUtils.isBlank(toUrl)){
				mm.put("title", "首页");
				mm.put(SUCCESS_MSG_KEY, MessageService.getMessage(MessageResourceKeys.LOGIN_SUCCESS));
				return "index";
			}
			return "redirect:" + toUrl;
		}
	}
	
	@RequestMapping(value="/index.html")
	public String index(HttpServletRequest request,ModelMap mm){
		String value = HttpHelper.getCookieValue(request, CommonConstant.USER_CONTEXT);
		if(value!= null){
			mm.put("cookieName", value);
		}
		mm.put("title", "首页");
		return "index";
	}
	
	@RequestMapping(value="/login.html",method=RequestMethod.GET)
	public String login(HttpServletRequest request,ModelMap mm){
		String value = HttpHelper.getCookieValue(request, CommonConstant.USER_CONTEXT);
		if(value!= null){
			mm.put("cookieName", value);
		}
		mm.put("title", "登陆");
		return "login";
	}
	
	@RequestMapping(value="/logout.html")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		MapStatStore.logoff(getSessionUser(request), request.getSession());
		request.getSession().removeAttribute(CommonConstant.USER_CONTEXT);
		return "redirect:index.html";
	}
}	
