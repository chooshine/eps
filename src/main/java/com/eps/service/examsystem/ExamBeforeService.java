package com.eps.service.examsystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.examsystem.ExamAfterDao;
import com.eps.dao.examsystem.ExamBeforeDao;
import com.eps.domain.ETestRecord;
import com.eps.domain.ETestRecordDetail;
import com.eps.utils.LStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ExamBeforeService {

	@Autowired
	private ExamBeforeDao ebf;
	
	@Autowired
	private ExamAfterDao ead; 
	
	@Autowired
	private MaxValueINcrementer seq_testRecId;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getExamTypeService(){
		List daolist=ebf.getExamTypeDao();
		Map<String,List> newMap=new HashMap<String,List>();
		List newList;
		for (int i = 0; i < daolist.size(); i++) {
			Map tempMap=(Map) daolist.get(i);
			if(newMap.containsKey(tempMap.get("EXAM_TYPE"))){
				newList=newMap.get(tempMap.get("EXAM_TYPE"));
				newList.add(daolist.get(i));
				newMap.put(tempMap.get("EXAM_TYPE").toString(), newList);
			}else{
				newList=new ArrayList();
				newList.add(daolist.get(i));
				newMap.put(tempMap.get("EXAM_TYPE").toString(), newList);
			}
		}
		return newMap;
	}
	
	/**
	 * 试卷类型输电信息 如模拟题
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @return
	 */
	public List<LStrMap<Object>> getExamTypeByAnyService(String subjectNo,String examArea,String examType){
		String examArea2="AA";
		if("all".equals(examArea)){
			examArea="1";
			examArea2="1";
		}else{
			
		}
		String examType2="AA";
		if("all".equals(examType)){
			examType="1";
			examType2="1";
		}
		if("all".equals(subjectNo)){
			examType=subjectNo.substring(0, 3);
		}
		
		return ebf.getExamTypeByAnyDao(subjectNo, examArea, examType,examArea2, examType2);
	}
	
	/**
	 * 试卷地区信息
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @return
	 */
	public List<LStrMap<Object>> getExamAreaByAnyService(String subjectNo,String examArea,String examType){
		String examArea2="AA";
		if("all".equals(examArea)){
			examArea="1";
			examArea2="1";
		}else{
			
		}
		String examType2="AA";
		if("all".equals(examType)){
			examType="1";
			examType2="1";
		}
		if("all".equals(subjectNo)){
			examType=subjectNo.substring(0, 3);
		}
		
		return ebf.getExamAreaByAnyDao(subjectNo, examArea, examType,examArea2, examType2);
	}
	
	/**
	 * 试卷科目信息
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @return
	 */
	public List<LStrMap<Object>> getSubjectNoByAnyService(String subjectNo,String examArea,String examType){
		String examArea2="AA";
		if("all".equals(examArea)){
			examArea="1";
			examArea2="1";
		}else{
			
		}
		String examType2="AA";
		if("all".equals(examType)){
			examType="1";
			examType2="1";
		}
		if("all".equals(subjectNo)){
			examType=subjectNo.substring(0, 3);
		}
		
		return ebf.getSubjectNoByAnyDao(subjectNo, examArea, examType,examArea2, examType2);
	}
	
	/**
	 * 获得试卷信息
	 * @param subjectNo
	 * @param examArea
	 * @param examType
	 * @return
	 */
	public List<LStrMap<Object>> getExamInfoByAnyService(String subjectNo,String examArea,String examType){
		String examArea2="AA";
		if("all".equals(examArea)){
			examArea="1";
			examArea2="1";
		}else{
			
		}
		String examType2="AA";
		if("all".equals(examType)){
			examType="1";
			examType2="1";
		}
		if("all".equals(subjectNo)){
			examType=subjectNo.substring(0, 3);
		}
		
		return ebf.getExamInfoByAnyDao(subjectNo, examArea, examType,examArea2, examType2);
	}
	
	/**
	 * 比较答案，得出分数
	 * @param examId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public float getAnswerByExamIdService(String answers,String markInfo,String timeInfo,int testRecId,String studentAnswers){
		Gson gson = new  GsonBuilder().create();
		Map<String,Object> mapContemt=gson.fromJson(answers,Map.class);					//得到考生答案
		Map<String,Object> studentAnswersMap=gson.fromJson(studentAnswers,Map.class);	//得到考生答案
		String examId=mapContemt.get("examId").toString();				//得到试卷编号
		Map<String,Object> mapMark=gson.fromJson(markInfo,Map.class);	//得到标记
		Map<String,Object> mapTime=gson.fromJson(timeInfo,Map.class);	//得到考试时间

		List<LStrMap<Object>> answerList=ebf.getAnswerByExamIdDao((int)(Double.parseDouble(examId)));	//得到试卷的所有小题和选项
		
		float score=0;
		//遍历试卷所有选项
		 for (int i = 0; i < answerList.size(); ) {
			 ETestRecordDetail eTestRecordDetail=new ETestRecordDetail();	//新建一个考试记录详情
			 eTestRecordDetail.setTestRecId(testRecId);
			 
			 String quesId=answerList.get(i).get("QUES_ID").toString();		//得到当前选项的小题编号
			 Map<String,Object> quesMap=(Map<String, Object>) mapContemt.get(quesId);					//得到小题的答案Map
			 Map<String,Object> studentAnswerMap=(Map<String, Object>) studentAnswersMap.get(quesId);	//得到小题考生选择的答案
			 
			 String studentAnswer = ""; 
			//设置小题考生答案，设置分隔符为@@
			 for (int j = 1; j <= studentAnswerMap.size(); j++) {
				 if(studentAnswerMap.containsKey(j+"")){
					 studentAnswer=studentAnswer+studentAnswerMap.get(j+"").toString()+"@@";
				}
			}
			if(studentAnswer.length()>=2) {
				studentAnswer = studentAnswer.substring(0, studentAnswer.length()-2);
			}
			if (studentAnswer.equals("")) {
				eTestRecordDetail.setStudentAnswer(null);
			} else {
				eTestRecordDetail.setStudentAnswer(studentAnswer);
			}
			
			 int optNum=Integer.parseInt(answerList.get(i).get("OPTION_NUM").toString());												//得到选项个数
			 String oScore=answerList.get(i).get("O_SCORE").toString();																			//得到选项得分
			 
			 //遍历当前选项所属小题的所有选项，计算考试得分
			 boolean flag=true;																																	//用来标示答案是否正确，正确则得分增加
			 for (int j = i; j <i+optNum; j++) {
				 int quesType=Integer.parseInt(answerList.get(j).get("QUES_TYPE").toString());											//得到小题类型
				 String answer=answerList.get(j).get("OPT_REFER").toString();																	//得到当前小题当前选项正确答案
				 String optId=answerList.get(j).get("OPT_ID").toString();																			//得到选项编号
				
				 if(quesMap.containsKey(optId)) {																											//如果小题答案Map中包含当前选项
					 if(quesType==4){																																//如果是填空题
						 flag=false;
						 String[] scoreArr=oScore.split(",");																									//将选项得分以“,”分隔存到数组中
						 if(answer.equals(quesMap.get(optId).toString())){																			//如果小题当前选项的正确答案和考生答案相同
							 score=score+Integer.parseInt(scoreArr[0]);																					//得分增加
							 eTestRecordDetail.setScore(eTestRecordDetail.getScore()+Integer.parseInt(scoreArr[0]));
						 }
					 }else{																																				//如果填空题
						 if(!(answer.equals(quesMap.get(optId).toString()))){																			//如果小题当前选项的正确答案和考生答案不相同
							 flag=false;
						 }
					 }
				 }else{																																					//如果考生小题答案中不包含当前选项
					 flag=false;
					 quesMap.put(optId, null);
				 }
			}
			 
			eTestRecordDetail.setQuesId(Integer.parseInt(quesId));
			
			Gson gson2 = new GsonBuilder().create();
			eTestRecordDetail.setAnswer(gson2.toJson(quesMap));																					//设置考试记录详情的答案内容
			
			 Map<String,Object> timeMap=(Map<String, Object>) mapTime.get(quesId);													//得到当前小题的考试时长
			 if(timeMap.containsKey("enter")){																												//如果包含“enter”，就设置小题用时
				 eTestRecordDetail.setEnterTime(timeMap.get("enter").toString());
				 eTestRecordDetail.setLeftTime(timeMap.get("leave").toString());
				 eTestRecordDetail.setAnswerTime(timeMap.get("remain").toString());
			 }
			 if(mapMark.containsKey(quesId)){																												//如果标记中包含当前小题的编号的键名
				 eTestRecordDetail.setRemark("true");
			 }
			if(flag){
				eTestRecordDetail.setScore(Integer.parseInt(oScore));
				score=score+Integer.parseInt(oScore);
			}
			i=i+optNum;
			
			ebf.saveEtestRecordDetail(eTestRecordDetail);																								//保存一个记录详情
		}
		 
		return score;
	}
	
	/**
	 * 考生考试中保存试卷时，将试卷信息录入到数据库
	 * @param answers
	 * @param markInfo
	 * @param timeInfo
	 * @param testRecId
	 * @param studentAnswers
	 */
	@SuppressWarnings("unchecked")
	public void saveExamInfo(String answers,String markInfo,int testRecId,String studentAnswers, String questionsTime){
		Gson gson = new GsonBuilder().create();
		Map<String,Object> mapStrAnswer = gson.fromJson(answers,Map.class);				//得到考生答案，原有的
		Map<String,Object> studentAnswersMap = gson.fromJson(studentAnswers,Map.class);	//得到考生答案
		Map<String,Object> mapMark = gson.fromJson(markInfo,Map.class);					//得到标记
		Map<String,Object> mapQuestionsTime = gson.fromJson(questionsTime, Map.class);	//得到每小题的做题时长，用秒表示
		
		String examId = mapStrAnswer.get("examId").toString();							//得到试卷编号
		List<LStrMap<Object>> answerList=ebf.getAnswerByExamIdDao((int)(Double.parseDouble(examId)));	//得到试卷的所有小题和选项
		
		//遍历试卷所有选项
		for (int i=0; i<answerList.size(); ) {
			//新建一个考试记录详情
			ETestRecordDetail eTestRecordDetail=new ETestRecordDetail();
			eTestRecordDetail.setTestRecId(testRecId);
			
			String quesId=answerList.get(i).get("QUES_ID").toString();								//得到当前选项的小题编号
			Map<String,Object> quesMap=(Map<String, Object>) mapStrAnswer.get(quesId);				//得到小题的答案Map
			Map<String,Object> studentAnswerMap=(Map<String, Object>) studentAnswersMap.get(quesId);//得到小题考生选择的答案
			
			String studentAnswer = "";
			//设置小题考生答案，设置分隔符为@@
			for (int j=1; j<=studentAnswerMap.size(); j++) {
				//如果小题答案中含有当前索引
				if(studentAnswerMap.containsKey(j+"") && !studentAnswerMap.get(j+"").equals("")){
					studentAnswer = studentAnswer+studentAnswerMap.get(j+"").toString()+"@@";
				}
			}
			//等于2是当填空题只有一个空格或者解答题的时候，如果大于2，要去除最后的@@
			if(studentAnswer.length() >= 2) {
				studentAnswer = studentAnswer.substring(0, studentAnswer.length()-2);
			}
			if (studentAnswer.equals("")) {
				eTestRecordDetail.setStudentAnswer(null);
			} else {
				eTestRecordDetail.setStudentAnswer(studentAnswer);
			}
			
			//得到小题的做题用时，以秒为单位
			if (mapQuestionsTime.containsKey(quesId)) {
				eTestRecordDetail.setAnswerTime(mapQuestionsTime.get(quesId).toString());
			}
			
			//得到小题的标记
			if(mapMark.containsKey(quesId)){	//如果标记中包含当前小题的编号的键名
				eTestRecordDetail.setRemark(mapMark.get(quesId).toString());
			}
			
			//得到选项个数，用于跳过多条记录
			int optNum=Integer.parseInt(answerList.get(i).get("OPTION_NUM").toString());
			i=i+optNum;
			
			eTestRecordDetail.setQuesId(Integer.parseInt(quesId));
			eTestRecordDetail.setAnswer(new GsonBuilder().create().toJson(quesMap));
			ebf.saveEtestRecordDetail(eTestRecordDetail);																								//保存一个记录详情
		}
		
	}
	
	/**
	 * 保存记录表
	 * @param eTestRecord
	 * @return
	 */
	public int saveEtestRecordService(ETestRecord eTestRecord){
		int testRecId=seq_testRecId.nextIntValue();
		eTestRecord.setTestRecId(testRecId);
		eTestRecord.setScore(0);
		eTestRecord.setExamStatus(0);
		eTestRecord.setCommitFlag(0);
		eTestRecord.setExamUseTime("00:00:00");
		
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		eTestRecord.setExamTime(dateformat.format(date));
		ebf.saveEtestRecord(eTestRecord);
		return testRecId;
	}
	
	/**
	 * 更新记录表
	 * @param eTestRecord
	 * @return
	 */
	public boolean updateEtestRecordService(ETestRecord eTestRecord){
		return ebf.updateEtestRecord(eTestRecord)==1;
	}
	
	/**
	 * 判断记录表是否已经存在记录
	 * @param examId
	 * @return
	 */
	public List<LStrMap<Object>> getTestRecordCountService(int testRecId){
		return ebf.getTestRecordCountDao(testRecId);
	}
	
	/**
	 * 获得记录的明细信息
	 * @param testRecId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LStrMap<Object>> getTestDetailInfoService(int testRecId){
		List<LStrMap<Object>> initList=ebf.getTestDetailInfoDao(testRecId);
		Gson gson=new GsonBuilder().create();
		for(int i = 0; i < initList.size(); i++) {
			Map map=gson.fromJson(initList.get(i).get("answer").toString(), Map.class);
			((Map)initList.get(i)).put("answer", map);
		}
		return initList;
	}
	
	/**
	 * 得到某次考试记录的客观题得分
	 * @param testRecId
	 * @return
	 */
	public float getExamScore(long testRecId) {
		List<LStrMap<Object>> list = ebf.getExamScore(testRecId);
		if (list.size() > 0) {
			return ((Double) list.get(0).get("score")).floatValue();
		}
		return 0;
	}
	
	/**
	 * 更新某次考试考试记录明细里的得分
	 * @param testRecId
	 */
	public void updateTestRecordDetailScore(long testRecId) {
		ebf.updateTestRecordDetailScore(testRecId);
	}
	
	/**
	 * 删除某次考试记录的所有考试明细
	 * @param testRecId
	 */
	public void deleteTestRecDetailByRecId(long testRecId) {
		ebf.deleteTestRecDetailByRecId(testRecId);
	}
	
	/**
	 * 考生考试过程中保存试卷
	 * @param answers
	 * @param markInfo
	 * @param timeInfo
	 * @param testRecId
	 * @param studentAnswers
	 */
	public void saveExam(String answers,String markInfo,int testRecId,String studentAnswers, String questionsTime) {
		deleteTestRecDetailByRecId(testRecId);//删除某次考试记录对应的所有考试详情
		saveExamInfo(answers, markInfo, testRecId, studentAnswers, questionsTime);//重新保存考试记录详情
	}
	
	public LStrMap<Object> getExamInfoAndEndTime(int examId, int testId, int classId, int subjectId){
		List<LStrMap<Object>> list = ebf.getExamInfoAndEndTime(examId, testId, classId, subjectId);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<LStrMap<Object>> getNetWorkStudentTypesAndQuestions(int testRecId) {
		List<LStrMap<Object>> dataList = ebf.getNetWorkStudentTypesAndQuestions(testRecId);
		Map<Integer, List<LStrMap<Object>>> map = new LinkedHashMap<Integer, List<LStrMap<Object>>>();
		
		//遍历大题，将该大题下的所有小题封装到该大题的一个List中
		for (int i = 0; i < dataList.size(); i++) {
			LStrMap<Object> tempMap = dataList.get(i);
			Integer tempKey = Integer.parseInt(tempMap.get("type_id").toString());
			//如果存在type_id对应的List，则放到List中
			if(map.containsKey(tempKey)) {
				map.get(tempKey).add(tempMap);
			} else {
				List<LStrMap<Object>> tempArrayList = new ArrayList<LStrMap<Object>>();
				tempArrayList.add(tempMap);
				map.put(tempKey, tempArrayList);
			}
		}
		
		//将map中的数据存到List中
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			int iteratorKey = iterator.next();
			List<LStrMap<Object>> tempList = map.get(iteratorKey);
			LStrMap<Object> resultMap = LStrMap.newInstance();
			resultMap.put("type_id", iteratorKey);
			resultMap.put("type_name", tempList.get(0).get("type_name"));
			resultMap.put("questionsList", tempList);
			
			list.add(resultMap);
		}
		return list;
	}

	public int getAutoMarkFlagByTestRecId(int testRecId) {
		return Integer.parseInt(ebf.getAutoMarkFlagByTestRecId(testRecId).get(0).get("automark_flag").toString());
	}

	public LStrMap<Object> getStudentScoreByTestRecId(int testRecId) {
		return ebf.getStudentScoreByTestRecId(testRecId).get(0);
	}
}
