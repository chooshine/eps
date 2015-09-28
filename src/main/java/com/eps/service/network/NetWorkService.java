package com.eps.service.network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.examsystem.ExamAfterDao;
import com.eps.dao.network.NetWorkDao;
import com.eps.domain.EExam;
import com.eps.domain.EExamQuestion;
import com.eps.domain.ETest;
import com.eps.domain.ETestEexam;
import com.eps.domain.ExamPreviewSets;
import com.eps.domain.ques.BigQues;
import com.eps.domain.ques.Exam;
import com.eps.domain.ques.Image;
import com.eps.domain.ques.Option;
import com.eps.domain.ques.Question;
import com.eps.service.scorerecord.XlsFileNameGenerator;
import com.eps.utils.FileUploader;
import com.eps.utils.LStrMap;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;
import com.eps.utils.UStrMap;

@Service
public class NetWorkService {

	@Autowired private NetWorkDao nwd;
	@Autowired private ExamAfterDao ead;
	@Autowired private MaxValueINcrementer seq_examId;	//试卷编号
	@Autowired private MaxValueINcrementer seq_testId;	//考试编号
	@Autowired private MaxValueINcrementer seq_typeId;
	
	public List<LStrMap<Object>> getUserFromTeaAndStu(long user_id){
		return nwd.getUserFromTeaAndStu(user_id);
	}

	
	public List<LStrMap<Object>> getstudentByuserId(long user_id){
		return nwd.getstudentByuserId(user_id);
	}
		
	public List<LStrMap<Object>> getTeacherAllNewWork(long user_id){
		return nwd.getTeacherAllNewWork(user_id);
	}

	
	public List<LStrMap<Object>>  getTestsByUser(long user_id){
		return nwd.getTestsByUser(user_id);
	}
	
	public List<LStrMap<Object>>  getTestsByExamId(long user_id,int examId){
		return nwd.getTestsExamId(user_id,examId);
	}
	
	public int insertTest(ETest eTest){
		int testiId=seq_testId.nextIntValue();
		eTest.setTestId(testiId);
		return nwd.insertTest(eTest);
	}
	
	public int saveExamService(EExam eExam){
		int examId=seq_examId.nextIntValue();
		eExam.setExamId(examId);
		nwd.saveExamDao(eExam);
		return examId;
	}
	
	public int saveTestExamDao(ETestEexam ete){
		return nwd.saveTestExamDao(ete);	
	}
	
	
	public List<LStrMap<Object>> getGradeByTeacher(long user_id){
		return nwd.getGrades(user_id);
	}
	
	public List<LStrMap<Object>> getTestTypes(String code_cate){
		return nwd.getTestTypes(code_cate);
	}
	
	public List<LStrMap<Object>> getTeachsOfTeacher(long user_id){
		return nwd.getTeachsOfTeacher(user_id);
	}
	
	
	//上传图片
	public LStrMap<Object> uploadMath(Long userId, HttpServletRequest request, HttpServletResponse response) {
		FileUploader photoUpload=new FileUploader();
		//设置保存路径
		photoUpload.setSavePath("/images/formula");
		//设置最大上传大小
		photoUpload.setFileSizeMax(1024000);
		photoUpload.setSizeMax(1024000);
		//设置文件格式
		Set<String> set=new HashSet<String>();
		set.add(".jpg");
		set.add(".png");
		set.add(".gif");
		set.add(".tiff");
		set.add(".bmp");
		set.add(".jpeg");
		set.add(".tif");
		set.add(".swf");
		set.add(".dib");
		photoUpload.setAcceptTypes(set);
		
		//设置文件名生成器
		FileNameGenerator xlsFileNameGenerator=new XlsFileNameGenerator(userId);
		photoUpload.setFileNameGenerator(xlsFileNameGenerator);
		
		//上传文件
		Result resut=photoUpload.upload(request, response);
		LStrMap<Object> map = LStrMap.newInstance();
		String errorInfo="上传失败！";
		if("SUCCESS".equals(resut.toString())){
			errorInfo="success";
		}else{
			errorInfo="error";
		}
		
		Set<String> name=photoUpload.getFileNames();
		StringBuffer sbf=new StringBuffer();
		sbf.append("/images/formula/"+name.toString().replace("[","").replace("]", ""));
    	map.put("errorInfo", errorInfo);
    	map.put("mathPath",new String(sbf));
    	return map;
	}
	
	/**
	 * 删除某篇文章下的所有小题及文章
	 * @param examId
	 * @param quesIds
	 */
	public void deleteArticleQuestions(long examId, String quesIds) {
		List<Integer> list = new ArrayList<Integer>();
		String[] quesIdArray =  quesIds.split(",");
		for (int i = 0; i < quesIdArray.length; i++) {
			list.add(Integer.parseInt(quesIdArray[i]));
		}
		nwd.deleteArticleQuestions(examId, list);
		
		//如果材料是未发布，则删除数据库中的材料及小题
		if (ead.getQuestionByQuesId(list.get(0)).getReleaseFlag() == 0) {
			ead.deleteQuestions(list);
			for (int i=0, size=list.size(); i<size; i++) {//删除选项
				ead.deleteOptionDao(list.get(i));
			}
		}
	}

	/**
	 * 更新某篇文章所有小题之后的小题的题号
	 * @param examId
	 * @param mTopic
	 * @param mTopicNum
	 */
	public void updateArticleAfterMTopic(long examId, int mTopic, int mTopicNum) {
		nwd.updateArticleAfterMTopic(examId, mTopic, mTopicNum);
	}
	
	public List<LStrMap<Object>> getCommitedExam(long userId) {
		return nwd.getCommitedExam(userId);
	}


	/**
	 * 更新大题的大题号
	 * @param parseLong
	 * @param integer
	 * @return
	 */
	public int updateTypeOrderNum(long typeId, Integer orderNum) {
		return nwd.updateOrderNum(typeId, orderNum);
	}


	/**
	 * 更新所有小题的小题号
	 * @param examId
	 * @return
	 */
	public void updateAllQuesMTopic(long examId) {
		nwd.updateNormalQueMTopic(examId);//更新普通小题小题号
		nwd.updateArtQuesMTopic(examId);	 //更新材料小题小题号
	}


	public LStrMap<Object> getExamInfoByExamId(long examId) {
		List<LStrMap<Object>> list = nwd.getExamInfoByExamId(examId);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}


	public void upExamInfoByExamId(EExam exam) {
		nwd.updateExamInfoByExamId(exam);
	}


	public int deleteTestExamRelation(int testId, int examId) {
		return nwd.deleteTestExamRelation(testId, examId);
	}


	public List<LStrMap<Object>> getKnowledgePointsByParentId(int parentKpId) {
		return nwd.getKnowledgePointsByParentId(parentKpId);
	}

	public List<LStrMap<Object>> getOneLayerKnowledgePointsBySubjectId(int subjectId) {
		return nwd.getOneLayerKnowledgePointsBySubjectId(subjectId);
	}


	public List<LStrMap<Object>> getAllGrades() {
		return nwd.getAllGrades();
	}


	public LStrMap<Object> getGrade(long userId, int subjectId) {
		List<LStrMap<Object>> list = nwd.getGrade(userId, subjectId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 返回试卷的预览设置信息，包括装订线的显示标记、保密标记的显示标记等
	 * @param examId 试卷编号
	 * @return 返回试卷的预览设置信息
	 */
	public LStrMap<Object> getExamPreviewSets(int examId) {
		List<LStrMap<Object>> list = nwd.getExamPreviewSets(examId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新一个试卷的设置信息
	 * @param eps 设置信息
	 */
	public void updateExamPreviewSets(ExamPreviewSets eps) {
		nwd.updateExamPreviewSets(eps);
	}

	/**
	 * 插入一个试卷预览设置记录
	 * @param examId 试卷编号
	 */
	public void saveExamPreviewSets(int examId) {
		nwd.saveExamPreviewSets(examId);
	}

	/**
	 * 获得某科目的所有题目类型
	 * @param subjectId	科目编号
	 * @return	包含该科目的所有题目类型的List
	 */
	public List<LStrMap<Object>> getTypeSubjects(int subjectId) {
		return nwd.getTypeSubjects(subjectId);
	}

	public void updatePrevQuestionsMTopic(int examId, int quesId, int moveNum) {
		nwd.updatePrevQuestionsMTopic(examId, quesId, moveNum);
	}
	public void updateQuestionMTopic(int examId, int quesId, int moveNum) {
		nwd.updateQuestionMTopic(examId, quesId, moveNum);
	}
	public void updateNextQuestionsMTopic(int examId, int quesId, int moveNum) {
		nwd.updateNextQuestionsMTopic(examId, quesId, moveNum);
	}

	public List<LStrMap<Object>> getKnowledgePointsBySubjectId(int subjectId) {
		return nwd.getKnowledgePointsBySubjectId(subjectId);
	}


	public int saveQuesType(int examId, int subjectId, int tsId, int orderNum) {
		int typeId = seq_typeId.nextIntValue();
		nwd.saveQuesType(examId, subjectId, typeId, tsId, orderNum);
		return typeId;
	}

	/**
	 * 得到符合条件的随机小题
	 * @param tsId 题型编号
	 * @param kpList 考点列表
	 * @param quesNum 小题数量
	 * @param usedQues 已用过小题列表
	 * @param diff 难度
	 * @return
	 */
	public List<LStrMap<Object>> getRandomQues(int tsId, List<String> kpList, int quesNum, List<Integer> usedQues, float diff) {
		return nwd.getRandomques(tsId, kpList, quesNum, usedQues, diff);
	}

	/**
	 * 批量插入试卷小题关系
	 * @param paramList 参数列表
	 */
	public void batchInsertExamQuestionRelation(List<UStrMap<Object>> paramList) {
		nwd.batchInsertExamQuestionRelation(paramList);
	}

	/**
	 * 获取试卷大题信息
	 * @param examId 试卷编号
	 * @return 含有试卷大题信息的List，如果试卷当前没有大题，则得到null
	 */
	public List<LStrMap<Object>> getQuesTypesOfExam(int examId) {
		return ead.getQuesTypeDao(examId);
	}

	/**
	 * 获取试卷的所有小题的小题Id
	 * @param examId 试卷编号
	 * @return
	 */
	public List<Integer> getQuesIdsOfExam(int examId) {
		List<LStrMap<Object>> list = nwd.getQuesIdsOfExam(examId);
		if (list.size() > 0) {
			List<Integer> result = new ArrayList<Integer>();
			for (Iterator<LStrMap<Object>> iterator = list.iterator(); iterator.hasNext();) {
				result.add(Integer.parseInt(iterator.next().get("ques_id").toString()));
			}
			return result;
		}
		return null;
	}

	/**
	 * 获取试卷中大题号<=当前大题的大题的最后一个小题的小题号lastMTopic
	 * @param examId 试卷编号
	 * @param orderNum 当前大题号
	 * @return
	 */
	public int getLastMTopic(int examId, int orderNum) {
		List<LStrMap<Object>> list = nwd.getLastMTopic(examId, orderNum);
		if (list.size()>0 && list.get(0).get("m_topic")!=null) {
			return Integer.parseInt(list.get(0).get("m_topic").toString());
		}
		return 0;
	}

	/**
	 * 插入一条试卷小题记录
	 * @param eeq 试卷小题对象
	 */
	public void saveEExamQuestion(EExamQuestion eeq) {
		ead.saveExamQuesDao(eeq);
	}

	/**
	 * 更新试卷的小题数
	 * @param examId 试卷编号
	 */
	public void updateExamMTopicNum(int examId) {
		nwd.updateExamMTopicNum(examId);
	}

	/**
	 * 得到试卷对象
	 * @param examId
	 */
	public Exam getExam(int examId) {
		Map<String, Image> images = LStrMap.newInstance();
		Map<Integer, List<Option>> options = getOptionsOfExam(examId, images);//得到选项
		Map<Integer, List<Question>> questions = getQuestionsOfExam(examId, images);//得到小题
		List<BigQues> bigQuestions = getBigQuestionsOfExam(examId);//得到大题
		fixQuestionsAndOptions(questions, options);//封装小题和选项
		fixBigQuestionsAndQuestions(bigQuestions, questions);//封装大题和小题
		
		Exam exam = nwd.getExam(examId);
		exam.setBigQues(bigQuestions);//试卷设置大题
		exam.setImages(images);
		return exam;
	}
	
	/**
	 * 封装大题和小题
	 * @param bigQuestions 包含试卷所有大题的List
	 * @param questions 包含试卷所有小题的Map
	 */
	private void fixBigQuestionsAndQuestions(List<BigQues> bigQuestions, Map<Integer, List<Question>> questions) {
		for (Iterator<BigQues> iterator = bigQuestions.iterator(); iterator.hasNext();) {
			BigQues bigQues = iterator.next();
			bigQues.setQuestions(questions.get(bigQues.getId()));
		}
	}

	/**
	 * 封装小题和选项
	 * @param questions 包含试卷所有小题的Map
	 * @param options 包含试卷所有选项的Map
	 */
	private void fixQuestionsAndOptions(Map<Integer, List<Question>> questions, Map<Integer, List<Option>> options) {
		for (Iterator<List<Question>> iterator = questions.values().iterator(); iterator.hasNext();)
			for (Iterator<Question> iterator2 = iterator.next().iterator(); iterator2.hasNext();) {
				Question question = iterator2.next();
				question.setOptions(options.get(question.getQuestionId()));
			}
	}


	/**
	 * 得到试卷的所有大题，将大题封装后存到一个list中
	 * @param examId 试卷编号
	 */
	private List<BigQues> getBigQuestionsOfExam(int examId) {
		List<LStrMap<Object>> list = nwd.getBigQuestionsOfExam(examId);
		if (list.size() > 0) {
			List<BigQues> resultList = new ArrayList<BigQues>();
			for (Iterator<LStrMap<Object>> iterator = list.iterator(); iterator.hasNext();) {
				LStrMap<Object> tempMap = iterator.next();
				//组合大题信息到大题对象
				BigQues bigQues = new BigQues();
				bigQues.setId(Integer.parseInt(tempMap.get("type_id").toString()));
				bigQues.setOrderNum(Integer.parseInt(tempMap.get("order_num").toString()));
				bigQues.setName(tempMap.get("type_name").toString());
				bigQues.setDetail(tempMap.get("type_detail").toString());
				bigQues.setIndexNo(tempMap.get("index_no").toString());
				bigQues.setQuesType(Integer.parseInt(tempMap.get("type").toString()));
				bigQues.setDefaultScore(Double.parseDouble(tempMap.get("default_score").toString()));
				resultList.add(bigQues);
			}
			
			return resultList;
		}
		return null;
	}


	/**
	 * 得到试卷所有小题，将同一大题的小题存到同一个list中，并且以大题Id为键名，将list存到map中
	 * @param examId 试卷编号
	 */
	private Map<Integer, List<Question>> getQuestionsOfExam(int examId, Map<String, Image> images) {
		List<LStrMap<Object>> list = nwd.getQuestionsOfExam(examId);
		if (list.size() > 0) {
			Map<Integer, List<Question>> resultMap = new LinkedHashMap<Integer, List<Question>>();
			for (Iterator<LStrMap<Object>> iterator = list.iterator(); iterator.hasNext();) {
				LStrMap<Object> tempMap = iterator.next();
				//组合小题信息到小题对象
				Question question = new Question();
				images.putAll(question.setContents(tempMap.get("ques_content").toString()));
				question.setDifficulty(Double.parseDouble(tempMap.get("difficulty").toString()));
				question.setIndexNo(tempMap.get("m_topic").toString());
				Object keyword = tempMap.get("keyword");
				images.putAll(question.setParses(keyword==null?"":keyword.toString()));
				question.setQuestionId(Integer.parseInt(tempMap.get("ques_id").toString()));
				question.setQuestionType(Integer.parseInt(tempMap.get("ques_type").toString()));
				question.setScore(Double.parseDouble(tempMap.get("score").toString()));
				question.setShareFlag(Integer.parseInt(tempMap.get("share_flag").toString()));
				Object quesRefer = tempMap.get("ques_refer");
				String answer = quesRefer==null?"":quesRefer.toString();
				if (question.getQuestionType() == 2)//多选题要去除@@
					answer.replace("@@", "");
				else if(question.getQuestionType() == 4)//填空题要去除选项号之前的##
					answer.replaceAll("##", "");
				Object referIds = tempMap.get("remark");
				if (referIds == null) question.setReferIds("");
				else question.setReferIds(referIds.toString());
				
				images.putAll(question.setAnswers(answer));
				
				int typeId = Integer.parseInt(tempMap.get("type_id").toString());
				if (resultMap.containsKey(typeId)) {
					resultMap.get(typeId).add(question);
				} else {
					ArrayList<Question> tempList = new ArrayList<Question>();
					tempList.add(question);
					resultMap.put(typeId, tempList);
				}
			}
			
			fixArtAndQuestions(resultMap);//封装材料和材料下小题
			return resultMap;
		}
		return null;
	}

	/**
	 * 封装材料和材料下小题
	 * @param questionsMap 含有试卷所有小题的map
	 */
	private void fixArtAndQuestions(Map<Integer, List<Question>> questionsMap) {
		for (Iterator<List<Question>> iterator = questionsMap.values().iterator(); iterator.hasNext();) {
			List<Question> questions = iterator.next();
			List<Integer> needRemovedQues = null;
			for (Iterator<Question> iterator2 = questions.iterator(); iterator2.hasNext();) {
				Question question = iterator2.next();
				if (question.getQuestionType() == 6) {//当前小题是材料时，才进行添加孩子小题的操作
					int quesIndex = questions.indexOf(question);//得到当前小题的索引
					String referIds = question.getReferIds();
					if (!referIds.equals("")) {
						//由于查出的数据是按照顺序排列的，所以材料下的小题的索引是紧接着材料的索引号的
						for (int i=1; i<=referIds.split(",").length; i++) {
							question.addChildQuestion(questions.get(quesIndex+i));
							if (needRemovedQues == null) needRemovedQues = new ArrayList<Integer>();
							needRemovedQues.add(quesIndex+i);//将小题存到需删除小题列表中
						}
					}
				}
			}
			
			if (needRemovedQues != null) {
				for (int i=0; i<needRemovedQues.size(); i++)//删除材料下小题
					questions.remove(needRemovedQues.get(i));
			}
		}
	}


	/**
	 * 得到试卷的所有选项，将同一小题的选项存到同一个list中，并且以小题Id为键名，将list存到map中
	 * @param examId 试卷编号
	 */
	private Map<Integer, List<Option>> getOptionsOfExam(int examId, Map<String, Image> images) {
		List<LStrMap<Object>> list = nwd.getOptionsOfExam(examId);
		if (list.size() > 0) {
			Map<Integer, List<Option>> resultMap = new LinkedHashMap<Integer, List<Option>>();
			for (Iterator<LStrMap<Object>> iterator = list.iterator(); iterator.hasNext();) {
				LStrMap<Object> lStrMap = iterator.next();
				Option option = new Option();
				option.setOptionId(Integer.parseInt(lStrMap.get("opt_id").toString()));
				option.setOptionNo(lStrMap.get("opt_no").toString());
				images.putAll(option.setContents(lStrMap.get("opt_content").toString()));
				
				int quesId = Integer.parseInt(lStrMap.get("ques_id").toString());
				if (resultMap.containsKey(quesId)) {
					resultMap.get(quesId).add(option);
				} else {
					ArrayList<Option> tempList = new ArrayList<Option>();
					tempList.add(option);
					resultMap.put(quesId, tempList);
				}
			}
			return resultMap;
		}
		return null;
	}

	
}
