package com.eps.domain;

public class Author {
	
	private long authorId;
	private String nameCode;
	private String firstName;
	private String lastName;
	private String firstNameOr;
	private String lastNameOr;
	private String firstNameEn;
	private String lastNameEn;
	private int articleNum; //作品数量
	public String getNameCode() {
		return nameCode;
	}
	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}
	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstNameOr() {
		return firstNameOr;
	}
	public void setFirstNameOr(String firstNameOr) {
		this.firstNameOr = firstNameOr;
	}
	public String getLastNameOr() {
		return lastNameOr;
	}
	public void setLastNameOr(String lastNameOr) {
		this.lastNameOr = lastNameOr;
	}
	public String getFirstNameEn() {
		return firstNameEn;
	}
	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}
	public String getLastNameEn() {
		return lastNameEn;
	}
	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}
	public int getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}
	
}
