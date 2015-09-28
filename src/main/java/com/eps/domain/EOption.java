package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EOption {
	private int optId;
	private int quesId;
	private String optContent;
	//private float score;
	private String remark;
	private String  knowledgePoint;
	private String keyword;
	private int optFlag;
	private String optRefer;
	private String optNo;
	
	public static EOption create(LStrMap<Object> map){
		EOption eOption = new EOption();
		eOption.setOptId(map.get("opt_id")!=null?(Integer) map.get("opt_id"):null);
		eOption.setQuesId(map.get("ques_id")!=null?(Integer) map.get("ques_id"):null);
		eOption.setOptContent(map.get("opt_content")!=null?(String) map.get("opt_content"):null);
		//eOption.setScore(map.get("score")!=null?(Float) map.get("score"):null);
		eOption.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		eOption.setKnowledgePoint(map.get("knowledge_point")!=null?(String) map.get("knowledge_point"):null);
		eOption.setKeyword(map.get("keyword")!=null?(String) map.get("keyword"):null);
		eOption.setOptFlag(map.get("opt_flag")!=null?(Integer) map.get("opt_flag"):null);
		eOption.setOptRefer(map.get("opt_refer")!=null?(String) map.get("opt_refer"):null);
		eOption.setOptRefer(map.get("opt_no")!=null?(String) map.get("opt_no"):null);
		return eOption;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("opt_id",optId);
		map.put("ques_id",quesId);
		map.put("opt_content",optContent);
		//map.put("score",score);
		map.put("remark",remark);
		map.put("knowledge_point",knowledgePoint);
		map.put("keyword",keyword);
		map.put("opt_flag",optFlag);
		map.put("opt_refer",optRefer);
		map.put("opt_no",optNo);
		return map;
	}

	public int getOptId() {
		return optId;
	}

	public void setOptId(int optId) {
		this.optId = optId;
	}

	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public String getOptContent() {
		return optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	/*public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}*/

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(String knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getOptFlag() {
		return optFlag;
	}

	public void setOptFlag(int optFlag) {
		this.optFlag = optFlag;
	}

	public String getOptRefer() {
		return optRefer;
	}

	public void setOptRefer(String optRefer) {
		this.optRefer = optRefer;
	}
	
	public String getOptNo() {
		return optNo;
	}

	public void setOptNo(String optNo) {
		this.optNo = optNo;
	}

}
