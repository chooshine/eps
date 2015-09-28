package com.eps.dao.corpus;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.UStrMap;

@Repository
public class SearchLogDao extends BaseDao {
	
	@Value("${search.log.save}")
	private String insertLog;
	
	/**
	 * 保存日志
	 * @param keywordId 关键字Id
	 * @param userId 用户Id
	 * @param otherKey 第一关键字
	 */
	public void writeLog(String keyword, long userId, String otherKey,String table){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("KEYWORD", keyword);
		params.put("USER_ID", userId);
		params.put("SEARCH_TIME", new Date());
		params.put("OTHER_KEYWORD", otherKey);
		params.put("TABLE_NAME", table);
		excute(insertLog, params);
	}
}
