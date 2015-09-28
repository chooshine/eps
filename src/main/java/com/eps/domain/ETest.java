package com.eps.domain;

import java.util.Date;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class ETest {
	
	private int testId;
	private String testName;
	private String year;
	private int semester;
	private int schoolId;
	private int gradeId;
	private int testType;
	private String remark;
	private String testTime;
	private String testEndTime;
	
	public static ETest create(LStrMap<Object> map){
		ETest eTest=new ETest();
		eTest.setTestId(map.get("test_id")!=null?(Integer) map.get("test_id"):null);
		eTest.setTestName(map.get("test_name")!=null?(String) map.get("test_name"):null);
		eTest.setYear(map.get("year")!=null?(String) map.get("year"):null);
		eTest.setSemester(map.get("semester")!=null?(Integer) map.get("semester"):null);
		eTest.setSchoolId(map.get("school_id")!=null?(Integer) map.get("school_id"):null);
		eTest.setGradeId(map.get("grade_id")!=null?(Integer) map.get("grade_id"):null);
		eTest.setTestType(map.get("test_type")!=null?(Integer) map.get("test_type"):null);
		eTest.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		eTest.setTestTime(map.get("test_time")!=null?(String) map.get("test_time"):null);
		eTest.setTestTime(map.get("test_endTime")!=null?(String) map.get("test_endTime"):null);
		return eTest;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_id",testId);
		map.put("test_name",testName);
		map.put("year",year);
		map.put("semester",semester);
		map.put("school_id",schoolId);
		map.put("grade_id",gradeId);
		map.put("test_type",testType);
		map.put("remark",remark);
		map.put("test_time",testTime);
		map.put("test_endTime",testEndTime);
		return map;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public int getTestType() {
		return testType;
	}

	public void setTestType(int testType) {
		this.testType = testType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestEndTime() {
		return testEndTime;
	}

	public void setTestEndTime(String testEndTime) {
		this.testEndTime = testEndTime;
	}





	


	
	
	
	
}
