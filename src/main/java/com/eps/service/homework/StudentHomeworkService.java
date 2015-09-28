package com.eps.service.homework;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.Page;
import com.eps.dao.homework.StudentHomeworkDao;
import com.eps.dao.homework.TeacherHomeworkDao;
import com.eps.domain.EHomeworkRecord;
import com.eps.domain.EHomeworkRecordDetail;
import com.eps.service.network.ExamIntelligentlyChooseService;
import com.eps.utils.LStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class StudentHomeworkService {

	@Autowired private StudentHomeworkDao studentHomeworkDao;
	@Autowired private MaxValueINcrementer seq_hwRecId;
	@Autowired private ExamIntelligentlyChooseService eics;
	@Autowired private TeacherHomeworkDao teacherHomeworkDao;
	
	public List<LStrMap<Object>> getHomeworksOfStudents(long userId) {
		return studentHomeworkDao.getHomeworksOfStudents(userId);
	}

	public EHomeworkRecord getHwRecord(int hwId, long userId) {
		List<LStrMap<Object>> list = studentHomeworkDao.getHwRecordInfo(hwId, userId);
		LStrMap<Object> map = list.get(0);
		EHomeworkRecord eHomeworkRecord = new EHomeworkRecord();
		eHomeworkRecord.setHwRecId(seq_hwRecId.nextIntValue());
		eHomeworkRecord.setHwId(hwId);
		eHomeworkRecord.setUserId(userId);
		eHomeworkRecord.setSubjectId((Integer)map.get("subject_id"));
		eHomeworkRecord.setClassId((Integer)(map.get("class_id")));
		eHomeworkRecord.setStudentId((Integer)map.get("student_id"));
		return eHomeworkRecord;
	}

	public int saveEhomeworkRecord(EHomeworkRecord eHomeworkRecord) {
		return studentHomeworkDao.saveEhomeworkRecord(eHomeworkRecord);
	}

	public void saveEhomework(String answers, String markInfo, int hwRecId,
			String studentAnswers, String questionsTime) {
		//删除某次作业记录对应的所有作业详情
		studentHomeworkDao.deleteHwRecordDetail(hwRecId);
		//重新保存作业记录详情
		saveHomeworkInfo(answers, markInfo, hwRecId, studentAnswers, questionsTime);
	}

	private void saveHomeworkInfo(String answers, String markInfo, int hwRecId,
			String studentAnswers, String questionsTime) {
		Gson gson = new GsonBuilder().create();
		Map<String,Object> mapStrAnswer = gson.fromJson(answers,Map.class);				//得到考生答案，原有的
		Map<String,Object> studentAnswersMap = gson.fromJson(studentAnswers,Map.class);	//得到考生答案
		Map<String,Object> mapMark = gson.fromJson(markInfo,Map.class);					//得到标记
		Map<String,Object> mapQuestionsTime = gson.fromJson(questionsTime, Map.class);	//得到每小题的做题时长，用秒表示
		
		String hwId = mapStrAnswer.get("hwId").toString();								//得到作业编号
		List<LStrMap<Object>> answerList = studentHomeworkDao.getAnswers((int)(Double.parseDouble(hwId)));	//得到作业的所有小题和选项
		
		//遍历作业所有选项
		for (int i=0; i<answerList.size(); ) {
			//新建一个作业记录详情
			EHomeworkRecordDetail eHomeworkRecordDetail = new EHomeworkRecordDetail();
			eHomeworkRecordDetail.setHwRecId(hwRecId);
			
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
				eHomeworkRecordDetail.setStudentAnswer(null);
			} else {
				eHomeworkRecordDetail.setStudentAnswer(studentAnswer);
			}
			
			//得到小题的做题用时，以秒为单位
			if (mapQuestionsTime.containsKey(quesId)) {
				eHomeworkRecordDetail.setAnswerTime(mapQuestionsTime.get(quesId).toString());
			}
			
			//得到小题的标记
			if(mapMark.containsKey(quesId)){	//如果标记中包含当前小题的编号的键名
				eHomeworkRecordDetail.setRemark(mapMark.get(quesId).toString());
			}
			
			//得到选项个数，用于跳过多条记录
			int optNum=Integer.parseInt(answerList.get(i).get("OPTION_NUM").toString());
			i=i+optNum;
			
			eHomeworkRecordDetail.setQuesId(Integer.parseInt(quesId));
			eHomeworkRecordDetail.setAnswer(new GsonBuilder().create().toJson(quesMap));
			studentHomeworkDao.saveEhomeworkRecordDetail(eHomeworkRecordDetail);
		}
	}

	public void updateEhomeworkRecord(EHomeworkRecord eHomeworkRecord) {
		studentHomeworkDao.updateEhomeworkRecord(eHomeworkRecord);
	}

	public LStrMap<Object> getHwFinishedNum(int hwRecId) {
		return studentHomeworkDao.getHwFinishedNum(hwRecId).get(0);
	}

	public void updateEhomeworkRecordDetailScore(int hwRecId) {
		studentHomeworkDao.updateEhomeworkRecordDetailScore(hwRecId);
	}

	public LStrMap<Object> getAutoCorrectFlag(int hwRecId) {
		return studentHomeworkDao.getAutoCorrectFlag(hwRecId).get(0);
	}

	public void updateHwRecord(int hwRecId, int correctFlag, int creator) {
		studentHomeworkDao.updateHwRecord(hwRecId, correctFlag, creator);
	}

	public LStrMap<Object> getHwClassInfo(int hwRecId) {
		return studentHomeworkDao.getHwClassInfo(hwRecId).get(0);
	}

	public List<LStrMap<Object>> getQuestionsInfo(int hwRecId) {
		return studentHomeworkDao.getQuestionsInfo(hwRecId);
	}

	public void updateEhomeworkRecordStar(int hwRecId) {
		studentHomeworkDao.updateEhomeworkRecordStar(hwRecId);
	}

	public LStrMap<Object> getHwRecordInfo(int hwRecId) {
		return studentHomeworkDao.getHwRecordInfo(hwRecId).get(0);
	}

	public List<LStrMap<Object>> getCommitedHomeworks(long userId) {
		return studentHomeworkDao.getCommitedHomeworks(userId);
	}

	public Map<String, Object> getQuestions(long hwRecId, int hwId) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		//得到对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = teacherHomeworkDao.getKnowledgePointsByHwId(hwId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		
		List<LStrMap<Object>> qiList = studentHomeworkDao.getQuestions(hwRecId, hwId);		//查出作业的所有选项及对应的大题和小题信息
		List<LStrMap<Object>> qiCList = new ArrayList<LStrMap<Object>>();
		
		//整合 选项和答案
		for (int i = 0; i < qiList.size();) {	//遍历所有选项
			int optNum=Integer.parseInt(qiList.get(i).get("option_num").toString());//得到选项个数
			if(optNum==0){	//如果选项个数等于0说明是阅读理解的文章介绍 
				qiCList.add(qiList.get(i));	//qiCList中存储该文章介绍
				i=i+1;
				continue;
			}
			
			//新建三个列表，用于存储相应的信息
			List<String> optList=new ArrayList<String>();		//选项列表
			List<String> refList=new ArrayList<String>();		//参考答案列表
			List<Integer> idList=new ArrayList<Integer>();		//用于存选项编号
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
				tempKpMap.put("kpId", tempKpIdStr);
				String tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
				tempKpMap.put("kpName", tempKpName);
				while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
					tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
					tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+" - "+tempKpName;//得到父级知识点和本知识点的组合字符串
				}
				tempKpMap.put("kpPath", tempKpName);
				kpList.add(tempKpMap);
			}
			
			//遍历一个小题的选项记录，封装选项内容、参考答案、编号、选项号这些内容
			for (int j = i; j <(i+optNum) && j<qiList.size(); j++) {			//遍历当前小题的选项
				if(qiList.get(j).get("OPT_CONTENT")!=null){						//如果当前选项内容不是空
					optList.add(qiList.get(j).get("OPT_CONTENT").toString());	//选项列表新增选项内容
				}
				if(qiList.get(j).get("OPT_REFER")!=null){						//如果参考答案不是空
					if(qiList.get(j).get("OPT_ID")!=null){						//如果选项编号不是空
						refList.add(qiList.get(j).get("OPT_REFER").toString());	//参考答案列表新增内容
					}
				}
				if(qiList.get(j).get("OPT_ID")!=null){							//如果选项编号不是空
					idList.add(Integer.parseInt(qiList.get(j).get("OPT_ID").toString()));	//选项编号列表新增内容
				}
				if(qiList.get(j).get("OPT_NO")!=null){	//如果选项号不是空
					optNoList.add(qiList.get(j).get("OPT_NO").toString());	//选项号列表新增内容
				}
			}
			
			// 把上面封装起来的相应的列表存到该小题第一个选项记录的里面
			qiList.get(i).put("OPT_CONTENT", optList);	//选项内容
			qiList.get(i).put("OPT_REFER", refList);	//选项参考答案
			qiList.get(i).put("OPT_ID", idList);		//选项编号
			qiList.get(i).put("OPT_NO", optNoList);		//选项号
			qiList.get(i).put("O_SCORE", oScoreArr);	//选项得分
			qiList.get(i).put("KNOWLEDGE_POINT", kpList);//知识点
			
			qiCList.add(qiList.get(i));	//qiCList中存储当前小题
			i=i+optNum;					//列表索引增加
		}
		
		for (int i = 0; i < qiCList.size(); i++) {	//遍历所有小题信息
			LStrMap<Object> oneQuestion = qiCList.get(i);
			int quesType = Integer.parseInt(oneQuestion.get("QUES_TYPE").toString());	//获得小题的类型
			Object remark = oneQuestion.get("remark");
			int mTopic = Integer.parseInt(oneQuestion.get("m_topic").toString());
			if (quesType != 6) {
				if (remark == null) {
					map.put("normal"+mTopic, oneQuestion);
				}
			} else {
				List<Object> tempReadList = new ArrayList<Object>();
				tempReadList.add(oneQuestion);
				if (remark==null || remark.equals("")) {
					map.put("read"+mTopic, tempReadList);
				} else {
					//如果当前的“小题”是文章的介绍，进行下面的操作
					String[] quesIdArr = remark.toString().split(",");	//得到文章对应的小题号
					for (int j = 0; j < qiCList.size(); j++) {	//遍历所有小题
						for (int x = 0; x < quesIdArr.length; x++) {	//遍历文章对应的所有小题号
							if(qiCList.get(j).get("QUES_ID").toString().equals(quesIdArr[x])){	//如果对应的小题号等于文章小题号
								tempReadList.add(qiCList.get(j));	//tempList中增加对应的小题
								break;
							}
						}
					}
					map.put("read"+mTopic, tempReadList);
				}
			}
		}
		
		return map;
	}

	public List<LStrMap<Object>> getQuestionsFlag(long hwRecId) {
		return studentHomeworkDao.getQuestionsFlag(hwRecId);
	}

	public List<LStrMap<Object>> getHwRecordId(int hwId, long userId) {
		return studentHomeworkDao.getHwRecordId(hwId, userId);
	}

	/**
	 * 如果传入的小题是材料，则可根据quesId获得该材料下的所有小题
	 * @param quesId
	 * @return
	 */
	public List<LStrMap<Object>> getNormalQuestions(int quesId) {
		return studentHomeworkDao.getNormalQuestions(quesId);
	}

	public List<LStrMap<Object>> getFavoriteKnowledgePoints(long userId, int subjectId) {
		return studentHomeworkDao.getFavoriteKnowledgePoints(userId, subjectId);
	}

	/**
	 * 得到收藏的小题，返回的是格式化之后的所有小题，即将“材料小题”和对应的“材料”组合到一起
	 * @param userId
	 * @param kpId
	 * @return
	 */
	public Map<String, Object> getFavoriteQuestions(long userId, int kpId, int subjectId) {
		//获得普通小题
		List<LStrMap<Object>> normalQuestions = studentHomeworkDao.getFavoriteNormalQuestion(userId, kpId);
		//获得普通小题对应的选项
		List<LStrMap<Object>> optionsOfNormalQuestions = studentHomeworkDao.getOptionsOfFavoriteNormalQuestion(userId, kpId);
		//获得材料及材料下小题
		List<LStrMap<Object>> articleQuestions = studentHomeworkDao.getFavoriteArticleQuestion(userId, kpId);
		//获得材料下小题对应的选项
		List<LStrMap<Object>> optionsOfArticleQuestions = studentHomeworkDao.getOptionsOfFavoriteArticleQuestion(userId, kpId);
		
		//得到对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = teacherHomeworkDao.getKnowledgePointsBySubject(subjectId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		
		//将普通小题的选项跟小题组合到一起
		int nowIndex = 0;//用于记录索引位置
		for (int i=0,size=normalQuestions.size(); i<size; i++) {
			LStrMap<Object> quesMap = normalQuestions.get(i);
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			String kpStr = quesMap.get("knowledge_point").toString();
			List<LStrMap<Object>> kpList = new ArrayList<LStrMap<Object>>();
			String[] kpArr = kpStr.split(",");
			for(String tempKpIdStr : kpArr) {
				LStrMap<Object> tempKpMap = LStrMap.newInstance();
				tempKpMap.put("kpId", tempKpIdStr);
				String tempKpName = "";
				if (kpMap.get(tempKpIdStr).get("kp_name") != null) {
					tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
				}
				tempKpMap.put("kpName", tempKpName);
				while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
					tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
					tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+" - "+tempKpName;//得到父级知识点和本知识点的组合字符串
				}
				tempKpMap.put("kpPath", tempKpName);
				kpList.add(tempKpMap);
			}
			quesMap.put("KNOWLEDGE_POINT", kpList);//知识点
			
			//从nowIndex开始遍历普通选项
			for (int j=nowIndex,innerSize=optionsOfNormalQuestions.size(); j<innerSize; j++) {
				nowIndex = j;
				LStrMap<Object> optMap = optionsOfNormalQuestions.get(j);
				//判断当前选项是否是该小题的，如果不是，就跳出循环
				if (quesMap.get("ques_id").toString().equals(optMap.get("ques_id").toString())) {
					if (quesMap.get("opt_no") == null) {//如果当前是第一个选项
						List<String> optNoList = new ArrayList<String>();//新建optNoList
						List<Integer> optIdList = new ArrayList<Integer>();//新建optIdList
						List<String> optReferList = new ArrayList<String>();//新建optReferList
						List<String> optContentList = new ArrayList<String>();//新建optContentList
						
						optNoList.add(optMap.get("opt_no").toString());
						optIdList.add(Integer.parseInt(optMap.get("opt_id").toString()));
						optReferList.add(optMap.get("opt_refer").toString());
						optContentList.add(optMap.get("opt_content").toString());
						
						quesMap.put("opt_no", optNoList);
						quesMap.put("opt_id", optIdList);
						quesMap.put("opt_content", optContentList);
						quesMap.put("opt_refer", optReferList);
					} else {//如果不是第一个选项
						((List<String>)quesMap.get("opt_no")).add(optMap.get("opt_no").toString());
						((List<Integer>)quesMap.get("opt_id")).add(Integer.parseInt(optMap.get("opt_id").toString()));
						((List<String>)quesMap.get("opt_refer")).add(optMap.get("opt_refer").toString());
						((List<String>)quesMap.get("opt_content")).add(optMap.get("opt_content").toString());
					}
					nowIndex++;
				} else {
					break;
				}
			}
		}
		
		//将材料小题的选项跟小题组合到一起
		nowIndex = 0;
		for (int i=0,size=articleQuestions.size(); i<size; i++) {
			LStrMap<Object> quesMap = articleQuestions.get(i);
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			String kpStr = quesMap.get("knowledge_point").toString();
			List<LStrMap<Object>> kpList = new ArrayList<LStrMap<Object>>();
			String[] kpArr = kpStr.split(",");
			for(String tempKpIdStr : kpArr) {
				LStrMap<Object> tempKpMap = LStrMap.newInstance();
				tempKpMap.put("kpId", tempKpIdStr);
				String tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
				tempKpMap.put("kpName", tempKpName);
				while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
					tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
					tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+" - "+tempKpName;//得到父级知识点和本知识点的组合字符串
				}
				tempKpMap.put("kpPath", tempKpName);
				kpList.add(tempKpMap);
			}
			quesMap.put("KNOWLEDGE_POINT", kpList);//知识点
			
			//从nowIndex开始遍历材料小题选项
			for (int j=nowIndex,innerSize=optionsOfArticleQuestions.size(); j<innerSize; j++) {
				nowIndex = j;
				LStrMap<Object> optMap = optionsOfArticleQuestions.get(j);
				//判断当前选项是否是该小题的，如果不是，就跳出循环
				if (quesMap.get("ques_id").toString().equals(optMap.get("ques_id").toString())) {
					if (quesMap.get("opt_no") == null) {//如果当前是第一个选项
						List<String> optNoList = new ArrayList<String>();//新建optNoList
						List<Integer> optIdList = new ArrayList<Integer>();//新建optIdList
						List<String> optReferList = new ArrayList<String>();//新建optReferList
						List<String> optContentList = new ArrayList<String>();//新建optContentList
						
						optNoList.add(optMap.get("opt_no").toString());
						optIdList.add(Integer.parseInt(optMap.get("opt_id").toString()));
						optReferList.add(optMap.get("opt_refer").toString());
						optContentList.add(optMap.get("opt_content").toString());
						
						quesMap.put("opt_no", optNoList);
						quesMap.put("opt_id", optIdList);
						quesMap.put("opt_content", optContentList);
						quesMap.put("opt_refer", optReferList);
					} else {//如果不是第一个选项
						((List<String>)quesMap.get("opt_no")).add(optMap.get("opt_no").toString());
						((List<Integer>)quesMap.get("opt_id")).add(Integer.parseInt(optMap.get("opt_id").toString()));
						((List<String>)quesMap.get("opt_refer")).add(optMap.get("opt_refer").toString());
						((List<String>)quesMap.get("opt_content")).add(optMap.get("opt_content").toString());
					}
					nowIndex++;
				} else {
					break;
				}
			}
		}
		
		//map用于存放最终结果
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		//将普通小题放到map中
		for (int i=0,size=normalQuestions.size(); i<size; i++) {
			map.put("normal"+normalQuestions.get(i).get("ques_id").toString(), normalQuestions.get(i));
		}
		
		//将材料小题和材料组合到一起，并放到map中
		for (int i=0,size=articleQuestions.size(); i<size; i++) {	//遍历所有小题信息
			LStrMap<Object> oneQuestion = articleQuestions.get(i);
			int quesType = Integer.parseInt(oneQuestion.get("QUES_TYPE").toString());	//获得小题的类型
			Object remark = oneQuestion.get("remark");
			int quesId = Integer.parseInt(oneQuestion.get("ques_id").toString());
			
			//当小题是材料时，将其及其下的小题组合，不是材料则不做处理
			if (quesType == 6) {
				List<Object> tempReadList = new ArrayList<Object>();
				tempReadList.add(oneQuestion);
				if (remark==null || remark.equals("")) {
					map.put("read"+quesId, tempReadList);
				} else {
					String[] quesIdArr = remark.toString().split(",");	//得到文章对应的小题号
					for (int j=0,innerSize=articleQuestions.size(); j<innerSize ; j++) {	//遍历所有小题
						for (int x=0,length=quesIdArr.length; x<length; x++) {	//遍历文章对应的所有小题号
							if(articleQuestions.get(j).get("QUES_ID").toString().equals(quesIdArr[x])){	//如果对应的小题号等于文章小题号
								tempReadList.add(articleQuestions.get(j));	//tempList中增加对应的小题
								break;
							}
						}
					}
					map.put("read"+quesId, tempReadList);
				}
			}
		}
		
		return map;
	}

	/**
	 * 得到错题集的知识点数据
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param quesType 题目类型
	 * @param keyword 关键字
	 * @return 一个知识点List
	 */
	public List<LStrMap<Object>> getErrorQuesKps(long userId, int subjectId, int tsId, String keyword) {
		return studentHomeworkDao.getErrorQuesKps(userId, subjectId, tsId, keyword);
	}

	public List<Object> getErrorQuestions(long userId, int subjectId, int tsId, int kpId, String keyword, int pageNo) {
		//得到对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<LStrMap<Object>> kpMap = eics.getKnowledgepointsOfSubject(subjectId);
		int nowIndex = 0;//用于记录索引位置
		Map<String, Object> map = new LinkedHashMap<String, Object>();//map用于存放最终结果
		
		Page page = studentHomeworkDao.getErrorQuesPage(userId, subjectId, tsId, keyword, kpId, pageNo);
		List<LStrMap<Object>> questions = studentHomeworkDao.getErrorQuestions(userId, subjectId, tsId, keyword, kpId, Page.getStartOfPage(pageNo), page.getData().size());
		List<LStrMap<Object>> options = studentHomeworkDao.getOptionsOfErrorQuestions(userId, subjectId, tsId, keyword, kpId, Page.getStartOfPage(pageNo), page.getData().size());

		eics.fixQuesAndOpts(kpMap, nowIndex, questions, options);
		//将普通小题放到map中
		for (int i=0,size=questions.size(); i<size; i++) {
			map.put("normal"+questions.get(i).get("ques_id").toString(), questions.get(i));
		}
		
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(map);
		resultList.add(page);
		return resultList;
	}

}