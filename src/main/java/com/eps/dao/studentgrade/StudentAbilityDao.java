package com.eps.dao.studentgrade;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class StudentAbilityDao extends BaseDao {

	@Value("${analy.person.ability.basicinfo}")
	private String abilityBasicInfo;
	
	@Value("${analy.person.ability.closedtest}")
	private String abilityClosedTest;
	
	@Value("${analy.person.ability.lasttest}")
	private String abilityLastTest;
	
	/**
	 * 得到学生基本信息
	 */
	public List<LStrMap<Object>> getStudentBasicInfo(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(abilityBasicInfo, params);
	}

	/**
	 * 得到学生最近一次考试，各科分数及总分
	 */
	public List<LStrMap<Object>> getClosedTestInfo(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(abilityClosedTest, params);
	} 
	
	/**
	 * 得到学生上一次考试，各科分数及总分
	 */
	public List<LStrMap<Object>> getLastTestInfo(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(abilityLastTest, params);
	} 
}
