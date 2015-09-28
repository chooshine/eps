package com.eps.service.homework;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.eps.dao.homework.TeacherHomeworkDao;
import com.eps.domain.EHomework;
import com.eps.domain.EHomeworkClass;
import com.eps.domain.EHomeworkQuestion;
import com.eps.domain.EOption;
import com.eps.domain.EQuestion;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class TeacherHomeworkService {

	@Autowired private MaxValueINcrementer seq_hwId;
	@Autowired private MaxValueINcrementer seq_quesId;
	@Autowired private MaxValueINcrementer seq_optId;
	@Autowired private ExamAfterDao examAfterDao;
	@Autowired private TeacherHomeworkDao thd;
	
	
	public List<Integer> saveQuestion(EHomeworkQuestion eHomeworkQuestion, EQuestion eQuestion, EOption eOption) {
		List<Integer> idList = new ArrayList<Integer>();
		int quesId = seq_quesId.nextIntValue();	//生成一个小题编号
		idList.add(quesId);
		
		eQuestion.setQuesId(quesId);
		eHomeworkQuestion.setQuesId(quesId);
		
		Gson gson = new  GsonBuilder().create();
		Map quesReferMap=gson.fromJson(eQuestion.getQuesRefer(),Map.class);	//得到小题选项内容
		Map mapContemt=gson.fromJson(eOption.getOptContent(),Map.class);	//得到选项内容
		Map mapContemt2=gson.fromJson(eOption.getOptRefer(),Map.class);		//得到选项参考答案
		Map mapContemt3=gson.fromJson(eOption.getOptNo(), Map.class);		//得到选项号，如ABCD或者对错或者1,2,3
		
		//设置小题正确答案，设置分隔符为@@
		String quesReferStr = "";
		for (int i = 1; i <= quesReferMap.size(); i++) {
			//如果小题答案中含有当前索引
			if(quesReferMap.containsKey(i+"")){
				quesReferStr=quesReferStr+quesReferMap.get(i+"").toString()+"@@";
			}
		}
		
		if (quesReferStr.length() >= 2) {
			quesReferStr = quesReferStr.substring(0, quesReferStr.length()-2);
		}
		if (quesReferStr.equals("")) {
			eQuestion.setQuesRefer(null);
		} else {
			eQuestion.setQuesRefer(quesReferStr);
		}
		examAfterDao.saveQuestionDao(eQuestion);	//保存小题
		
		//从索引1开始，遍历参考答案
		for (int i = 1; i <= mapContemt2.size(); i++) {
			if(mapContemt.containsKey(i+"")){	//如果选项内容中包含该索引键值
				eOption.setOptContent(mapContemt.get(i+"").toString());	//设置选项内容
			}else{
				eOption.setOptContent("");
			}
			
			//设置选项号
			if (mapContemt3.containsKey(i+"")) {
				eOption.setOptNo(mapContemt3.get(i+"").toString());
			}else {
				eOption.setOptNo("");
			}
			
			eOption.setOptRefer(mapContemt2.get(i+"").toString());	//设置选项的参考答案
			int optId = seq_optId.nextIntValue();					//获得一个选项的编号
			idList.add(optId);										//把选项编号放到idList中
			
			eOption.setQuesId(quesId);
			eOption.setOptId(optId);
			examAfterDao.saveOptionDao(eOption);					//保存一个选项
		}
		
		thd.saveHomeworkQuestion(eHomeworkQuestion);	//保存一个作业小题关系
		
		return idList;
	}

	public EHomework saveHomework(EHomework eHomework) {
		int hwId = seq_hwId.nextIntValue();
		eHomework.setHwId(hwId);
		thd.saveHomework(eHomework);
		return eHomework;
	}

	public void updateHomework(EHomework eHomework) {
		thd.updateHomework(eHomework);
	}

	//插入一条小题记录，插入一条作业小题记录
	public int saveArticle(EQuestion eQuestion, EHomeworkQuestion eHomeworkQuestion) {
		int quesId=seq_quesId.nextIntValue();	//获得一个小题编号
		eQuestion.setQuesId(quesId);			//设置小题编号
		examAfterDao.saveQuestionDao(eQuestion);//保存一个小题
		eHomeworkQuestion.setQuesId(quesId);
		thd.saveHomeworkQuestion(eHomeworkQuestion);	//保存一个作业小题关系
		return quesId;
	}

	public LStrMap<Object> getHwInfoByHwId(int hwId) {
		List<LStrMap<Object>> list = thd.getHwInfoByHwId(hwId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	//得到一次作业的所有信息
	public Map<String, Object> getFullHwInfoByHwId(int hwId, int hwRecId) {
		List<LStrMap<Object>> qiList = thd.getAllQuesInfoByHwId(hwId, hwRecId);	//查出作业的所有选项及对应的大题和小题信息
		return getHomeworkDetailInfo(hwId, qiList);
	}

	/**
	 * 得到作业的详细信息
	 * @param hwId
	 * @param qiList
	 * @return
	 */
	private Map<String, Object> getHomeworkDetailInfo(int hwId, List<LStrMap<Object>> qiList) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		//得到这张作业对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = thd.getKnowledgePointsByHwId(hwId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		
		List<LStrMap<Object>> qiCList = new ArrayList<LStrMap<Object>>();
		
		//整合 选项和答案
		for (int i = 0; i < qiList.size();) {	//遍历所有选项
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
			
			int optNum=Integer.parseInt(qiList.get(i).get("option_num").toString());//得到选项个数
			if(optNum==0){	//如果选项个数等于0说明是阅读理解的文章介绍 
				qiList.get(i).put("KNOWLEDGE_POINT", kpList);//知识点
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
			/*1、判断小题类型，有两种情况，普通小题和文章小题
			  2、如果是普遍小题判断小题的remark是否为null，
				（1）如果为null说明不是阅读理解下的小题，此时，设键名为normal+小题号，键值为小题信息存到map中，i++
				（2）如果不为null，则i++
			  3、如果是文章小题
			  	（0）新建一个ArrayList，将文章小题存到ArrayList中
			  	（1）判断文章小题的remark字段是否null或者为空字符串
			  	（2）如果为null或空字符串，则以read+小题号为键名，ArrayList为键值存到map中，i++
			  	（3）如果remark不为null且不是空字符串,得到remark字段分隔成的数组
			  	（4）遍历所有小题和数组，如果小题编号等于数组值，则将小题放到ArrayList中
			  	（5）i++
			*/
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

	/**
	 * 更新时，删除一个小题
	 * @param hwId 作业编号
	 * @param quesId 小题编号
	 */
	public void deleteOneQuestion(int hwId, int quesId) {
		thd.deleteOneHwQuesRelation(hwId, quesId);	//删除e_hw_question表中，当前小题对应的记录
		//如果小题未发布，则允许删除
		if (examAfterDao.getQuestionByQuesId(quesId).getReleaseFlag() == 0) {
			examAfterDao.deleteQuestionDao(quesId);
			examAfterDao.deleteOptionDao(quesId);
		}
	}
	
	public void updateMTopics(UStrMap<Object> map) {
		thd.updateMTopics(map);
	}

	public void deleteArticleAndQuestions(int hwId, String quesIds) {
		List<Integer> list = new ArrayList<Integer>();
		String[] quesIdArray =  quesIds.split(",");
		for (int i = 0; i < quesIdArray.length; i++) {
			list.add(Integer.parseInt(quesIdArray[i]));
		}
		//删除材料及小题和作业的关系
		thd.deleteArticleAndQuestionsInHw(hwId, list);
		//如果材料是未发布，则删除数据库中的材料及小题
		if (examAfterDao.getQuestionByQuesId(list.get(0)).getReleaseFlag() == 0) {
			examAfterDao.deleteQuestions(list);
			for (int i=0, size=list.size(); i<size; i++) {//删除选项
				examAfterDao.deleteOptionDao(list.get(i));
			}
		}
	}

	public void updateMTopicAfterArticle(int hwId, int mTopic, int mTopicNum) {
		thd.updateMTopicAfterArticle(hwId, mTopic, mTopicNum);
	}

	/**
	 * 更新作业的小题数
	 * @param hwId 作业编号
	 * @param addNum 增加的小题数量
	 */
	public void updateHomeworkTopicNumByHwId(int hwId, int addNum) {
		thd.updateTopicNumByHwId(hwId,addNum);
	}

	public void updateQuestionMTopic(int hwId, int quesId, int moveNum) {
		thd.updateQuestionMTopic(hwId, quesId, moveNum);
	}

	public void updatePrevQuestionsMTopic(int hwId, int quesId, int moveNum) {
		thd.updatePrevQuestionsMTopic(hwId, quesId, moveNum);
	}

	public void updateNextQuestionsMTopic(int hwId, int quesId, int moveNum) {
		thd.updateNextQuestionsMTopic(hwId, quesId, moveNum);
	}

	public Object getHwAutoCorrectFlag(int hwId, long userId) {
		return thd.getHwAutoCorrectFlag(hwId, userId).get(0).get("auto_correct");
	}

	public List<LStrMap<Object>> getClassList(int hwId, int subjectId, long userId) {
		return thd.getClassList(hwId, subjectId, userId);
	}

	public void saveHomeworkClass(EHomeworkClass eHomeworkClass) {
		thd.saveHomeworkClass(eHomeworkClass);
	}

	public List<LStrMap<Object>> getSettleClassNameList(int hwId, long userId) {
		return thd.getSettleClassNameList(hwId, userId);
	}

	public void deleteHwClass(EHomeworkClass eHomeworkClass) {
		thd.deleteHwClass(eHomeworkClass.getHwId(), eHomeworkClass.getClassId());
	}

	public void updateHomeworkToRelease(int hwId) {
		thd.updateHomeworkToRelease(hwId);
	}

	public LStrMap<Object> getHwStatisticByUserId(long userId) {
		List<LStrMap<Object>> list = thd.getHwStatisticByUserId(userId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<LStrMap<Object>> getUnSettleHomeworks(long userId) {
		return thd.getUnSettleHomeworks(userId);
	}

	public void updateHomeworkToDeletedStatus(int hwId) {
		thd.updateHomeworkToDeletedStatus(hwId);
	}

	public void updateHomeworkQuestionsSubjectNo(int hwId, int subjectId) {
		thd.updateHomeworkQuestionsSubjectNo(hwId ,subjectId);
	}

	public List<LStrMap<Object>> getHomeworksOfTeacher(long userId, Date releaseTime, int subjectId) {
		return thd.getHomeworksOfTeacher(userId, releaseTime, subjectId);
	}

	public void setHwShareFlag(int hwId, int shareFlag) {
		thd.setHwShareFlag(hwId, shareFlag);
	}

	public List<LStrMap<Object>> searchSharedHomeworks(Date releaseTime, int schoolId, int subjectId, long userId) {
		return thd.searchSharedHomeworks(releaseTime, schoolId, subjectId, userId);
	}

	public List<LStrMap<Object>> getHwQuestionIds(int hwId) {
		return thd.getHwQuestionIds(hwId);
	}

	public void deleteOneHwQuesRelation(int hwId, int quesId) {
		thd.deleteOneHwQuesRelation(hwId, quesId);
	}

	public List<LStrMap<Object>> getHwQuesRelationList(int hwId) {
		return thd.getHwQuesRelationList(hwId);
	}

	public void saveHomeworkQuestion(EHomeworkQuestion eHomeworkQuestion) {
		thd.saveHomeworkQuestion(eHomeworkQuestion);
	}

	public Object getHwUsefulFlag(int hwId, long userId) {
		return thd.getHwAutoCorrectFlag(hwId, userId).get(0).get("useful_status");
	}

	public LinkedHashMap<String, List<LStrMap<Object>>> getPastHomeworks(long userId) {
		List<LStrMap<Object>> list = thd.getPastHomeworks(userId);
		LinkedHashMap<String, List<LStrMap<Object>>> map = new LinkedHashMap<String, List<LStrMap<Object>>>();
		for (int i = 0; i < list.size(); i++) {
			LStrMap<Object> tempMap = list.get(i);
			if (map.containsKey(tempMap.get("hw_name"))) {
				map.get(tempMap.get("hw_name").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(tempMap);
				map.put(tempMap.get("hw_name").toString(), tempList);
			}
		}
		return map;
	}

	public List<LStrMap<Object>> getCorrectInfoOfOneClass(int hwId, int classId) {
		return thd.getCorrectInfoOfOneClass(hwId, classId);
	}

	public List<LStrMap<Object>> getQuestionsInfo(int hwRecId) {
		return thd.getQuestionsInfo(hwRecId);
	}

	public LStrMap<Object> getCorrectNum(int hwRecId) {
		List<LStrMap<Object>> list = thd.getCorrectNum(hwRecId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getStudentHomeworkInfo(int hwId, int hwRecId, int quesType) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		//得到这张作业对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<Object> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = thd.getKnowledgePointsByHwId(hwId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap.get("kp_name"));
		}
		
		List<LStrMap<Object>> qiList = thd.getStudentHomeworkInfo(hwId, hwRecId, quesType);//查出作业的所有选项及对应的大题和小题信息
		List<LStrMap<Object>> qiCList = new ArrayList<LStrMap<Object>>();
		
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
			List<Object> kpList = new ArrayList<Object>();
			String[] kpArr = kpStr.split(",");
			for(String tempKpStr : kpArr) {
				kpList.add(kpMap.get(tempKpStr));
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
			qiList.get(i).put("Doubt_Options", doubtOptionsList);
			qiList.get(i).put("KNOWLEDGE_POINT", kpList);//知识点
			
			qiCList.add(qiList.get(i));					//qiCList中存储当前小题
			i = i+optNum;								//列表索引增加
		}
		
		
		//遍历所有小题信息
		for (int i = 0; i < qiCList.size(); i++) {	//遍历所有小题信息
			/*1、判断小题类型，有两种情况，普通小题和文章小题
			  2、如果是普遍小题判断小题的remark是否为null，
				（1）如果为null说明不是阅读理解下的小题，此时，设键名为normal+小题号，键值为小题信息存到map中，i++
				（2）如果不为null，则i++
			  3、如果是文章小题
			  	（0）新建一个ArrayList，将文章小题存到ArrayList中
			  	（1）判断文章小题的remark字段是否null或者为空字符串
			  	（2）如果为null或空字符串，则以read+小题号为键名，ArrayList为键值存到map中，i++
			  	（3）如果remark不为null且不是空字符串,得到remark字段分隔成的数组
			  	（4）遍历所有小题和数组，如果小题编号等于数组值，则将小题放到ArrayList中
			  	（5）i++
			*/
			LStrMap<Object> oneQuestion = qiCList.get(i);
			int tempQuesType = Integer.parseInt(oneQuestion.get("QUES_TYPE").toString());	//获得小题的类型
			Object remark = oneQuestion.get("remark");
			int mTopic = Integer.parseInt(oneQuestion.get("m_topic").toString());
			if (tempQuesType != 6) {//小题是普通小题
				if (remark == null) {//小题不是材料下小题
					map.put("normal"+mTopic, oneQuestion);
				}
			} else {//小题是材料
				List<Object> tempReadList = new ArrayList<Object>();
				tempReadList.add(oneQuestion);
				if (remark==null || remark.equals("")) {//材料remark为空，说明该材料下没有小题
					if (quesType == -1) {
						map.put("read"+mTopic, tempReadList);
					}
				} else {
					String[] quesIdArr = remark.toString().split(",");	//得到文章对应的小题号
					for (int j = 0; j < qiCList.size(); j++) {			//遍历所有小题
						for (int x = 0; x < quesIdArr.length; x++) {	//遍历文章对应的所有小题号
							if(qiCList.get(j).get("QUES_ID").toString().equals(quesIdArr[x])){	//如果对应的小题号等于文章小题号
								tempReadList.add(qiCList.get(j));		//tempList中增加对应的小题
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

	public void updateEHomeworkRecordDetailInfo(int hwRecId, Map<String, String> quesScoreJsonMap, Map<String, String> oScoreMap, Map<String, String> teacherCommentMap) {
		Iterator<String> iterator = quesScoreJsonMap.keySet().iterator();
		while (iterator.hasNext()) {
			String quesId = iterator.next();
			String score = quesScoreJsonMap.get(quesId);
			String oScore = oScoreMap.get(quesId);
			try {
				String comment = URLDecoder.decode(teacherCommentMap.get(quesId), "utf-8");
				if (score.equals("")) {
					score = "-1";
				}
				thd.updateEHomeworkRecordDetailInfo(hwRecId, Integer.parseInt(quesId), Double.parseDouble(score), oScore, comment.equals("")?null:comment);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateHomeworkRecordCorrectFlag(int hwRecId, int correctFlag, long userId, int star, String comment) {
		thd.updateHomeworkRecordCorrectFlag(hwRecId, correctFlag, userId, star, comment);
	}

	public LStrMap<Object> getStudentInfo(int hwRecId) {
		return thd.getStudentInfo(hwRecId).get(0);
	}

	public LStrMap<Object> getClassUnmarkedInfo(int hwRecId) {
		return thd.getClassUnmarkedInfo(hwRecId).get(0);
	}

	public LStrMap<Object> getNextHomework(int hwRecId) {
		List<LStrMap<Object>> list = thd.getNextHomework(hwRecId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 测试老师所在学校是否开通课后作业服务，如果有开通，则返回true，否则，返回false
	 * @param userId
	 * @return
	 */
	public boolean existHomeworkServiceOfTeacherSchool(long userId) {
		if (thd.getTeacherId(userId).size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 测试学生所在学校是否开通课后作业服务，如果有开通，则返回true，否则，返回false
	 * @param userId
	 * @return
	 */
	public boolean existHomeworkServiceOfStudentSchool(long userId) {
		if (thd.getStudentId(userId).size() > 0) {
			return true;
		}
		return false;
	}

	public void updateHomeworkName(int hwId, String hwName) {
		thd.updateHomeworkName(hwId, hwName);
	}

	public void clearUnStartedHomeworkArranges(int hwId) {
		thd.clearUnStartedHomeworkArranges(hwId);
	}

	public LStrMap<Object> getClassBasicCorrectInfo(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getClassBasicCorrectInfo(hwId, classId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<LStrMap<Object>> getUnCommitStudents(int hwId, int classId) {
		return thd.getUnCommitStudents(hwId, classId);
	}

	public List<LStrMap<Object>> getStudentsNumOfStar(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getStudentsNumOfStar(hwId, classId);
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("5", -1);
		map.put("4", -1);
		map.put("3", -1);
		map.put("2", -1);
		map.put("1", -1);
		map.put("0", -1);
		for (int i=0; i<list.size(); i++) {
			map.put(list.get(i).get("star").toString(), Integer.parseInt(list.get(i).get("star_num").toString()));
		}
		List<LStrMap<Object>> result = new ArrayList<LStrMap<Object>>();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			LStrMap<Object> tempMap = LStrMap.newInstance();
			String key = iterator.next();
			int value = map.get(key);
			if (map.get(key) == -1) {
				value = 0;
			}
			tempMap.put("starNum", key);
			tempMap.put("stuNum", value);
			result.add(tempMap);
		}
		
		return result;
	}

	public Map<String, List<LStrMap<Object>>> getStudentsOfStar(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getStudentsOfStar(hwId, classId);
		Map<String, List<LStrMap<Object>>> map = new LinkedHashMap<String, List<LStrMap<Object>>>();
		map.put("5", new ArrayList<LStrMap<Object>>());
		map.put("4", new ArrayList<LStrMap<Object>>());
		map.put("3", new ArrayList<LStrMap<Object>>());
		map.put("2", new ArrayList<LStrMap<Object>>());
		map.put("1", new ArrayList<LStrMap<Object>>());
		map.put("0", new ArrayList<LStrMap<Object>>());
		
		for (int i=0; i<list.size(); i++) {
			map.get(list.get(i).get("star").toString()).add(list.get(i));
		}
		return map;
	}

	public List<LStrMap<Object>> getStudentsNumOfQuestions(int hwId, int classId) {
		return thd.getStudentsNumOfQuestions(hwId, classId);
	}

	public Map<String, ArrayList<Object>> getErrorStudentsOfQuestions(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getErrorStudentsOfQuestions(hwId, classId);
		Map<String, ArrayList<Object>> map = new LinkedHashMap<String, ArrayList<Object>>();
		for (int i=0; i<list.size(); i++) {
			LStrMap<Object> tempMap = list.get(i);
			String tempKey = tempMap.get("m_topic").toString();
			if (map.containsKey(tempKey)) {
				map.get(tempKey).add(tempMap);
			} else {
				ArrayList<Object> tempList = new ArrayList<Object>();
				tempList.add(tempMap);
				map.put(tempKey, tempList);
			}
		}
		return map;
	}

	public LStrMap<List<LStrMap<Object>>> getErrorInfoOfQuestions(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getStudentsNumOfQuestions(hwId, classId);
		LStrMap<List<LStrMap<Object>>> map = LStrMap.newInstance();
		for (int i=0; i<list.size(); i++) {
			List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
			LStrMap<Object> tempMap = list.get(i);
			int classNum = Integer.parseInt(tempMap.get("class_num").toString());
			int errorNum = Integer.parseInt(tempMap.get("error_num").toString());
			int rightNum = classNum - errorNum;
			String quesId = tempMap.get("ques_id").toString();
			LStrMap<Object> map1 = LStrMap.newInstance();
			LStrMap<Object> map2 = LStrMap.newInstance();
			map1.put("type", "错误");
			map1.put("num", errorNum);
			map2.put("type", "正确");
			map2.put("num", rightNum);
			tempList.add(map1);
			tempList.add(map2);
			map.put(quesId, tempList);
		}
		return map;
	}

	public Map<String, ArrayList<LStrMap<Object>>> getNumOfOptions(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getNumOfOptions(hwId, classId);
		//将同一小题的选项组装到一个ArrayList中，然后将ArrayList存到Map中，以小题编号未键名
		Map<String, ArrayList<LStrMap<Object>>> resultMap = new HashMap<String, ArrayList<LStrMap<Object>>>();
		for (int i=0; i<list.size(); i++) {
			LStrMap<Object> tempMap = list.get(i);
			String key = tempMap.get("ques_id").toString();
			if(resultMap.containsKey(key)) {
				resultMap.get(key).add(tempMap);
			} else {
				ArrayList<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(tempMap);
				resultMap.put(tempMap.get("ques_id").toString(), tempList);
			}
		}
		return resultMap;
	}

	//得到有多少学生用了多长时间做作业
	public List<Map<String, Object>> getStuNumInArea(int hwId, int classId) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<LStrMap<Object>> list = thd.getStuNumInArea(hwId, classId);
		if (list.size() > 0) {
			//得到最小时间
			float minTime = Float.parseFloat(list.get(0).get("answer_time").toString());
			//得到最大时间
			float maxTime = Float.parseFloat(list.get(list.size()-1).get("answer_time").toString());
			//得到平均间隔时长
			float oldRange = (maxTime - minTime)/10;
			int newRange = (int)oldRange;//range的整数部分
			if (oldRange > newRange) {//如果range有小数位，要将其变为比整数位大1的整数
				newRange = newRange+1;
			}
			
			//用于存放要删除的元素
			List<LStrMap<Object>> removeList = new ArrayList<LStrMap<Object>>();
			//创建对应键值对
			for (int i=0; i<11; i++) {
				Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
				int value = 0;//记录某端时长范围内完成的学生数
				//list删除removeList中的元素
				for (int j=0; j<removeList.size(); j++) {
					list.remove(removeList.get(j));
				}
				//清空removeList
				removeList.clear();
				//遍历所有学生，如果某学生的做题时长大于minTime+range*(j-1)且<=minTime+range*j，就把value值加1
				for (int j=0; j<list.size(); j++) {
					//当前学生做题总时长
					int currentTime = Integer.parseInt(list.get(j).get("answer_time").toString());
					if ((currentTime>minTime+newRange*(i-1)) && (currentTime<=minTime+newRange*i)) {
						value++;
						removeList.add(list.get(j));//将当前学生存为需要删除的信息
					}
				}
				
				tempMap.put("duration", secondsToFormat((int)minTime+newRange*i));
				tempMap.put("num", value);
				result.add(tempMap);
			}
			
			return result;
		}
		
		return null;
	}
	
	//秒数转HH:mm格式
	private String secondsToFormat(int seconds) {
		String timeStr = "";
		int minute=0, second=0;
		
		if (seconds < 0) {
			return "0分0秒";
		} else {
			minute = seconds /60;
			second = seconds % 60;
			timeStr = minute+"分"+second+"秒";
		}
		return timeStr;
	}

	public Map<String, ArrayList<Object>> getStudentsInDuration(int hwId, int classId) {
		Map<String, ArrayList<Object>> map = new LinkedHashMap<String, ArrayList<Object>>();
		List<LStrMap<Object>> list = thd.getStuNumInArea(hwId, classId);
		if (list.size() > 0) {
			float minTime = Float.parseFloat(list.get(0).get("answer_time").toString());
			float maxTime = Float.parseFloat(list.get(list.size()-1).get("answer_time").toString());
			float oldRange = (maxTime - minTime)/10;
			int newRange = (int)oldRange;//range的整数部分
			if (oldRange > newRange) {//如果range有小数位，要将其变为比整数位大1的整数
				newRange = newRange+1;
			}
			
			//存放时间点Key的list
			List<Integer> keyList = new ArrayList<Integer>();
			//创建对应键值对
			for (int i=0; i<11; i++) {
				map.put((int)minTime+newRange*i+"", new ArrayList<Object>());
				keyList.add((int)minTime+newRange*i);
			}
			
			//遍历所有学生，如果学生的做题时长小于某个时间点，则将该学生信息存到时间点对应的List中
			int secondIndex = 0;//内部循环的起始索引
			for (int i=0; i<list.size(); i++) {
				//得到学生的做题时长
				int currentTime = Integer.parseInt(list.get(i).get("answer_time").toString());
				//从第一个时间点遍历，keyList中存放的是时间点
				for (int j=secondIndex; j<keyList.size(); j++) {
					secondIndex = j+1;//下次循环从下一个索引位置开始
					if (currentTime <= keyList.get(j)) {//如果学生做题时长小题当前时间点
						map.get(keyList.get(j)+"").add(list.get(i));//将学生信息放到时间点对应的List中
						break;
					}
				}
			}
			
			Map<String, ArrayList<Object>> result = new LinkedHashMap<String, ArrayList<Object>>();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				result.put(secondsToFormat(Integer.parseInt(key)), map.get(key));
			}
			return result;
		}
		
		return null;
	}

	public Map<String, Object> getHomeworkInfo(int hwId) {
		List<LStrMap<Object>> qiList = thd.getHomeworkInfo(hwId);
		return getHomeworkDetailInfo(hwId, qiList);
	}

	/**
	 * 得到某班某个选择题选择了相应选项的学生
	 * @param hwId
	 * @param classId
	 * @return
	 */
	public Map<String, Map<String, List<LStrMap<Object>>>> getStudentsOfOptions(int hwId, int classId) {
		List<LStrMap<Object>> list = thd.getStudentsOfOptions(hwId, classId);
		
		Map<String, Map<String, List<LStrMap<Object>>>> result = new LinkedHashMap<String, Map<String,List<LStrMap<Object>>>>();
		Map<String, List<LStrMap<Object>>> tempInnerMap = null;
		List<LStrMap<Object>> tempList = null;
		
		for (int i=0,size=list.size(); i<size; i++) {
			LStrMap<Object> tempMap = list.get(i);
			String quesId = tempMap.get("ques_id").toString();
			String optNo = tempMap.get("opt_no").toString();
			if (result.containsKey(quesId)) {
				tempInnerMap = result.get(quesId);
				if (tempInnerMap.containsKey(optNo)) {
					tempInnerMap.get(optNo).add(list.get(i));
				} else {
					tempList = new ArrayList<LStrMap<Object>>();
					tempList.add(list.get(i));
					tempInnerMap.put(optNo, tempList);
				}
			} else {
				tempInnerMap = new LinkedHashMap<String, List<LStrMap<Object>>>();
				tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(list.get(i));
				tempInnerMap.put(optNo, tempList);
				result.put(quesId, tempInnerMap);
			}
		}
		return result;
	}

	public int getClassNum(int classId) {
		List<LStrMap<Object>> list = thd.getClassNum(classId);
		if (list.size() > 0) {
			return Integer.parseInt(list.get(0).get("class_num").toString());
		}
		return 0;
	}

	public int getRightNumOfQuestion(int hwId, int classId, int quesId) {
		List<LStrMap<Object>> list = thd.getRightNumOfQuestion(hwId, classId, quesId);
		if (list.size() > 0 ) {
			return Integer.parseInt(list.get(0).get("right_num").toString());
		}
		return 0;
	}

	public List<LStrMap<Object>> getStudentsOfQuestionRight(int hwId, int classId, int quesId) {
		return thd.getStudentsOfQuestionRight(hwId, classId, quesId);
	}

	public List<LStrMap<Object>> getStudentsOfQuestionError(int hwId, int classId, int quesId) {
		return thd.getStudentsOfQuestionError(hwId, classId, quesId);
	}

	public List<LStrMap<Object>> getStudentsNumOfOptions(int hwId, int classId, int quesId) {
		return thd.getStudentsNumOfOptions(hwId, classId, quesId);
	}

	public Map<String, List<LStrMap<Object>>> getStusNameOfOptions(int hwId, int classId, int quesId) {
		List<LStrMap<Object>> list = thd.getStusNameOfOptions(hwId, classId, quesId);
		if (list.size() > 0) {//list不未空才有下面的操作
			//result代表最终结果
			Map<String, List<LStrMap<Object>>> result = new LinkedHashMap<String, List<LStrMap<Object>>>();
			
			List<LStrMap<Object>> innerList = null;
			for (int i=0,size=list.size(); i<size; i++) {
				LStrMap<Object> tempMap = list.get(i);				//得到一条记录
				String optNo = tempMap.get("opt_no").toString();	//得到记录对应的选项号
				if (result.containsKey(optNo)) {					//如果result中有当前选项号对应的内容
					innerList = result.get(optNo);					//得到当前选项号对应的List
					innerList.add(tempMap);
					result.put(optNo, innerList);
				} else {											//result中没有当前选项号对应的List
					innerList = new ArrayList<LStrMap<Object>>();	//新建一个List，用于存放当前选项号对应的内容
					innerList.add(tempMap);
					result.put(optNo, innerList);
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 更新作业的小题的发布状态
	 * @param hwId 作业编号
	 * @param status 发布状态，0为未发布，1为已发布
	 * @return
	 */
	public boolean updateQuestionsReleaseStatusOfHomework(int hwId, int status) {
		return thd.updateQuestionsReleaseStatusOfHomework(hwId, status)==1;
	}

	/**
	 * 更新作业记录明细的评语录音
	 * @param hwRecId 作业记录编号
	 * @param quesId 小题编号
	 * @param path 录音路径
	 */
	public void updateHomeworkRecordDetailCommentRec(int hwRecId, int quesId, String path) {
		thd.updateHomeworkRecordDetailCommentRec(hwRecId, quesId, path);
	}

	/**
	 * 更新作业记录的评语录音
	 * @param hwRecId 作业记录编号
	 * @param path 录音路径
	 */
	public void updateHomeworkRecordCommentRec(int hwRecId, String path) {
		thd.updateHomeworkRecordCommentRec(hwRecId, path);
	}

	/**
	 * 更新小题的录音
	 * @param quesId 小题编号
	 * @param path 录音路径
	 */
	public void updateQuesRec(int quesId, String path) {
		thd.updateQuesRec(quesId, path);
	}

	/**
	 * 获取作业的所有小题的小题Id
	 * @param hwId 作业编号
	 * @return
	 */
	public List<Integer> getQuesIdsOfHw(int hwId) {
		List<LStrMap<Object>> list = thd.getQuesIdsOfHw(hwId);
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
	 * 得到作业的最大小题号
	 * @param hwId
	 */
	public int getLastMTopicNumOfHw(int hwId) {
		List<LStrMap<Object>> list = thd.getLastMTopicNumOfHw(hwId);
		if (list.size()>0  && list.get(0).get("m_topic")!=null) {
			return Integer.parseInt(list.get(0).get("m_topic").toString());
		}
		return 0;
	}
	
}