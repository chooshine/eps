package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EQuestion {
	private int  quesId;
	private String subjectNo;
	private String quesType;
	private String quesContent;
	private String creator;
	private String difficulty;
	private String remark;
	private String  knowledgePoint;
	private String keyword;
	private int optionNum;
	private String relFlag;
	private String inputTime;
	private String quesRefer;
	private String resource;
	private int shareFlag;
	private int gradeLevel;
	private int releaseFlag;
	private String quesRec;
	private int tsId;
	
	public static EQuestion create(LStrMap<Object> map){
		EQuestion eQuestion = new EQuestion();
		eQuestion.setQuesId(map.get("ques_id")!=null?(Integer) map.get("ques_id"):null);
		eQuestion.setSubjectNo(map.get("subject_no")!=null?(String) map.get("subject_no"):null);
		eQuestion.setQuesType(map.get("ques_type")!=null?(String) map.get("ques_type"):null);
		eQuestion.setQuesContent(map.get("ques_content")!=null?(String) map.get("ques_content"):null);
		eQuestion.setCreator(map.get("creator")!=null?(String) map.get("creator"):null);
		eQuestion.setDifficulty(map.get("difficulty")!=null?(String) map.get("difficulty"):null);
		eQuestion.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		eQuestion.setKnowledgePoint(map.get("knowledge_point")!=null?(String) map.get("knowledge_point"):null);
		eQuestion.setKeyword(map.get("keyword")!=null?(String) map.get("keyword"):null);
		eQuestion.setOptionNum(map.get("option_num")!=null?(Integer) map.get("option_num"):null);
		eQuestion.setRelFlag(map.get("rel_flag")!=null?(String) map.get("rel_flag"):null);
		eQuestion.setInputTime(map.get("input_time")!=null?(String) map.get("input_time"):null);
		eQuestion.setQuesRefer(map.get("ques_refer")!=null?(String) map.get("ques_refer"):null);
		eQuestion.setResource(map.get("resource")!=null?(String) map.get("resource"):null);
		eQuestion.setShareFlag(map.get("share_flag")!=null?(Integer) map.get("share_flag"):null);
		eQuestion.setGradeLevel(map.get("grade_level")!=null?(Integer) map.get("grade_level"):null);
		eQuestion.setReleaseFlag(map.get("release_flag")!=null?(Integer) map.get("release_flag"):null);
		eQuestion.setQuesRec(map.get("ques_rec")!=null?(String) map.get("ques_rec"):null);
		eQuestion.setTsId(map.get("ts_id")!=null?(Integer) map.get("ts_id"):null);
		return eQuestion;
	}

	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id",quesId);
		map.put("subject_no",subjectNo);
		map.put("ques_type",quesType);
		map.put("ques_content",quesContent);
		map.put("creator",creator);
		map.put("difficulty",difficulty);
		map.put("remark",remark);
		map.put("knowledge_point",knowledgePoint);
		map.put("keyword",keyword);
		map.put("option_num",optionNum);
		map.put("rel_flag",relFlag);
		map.put("input_time",inputTime);
		map.put("ques_refer",quesRefer);
		map.put("resource",resource);
		map.put("share_flag",shareFlag);
		map.put("grade_level",gradeLevel);
		map.put("release_flag",releaseFlag);
		map.put("ques_rec", quesRec);
		map.put("ts_id", tsId);
		return map;
	}
	
	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public String getQuesContent() {
		return quesContent;
	}

	public void setQuesContent(String quesContent) {
		this.quesContent = quesContent;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

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

	public int getOptionNum() {
		return optionNum;
	}

	public void setOptionNum(int optionNum) {
		this.optionNum = optionNum;
	}

	public String getRelFlag() {
		return relFlag;
	}

	public void setRelFlag(String relFlag) {
		this.relFlag = relFlag;
	}

	public String getInputTime() {
		return inputTime;
	}

	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}

	public String getQuesRefer() {
		return quesRefer;
	}

	public void setQuesRefer(String quesRefer) {
		this.quesRefer = quesRefer;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(int shareFlag) {
		this.shareFlag = shareFlag;
	}

	public int getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public int getReleaseFlag() {
		return releaseFlag;
	}

	public void setReleaseFlag(int releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public String getQuesRec() {
		return quesRec;
	}

	public void setQuesRec(String quesRec) {
		this.quesRec = quesRec;
	}

	public int getTsId() {
		return tsId;
	}

	public void setTsId(int tsId) {
		this.tsId = tsId;
	}
	
}
