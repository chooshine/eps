package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EExamQuestion {
	private int examId;
	private int quesId;
	private int typeId;
	private int mTopic;
	private float score;
	private String oScore;
	private String remark;
	
	public static EExamQuestion create(LStrMap<Object> map){
		EExamQuestion eExamQuestion = new EExamQuestion();
		eExamQuestion.setExamId(map.get("exam_id")!=null?(Integer) map.get("exam_id"):null);
		eExamQuestion.setQuesId(map.get("ques_id")!=null?(Integer) map.get("ques_id"):null);
		eExamQuestion.setTypeId(map.get("type_id")!=null?(Integer) map.get("type_id"):null);
		eExamQuestion.setmTopic(map.get("m_topic")!=null?(Integer) map.get("m_topic"):null);
		eExamQuestion.setScore(map.get("score")!=null?(Float) map.get("score"):null);
		eExamQuestion.setoScore(map.get("o_score")!=null?(String) map.get("o_score"):null);
		eExamQuestion.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		return eExamQuestion;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id",examId);
		map.put("ques_id",quesId);
		map.put("type_id",typeId);
		map.put("m_topic",mTopic);
		map.put("score",score);
		map.put("o_score",oScore);
		map.put("remark",remark);
		return map;
	}
	
	
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getQuesId() {
		return quesId;
	}
	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getmTopic() {
		return mTopic;
	}
	public void setmTopic(int mTopic) {
		this.mTopic = mTopic;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getoScore() {
		return oScore;
	}
	public void setoScore(String oScore) {
		this.oScore = oScore;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
