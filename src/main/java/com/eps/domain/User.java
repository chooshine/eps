package com.eps.domain;

import java.util.Date;


import com.eps.utils.DateHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.StringHelper;
import com.eps.utils.UStrMap;

@SuppressWarnings("serial")
public class User extends BaseDomain{
	//普通用户
	public final static int NORMALUSER = 0;
	//管理员
	public final static int ADMINUSER = 1;
	public final static int TEACHERUSER = 10;
	public final static int STUDENTUSER = 20;
	
	public final static int ENTERPRISE = 1; //企业用户
	public final static int UNENTERPRISE = 0; //个人用户
	
	public final static int AUTHENMAIL = 1; //已认证邮箱
	public final static int UNAUTHENMAIL = 0;//未认证邮箱
	
	//锁定状态
	public final static int LOCKED = 1;
	//未锁定状态
	public final static int UNLOCK = 0;
	
	private long userId;
	private String userNo;
	private String userName;
	private String password;
	//邮箱地址
	private String mailAddress;
	//头像
	private String photo;
	//用户默认为普通用户
	private int userType = NORMALUSER; 
	//用户普通认不锁定
	private int locked = UNLOCK; 
	//最后登陆时间
	private Date lastVisit;
	//最后登陆IP
	private String ip;
	//积分
	private int credit;
	//注册时间
	private Date registDate;
	//是否企业用户
	private int isEnterpriseUser = UNENTERPRISE; // 默认为个人用户
	//允许同时访问的session数量
	private int sessionNum = 1; //默认为单点
	//允许访问的IP地址
	private String ipLimit = "%"; //默认不限制IP
	//是否认证邮箱
	private int isAuthenMail = UNAUTHENMAIL; //是否认证邮箱
	//真实姓名
	private String realName;
	//生日
	private String birthDate;
	//身份证号
	private String identityId;
	//性别
	private int gender; 
	//电话号码
	private String phone;
	//地址
	private String address;
	//学历
	private int education;
	//职业
	private String career;
	//帐户余额
	private double cash;
	//可用消费券余额
	private double couponCash;
	
	public static User create(LStrMap<Object> map){
		User user = new User();
		user.setUserId(map.get("user_id")!=null?(Integer) map.get("user_id"):null);
		user.setUserNo(map.get("user_No") != null ? (String)map.get("user_No"):null);
		user.setUserName(map.get("user_name")!=null? (String)map.get("user_name"):null);
		user.setPassword(map.get("password")!=null?(String)map.get("password"):null);
		user.setMailAddress(map.get("mail_address")!=null ?(String)map.get("mail_address"):null);
		user.setPhoto(map.get("photo")!=null?(String)map.get("photo"):null);
		user.setUserType(map.get("user_type")!=null ? (Integer)map.get("user_type"):null);
		user.setLocked(map.get("locked")!=null?(Integer)map.get("locked"):null);
		user.setLastVisit(map.get("last_visit")!=null?(Date)map.get("last_visit"):null);
		user.setIp(map.get("last_ip")!=null?(String)map.get("last_ip"):null);
		user.setCredit(map.get("credit")!=null?(Integer)map.get("credit"):null);
		user.setRealName(map.get("real_name")!=null?(String)map.get("real_name"):null);
		user.setBirthDate(map.get("birth_date")!=null?(String)map.get("birth_date"):null);
		user.setIdentityId(map.get("identityId")!=null?(String)map.get("identityId"):null);
		user.setGender(map.get("gender")!=null?(Integer)map.get("gender"):null);
		user.setPhone(map.get("phone")!=null?(String)map.get("phone"):null);
		user.setAddress(map.get("address")!=null?(String)map.get("address"):null);
		user.setEducation(map.get("education")!=null?(Integer)map.get("education"):null);
		user.setCareer(map.get("career")!=null?(String)map.get("career"):null);
		user.setCash(map.get("cash")!=null?Double.parseDouble(map.get("cash").toString()):null);
		user.setRegistDate(map.get("regist_time")!=null?(Date)map.get(""):null);
		user.setIsEnterpriseUser(map.get("is_enterprise_user")!=null?(Integer)map.get("is_enterprise_user"):null);
		user.setSessionNum(map.get("session_num")!=null?(Integer)map.get("session_num"):null);
		user.setIpLimit(map.get("ip_limit")!=null?(String)map.get("ip_limit"):null);
		user.setIsAuthenMail(map.get("isAuthenMail")!=null?(Integer)map.get("isAuthenMail"):null);
		return user;
	}
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("user_No", userNo);
		map.put("user_name", userName);
		map.put("password", password);
		map.put("mail_address", mailAddress);
		map.put("photo", photo);
		map.put("user_type", userType);
		map.put("locked", locked);
		map.put("last_visit", lastVisit);
		map.put("last_ip", ip);
		map.put("credit", credit);
		map.put("real_name", realName);
		map.put("birth_date", birthDate);
		map.put("identityId", identityId);
		map.put("gender", gender);
		map.put("phone", phone);
		map.put("address", address);
		map.put("education", education);
		map.put("career", career);
		map.put("cash", cash);
		map.put("regist_time", registDate);
		map.put("is_enterprise_user", isEnterpriseUser);
		map.put("session_num", sessionNum);
		map.put("ip_limit", ipLimit);
		map.put("isauthenmail", isAuthenMail);
		return map;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public Date getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getEducation() {
		return education;
	}
	public void setEducation(int education) {
		this.education = education;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public double getCouponCash() {
		return couponCash;
	}
	public void setCouponCash(double couponCash) {
		this.couponCash = couponCash;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public int getIsEnterpriseUser() {
		return isEnterpriseUser;
	}
	public void setIsEnterpriseUser(int isEnterpriseUser) {
		this.isEnterpriseUser = isEnterpriseUser;
	}
	public int getSessionNum() {
		return sessionNum;
	}
	public void setSessionNum(int sessionNum) {
		this.sessionNum = sessionNum;
	}
	public String getIpLimit() {
		return ipLimit;
	}
	public void setIpLimit(String ipLimit) {
		this.ipLimit = ipLimit;
	}
	
	public int getIsAuthenMail() {
		return isAuthenMail;
	}
	public void setIsAuthenMail(int isAuthenMail) {
		this.isAuthenMail = isAuthenMail;
	}
	/**
	 * 验证用户登陆IP是否在允许访问IP地址内
	 * @return
	 */
	public boolean hasInIpLimit(){
		if(ipLimit.equals("%"))return true;
		long curIp = StringHelper.toNumeric(ip);
		String[] ips = ipLimit.split("&");
		for(String item:ips){
			if(item.indexOf("~")!=-1){
				String startIp = item.split("~")[0];
				String endIp = item.split("~")[1];
				if(curIp >= StringHelper.toNumeric(startIp) && curIp <= StringHelper.toNumeric(endIp)){
					return true;
				}
				continue;
			}else{
				if(curIp == StringHelper.toNumeric(item)){
					return true;
				}
				continue;
			}
		}
		return false;
	}
}
