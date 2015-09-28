package com.eps.android.dao.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class AUserDao extends BaseDao {

	@Value("${android.get.userinfo.by.userid}")
	private String getUserInfoByUserId;
	

	public List<LStrMap<Object>> getUserInfo(int userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getUserInfoByUserId, map);
	}
}
