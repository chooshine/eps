package com.eps.dao.network;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class ExamIntelligentlyChooseDao extends BaseDao {
	@Value("${exam.intelligentlychoose.get.knowledgepoints.in.all}") private String getKnowledgePointsInAll;
	@Value("${exam.intelligentlychoose.get.knowledgepoints.in.fav}") private String getKnowledgePointsInFav;
	@Value("${exam.intelligentlychoose.get.knowledgepoints.in.my}") private String getKnowledgePointsInMy;
	
	@Value("${exam.intelligentlychoose.get.sortnameinfo.by.subjectid}")
	private String getSortNamesInfoBySubjectid;
	@Value("${exam.intelligentlychoose.get.typeinfo.by.examid.typeid}")
	private String getTypeInfoByExamidTypeid;
	
	@Value("${exam.intelligentlychoose.get.ques.in.fav.for.exam}") private String getQuesInFavForExam;
	@Value("${exam.intelligentlychoose.get.ques.in.my.for.exam}") private String getQuesInMyForExam;
	@Value("${exam.intelligentlychoose.get.ques.in.all.for.exam}") private String getQuesInAllForExam;
	
	@Value("${exam.intelligentlychoose.get.ques.in.fav.for.hw}") private String getQuesInFavForHw;
	@Value("${exam.intelligentlychoose.get.ques.in.my.for.hw}") private String getQuesInMyForHw;
	@Value("${exam.intelligentlychoose.get.ques.in.all.for.hw}") private String getOptionalArticleQuestionsForHw;
	
	@Value("${exam.intelligentlychoose.get.opts.in.fav}") private String getOptsInFav;
	@Value("${exam.intelligentlychoose.get.opts.in.my}") private String getOptsInMy;
	@Value("${exam.intelligentlychoose.get.opts.in.all}") private String getOptsInAll;
	
	@Value("${exam.intelligentlychoose.update.mtopics.for.exam}")
	private String updateMtopicsAfterOneQuestionForExam;
	@Value("${exam.intelligentlychoose.update.mtopics.for.hw}")
	private String updateMtopicsAfterOneQuestionForHw;
	
	@Value("${exam.intelligentlychoose.get.quesinfo.of.exam}")
	private String getQuestionInfoOfExam;
	@Value("${exam.intelligentlychoose.get.quesinfo.of.hw}")
	private String getQuestionInfoOfHw;
	@Value("${exam.intelligentlychoose.delete.questions.of.exam}")
	private String deleteQuestionsByExamIdCountMtopicRemark;
	@Value("${exam.intelligentlychoose.delete.questions.of.hw}")
	private String deleteQuestionsByExamIdCountMtopicRemarkOfHw;
	
	@Value("${exam.intelligentlychoose.get.page.in.fav}") private String getPageInFav;
	@Value("${exam.intelligentlychoose.get.page.in.my}") private String getPageInMy;
	@Value("${exam.intelligentlychoose.get.page.in.all}") private String getPageInAll;
	
	/**
	 * 获取网站题库的所有题目的所有知识点
	 * @param subjectId 科目编号
	 * @param tsId 题型编号
	 * @param userId 用户编号
	 * @param keyword 关键字
	 * @return 含有符合条件的所有知识点的List
	 */
	public List<LStrMap<Object>> getKnowledgePointsInAll(int subjectId, int tsId, long userId, String keyword) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("user_id", userId);
		map.put("keyword", keyword);
		return this.find(getKnowledgePointsInAll, map);
	}
	
	/**
	 * 获取用户收藏的所有题目的所有知识点
	 * @param subjectId 科目编号
	 * @param tsId 题型编号
	 * @param userId 用户编号
	 * @param keyword 关键字
	 * @return 含有符合条件的所有知识点的List
	 */
	public List<LStrMap<Object>> getKnowledgePointsInFavoriteRange(int subjectId, int tsId, long userId, String keyword) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("user_id", userId);
		map.put("keyword", keyword);
		return this.find(getKnowledgePointsInFav, map);
	}
	
	/**
	 * 获取用户自己出的所有题目的所有知识点
	 * @param subjectId 科目编号
	 * @param tsId 题型编号
	 * @param userId 用户编号
	 * @param keyword 关键字
	 * @return 含有符合条件的所有知识点的List
	 */
	public List<LStrMap<Object>> getKnowledgePointsInMyRange(int subjectId, int tsId, long userId, String keyword) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("user_id", userId);
		map.put("keyword", keyword);
		return this.find(getKnowledgePointsInMy, map);
	}

	public List<LStrMap<Object>> getSortNamesInfo(int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		return this.find(getSortNamesInfoBySubjectid, map);
	}

	public List<LStrMap<Object>> getTypeInfo(int examId, int typeId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("type_id", typeId);
		return this.find(getTypeInfoByExamidTypeid, map);
	}

	/**
	 * 得到收藏题库所有可选题目，针对于试卷的
	 * @param examId 试卷编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题型
	 * @param kpId 知识点编号
	 * @param quesType 小题类型
	 * @param range 题库类型，0代表所有题库，1代表收藏题库，2代表我的题库
	 * @param keyword 关键字
	 * @param pageStart 本页的起始索引
	 * @param pageSize 本页的尺寸
	 * @return
	 */
	public List<LStrMap<Object>> getQuesInFavForExam(int examId, long userId, int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getQuesInFavForExam, map);
	}
	/**
	 * 得到收藏题库所有可选题目，针对于作业的
	 * @param hwId 作业编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题型
	 * @param kpId 知识点编号
	 * @param quesType 小题类型
	 * @param keyword 关键字
	 * @param pageStart 本页的起始索引
	 * @param pageSize 本页的尺寸
	 * @return
	 */
	public List<LStrMap<Object>> getQuesInFavForHw(int hwId, long userId, int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getQuesInFavForHw, map);
	}

	public List<LStrMap<Object>> getOptsInFav(int subjectId, int tsId, int kpId, long userId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("user_id", userId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getOptsInFav, map);
	}
	public List<LStrMap<Object>> getOptsInMy(int subjectId, int tsId, int kpId, long userId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("user_id", userId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getOptsInMy, map);
	}
	public List<LStrMap<Object>> getOptsInAllForExam(int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		map.put("user_id", userId);
		return this.find(getOptsInAll, map);
	}

	/**
	 * 更新试卷的某个小题之后的所有小题的小题号，不更新材料的小题号
	 * @param examId 试卷编号
	 * @param mTopic 小题号
	 * @param count 需更新的小题的个数
	 * @return
	 */
	public boolean updateMtopicsAfterOneQuestionForExam(int examId, int mTopic, int count) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("m_topic", mTopic);
		map.put("count", count);
		return this.excute(updateMtopicsAfterOneQuestionForExam, map)==1;
	}
	/**
	 * 更新作业的某个小题之后的所有小题的小题号，包括材料的小题号
	 * @param hwId 作业编号
	 * @param mTopic 小题号
	 * @param count 需更新的小题的个数
	 * @return
	 */
	public boolean updateMtopicsAfterOneQuestionForHw(int hwId, int mTopic, int count) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("m_topic", mTopic);
		map.put("count", count);
		return this.excute(updateMtopicsAfterOneQuestionForHw, map)==1;
	}
	
	/**
	 * 得到试卷中某个小题的信息
	 * @param examId 试卷编号
	 * @param quesId 小题编号
	 * @return
	 */
	public List<LStrMap<Object>> getQuestionInfoOfExam(int examId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("ques_id", quesId);
		return this.find(getQuestionInfoOfExam, map);
	}
	/**
	 * 得到作业中某个小题的信息
	 * @param hwId 作业编号
	 * @param quesId 小题编号
	 * @return
	 */
	public List<LStrMap<Object>> getQuestionInfoOfHw(int hwId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("ques_id", quesId);
		return this.find(getQuestionInfoOfHw, map);
	}
	
	/**
	 * 删除试卷中的小题
	 * @param examId 试卷编号
	 * @param count 需删除的个数
	 * @param mTopic 小题号
	 * @param remark 小题的remark，用于删除材料下小题对应的材料
	 * @return
	 */
	public boolean deleteQuestions(int examId, int count, int mTopic, String remark) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("count", count);
		map.put("m_topic", mTopic);
		map.put("remark", remark);
		return this.excute(deleteQuestionsByExamIdCountMtopicRemark, map)==1;
	}
	/**
	 * 删除作业中的小题
	 * @param hwId 试卷编号
	 * @param count 需删除的个数
	 * @param mTopic 小题号
	 * @param remark 小题的remark，用于删除材料下小题对应的材料
	 * @return
	 */
	public boolean deleteQuestions(int hwId, int count, int mTopic) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("count", count);
		map.put("m_topic", mTopic);
		return this.excute(deleteQuestionsByExamIdCountMtopicRemarkOfHw, map)==1;
	}
	
	/**
	 * 得到我的题库所有可选题目，针对于试卷的
	 * @param examId 试卷编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题型
	 * @param kpId 知识点编号
	 * @param quesType 小题类型
	 * @param range 题库类型，0代表所有题库，1代表收藏题库，2代表我的题库
	 * @param keyword 关键字
	 * @param pageStart 本页的起始索引
	 * @param pageSize 本页的尺寸
	 * @return
	 */
	public List<LStrMap<Object>> getQuesInMyForExam(int examId, long userId, int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getQuesInMyForExam, map);
	}
	/**
	 * 得到我的题库所有可选题目，针对于作业的
	 * @param hwId 作业编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题型
	 * @param kpId 知识点编号
	 * @param quesType 小题类型
	 * @param range 题库类型，0代表所有题库，1代表收藏题库，2代表我的题库
	 * @param keyword 关键字
	 * @param pageStart 本页的起始索引
	 * @param pageSize 本页的尺寸
	 * @return
	 */
	public List<LStrMap<Object>> getQuesInMyForHw(int hwId, long userId, int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getQuesInMyForHw, map);
	}
	
	/**
	 * 得到所有题库所有可选题目，针对于试卷的
	 * @param examId 试卷编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题型
	 * @param kpId 知识点编号
	 * @param quesType 小题类型
	 * @param range 题库类型，0代表所有题库，1代表收藏题库，2代表我的题库
	 * @param keyword 关键字
	 * @param pageStart 本页的起始索引
	 * @param pageSize 本页的尺寸
	 * @return
	 */
	public List<LStrMap<Object>> getQuesInAllForExam(int examId, long userId, int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getQuesInAllForExam, map);
	}
	/**
	 * 得到所有题库所有可选题目，针对于作业的
	 * @param hwId 作业编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param tsId 题型
	 * @param kpId 知识点编号
	 * @param quesType 小题类型
	 * @param range 题库类型，0代表所有题库，1代表收藏题库，2代表我的题库
	 * @param keyword 关键字
	 * @param pageStart 本页的起始索引
	 * @param pageSize 本页的尺寸
	 * @return
	 */
	public List<LStrMap<Object>> getQuesInAllForHw(int hwId, long userId, int subjectId, int tsId, int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getOptionalArticleQuestionsForHw, map);
	}

	/**
	 * 得到一页所有题库的题目
	 * @param subjectId 科目编号
	 * @param tsId 题型编号
	 * @param kpId 考点
	 * @param keyword 关键字
	 * @param pageNo 页码
	 * @param userId 用户编号
	 * @return
	 */
	public Page getPageInAll(int subjectId, int tsId, int kpId, String keyword,	int pageNo, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		return this.pageQuery(getPageInAll, pageNo, map);
	}

	/**
	 * 得到一页用户收藏的题目
	 * @param subjectId 科目编号
	 * @param tsId 题型编号
	 * @param kpId 考点
	 * @param keyword 关键字
	 * @param pageNo 页码
	 * @param userId 用户编号
	 * @return
	 */
	public Page getPageInFav(int subjectId, int tsId, int kpId, String keyword, int pageNo, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		return this.pageQuery(getPageInFav, pageNo, map);
	}

	/**
	 * 得到一页用户自己出的题目
	 * @param userId 用户编号
	 * @param subjectId 科目
	 * @param tsId 题型
	 * @param kpId 考点
	 * @param keyword 关键字
	 * @param pageNo 页码
	 * @return
	 */
	public Page getPageInMy(long userId, int subjectId, int tsId, int kpId, String keyword, int pageNo) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		return this.pageQuery(getPageInMy, pageNo, map);
	}

}
