package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EHomeworkRecord {
	private int hwRecId;
	private long userId;
	private int subjectId;
	private int hwId;
	private int finishedNum;
	private int correctFlag;
	private int commitFlag;
	private String commitTime;
	private double score;
	private int studentId;
	private int classId;
	private int star;
	private String remark;
	
	public static EHomeworkRecord create(LStrMap<Object> map) {
		EHomeworkRecord eHomeworkRecord = new EHomeworkRecord();
		eHomeworkRecord.setHwRecId(map.get("hw_rec_id")!=null?(Integer)map.get("hw_rec_id"):null);
		eHomeworkRecord.setUserId(map.get("user_id")!=null?(Long)map.get("user_id"):null);
		eHomeworkRecord.setSubjectId(map.get("subject_id")!=null?(Integer)map.get("subject_id"):null);
		eHomeworkRecord.setHwId(map.get("hw_id")!=null?(Integer)map.get("hw_id"):null);
		eHomeworkRecord.setHwId(map.get("finished_num")!=null?(Integer)map.get("finished_num"):null);
		eHomeworkRecord.setHwId(map.get("correct_flag")!=null?(Integer)map.get("correct_flag"):null);
		eHomeworkRecord.setCommitFlag(map.get("commit_flag")!=null?(Integer)map.get("commit_flag"):null);
		eHomeworkRecord.setCommitTime(map.get("commit_time")!=null?(String)map.get("commit_time"):null);
		eHomeworkRecord.setScore(map.get("score")!=null?(Double)map.get("score"):null);
		eHomeworkRecord.setStudentId(map.get("student_id")!=null?(Integer)map.get("student_id"):null);
		eHomeworkRecord.setClassId(map.get("class_id")!=null?(Integer)map.get("class_id"):null);
		eHomeworkRecord.setClassId(map.get("start")!=null?(Integer)map.get("star"):null);
		eHomeworkRecord.setRemark(map.get("remark")!=null?(String)map.get("remark"):null);
		return eHomeworkRecord;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id",hwRecId);
		map.put("user_id",userId);
		map.put("subject_id",subjectId);
		map.put("hw_id",hwId);
		map.put("finished_num",finishedNum);
		map.put("correct_flag",correctFlag);
		map.put("commit_flag",commitFlag);
		map.put("commit_time",commitTime);
		map.put("score",score);
		map.put("student_id",studentId);
		map.put("class_id",classId);
		map.put("star",star);
		map.put("remark",remark);
		return map;
	}

	public int getHwRecId() {
		return hwRecId;
	}

	public void setHwRecId(int hwRecId) {
		this.hwRecId = hwRecId;
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

	public int getHwId() {
		return hwId;
	}

	public void setHwId(int hwId) {
		this.hwId = hwId;
	}

	public int getCommitFlag() {
		return commitFlag;
	}

	public void setCommitFlag(int commitFlag) {
		this.commitFlag = commitFlag;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(int finishedNum) {
		this.finishedNum = finishedNum;
	}

	public int getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(int correctFlag) {
		this.correctFlag = correctFlag;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}
}
