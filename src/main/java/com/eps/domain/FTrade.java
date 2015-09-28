package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class FTrade {
	public final static int TRADEDING = 1; //交易中
	public final static int TRADESUCCESS=2;//交易成功
	public final static int TRADEFAILD = 3;//交易失败
	public final static int PAY = 1;       //支出
	public final static int INCOME = 2;    //收入
	public final static int RECHARGEID = 0;//充值的model_id和goods_id
	public final static int DRAWMONEYID = -1;//提现的model_id和goods_id
	private String tradeId;
	private long userId;
	private int tradeType;
	private String tradeTime;
	private float money;
	private int tradeVariety;
	private String goodsId;
	private int modelId;
	private int tradeStatus;
	private String snapId;
	private String remark;
	private String tradeName;
	
	
	public static FTrade create(LStrMap<Object> map){
		FTrade fTrade = new FTrade();
		fTrade.setTradeId(map.get("trade_id")!=null?(String) map.get("trade_id"):null);
		fTrade.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		fTrade.setTradeType(map.get("trade_type")!=null?(Integer) map.get("trade_type"):null);
		fTrade.setTradeTime(map.get("trade_time")!=null?(String) map.get("trade_time"):null);
		fTrade.setMoney(map.get("money")!=null?(Float) map.get("money"):null);
		fTrade.setTradeVariety(map.get("trade_variety")!=null?(Integer) map.get("trade_variety"):null);
		fTrade.setGoodsId(map.get("goods_id")!=null?(String) map.get("goods_id"):null);
		fTrade.setModelId(map.get("model_id")!=null?(Integer) map.get("model_id"):null);
		fTrade.setTradeStatus(map.get("trade_status")!=null?(Integer) map.get("trade_status"):null);
		fTrade.setSnapId(map.get("snap_id")!=null?(String) map.get("snap_id"):null);
		fTrade.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		fTrade.setTradeName(map.get("trade_name")!=null?(String) map.get("trade_name"):null);
		return fTrade;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("trade_id", tradeId);
		map.put("user_id", userId);
		map.put("trade_type", tradeType);
		map.put("trade_time", tradeTime);
		map.put("money", money);
		map.put("trade_variety", tradeVariety);
		map.put("goods_id", goodsId);
		map.put("model_id", modelId);
		map.put("trade_status", tradeStatus);
		map.put("snap_id", snapId);
		map.put("remark", remark);
		map.put("trade_name",tradeName);
		return map;
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
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public int getTradeVariety() {
		return tradeVariety;
	}
	public void setTradeVariety(int tradeVariety) {
		this.tradeVariety = tradeVariety;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getSnapId() {
		return snapId;
	}
	public void setSnapId(String snapId) {
		this.snapId = snapId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
}
