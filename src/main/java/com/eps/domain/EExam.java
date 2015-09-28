package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EExam {
	private int examId = -1;
	private String examName;
	private int year;
	private int semester;
	private String subjectNo;
	private int examType;
	private int bTopicNum;
	private int mTopicNum;
	private int total;
	private String careaor;
	private int costTime;
	private String remark;
	private int qRandom;
	private int oRandom;
	private int bCodeType;
	private int sCodeType;
	private int oCodeType;
	private String examArea;
	private float passScore;
	private int releaseStatus;
	private String release_time;
	private int testId;//考试ID
	private int e_status;
	private int gradeLevel;
	
	public static EExam create(LStrMap<Object> map){
		EExam eExam = new EExam();
		eExam.setExamId(map.get("exam_id")!=null?(Integer) map.get("exam_id"):null);
		eExam.setExamName(map.get("exam_name")!=null?(String) map.get("exam_name"):null);
		eExam.setYear(map.get("year")!=null?(Integer) map.get("year"):null);
		eExam.setSemester(map.get("semester")!=null?(Integer) map.get("semester"):null);
		eExam.setSubjectNo(map.get("subject_no")!=null?(String) map.get("subject_no"):null);
		eExam.setExamType(map.get("exam_type")!=null?(Integer) map.get("exam_type"):null);
		eExam.setbTopicNum(map.get("b_topic_num")!=null?(Integer) map.get("b_topic_num"):null);
		eExam.setmTopicNum(map.get("m_topic_num")!=null?(Integer) map.get("m_topic_num"):null);
		eExam.setTotal(map.get("total")!=null?(Integer) map.get("total"):null);
		eExam.setCareaor(map.get("careaor")!=null?(String) map.get("careaor"):null);
		eExam.setCostTime(map.get("cost_time")!=null?(Integer) map.get("cost_time"):null);
		eExam.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		eExam.setqRandom(map.get("q_random")!=null?(Integer) map.get("q_random"):null);
		eExam.setoRandom(map.get("o_random")!=null?(Integer) map.get("o_random"):null);
		eExam.setbCodeType(map.get("b_code_type")!=null?(Integer) map.get("b_code_type"):null);
		eExam.setsCodeType(map.get("s_code_type")!=null?(Integer) map.get("s_code_type"):null);
		eExam.setoCodeType(map.get("o_code_type")!=null?(Integer) map.get("o_code_type"):null);
		eExam.setExamArea(map.get("exam_area")!=null?(String) map.get("exam_area"):null);
		eExam.setPassScore(map.get("pass_score")!=null?(Float) map.get("pass_score"):null);
		eExam.setReleaseStatus(map.get("release_status")!=null?(Integer) map.get("release_status"):null);
		eExam.setTestId(map.get("test_id")!=null?(Integer) map.get("test_id"):null);
		eExam.setRelease_time(map.get("release_time")!=null?(String) map.get("release_time"):null);
		eExam.setE_status(map.get("e_status")!=null?(Integer) map.get("e_status"):null);
		eExam.setE_status(map.get("grade_level")!=null?(Integer) map.get("grade_level"):null);
		return eExam;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id",examId);
		map.put("exam_name",examName);
		map.put("year",year);
		map.put("semester",semester);
		map.put("subject_no",subjectNo);
		map.put("exam_type",examType);
		map.put("b_topic_num",bTopicNum);
		map.put("m_topic_num",mTopicNum);
		map.put("total",total);
		map.put("careaor",careaor);
		map.put("cost_time",costTime);
		map.put("remark",remark);
		map.put("q_random",qRandom);
		map.put("o_random",oRandom);
		map.put("b_code_type",bCodeType);
		map.put("s_code_type",sCodeType);
		map.put("o_code_type",oCodeType);
		map.put("exam_area",examArea);
		map.put("pass_score",passScore);
		map.put("release_status",releaseStatus);
		map.put("test_Id",testId);
		map.put("release_time",release_time);
		map.put("e_status",e_status);
		map.put("grade_level",gradeLevel);
		return map;
	}
	
	
	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	public int getExamType() {
		return examType;
	}
	public void setExamType(int examType) {
		this.examType = examType;
	}
	public int getbTopicNum() {
		return bTopicNum;
	}
	public void setbTopicNum(int bTopicNum) {
		this.bTopicNum = bTopicNum;
	}
	public int getmTopicNum() {
		return mTopicNum;
	}
	public void setmTopicNum(int mTopicNum) {
		this.mTopicNum = mTopicNum;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getCareaor() {
		return careaor;
	}
	public void setCareaor(String careaor) {
		this.careaor = careaor;
	}
	public int getCostTime() {
		return costTime;
	}
	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getqRandom() {
		return qRandom;
	}
	public void setqRandom(int qRandom) {
		this.qRandom = qRandom;
	}
	public int getoRandom() {
		return oRandom;
	}
	public void setoRandom(int oRandom) {
		this.oRandom = oRandom;
	}
	public int getbCodeType() {
		return bCodeType;
	}
	public void setbCodeType(int bCodeType) {
		this.bCodeType = bCodeType;
	}
	public int getsCodeType() {
		return sCodeType;
	}
	public void setsCodeType(int sCodeType) {
		this.sCodeType = sCodeType;
	}
	public int getoCodeType() {
		return oCodeType;
	}
	public void setoCodeType(int oCodeType) {
		this.oCodeType = oCodeType;
	}
	public String getExamArea() {
		return examArea;
	}
	public void setExamArea(String examArea) {
		this.examArea = examArea;
	}
	public float getPassScore() {
		return passScore;
	}
	public void setPassScore(float passScore) {
		this.passScore = passScore;
	}

	public int getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(int releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	

	public String getRelease_time() {
		return release_time;
	}

	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}

	public int getE_status() {
		return e_status;
	}

	public void setE_status(int e_status) {
		this.e_status = e_status;
	}

	public int getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
	
	
	
}
