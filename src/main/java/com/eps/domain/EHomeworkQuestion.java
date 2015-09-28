package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EHomeworkQuestion {

	private int hwId;
	private int quesId;
	private int mTopic;
	private double score;
	private String oScore;
	private String remark;
	
	public static EHomeworkQuestion create(LStrMap<Object> map) {
		EHomeworkQuestion eHomeworkQuestion = new EHomeworkQuestion();
		eHomeworkQuestion.setHwId(map.get("hw_id")!=null?(Integer)map.get("hw_id"):null);
		eHomeworkQuestion.setQuesId(map.get("ques_id")!=null?(Integer)map.get("ques_id"):null);
		eHomeworkQuestion.setmTopic(map.get("m_topic")!=null?(Integer)map.get("m_topic"):null);
		eHomeworkQuestion.setScore(map.get("score")!=null?(Double)map.get("score"):null);
		eHomeworkQuestion.setoScore(map.get("o_score")!=null?(String)map.get("o_score"):null);
		eHomeworkQuestion.setRemark(map.get("remark")!=null?(String)map.get("remark"):null);
		return eHomeworkQuestion;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id",hwId);
		map.put("ques_id",quesId);
		map.put("m_topic",mTopic);
		map.put("score",score);
		map.put("o_score",oScore);
		map.put("remark",remark);
		return map;
	}

	public int getHwId() {
		return hwId;
	}

	public void setHwId(int hwId) {
		this.hwId = hwId;
	}

	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public int getmTopic() {
		return mTopic;
	}

	public void setmTopic(int mTopic) {
		this.mTopic = mTopic;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
