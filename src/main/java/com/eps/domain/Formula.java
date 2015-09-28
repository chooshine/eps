package com.eps.domain;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.eps.utils.UStrMap;


public class Formula extends BaseDomain implements RowMapper<Formula> {
	private long userId;
	private String latex;
	private String _url;
	private int width;
	private int height;
	private int size;
	private Date createTime;
	private int count;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLatex() {
		return latex;
	}
	public void setLatex(String latex) {
		this.latex = latex;
	}
	public String getUrl() {
		return _url;
	}
	public void setUrl(String _url) {
		this._url = _url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("userId", userId);
		map.put("url", _url);
		map.put("latex", latex);
		map.put("size", size);
		map.put("width", width);
		map.put("height", height);
		map.put("createTime", createTime);
		map.put("count", count);
		return map;
	}
	@Override
	public Formula mapRow(ResultSet rs, int i) throws SQLException {
		Formula f = new Formula();
		f.setUserId(rs.getInt("USER_ID"));
		f.setLatex(rs.getString("LATEX"));
		f.setUrl(rs.getString("URL"));
		f.setSize(rs.getInt("SIZE"));
		f.setWidth(rs.getInt("WIDTH"));
		f.setHeight(rs.getInt("HEIGHT"));
		f.setCount(rs.getInt("COUNT"));
		f.setCreateTime(rs.getDate("CREATETIME"));
		return f;
	}
}
