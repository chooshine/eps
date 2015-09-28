package com.eps.domain;

import com.eps.utils.UStrMap;

public class ETestEexam {

	private int testId;
	private int examId;
	private int subjectId;
	private int his_flag;
	private int automarkFlag;
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_id",testId);
		map.put("exam_id",examId);
		map.put("subject_id",subjectId);
		map.put("his_flag",his_flag);
		map.put("automark_flag", automarkFlag);
		return map;
	}
	
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getHis_flag() {
		return his_flag;
	}

	public void setHis_flag(int his_flag) {
		this.his_flag = his_flag;
	}

	public int getAutomarkFlag() {
		return automarkFlag;
	}

	public void setAutomarkFlag(int automarkFlag) {
		this.automarkFlag = automarkFlag;
	}
	
	
	
}
