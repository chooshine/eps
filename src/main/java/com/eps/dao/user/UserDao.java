package com.eps.dao.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.User;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class UserDao extends BaseDao{
	
	@Value("${user.load.id}")
	private String loadSql;
	
	@Value("${user.get.userNo}")
	private String getUserByNo;
	
	@Value("${user.login.matchcount}")
	private String getMatchCount;
	
	@Value("${user.update.logininfo}")
	private String updateLoginInfo;
	
	@Value("${user.check.usernoandmail}")
	private String checkUserNo;
	
	@Value("${user.create}")
	private String createUser;
	
	@Value("${user.save.detail}")
	private String saveUserDetail;
	
	@Value("${user.init.account}")
	private String initAccount;
	
	@Value("${user.update.info}")
	private String updateUser;
	
	@Value("${user.update.detail}")
	private String updateUserDetail;
	
	@Value("${user.update.account}")
	private String updateAccount;
	
	@Value("${user.update.password}")
	private String updatePassword;
	
	@Value("${user.detail.checkphone}")
	private String checkPhone;
	
	@Value("${user.update.isauthenmail}")
	private String updateisauthenmail;
	
	@Value("${user.get.phone}")
	private String getUserByPhone;
	/**
	 * 加载用户
	 * @param id
	 * @return User {@link User}}
	 */
	public User load(long id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", id);
		LStrMap<Object> value = this.readRecode(loadSql, map);
		return User.create(value);
	}
	
	/**
	 * 根据用户名和密码查找用户数量，登陆用
	 * @param userNo
	 * @param password
	 * @return int 
	 */
	public int getMatchCount(String userNo,String password){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_no", userNo);
		map.put("password", password);
		return getNameParameTemplate().queryForInt(getMatchCount, map);
	}
	/**
	 * 验证用户名或邮箱是否存在，注册用
	 * @param userNo
	 * @return
	 */
	public int getMatchCount(String userNo){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_no", userNo);
		//map.put("password", password);
		return getNameParameTemplate().queryForInt(checkUserNo, map);
	}
	/**
	 * 根据登陆用户名查找用户
	 * @param userNo
	 * @return
	 */
	public User getUserByUserNo(String userNo){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_no", userNo);
		LStrMap<Object> value = this.readRecode(getUserByNo, map);
		return User.create(value);
	}
	
	/**
	 * 更新用户登陆信息(更新最后登陆时间及登陆IP)
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(Date lastVisit,String ip,long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("last_visit", lastVisit);
		map.put("last_ip", ip);
		map.put("user_id", userId);
		return this.excute(updateLoginInfo, map);
	}
	/**
	 * 保存用户基本信息
	 * @return 新生成用户Id
	 */
	public int saveUser(User user){
		UStrMap<Object> map = user.toMap();
		return excute(createUser, map);
	}
	/**
	 * 保存用户详细信息
	 * @return
	 */
	public void saveUserDetail(User user){
		UStrMap<Object> map = user.toMap();
		excute(saveUserDetail, map);
	}
	/**
	 * 生成用户金额帐户
	 * @return
	 */
	public void initUserAccount(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		excute(initAccount, map);
	}
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public void updateUser(User user){
		UStrMap<Object> map = user.toMap();
		excute(updateUser, map);
	}
	/**
	 * 更新用户详细信息
	 */
	public void updateUserDetail(User user){
		UStrMap<Object> map = user.toMap();
		excute(updateUserDetail, map);
	}
	/**
	 * 更新帐户余额
	 * @param userId
	 * @param cash
	 */
	public void updateUserAccount(long userId,double cash){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("cash", cash);
		excute(updateAccount, map);
	}
	/**
	 * 重置密码
	 * @param userId 用户Id
	 * @param newPassword 新密码
	 */
	public void updatePassword(int userId,String newPassword){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("password", newPassword);
		excute(updatePassword, map);
	}
	
	public int checkPhone(String phone){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("phone", phone);
		LStrMap<Object> result = readRecode(checkPhone, map);
		return Integer.parseInt(String.valueOf(result.get("count")));
	}
	public int updateIsAuthenMailAddress(long userId, int authenMailtype){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("ISAUTHENMAIL", authenMailtype);
		return excute(updateisauthenmail, map);
		
	}
	public User getUserByPhone(String phone){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("phone", phone);
		List<LStrMap<Object>> result = find(getUserByPhone, map);
		return result.size()==1?User.create(result.get(0)):null;
	}
}
