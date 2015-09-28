package com.eps.domain;

import java.util.Date;

/**
 * 语料搜索服务实体
 * @author hejunwei
 *
 */
public class LSubscribe {
	
	/** 购买人Id */
	private long userId; 
	
	/** 购买时间  */
	private Date startDate;
	
	/** 到期时间 */
	private Date endDate;
	
	/** 是否过期  */
	private String remark;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
