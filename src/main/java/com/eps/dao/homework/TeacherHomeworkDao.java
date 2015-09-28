package com.eps.dao.homework;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.EHomework;
import com.eps.domain.EHomeworkClass;
import com.eps.domain.EHomeworkQuestion;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class TeacherHomeworkDao extends BaseDao {

	@Value("${homework.insert.ehomeworkquestion}")
	private String saveEHomeworkQues;
	@Value("${homework.insert.ehomework}")
	private String saveEHomework;
	@Value("${homework.update.ehomework}")
	private String updateEHomework;
	@Value("${homework.get.hwinfo.hwid}")
	private String getHwInfoByHwId;
	@Value("${homework.get.all.hwinfo.hwid.hwrecid}")
	private String getAllQuesInfoByHwId;
	@Value("${homework.update.mtopics}")
	private String updateMTopics;
	@Value("${homework.delete.articleandquestions}")
	private String deleteArticleAndQuestionsInHw;
	@Value("${homework.update.mtopics.afterarticle}")
	private String updateMTopicAfterArticle;
	@Value("${homework.update.topicnum.hwid}")
	private String updateHomeworkTopicNum;
	@Value("${homework.get.questionIds.by.hwid}")
	private String getHwQuestionIdsByHwId;
	@Value("${homework.delete.hwques}")
	private String deleteOneHwQues;
	@Value("${homework.teacher.update.homeworkname.by.hwid.hwname}")
	private String updateHomeworkNameByHwIdHwName;
	
	@Value("${homework.update.question.mtopic}")
	private String updateQuestionMTopic;
	@Value("${homework.update.prev.questions.mtopic}")
	private String updatePrevQuestionsMTopic;
	@Value("${homework.update.next.questions.mtopic}")
	private String updateNextQuestionsMTopic;
	@Value("${howework.get.knowledgepoints.by.hwid}")
	private String getKnowledgePointsByHwId;
	@Value("${homework.get.hwautocorrectflag.hwid}")
	private String getHwAutoCorrectFlagByHwId;
	@Value("${homework.get.classlist.hwid}")
	private String getClassList;
	@Value("${homework.save.ehomeworkclass}")
	private String saveHomeworkClass;
	@Value("${homework.get.settleclassnames.by.hwid.creator}")
	private String getSettleClassNameListByHwidCreator;
	@Value("${homework.delete.hwclass.by.hwidclassid}")
	private String deleteHwClassByHwIdClassId;
	@Value("${homework.update.ehomework.releaseinfo}")
	private String updateHomeworkToRelease;
	
	@Value("${homework.get.hwstatistic.by.userid}")
	private String getHwStatisticByUserId;
	@Value("${homework.get.unsettle.homeworks}")
	private String getUnSettleHomeworks;
	@Value("${homework.update.homework.todeletedstatus}")
	private String updateHomeworkToDeletedStatus;
	@Value("${homework.update.questionssubjectno.by.hwid.subjectno}")
	private String updateHomeworkQuestionsSubjectNo;
	@Value("${homework.get.homework.by.userid.subjectid.releasetime}")
	private String getHomeworksOfTeacher;
	@Value("${homework.update.homework.shareflag.by.hwid}")
	private String setHwShareFlag;
	@Value("${homework.get.sharedhomeworks}")
	private String searchSharedHomeworks;
	@Value("${homework.get.hwquesrelation.by.hwid}")
	private String getHwQuesRelationListByHwId;
	
	@Value("${homework.teacher.get.pasthomeworks.by.userid}")
	private String getPastHomeworksByUserId;
	@Value("${homework.teacher.get.correctinfo.by.hwid.classid}")
	private String getCorrectInfoOfOneClassByHwIdClassId;
	@Value("${homework.teacher.get.questionsinfo.by.hwrecid}")
	private String getQuestionsInfoByHwRecId;
	@Value("${homework.teacher.get.correctnum.by.hwrecid}")
	private String getCorrectNumByHwRecId;
	@Value("${homework.teacher.get.studenthomework}")
	private String getStudentHomeworkInfoByHwRecIdHwId;
	@Value("${homework.teacher.update.homeworkrecorddetailinfo}")
	private String updateEHomeworkRecordDetailInfo;
	@Value("${homework.teacher.update.homeworkrecord.correctflag}")
	private String updateHomeworkRecordCorrectFlag;
	@Value("${homework.teacher.get.knowledgepoints.by.subjectid}")
	private String getKnowledgePointsBySubjectId;
	
	@Value("${homework.teacher.get.studentinfo.by.hwrecid}")
	private String getStudentInfoByHwRecId;
	@Value("${homework.teacher.get.class.unmarkedinfo.by.hwrecid}")
	private String getClassUnmarkedInfoByHwRecId;
	@Value("${homework.teacher.get.nexthomework.by.hwrecid}")
	private String getNextHomeworkByHwRecId;
	
	@Value("${homework.teacher.get.teacherid.by.userid}")
	private String getTeacherIdByUserId;
	@Value("${homework.teacher.get.studentid.by.userid}")
	private String getStudentIdByUserId;
	@Value("${homework.teacher.delete.unstartedhwclass.by.hwid}")
	private String clearUnStartedHomeworkArrangesByHwId;
	@Value("${homework.teacher.get.class.basiccorrectinfo.by.hwid.classid}")
	private String getClassBasicCorrectInfoByHwIdClassId;
	
	@Value("${homework.teacher.get.uncommit.students.by.hwid.classid}")
	private String getUnCommitStudentsByHwIdClassId;
	@Value("${homework.teacher.get.studentsnum.of.star.by.hwid.classid}") private String getStudentsNumOfStarByHwIdClassId;
	@Value("${homework.teacher.get.students.of.star.by.hwid.classid}") private String getStudentsOfStarByHwIdClassId;
	@Value("${homework.teacher.get.studentsnum.of.questions.by.hwid.classid}") private String getStudentsNumOfQuestionsByHwIdClassId;
	@Value("${homework.teacher.get.errorstudents.of.questions.by.hwid.classid}") private String getErrorStudentsOfQuestionsByHwIdClassId;
	@Value("${homework.teacher.get.num.of.options.by.hwid.classid}") private String getNumOfOptionsByHwIdClassId;
	@Value("${homework.teacher.get.stunum.in.area.by.hwid.classid}") private String getStuNumInAreaByHwIdClassId;
	@Value("${homework.teacher.get.homeworkinfo.by.hwid}") private String getHomeworkInfoByHwId;
	@Value("${homework.teacher.get.students.of.options.by.hwid.classid}") private String getStudentsOfOptionsByHwIdClassId;
	@Value("${homework.teacher.get.classnum.by.classid}") private String getClassNumByClassId;
	@Value("${homework.teacher.get.rightnum.of.question.by.hwid.classid.quesid}") private String getRightNumOfQuestionByHwIdClassIdQuesId;
	@Value("${homework.teacher.get.students.of.question.right.by.hwid.classid.quesid}") private String getStudentsOfQuestionRightByHwIdClassIdQuesId;
	@Value("${homework.teacher.get.students.of.question.error.by.hwid.classid.quesid}") private String getStudentsOfQuestionErrorByHwIdClassIdQuesId;
	@Value("${homework.teacher.get.studentsnum.of.options.by.hwid.classid.quesid}") private String getStudentsNumOfOptionsByHwIdClassIdQuesId;
	@Value("${homework.teacher.get.studentsname.of.options.by.hwid.classid.quesid}") private String getStusNameOfOptionsByHwIdClassIdQuesId;
	@Value("${homework.teacher.update.releaseflag.of.ques}") private String updateQuestionsReleaseStatusOfHomework;
	@Value("${homework.teacher.update.recorddetail.commentrec}") private String updateHomeworkRecordDetailCommentRec;
	@Value("${homework.teacher.update.record.commentrec}") private String updateHomeworkRecordCommentRec;
	@Value("${homework.teacher.update.quesrec}") private String updateQuesRec;
	@Value("${homework.get.quesids.of.hw}") private String getQuesIdsOfHw;
	@Value("${homework.get.last.mtopic.of.hw}") private String getLastMTopicNumOfHw;
	
	
	public void saveHomeworkQuestion(EHomeworkQuestion eHomeworkQuestion) {
		this.excute(saveEHomeworkQues, eHomeworkQuestion.toMap());
	}

	public void saveHomework(EHomework eHomework) {
		this.excute(saveEHomework, eHomework.toMap());
	}

	public void updateHomework(EHomework eHomework) {
		this.excute(updateEHomework, eHomework.toMap());
	}

	public List<LStrMap<Object>> getHwInfoByHwId(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getHwInfoByHwId, map);
	}

	public List<LStrMap<Object>> getAllQuesInfoByHwId(int hwId, int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("hw_rec_id", hwRecId);
		return this.find(getAllQuesInfoByHwId, map);
	}

	/**
	 * 新增或者删除小题后，更新其后的小题号，包括文章的小题号
	 * @param map
	 */
	public void updateMTopics(UStrMap<Object> map) {
		this.excute(updateMTopics, map);
	}

	/**
	 * 删除某篇文章下的所有小题及文章自身与作业的关系
	 * @param examId
	 * @param quesIds
	 */
	public void deleteArticleAndQuestionsInHw(int hwId, List<Integer> quesIds) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("ques_id", quesIds);
		this.excute(deleteArticleAndQuestionsInHw, params);
	}

	/**
	 * 更新某篇文章所有小题之后的小题的题号
	 * @param examId
	 * @param mTopic
	 * @param mTopicNum
	 */
	public void updateMTopicAfterArticle(int hwId, int mTopic, int mTopicNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("m_topic", mTopic);
		params.put("m_topic_num", mTopicNum);
		this.excute(updateMTopicAfterArticle, params);
	}

	public void updateTopicNumByHwId(int hwId, int addNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("add_num", addNum);
		this.excute(updateHomeworkTopicNum, params);
	}

	public void updateQuestionMTopic(int hwId, int quesId, int moveNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("ques_id", quesId);
		params.put("move_num", moveNum);
		this.excute(updateQuestionMTopic, params);	}

	public void updatePrevQuestionsMTopic(int hwId, int quesId, int moveNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("ques_id", quesId);
		params.put("move_num", moveNum);
		this.excute(updatePrevQuestionsMTopic, params);
	}

	public void updateNextQuestionsMTopic(int hwId, int quesId, int moveNum) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		params.put("ques_id", quesId);
		params.put("move_num", moveNum);
		this.excute(updateNextQuestionsMTopic, params);
	}

	public List<LStrMap<Object>> getKnowledgePointsByHwId(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getKnowledgePointsByHwId, map);
	}

	public List<LStrMap<Object>> getHwAutoCorrectFlag(int hwId, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("user_id", userId);
		return this.find(getHwAutoCorrectFlagByHwId, map);
	}

	public List<LStrMap<Object>> getClassList(int hwId, int subjectId, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("subject_id", subjectId);
		map.put("user_id", userId);
		return this.find(getClassList, map);
	}

	public void saveHomeworkClass(EHomeworkClass eHomeworkClass) {
		this.excute(saveHomeworkClass, eHomeworkClass.toMap());
	}

	/**
	 * 得到某个老师将作业布置给哪些班级
	 * @param hwId
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getSettleClassNameList(int hwId, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("creator", userId);
		return this.find(getSettleClassNameListByHwidCreator, map);
	}

	public void deleteHwClass(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		this.excute(deleteHwClassByHwIdClassId, map);
	}

	/**
	 * 设置作为发布状态
	 * @param releaseTime
	 */
	public void updateHomeworkToRelease(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		this.excute(updateHomeworkToRelease, map);
	}

	/**
	 * 得到作业统计信息
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getHwStatisticByUserId(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getHwStatisticByUserId, map);
	}

	/**
	 * 得到还未布置完成的作业
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getUnSettleHomeworks(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getUnSettleHomeworks, map);
	}

	/**
	 * 更新作业的发布状态为删除状态
	 * @param hwId
	 */
	public void updateHomeworkToDeletedStatus(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		this.excute(updateHomeworkToDeletedStatus, map);
	}

	public void updateHomeworkQuestionsSubjectNo(int hwId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("subject_no", subjectId);
		this.excute(updateHomeworkQuestionsSubjectNo, map);
	}

	//得到老师某个时间内某个科目的作业
	public List<LStrMap<Object>> getHomeworksOfTeacher(long userId, Date releaseTime, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("release_time", releaseTime);
		map.put("subject_id", subjectId);
		return this.find(getHomeworksOfTeacher, map);
	}

	/**
	 * 设置作业的共享标志
	 * @param hwId
	 * @param shareFlag
	 */
	public void setHwShareFlag(int hwId, int shareFlag) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("share_flag", shareFlag);
		this.excute(setHwShareFlag, map);
	}

	public List<LStrMap<Object>> searchSharedHomeworks(Date releaseTime, int schoolId, int subjectId, long userId) {
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("release_time", releaseTime);
		paramMap.put("school_id", schoolId);
		paramMap.put("subject_id", subjectId);
		paramMap.put("user_id", userId);
		return this.find(searchSharedHomeworks, paramMap);
	}

	/**
	 * 根据hwId获得作业的所有小题的小题编号
	 * @param hwId
	 * @return
	 */
	public List<LStrMap<Object>> getHwQuestionIds(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getHwQuestionIdsByHwId, map);
	}

	//得到作业试题关系数据
	public List<LStrMap<Object>> getHwQuesRelationList(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getHwQuesRelationListByHwId, map);
	}

	public List<LStrMap<Object>> getPastHomeworks(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getPastHomeworksByUserId, map);
	}

	public List<LStrMap<Object>> getCorrectInfoOfOneClass(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getCorrectInfoOfOneClassByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getQuestionsInfo(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getQuestionsInfoByHwRecId, map);
	}

	public List<LStrMap<Object>> getCorrectNum(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getCorrectNumByHwRecId, map);
	}

	public List<LStrMap<Object>> getStudentHomeworkInfo(int hwId, int hwRecId, int quesType) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("hw_id", hwId);
		map.put("ques_type", quesType);
		return this.find(getStudentHomeworkInfoByHwRecIdHwId, map);
	}

	public void updateEHomeworkRecordDetailInfo(int hwRecId, int quesId, double score, String oScore, String teacherComment) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("ques_id", quesId);
		map.put("score", score);
		map.put("o_score", oScore);
		map.put("teacher_comment", teacherComment);
		this.excute(updateEHomeworkRecordDetailInfo, map);
	}

	public void updateHomeworkRecordCorrectFlag(int hwRecId, int correctFlag, long userId, int star, String comment) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("correct_flag", correctFlag);
		map.put("creator", userId);
		map.put("star", star);
		map.put("remark", comment);
		this.excute(updateHomeworkRecordCorrectFlag, map);
	}

	public List<LStrMap<Object>> getKnowledgePointsBySubject(int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("subject_id", subjectId);
		return this.find(getKnowledgePointsBySubjectId, map);
	}

	public List<LStrMap<Object>> getStudentInfo(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getStudentInfoByHwRecId, map);
	}

	public List<LStrMap<Object>> getClassUnmarkedInfo(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getClassUnmarkedInfoByHwRecId, map);
	}

	public List<LStrMap<Object>> getNextHomework(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		return this.find(getNextHomeworkByHwRecId, map);
	}

	public List<LStrMap<Object>> getTeacherId(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getTeacherIdByUserId, map);
	}

	public List<LStrMap<Object>> getStudentId(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getStudentIdByUserId, map);
	}

	/**
	 * 删除一个作业小题关系
	 * @param hwId 作业编号
	 * @param quesId 小题编号
	 */
	public void deleteOneHwQuesRelation(int hwId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("ques_id", quesId);
		this.excute(deleteOneHwQues, map);
	}

	public void updateHomeworkName(int hwId, String hwName) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("hw_name", hwName);
		this.excute(updateHomeworkNameByHwIdHwName, map);
	}

	public void clearUnStartedHomeworkArranges(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		this.excute(clearUnStartedHomeworkArrangesByHwId, map);
	}

	public List<LStrMap<Object>> getClassBasicCorrectInfo(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getClassBasicCorrectInfoByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getUnCommitStudents(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getUnCommitStudentsByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getStudentsNumOfStar(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getStudentsNumOfStarByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getStudentsOfStar(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getStudentsOfStarByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getStudentsNumOfQuestions(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getStudentsNumOfQuestionsByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getErrorStudentsOfQuestions(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getErrorStudentsOfQuestionsByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getNumOfOptions(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getNumOfOptionsByHwIdClassId, map);
	}

	//得到每个学生做作业的时间
	public List<LStrMap<Object>> getStuNumInArea(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getStuNumInAreaByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getHomeworkInfo(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getHomeworkInfoByHwId, map);
	}

	public List<LStrMap<Object>> getStudentsOfOptions(int hwId, int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		return this.find(getStudentsOfOptionsByHwIdClassId, map);
	}

	public List<LStrMap<Object>> getClassNum(int classId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("class_id", classId);
		return this.find(getClassNumByClassId, map);
	}

	public List<LStrMap<Object>> getRightNumOfQuestion(int hwId, int classId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		map.put("ques_id", quesId);
		return this.find(getRightNumOfQuestionByHwIdClassIdQuesId, map);
	}

	public List<LStrMap<Object>> getStudentsOfQuestionRight(int hwId, int classId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		map.put("ques_id", quesId);
		return this.find(getStudentsOfQuestionRightByHwIdClassIdQuesId, map);
	}

	public List<LStrMap<Object>> getStudentsOfQuestionError(int hwId, int classId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		map.put("ques_id", quesId);
		return this.find(getStudentsOfQuestionErrorByHwIdClassIdQuesId, map);
	}

	public List<LStrMap<Object>> getStudentsNumOfOptions(int hwId, int classId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		map.put("ques_id", quesId);
		return this.find(getStudentsNumOfOptionsByHwIdClassIdQuesId, map);
	}

	public List<LStrMap<Object>> getStusNameOfOptions(int hwId, int classId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("class_id", classId);
		map.put("ques_id", quesId);
		return this.find(getStusNameOfOptionsByHwIdClassIdQuesId, map);
	}

	/**
	 * 更新作业的小题的发布状态
	 * @param hwId 作业编号
	 * @param status 发布状态，0为未发布，1为已发布
	 * @return
	 */
	public int updateQuestionsReleaseStatusOfHomework(int hwId, int status) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("release_flag", status);
		return this.excute(updateQuestionsReleaseStatusOfHomework, map);
	}

	/**
	 * 更新作业记录明细的评语录音
	 * @param hwRecId 作业记录编号
	 * @param quesId 小题编号
	 * @param path 录音路径
	 */
	public void updateHomeworkRecordDetailCommentRec(int hwRecId, int quesId, String path) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("ques_id", quesId);
		map.put("comment_rec", path);
		this.excute(updateHomeworkRecordDetailCommentRec, map);
	}

	/**
	 * 更新作业记录的评语录音
	 * @param hwRecId 作业记录编号
	 * @param path 录音路径
	 */
	public void updateHomeworkRecordCommentRec(int hwRecId, String path) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("comment_rec", path);
		this.excute(updateHomeworkRecordCommentRec, map);
	}

	/**
	 * 更新小题的录音
	 * @param quesId 小题编号
	 * @param path 录音路径
	 */
	public void updateQuesRec(int quesId, String path) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		map.put("ques_rec", path);
		this.excute(updateQuesRec, map);
	}

	/**
	 * 获取作业的所有小题的小题Id
	 * @param hwId 作业编号
	 */
	public List<LStrMap<Object>> getQuesIdsOfHw(int hwId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		return this.find(getQuesIdsOfHw, params);
	}

	/**
	 * 得到作业的最大小题号
	 * @param hwId
	 */
	public List<LStrMap<Object>> getLastMTopicNumOfHw(int hwId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("hw_id", hwId);
		return this.find(getLastMTopicNumOfHw, params);
	}

}
