package com.eps.dao.examsystem;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.ETestRecord;
import com.eps.domain.ETestRecordDetail;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class ExamBeforeDao extends BaseDao{
	@Value("${examsystem.before.examtype.select}")
	private String getExamType;
	
	@Value("${examsystem.before.get.examtype}")
	private String getExamTypeByAny;
	
	@Value("${examsystem.before.get.examarea}")
	private String getExamAreaByAny;
	
	@Value("${examsystem.before.get.subjectNo}")
	private String getSubjectNOByAny;
	
	@Value("${examsystem.before.get.examInfo}")
	private String getExamInfoByAny;
	
	@Value("${examsystem.before.get.answer.examId}")
	private String getAnswerByExamId;
	
	@Value("${examsystem.before.save.etestrecord}")
	private String saveTestRecord;
	
	@Value("${examsystem.before.update.etestrecord}")
	private String updateTestRecord;
	
	@Value("${examsystem.before.save.etestrecorddetail}")
	private String saveTestRecordDetail;
	
	@Value("${examsystem.before.delete.etestrecorddetail.quesId}")
	private String deleteTestRecordDetailByQuesid;
	
	@Value("${examsystem.before.get.testrecordcount}")
	private String getTestRecordCount;
	
	@Value("${examsystem.before.get.etestdetailinfo.examid}")
	private String getTestDetailInfo;
	
	@Value("${examsystem.before.exam.score}")
	private String getExamScore;
	
	@Value("${examsystem.before.update.detail.score}")
	private String updateDetailScore;
	
	@Value("${examsystem.before.delete.detail.recid}")
	private String deleteTestRecordDetailByRecId;
	
	@Value("${examsystem.before.get.examinfo.enddate}")
	private String getExamInfoAndEndTime;
	
	@Value("${examsystem.before.gettypesandquestions.testrecid}")
	private String getNetWorkStudentTypesAndQuestions;
	
	@Value("${examsystem.before.get.automarkflag.testrecid}")
	private String getAutoMarkFlagByTestRecId;
	
	@Value("${examsystem.before.get.studentscore.testrecid}")
	private String getStudentScoreByTestRecId;
	/**
	 * 获得一门考试的分类
	 * @return List
	 */
	public List getExamTypeDao(){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("sort_no", "007");
		List list=this.find(getExamType, examTypeMap);
		return list;
	}
	
	/**
	 * 试卷类型信息 如模拟题
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @param examArea2
	 * @param examType2
	 * @return
	 */
	public List<LStrMap<Object>> getExamTypeByAnyDao(String subjectNo,String examArea,String examType,String examArea2,String examType2){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("subject_no", subjectNo);
		examTypeMap.put("exam_area", examArea);
		examTypeMap.put("exam_area2", examArea2);
		examTypeMap.put("exam_type", examType);
		examTypeMap.put("exam_type2", examType2);
		
		return this.find(getExamTypeByAny, examTypeMap);
	}
	
	/**
	 * 试卷地区信息 
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @param examArea2
	 * @param examType2
	 * @return
	 */
	public List<LStrMap<Object>> getExamAreaByAnyDao(String subjectNo,String examArea,String examType,String examArea2,String examType2){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("subject_no", subjectNo);
		examTypeMap.put("exam_area", examArea);
		examTypeMap.put("exam_area2", examArea2);
		examTypeMap.put("exam_type", examType);
		examTypeMap.put("exam_type2", examType2);
		
		return this.find(getExamAreaByAny, examTypeMap);
	}
	
	/**
	 * 试卷科目信息
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @param examArea2
	 * @param examType2
	 * @return
	 */
	public List<LStrMap<Object>> getSubjectNoByAnyDao(String subjectNo,String examArea,String examType,String examArea2,String examType2){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("subject_no", subjectNo);
		examTypeMap.put("exam_area", examArea);
		examTypeMap.put("exam_area2", examArea2);
		examTypeMap.put("exam_type", examType);
		examTypeMap.put("exam_type2", examType2);
		
		return this.find(getSubjectNOByAny, examTypeMap);
	}
	
	/**
	 * 试卷信息
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @param examArea2
	 * @param examType2
	 * @return
	 */
	public List<LStrMap<Object>> getExamInfoByAnyDao(String subjectNo,String examArea,String examType,String examArea2,String examType2){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("subject_no", subjectNo);
		examTypeMap.put("exam_area", examArea);
		examTypeMap.put("exam_area2", examArea2);
		examTypeMap.put("exam_type", examType);
		examTypeMap.put("exam_type2", examType2);
		
		return this.find(getExamInfoByAny, examTypeMap);
	}
	
	/**
	 * 获取考试答案
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getAnswerByExamIdDao(int examId){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("exam_id", examId);
		return this.find(getAnswerByExamId, examTypeMap);
	}
	
	/**
	 * 保存考试记录
	 * @param eTestRecord
	 * @return
	 */
	public int saveEtestRecord(ETestRecord eTestRecord){
		return this.excute(saveTestRecord, eTestRecord.toMap());
	}
	
	/**
	 * 更新考试记录
	 * @param eTestRecord
	 * @return
	 */
	public int updateEtestRecord(ETestRecord eTestRecord){
		return this.excute(updateTestRecord, eTestRecord.toMap());
	}
	
	/**
	 * 保存考试明细记录
	 * @param eTestRecord
	 * @return
	 */
	public int saveEtestRecordDetail(ETestRecordDetail eTestRecordDetail){
		return this.excute(saveTestRecordDetail, eTestRecordDetail.toMap());
	}
	
	/**
	 * 删除原有记录
	 * @param examId
	 * @return
	 */
	public int deleteTestRecordDetail(int quesId){
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("ques_id", quesId);
		return this.excute(deleteTestRecordDetailByQuesid, examTypeMap);
	}
	
	/**
	 * 判断记录表里是否已经存在记录
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getTestRecordCountDao(int testRecId){
		UStrMap<Object> cMap = UStrMap.newInstance();
		cMap.put("test_rec_id", testRecId);
		return this.find(getTestRecordCount, cMap);
	}
	
	/**
	 * 得到选项明细信息
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getTestDetailInfoDao(int testRecId){
		UStrMap<Object> detailMap = UStrMap.newInstance();
		detailMap.put("TEST_REC_ID", testRecId);
		return this.find(getTestDetailInfo, detailMap);
	}
	
	/**
	 * 得到某次考试记录的客观题得分
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getExamScore(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getExamScore, map);
	}
	
	/**
	 * 更新某次考试考试记录明细里的得分
	 * @param testRecId
	 */
	public void updateTestRecordDetailScore(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		this.excute(updateDetailScore, map);
	}
	
	/**
	 * 删除某次考试记录的所有考试明细
	 * @param testRecId
	 */
	public void deleteTestRecDetailByRecId(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		this.excute(deleteTestRecordDetailByRecId, map);
	}
	
	/**
	 * 得到试卷信息及某次考试某班级某科考试的结束时间
	 * @param examId
	 * @param testId
	 * @param classId
	 * @param subjectId
	 * @return
	 */
	public List<LStrMap<Object>> getExamInfoAndEndTime(int examId, int testId, int classId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("test_id", testId);
		map.put("class_id", classId);
		map.put("subject_id", subjectId);
		return this.find(getExamInfoAndEndTime, map);
	}


	public List<LStrMap<Object>> getNetWorkStudentTypesAndQuestions(int testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getNetWorkStudentTypesAndQuestions, map);
	}

	//根据考试记录编号得到本次考试该科的试卷是否自动阅卷
	public List<LStrMap<Object>> getAutoMarkFlagByTestRecId(int testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getAutoMarkFlagByTestRecId, map);
	}

	/**
	 * 根据考试记录编号获得考生得分
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getStudentScoreByTestRecId(int testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getStudentScoreByTestRecId, map);
	}
	
}
