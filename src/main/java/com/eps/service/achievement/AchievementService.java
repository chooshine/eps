package com.eps.service.achievement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.cons.CommonConstant;
import com.eps.dao.achievement.AnalyDao;
import com.eps.dao.achievement.ClassDao;
import com.eps.dao.achievement.LessonDao;
import com.eps.dao.achievement.StudentDao;
import com.eps.dao.achievement.TeacherDao;
import com.eps.domain.CClass;
import com.eps.domain.Student;
import com.eps.domain.Teacher;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Service
public class AchievementService {
	
	@Autowired
	private TeacherDao tdao;
	
	@Autowired
	private StudentDao sdao;
	
	@Autowired
	private ClassDao cdao;
	
	@Autowired
	private AnalyDao adao;
	
	@Autowired
	private LessonDao ldao;
	
	public Teacher getTeacher(long userId){
		return tdao.load(userId);
	}
	
	public List<CClass> getClasses(int teacherId){
		return cdao.getClassByTeacher(teacherId);
	}
	
	public List<Student> getStudent(int classId){
		return sdao.getStudentByClass(classId);
	}
	
	public List<LStrMap<Object>> getClassAnalyByClass(int testId,int classId){
		return adao.getClassAnalyByClass(testId, classId);
	}
	
	public List<LStrMap<Object>> getClassAnalyByTeacher(int testId,int teacherId){
		return adao.getClassAnalyByTeacher(testId, teacherId);
	}
	public List<LStrMap<Object>> getClassAnalyByTeacherClass(int classId,int teacherId){
		return adao.getClassAnalyByTeacherAndClass(classId, teacherId);
	}
	public List<LStrMap<Object>> getStudentScoreNotice(int testId,int teacherId,int classId,List<Integer> subjects){
		//return adao.getStudentScoreNotice(testId, teacherId, classId, pageNo);
//		String subject = ArrayUtils.toString(subjects);
//		subject = subject.substring(1, subject.length()-1);
		List<LStrMap<Object>> list = adao.getStudentScoreNotice(testId, teacherId, classId, subjects);
		LStrMap<LStrMap<Object>> temp = LStrMap.newInstance();
		List<LStrMap<Object>> result = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = null;
		double total = 0d;
		for (LStrMap<Object> item : list) {
			int userId = (Integer) item.get("user_Id");
			double score = (Double) item.get("score");
			int scoreCr = (Integer) item.get("score_cr");
			int scoreGr = (Integer) item.get("score_gr");
			int subjectId = (Integer) item.get("subject_id");
			map = temp.get(String.valueOf(userId));
			if(map == null){
				map = LStrMap.newInstance();
				//map.put("total", total);
				temp.put(String.valueOf(userId), map);
				result.add(map);
				total = 0d;
			}
			//total = (Double)map.get("total");
			total += score;
			map.put("userId", userId);
			map.put("studentName", item.get("student_name"));
			map.put("testName", item.get("test_name"));
			map.put("studentNo", item.get("student_no"));
			map.put(String.valueOf(subjectId), score+"("+ scoreCr+ "/" + scoreGr+")");
			map.put("total", total);
			map.put("scoreCr", item.get("totalcr"));
			map.put("scoreGr", item.get("totalgr"));
		}
		list = null;
		return result;
	}
	public List<LStrMap<Object>> getTestSemester(int teacherId){
		return adao.getTestSemester(teacherId);
	}
	
	public UStrMap<List<LStrMap<Object>>> getTestClassAndSubject(int teacherId){
		List<LStrMap<Object>> list = adao.getTestAndSubject(teacherId);
		List<LStrMap<Object>> tests = new ArrayList<LStrMap<Object>>();
		List<LStrMap<Object>> classes = new ArrayList<LStrMap<Object>>();
		List<LStrMap<Object>> subjects = new ArrayList<LStrMap<Object>>();
		List<String> temp1 = new ArrayList<String>();
		List<String> temp2 = new ArrayList<String>();
		List<String> temp3 = new ArrayList<String>();
		for (LStrMap<Object> item : list) {
			String testId = String.valueOf(item.get("TEST_ID"));
			String testName = String.valueOf(item.get("test_name"));
			String subjectId = String.valueOf(item.get("subject_id"));
			String subjectName = String.valueOf(item.get("SORT_NAME"));
			String classId = String.valueOf(item.get("CLASS_ID"));
			String className = String.valueOf(item.get("class_name"));
			boolean isSelected = false;
			if(temp1.equals("")){
				isSelected = true;
			}
			if(!ArrayUtils.contains(temp1.toArray(),testId)){
				LStrMap<Object> map = LStrMap.newInstance();
				map.put("id", testId);
				map.put("name", testName);
				map.put("selected", isSelected);
				tests.add(map);
				temp1.add(testId);
			}
			if(!ArrayUtils.contains(temp2.toArray(),subjectId)){
				//temp2 = subjectId;
				LStrMap<Object> map = LStrMap.newInstance();
				map.put("id", subjectId);
				map.put("name", subjectName);
				map.put("selected", true);
				subjects.add(map);
				temp2.add(subjectId);
			}
			if(!ArrayUtils.contains(temp3.toArray(),classId)){
				LStrMap<Object> map = LStrMap.newInstance();
				map.put("id", classId);
				map.put("name", className);
				map.put("selected", isSelected);
				classes.add(map);
				temp3.add(classId);
			}
		}
		UStrMap<List<LStrMap<Object>>> result = UStrMap.newInstance();
		result.put("test", tests);
		result.put("clazz", classes);
		result.put("subject", subjects);
		return result;
	}
	/**
	 * 获取某次考试某门课的班级与年级对比情况
	 * @param teacherId
	 * @param testId
	 * @param classId
	 * @param subjectId
	 * @return
	 */
	public List<LStrMap<Object>> getClassAndGradeAnaly(int teacherId,int testId,int classId, int subjectId){
		return adao.getClassAndGradeAnaly(teacherId, testId, classId, subjectId);
	}
	/**
	 * 获取老师今天有的课
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTodayLessonByTeacher(int teacherId){
		List<LStrMap<Object>> list = ldao.getTodayLessonByTeacher(teacherId);
		for (LStrMap<Object> item : list) {
			int lessonTime = (Integer)item.get("lesson_time");
			item.put("time", CommonConstant.LESSON_TIME[lessonTime-1]);
		}
		return list;
	}
	/**
	 * 获取老师课表
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getLessonByTeacher(int teacherId){
		List<LStrMap<Object>> list = ldao.getLessonByTeacher(teacherId);
		List<LStrMap<Object>> result = new ArrayList<LStrMap<Object>>();
		for(int i=0;i<10;i++){
			LStrMap<Object> item = LStrMap.newInstance();
			item.put("time", CommonConstant.LESSON_TIME[i]);
			for(int j=0;j<7;j++){
				item.put(String.valueOf(j), null);
			}
			result.add(item);
		}
		for (LStrMap<Object> lesson : list) {
			int week = (Integer) lesson.get("weekday");
			int time = (Integer) lesson.get("lesson_time");
			LStrMap<Object> item = result.get(time-1);
			item.put(String.valueOf(week), lesson);
			
			//item.put("time", CommonConstant.LESSON_TIME[time-1]);
		}
		return result;
	}
}
