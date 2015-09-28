package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class ExamPreviewSets {

	private int examId;
	private int sealFlag;
	private int marktagFlag;
	private String marktagContent;
	private int examinfoFlag;
	private String examinfoContent;
	private int studentinputFlag;
	private String studentinputContent;
	private int scoreFlag;
	private int noticeFlag;
	private String noticeContent;
	
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getSealFlag() {
		return sealFlag;
	}
	public void setSealFlag(int sealFlag) {
		this.sealFlag = sealFlag;
	}
	public int getMarktagFlag() {
		return marktagFlag;
	}
	public void setMarktagFlag(int marktagFlag) {
		this.marktagFlag = marktagFlag;
	}
	public String getMarktagContent() {
		return marktagContent;
	}
	public void setMarktagContent(String marktagContent) {
		this.marktagContent = marktagContent;
	}
	public int getExaminfoFlag() {
		return examinfoFlag;
	}
	public void setExaminfoFlag(int examinfoFlag) {
		this.examinfoFlag = examinfoFlag;
	}
	public String getExaminfoContent() {
		return examinfoContent;
	}
	public void setExaminfoContent(String examinfoContent) {
		this.examinfoContent = examinfoContent;
	}
	public int getStudentinputFlag() {
		return studentinputFlag;
	}
	public void setStudentinputFlag(int studentinputFlag) {
		this.studentinputFlag = studentinputFlag;
	}
	public String getStudentinputContent() {
		return studentinputContent;
	}
	public void setStudentinputContent(String studentinputContent) {
		this.studentinputContent = studentinputContent;
	}
	public int getScoreFlag() {
		return scoreFlag;
	}
	public void setScoreFlag(int scoreFlag) {
		this.scoreFlag = scoreFlag;
	}
	public int getNoticeFlag() {
		return noticeFlag;
	}
	public void setNoticeFlag(int noticeFlag) {
		this.noticeFlag = noticeFlag;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("seal_flag", sealFlag);
		map.put("marktag_flag", marktagFlag);
		map.put("marktag_content", marktagContent);
		map.put("examinfo_flag", examinfoFlag);
		map.put("examinfo_content", examinfoContent);
		map.put("studentinput_flag", studentinputFlag);
		map.put("studentinput_content", studentinputContent);
		map.put("score_flag", scoreFlag);
		map.put("notice_flag", noticeFlag);
		map.put("notice_content", noticeContent);
		return map;
	}
	
	public static ExamPreviewSets create(LStrMap<Object> map){
		ExamPreviewSets examPreviewSets = new ExamPreviewSets();
		examPreviewSets.setExamId(map.get("exam_id")!=null?(Integer)map.get("exam_id"):null);
		examPreviewSets.setSealFlag(map.get("seal_flag")!=null?(Integer)map.get("seal_flag"):null);
		examPreviewSets.setMarktagFlag(map.get("marktag_flag")!=null?(Integer)map.get("marktag_flag"):null);
		examPreviewSets.setMarktagContent(map.get("margtag_content")!=null?(String)map.get("margtag_content"):null);
		examPreviewSets.setExaminfoFlag(map.get("examinfo_flag")!=null?(Integer)map.get("examinfo_flag"):null);
		examPreviewSets.setExaminfoContent(map.get("examinfo_content")!=null?(String)map.get("examinfo_content"):null);
		examPreviewSets.setStudentinputFlag(map.get("studentinput_flag")!=null?(Integer)map.get("studentinput_flag"):null);
		examPreviewSets.setStudentinputContent(map.get("studentinput_content")!=null?(String)map.get("studentinput_content"):null);
		examPreviewSets.setScoreFlag(map.get("score_flag")!=null?(Integer)map.get("score_flag"):null);
		examPreviewSets.setNoticeFlag(map.get("notice_flag")!=null?(Integer)map.get("notice_flag"):null);
		examPreviewSets.setNoticeContent(map.get("notice_content")!=null?(String)map.get("notice_content"):null);
		return examPreviewSets;
	}
}
