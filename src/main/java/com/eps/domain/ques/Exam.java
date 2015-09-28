package com.eps.domain.ques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exam {
	private int id;
	private String name;
	private int examType;
	private int examArea;
	private int subjectNo;
	private int year;
	private int totalScore;
	private int passScore;
	private int bigQuesNum;
	private int questionNum;
	private int eStatus;
	private List<BigQues> bigQues;
	private String[] bCodes;
	private String[] mCodes;
	private int qRandom;
	private int oRandom;
	private Map<String,Image> images;
	public Exam addBigQues(BigQues big){
		if(bigQues==null){
			bigQues = new ArrayList<BigQues>();
		}
		bigQues.add(big);
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
	public int getExamType() {
		return examType;
	}
	public void setExamType(int examType) {
		this.examType = examType;
	}
	public int getExamArea() {
		return examArea;
	}
	public void setExamArea(int examArea) {
		this.examArea = examArea;
	}
	public int getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(int subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getPassScore() {
		return passScore;
	}
	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}
	public int getBigQuesNum() {
		return bigQuesNum;
	}
	public void setBigQuesNum(int bigQuesNum) {
		this.bigQuesNum = bigQuesNum;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public int geteStatus() {
		return eStatus;
	}
	public void seteStatus(int eStatus) {
		this.eStatus = eStatus;
	}
	public List<BigQues> getBigQues() {
		return bigQues;
	}
	public void setBigQues(List<BigQues> bigQues) {
		this.bigQues = bigQues;
	}
	public String[] getbCodes() {
		return bCodes;
	}
	public void setbCodes(String[] bCodes) {
		this.bCodes = bCodes;
	}
	public String[] getmCodes() {
		return mCodes;
	}
	public void setmCodes(String[] mCodes) {
		this.mCodes = mCodes;
	}
	public int getqRandom() {
		return qRandom;
	}
	public void setqRandom(int qRandom) {
		this.qRandom = qRandom;
	}
	public int getoRandom() {
		return oRandom;
	}
	public void setoRandom(int oRandom) {
		this.oRandom = oRandom;
	}

	public Map<String, Image> getImages() {
		return images;
	}

	public void setImages(Map<String, Image> images) {
		this.images = images;
	}
	
	public Exam addImage(Image image){
		if(images == null) images = new HashMap<String, Image>();
		images.put(image.getKey(), image);
		return this;
	}
	public Exam addImages(Map<String,Image> images){
		if(images == null) images = new HashMap<String, Image>();
		images.putAll(images);
		return this;
	}
	public Exam addImages(List<Image> images){
		for (Image image : images) {
			addImage(image);
		}
		return this;
	}
}
