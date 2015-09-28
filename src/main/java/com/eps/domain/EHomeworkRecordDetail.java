package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EHomeworkRecordDetail {
	private int hwRecId;
	private int quesId;
	private String answer;
	private String studentAnswer;
	private double score = -1;
	private String oScore;
	private String teacherComment;
	private String answerTime;
	private String remark;
	
	public static EHomeworkRecordDetail create(LStrMap<Object> map) {
		EHomeworkRecordDetail eHomeworkRecordDetail = new EHomeworkRecordDetail();
		eHomeworkRecordDetail.setHwRecId(map.get("hw_rec_id")!=null?(Integer)map.get("hw_rec_id"):null);
		eHomeworkRecordDetail.setQuesId(map.get("ques_id")!=null?(Integer)map.get("ques_id"):null);
		eHomeworkRecordDetail.setAnswer(map.get("answer")!=null?(String)map.get("answer"):null);
		eHomeworkRecordDetail.setStudentAnswer(map.get("student_answer")!=null?(String)map.get("student_answer"):null);
		eHomeworkRecordDetail.setScore(map.get("score")!=null?(Double)map.get("score"):null);
		eHomeworkRecordDetail.setoScore(map.get("o_score")!=null?(String)map.get("o_score"):null);
		eHomeworkRecordDetail.setTeacherComment(map.get("teacher_comment")!=null?(String)map.get("teacher_comment"):null);
		eHomeworkRecordDetail.setAnswerTime(map.get("answer_time")!=null?(String)map.get("answer_time"):null);
		eHomeworkRecordDetail.setRemark(map.get("remark")!=null?(String)map.get("remark"):null);
		return eHomeworkRecordDetail;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id",hwRecId);
		map.put("ques_id",quesId);
		map.put("answer",answer);
		map.put("student_answer",studentAnswer);
		map.put("score",score);
		map.put("o_score",oScore);
		map.put("teacher_comment",teacherComment);
		map.put("answer_time",answerTime);
		map.put("remark",remark);
		return map;
	}

	public int getHwRecId() {
		return hwRecId;
	}

	public void setHwRecId(int hwRecId) {
		this.hwRecId = hwRecId;
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

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getoScore() {
		return oScore;
	}

	public void setoScore(String oScore) {
		this.oScore = oScore;
	}

	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	public String getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
