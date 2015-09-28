package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EHomeworkClass {
	private int hwId;
	private int classId;
	private String startTime;
	private String endTime;
	private long creator; 
	private int correct;
	private String remark;
	
	public static EHomeworkClass create(LStrMap<Object> map) {
		EHomeworkClass eHomeworkClass = new EHomeworkClass();
		eHomeworkClass.setHwId(map.get("hw_id")!=null?(Integer)map.get("hw_id"):null);
		eHomeworkClass.setClassId(map.get("class_id")!=null?(Integer)map.get("class_id"):null);
		eHomeworkClass.setStartTime(map.get("start_time")!=null?(String)map.get("start_time"):null);
		eHomeworkClass.setEndTime(map.get("end_time")!=null?(String)map.get("end_time"):null);
		eHomeworkClass.setCreator(map.get("creator")!=null?(Long)map.get("creator"):null);
		eHomeworkClass.setCorrect(map.get("correct")!=null?(Integer)map.get("correct"):null);
		eHomeworkClass.setRemark(map.get("remark")!=null?(String)map.get("remark"):null);
		return eHomeworkClass;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id",hwId);
		map.put("class_id",classId);
		map.put("start_time",startTime);
		map.put("end_time",endTime);
		map.put("creator",creator);
		map.put("correct",correct);
		map.put("remark",remark);
		return map;
	}

	public int getHwId() {
		return hwId;
	}

	public void setHwId(int hwId) {
		this.hwId = hwId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
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

	public long getCreator() {
		return creator;
	}

	public void setCreator(long userId) {
		this.creator = userId;
	}

	public long getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}