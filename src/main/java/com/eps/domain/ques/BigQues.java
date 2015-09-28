package com.eps.domain.ques;

import java.util.ArrayList;
import java.util.List;

public class BigQues {
	private int id;
	private String name;
	private String detail;
	private int orderNum;
	private String indexNo;
	private int quesType;
	private double defaultScore;
	private List<Question> questions;
	
	public BigQues addQuestion(Question q){
		if(questions==null) questions = new ArrayList<Question>();
		questions.add(q);
		return this;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	public int getQuesType() {
		return quesType;
	}
	public void setQuesType(int quesType) {
		this.quesType = quesType;
	}
	public double getDefaultScore() {
		return defaultScore;
	}
	public void setDefaultScore(double defaultScore) {
		this.defaultScore = defaultScore;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
}
