package com.eps.domain;

import java.util.Date;

public class CClass extends BaseDomain{
	private int classId;
	
	private String className;
	
	private int gradeId;
	
	private int classType;
	
	private int teacherId;
	
	private Date startTime;
	
	private Date endTime;
	
	private int existFlag;

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public int getClassType() {
		return classType;
	}

	public void setClassType(int classType) {
		this.classType = classType;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getExistFlag() {
		return existFlag;
	}

	public void setExistFlag(int existFlag) {
		this.existFlag = existFlag;
	}
	
	
}
