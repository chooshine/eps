package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class FTradeLog {
	private String 	tradeLogId;
	private String tradeId;
	private long userId;
	private int  tradeType;
	private String tradeTime;
	private float cash;
	private float coupon;
	private String couponId;
	private float cashBefore;
	private float cashAfter;
	private float couponBefore;
	private float couponAfter;
	private String remark;
	
	public static FTradeLog create(LStrMap<Object> map){
		FTradeLog fTradeLog = new FTradeLog();
		fTradeLog.setTradeLogId(map.get("trade_log_id")!=null?(String) map.get("trade_log_id"):null);
		fTradeLog.setTradeId(map.get("trade_id")!=null?(String) map.get("trade_id"):null);
		fTradeLog.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		fTradeLog.setTradeType(map.get("trade_type")!=null?(Integer) map.get("trade_type"):null);
		fTradeLog.setTradeTime(map.get("trade_time")!=null?(String) map.get("trade_time"):null);
		
		fTradeLog.setCash(map.get("cash")!=null?(Float) map.get("cash"):null);
		fTradeLog.setCoupon(map.get("coupon")!=null?(Float) map.get("coupon"):null);
		fTradeLog.setCouponId(map.get("coupon_id")!=null?(String) map.get("coupon_id"):null);
		fTradeLog.setCashBefore(map.get("cash_before")!=null?(Float) map.get("cash_before"):null);
		fTradeLog.setCashAfter(map.get("cash_after")!=null?(Float) map.get("cash_after"):null);
		fTradeLog.setCouponBefore(map.get("coupon_before")!=null?(Float) map.get("coupon_before"):null);
		fTradeLog.setCouponAfter(map.get("coupon_after")!=null?(Float) map.get("coupon_after"):null);
		
		fTradeLog.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		return fTradeLog;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("trade_log_id", tradeLogId);
		map.put("trade_id", tradeId);
		map.put("user_id", userId);
		map.put("trade_time", tradeTime);
		map.put("trade_type", tradeType);
		map.put("cash", cash);
		map.put("coupon", coupon);
		map.put("cash_before", cashBefore);
		map.put("cash_after", cashAfter);
		map.put("coupon_before", couponBefore);
		map.put("coupon_after", couponAfter);
		map.put("remark", remark);
		return map;
	}
	
	public String getTradeLogId() {
		return tradeLogId;
	}
	public void setTradeLogId(String tradeLogId) {
		this.tradeLogId = tradeLogId;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getTradeType() {
		return tradeType;
	}
	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}
	public float getCoupon() {
		return coupon;
	}
	public void setCoupon(float coupon) {
		this.coupon = coupon;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public float getCashBefore() {
		return cashBefore;
	}
	public void setCashBefore(float cashBefore) {
		this.cashBefore = cashBefore;
	}
	public float getCashAfter() {
		return cashAfter;
	}
	public void setCashAfter(float cashAfter) {
		this.cashAfter = cashAfter;
	}
	public float getCouponBefore() {
		return couponBefore;
	}
	public void setCouponBefore(float couponBefore) {
		this.couponBefore = couponBefore;
	}
	public float getCouponAfter() {
		return couponAfter;
	}
	public void setCouponAfter(float couponAfter) {
		this.couponAfter = couponAfter;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
