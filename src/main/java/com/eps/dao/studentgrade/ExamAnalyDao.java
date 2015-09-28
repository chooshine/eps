package com.eps.dao.studentgrade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class ExamAnalyDao extends BaseDao {

	@Value("${analy.person.exam.onetest}")
	private String examOneTest;
	
	@Value("${analy.person.exam.fourtimes}")
	private String examFourTimes;
	
	
	@Value("${analy.person.exam.testsort}")
	private String examTestSort;
	
	@Value("${analy.person.exam.fourtest}")
	private String examFourTest;
		
	/**
	 * 得到最近四次考试的名字和考试编号
	 * @return
	 */
	public List<LStrMap<Object>> getFourTestNameId(UStrMap<Object> map){
		return this.find(examFourTest,map);
	}
	
	/**
	 * 根据用户编号、考试id，得到4次考试时间
	 * @param user_id
	 * @param test_id
	 * @return
	 */
	public List<LStrMap<Object>> getTime(long user_id,int test_id) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		params.put("test_id", test_id);
		return this.find(examFourTimes, params);
	}
	
	/**
	 * 得到一次的考试信息
	 * @param user_id
	 * @param times
	 * @return
	 */
	public List<LStrMap<Object>> getOneTestInfo(UStrMap<Object> map) {
		return this.find(examOneTest, map);
	}
	
	/**
	 * 得到学生所在班级的所有考试的考试号和科目
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getTestSort(long user_id) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return this.find(examTestSort, params);
	}
}
