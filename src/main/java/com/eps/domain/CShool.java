package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class CShool {

	private int schoolId;
	private String schoolName;
	private int orgNo;
	private String respDist;
	private int schoolLevel;
	private int schoolType;
	private String address;
	private String  contactPhone;
	private String contactor;
	private int schoolmaster;
	private String remark;
	
	public static CShool create(LStrMap<Object> map){
		CShool cShool = new CShool();
		cShool.setSchoolId(map.get("school_id")!=null?(Integer) map.get("school_id"):null);
		cShool.setSchoolName(map.get("school_name")!=null?(String) map.get("school_name"):null);
		cShool.setOrgNo(map.get("org_no")!=null?(Integer) map.get("org_no"):null);
		cShool.setRespDist(map.get("resp_dist")!=null?(String) map.get("resp_dist"):null);
		cShool.setSchoolLevel(map.get("school_level")!=null?(Integer) map.get("school_level"):null);
		cShool.setSchoolType(map.get("school_type")!=null?(Integer) map.get("school_type"):null);
		cShool.setAddress(map.get("address")!=null?(String) map.get("address"):null);
		cShool.setContactPhone(map.get("contact_phone")!=null?(String) map.get("contact_phone"):null);
		cShool.setSchoolmaster(map.get("schoolmaster")!=null?(Integer) map.get("schoolmaster"):null);
		cShool.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		return cShool;
		
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("school_id", schoolId);
		map.put("school_name", schoolName);
		map.put("org_no", orgNo);
		map.put("resp_dist", respDist);
		map.put("school_level", schoolLevel);
		map.put("school_type", schoolType);
		map.put("address", address);
		map.put("contact_phone",contactPhone);
		map.put("schoolmaster", schoolmaster);
		map.put("remark",remark);
		return map;
	}
	
	
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public int getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(int orgNo) {
		this.orgNo = orgNo;
	}
	public String getRespDist() {
		return respDist;
	}
	public void setRespDist(String respDist) {
		this.respDist = respDist;
	}
	public int getSchoolLevel() {
		return schoolLevel;
	}
	public void setSchoolLevel(int schoolLevel) {
		this.schoolLevel = schoolLevel;
	}
	public int getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public int getSchoolmaster() {
		return schoolmaster;
	}
	public void setSchoolmaster(int schoolmaster) {
		this.schoolmaster = schoolmaster;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
