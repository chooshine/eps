package com.eps.service.network;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.examsystem.ExamAfterDao;
import com.eps.dao.homework.TeacherHomeworkDao;
import com.eps.dao.network.ExamIntelligentlyChooseDao;
import com.eps.domain.EExamQuestion;
import com.eps.domain.EHomeworkQuestion;
import com.eps.utils.LStrMap;
import com.google.gson.GsonBuilder;

@Service
public class ExamIntelligentlyChooseService {

	@Autowired private ExamIntelligentlyChooseDao eicd;
	@Autowired private TeacherHomeworkDao thd;
	@Autowired private ExamAfterDao ead;
	
	//得到封装好结构之后的知识点
	public List<LStrMap<Object>> getStructedKnowledgePoints(int subjectId, int tsId, int range, String keyword, long userId) {
		List<LStrMap<Object>> list = null;
		//0代表全部题库，1代表收藏题库，2代表我的题库
		if (range == 1) {
			list = eicd.getKnowledgePointsInFavoriteRange(subjectId, tsId, userId, keyword);
		} else if (range == 2) {
			list = eicd.getKnowledgePointsInMyRange(subjectId, tsId, userId, keyword);
		} else {
			list = eicd.getKnowledgePointsInAll(subjectId, tsId, userId, keyword);
		}
		return formatKnowledgepoints(list);
	}

	/**
	 * @param kpList 知识点列表
	 * @return
	 */
	public List<LStrMap<Object>> formatKnowledgepoints(List<LStrMap<Object>> kpList) {
		List<LStrMap<Object>> resultList = new ArrayList<LStrMap<Object>>();//存放结果元素
		List<LStrMap<Object>> removeList = new ArrayList<LStrMap<Object>>();//记录需要删除的元素
		
		//将第一级知识点封装
		for (int i=0; i<kpList.size(); i++) {
			if (Integer.parseInt(kpList.get(i).get("p_kp_id").toString()) == 0) {
				kpList.get(i).put("subKps", new ArrayList<Object>());
				resultList.add(kpList.get(i));
				removeList.add(kpList.get(i));
			}
		}
		//删除list中已被封装过的一级知识点
		for (int i=0; i<removeList.size(); i++) {
			kpList.remove(removeList.get(i));
		}
		//清空indexList
		removeList.clear();
		
		//封装二级知识点
		for (int i=0; i<resultList.size(); i++) {
			//得到当前一级知识点
			LStrMap<Object> tempMap = resultList.get(i);
			ArrayList<LStrMap<Object>> tempList = (ArrayList<LStrMap<Object>>)tempMap.get("subKps");
			//遍历剩余知识点，如果遍历到的知识点的p_kp_id==当前一级知识点的kp_id，则把其放到一级知识点的subKps中
			//并将符合条件的知识点的索引放到indexList中
			for (int j=0; j< kpList.size(); j++) {
				if (Integer.parseInt(kpList.get(j).get("p_kp_id").toString()) == Integer.parseInt(tempMap.get("kp_id").toString())) {
					kpList.get(j).put("subKps", new ArrayList<LStrMap<Object>>());
					tempList.add(kpList.get(j));
					removeList.add(kpList.get(j));
				}
			}
		}
		//删除list中已封装过的二级知识点
		for (int i=0; i<removeList.size(); i++) {
			kpList.remove(removeList.get(i));
		}
		
		//封装三级知识点
		for (int i=0; i<resultList.size(); i++) {
			ArrayList<LStrMap<Object>> tempList = (ArrayList<LStrMap<Object>>) resultList.get(i).get("subKps");
			for (int j=0; j<tempList.size(); j++) {
				LStrMap<Object> tempMap = tempList.get(j);
				ArrayList<LStrMap<Object>> tempList2 = (ArrayList<LStrMap<Object>>)tempMap.get("subKps");
				for (int k=0; k<kpList.size(); k++) {
					if (Integer.parseInt(kpList.get(k).get("p_kp_id").toString()) == Integer.parseInt(tempMap.get("kp_id").toString())) {
						tempList2.add(kpList.get(k));
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 得到科目名称的信息，包括是高中、初中还是小学，以及是语文还是数学等科目
	 * @param subjectId
	 * @return
	 */
	public LStrMap<Object> getSortNamesInfo(int subjectId) {
		List<LStrMap<Object>> list = eicd.getSortNamesInfo(subjectId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获得试卷的某个大题的信息，包括大题号、大题名称、大题下小题的个数、大题的当前分数
	 * @param examId
	 * @param typeId
	 * @return
	 */
	public LStrMap<Object> getTypeInfo(int examId, int typeId) {
		List<LStrMap<Object>> list = eicd.getTypeInfo(examId, typeId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 得到可选小题
	 * @param examOrHwId 试卷或作业编号
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param kpId 知识点编号
	 * @param tsId 题型编号
	 * @param range 题库类型，0代表所有题库，1代表收藏题库，2代表我的题库
	 * @param keyword 关键字
	 * @param pageNo 页码
	 * @param operateType 操作类型，1代表是试卷，2代表是作业
	 * @return
	 */
	public List<Object> getOptionalQuestions(int examOrHwId, long userId, int subjectId, int kpId, int tsId, int range, String keyword, int pageNo, int operateType) {
		//得到对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<LStrMap<Object>> kpMap = getKnowledgepointsOfSubject(subjectId);
		int nowIndex = 0;//用于记录索引位置
		Map<String, Object> map = new LinkedHashMap<String, Object>();//map用于存放最终结果
		
		//得到一页的材料
		Page page = null;
		List<LStrMap<Object>> questions = null;
		List<LStrMap<Object>> options = null;
		switch (range) {//0代表全部题库，1代表收藏题库，2代表我的题库
		case 0:
			page = eicd.getPageInAll(subjectId, tsId, kpId, keyword, pageNo, userId);
			if (operateType == 1) {
				questions = eicd.getQuesInAllForExam(examOrHwId, userId, subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			} else {
				questions = eicd.getQuesInAllForHw(examOrHwId, userId, subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			}
			options = eicd.getOptsInAllForExam(subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size(), userId);
			break;
		case 1:
			page = eicd.getPageInFav(subjectId, tsId, kpId, keyword, pageNo, userId);
			if (operateType == 1) { //如果是试卷
				questions = eicd.getQuesInFavForExam(examOrHwId, userId, subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			} else {
				questions = eicd.getQuesInFavForHw(examOrHwId, userId, subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			}
			options = eicd.getOptsInFav(subjectId, tsId, kpId, userId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			break;
		case 2:
			page = eicd.getPageInMy(userId, subjectId, tsId, kpId, keyword, pageNo);
			if (operateType ==1) {
				questions = eicd.getQuesInMyForExam(examOrHwId, userId, subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			} else {
				questions = eicd.getQuesInMyForHw(examOrHwId, userId, subjectId, tsId, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			}
			options = eicd.getOptsInMy(subjectId, tsId, kpId, userId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
			break;
		default:
			break;
		}
		
		//组合题目和选项
		nowIndex = fixQuesAndOpts(kpMap, nowIndex, questions, options);
		//将材料小题和材料组合到一起，并放到map中
		fixArticleAndQues(map, questions);
		//将普通小题放到map中
		for (int i=0,size=questions.size(); i<size; i++) {
			LStrMap<Object> tempQues = questions.get(i);
			int quesType = Integer.parseInt(tempQues.get("ques_type").toString());
			Object remark = tempQues.get("remark");
			if (quesType!=6 && remark==null) {
				map.put("normal"+tempQues.get("ques_id").toString(), tempQues);
			}
			
		}
		
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(map);
		resultList.add(page);
		return resultList;
	}

	/**
	 * 将材料和属于它的小题组合起来
	 * @param map 一个存放结果的map
	 * @param articleQuestions 材料小题
	 */
	public void fixArticleAndQues(Map<String, Object> map, List<LStrMap<Object>> articleQuestions) {
		for (int i=0,size=articleQuestions.size(); i<size; i++) {	//遍历所有小题信息
			LStrMap<Object> oneQuestion = articleQuestions.get(i);
			int tempQuesType = Integer.parseInt(oneQuestion.get("QUES_TYPE").toString());	//获得小题的类型
			Object remark = oneQuestion.get("remark");
			int quesId = Integer.parseInt(oneQuestion.get("ques_id").toString());
			
			//当小题是材料时，将其及其下的小题组合，不是材料则不做处理
			if (tempQuesType == 6) {
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
	}

	/**
	 * 将小题和选项组合
	 * @param kpMap 知识点map
	 * @param nowIndex 当前索引，
	 * @param articleQuestions
	 * @param optionsOfArticleQuestions
	 * @return
	 */
	public int fixQuesAndOpts(LStrMap<LStrMap<Object>> kpMap, int nowIndex, List<LStrMap<Object>> articleQuestions,	List<LStrMap<Object>> optionsOfArticleQuestions) {
		for (int i=0,size=articleQuestions.size(); i<size; i++) {
			LStrMap<Object> quesMap = articleQuestions.get(i);
			String kpStr = quesMap.get("knowledge_point").toString();//将id,id2,id3...形式的知识点转成存有知识点名称的List
			quesMap.put("KNOWLEDGE_POINT", formatQuesKnowledgepoint(kpMap, kpStr));//知识点
			
			//从nowIndex开始遍历材料小题选项
			for (int j=nowIndex,innerSize=optionsOfArticleQuestions.size(); j<innerSize; j++) {
				LStrMap<Object> optMap = optionsOfArticleQuestions.get(j);
				//判断当前选项是否是该小题的，如果不是，就跳出循环
				if (quesMap.get("ques_id").toString().equals(optMap.get("ques_id").toString())) {
					fixOptionsOfQues(quesMap, optMap);
					nowIndex++;
				}
			}
		}
		return nowIndex;
	}

	/**
	 * 得到对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
	 * @param subjectId
	 * @return
	 */
	public LStrMap<LStrMap<Object>> getKnowledgepointsOfSubject(int subjectId) {
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = thd.getKnowledgePointsBySubject(subjectId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		return kpMap;
	}

	/**
	 * 组合小题的选项
	 * @param quesMap 小题的信息
	 * @param optMap 选项的信息
	 */
	private void fixOptionsOfQues(LStrMap<Object> quesMap, LStrMap<Object> optMap) {
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
	}

	/**
	 * 格式化小题的知识点成“基础知识-字音辨析”这样的格式
	 * @param kpMap 存储某个科目的所有知识点的Map
	 * @param kpStr 小题的knowledgepoint字符串，形式如“9,2,”
	 * @return
	 */
	private List<LStrMap<Object>> formatQuesKnowledgepoint(LStrMap<LStrMap<Object>> kpMap, String kpStr) {
		List<LStrMap<Object>> kpList = new ArrayList<LStrMap<Object>>();
		String[] kpArr = kpStr.split(",");
		for(String tempKpIdStr : kpArr) {
			LStrMap<Object> tempKpMap = LStrMap.newInstance();
			tempKpMap.put("kpId", tempKpIdStr);
			String tempKpName = kpMap.get(tempKpIdStr)==null?"":kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
			tempKpMap.put("kpName", tempKpName);
			while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
				tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
				tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+" - "+tempKpName;//得到父级知识点和本知识点的组合字符串
			}
			tempKpMap.put("kpPath", tempKpName);
			kpList.add(tempKpMap);
		}
		return kpList;
	}

	/**
	 * 更新试卷或作业中某个小题之后的所有小题的小题号，如果是试卷，则更新的小题不包括材料
	 * @param examOrHwId 试卷或者作业编号
	 * @param mTopic 小题号
	 * @param count 之后的小题号需要更改的大小
	 * @param type 试卷或者作业
	 * @return
	 */
	public boolean updateMtopicsAfterOneQuestion(int examOrHwId, int mTopic, int count, String type) {
		if (type.equals("exam")) {
			return eicd.updateMtopicsAfterOneQuestionForExam(examOrHwId, mTopic, count);
		} else {
			return eicd.updateMtopicsAfterOneQuestionForHw(examOrHwId, mTopic, count);
		}
	}

	/**
	 * 将小题和试卷建立联系
	 * @param examId
	 * @param typeId
	 * @param quesIds
	 * @param mTopics
	 * @param oScores
	 * @param scores
	 */
	public void createEExamQuestions(int examId, int typeId, String quesIds, String mTopics, String oScores, String scores) {
		Map<String, String> quesIdsMap = new GsonBuilder().create().fromJson(quesIds, LinkedHashMap.class);
		Map<String, String> mTopicsMap = new GsonBuilder().create().fromJson(mTopics, LinkedHashMap.class);
		Map<String, String> scoresMap = new GsonBuilder().create().fromJson(scores, LinkedHashMap.class);
		Map<String, String> oscoresMap = new GsonBuilder().create().fromJson(oScores, LinkedHashMap.class);
		
		for (int i=0,size=quesIdsMap.size(); i<size; i++) {
			EExamQuestion eExamQuestion = new EExamQuestion();
			eExamQuestion.setExamId(examId);
			eExamQuestion.setTypeId(typeId);
			eExamQuestion.setQuesId(Integer.parseInt(quesIdsMap.get(i+"")));
			eExamQuestion.setmTopic(Integer.parseInt(mTopicsMap.get(i+"")));
			eExamQuestion.setScore(Integer.parseInt(scoresMap.get(i+"")));
			eExamQuestion.setoScore(oscoresMap.get(i+""));
			ead.saveExamQuesDao(eExamQuestion);
		}
	}
	/**
	 * 将小题和作业建立联系
	 * @param hwId 作业编号
	 * @param quesIds 小题编号字符串
	 * @param mTopics 小题号字符串
	 * @param oScores 小题选项分数字符串
	 * @param scores 小题分数字符串
	 */
	public void createEHomeworQuestions(int hwId, String quesIds, String mTopics, String oScores, String scores) {
		Map<String, String> quesIdsMap = new GsonBuilder().create().fromJson(quesIds, LinkedHashMap.class);
		Map<String, String> mTopicsMap = new GsonBuilder().create().fromJson(mTopics, LinkedHashMap.class);
		Map<String, String> scoresMap = new GsonBuilder().create().fromJson(scores, LinkedHashMap.class);
		Map<String, String> oscoresMap = new GsonBuilder().create().fromJson(oScores, LinkedHashMap.class);
		
		for (int i=0,size=quesIdsMap.size(); i<size; i++) {
			EHomeworkQuestion eHomeworkQuestion = new EHomeworkQuestion();
			eHomeworkQuestion.setHwId(hwId);
			eHomeworkQuestion.setQuesId(Integer.parseInt(quesIdsMap.get(i+"")));
			eHomeworkQuestion.setmTopic(Integer.parseInt(mTopicsMap.get(i+"")));
			eHomeworkQuestion.setScore(Integer.parseInt(scoresMap.get(i+"")));
			eHomeworkQuestion.setoScore(oscoresMap.get(i+""));
			thd.saveHomeworkQuestion(eHomeworkQuestion);
		}
	}

	/**
	 * 得到试卷或作业中一个小题的信息，小题号和remark字段
	 * @param examOrHwId 试卷或者作业的编号
	 * @param quesId 小题编号
	 * @return
	 */
	public LStrMap<Object> getQuestionInfo(int examOrHwId, int quesId, String type) {
		List<LStrMap<Object>> list = null;
		if (type.equals("exam")) {
			list = eicd.getQuestionInfoOfExam(examOrHwId, quesId);
		} else {
			list = eicd.getQuestionInfoOfHw(examOrHwId, quesId);
		}
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 删除试卷中的小题
	 * @param examId 试卷编号
	 * @param count 需删除的个数
	 * @param mTopic 小题号
	 * @param remark 小题的remark，用于删除材料下小题对应的材料
	 * @return
	 */
	public boolean deleteQuestions(int examId, int count ,int mTopic, String remark) {
		return eicd.deleteQuestions(examId, count, mTopic, remark);
	}
	/**
	 * 删除作业中的小题
	 * @param hwId 作业编号
	 * @param count 需删除的个数
	 * @param mTopic 小题号
	 * @return
	 */
	public boolean deleteQuestions(int hwId, int count ,int mTopic) {
		return eicd.deleteQuestions(hwId, count, mTopic);
	}
}
