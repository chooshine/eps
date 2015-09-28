package com.eps.domain;

public class KeyWord {
	
	public static final int NOTCOMPLIT = 10;
	public static final int COMPLIT = 20;
	private long keyWordId;
	
	private String keyWordName;
	
	private String fileName;
	
	private String language;
	
	private int status = NOTCOMPLIT;
	
	private String repType;
	
	public long getKeyWordId() {
		return keyWordId;
	}

	public void setKeyWordId(long keyWordId) {
		this.keyWordId = keyWordId;
	}

	public String getKeyWordName() {
		return keyWordName;
	}

	public void setKeyWordName(String keyWordName) {
		this.keyWordName = keyWordName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRepType() {
		return repType;
	}

	public void setRepType(String repType) {
		this.repType = repType;
	}
	
	
}
