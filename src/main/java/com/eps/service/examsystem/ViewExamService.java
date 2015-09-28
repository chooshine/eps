package com.eps.service.examsystem;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.examsystem.ExamAfterDao;
import com.eps.dao.examsystem.ViewExamDao;
import com.eps.utils.LStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ViewExamService {

	@Autowired 
	private ViewExamDao viewExamDao;
	
	@Autowired
	private ExamAfterDao ead;
	
	/**
	 * 得到某次考试记录的统计数据
	 * @param examId
	 * @param testRecId
	 * @param userId
	 * @return
	 */
	public LStrMap<Object> getRecordStatistics(long examId, long testRecId, long userId) {
		List<LStrMap<Object>> list = viewExamDao.getRecordStatistics(examId, testRecId, userId);
		return list.size()>0 ? list.get(0) : null;
	}
	
	/**
	 * 得到某次考试记录的所有大题及小题的得分及耗时
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getTypesAndQuestionsInfo(long testRecId){
		List<LStrMap<Object>> typesScoreTimeList = viewExamDao.getTypeScoreAndTime(testRecId);			//得到所有大题的信息
		List<LStrMap<Object>> questionsScoreTimeList = viewExamDao.getQuestionScoreAndTime(testRecId);	//得到所有小题的信息
		
		//遍历大题，将该大题下的所有小题封装到该大题的一个List中
		for (int i = 0; i < typesScoreTimeList.size(); i++) {
			LStrMap<Object> typeMap = typesScoreTimeList.get(i);	//得到大题
			
			//遍历小题，如果是该大题下的小题，则将当前小题放到该大题的小题List中
			List<LStrMap<Object>> questionsList = new ArrayList<LStrMap<Object>>();	//新建小题list
			for (int j = 0; j < questionsScoreTimeList.size(); j++) {
				//如果小题的大题编号和当前大题的
				if(Long.parseLong(questionsScoreTimeList.get(j).get("type_id").toString()) == Long.parseLong(typeMap.get("type_id").toString())){
					questionsList.add(questionsScoreTimeList.get(j));																								//将小题放到大题的小题列表中
				}
			}
			
			typeMap.put("questionsList", questionsList);																								//将大题的小题列表放到大题的Map中
		}
		
		return typesScoreTimeList;
	}
	
	public LStrMap<Object> getExamBasicInfo(long user_id, long exam_id){
		List<LStrMap<Object>> list = viewExamDao.getExamBasicInfo(user_id, exam_id);
		LStrMap<Object> examBasicInfo = null;
		if (list.size() >= 1) {
			examBasicInfo = list.get(0);
		}
		return examBasicInfo;
	} 
	
	/**
	 * 得到该学生超过了多少做过这张试卷的学生，结果为百分比，及这张试卷的全网平均分
	 * @param exam_id
	 * @return
	 */
	public LStrMap<Object> getOverPercentAndAvg(long exam_id, long user_id){
		List<LStrMap<Object>> list = viewExamDao.getAllStudentScore(exam_id);
		int total = list.size();		//得到做了该试卷的总人数
		int rank = 0;					//用于标示该学生的排名
		double total_point = 0;	//用于标示全网改卷总分
		double avg_point = 0;	//用于标示全网平均分
		double percent = 0;		//用于标示超过人数的百分比
		
		for (int i = 0; i < total; i++) {
			if ((Integer)list.get(i).get("user_id") == user_id) {
				rank = i+1;
			}
			total_point = total_point+(Double)list.get(i).get("score");
		}
		
		if (total != 0) {
			percent = ((rank-1)/total)*100;
		}
		avg_point = total_point/total;
		LStrMap<Object> map = LStrMap.newInstance();
		map.put("percent", String.format("%.1f", percent));
		map.put("avgPoint", avg_point);
		return map;
	}
	
	/**
	 * 得到所有大题的编号，名称，耗时，得分，总分，格式如：[{type_id,type_name,type_answer_time,type_score,type_o_score,[]}]
	 * @param examId
	 * @param testRecId
	 * @return
	 */
	public List<LStrMap<Object>> getTypesAndQuestions(long examId, long testRecId) {
		List<LStrMap<Object>> list = viewExamDao.getQuestionsInfo(examId, testRecId);			//得到所有小题的信息
		
		//按顺序存放大题，格式如：{{type_id,type_name,type_answer_time,type_score,type_o_score,[]}
		Map<String, LStrMap<Object>> typeMap = new LinkedHashMap<String, LStrMap<Object>>();
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		for (int i = 0; i < list.size();) {																				//遍历所有记录
			
			//得到一个“小题”的基本信息
			LStrMap<Object> optionMap = list.get(i);																	//得到当前记录，根据它得到对应小题的信息
			int typeId = (Integer)optionMap.get("type_id");														//得到大题号
			int optNum = (Integer)optionMap.get("option_num");												//得到选项编号
			long answerTime = Long.parseLong(optionMap.get("answer_time").toString());			//得到对应小题的答题时间
			double score = (Double)optionMap.get("score");														//得到对应小题的得分
			double oScore = Double.parseDouble(optionMap.get("o_score").toString());				//得到对应小题的总分
			
			// 得到对应小题的考生答案，并比较该小题所有选项的考生答案和参考答案
			Gson gson = gsonBuilder.create();
			LStrMap<Object> currentQuesAnswer = gson.fromJson(optionMap.get("answer").toString(), LStrMap.class);
			optionMap.put("flag", 0);																							//默认未作答
			for (int j = i; j < i+optNum && j<list.size(); j++) {
				int optionId = (Integer) list.get(j).get("opt_id");													//得到一个选项编号
				String studentAnswer = "";
				if (currentQuesAnswer.get(String.valueOf(optionId)) != null) {								//如果选项的考生答案不为空
					studentAnswer = currentQuesAnswer.get(String.valueOf(optionId)).toString();		//得到考生答案
					if (!studentAnswer.equals("0") && !studentAnswer.equals("")) {							//如果考生答案不是0 而且不是空字符串
						optionMap.put("flag", 1);																				//设置大题情况是回答正确，且跳出循环
						break;
					}
				}
			}
			
			if ((Integer)optionMap.get("flag")!=0) {																	//如果考生做了该题
				for (int j = i; j < i+optNum && j<list.size(); j++) { 												// 将所有该小题的选项跟参考答案做比较
					int optionId = (Integer) list.get(j).get("opt_id");												//得到选项的考生答案
					String optionRefer = "";
					if (list.get(j).get("opt_refer")!=null) {																//如果参考答案不是空
						optionRefer = list.get(j).get("opt_refer").toString();										//得到选项的参考答案
					}
					String studentAnswer = "";
					if (currentQuesAnswer.get(String.valueOf(optionId)) != null) {							//如果考生答案不是空
						studentAnswer = currentQuesAnswer.get(String.valueOf(optionId)).toString();	//得到考生答案
					}
					if (!studentAnswer.equals(optionRefer)) {															//如果考生答案和参考答案不一致
						optionMap.put("flag", 2);																				//设置标记为回答错误，并跳出循环比较
						break;
					}
				}
			}
			i = i+optNum;																										//设置i为i+optNum
			if (typeMap.containsKey(String.valueOf(typeId))) {														//如果typeMap包含对应的大题号
				//得到对应大题原本的信息
				LStrMap<Object> beforeMap = typeMap.get(String.valueOf(typeId));
				long beforeTime = Long.parseLong(beforeMap.get("type_answer_time").toString());
				double beforeScore = (Double)beforeMap.get("type_score");
				double beforeOScore = Double.parseDouble(beforeMap.get("type_o_score").toString());
				List<LStrMap<Object>> questionsList = (List<LStrMap<Object>>) beforeMap.get("questionsList");		//得到对应大题的小题列表
				
				//更新对应大题的信息
				beforeTime = beforeTime+answerTime;
				beforeScore = beforeScore + score;
				beforeOScore = beforeOScore + oScore;
				
				questionsList.add(optionMap);		//将当前小题存到原有大题的小题列表中
				
				beforeMap.put("type_answer_time", beforeTime);
				beforeMap.put("type_score", beforeScore);
				beforeMap.put("type_o_score", beforeOScore);
				beforeMap.put("questionsList", questionsList);
				
				typeMap.put(String.valueOf(typeId), beforeMap);		//替换原有大题的信息
			} else {
				//新建一个map用于保存对应的大题
				LStrMap<Object> initialMap = LStrMap.newInstance();

				initialMap.put("type_id", typeId);
				initialMap.put("type_name", optionMap.get("type_name"));
				initialMap.put("type_answer_time", optionMap.get("answer_time"));
				initialMap.put("type_score", optionMap.get("score"));
				initialMap.put("type_o_score", optionMap.get("o_score"));

				List<LStrMap<Object>> questionsList = new ArrayList<LStrMap<Object>>();
				questionsList.add(optionMap);
				initialMap.put("questionsList", questionsList);
				typeMap.put(String.valueOf(typeId), initialMap);
			}
		}
		
		List<LStrMap<Object>> typesAndQuestions = new ArrayList<LStrMap<Object>>();
		
		Iterator<String> iterator = typeMap.keySet().iterator();
		while (iterator.hasNext()) {
			typesAndQuestions.add(typeMap.get(iterator.next()));
		}
		return typesAndQuestions;
	}
	
	
	public LStrMap<LStrMap<Object>> getQuestionPercent(long examId) {
		List<LStrMap<Object>> list = viewExamDao.getQuestionPercnet(examId);							//得到所有选项记录
		
		LStrMap<LStrMap<Object>> questionsMap = LStrMap.newInstance();
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		for (int i = 0; i < list.size();) {																						//遍历选项记录
			LStrMap<Object> questionMap = list.get(i);																	//得到选项
			int quesId = (Integer)questionMap.get("ques_id");														//得到选项小题编号
			int optNum = (Integer)questionMap.get("option_num");												//得到选项个数
			Gson gson = gsonBuilder.create();
			//得到当前小题的考生答案
			LStrMap<Object> currentQuesAnswer = gson.fromJson(questionMap.get("answer").toString(), LStrMap.class);
			
			if (questionsMap.containsKey(String.valueOf(quesId))) {													//如果对应小题已经存在
				LStrMap<Object> beforeMap = questionsMap.get(String.valueOf(quesId));					//得到对应小题
				int beforeCount = (Integer)beforeMap.get("count");													//得到回答总次数
				int beforeRightCount = (Integer)beforeMap.get("rightCount");									//得到正确的次数
				Map beforErrorMap = (LinkedHashMap)beforeMap.get("optErrorMap");							//得到错误选项map
				boolean flag = true;
				
				//比较每个选项的考生答案和参考答案
				for (int j = i; j < i+optNum && j<list.size(); j++) { 													// 将所有该小题的选项跟参考答案做比较
					int optionId = (Integer) list.get(j).get("opt_id");													//得到选项的考生答案
					String optionRefer = "";
					if (list.get(j).get("opt_refer")!=null) {																	//如果参考答案不是空
						optionRefer = list.get(j).get("opt_refer").toString();											//得到选项的参考答案
					}
					String studentAnswer = "";
					if (currentQuesAnswer.get(String.valueOf(optionId)) != null) {								//如果考生答案不是空
						studentAnswer = currentQuesAnswer.get(String.valueOf(optionId)).toString();		//得到考生答案
					}
					
					if (!studentAnswer.equals(optionRefer)) {																//如果考生答案和参考答案不一致
						flag = false;																									//标记变为false
						int errorCount = (Integer)beforErrorMap.get(String.valueOf(optionId));				//得到选项的错误次数
						beforErrorMap.put(String.valueOf(optionId), errorCount+1);								//选项的错误次数加1
					}
				}
				
				//如果回答正确
				if (flag == true) {
					beforeMap.put("rightCount", beforeRightCount+1);												//正确次数加1
				} 
				
				//将做题次数加1，并将选项的错误Map放到该题的Map中
				beforeMap.put("count", beforeCount+1);																	//将做过次数加1
				questionMap.put("optErrorMap", beforErrorMap);														//更新选项错误次数
				
				//将questionsMap中增加当前小题的Map，并将i值增加
				questionsMap.put(String.valueOf(quesId), beforeMap);
				i = i+optNum;
			} else {																														//对应的小题不存在
				Map<String, Integer> optErrorMap = new LinkedHashMap<String, Integer>();			//新建一个选项错误Map
				boolean flag = true;
				
				//比较每个选项的考生答案和参考答案
				for (int j = i; j < i+optNum && j<list.size(); j++) { 													// 将所有该小题的选项跟参考答案做比较
					int optionId = (Integer) list.get(j).get("opt_id");													//得到选项的考生答案
					String optionRefer = "";
					if (list.get(j).get("opt_refer")!=null) {																	//如果参考答案不是空
						optionRefer = list.get(j).get("opt_refer").toString();											//得到选项的参考答案
					}
					String studentAnswer = "";
					if (currentQuesAnswer.get(String.valueOf(optionId)) != null) {								//如果考生答案不是空
						studentAnswer = currentQuesAnswer.get(String.valueOf(optionId)).toString();		//得到考生答案
					}
					
					if (!studentAnswer.equals(optionRefer)) {																//如果考生答案和参考答案不一致
						flag = false;
						optErrorMap.put(String.valueOf(optionId), 1);
					} else {
						optErrorMap.put(String.valueOf(optionId), 0);
					}
				}
				
				//如果回答正确
				if (flag == true) {
					questionMap.put("rightCount", 1);
				} else {
					questionMap.put("rightCount", 0);
				}
				
				//将做题次数加1，并将选项的错误Map放到该题的Map中
				questionMap.put("count", 1);
				questionMap.put("optErrorMap", optErrorMap);
				
				//将questionsMap中增加当前小题的Map，并将i值增加
				questionsMap.put(String.valueOf(quesId), questionMap);
				i = i+optNum;
			}
		}
		
		Iterator<String> iterator = questionsMap.keySet().iterator();
		Map<String, Object> tempMap = null;
		while (iterator.hasNext()) {
			tempMap = questionsMap.get(iterator.next());
			Map<String, Integer> optErrorMap = (Map<String, Integer>) tempMap.get("optErrorMap");
			//计算正确率
			int count = (Integer)tempMap.get("count");
			int rightCount = (Integer)tempMap.get("rightCount");
			double percent = rightCount/count;
			String rightPercent = String.format("%.2f", percent);
			tempMap.put("rightPercent", rightPercent);
			
			//得出易错项
			Map<String, Boolean> easyErrorMap = new LinkedHashMap<String, Boolean>();
			int maxErrorCount = 0;
			Iterator<String> iterator2 = optErrorMap.keySet().iterator();
			while (iterator2.hasNext()) {
				String currentOpt = iterator2.next();
				int errorCount  = optErrorMap.get(currentOpt);
				if (errorCount > maxErrorCount) {
					
					Iterator<String> iterator3 = easyErrorMap.keySet().iterator();
					while (iterator3.hasNext()) {
						easyErrorMap.put(iterator3.next(), false);
					}
					
					easyErrorMap.put(currentOpt, true);
					maxErrorCount = optErrorMap.get(currentOpt);
				} else if (errorCount < maxErrorCount) {
					easyErrorMap.put(currentOpt, false);
				} else {
					if (errorCount==0) {
						easyErrorMap.put(currentOpt, false);
					}
					easyErrorMap.put(currentOpt, true);
				}
			}
			
			if (maxErrorCount == 0) {		//maxErrorCount等于0说明没有易错项
				tempMap.put("easyErrorFlag", 0);
			} else {
				tempMap.put("easyErrorFlag", 1);
			}
			tempMap.put("easyErrorOption", easyErrorMap);
		}
		
		return questionsMap;
	}
	

	/**
	 * 获得试卷符合条件的小题
	 * @param testRecId 考试记录编号
	 * @param examId 试卷编号
	 * @param viewType 查看类型，有查看全部（值为-1）和查看主观题（值为0）两种
	 * @return 符合条件的小题信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getExamAllInfoToShow(long testRecId, int examId, int quesType) {
		List list=new ArrayList();
		
		//从e_ques_type表根据order_num得到试卷上的所有大题信息
		List<LStrMap<Object>> qtlist=ead.getQuesTypeDao(examId);
		
		List<String> bctList=new ArrayList<String>();
		List<String> sctList=new ArrayList<String>();
		String bigQt = ead.getBigQtDao(examId).get("NAME").toString();
		String smallQt = ead.getSmallQtDao(examId).get("NAME").toString();
		String[] bct = bigQt.split(",");	//得到大题题号类型名，使用的是e_exam和s_code表
		String[] sct = smallQt.split(",");	//得到小题题号类型名，使用的是e_exam和s_code表
		
		//将大题号放到bctList中
		for (int i = 0; i < bct.length; i++) {
			bctList.add(bct[i]);
		}
		bctList.add(bigQt);
		
		for (int i = 0; i < sct.length; i++) {
			sctList.add(sct[i]);
		}
		sctList.add(smallQt);
		
		//得到这张试卷对应的科目的所有知识点，并存到map中，键名是kp_id,键值是知识点的map
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = ead.getAllKnowledgePoints(examId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		
		List<LStrMap<Object>> qiList = viewExamDao.getExamAllInfoToShow(testRecId, examId, quesType);
		List<LStrMap<Object>> qiCList=new ArrayList<LStrMap<Object>>();
		List tkList=new ArrayList();
		
		//将选项组合成小题
		//遍历所有选项
		for (int i=0; i<qiList.size();) {
			int optNum=Integer.parseInt(qiList.get(i).get("OPTION_NUM").toString());	//得到选项个数
			//如果选项个数等于0说明是阅读理解的文章介绍 
			if(optNum==0){
				qiCList.add(qiList.get(i));	//qiCList中存储该文章介绍
				i=i+1;
				continue;
			}
			
			//新建三个列表，用于存储选项的相应的信息
			List<String> optList = new ArrayList<String>();		//选项内容列表
			List<String> refList = new ArrayList<String>();		//参考答案列表
			List<Integer> idList = new ArrayList<Integer>();	//用于存选项编号
			List<String> optNoList = new ArrayList<String>();	//用于存选项号
			String[] oScoreArr = null;							//用于存选项得分
			
			//将选项得分分割为数组
			if(qiList.get(i).get("O_SCORE")!=null){
				oScoreArr = qiList.get(i).get("O_SCORE").toString().split(",");
			}
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			String kpStr = qiList.get(i).get("knowledge_point").toString();
			List<LStrMap<Object>> kpList = new ArrayList<LStrMap<Object>>();
			String[] kpArr = kpStr.split(",");
			for(String tempKpIdStr : kpArr) {
				LStrMap<Object> tempKpMap = LStrMap.newInstance();
				String tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
				tempKpMap.put("kpName", tempKpName);
				while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
					tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
					tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+"-"+tempKpName;//得到父级知识点和本知识点的组合字符串
				}
				tempKpMap.put("kpPath", tempKpName);
				kpList.add(tempKpMap);
			}
			
			//遍历一个小题的选项记录，封装选项内容、参考答案、编号、选项号这些内容
			//遍历当前小题的选项
			for (int j=i; j<(i+optNum) && j<qiList.size(); j++) {
				if(qiList.get(j).get("OPT_CONTENT") != null) {//如果当前选项内容不是空
					optList.add(qiList.get(j).get("OPT_CONTENT").toString());			//选项列表新增选项内容
				}
				if(qiList.get(j).get("OPT_REFER")!=null) {	//如果参考答案不是空
					refList.add(qiList.get(j).get("OPT_REFER").toString());				//参考答案列表新增内容
				}
				idList.add(Integer.parseInt(qiList.get(j).get("OPT_ID").toString()));	//选项编号列表新增内容
				if(qiList.get(j).get("OPT_NO")!=null){		//如果选项号不是空
					optNoList.add(qiList.get(j).get("OPT_NO").toString());				//选项号列表新增内容
				}
			}
			
			//疑惑项
			List<String> doubtOptionsList = new ArrayList<String>();
			if (qiList.get(i).get("answer") != null) {
				String doubtOptionsStr = qiList.get(i).get("answer").toString();
				Map<String, Object> gsonMap = new GsonBuilder().create().fromJson(doubtOptionsStr, LinkedHashMap.class);
				Iterator<String> iterator = gsonMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					doubtOptionsList.add(gsonMap.get(key).toString());
				}
			}
			
			// 把上面封装起来的相应的列表存到该小题第一个选项记录的里面
			qiList.get(i).put("OPT_CONTENT", optList);	//选项内容
			qiList.get(i).put("OPT_REFER", refList);	//选项参考答案
			qiList.get(i).put("OPT_ID", idList);		//选项编号
			qiList.get(i).put("OPT_NO", optNoList);		//选项号
			qiList.get(i).put("O_SCORE", oScoreArr);	//选项得分
			qiList.get(i).put("Doubt_Options", doubtOptionsList);
			qiList.get(i).put("KNOWLEDGE_POINT", kpList);//知识点
			
			qiCList.add(qiList.get(i));					//qiCList中存储当前小题
			i = i+optNum;								//列表索引增加
		}
		
		//将小题组合成大题
		Map<String,LinkedHashMap<String, Object>> qiMap = new HashMap<String, LinkedHashMap<String,Object>>();	//用于存储所有小题信息
		List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
		
		//遍历所有大题记录
		for (int i=0; i<qtlist.size(); i++) {
			String qkey = ((LStrMap<Object>)qtlist.get(i)).get("TYPE_ID").toString();//得到对应的大题的编号
			qiMap.put(qkey, new LinkedHashMap<String, Object>());//对应每个大题新建一个LinkedHashMap
		}
		
		//用于存储临时小题的map
		LinkedHashMap<String, Object> tempMap = null;
		//将小题对应到大题
		for (int i = 0; i < qiCList.size(); i++) {	//遍历所有小题信息
			LStrMap<Object> tempQues = qiCList.get(i);
			//获得其键值
			String quesKey = tempQues.get("TYPE_ID").toString();	//获得小题对应的大题编号
			int tempQuesType = Integer.parseInt(tempQues.get("QUES_TYPE").toString());	//获得小题的类型
			int type = Integer.parseInt(tempQues.get("TYPE").toString());			//获得小题对应大题的类型
			
			int quesId = Integer.parseInt(tempQues.get("ques_id").toString());
			String remark = tempQues.get("remark")==null?null:tempQues.get("remark").toString();
			
			if(qiMap.containsKey(quesKey)){	//如果qiMap中存到对应的大题
				tempMap = (LinkedHashMap) qiMap.get(quesKey);	//得到对应的大题列表
			} else {	//如果qiMap中不存在对应的大题列表
				tempMap = new LinkedHashMap<String, Object>();	//新建一个列表
			}
			//判断小题类型是否是6
			if (tempQuesType == 6) {
				//小题如果是材料，则把小题及其下的小题存入到一个ArrayList中，并将ArrayList存入对应大题的的LinkedHashMap
				tempList = new ArrayList<LStrMap<Object>>();	//新建一个临时列表
				tempList.add(qiCList.get(i));	//tempList中增加对应的小题
				if(remark == null){	//如果remark是null，说明该材料下没有小题
					tempMap.put("art"+quesId, tempList);
					qiMap.put(quesKey, tempMap);//将tkList存到qiMap中
					continue;	//跳出当前一次循环
				}
				
				//材料下有小题
				String[] quesIdArr = remark.split(",");	//得到材料对应的小题号
				for (int j=0; j<qiCList.size(); j++) {	//遍历所有小题
					for (int x = 0; x < quesIdArr.length; x++) {	//遍历材料对应的所有小题号
						if(qiCList.get(j).get("QUES_ID").toString().equals(quesIdArr[x])){	//如果对应的小题号等于材料小题号
							tempList.add(qiCList.get(j));	//tempList中增加对应的小题
						}
					}
				}
				tempMap.put("art"+quesId, tempList);
				qiMap.put(quesKey, tempMap);	//将对应的大题放到qiMap中
			} else if (remark==null || remark.equals("")) {//判断小题remark是否为null或""
				//小题是普通小题，则把小题存入对应大题的LinkedHashMap中，键名为normal+小题编号
				tempMap.put("normal"+quesId, tempQues);
				qiMap.put(quesKey, tempMap);
			}
		}
		
		//过滤主观题
		if (quesType != -1) {
			//去除所有非材料题
			Iterator<String> keyIterator = qiMap.keySet().iterator();
			while (keyIterator.hasNext()) {//遍历所有的大题
				String key = keyIterator.next();
				
				LinkedHashMap<String, Object> tempQuesMap = qiMap.get(key);
				Iterator<String> quesKeyIterator = tempQuesMap.keySet().iterator();
				while (quesKeyIterator.hasNext()) {//遍历所有的小题
					String quesKey = quesKeyIterator.next();
					//如果是材料题，且材料题对应的小题数量为1，说明只有材料，没有小题，则要删除材料
					if (quesKey.startsWith("art") && (((List<LStrMap<Object>>)tempQuesMap.get(quesKey)).size() == 1))
					quesKeyIterator.remove();
				}
				
				//小题遍历结束后，判断大题中的小题数量是否为0，如果是，则删除该大题 
				if (tempQuesMap.size() == 0) {
					keyIterator.remove();
					Iterator<LStrMap<Object>> qtListIterator = qtlist.iterator();
					while (qtListIterator.hasNext()) {
						LStrMap<Object> tempQtMap = qtListIterator.next();
						if (tempQtMap.get("type_id").toString().equals(key)) {
							qtListIterator.remove();
							break;
						}
					}
				}
			}
		}
		
		list.add(qtlist);
		list.add(bctList);
		list.add(sctList);
		list.add(qiMap);
		return list;
	}
	
	
	/**
	 * 获得试卷所有错题信息，并转化成固定格式
	 * @param examId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getExamErrorQuestionsToShow(long testRecId, int examId) {
		List list = new ArrayList();
		
		//从e_ques_type表根据order_num得到试卷上的所有大题信息
		List<LStrMap<Object>> qtlist = ead.getQuesTypeDao(examId);
		
		List<String> bctList = new ArrayList<String>();
		List<String> sctList = new ArrayList<String>();
		String bigQt = ead.getBigQtDao(examId).get("NAME").toString();
		String smallQt = ead.getSmallQtDao(examId).get("NAME").toString();
		String[] bct = bigQt.split(",");	//得到大题题号类型名，使用的是e_exam和s_code表
		String[] sct = smallQt.split(",");	//得到小题题号类型名，使用的是e_exam和s_code表
		
		//将大题号放到bctList中
		for (int i = 0; i < bct.length; i++) {
			bctList.add(bct[i]);
		}
		bctList.add(bigQt);
		
		for (int i = 0; i < sct.length; i++) {
			sctList.add(sct[i]);
		}
		sctList.add(smallQt);
		
		//得到这张试卷对应的科目的所有知识点，并存到map中，键名是kp_id,键值是知识点的map
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = ead.getAllKnowledgePoints(examId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		
		List<LStrMap<Object>> qiList=viewExamDao.getExamErrorQuestionsToShow(testRecId, examId);	//查出试卷的所有选项及对应的大题和小题信息
		List<LStrMap<Object>> qiCList=new ArrayList<LStrMap<Object>>();
		List tkList=new ArrayList();
		
		//将选项组合成小题
		//遍历所有选项
		for (int i=0; i<qiList.size();) {
			int optNum=Integer.parseInt(qiList.get(i).get("OPTION_NUM").toString());	//得到选项个数
			//如果选项个数等于0说明是阅读理解的文章介绍 
			if(optNum==0){
				qiCList.add(qiList.get(i));	//qiCList中存储该文章介绍
				i=i+1;
				continue;
			}
			
			//新建三个列表，用于存储选项的相应的信息
			List<String> optList = new ArrayList<String>();		//选项内容列表
			List<String> refList = new ArrayList<String>();		//参考答案列表
			List<Integer> idList = new ArrayList<Integer>();	//用于存选项编号
			List<String> optNoList = new ArrayList<String>();	//用于存选项号
			String[] oScoreArr = null;							//用于存选项得分
			
			//将选项得分分割为数组
			if(qiList.get(i).get("O_SCORE")!=null){
				oScoreArr = qiList.get(i).get("O_SCORE").toString().split(",");
			}
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			String kpStr = qiList.get(i).get("knowledge_point").toString();
			List<LStrMap<Object>> kpList = new ArrayList<LStrMap<Object>>();
			String[] kpArr = kpStr.split(",");
			for(String tempKpIdStr : kpArr) {
				LStrMap<Object> tempKpMap = LStrMap.newInstance();
				String tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
				tempKpMap.put("kpName", tempKpName);
				while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
					tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
					tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+"-"+tempKpName;//得到父级知识点和本知识点的组合字符串
				}
				tempKpMap.put("kpPath", tempKpName);
				kpList.add(tempKpMap);
			}
			
			//遍历一个小题的选项记录，封装选项内容、参考答案、编号、选项号这些内容
			//遍历当前小题的选项
			for (int j=i; j<(i+optNum) && j<qiList.size(); j++) {
				if(qiList.get(j).get("OPT_CONTENT") != null) {//如果当前选项内容不是空
					optList.add(qiList.get(j).get("OPT_CONTENT").toString());			//选项列表新增选项内容
				}
				if(qiList.get(j).get("OPT_REFER")!=null) {	//如果参考答案不是空
					refList.add(qiList.get(j).get("OPT_REFER").toString());				//参考答案列表新增内容
				}
				idList.add(Integer.parseInt(qiList.get(j).get("OPT_ID").toString()));	//选项编号列表新增内容
				if(qiList.get(j).get("OPT_NO")!=null){		//如果选项号不是空
					optNoList.add(qiList.get(j).get("OPT_NO").toString());				//选项号列表新增内容
				}
			}
			
			//疑惑项
			List<String> doubtOptionsList = new ArrayList<String>();
			String doubtOptionsStr = qiList.get(i).get("answer").toString();
			Map<String, Object> gsonMap = new GsonBuilder().create().fromJson(doubtOptionsStr, LinkedHashMap.class);
			Iterator<String> iterator = gsonMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				doubtOptionsList.add(gsonMap.get(key).toString());
			}
			
			// 把上面封装起来的相应的列表存到该小题第一个选项记录的里面
			qiList.get(i).put("OPT_CONTENT", optList);	//选项内容
			qiList.get(i).put("OPT_REFER", refList);	//选项参考答案
			qiList.get(i).put("OPT_ID", idList);		//选项编号
			qiList.get(i).put("OPT_NO", optNoList);		//选项号
			qiList.get(i).put("O_SCORE", oScoreArr);	//选项得分
			qiList.get(i).put("KNOWLEDGE_POINT", kpList);//知识点
			
			qiCList.add(qiList.get(i));					//qiCList中存储当前小题
			i = i+optNum;								//列表索引增加
		}
		
		//将小题组合成大题
		Map<String,LinkedHashMap<String, Object>> qiMap = new HashMap<String, LinkedHashMap<String,Object>>();	//用于存储所有小题信息
		List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
		
		//遍历所有大题记录
		for (int i=0; i<qtlist.size(); i++) {
			String qkey = ((LStrMap<Object>)qtlist.get(i)).get("TYPE_ID").toString();//得到对应的大题的编号
			qiMap.put(qkey, new LinkedHashMap<String, Object>());//对应每个大题新建一个LinkedHashMap
		}
		
		//用于存储临时小题的map
		LinkedHashMap<String, Object> tempMap = null;
		//将小题对应到大题
		for (int i = 0; i < qiCList.size(); i++) {	//遍历所有小题信息
			LStrMap<Object> tempQues = qiCList.get(i);
			//获得其键值
			String quesKey = tempQues.get("TYPE_ID").toString();	//获得小题对应的大题编号
			int tempQuesType = Integer.parseInt(tempQues.get("QUES_TYPE").toString());	//获得小题的类型
			int type = Integer.parseInt(tempQues.get("TYPE").toString());			//获得小题对应大题的类型
			
			int quesId = Integer.parseInt(tempQues.get("ques_id").toString());
			String remark = tempQues.get("remark")==null?null:tempQues.get("remark").toString();
			
			if(qiMap.containsKey(quesKey)){	//如果qiMap中存到对应的大题
				tempMap = (LinkedHashMap) qiMap.get(quesKey);	//得到对应的大题列表
			} else {	//如果qiMap中不存在对应的大题列表
				tempMap = new LinkedHashMap<String, Object>();	//新建一个列表
			}
			//判断小题类型是否是6
			if (tempQuesType == 6) {
				//小题如果是材料，则把小题及其下的小题存入到一个ArrayList中，并将ArrayList存入对应大题的的LinkedHashMap
				tempList = new ArrayList<LStrMap<Object>>();	//新建一个临时列表
				tempList.add(qiCList.get(i));	//tempList中增加对应的小题
				if(remark == null){	//如果remark是null，说明该材料下没有小题
					tempMap.put("art"+quesId, tempList);
					qiMap.put(quesKey, tempMap);//将tkList存到qiMap中
					continue;	//跳出当前一次循环
				}
				
				//材料下有小题
				String[] quesIdArr = remark.split(",");	//得到材料对应的小题号
				for (int j=0; j<qiCList.size(); j++) {	//遍历所有小题
					for (int x = 0; x < quesIdArr.length; x++) {	//遍历材料对应的所有小题号
						if(qiCList.get(j).get("QUES_ID").toString().equals(quesIdArr[x])){	//如果对应的小题号等于材料小题号
							tempList.add(qiCList.get(j));	//tempList中增加对应的小题
						}
					}
				}
				tempMap.put("art"+quesId, tempList);
				qiMap.put(quesKey, tempMap);	//将对应的大题放到qiMap中
			} else if (remark==null || remark.equals("")) {//判断小题remark是否为null或""
				//小题是普通小题，则把小题存入对应大题的LinkedHashMap中，键名为normal+小题编号
				tempMap.put("normal"+quesId, tempQues);
				qiMap.put(quesKey, tempMap);
			}
		}
		
		//过滤错题
		Iterator<String> keyIterator = qiMap.keySet().iterator();
		while (keyIterator.hasNext()) {//遍历所有的大题
			String key = keyIterator.next();
			
			LinkedHashMap<String, Object> tempQuesMap = qiMap.get(key);
			Iterator<String> quesKeyIterator = tempQuesMap.keySet().iterator();
			while (quesKeyIterator.hasNext()) {//遍历所有的小题
				String quesKey = quesKeyIterator.next();
				//如果是材料题，且材料题对应的小题数量为1，说明只有材料，没有小题，则要删除材料
				if (quesKey.startsWith("art") && (((List<LStrMap<Object>>)tempQuesMap.get(quesKey)).size() == 1))
				quesKeyIterator.remove();
			}
			
			//小题遍历结束后，判断大题中的小题数量是否为0，如果是，则删除该大题 
			if (tempQuesMap.size() == 0) {
				keyIterator.remove();
				Iterator<LStrMap<Object>> qtListIterator = qtlist.iterator();
				while (qtListIterator.hasNext()) {
					LStrMap<Object> tempQtMap = qtListIterator.next();
					if (tempQtMap.get("type_id").toString().equals(key)) {
						qtListIterator.remove();
						break;
					}
				}
			}
		}
		
		list.add(qtlist);
		list.add(bctList);
		list.add(sctList);
		list.add(qiMap);
		return list;
	}
	
	public void updateTestRecordDetailInfo(long testRecId, Map<String, String> quesScoreJsonMap, Map<String, String> oScoreMap, Map<String, String> teacherCommentMap) {
		Iterator<String> iterator = quesScoreJsonMap.keySet().iterator();
		while (iterator.hasNext()) {
			String quesId = iterator.next();
			String score = quesScoreJsonMap.get(quesId).equals("")?"-1":quesScoreJsonMap.get(quesId);
			String oScore = oScoreMap.get(quesId);
			try {
				String comment = URLDecoder.decode(teacherCommentMap.get(quesId), "utf-8");
				viewExamDao.updateRecordDetailScore(testRecId, Long.parseLong(quesId), Double.parseDouble(score), oScore, comment.equals("")?null:comment);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (score.equals("")) {
				score = "-1";
			}
		}
	}
	
	public int updateRecordMarkFlagAndScore(long testRecId, int markFlag, long creatorId) {
		return viewExamDao.updateRecordMarkFlagAndScore(testRecId, markFlag, creatorId);
	}
	public int updateRecordMarkFlag(long testRecId, int markFlag, long creatorId) {
		return viewExamDao.updateRecordMarkFlagAndScore(testRecId, markFlag, creatorId);
	}
	
	public LStrMap<Object> getMarkNum(long testRecId) {
		List<LStrMap<Object>> list = viewExamDao.getMarkNum(testRecId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public LStrMap<Object> getStudentInfo(long testRecId) {
		List<LStrMap<Object>> list = viewExamDao.getStudentInfo(testRecId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public LStrMap<Object> getClassUnmarkedInfo(long userId, long classId, long testId, long subjectId) {
		List<LStrMap<Object>> list = viewExamDao.getClassUnmarkedInfo(userId, classId, testId, subjectId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<LStrMap<Object>> getGradePaperAnswerCardData(long testRecId) {
		List<LStrMap<Object>> dataList = viewExamDao.getGradePaperAnswerCardData(testRecId);
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

	/**
	 * 获得大题的信息，包括小题数量和大题分数
	 * @param examId
	 * @return
	 */
	public Map<String, LStrMap<Object>> getTypeInfo(int examId) {
		List<LStrMap<Object>> list = viewExamDao.getTypeInfo(examId);
		Map<String, LStrMap<Object>> map = new LinkedHashMap<String, LStrMap<Object>>();
		for (int i=0,size=list.size(); i<size; i++) {
			map.put(list.get(i).get("type_id").toString(), list.get(i));
		}
		return map;
	}

	/**
	 * 更新考试做题明细的录音
	 * @param testRecId 考试记录编号
	 * @param quesId	小题编号
	 * @param path	录音路径
	 */
	public void updateTestRecordDetailCommentRec(int testRecId, int quesId,	String path) {
		viewExamDao.updateTestRecordDetailCommentRec(testRecId, quesId, path);
	}
	
}
