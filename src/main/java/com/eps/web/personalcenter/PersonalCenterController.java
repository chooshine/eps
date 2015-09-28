package com.eps.web.personalcenter;


import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.User;
import com.eps.service.esextends.TeacherAuthenService;
import com.eps.service.user.UserService;
import com.eps.utils.HttpHelper;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;

@Controller
public class PersonalCenterController extends BaseController{
	
	@Autowired
	private UserService us;
	private final static String PHONECODE = "phonecode";
	
	@Autowired
	TeacherAuthenService tas;
	
	//更新头像
	@RequestMapping(value="/updateHeadImage.html", method=RequestMethod.POST)
	public String updateHeadImage(String image, HttpServletRequest request) {
		//删除原有头像
		User user = getSessionUser(request);
		File file = new File(HttpHelper.getRequestRealPath(request, "/"+user.getPhoto()));
		file.delete();
		//更新头像
		user.setPhoto(image);
		us.updateUserInfo(user);
		return "redirect:/person/center.html";
	}
	
	@RequestMapping(value="/person/center.html")
	public String toIndex(ModelMap mm,HttpServletRequest request){
		mm.put("title", "个人中心");
		User user = getSessionUser(request);
		if(user.getIsEnterpriseUser() == User.ENTERPRISE){
			mm.put("error", "您正在使用的帐号为集团帐号，无法使用个人中心。");
			return "index";
		}
		List<LStrMap<Object>> list = tas.getAuthenInfo(user.getUserId());
		if (!list.isEmpty()) {
			int authen_status = (Integer)list.get(0).get("authen_status");
			mm.put("authen_status", authen_status);
		}
		return "personalCenter/index";
	}
	
	@RequestMapping(value="/person/center/bind-phone.html")
	public String bindPhone(ModelMap mm){
		mm.put("title", "个人中心");
		return "personalCenter/bindPhoneFirst";
	}
	
	@RequestMapping(value="/person/center/bind-phone-second.html")
	public String bindPhoneSecond(HttpServletRequest request, ModelMap mm,String phone){
		mm.put("title", "个人中心");
		mm.put("phone", phone);
		User user = getSessionUser(request);
		String code = us.sendPhoneCode(user, phone);
		request.getSession().setAttribute(PHONECODE, code);
		return "personalCenter/bindPhoneSecond";
	}
	
	@RequestMapping(value="/person/center/bind-phone-last.html")
	public String bindPhoneLast(HttpServletRequest request, ModelMap mm,String phone,String phoneCode){
		String code = (String)request.getSession().getAttribute(PHONECODE);
		mm.put("title", "个人中心");
		mm.put("phone", phone);
		if(phoneCode.equals(code)){
			User user = getSessionUser(request);
			user.setPhone(phone);
			us.updateUserDetailInfo(user);
			setSessionUser(request, user);
			return "personalCenter/bindPhoneLast";
		}else{
			mm.put(ERROR_MSG_KEY, "验证码错误，请填写正确的验证码或重新获取验证码");
			return "personalCenter/bindPhoneSecond";
		}
	}
	
	@RequestMapping(value="/person/center/bind-phone-check.json")
	public String checkPhone(ModelMap mm, String phone){
		mm.put("hasMatchCount", us.hasMatchPhoneCount(phone));
		return "jsonView";
	}
	
	@RequestMapping(value="/person/center/authen-email.html")
	public String authenEMail(HttpServletRequest request,ModelMap mm){
		mm.put("title", "个人中心");
		us.sendAuthenMailAddressMail(getSessionUser(request), HttpHelper.getRequestBasePath(request));
		//System.out.println(HttpHelper.getRequestBasePath(request));
		return "personalCenter/authenMailAddressFirst";
	}
	
	@RequestMapping(value="/person/center/authen-mail-last.html")
	public String authenmailend(ModelMap mm, String n, HttpServletRequest request){
		mm.put("title", "个人中心");
		if(us.linkHasEffective(n)){
			User user = us.getUserByLinkNo(n);
			us.updateAuthenMailStatus(user, User.AUTHENMAIL);
			user.setIsAuthenMail(User.AUTHENMAIL);
			setSessionUser(request, user);
		}
		return "personalCenter/authenMailAddressLast";
	}
	
	@RequestMapping(value="/person/center/updateMailAddress.html")
	public String updateMailAddress(ModelMap mm){
		mm.put("title","个人中心");
		return "personalCenter/updateMailAddressFirst";
	}
	
	@RequestMapping(value="/person/center/updateMailAddress-second.html")
	public String updateMailAddressSecond(ModelMap mm,HttpServletRequest request,String code){
		mm.put("title","个人中心");
		String pcode = (String)request.getSession().getAttribute(PHONECODE);
		if(pcode.equals(code)){
			return "personalCenter/updateMailAddressSecond";
		}else{
			mm.put(ERROR_MSG_KEY, "验证码错误，请填写正确的验证码或重新获取验证码");
			return "personalCenter/updateMailAddressFirst";
		}
		
	}
	
	@RequestMapping(value="/person/center/updateMailAddress-last.html")
	public String updateMailAddressLast(ModelMap mm,HttpServletRequest request,String code, String email){
		mm.put("title","个人中心");
		String pcode = (String)request.getSession().getAttribute(PHONECODE);
		String mail = (String)request.getSession().getAttribute("newEmalAddress");
		User user = getSessionUser(request);
		if(pcode.equals(code) && mail.equals(email)){
			user.setMailAddress(email);
			us.updateUserInfo(user);
			setSessionUser(request, user);
		}else{
			mm.put(ERROR_MSG_KEY, "验证码错误或者验证与邮箱不匹配，请填写正确的验证码或重新获取验证码");
			return "personalCenter/updateMailAddressSecond";
		}
		return "personalCenter/updateMailAddressLast";
	}
	
	@RequestMapping(value="/person/center/updateMailAddress-sendCode.json")
	public String updateMailAddressLast(ModelMap mm,HttpServletRequest request,String email){
		mm.put("title","个人中心");
		if(!us.hashMatchCount(email)){
			mm.put(ERROR_MSG_KEY, "该邮箱已被使用，请重新输入一个邮箱地址");
			return "jsonView";
		}else{
			String code = us.sendMailCode(getSessionUser(request), email);
			mm.put(SUCCESS_MSG_KEY, "发送成功");
			request.getSession().setAttribute(PHONECODE, code);
			request.getSession().setAttribute("newEmalAddress", email);
		}
		return "jsonView";
	}
	@RequestMapping(value="/person/center/sendCode.json")
	public String sendCode(ModelMap mm,HttpServletRequest request,String type){
		mm.put("title", "个人中心");
		User user = getSessionUser(request);
		String code = "";
		if("mail".equals(type)){ //通过邮箱验证
			code = us.sendMailCode(user, user.getMailAddress());
		}else if("phone".equals(type)){ //通过手机验证
			code = us.sendPhoneCode(user, user.getPhone());
		}else{
			mm.put(ERROR_MSG_KEY,"验证类型错误");
			return "jsonView";
		}
		mm.put(SUCCESS_MSG_KEY, "发送成功");
		request.getSession().setAttribute(PHONECODE, code);
		return "jsonView";
	}
}
