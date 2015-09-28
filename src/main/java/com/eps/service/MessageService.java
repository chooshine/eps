package com.eps.service;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MessageService implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		//this.appliectionContext = applicationContext;
		this.applicationContext = applicationContext;
	}
	
	public static String getMessage(String messageKey,Object[] params ,Locale locale){
		return applicationContext.getMessage(messageKey, params, locale);
	}
	
	public static String getMessage(String messageKey){
		return getMessage(messageKey,null,Locale.getDefault());
	}
	
	public static String getMessage(String messageKey,Object[] params){
		return getMessage(messageKey,params,Locale.getDefault());
	}
	public static String getMessage(String messageKey,Locale locale){
		return getMessage(messageKey,null,locale);
	}
	
	public static <T> T getBean(Class<T> T){
		return applicationContext.getBean(T);
				
	}
}
