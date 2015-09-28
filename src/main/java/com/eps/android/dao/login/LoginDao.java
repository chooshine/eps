package com.eps.android.dao.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class LoginDao extends BaseDao {
	@Value("${android.get.userinfo.by.username.password}")
	private String getUserInfoByUsernamePassword;
	@Value("${android.get.sortlistofstudent.by.userid}")
	private String getSortListOfStudentByUserId;
	
	public List<LStrMap<Object>> getUserInfo(String username, String password) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_no", username);
		map.put("password", password);
		
		return this.find(getUserInfoByUsernamePassword, map);
	}

	public List<LStrMap<Object>> getSortListOfStudent(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getSortListOfStudentByUserId, map);
	}
}