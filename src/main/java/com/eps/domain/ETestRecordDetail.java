package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class ETestRecordDetail {
	private int testRecId;
	private int quesId;
	private String answer;
	private float score = -1;
	private String enterTime;
	private String leftTime;
	private String answerTime;
	private String answerLog;
	private String remark;
	private String studentAnswer;
	
	public static ETestRecordDetail create(LStrMap<Object> map){
		ETestRecordDetail eTestRecordDetail = new ETestRecordDetail();
		eTestRecordDetail.setTestRecId(map.get("test_rec_id")!=null?(Integer) map.get("test_rec_id"):null);
		eTestRecordDetail.setQuesId(map.get("ques_id")!=null?(Integer) map.get("ques_id"):null);
		eTestRecordDetail.setAnswer(map.get("answer")!=null?(String) map.get("answer"):null);
		eTestRecordDetail.setEnterTime(map.get("enter_time")!=null?(String) map.get("enter_time"):null);
		eTestRecordDetail.setLeftTime(map.get("left_time")!=null?(String) map.get("left_time"):null);
		eTestRecordDetail.setAnswerTime(map.get("answer_time")!=null?(String) map.get("answer_time"):null);
		eTestRecordDetail.setAnswerLog(map.get("answer_log")!=null?(String) map.get("answer_log"):null);
		eTestRecordDetail.setScore(map.get("score")!=null?(Float) map.get("score"):null);
		eTestRecordDetail.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		return eTestRecordDetail;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id",testRecId);
		map.put("ques_id",quesId);
		map.put("answer",answer);
		map.put("score",score);
		map.put("enter_time",enterTime);
		map.put("left_time",leftTime);
		map.put("answer_time",answerTime);
		map.put("remark",remark);
		map.put("answer_log",answerLog);
		map.put("student_answer", studentAnswer);
		return map;
	}
	
	public int getTestRecId() {
		return testRecId;
	}
	public void setTestRecId(int testRecId) {
		this.testRecId = testRecId;
	}
	public int getQuesId() {
		return quesId;
	}
	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	public String getLeftTime() {
		return leftTime;
	}
	public void setLeftTime(String leftTime) {
		this.leftTime = leftTime;
	}
	
	public String getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
	public String getAnswerLog() {
		return answerLog;
	}
	public void setAnswerLog(String answerLog) {
		this.answerLog = answerLog;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

}
