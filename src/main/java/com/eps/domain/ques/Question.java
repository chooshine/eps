package com.eps.domain.ques;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eps.utils.UStrMap;

public class Question {
	private int questionId;
	private int questionType;
	private double score;
	private double defaultScore;
	private List<Content> contents;
	private List<Content> parses;
	private List<Content> answers;
	private String questionRec;
	private double difficulty;
	private String indexNo;
	private int shareFlag;
	private List<KnowledgePoint> points;
	private List<Option> options;
	private List<Question> childQuestions;
	private String referIds;
	
	@SuppressWarnings("unchecked")
	public Map<String,Image> setContents(String content){
		UStrMap<Object> data = Content.parseContent(content);
		this.contents = (List<Content>) data.get("text");
		return (Map<String, Image>) data.get("img");
	}
	@SuppressWarnings("unchecked")
	public Map<String,Image> setParses(String content){
		UStrMap<Object> data = Content.parseContent(content);
		this.parses = (List<Content>) data.get("text");
		return (Map<String, Image>) data.get("img");
	}
	@SuppressWarnings("unchecked")
	public Map<String,Image> setAnswers(String content){
		UStrMap<Object> data = Content.parseContent(content);
		this.answers = (List<Content>) data.get("text");
		return (Map<String, Image>) data.get("img");
	}
	public Question addChildQuestion(Question ques){
		if(childQuestions==null)childQuestions = new ArrayList<Question>();
		childQuestions.add(ques);
		return this;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getDefaultScore() {
		return defaultScore;
	}
	public void setDefaultScore(double defaultScore) {
		this.defaultScore = defaultScore;
	}
	public List<Content> getContents() {
		return contents;
	}
	public void setContents(List<Content> content) {
		this.contents = content;
	}
	public List<Content> getParses() {
		return parses;
	}
	public void setParses(List<Content> parse) {
		this.parses = parse;
	}
	public List<Content> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Content> answer) {
		this.answers = answer;
	}
	public String getQuestionRec() {
		return questionRec;
	}
	public void setQuestionRec(String questionRec) {
		this.questionRec = questionRec;
	}
	public double getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
	public String getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	public int getShareFlag() {
		return shareFlag;
	}
	public void setShareFlag(int shareFlag) {
		this.shareFlag = shareFlag;
	}
	public List<KnowledgePoint> getPoints() {
		return points;
	}
	public void setPoints(List<KnowledgePoint> points) {
		this.points = points;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public List<Question> getChildQuestions() {
		return childQuestions;
	}
	public void setChildQuestions(List<Question> childQuestions) {
		this.childQuestions = childQuestions;
	}
	public String getReferIds() {
		return referIds;
	}
	public void setReferIds(String referIds) {
		this.referIds = referIds;
	}
	
	
}
