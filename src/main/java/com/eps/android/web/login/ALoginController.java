package com.eps.android.web.login;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.android.service.login.LoginService;
import com.eps.encrypt.Encrypt_MD5;
import com.eps.service.user.UserService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;

@Controller
public class ALoginController extends BaseController {
	
	@Autowired private LoginService loginService;
	@Autowired private UserService userService;
	
	@RequestMapping(value="/androidTest.html")
	public String toAndroidTest() {
		
		return "androidTest";
	}
	
	//登录
	@RequestMapping(value="/android/login.json")
	public String login(HttpServletRequest request, ModelMap mm) {
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = LStrMap.newInstance();
		try {
			String username = URLDecoder.decode(request.getParameter("uname"), "UTF-8");
			String password = request.getParameter("pw");
			
			if (username==null || username.equals("") || password==null || password.equals("")) {
				map.put("userid", null);
				map.put("msg", "用户名或密码不正确");
				mm.put(SUCCESS_MSG_KEY, false);
			} else if (loginService.existUser(username, Encrypt_MD5.encrypt(password))) {
				long userId = (Long)userService.getUserByUserName(username).getUserId();
				map.put("userid", userId);
				map.put("sortlist", loginService.getSortListOfStudent(userId));
				map.put("msg", "登录成功");
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
			} else {
				map.put("userid", null);
				map.put("msg", "用户名或密码不正确");
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("userid", null);
			map.put("msg", "用户名或密码不正确");
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		list.add(map);
		mm.put("DATA", list);
		return "jsonView";
	}
	
	//找回密码
	@RequestMapping(value="/android/findPw.json")
	public String findPassword(String nameOrmail, HttpServletRequest request, ModelMap mm) {
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = LStrMap.newInstance();
		
		try {
			if(userService.hashMatchCount(nameOrmail)) {
				map.put("msg", "无此用户");
				map.put("mail", null);
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
			} else {
				userService.sendResetPasswordMail(nameOrmail, "http://"+request.getHeader("Host")+request.getContextPath());
				map.put("msg", "发送成功");
				map.put("mail", userService.getUserByUserName(nameOrmail).getMailAddress());
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "无此用户");
			map.put("mail", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		list.add(map);
		mm.put("DATA", list);
		return "jsonView";
	}
	
}