package com.eps.android.dao.homework;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.android.domain.ENote;
import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class AHomeworkDao extends BaseDao {
	@Value("${android.get.homeworks.by.userid}")
	private String getHomeworksByUserId;
	@Value("${android.get.statistic.by.userid.subjectid}")
	private String getStarStatisticByUserIdSubjectId;
	@Value("${android.get.starnum.by.userid.subjectid}")
	private String getStarNumOfHomeworksByUserIdSubjectId;
	@Value("${android.get.hwinfo.by.userid.hwid}")
	private String getHwInfoByUserIdHwId;
	@Value("${android.get.answercard.by.userid.hwid}")
	private String getAnswerCardByUserIdHwId;
	@Value("${android.get.knowledgepoint.by.userid.hwid}")
	private String getKnowledgePointsByUserIdHwId;
	@Value("${android.get.all.questions.by.hwid.hwrecid}")
	private String getAllQuestionsByHwIdHwRecId;
	@Value("${android.get.options.by.hwid}")
	private String getOptionsByHwId;
	@Value("${android.save.note}")
	private String saveNote;
	@Value("${android.update.note.by.noteid}")
	private String updateNoteByNoteId;
	@Value("${android.get.favoriteknowledgepoints.by.userid.subjectid}")
	private String getFavoriteKnowledgePointsByUserIdSubjectId;
	@Value("${android.get.favorite.questions.basicinfo.by.userid.kpid}")
	private String getFavoriteQuestionsBasicInfoByUserIdKpId;
	@Value("${android.get.options.of.favoritequestions.by.userid.kpid}")
	private String getOptionsOfFavoriteQuestionsByUserIdKpId;
	@Value("${android.get.error.knowledgepoints.by.userid.subjectid}")
	private String getErrorKnowledgePointsByUserIdSubjectId;
	@Value("${android.get.error.questions.basicinfo.by.userid.kpid}")
	private String getErrorQuestionsBasicInfoByUserIdKpId;
	@Value("${android.get.options.of.errorquestions.by.userid.kpid}")
	private String getOptionsOfErrorQuestionsByUserIdKpId;
	@Value("${android.get.note.knowledgepoints.by.userid.subjectid}")
	private String getNoteKnowledgePointsByUserIdSubjectId;
	@Value("${android.get.note.questions.basicinfo.by.userid.kpid}")
	private String getNoteQuestionsBasicInfoByUserIdKpId;
	@Value("${android.get.options.of.notequestions.by.userid.kpid}")
	private String getOptionsOfNoteQuestionsByUserIdKpId;
	@Value("${android.collect.question}")
	private String collectQuestion;
	@Value("${android.update.homeworkrecord.to.commit}")
	private String updateHomeworkRecordToCommit;
	@Value("${android.get.questionsinfo.by.hwid.hwrecid}")
	private String getQuestionsInfoByHwIdAndHwRecId;
	@Value("${android.get.question.by.quesid}")
	private String getQuestionByQuesId;
	@Value("${android.get.option.by.quesid}")
	private String getOptionByQuesId;
	@Value("${android.get.studentid.by.userid}")
	private String getStudentIdByUserId;
	@Value("${android.get.uncommithwnum.by.userid}")
	private String getUnCommitHomeworkNumByUserId;
	@Value("${android.get.hwrecinfo.by.hwid.userid}")
	private String getHwRecordInfoByHwIdUserId;
	@Value("${android.get.errorquestions.by.hwid.hwrecid}")
	private String getErrorQuestionsByHwIdHwRecId;
	@Value("${android.get.options.of.errorquestions.by.hwid.hwrecid}")
	private String getOptionsOfErrorQuestionsByHwIdHwRecId;
	@Value("${android.cancel.collection.by.userid.quesid}")
	private String cancelCollectionByUserIdQuesId;
	@Value("${android.get.options.of.homeworkquestions.by.hwid}")
	private String getOptionsOfHomeworkQuestionsByHwId;
	@Value("${android.get.hwrecorddetailinfo.by.hwrecid.quesid}")
	private String getHwRecordDetailInfoByHwRecIdQuesId;
	@Value("${android.get.options.of.onequestion.by.quesid}")
	private String getOptionsOfOneQuestionByQuesId;
	@Value("${android.update.homeworkdetail.of.radio.checkbox}")
	private String updateHomeworkDetailOfRadioCheckBox;
	@Value("${android.update.homeworkdetail.of.objectiveques}")
	private String updateHomeworkDetailOfObjectiveQues;
	@Value("${android.get.quesrecord.by.userid.quesid}")
	private String getQuesRecordByUserIdQuesId;
	@Value("${android.get.hwrecid.by.hwid.userid}")
	private String getHwRecordIdByHwIdUserId;
	@Value("${android.update.homework.finishednum.by.hwrecid}")
	private String updateHomeworkFinishedNumByHwRecId;
	
	
	public Page getHomeworks(int uid, int pageNo) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", uid);
		return pageQuery(getHomeworksByUserId, pageNo, 15, map);
	}

	public List<LStrMap<Object>> getStarStatistic(int userId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		return this.find(getStarStatisticByUserIdSubjectId, map);
	}

	public Page getStarNumOfHomeworks(int pageNo, int userId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		return pageQuery(getStarNumOfHomeworksByUserIdSubjectId, pageNo, 15, map);
	}

	public List<LStrMap<Object>> getHwInfo(int userId, int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("hw_id", hwId);
		return this.find(getHwInfoByUserIdHwId, map);
	}

	public List<LStrMap<Object>> getAnswerCard(int userId, int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("hw_id", hwId);
		return this.find(getAnswerCardByUserIdHwId, map);
	}

	public List<LStrMap<Object>> getKnowledgePoints(int userId, int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("hw_id", hwId);
		return this.find(getKnowledgePointsByUserIdHwId, map);
	}

	public List<LStrMap<Object>> getAllQuestions(int hwId, int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("hw_rec_id", hwRecId);
		return this.find(getAllQuestionsByHwIdHwRecId, map);
	}

	public List<LStrMap<Object>> getOptions(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getOptionsByHwId, map);
	}

	public void saveNote(ENote eNote) {
		UStrMap<Object> map = UStrMap.newInstance();
		this.excute(saveNote, eNote.toMap());
	}

	public void updateNote(int noteId, String commitTime, String content) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("note_id", noteId);
		map.put("commit_time", commitTime);
		map.put("content", content);
		this.excute(updateNoteByNoteId, map);
	}

	public List<LStrMap<Object>> getFavoriteKnowledgePoints(long userId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		return this.find(getFavoriteKnowledgePointsByUserIdSubjectId, map);
	}

	public List<LStrMap<Object>> getFavoriteQuestionsBasicInfo(long userId, int kpId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		return this.find(getFavoriteQuestionsBasicInfoByUserIdKpId, map);
	}

	public List<LStrMap<Object>> getOptionsOfFavoriteQuestions(long userId, int kpId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		return this.find(getOptionsOfFavoriteQuestionsByUserIdKpId, map);
	}

	public List<LStrMap<Object>> getErrorKnowledgePoints(long userId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		return this.find(getErrorKnowledgePointsByUserIdSubjectId, map);
	}

	public List<LStrMap<Object>> getErrorQuestionsBasicInfo(long userId, int kpId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		return this.find(getErrorQuestionsBasicInfoByUserIdKpId, map);
	}

	public List<LStrMap<Object>> getOptionsOfErrorQuestions(long userId, int kpId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		return this.find(getOptionsOfErrorQuestionsByUserIdKpId, map);
	}

	public List<LStrMap<Object>> getNoteKnowledgePoints(int userId, int subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("subject_id", subjectId);
		return this.find(getNoteKnowledgePointsByUserIdSubjectId, map);
	}

	public List<LStrMap<Object>> getNoteQuestionsBasicInfo(long userId, int kpId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		return this.find(getNoteQuestionsBasicInfoByUserIdKpId, map);
	}

	public List<LStrMap<Object>> getOptionsOfNoteQuestions(long userId, int kpId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("kp_id", kpId);
		return this.find(getOptionsOfNoteQuestionsByUserIdKpId, map);
	}

	public void collectQuestion(long userId, int quesId, String remark) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("ques_id", quesId);
		map.put("remark", remark);
		this.excute(collectQuestion, map);
	}

	public void updateHomeworkRecordToCommit(int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		this.excute(updateHomeworkRecordToCommit, map);
	}

	public List<LStrMap<Object>> getQuestionsInfo(int hwId, int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("hw_id", hwId);
		return this.find(getQuestionsInfoByHwIdAndHwRecId, map);
	}

	public List<LStrMap<Object>> getQuestion(int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		return this.find(getQuestionByQuesId, map);
	}

	public List<LStrMap<Object>> getOption(int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		return this.find(getOptionByQuesId, map);
	}

	public List<LStrMap<Object>> getStudentId(int userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getStudentIdByUserId, map);
	}

	public List<LStrMap<Object>> getUnCommitHomeworkNum(int userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getUnCommitHomeworkNumByUserId, map);
	}

	public List<LStrMap<Object>> getHwRecordInfo(int hwId, int userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("user_id", userId);
		return this.find(getHwRecordInfoByHwIdUserId, map);
	}

	public List<LStrMap<Object>> getErrorQuestions(int hwId, int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("hw_rec_id", hwRecId);
		return this.find(getErrorQuestionsByHwIdHwRecId, map);
	}
	
	public List<LStrMap<Object>> getOptionsOfHomeworkErrorQuestions(int hwId, int hwRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		map.put("hw_rec_id", hwRecId);
		return this.find(getOptionsOfErrorQuestionsByHwIdHwRecId, map);
	}

	public void cancelCollection(long userId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("ques_id", quesId);
		this.excute(cancelCollectionByUserIdQuesId, map);
	}

	public List<LStrMap<Object>> getOptionsOfHomeworkQuestions(int hwId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_id", hwId);
		return this.find(getOptionsOfHomeworkQuestionsByHwId, map);
	}

	public List<LStrMap<Object>> getHwRecordDetailInfo(int hwRecId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("ques_id", quesId);
		return this.find(getHwRecordDetailInfoByHwRecIdQuesId, map);
	}

	public List<LStrMap<Object>> getOptionsOfOneQuestion(int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("ques_id", quesId);
		return this.find(getOptionsOfOneQuestionByQuesId, map);
	}

	public void updateHomeworkDetail(int hwRecId, int quesId, String answer,
			String studentAnswer, int score, String answerTime) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("ques_id", quesId);
		map.put("answer", answer);
		map.put("student_answer", studentAnswer);
		map.put("score", score);
		map.put("answer_time", answerTime);
		this.excute(updateHomeworkDetailOfRadioCheckBox, map);
	}

	public void updateHomeworkDetail(int hwRecId, int quesId, String studentAnswer, String answerTime) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("ques_id", quesId);
		map.put("student_answer", studentAnswer);
		map.put("answer_time", answerTime);
		this.excute(updateHomeworkDetailOfObjectiveQues, map);
	}

	public List<LStrMap<Object>> getQuesRecord(long userId, int quesId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("ques_id", quesId);
		return this.find(getQuesRecordByUserIdQuesId, map);
	}

	public List<LStrMap<Object>> getHwRecordId(int hwId, int userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("hw_id", hwId);
		return this.find(getHwRecordIdByHwIdUserId, map);
	}

	public void updateHomeworkFinishedNum(int hwRecId, int num) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("hw_rec_id", hwRecId);
		map.put("num", num);
		this.excute(updateHomeworkFinishedNumByHwRecId, map);
	}

}
