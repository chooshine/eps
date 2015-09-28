package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class FGoodModel {
	private int modelId;
	private String goodsId;
	private String modelName;
	private float unitPrice;
	private int stock;
	private String remark;
	
	public static FGoodModel create(LStrMap<Object> map){
		FGoodModel fGoodModel = new FGoodModel();
		fGoodModel.setModelId(map.get("model_id")!=null?(Integer) map.get("model_id"):null);
		fGoodModel.setGoodsId(map.get("goods_id")!=null?(String) map.get("goods_id"):null);
		fGoodModel.setModelName(map.get("model_name")!=null?(String) map.get("model_name"):null);
		fGoodModel.setUnitPrice(map.get("unit_price")!=null?(Float) map.get("unit_price"):null);
		fGoodModel.setModelId(map.get("stock")!=null?(Integer) map.get("stock"):null);
		fGoodModel.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		return fGoodModel;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("model_id", modelId);
		map.put("goods_id", goodsId);
		map.put("model_name",modelName);
		map.put("unit_price",unitPrice);
		map.put("stock",stock);
		map.put("remark", remark);
		return map;
	}
	
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
