package com.eps.dao.corpus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.KeyWord;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class KeyWordDao extends BaseDao{
	
	@Value("${keyword.query.keyname}")
	private String getFileName;
	
	@Value("${keyword.insert}")
	private String insertKeyWord;
	
	@Value("${keyword.query.all}")
	private String getAllKeyWord;
	
	@Value("${keyword.update}")
	private String updateKeyWord;
	
	public KeyWord getKeyWord(String key,String lanuage,String repType){
		UStrMap<String> params = UStrMap.newInstance();
		params.put("name", key);
		params.put("language", lanuage);
		params.put("rep_type", repType);
		List<KeyWord> list = getNameParameTemplate().query(getFileName, params, new KeyWordRowMapper());
		if(list.size()>=1) return list.get(0);
		return null;
	}
	
	public List<KeyWord> findKeyWord(){
		return getJdbcTemplate().query(getAllKeyWord, new KeyWordRowMapper());
	}
	public int saveKeyWord(KeyWord kw){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("keyword_name", kw.getKeyWordName());
		params.put("BUFFER_FILE", kw.getFileName());
		params.put("LANGUAGE", kw.getLanguage());
		params.put("STATUS_FLAG", kw.getStatus());
		params.put("rep_type", kw.getRepType());
		return this.excute(insertKeyWord, params);
	}
	public void updateKeyWord(KeyWord kw){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("keyword_id", kw.getKeyWordId());
		params.put("keyword_name", kw.getKeyWordName());
		params.put("BUFFER_FILE", kw.getFileName());
		params.put("LANGUAGE", kw.getLanguage());
		params.put("STATUS_FLAG", kw.getStatus());
		params.put("rep_type", kw.getRepType());
		this.excute(updateKeyWord, params);
	}
	class KeyWordRowMapper implements RowMapper<KeyWord>{
		public KeyWord mapRow(ResultSet rs, int arg1) throws SQLException {
			KeyWord kw = new KeyWord();
			kw.setKeyWordId(rs.getLong("KEYWORD_ID"));
			kw.setKeyWordName(rs.getString("KEYWORD_NAME"));
			kw.setFileName(rs.getString("BUFFER_FILE"));
			kw.setLanguage(rs.getString("LANGUAGE"));
			kw.setStatus(rs.getInt("STATUS_FLAG"));
			kw.setRepType(rs.getString("REP_TYPE"));
			return kw;
		}
		
	}
}
