package com.eps.dao.examsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.EExam;
import com.eps.domain.EExamQuestion;
import com.eps.domain.EOption;
import com.eps.domain.EQuesType;
import com.eps.domain.EQuestion;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class ExamAfterDao extends BaseDao{
	
	@Value("${examsystem.all.select.code.name}")
	private String getCodeByName;
	@Value("${examsystem.all.select.code}")
	private String getCodeBycodecate;
	@Value("${examsystem.after.quesType.save}")
	private String saveQuesType;
	@Value("${examsystem.all.quesType.select.examId}")
	private String getQuesTypeByExamid;
	@Value("${examsystem.all.exam.bcodetype.select}")
	private String getBcodeType;
	@Value("${examsystem.all.exam.scodetype.select}")
	private String getScodeType;
	@Value("${examsystem.all.exam.quesInfo.select}")
	private String getQuesInfoByExamid;
	@Value("${examsystem.after.questype.update}")
	private String updateQuesTypeById;
	@Value("${examsystem.after.questype.delete.typeid}")
	private String deleteQuesTypeById;
	@Value("${examsystem.after.examquestion.delete.typeid}")
	private String deleteExamquestionByTypeid;
	@Value("${examsystem.after.wsort.select.two}")
	private String getAllSortNoAndName;
	@Value("${examsystem.after.save.question}")
	private String saveOneQuestion;
	@Value("${examsystem.after.save.option}")
	private String saveOneOption;
	@Value("${examsystem.after.save.examquestion}")
	private String saveOneExamquestion;
	@Value("${examsystem.after.delete.examquestion}")
	private String deleteOneExamquestion;
	@Value("${examsystem.after.save.eexam}")
	private String saveOneExam;
	@Value("${examsystem.all.get.exam.examId}")
	private String getExamInfoByExamid;
	@Value("${examsystem.all.get.question.remark}")
	private String getArtRemarkByExamid;
	@Value("${examsystem.all.update.question.remark}")
	private String updateArtRemarkByQuesid;
	@Value("${examsystem.all.update.article}")
	private String updateArticle;
	@Value("${examsystem.after.delete.question.quesid}")
	private String deleteQuesionByQuesid;
	@Value("${examsystem.after.delete.option.quesid}")
	private String deleteOptionByOptid;
	@Value("${examsystem.before.update.exam.releasestatus}")
	private String updateReleasestatucByExamid;
	@Value("${examsystem.before.arrangetest.testisarranged}")
	private String getTestArrangedFlag;
	@Value("${examsystem.after.sortname.ofter}")
	private String getOfterSortName;
	@Value("${examsystem.after.update.examnum}")
	private String updateExamNum;
	@Value("${examsystem.after.update.mtopics.after.addques}")
	private String updateMTopicsAfterAddQues;
	@Value("${examsystem.after.update.questype.ordernum}")
	private String updateQuesTypeOrderNum;
	@Value("${examsystem.after.update.examquestion.one.mtopic}")
	private String updateOneQuesMTopic;
	@Value("${examsystem.after.get.allknowledgepoints.examid}")
	private String getAllKnowledgePointsByExamId;
	@Value("${examsystem.after.update.ques.kp.by.artid.kp}")
	private String updateQuestionsKnowledgePointOfArticleByArticleIdKnowledgePoint;
	@Value("${examsystem.after.update.release.status.of.questions}")
	private String updateReleaseStatusOfQuestions;
	@Value("${examsystem.after.get.question.by.quesid}")
	private String getQuestionByQuesId;
	@Value("${examsystem.after.update.ques.score.oscore}")
	private String updateQuesScoreOscore;
	@Value("${examsystem.after.update.mtopics.after.deleteques}")
	private String updateMTopicsAfterDeleteQues;
	@Value("${examsystem.after.delete.questions}")
	private String deleteQuestions;
	@Value("${examsystem.after.save.ques.kps.relation}") private String saveQuesKpsRelation;
	
	/**
	 * 获得code表的地区
	 * @param codeCate
	 * @return
	 */
	public List<LStrMap<Object>> getAreaFromCodeDao(String codeCate){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("code_cate", codeCate);
		return this.find(getCodeBycodecate,map);
	}
	
	/**
	 * 获得code表的考试类型
	 * @param codeCate
	 * @return
	 */
	public List<LStrMap<Object>> getExamTypeFromCodeDao(String codeCate){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("code_cate", codeCate);
		return this.find(getCodeBycodecate,map);
	}
	
	/**
	 * 获得code表的题目编号类型
	 * @param codeCate
	 * @return
	 */
	public List<LStrMap<Object>> getCodeTypeTypeFromCodeDao(String codeCate){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("code_cate", codeCate);
		return this.find(getCodeBycodecate,map);
	}
	
	public LStrMap<Object> getCodeByNameDao(String name,String codeCate){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("name", name);
		uStrMap.put("code_cate", codeCate);
		return this.readRecode(getCodeByName, uStrMap);
	}
	
	/**
	 * 保存题型 
	 * @param eQuesType
	 */
	public void startReadyEditExamDao(EQuesType eQuesType){
		this.excute(saveQuesType,eQuesType.toMap());
	}
	
	/**
	 * 更新题型
	 * @param eQuesType
	 */
	public void updateQuesTypeDao(EQuesType eQuesType){
		this.excute(updateQuesTypeById, eQuesType.toMap());
	}
	
	/**
	 * 删除题型表
	 * @param eQuesType
	 */
	public void deleteQuesTypeDao(EQuesType eQuesType){
		this.excute(deleteQuesTypeById, eQuesType.toMap());
	}
	
	/**
	 * 删除关系表 通过题型编号
	 * @param eQuesType
	 */
	public void deleteExamQuesByTypeid(EQuesType eQuesType){
		this.excute(deleteExamquestionByTypeid, eQuesType.toMap());
	}
	
	/**
	 * 获得试卷题型信息
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getQuesTypeDao(int examId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.find(getQuesTypeByExamid, map);
		
	}
	
	/**
	 * 获得所有试卷信息
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getAllQuesInfoDao(int examId, int testRecId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("test_rec_id", testRecId);
		return this.find(getQuesInfoByExamid, map);
	}
	
	public LStrMap<Object> getBigQtDao(int examId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.readRecode(getBcodeType, map);
	}
	
	public LStrMap<Object> getSmallQtDao(int examId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.readRecode(getScodeType, map);
	}
	
	/**
	 * 保存一条问题
	 * @param eQuestion
	 * @return
	 */
	public int saveQuestionDao(EQuestion eQuestion){
		//保存小题考点关系
		int quesId = eQuestion.getQuesId();
		List<UStrMap<Object>> kpList = new ArrayList<UStrMap<Object>>();
		for (String tempKp : eQuestion.getKnowledgePoint().split(",")) {
			if (!tempKp.equals("")) {
				UStrMap<Object> relationMap = UStrMap.newInstance();
				relationMap.put("ques_id", quesId);
				relationMap.put("kp_id", tempKp);
				kpList.add(relationMap);
			}
		}
		saveQuesKpsRelation(kpList);
		return this.excute(saveOneQuestion, eQuestion.toMap());
	}
	
	/**
	 * 保存一条选项
	 * @param eOption
	 * @return
	 */
	public int saveOptionDao(EOption eOption){
		return this.excute(saveOneOption, eOption.toMap());
	}
	
	/**
	 * 保存一条关系记录
	 * @param eExamQuestion
	 * @return
	 */
	public int saveExamQuesDao(EExamQuestion eExamQuestion){
		return this.excute(saveOneExamquestion, eExamQuestion.toMap());
	}
	
	/**
	 * 删除一条试卷小题关系
	 * @param examId 试卷编号
	 * @param quesId 小题编号
	 * @return
	 */
	public int deleteOneExamQuesRelation(int examId, int quesId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("ques_id", quesId);
		return this.excute(deleteOneExamquestion, map);
	}
	
	/**
	 * 获得己有的所有考试分类
	 * @return
	 */
	public List<LStrMap<Object>> getAllSortNoAndNameDao(){
		return this.find(getAllSortNoAndName);
	}
	
	/**
	 * 保存试卷
	 * @param eExam
	 * @return
	 */
	public int saveExamDao(EExam eExam){
		return this.excute(saveOneExam, eExam.toMap());
	}
	
	/**
	 * 根据id获得试卷信息
	 * @param examId
	 * @param testId
	 * @param classId
	 * @param subjectId
	 * @return
	 */
	public LStrMap<Object> getExamInfoByExamidDao(int examId){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("exam_id", examId);
		return this.readRecode(getExamInfoByExamid, uStrMap);
	}
	
	/**
	 * 根据id获得文章下面有哪些题目， 放于remark中的
	 * @param examId
	 * @return
	 */
	public LStrMap<Object> getArtRemarkDao(int quesId){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("ques_id", quesId);
		return this.readRecode(getArtRemarkByExamid, uStrMap);
	}
	
	/**
	 * 更新下面的题目号
	 * @param examId
	 * @param remark
	 * @return
	 */
	public int updateArtRemarkDao(int quesId,String remark){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("ques_id", quesId);
		uStrMap.put("remark", remark);
		return this.excute(updateArtRemarkByQuesid, uStrMap);
	}
	
	/**
	 * 更新材料信息，包括材料内容、材料考点、更新时间
	 * @param eQuestion
	 * @return
	 */
	public int updateArticle(EQuestion eQuestion){
		return this.excute(updateArticle, eQuestion.toMap());
	}
	
	/**
	 * 更新问题表
	 * @param quesId
	 * @return
	 */
	public int deleteQuestionDao(int quesId){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("ques_id", quesId);
		return this.excute(deleteQuesionByQuesid, uStrMap);
	}
	

	/**
	 * 更新选项表
	 * @param quesId
	 * @return
	 */
	public int deleteOptionDao(int quesId){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("ques_id", quesId);
		return this.excute(deleteOptionByOptid,uStrMap);
	}
	
	/**
	 * 发布试卷
	 * @param examId
	 * @return
	 */
	public int updateReleasestatusDao(int examId){
		UStrMap<Object> uStrMap= UStrMap.newInstance();
		uStrMap.put("exam_id", examId);
		return this.excute(updateReleasestatucByExamid,uStrMap);
	}
	
	/**
	 * 得到当前用户经常出的试卷科目
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getOfterSortNameDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getOfterSortName, map);
	}
	
	/**
	 *  更新大题小题数 
	 * @param map
	 * @return
	 */
	public int updateExamNumDao(UStrMap<Object> map){
		return this.excute(updateExamNum, map);
	}
	
	/**
	 *新增小题时，更新该小题之后所有小题的题号
	 *@param map
	 *@return
	 */
	public int updateMTopicsAfterAddQues(UStrMap<Object> map){
		return this.excute(updateMTopicsAfterAddQues, map);
	}
	
	/**
	 * 当删除大题时，其后所有大题题号都要减一
	 * @param examId
	 * @param orderNum
	 * @return
	 */
	public int updateOrderNum(long examId, int orderNum){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("order_num", orderNum);
		return this.excute(updateQuesTypeOrderNum, map);
	}
	
	/**
	 * 更新一个小题的小题号
	 * @param examId
	 * @param quesId
	 * @param MTopic
	 * @return
	 */
	public int updateOneQuesMTopic(long examId, long quesId, int MTopic){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("ques_id", quesId);
		map.put("m_topic", MTopic);
		return this.excute(updateOneQuesMTopic, map);
	}
	
	/**
	 * 得到某张刚发布的试卷是否已经安排过考试
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getTestArrangeFlag(long examId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.find(getTestArrangedFlag, map);
	}

	/**
	 * 找到某张试卷对应的科目的所有的知识点
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getAllKnowledgePoints(int examId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.find(getAllKnowledgePointsByExamId, map);
	}

	public void updateQuestionsKnowledgePointOfArticle(int articleId, String knowledgePoint) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", articleId);
		map.put("knowledge_point", knowledgePoint);
		this.excute(updateQuestionsKnowledgePointOfArticleByArticleIdKnowledgePoint, map);
	}

	public int updateReleaseStatusOfQuestions(int examId, int status) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("release_flag", status);
		return this.excute(updateReleaseStatusOfQuestions, map);
	}

	/**
	 * 根据小题编号得到小题信息
	 * @param quesId 小题编号
	 * @return
	 */
	public EQuestion getQuestionByQuesId(int quesId) {
		EQuestion question = null;
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		try {
			question = getNameParameTemplate().queryForObject(getQuestionByQuesId, map, new QuestionRowMapper());
			return question;
		} catch (Exception e) {
			return null;
		}
	}
	
	class QuestionRowMapper implements RowMapper<EQuestion>{
		public EQuestion mapRow(ResultSet rs, int arg1) throws SQLException {
			EQuestion question = new EQuestion();
			question.setCreator(rs.getString("creator"));
			question.setDifficulty(rs.getString("difficulty"));
			question.setGradeLevel(rs.getInt("grade_level"));
			question.setInputTime(rs.getString("input_time"));
			question.setKeyword(rs.getString("keyword"));
			question.setKnowledgePoint(rs.getString("knowledge_point"));
			question.setOptionNum(rs.getInt("option_num"));
			question.setQuesContent(rs.getString("ques_content"));
			question.setQuesId(rs.getInt("ques_id"));
			question.setQuesRefer(rs.getString("ques_refer"));
			question.setQuesType(rs.getString("ques_type"));
			question.setReleaseFlag(rs.getInt("release_flag"));
			question.setRelFlag(rs.getString("rel_flag"));
			question.setRemark(rs.getString("remark"));
			question.setResource(rs.getString("resource"));
			question.setShareFlag(rs.getInt("share_flag"));
			question.setSubjectNo(rs.getString("subject_no"));
			return question;
		}
	}

	public int updateQuesScoreOscore(int examId, int quesId, int score, String oScore) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("exam_id", examId);
		params.put("ques_id", quesId);
		params.put("score", score);
		params.put("o_score", oScore);
		return this.excute(updateQuesScoreOscore, params);
	}

	/**
	 * 删除试卷某个小题时，更新该小题之后的所有小题的小题号
	 * @param params
	 * @return
	 */
	public int updateMTopicsAfterDeleteQues(UStrMap<Object> params) {
		return this.excute(updateMTopicsAfterDeleteQues, params);
	}

	/**
	 * 删除多个小题
	 * @param quesIds
	 * @return
	 */
	public int deleteQuestions(List<Integer> quesIds) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_ids", quesIds);
		return this.excute(deleteQuestions, map);
	}

	/**
	 * 保存小题考点关系
	 * @param paramList 参数列表
	 */
	public void saveQuesKpsRelation(List<UStrMap<Object>> paramList) {
		this.batchUpdate(saveQuesKpsRelation, paramList);
	}
}
