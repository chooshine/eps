package com.eps.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.eps.utils.LStrMap;

public class LStrMapRowMapper implements RowMapper<LStrMap<Object>>{

	public LStrMap<Object> mapRow(ResultSet rs, int index) throws SQLException {
		LStrMap<Object> item = LStrMap.newInstance();
		ResultSetMetaData metaData = rs.getMetaData();
		String key = "";
		Object value = null;
		for (int i = 0; i < metaData.getColumnCount(); i++) {
			key =  metaData.getColumnLabel(i+1);
			value = rs.getObject(key);
			item.put(key, value); 
		}
		return item;
	}

}
