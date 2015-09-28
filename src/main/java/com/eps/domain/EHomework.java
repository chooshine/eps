package com.eps.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class EHomework {

	private int hwId = -1;
	private String hwName;
	private int subjectId;
	private int hwType;
	private int topicNum;
	private long creator; 
	private String createTime;
	private int releaseStatus;
	private String releaseTime;
	private int referHwId;
	private String remark;
	private int gradeLevel;
	
	public static EHomework create(LStrMap<Object> map) {
		EHomework eHomework = new EHomework();
		eHomework.setHwId(map.get("hw_id")!=null?(Integer)map.get("hw_id"):null);
		eHomework.setHwName(map.get("hw_name")!=null?(String)map.get("hw_name"):null);
		eHomework.setSubjectId(map.get("subject_id")!=null?(Integer)map.get("subject_id"):null);
		eHomework.setHwType(map.get("hw_type")!=null?(Integer)map.get("hw_type"):null);
		eHomework.setTopicNum(map.get("topic_num")!=null?(Integer)map.get("topic_num"):null);
		eHomework.setCreator(map.get("creator")!=null?(Integer)map.get("creator"):null);
		eHomework.setCreateTime(map.get("create_time")!=null?(String)map.get("create_time"):null);
		eHomework.setReleaseStatus(map.get("release_status")!=null?(Integer)map.get("release_status"):null);
		eHomework.setReleaseTime(map.get("release_time")!=null?(String)map.get("release_time"):null);
		eHomework.setTopicNum(map.get("refer_hwid")!=null?(Integer)map.get("refer_hwid"):null);
		eHomework.setRemark(map.get("remark")!=null?(String)map.get("remark"):null);
		eHomework.setGradeLevel(map.get("grade_level")!=null?(Integer)map.get("remark"):null);
		return eHomework;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id",hwId);
		map.put("hw_name",hwName);
		map.put("subject_id",subjectId);
		map.put("hw_type",hwType);
		map.put("topic_num",topicNum);
		map.put("creator",creator);
		map.put("create_time",createTime);
		map.put("release_status",releaseStatus);
		map.put("release_time",releaseTime);
		map.put("refer_hwid", referHwId);
		map.put("remark",remark);
		map.put("grade_level",gradeLevel);
		return map;
	}
	
	public int getHwId() {
		return hwId;
	}
	public void setHwId(int hwId) {
		this.hwId = hwId;
	}
	public String getHwName() {
		return hwName;
	}
	public void setHwName(String hwName) {
		this.hwName = hwName;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getHwType() {
		return hwType;
	}
	public void setHwType(int hwType) {
		this.hwType = hwType;
	}
	public int getTopicNum() {
		return topicNum;
	}
	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}
	public long getCreator() {
		return creator;
	}
	public void setCreator(long creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(int releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getReferHwId() {
		return referHwId;
	}

	public void setReferHwId(int referHwId) {
		this.referHwId = referHwId;
	}

	public int getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
}