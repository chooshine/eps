package com.eps.dao.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.UStrMap;

@Repository
public class LoginLogDao extends BaseDao{
	
	@Value("${login.log.insert}")
	private String saveLog;

	public void saveLoginLog(long userId, String ip, Date loginTime){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("ip", ip);
		map.put("login_datetime", loginTime);
		this.excute(saveLog, map);
	}
}
