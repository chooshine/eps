package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class FAccount {
	private long userId;
	private float cash;
	
	public static FAccount create(LStrMap<Object> map){
		FAccount fAccount = new FAccount();
		fAccount.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		fAccount.setCash(map.get("cash")!=null?(Float) map.get("cash"):null);
		return fAccount;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id",userId);
		map.put("cash",cash);
		return map;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}
}
