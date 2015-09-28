package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class ETestRecord {
	private int testRecId;//考试记录编号
	private int testId;//考试编号
	private long userId;//参考者编号
	private int studentId;//学生ID
	private int examId;//试卷编号
	private int examStatus;//考试状态
	private int commitFlag;//是否交卷
	private String examTime;//考试时间
	private float score;//得分
	private String remark;//备注
	private String examUseTime;//答题时间
	private int subjectId;//科目
	private int classId;
	private int creatorId;//此次考试的创建人
	private int finishedNum; //已经做的小题数

	public static ETestRecord create(LStrMap<Object> map){
		ETestRecord eTestRecord = new ETestRecord();
		eTestRecord.setTestRecId(map.get("test_rec_id")!=null?(Integer) map.get("test_rec_id"):null);
		eTestRecord.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		eTestRecord.setExamId(map.get("exam_id")!=null?(Integer) map.get("exam_id"):null);
		eTestRecord.setExamId(map.get("student_id")!=null?(Integer) map.get("student_id"):null);
		eTestRecord.setTestId(map.get("test_id")!=null?(Integer) map.get("test_id"):null);
		eTestRecord.setExamStatus(map.get("exam_status")!=null?(Integer) map.get("exam_status"):null);
		eTestRecord.setCommitFlag(map.get("commit_flag")!=null?(Integer) map.get("commit_flag"):null);
		eTestRecord.setExamTime(map.get("exam_time")!=null?(String) map.get("exam_time"):null);
		eTestRecord.setScore(map.get("score")!=null?(Float) map.get("score"):null);
		eTestRecord.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		eTestRecord.setExamUseTime(map.get("exam_use_time")!=null?(String) map.get("exam_use_time"):null);
		eTestRecord.setSubjectId(map.get("subject_Id")!=null?(Integer) map.get("subject_Id"):null);
		eTestRecord.setSubjectId(map.get("class_Id")!=null?(Integer) map.get("class_Id"):null);
		eTestRecord.setCreatorId(map.get("creator_id")!=null?(Integer) map.get("creator_id"):null);
		eTestRecord.setFinishedNum(map.get("finished_num")!=null?(Integer) map.get("finished_num"):null);
		return eTestRecord;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id",testRecId);
		map.put("user_id",userId);
		map.put("exam_id",examId);
		map.put("student_id",studentId);
		map.put("test_id",testId);
		map.put("exam_status",examStatus);
		map.put("commit_flag",commitFlag);
		map.put("exam_time",examTime);
		map.put("score",score);
		map.put("remark",remark);
		map.put("exam_use_time",examUseTime);
		map.put("subject_Id",subjectId);
		map.put("class_Id",classId);
		map.put("creator_id",creatorId);
		map.put("finished_num",finishedNum);
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
	
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getExamStatus() {
		return examStatus;
	}
	public void setExamStatus(int examStatus) {
		this.examStatus = examStatus;
	}
	public int getCommitFlag() {
		return commitFlag;
	}
	public void setCommitFlag(int commitFlag) {
		this.commitFlag = commitFlag;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExamUseTime() {
		return examUseTime;
	}

	public void setExamUseTime(String examUseTime) {
		this.examUseTime = examUseTime;
	}

	public int getTestRecId() {
		return testRecId;
	}

	public void setTestRecId(int testRecId) {
		this.testRecId = testRecId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(int finishedNum) {
		this.finishedNum = finishedNum;
	}
	
}
