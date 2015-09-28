package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class CTeacher {
	private int teacherId;
	private String teacherName;
	private int schoolId;
	private String birthDate;
	private String identityid;
	private int gender;
	private int teacherLevel;
	private String contactPhone;
	private String address;
	private int education;
	private int  marriage;
	private int poliLand;
	private String teach;
	private String license;
	private String remark;
	private long userId;
	private String teacherIntro;
	private int authenStatus;
	private String authenTime;
	
	public static CTeacher create(LStrMap<Object> map){
		CTeacher cTeacher = new CTeacher();
		cTeacher.setTeacherId(map.get("teacher_id")!=null?(Integer) map.get("teacher_id"):null);
		cTeacher.setTeacherName(map.get("teacher_name")!=null?(String) map.get("teacher_name"):null);
		cTeacher.setSchoolId(map.get("school_id")!=null?(Integer) map.get("school_id"):null);
		cTeacher.setBirthDate(map.get("birth_date")!=null?(String) map.get("birth_date"):null);
		cTeacher.setIdentityid(map.get("identityid")!=null?(String) map.get("identityid"):null);
		cTeacher.setGender(map.get("gender")!=null?(Integer) map.get("gender"):null);
		cTeacher.setTeacherLevel(map.get("teacher_level")!=null?(Integer) map.get("teacher_level"):null);
		cTeacher.setContactPhone(map.get("contact_phone")!=null?(String) map.get("contact_phone"):null);
		cTeacher.setAddress(map.get("address")!=null?(String) map.get("address"):null);
		cTeacher.setEducation(map.get("education")!=null?(Integer) map.get("education"):null);
		cTeacher.setMarriage(map.get("marriage")!=null?(Integer) map.get("marriage"):null);
		cTeacher.setPoliLand(map.get("poli_land")!=null?(Integer) map.get("poli_land"):null);
		cTeacher.setTeach(map.get("teach")!=null?(String) map.get("teach"):null);
		cTeacher.setLicense(map.get("license")!=null?(String) map.get("license"):null);
		cTeacher.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		cTeacher.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		cTeacher.setTeacherIntro(map.get("teacher_intro")!=null?(String) map.get("teacher_intro"):null);
		cTeacher.setAuthenStatus(map.get("authen_status")!=null?(Integer) map.get("authen_status"):null);
		cTeacher.setTeacherIntro(map.get("authen_time")!=null?(String) map.get("authen_time"):null);
		return cTeacher;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("teacher_id",teacherId);
		map.put("teacher_name",teacherName);
		map.put("school_id",schoolId);
		map.put("birth_date",birthDate);
		map.put("identityid",identityid);
		map.put("gender",gender);
		map.put("teacher_level",teacherLevel);
		map.put("contact_phone",contactPhone);
		map.put("address",address);
		map.put("education",education);
		map.put("marriage",marriage);
		map.put("poli_land",poliLand);
		map.put("teach",teach);
		map.put("license",license);
		map.put("remark",remark);
		map.put("user_id",userId);
		map.put("teacher_intro",teacherIntro);
		map.put("authen_status",authenStatus);
		map.put("authen_time",authenTime);
		return map;
	}
	
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getIdentityid() {
		return identityid;
	}
	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getTeacherLevel() {
		return teacherLevel;
	}
	public void setTeacherLevel(int teacherLevel) {
		this.teacherLevel = teacherLevel;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	public int getMarriage() {
		return marriage;
	}
	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}
	public int getPoliLand() {
		return poliLand;
	}
	public void setPoliLand(int poliLand) {
		this.poliLand = poliLand;
	}
	public String getTeach() {
		return teach;
	}
	public void setTeach(String teach) {
		this.teach = teach;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTeacherIntro() {
		return teacherIntro;
	}
	public void setTeacherIntro(String teacherIntro) {
		this.teacherIntro = teacherIntro;
	}
	public int getAuthenStatus() {
		return authenStatus;
	}
	public void setAuthenStatus(int authenStatus) {
		this.authenStatus = authenStatus;
	}

	public String getAuthenTime() {
		return authenTime;
	}

	public void setAuthenTime(String authenTime) {
		this.authenTime = authenTime;
	}

	
}
