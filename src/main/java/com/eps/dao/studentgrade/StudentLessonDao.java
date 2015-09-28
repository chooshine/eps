package com.eps.dao.studentgrade;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class StudentLessonDao extends BaseDao {

	@Value("${lesson.get.all.student}")
	private String lessonAll;
	
	@Value("${lesson.get.today.student}")
	private String getStudentTodayLesson;
	
	/**
	 * 获取学生今天有的课
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTodayLessonByStudent(int student_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("student_id", student_id);
		return find(getStudentTodayLesson, params);
	}
	
	/**
	 * 获取学生课表
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getLessonByStudent(int student_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("student_id", student_id);
		return find(lessonAll,params);
	}
}
