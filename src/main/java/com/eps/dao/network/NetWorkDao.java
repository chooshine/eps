package com.eps.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.EExam;
import com.eps.domain.ETest;
import com.eps.domain.ETestEexam;
import com.eps.domain.ExamPreviewSets;
import com.eps.domain.ques.Exam;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class NetWorkDao extends BaseDao{

	@Value("${teacher.user.byuserId}") private String getTeacherAndStudent;
	@Value("${exam.get.all}") private String getExam;
	@Value("${test.get.byuser}") private String getTest;
	@Value("${test.get.byExamId}") private String getTests;
	@Value("${network.save.eexam}") private String saveExam;
	@Value("${network.test.insert}") private String insertTest;
	@Value("${greate.all.byteacher}") private String getGradeByTeacher;
	@Value("${network.getTestTypes}") private String getTestTypes;
	@Value("${student.user.byuserId}") private String getstudentByuserId;
	@Value("${network.eTtestExam.insert}") private String insertTestExam;
	@Value("${network.delete.testexamrelation.testid.examid}") private String deleteTestExamRelation;
	@Value("${examsystem.after.delete.article.questions}") private String deleteArticleQuestions;
	@Value("${examsystem.after.update.article.after.mtopic}") private String updateArticleAfterMTopic;
	@Value("${teacher.get.SubByTeach}") private String getTeachsByUserid;
	@Value("${viewexam.get.commited.info}") private String getCommitedExam;
	@Value("${viewexam.update.ordernum}") private String updateOrderNum;
	@Value("${viewexam.get.examinfo.examid}")
	private String geExamInfoByExamId;
	@Value("${viewexam.update.examinfo.examid}")
	private String updateExamInfoByExamId;
	@Value("${examsystem.before.getknowledgepoints.parentid}")
	private String getKnowledgePointsByParentId;
	@Value("${examsystem.before.get.knowledgepoints.by.subjectid}")
	private String getOneLayerKnowledgePointsBySubjectId;
	@Value("${network.createexam.get.allgrades}")
	private String getAllGrades;
	@Value("${network.createexam.get.grade}")
	private String getGradeByUserIdSubjectId;
	@Value("${network.editexam.update.normal.mtopic}")
	private String updateNormalQueMTopic;
	@Value("${network.editexam.update.art.mtopic}")
	private String updateArtQueMTopic;
	@Value("${network.get.exam.previewsets}") private String getExamPreviewSets;
	@Value("${network.update.exam.previewsets}") private String updateExamPreviewSets;
	@Value("${network.insert.exam.previewsets}") private String insertExamPreviewSets;
	@Value("${network.get.typesubjects.by.subjectid}") private String getTypeSubjects;
	
	@Value("${network.update.prev.ques.mtopic}") private String updatePrevQuestionsMTopic;
	@Value("${network.update.ques.mtopic}") private String updateQuestionMTopic;
	@Value("${network.update.next.ques.mtopic}") private String updateNextQuestionsMTopic;
	@Value("${network.get.kps.by.subjectid}") private String getKnowledgePointsBySubjectId;
	@Value("${network.save.questype}") private String saveQuesType;
	@Value("${network.get.random.ques.in.kpsrange}") private String getRandomQuesInKpsRange;
	@Value("${examsystem.after.save.examquestion}") private String saveExamQuesRelation;
	@Value("${network.get.quesids.of.exam}") private String getQuesIdsOfExam;
	@Value("${network.get.lastmtopic.of.exam}") private String getLastMTopic;
	@Value("${network.update.topicsnum.of.exam}") private String updateExamMTopicNum;
	@Value("${network.get.options.of.exam}") private String getOptionsOfExam;
	@Value("${network.get.questions.of.exam}") private String getQuestionsOfExam;
	@Value("${network.get.bigquestions.of.exam}") private String getBigQuestionsOfExam;
	
	/**
	 * 通过用户编号判断教师所在学校是否已授权
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getUserFromTeaAndStu(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getTeacherAndStudent, params);
		
	}
	
	/**
	 * 通过用户编号判断学生所在学校是否已经授权
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getstudentByuserId(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getstudentByuserId, params);
		
	}
	
	
	/**
	 * 得到老师的所有试卷数量以及共计小题数目和未发布的试卷数目
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherAllNewWork(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getExam, params);
		
	}
	
	/**
	 * 得到用户作为老师所在学校和年级的考试
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getTestsByUser(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getTest, params);
	}
	
	public List<LStrMap<Object>> getTestsExamId(long user_id,int examId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		params.put("exam_id", examId);
		return find(getTests, params);
	}
	
	/**
	 * 得到用户作为老师所在学校和年级6个月内考试
	 * @param user_id
	 * @return
	 */
	public int insertTest(ETest etest){
		return this.excute(insertTest, etest.toMap());
	}
	 
	/**
	 * 获得考试类型
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getTestTypes(String code_cate){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("CODE_CATE", code_cate);
		return find(getTestTypes, params);
	}
	
	//创建试卷时获得老师已授权的科目
	public List<LStrMap<Object>> getTeachsOfTeacher(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getTeachsByUserid, params);
	}
	
	/**
	 * 保存试卷
	 * @param eExam
	 * @return
	 */
	public int saveExamDao(EExam eExam){
		return this.excute(saveExam, eExam.toMap());
	}
	
	/**
	 * 保存试卷和考试的关系
	 * @param eExam
	 * @return
	 */
	public int saveTestExamDao(ETestEexam ete){
		return this.excute(insertTestExam, ete.toMap());
	}
	
	/**
	 * 获得老师所教的年级
	 * @param teacher_id
	 * @return
	 */
	public List<LStrMap<Object>> getGrades(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getGradeByTeacher, params);
	}
	
	/**
	 * 删除某篇文章下的所有小题及文章
	 * @param examId
	 * @param quesIds
	 */
	public void deleteArticleQuestions(long examId, List<Integer> quesIds) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("ques_id", quesIds);
		this.excute(deleteArticleQuestions, params);
	}
	
	/**
	 * 更新某篇文章所有小题之后的小题的题号
	 * @param examId
	 * @param mTopic
	 * @param mTopicNum
	 */
	public void updateArticleAfterMTopic(long examId, int mTopic, int mTopicNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("m_topic", mTopic);
		params.put("m_topic_num", mTopicNum);
		this.excute(updateArticleAfterMTopic, params);
	}
	

	/**
	 * 得到学生提交过的试卷
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getCommitedExam(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getCommitedExam, map);
	}

	/**
	 * 更新大题的大题号
	 * @param typeId
	 * @param orderNum
	 * @return
	 */
	public int updateOrderNum(long typeId, int orderNum) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("type_id", typeId);
		map.put("order_num", orderNum);
		return this.excute(updateOrderNum, map);
	}
	
	/**
	 * 得到试卷基本信息
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getExamInfoByExamId(long examId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return find(geExamInfoByExamId,map);
	}
	
	/**
	 * 更新试卷基本信息
	 * @param examId
	 * @return
	 */
	public int updateExamInfoByExamId(EExam exam) {
		return excute(updateExamInfoByExamId,exam.toMap());
	}

	//删除一条考试-试卷关系
	public int deleteTestExamRelation(int testId, int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("test_id", testId);
		params.put("exam_id", examId);
		return excute(deleteTestExamRelation, params);
	}

	/**
	 * 根据上级知识点的编号得到下级知识点
	 * @param examId
	 * @param parentPkId
	 * @return
	 */
	public List<LStrMap<Object>> getKnowledgePointsByParentId(int parentKpId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("p_kp_id", parentKpId);
		return this.find(getKnowledgePointsByParentId, params);
	}

	public List<LStrMap<Object>> getOneLayerKnowledgePointsBySubjectId(int subjectId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("subject_id", subjectId);
		return this.find(getOneLayerKnowledgePointsBySubjectId, params);
	}

	public List<LStrMap<Object>> getAllGrades() {
		return this.find(getAllGrades);
	}

	public List<LStrMap<Object>> getGrade(long userId, int subjectId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("subject_id", subjectId);
		return this.find(getGradeByUserIdSubjectId, params);
	}

	/**
	 * 更新试卷普通小题的小题号
	 * @param examId 试卷编号
	 */
	public void updateNormalQueMTopic(long examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		this.excute(updateNormalQueMTopic, params);
	}
	/**
	 * 更新试卷材料的小题号
	 * @param examId 试卷编号
	 */
	public void updateArtQuesMTopic(long examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		this.excute(updateArtQueMTopic, params);
	}

	/**
	 * 返回试卷的预览设置信息，包括装订线的显示标记、保密标记的显示标记等
	 * @param examId 试卷编号
	 * @return 返回试卷的预览设置信息
	 */
	public List<LStrMap<Object>> getExamPreviewSets(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		return this.find(getExamPreviewSets, params);
	}

	/**
	 * 更新一个试卷的设置信息
	 * @param eps 设置信息
	 */
	public void updateExamPreviewSets(ExamPreviewSets eps) {
		this.excute(updateExamPreviewSets, eps.toMap());
	}

	/**
	 * 插入一个试卷预览设置记录
	 * @param examId 试卷编号
	 */
	public void saveExamPreviewSets(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		this.excute(insertExamPreviewSets, params);
	}

	/**
	 * 获得某科目的所有题目类型
	 * @param subjectId	科目编号
	 * @return	包含该科目的所有题目类型的一个list
	 */
	public List<LStrMap<Object>> getTypeSubjects(int subjectId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("subject_id", subjectId);
		return this.find(getTypeSubjects, params);
	}

	public void updatePrevQuestionsMTopic(int examId, int quesId, int moveNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("ques_id", quesId);
		params.put("move_num", moveNum);
		this.excute(updatePrevQuestionsMTopic, params);
	}

	public void updateQuestionMTopic(int examId, int quesId, int moveNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("ques_id", quesId);
		params.put("move_num", moveNum);
		this.excute(updateQuestionMTopic, params);
	}

	public void updateNextQuestionsMTopic(int examId, int quesId, int moveNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("ques_id", quesId);
		params.put("move_num", moveNum);
		this.excute(updateNextQuestionsMTopic, params);
	}

	public List<LStrMap<Object>> getKnowledgePointsBySubjectId(int subjectId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("subject_id", subjectId);
		return this.find(getKnowledgePointsBySubjectId, params);
	}

	public void saveQuesType(int examId, int subjectId, int typeId, int tsId, int orderNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("subject_id", subjectId);
		params.put("type_id", typeId);
		params.put("ts_id", tsId);
		params.put("order_num", orderNum);
		this.excute(saveQuesType, params);
	}

	public List<LStrMap<Object>> getRandomques(int tsId, List<String> kpList, int quesNum, List<Integer> usedQues, float diff) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("ts_id", tsId);
		params.put("kp_id", kpList);
		params.put("ques_num", quesNum);
		params.put("ques_id", usedQues);
		params.put("difficulty", diff);
		return this.find(getRandomQuesInKpsRange, params);
	}

	public void batchInsertExamQuestionRelation(List<UStrMap<Object>> paramList) {
		this.batchUpdate(saveExamQuesRelation, paramList);
	}

	/**
	 * 获取试卷的所有小题的小题编号
	 * @param examId 试卷编号
	 * @return
	 */
	public List<LStrMap<Object>> getQuesIdsOfExam(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		return this.find(getQuesIdsOfExam, params);
	}

	/**
	 * 获取试卷中大题号<=当前大题的大题的最后一个小题的小题号lastMTopic
	 * @param examId 试卷编号
	 * @param orderNum 当前大题号
	 * @return
	 */
	public List<LStrMap<Object>> getLastMTopic(int examId, int orderNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("order_num", orderNum);
		return this.find(getLastMTopic, params);
	}

	/**
	 * 更新试卷的小题数
	 * @param examId 试卷编号
	 */
	public void updateExamMTopicNum(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		this.excute(updateExamMTopicNum, params);
	}

	/**
	 * 得到试卷的所有选项
	 * @param examId
	 */
	public List<LStrMap<Object>> getOptionsOfExam(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		return this.find(getOptionsOfExam, params);
	}

	/**
	 * 得到试卷的所有小题
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getQuestionsOfExam(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		return this.find(getQuestionsOfExam, params);
	}
	
	/**
	 * 得到试卷的所有大题
	 * @param examId
	 */
	public List<LStrMap<Object>> getBigQuestionsOfExam(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		return this.find(getBigQuestionsOfExam, params);
	}

	public Exam getExam(int examId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		return getNameParameTemplate().queryForObject(geExamInfoByExamId, params, new RowMapper<Exam>() {
			@Override
			public Exam mapRow(ResultSet rs, int arg1) throws SQLException {
				Exam exam = new Exam();
				exam.setId(rs.getInt("exam_id"));
				exam.setName(rs.getString("exam_name"));
				exam.setExamType(rs.getInt("exam_type"));
				exam.setExamArea(rs.getInt("exam_area"));
				exam.setSubjectNo(rs.getInt("subject_no"));
				exam.setYear(rs.getInt("year"));
				exam.setTotalScore(rs.getInt("total"));
				exam.setPassScore(rs.getInt("pass_score"));
				exam.setBigQuesNum(rs.getInt("b_topic_num"));
				exam.setQuestionNum(rs.getInt("m_topic_num"));
				exam.seteStatus(rs.getInt("e_status"));
				exam.setbCodes(rs.getString("b_codes").split(","));
				exam.setmCodes(rs.getString("m_codes").split(","));
				exam.setqRandom(rs.getInt("q_random"));
				exam.setoRandom(rs.getInt("o_random"));
				return exam;
			}
		});
	}
	
}
