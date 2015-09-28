package com.eps.web.user;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.eps.domain.User;
import com.eps.utils.LStrMap;

public class MapStatStore {
	public static LStrMap<Set<HttpSession>> USERSESSIONS = null;
	
	static {
		USERSESSIONS = LStrMap.newInstance();
	}
	
	public static void  login(User user, HttpSession session){
		Set<HttpSession> sessions;
		if(USERSESSIONS.containsKey(user.getUserId())){
			sessions = USERSESSIONS.get(user.getUserId());
			if(!sessions.contains(session))
				sessions.add(session);
		}else{
			sessions = new HashSet<HttpSession>();
			sessions.add(session);
		}
		USERSESSIONS.put(String.valueOf(user.getUserId()), sessions);
	}
	
	public static void logoff(User user,HttpSession session){
		Set<HttpSession> sessions;
		if(USERSESSIONS.containsKey(user.getUserId())){
			sessions = USERSESSIONS.get(user.getUserId());
			sessions.remove(session);
			session.invalidate();
			USERSESSIONS.put(String.valueOf(user.getUserId()), sessions);
		}
	}
	
//	public static void removeUser(User user){
//		if(USERSESSIONS.containsKey(user.getUserId())){
//			USERSESSIONS.remove(user.getUserId());
//			
//		}
//	}
	
	public static int getUserSessionCount(User user){
		Set<HttpSession> sessions;
		if(USERSESSIONS.containsKey(user.getUserId())){
			sessions = USERSESSIONS.get(user.getUserId());
			return sessions.size();
		}
		return 0;
	}
	public static int getUserCount(){
		return USERSESSIONS.size();
	}
}
