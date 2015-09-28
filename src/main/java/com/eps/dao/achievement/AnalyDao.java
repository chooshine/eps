package com.eps.dao.achievement;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class AnalyDao extends BaseDao{
	
	@Value("${analy.class.query.class}")
	private String getClassAnalyByClass;
	
	@Value("${analy.class.query.teacher}")
	private String getClassAnalyByTeacher;
	
	@Value("${analy.class.query.teacher.class}")
	private String getClassAnalyByTeacherAndClass;
	
	@Value("${analy.student.query.teacher}")
	private String getStudentScore;
	
	@Value("${test.get.semester.teacher}")
	private String getTestSemester;
	
	@Value("${test.get.subject.teacher}")
	private String getTestAndSubject;
	
	@Value("${analy.class.grade.test}")
	private String getClassAndGrade;
	/**
	 * 查询某班级某次考试各科目情况
	 * @param testId
	 * @param classId
	 * @return
	 */
	public List<LStrMap<Object>> getClassAnalyByClass(int testId,int classId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("test_id", testId);
		params.put("class_id", classId);
		return find(getClassAnalyByClass, params);
	}
	/**
	 * 查询某教师所带班级某次考试情况
	 * @param test
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getClassAnalyByTeacher(int testId,int teacherId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("test_id", testId);
		params.put("teacher_id", teacherId);
		return find(getClassAnalyByTeacher, params);
	}
	/**
	 * 查询某教师所带某班级最近一次考试情况
	 * @param test
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getClassAnalyByTeacherAndClass(int classId,int teacherId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("class_id", classId);
		params.put("teacher_id", teacherId);
		return find(getClassAnalyByTeacherAndClass, params);
	}
	/**
	 * 查询某班级某次考次学生分数
	 * @param testId
	 * @param teacherId
	 * @param classId
	 * @return
	 */
	public List<LStrMap<Object>> getStudentScoreNotice(int testId,int teacherId,int classId,List<Integer> subject){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("test_id", testId);
		params.put("teacher_id", teacherId);
		params.put("class_id", classId);
		params.put("subject", subject);
		return find(getStudentScore, params);
	}
	
	public List<LStrMap<Object>> getTestSemester(int teacherId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("teacher_id", teacherId);
		return find(getTestSemester,params);
	}
	/**
	 * 查询老师所带班级有的考试及科目
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTestAndSubject(int teacherId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("teacher_id", teacherId);
		return find(getTestAndSubject,params);
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
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("test_id", testId);
		params.put("teacher_id", teacherId);
		params.put("class_id", classId);
		params.put("subject", subjectId);
		return find(getClassAndGrade,params);
	}
}
