package com.eps.dao.network;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class PersonalWharehouseDao extends BaseDao {

	@Value("${personalwharehouse.get.kps}") private String getKnowledgepoints;
	@Value("${personalwharehouse.get.page}") private String getPage;
	@Value("${personalwharehouse.get.ques}") private String getQuestions;
	@Value("${personalwharehouse.get.opts}") private String getOptions;
	@Value("${personalwharehouse.update.quesflag}") private String updateQuesFlag;
	@Value("${personalwharehouse.get.relation.existflag}") private String getRelationExistFlag;
	@Value("${personalwharehouse.update.article.flag}") private String updateArticleFlag;
	@Value("${personalwharehouse.delete.article.questions}") private String deleteArticle;
	@Value("${personalwharehouse.update.article.questions.resource.gradelevel}") private String updateArticleQuestions;
	@Value("${personalwharehouse.get.relation.of.ques.and.exam.or.hw}") private String getRelationOfQuesAndExamOrHw;

	public List<LStrMap<Object>> getKnowledgepoints(long userId, int subjectId, int tsId, int releaseFlag, int shareFlag, String keyword) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("release_flag", releaseFlag);
		map.put("share_flag", shareFlag);
		map.put("keyword", keyword);
		return this.find(getKnowledgepoints, map);
	}

	public Page getPage(long userId, int subjectId, int tsId, int releaseFlag, int shareFlag, int kpId, String keyword, int pageNo) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("release_flag", releaseFlag);
		map.put("share_flag", shareFlag);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		return this.pageQuery(getPage, pageNo, map);
	}

	public List<LStrMap<Object>> getQuestions(long userId, int subjectId, int tsId, int releaseFlag, int shareFlag, int kpId,
			String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("release_flag", releaseFlag);
		map.put("share_flag", shareFlag);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getQuestions, map);
	}

	public List<LStrMap<Object>> getOptions(long userId, int subjectId, int tsId, int releaseFlag, int shareFlag,
			int kpId, String keyword, int pageStart, int pageSize) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		map.put("ts_id", tsId);
		map.put("release_flag", releaseFlag);
		map.put("share_flag", shareFlag);
		map.put("kp_id", kpId);
		map.put("keyword", keyword);
		map.put("page_start", pageStart);
		map.put("page_size", pageSize);
		return this.find(getOptions, map);
	}

	public int updateQuesFlag(int quesId, int releaseFlag, int shareFlag) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		map.put("release_flag", releaseFlag);
		map.put("share_flag", shareFlag);
		return this.excute(updateQuesFlag, map);
	}

	/**
	 * 判断当前小题是否存在与作业或试卷的关系，如果存在，则返回1,否则返回0
	 * @param quesId
	 * @return
	 */
	public List<LStrMap<Object>> getRelationExistFlag(int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		return this.find(getRelationExistFlag, map);
	}

	/**
	 * 更新材料题的状态，同时修改材料及材料小题的状态
	 * @param artId 材料编号
	 * @param releaseFlag 发布标记
	 * @param shareFlag 共享标记
	 */
	public void updateArticleFlag(int artId, int releaseFlag, int shareFlag) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", artId);
		map.put("release_flag", releaseFlag);
		map.put("share_flag", shareFlag);
		this.excute(updateArticleFlag, map);
	}

	/**
	 * 删除材料题的材料及所有小题
	 * @param quesId 材料的编号
	 */
	public void deleteArticle(int artId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", artId);
		this.excute(deleteArticle, map);
	}

	/**
	 * 更新一个材料的所有小题的出处和年级
	 * @param resource 出处
	 * @param gradeLevel 年级
	 */
	public void updateArticleQuestions(int artId, String resource, String gradeLevel) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", artId);
		map.put("resource", resource);
		map.put("grade_level", gradeLevel);
		this.excute(updateArticleQuestions, map);
	}

	/**
	 * 得到小题与试卷或作业的关系
	 * @param quesId 小题编号
	 * @return 含有关系的List
	 */
	public List<LStrMap<Object>> getRelation(int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		return this.find(getRelationOfQuesAndExamOrHw, map);
	}
}
