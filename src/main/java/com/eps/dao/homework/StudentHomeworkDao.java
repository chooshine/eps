package com.eps.dao.homework;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.domain.EHomeworkRecord;
import com.eps.domain.EHomeworkRecordDetail;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class StudentHomeworkDao extends BaseDao {

	@Value("${homework.student.get.homework.by.userid}")
	private String getHomeworksOfStudentsByUserId;
	@Value("${homework.student.get.hwrecordinfo.by.hwid.userid}")
	private String getHwRecordInfoByHwIdUserId;
	@Value("${homework.student.save.ehomeworkrecord}")
	private String saveEhomeworkRecord;
	@Value("${homework.student.delete.hwrecdetail.by.hwrecid}")
	private String deleteHwRecordDetailByHwRecId;
	@Value("${homework.student.get.answers.by.hwid}")
	private String getAnswersByHwId;
	@Value("${homework.student.save.ehomeworkrecorddetail}")
	private String saveEhomeworkRecordDetail;
	@Value("${homework.student.update.ehomeworkrecord}")
	private String updateEhomeworkRecord;
	@Value("${homework.student.get.hwfinishednum.by.hwrecid}")
	private String getHwFinishedNumByHwRecId;
	@Value("${homework.update.hwrecorddetailscore.by.hwrecid}")
	private String updateEhomeworkRecordDetailScoreByHwRecId;
	@Value("${homework.student.get.autocorrectflag.by.hwrecid}")
	private String getAutoCorrectFlagByHwRecId;
	@Value("${homework.student.update.hwrecordcorrectinfo}")
	private String updateHwRecordCorrectInfo;
	@Value("${homework.student.get.hwclssinfo.by.hwrecid}")
	private String getHwClassInfoByHwRecId;
	@Value("${homework.student.get.questionsinfo.by.hwrecid}")
	private String getQuestionsInfoByHwRecId;
	@Value("${homework.student.update.ehomeworkrecord.star.by.hwrecid}")
	private String updateEhomeworkRecordStarByHwRecId;
	@Value("${homework.student.get.hwrecord.by.hwrecid}")
	private String getHwRecordInfoByHwRecId;
	@Value("${homework.student.get.commitedhomeworks.by.userid}")
	private String getCommitedHomeworksByUserId;
	@Value("${homework.student.get.questions.by.hwid.hwrecid}")
	private String getQuestionsByHwIdHwRecId;
	@Value("${homework.student.get.questionsflag.by.hwrecid}")
	private String getQuestionsFlagByHwRecId;
	@Value("${homework.stuent.get.hwrecid.by.hwid.userid}")
	private String getHwRecordIdByHwIdUserId;
	@Value("${homework.student.get.normalquestions.by.quesid}")
	private String getNormalQuestionsByQuesId;
	@Value("${homework.student.get.favoritekps.by.userid.subjectid}")
	private String getFavoriteKnowledgePointsByUserIdSubjectId;
	@Value("${homework.student.get.favorite.normalquestions.by.userid.kpid}")
	private String getFavoriteNormalQuestionByUserIdKpId;
	@Value("${homework.student.get.options.of.favorite.normalquestion.by.userid.kpid}")
	private String getOptionsOfFavoriteNormalQuestionByUserIdKpId;
	@Value("${homework.student.get.favorite.articequestion.by.userid.kpid}")
	private String getFavoriteArticleQuestionByUserIdKpId;
	@Value("${homework.student.get.options.of.favorite.articlequestion.by.userid.kpid}")
	private String getOptionsOfFavoriteArticleQuestionByUserIdKpId;
	@Value("${homework.student.get.errorqueskps}")
	private String getErrorQuesKps;
	@Value("${homework.student.get.errorquespage}")
	private String getErrorQuesPage;
	@Value("${homework.student.get.errorquestions}")
	private String getErrorQuestions;
	@Value("${homework.student.get.options.of.errorquestions}")
	private String getOptionsOfErrorQuestions;
	
	
	public List<LStrMap<Object>> getHomeworksOfStudents(long userId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		return find(getHomeworksOfStudentsByUserId, params);
	}

	public List<LStrMap<Object>> getHwRecordInfo(int hwId, long userId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("user_id", userId);
		return find(getHwRecordInfoByHwIdUserId, params);
	}

	public int saveEhomeworkRecord(EHomeworkRecord eHomeworkRecord) {
		return this.excute(saveEhomeworkRecord, eHomeworkRecord.toMap());
	}

	/**
	 * 删除作业记录详情
	 * @param hwRecId
	 */
	public void deleteHwRecordDetail(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		this.excute(deleteHwRecordDetailByHwRecId, map);
	}

	public List<LStrMap<Object>> getAnswers(int hwId) {
		UStrMap<Object> examTypeMap = UStrMap.newInstance();
		examTypeMap.put("hw_id", hwId);
		return this.find(getAnswersByHwId, examTypeMap);
	}

	public void saveEhomeworkRecordDetail(EHomeworkRecordDetail eHomeworkRecordDetail) {
		this.excute(saveEhomeworkRecordDetail, eHomeworkRecordDetail.toMap());
	}

	public void updateEhomeworkRecord(EHomeworkRecord eHomeworkRecord) {
		this.excute(updateEhomeworkRecord, eHomeworkRecord.toMap());
	}

	public List<LStrMap<Object>> getHwFinishedNum(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getHwFinishedNumByHwRecId, map);
	}

	public void updateEhomeworkRecordDetailScore(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		this.excute(updateEhomeworkRecordDetailScoreByHwRecId, map);
	}

	public List<LStrMap<Object>> getAutoCorrectFlag(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getAutoCorrectFlagByHwRecId, map);
	}

	public void updateHwRecord(int hwRecId, int correctFlag, int creator) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("correct_flag", correctFlag);
		map.put("creator", creator);
		this.excute(updateHwRecordCorrectInfo, map);
	}

	public List<LStrMap<Object>> getHwClassInfo(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getHwClassInfoByHwRecId, map);
	}

	public List<LStrMap<Object>> getQuestionsInfo(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getQuestionsInfoByHwRecId, map);
	}

	public void updateEhomeworkRecordStar(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		this.excute(updateEhomeworkRecordStarByHwRecId, map);
	}

	public List<LStrMap<Object>> getHwRecordInfo(int hwRecId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_rec_id", hwRecId);
		return find(getHwRecordInfoByHwRecId, params);
	}

	public List<LStrMap<Object>> getCommitedHomeworks(long userId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		return find(getCommitedHomeworksByUserId, params);
	}

	public List<LStrMap<Object>> getQuestions(long hwRecId, int hwId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_rec_id", hwRecId);
		params.put("hw_id", hwId);
		return find(getQuestionsByHwIdHwRecId, params);
	}

	//得到小题的对错情况
	public List<LStrMap<Object>> getQuestionsFlag(long hwRecId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_rec_id", hwRecId);
		return find(getQuestionsFlagByHwRecId, params);
	}

	public List<LStrMap<Object>> getHwRecordId(int hwId, long userId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("user_id", userId);
		return find(getHwRecordIdByHwIdUserId, params);
	}

	public List<LStrMap<Object>> getNormalQuestions(int quesId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("ques_id", quesId);
		return find(getNormalQuestionsByQuesId, params);
	}

	public List<LStrMap<Object>> getFavoriteKnowledgePoints(long userId, int subjectId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("subject_id", subjectId);
		return find(getFavoriteKnowledgePointsByUserIdSubjectId, params);
	}

	public List<LStrMap<Object>> getFavoriteNormalQuestion(long userId, int kpId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("kp_id", kpId);
		return find(getFavoriteNormalQuestionByUserIdKpId, params);
	}

	public List<LStrMap<Object>> getOptionsOfFavoriteNormalQuestion(long userId, int kpId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("kp_id", kpId);
		return find(getOptionsOfFavoriteNormalQuestionByUserIdKpId, params);
	}

	public List<LStrMap<Object>> getFavoriteArticleQuestion(long userId, int kpId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("kp_id", kpId);
		return find(getFavoriteArticleQuestionByUserIdKpId, params);
	}

	public List<LStrMap<Object>> getOptionsOfFavoriteArticleQuestion(long userId, int kpId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("kp_id", kpId);
		return find(getOptionsOfFavoriteArticleQuestionByUserIdKpId, params);
	}

	/**
	 * 获取错题集的知识点数据
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 大题类型
	 * @param keyword 关键字
	 * @return 知识点数据List
	 */
	public List<LStrMap<Object>> getErrorQuesKps(long userId, int subjectId, int tsId, String keyword) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("subject_id", subjectId);
		params.put("ts_id", tsId);
		params.put("keyword", keyword);
		return find(getErrorQuesKps, params);
	}

	/**
	 * 得到错题集小题的分页数据
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题目类型
	 * @param keyword 关键字
	 * @param kpId 知识点编号
	 * @param pageNo 页码
	 * @return 错题集分页数据，如果没有，则返回null
	 */
	public Page getErrorQuesPage(long userId, int subjectId, int tsId, String keyword, int kpId, int pageNo) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("subject_id", subjectId);
		params.put("ts_id", tsId);
		params.put("kp_id", kpId);
		params.put("keyword", keyword);
		return pageQuery(getErrorQuesPage, pageNo, params);
	}

	/**
	 * 
	 * 获取错题集小题
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题目类型
	 * @param keyword 题干中的关键字
	 * @param kpId 知识点编号
	 * @param pageStart 页的开始位置
	 * @param pageSize 一页记录的长度
	 * @return 一页的错题集小题
	 */
	public List<LStrMap<Object>> getErrorQuestions(long userId, int subjectId,
			int tsId, String keyword, int kpId, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getErrorQuestions, map);
	}

	/**
	 * 获取某页错题的所有选项
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题目类型
	 * @param keyword 题干关键字
	 * @param kpId 知识点编号
	 * @param pageStart 页的起始位置
	 * @param pageSize 一页记录的长度 
	 * @return
	 */
	public List<LStrMap<Object>> getOptionsOfErrorQuestions(long userId, int subjectId, int tsId, 
			String keyword, int kpId, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getOptionsOfErrorQuestions, map);
	}

}
