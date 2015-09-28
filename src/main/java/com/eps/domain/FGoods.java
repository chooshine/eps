package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class FGoods {
	private String goodsId;
	private long userId;
	private String goodsDetail;
	private String startTime;
	private String endTime;
	private int productType;
	private int productId;
	private int payType;
	private int forSale;
	private String remark;
	
	public static FGoods create(LStrMap<Object> map){
		FGoods fGoods = new FGoods();
		fGoods.setGoodsId(map.get("goods_id")!=null?(String) map.get("goods_id"):null);
		fGoods.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		fGoods.setGoodsDetail(map.get("goods_detail")!=null?(String) map.get("goods_detail"):null);
		fGoods.setStartTime(map.get("start_time")!=null?(String) map.get("start_time"):null);
		fGoods.setEndTime(map.get("end_time")!=null?(String) map.get("end_time"):null);
		
		fGoods.setProductType(map.get("product_type")!=null?(Integer) map.get("product_type"):null);
		fGoods.setProductId(map.get("product_id")!=null?(Integer) map.get("product_id"):null);
		fGoods.setPayType(map.get("pay_type")!=null?(Integer) map.get("pay_type"):null);
		fGoods.setForSale(map.get("for_sale")!=null?(Integer) map.get("for_sale"):null);
		
		fGoods.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		return fGoods;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("goods_id", goodsId);
		map.put("user_id", userId);
		map.put("goods_detail", goodsDetail);
		map.put("start_time", startTime);
		map.put("end_time", endTime);
		map.put("product_type", productType);
		map.put("product_id", productId);
		map.put("pay_type", payType);
		map.put("for_sale", forSale);
		map.put("remark", remark);
		return map;
	}
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public int getForSale() {
		return forSale;
	}
	public void setForSale(int forSale) {
		this.forSale = forSale;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
