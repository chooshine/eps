package com.eps.web.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.User;
import com.eps.service.user.UserService;
import com.eps.web.BaseController;

@Controller
public class ResetPasswordController extends BaseController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/resetPassword/sendmail.html", method=RequestMethod.GET)
	public String toResetPassword(ModelMap mm){
		mm.put("title", "重置密码");
		return "resetPasswordSendMail";
	}
	
	@RequestMapping(value="/resetPassword/sendmail.html", method=RequestMethod.POST)
	public String sendResetPasswordMail(HttpServletRequest request,String nameOrEmail,ModelMap mm){
		service.sendResetPasswordMail(nameOrEmail, "http://"+request.getHeader("Host")+request.getContextPath());
		return "redirect:/resetPassword/sendmail/success.html";
	}
	
	@RequestMapping(value="/resetPassword/sendmail/success.html")
	public String sendMailSuccess(ModelMap mm){
		mm.put("title", "重置密码");
		return "resetPasswordSendMailSuccess";
	}
	
	@RequestMapping(value="/user/resetPassword.html",method=RequestMethod.GET)
	public String resetPassword(String n,ModelMap mm){
		mm.put("title", "重置密码");
		if(service.linkHasEffective(n)){
			service.updateLink(n);
			User user = service.getUserByLinkNo(n);
			mm.put("userId", user.getUserId());
			return "resetPassword";
		}else{
			mm.put(ERROR_MSG_KEY, "重置密码链接已失效!");
		}
		return "resetPassword";
	}
	
	@RequestMapping(value="/user/resetPassword.html",method=RequestMethod.POST)
	public String resetPassword(String userId,String password,ModelMap mm){
		service.resetPassword(password, Integer.parseInt(userId));
		return "redirect:/user/resetPassword/success.html";
	}
	@RequestMapping(value="/user/resetPassword/success.html")
	public String resetSuccess(ModelMap mm){
		mm.put("title", "重置密码");
		return "resetPasswordSuccess";
	}
}
