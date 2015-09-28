package com.eps.dao.achievement;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class LessonDao extends BaseDao {
	
	@Value("${lesson.get.today.teacher}")
	private String getTodayLessonByTeacher;
	
	@Value("${lesson.get.all.teacher}")
	private String getLessonByTeacher;
	/**
	 * 获取老师今天有的课
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTodayLessonByTeacher(int teacherId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("teacher_id", teacherId);
		return find(getTodayLessonByTeacher, params);
	}
	
	/**
	 * 获取老师课表
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getLessonByTeacher(int teacherId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("teacher_id", teacherId);
		return find(getLessonByTeacher,params);
	}
}
