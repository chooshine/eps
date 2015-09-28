package com.eps.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eps.utils.HttpHelper;

public class CookieUtils {
	
	public static Cookie getCookie(HttpServletRequest request,String name){
		return HttpHelper.getCookie(request, name);
	}
	public static String getCookieValue(HttpServletRequest request,String name){
		return HttpHelper.getCookieValue(request, name);
	}
	public static void deleteCookie(HttpServletResponse response, Cookie cookie){
		if (cookie != null) {
			//cookie.setPath(getPath(request));
		    cookie.setValue("");
		    cookie.setMaxAge(0);
		    HttpHelper.addCookie(response, cookie);
		 }
	}
	
	public static void deleteCookie(HttpServletRequest request,HttpServletResponse response, String name){
		deleteCookie(response,getCookie(request,name));
	}
	/**
	 * 保存Cookie 默认保存30天
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response, String name,String value){
		addCookie(response, name, value, 0x278d00);
	}
	
	/**
	 * 保存Cookie
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge 时间(秒)
	 */
	public static void addCookie(HttpServletResponse response, String name,String value,int maxAge){
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie(name, value == null ? "" : value);
	    cookie.setMaxAge(maxAge);
	    HttpHelper.addCookie(response, cookie);
	}
}
