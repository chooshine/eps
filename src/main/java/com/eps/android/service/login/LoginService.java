package com.eps.android.service.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.android.dao.login.LoginDao;
import com.eps.dao.homework.TeacherHomeworkDao;
import com.eps.utils.LStrMap;

@Service
public class LoginService {
	@Autowired private LoginDao loginDao;
	
	public boolean existUser(String username, String password) {
		if (loginDao.getUserInfo(username, password).size() > 0) {
			return true;
		}
		return false;
	}
	
	public List<LStrMap<Object>> getSortListOfStudent(long userId) {
		return loginDao.getSortListOfStudent(userId);
	}

}
