package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class NNetWork_Arrange extends BaseDomain{
	
	private int arrange_Id;//安排编号
	private String date;//考试时间
	private String teacher_Name;//监考人
	private int class_Id;//班级编号
	private int test_Id;//考试编号
	private int subject_Id;//科目编号
	private int grade_Id;//年级编号
	private String netWork_Addr;//考场名称
	private long user_Id;
	private String endDate;
	private int costTime;
	
	private int netWork_Status;//安排考试状态标识
	
	public static NNetWork_Arrange create(LStrMap<Object> map){
		NNetWork_Arrange nwa=new NNetWork_Arrange();
		nwa.setArrange_Id(map.get("arrange_Id")!=null?(Integer) map.get("arrange_Id"):null);
		nwa.setDate(map.get("date")!=null?(String) map.get("date"):null);
		nwa.setTeacher_Name(map.get("teacher_Name")!=null?(String) map.get("teacher_Name"):null);
		nwa.setGrade_Id(map.get("grade_Id")!=null?(Integer) map.get("grade_Id"):null);
		nwa.setClass_Id(map.get("class_Id")!=null?(Integer) map.get("class_Id"):null);
		nwa.setTest_Id(map.get("test_Id")!=null?(Integer) map.get("test_Id"):null);
		nwa.setNetWork_Addr(map.get("netWork_Addr")!=null?(String) map.get("netWork_Addr"):null);
		nwa.setNetWork_Status(map.get("netWork_Status")!=null?(Integer) map.get("netWork_Status"):null);
		nwa.setUser_Id(map.get("user_Id")!=null?(Integer) map.get("user_Id"):null);
		nwa.setEndDate(map.get("endDate")!=null?(String) map.get("endDate"):null);
		nwa.setCostTime(map.get("cost_time")!=null?(Integer) map.get("cost_time"):null);
		return nwa;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("arrange_Id",arrange_Id);
		map.put("date",date);
		map.put("teacher_Name",teacher_Name);
		map.put("class_Id",class_Id);
		map.put("test_Id",test_Id);
		map.put("subject_Id",subject_Id);
		map.put("grade_Id",grade_Id);
		map.put("netWork_Addr",netWork_Addr);
		map.put("netWork_Status",netWork_Status);
		map.put("user_Id",user_Id);
		map.put("endDate",endDate);
		map.put("cost_time",costTime);
		return map;
		}
	

	public int getArrange_Id() {
		return arrange_Id;
	}

	public void setArrange_Id(int arrange_Id) {
		this.arrange_Id = arrange_Id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTeacher_Name() {
		return teacher_Name;
	}

	public void setTeacher_Name(String teacher_Name) {
		this.teacher_Name = teacher_Name;
	}

	public int getClass_Id() {
		return class_Id;
	}

	public void setClass_Id(int class_Id) {
		this.class_Id = class_Id;
	}

	public int getTest_Id() {
		return test_Id;
	}

	public void setTest_Id(int test_Id) {
		this.test_Id = test_Id;
	}

	public int getSubject_Id() {
		return subject_Id;
	}

	public void setSubject_Id(int subject_Id) {
		this.subject_Id = subject_Id;
	}

	public int getGrade_Id() {
		return grade_Id;
	}

	public void setGrade_Id(int grade_Id) {
		this.grade_Id = grade_Id;
	}

	public String getNetWork_Addr() {
		return netWork_Addr;
	}

	public void setNetWork_Addr(String netWork_Addr) {
		this.netWork_Addr = netWork_Addr;
	}

	public int getNetWork_Status() {
		return netWork_Status;
	}

	public void setNetWork_Status(int netWork_Status) {
		this.netWork_Status = netWork_Status;
	}

	public long getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(long user_Id) {
		this.user_Id = user_Id;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

}
