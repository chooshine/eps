package com.eps.dao.examsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class ViewExamDao extends BaseDao {
	
	@Value("${viewexam.exam.get.basicinfo}")
	private String getExamBasicInfo;
	@Value("${viewexam.student.get.all}")
	private String getAllStudentScore;
	@Value("${viewexam.question.score}")
	private String getQuestionInfo;
	@Value("${viewexam.question.percent}")
	private String getQuestionPercent;
	@Value("${viewexam.type.score.time}")
	private String getTypeScoreTime;
	@Value("${viewexam.question.score.time}")
	private String getQuestionScoreTime;
	@Value("${viewexam.record.statistics}")
	private String getRecordStatistics;
	@Value("${examsystem.before.show.all.info}")
	private String showAllInfo;
	@Value("${examsystem.before.show.error.questions}")
	private String showErrorQuestions;
	@Value("${viewexam.recorddetail.update.score}")
	private String updateRecDetailScore;
	@Value("${viewexam.record.update.markflagandscore}")
	private String updateRecordMarkFlagAndScore;
	@Value("${viewexam.record.update.markflag}")
	private String updateRecordMarkFlag;
	@Value("${viewexam.get.marknum.recid}")
	private String getMarkedNum;
	@Value("${gradepaper.get.next.unmarked}")
	private String getNextUnmarkedPaper;
	@Value("${gradepaper.unmaked.info.userid.classid}")
	private String getClassUnmarkedInfo;
	@Value("${gradepaper.typesandquestions.testrecid}")
	private String getGradePaperAnswersCardData;
	@Value("${gradepaper.get.typeinfo.by.examid}")
	private String getTypeInfoByExamId;
	@Value("${gradepaper.update.testrecdetail.commentrec}") private String updateTestRecordDetailCommentRec;
	/**
	 * 得到试卷的基本信息
	 * @param user_id
	 * @param exam_id
	 * @return
	 */
	public List<LStrMap<Object>> getExamBasicInfo(long user_id, long exam_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", user_id);
		map.put("exam_id", exam_id);
		return this.find(getExamBasicInfo,map);
	}
	
	/**
	 * 得到做了该试卷的所有学生的最近一次做卷得分
	 * @param exam_id
	 * @return
	 */
	public List<LStrMap<Object>> getAllStudentScore(long exam_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", exam_id);
		return this.find(getAllStudentScore, map);
	}
	
	/**
	 * 得到所有小题的得分、总分、用时、大题号
	 * @param examId
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getQuestionsInfo(long examId, long testRecId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("test_rec_id", testRecId);
		return this.find(getQuestionInfo,map);
	}
	
	/**
	 * 得到每个小题的共被做过次数、正确率、易错项
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getQuestionPercnet(long examId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.find(getQuestionPercent, map);
	}
	
	/**
	 * 得到某次做题记录中每个大题的得分和耗时
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getTypeScoreAndTime(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getTypeScoreTime, map);
	}
	
	/**
	 * 得到某次做题记录中每个小题的得分和耗时以及是否正确的标记
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getQuestionScoreAndTime(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getQuestionScoreTime, map);
	}
	
	/**
	 * 得到某次考试记录的统计数据
	 * @param examId
	 * @param testRecId
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getRecordStatistics(long examId, long testRecId, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		map.put("test_rec_id", testRecId);
		map.put("user_id", userId);
		return this.find(getRecordStatistics, map);
	}
	
	/**
	 * 得到某次考试记录的所有小题的详细信息
	 * @param testRecId
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getExamAllInfoToShow(long testRecId, int examId, int quesType) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		map.put("exam_id", examId);
		map.put("ques_type", quesType);
		return this.find(showAllInfo, map);
	}
	
	/**
	 * 得到某次考试记录的错误小题的详细信息
	 * @param testRecId
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getExamErrorQuestionsToShow(long testRecId, int examId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		map.put("exam_id", examId);
		return this.find(showErrorQuestions, map);
	}
	
	/**
	 * 更新某个考生的某个小题的得分
	 * @param testRecId
	 * @param quesId
	 * @param score
	 * @return
	 */
	public int updateRecordDetailScore(long testRecId, long quesId, double score, String oScore, String teacherComment) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		map.put("ques_id", quesId);
		map.put("score", score);
		map.put("o_score", oScore);
		map.put("teacher_comment", teacherComment);
		return this.excute(updateRecDetailScore, map);
	}
	
	/**
	 * 更新e_test_record表的阅卷标志
	 * @param testRecId
	 * @param markFlag
	 * @return
	 */
	public int updateRecordMarkFlag(long testRecId, int markFlag, long creatorId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		map.put("mark_flag", markFlag);
		map.put("creator_id", creatorId);
		return this.excute(updateRecordMarkFlagAndScore, map);
	}
	public int updateRecordMarkFlagAndScore(long testRecId, int markFlag, long creatorId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		map.put("mark_flag", markFlag);
		map.put("creator_id", creatorId);
		return this.excute(updateRecordMarkFlagAndScore, map);
	}
	
	/**
	 * 得到主观题总个数及已阅主观题个数
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getMarkNum(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getMarkedNum, map);
	}

	/**
	 * 得到下一个未批阅的试卷
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getStudentInfo(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getNextUnmarkedPaper, map);
	}

	/**
	 * 得到某个班级未批阅完试卷的信息
	 * @param userId
	 * @param classId
	 * @return
	 */
	public List<LStrMap<Object>> getClassUnmarkedInfo(long userId, long classId, long testId, long subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		map.put("class_id", classId);
		map.put("test_id", testId);
		map.put("subject_id", subjectId);
		return this.find(getClassUnmarkedInfo, map);
	}

	public List<LStrMap<Object>> getGradePaperAnswerCardData(long testRecId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		return this.find(getGradePaperAnswersCardData, map);
	}

	public List<LStrMap<Object>> getTypeInfo(int examId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("exam_id", examId);
		return this.find(getTypeInfoByExamId, map);
	}

	/**
	 * 更新考试做题明细的录音
	 * @param testRecId 考试记录编号
	 * @param quesId	小题编号
	 * @param path	录音路径
	 */
	public void updateTestRecordDetailCommentRec(int testRecId, int quesId, String path) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_rec_id", testRecId);
		map.put("ques_id", quesId);
		map.put("comment_rec", path);
		this.excute(updateTestRecordDetailCommentRec, map);
	}
	
}
