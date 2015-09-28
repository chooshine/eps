package com.eps.dao.scorerecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class ScoreRecordDao extends BaseDao {

	@Value("${inforecord.etest.create}")
	private String createETest;
	
	@Value("${inforecord.eexam.create}")
	private String createEExam;
	
	@Value("${inforecord.grade.exam}")
	private String getExam;
	
	@Value("${inforecord.teacher.class.all}")
	private String getAllClass;
	
	@Value("${inforecord.get.grade.school}")
	private String getGradeSchool;
	
	@Value("${scorerecord.teacher.classes}")
	private String getClasses;
	
	@Value("${scorerecord.teacher.sorts}")
	private String getSorts;
	
	@Value("${scorerecord.grade.test.closed}")
	private String getClosedTest;
	
	@Value("${score.get.all.student.classid}")
	private String getAllStudentByClassId;
	
	@Value("${score.insert.testscore}")
	private String insertTestScore;
	
	@Value("${score.update.score}")
	private String updateScore;
	
	@Value("${score.get.all.grade.userid}")
	private String getAllGradeByUserId;
	
	/**
	 * 创建考试
	 * @param map
	 */
	public void createTest(UStrMap<Object> map){
		this.excute(createETest, map);
	}
	
	/**
	 * 创建试卷
	 * @param map
	 */
	public void createExam(UStrMap<Object> map){
		this.excute(createEExam, map);
	}
	
	/**
	 * 得到该老师所在学校的所有年级
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getAllGradeByUserId(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id",userId);
		return this.find(getAllGradeByUserId, map);
	}
	/**
	 * 得到该老师所带的所有班级
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getClasses(UStrMap<Object> map){
		return this.find(getClasses, map);
	}
	
	/**
	 * 得到该老师所教的所有科目
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getSorts(UStrMap<Object> map){
		return this.find(getSorts,map);
	}
	
	/**
	 * 得到该老师所在年级的最近一次考试名称
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getClosedTest(UStrMap<Object> map){
		return this.find(getClosedTest, map);
	}
	
	/**
	 * 得到该科目的试卷
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getExam(UStrMap<Object> map){
		return this.find(getExam, map);
	}
	
	/**
	 * 得到所有的班级
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getAllClass(UStrMap<Object> map){
		return this.find(getAllClass, map);
	}

	public List<LStrMap<Object>> getGradeSchoolByClassId(UStrMap<Object> map) {
		return this.find(getGradeSchool,map);
	}
	
	/**
	 * 得到某个班所有学生的学号和名字
	 * @param classId
	 * @return
	 */
	public List<LStrMap<Object>> getAllStudentByClassId(long classId, long testId, long subjectId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("class_id", classId);
		map.put("test_id", testId);
		map.put("subject_id", subjectId);
		return this.find(getAllStudentByClassId, map);
	}
	
	public int insertTestScore(UStrMap<Object> map) {
		return this.excute(insertTestScore, map);
	}
	
	public int updateScore(UStrMap<Object> map) {
		return this.excute(updateScore, map);
	}
 }
