package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@SuppressWarnings("serial")
public class EQuesType extends BaseDomain {
	
	private int typeId;
	private int examId;
	private String typeDetail;
	private String type;
	private int orderNum;
	private String remark;
	private float defaultScore;
	private String typeName;
	private int tsId;
	
	public static EQuesType create(LStrMap<Object> map){
		EQuesType eQuesType = new EQuesType();
		eQuesType.setTypeId(map.get("type_Id")!=null?(Integer) map.get("type_Id"):null);
		eQuesType.setExamId(map.get("exam_Id")!=null?(Integer) map.get("exam_Id"):null);
		eQuesType.setTypeDetail(map.get("type_Detail")!=null?(String) map.get("type_Detail"):null);
		eQuesType.setType(map.get("type")!=null?map.get("type").toString():null);
		eQuesType.setOrderNum(map.get("order_Num")!=null?(Integer) map.get("order_Num"):null);
		eQuesType.setRemark(map.get("remark")!=null?(String) map.get("remark"):null);
		eQuesType.setDefaultScore(map.get("default_Score")!=null?(Float) map.get("default_Score"):null);
		eQuesType.setTypeName(map.get("type_name")!=null?(String) map.get("type_name"):null);
		eQuesType.setTsId(map.get("ts_id")!=null?(Integer) map.get("ts_id"):null);
		return eQuesType;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("type_id", typeId);
		map.put("exam_id", examId);
		map.put("type_detail", typeDetail);
		map.put("type", type);
		map.put("order_num", orderNum);
		map.put("remark", remark);
		map.put("default_score", defaultScore);
		map.put("type_name", typeName);
		map.put("ts_id", tsId);
		return map;
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getTypeDetail() {
		return typeDetail;
	}
	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public float getDefaultScore() {
		return defaultScore;
	}
	public void setDefaultScore(float defaultScore) {
		this.defaultScore = defaultScore;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTsId() {
		return tsId;
	}

	public void setTsId(int tsId) {
		this.tsId = tsId;
	}
	
	
}
