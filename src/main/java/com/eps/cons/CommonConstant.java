package com.eps.cons;

public class CommonConstant {
	/**
	    * 用户对象放到Session中的键名称
	    */
	   public static final String USER_CONTEXT = "USER_CONTEXT";
	   
	   /**
	    * 将登录前的URL放到Session中的键名称
	    */
	   public static final String LOGIN_TO_URL = "toUrl";
	   
	   /**
	    * 每页的记录数
	    */
	   public static final int PAGE_SIZE = 50;  
	   
	   public static final String YYYYMMDDHHMISS = "yyyy-MM-dd HH:mm:ss";
	   
	   public static final String[] LESSON_TIME = {"08:00-08:45","08:55-09:40","10:00-10:45","10:55-11:40","14:00-14:45","14:55-15:40","16:00-16:45","16:55-17:40","19:00-19:45","19:55-20:40"};
	   
	   
	   public static final String KEYCHAR = "@#@";
	   
	   /**
	    * 解析网页连接超时时间(毫秒)
	    */
	   public static final int CONNECT_TIMEOUT = 60000;
	   
	   /**
	    * 解析网页读取超时时间(毫秒)
	    */
	   public static final int READ_TIMEOUT = 600000;
	   
	   public static final String KEYWORD = "_SESSION_KEYWORD";
}
