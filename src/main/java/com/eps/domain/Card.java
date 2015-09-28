package com.eps.domain;

public class Card {
	private long cardId;
	private String cardNo;
	private String cardPassword;
	private String cardName;
	private int cardType;
	private long carOwner;
	private long cardUser;
	private int cardStatus;
	private String remark;
	private int lockStatus;
	
	public long getCardId() {
		return cardId;
	}
	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardPassword() {
		return cardPassword;
	}
	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public int getCardType() {
		return cardType;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	public long getCarOwner() {
		return carOwner;
	}
	public void setCarOwner(long carOwner) {
		this.carOwner = carOwner;
	}
	public long getCardUser() {
		return cardUser;
	}
	public void setCardUser(long cardUser) {
		this.cardUser = cardUser;
	}
	public int getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(int cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}
}
