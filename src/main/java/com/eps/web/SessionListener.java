package com.eps.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.eps.cons.CommonConstant;
import com.eps.domain.User;
import com.eps.web.user.MapStatStore;

public class SessionListener implements HttpSessionListener{
	public void sessionCreated(HttpSessionEvent event) {
		
	}
	public void sessionDestroyed(HttpSessionEvent event) {
		User user = (User) event.getSession().getAttribute(CommonConstant.USER_CONTEXT);
		if(user != null){
			//System.out.println(user.getUserNo()+"退出");
			MapStatStore.logoff(user, event.getSession());
			//System.out.println("当前用户数量："+ MapStatStore.getUserCount());
		}
	}
}
