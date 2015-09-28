package com.eps.dao.studentgrade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class DevelopmentDao extends BaseDao {

	@Value("${analy.person.subject.all}")
	private String subjectAll;
	
	@Value("${analy.person.subject.single}")
	private String subjectSingle;
	
	/**
	 * 得到学生参加过的所有考试科目
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getAllSubject(int student_id) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("student_id", student_id);
		return this.find(subjectAll, params);
	}
	
	/**
	 * 得到某段时间内的某个科目在所有考试中的成绩
	 * @param user_id
	 * @param time1
	 * @param time2
	 * @param subject
	 * @return
	 */
	public List<LStrMap<Object>> getSingleSubject(int student_id, Date time1, Date time2, int subject_id) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("student_id", student_id);
		params.put("test_time1", time1);
		params.put("test_time2", time2);
		params.put("subject_id", subject_id);
		return this.find(subjectSingle, params);
	}
}