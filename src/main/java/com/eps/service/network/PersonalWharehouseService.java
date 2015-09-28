package com.eps.service.network;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.Page;
import com.eps.dao.examsystem.ExamAfterDao;
import com.eps.dao.network.PersonalWharehouseDao;
import com.eps.domain.EOption;
import com.eps.domain.EQuestion;
import com.eps.utils.LStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class PersonalWharehouseService {

	@Autowired private PersonalWharehouseDao pwd;
	@Autowired private ExamIntelligentlyChooseService eics;
	@Autowired private ExamAfterDao ead;
	@Autowired private MaxValueINcrementer seq_quesId;
	@Autowired private MaxValueINcrementer seq_optId;
	
	/**
	 * 得到我的题库中的符合条件的小题
	 * @param userId 用户编号
	 * @param subjectId 科目编号
	 * @param kpId 知识点编号
	 * @param tsId 题型编号
	 * @param flag 状态，0未发布，1已发布，2已删除，3已共享
	 * @param keyword 关键字
	 * @return
	 */
	public List<Object> getMyQuestions(long userId, int subjectId, int tsId, int flag, int kpId, String keyword, int pageNo) {
		//得到releaseFlag 和 shareFlag
		int releaseFlag, shareFlag = 0;
		if (flag == 3) {
			releaseFlag = -1;
			shareFlag = 1;
		} else {
			releaseFlag = flag;
			shareFlag = -1;
		}
		
		//得到对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<LStrMap<Object>> kpMap = eics.getKnowledgepointsOfSubject(subjectId);
		int nowIndex = 0;//用于记录索引位置
		Map<String, Object> map = new LinkedHashMap<String, Object>();//map用于存放最终结果
		
		Page page = pwd.getPage(userId, subjectId, tsId, releaseFlag, shareFlag, kpId, keyword, pageNo);
		List<LStrMap<Object>> questions = pwd.getQuestions(userId, subjectId, tsId, releaseFlag, shareFlag, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
		List<LStrMap<Object>> options = pwd.getOptions(userId, subjectId, tsId, releaseFlag, shareFlag, kpId, keyword, Page.getStartOfPage(pageNo), page.getData().size());
		eics.fixQuesAndOpts(kpMap, nowIndex, questions, options);
		eics.fixArticleAndQues(map, questions);//组合材料和小题
		//将普通小题放到map中
		for (int i=0,size=questions.size(); i<size; i++) {
			LStrMap<Object> tempQues = questions.get(i);
			if (tempQues.get("remark") != null) {
				continue;
			}
			map.put("normal"+questions.get(i).get("ques_id").toString(), questions.get(i));
		}
		
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(map);
		resultList.add(page);
		return resultList;
	}

	/**
	 * 得到格式化后的考点数据
	 * @param userId 用户Id
	 * @param subjectId 科目Id
	 * @param tsId 题型Id
	 * @param flag 状态标记，3代表已作废状态，其他代表非作废状态
	 * @param keyword 题干中的关键字
	 */
	public List<LStrMap<Object>> getStructedKnowledgePoints(long userId, int subjectId, int tsId, int flag, String keyword) {
		List<LStrMap<Object>> kpList = null;
		
		//得到releaseFlag 和 shareFlag
		int releaseFlag, shareFlag = 0;
		if (flag == 3) {
			releaseFlag = -1;
			shareFlag = 1;
		} else {
			releaseFlag = flag;
			shareFlag = -1;
		}
		
		kpList = pwd.getKnowledgepoints(userId, subjectId, tsId, releaseFlag, shareFlag, keyword);
		return eics.formatKnowledgepoints(kpList);
	}

	/**
	 * 新创建一个小题
	 * @param eQuestion
	 * @param eOption
	 */
	public void createQuestion(EQuestion eQuestion, EOption eOption) {
		int quesId = seq_quesId.nextIntValue();	//生成一个小题编号
		eQuestion.setQuesId(quesId);
		
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
		ead.saveQuestionDao(eQuestion);	//保存小题
		
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
			eOption.setQuesId(quesId);
			eOption.setOptId(optId);
			ead.saveOptionDao(eOption);					//保存一个选项
		}
		
	}

	/**
	 * 更新小题的发布状态和共享状态
	 * @param quesId
	 * @param releaseFlag
	 * @param shareFlag
	 */
	public void updateQuesFlag(int quesId, int releaseFlag, int shareFlag) {
		pwd.updateQuesFlag(quesId, releaseFlag, shareFlag);
	}

	/**
	 * 判断当前小题是否存在与作业或试卷的关系，如果存在，则返回1,否则返回0
	 * @param quesId
	 * @return
	 */
	public int isRelationExist(int quesId) {
		if (pwd.getRelationExistFlag(quesId).size() > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 更新材料题的状态，同时修改材料及材料小题的状态
	 * @param artId 材料编号
	 * @param releaseFlag 发布标记
	 * @param shareFlag 共享标记
	 */
	public void updateArticleFlag(int artId, int releaseFlag, int shareFlag) {
		pwd.updateArticleFlag(artId, releaseFlag, shareFlag);
	}

	/**
	 * 删除一个材料题的材料及所有小题
	 * @param artId
	 */
	public void deleteArticle(int artId) {
		pwd.deleteArticle(artId);
	}

	/**
	 * 新增一个材料
	 * @param eQuestion
	 */
	public void createArticle(EQuestion eQuestion) {
		int quesId=seq_quesId.nextIntValue();	//获得一个小题编号
		eQuestion.setQuesId(quesId);			//设置小题编号
		ead.saveQuestionDao(eQuestion);//保存一个小题
	}

	/**
	 * 更新一个材料的所有小题的出处和年级
	 * @param artId 文章编号
	 * @param resource 出处
	 * @param gradeLevel 年级
	 */
	public void updateArticleQuestions(int artId, String resource, String gradeLevel) {
		pwd.updateArticleQuestions(artId, resource, gradeLevel);
	}

	/**
	 * 得到一个小题与试卷或作业的关系
	 * @param quesId 小题编号
	 * @return 如果存在这样的关系，就返回关系map，否则返回null
	 */
	public LStrMap<Object> getRelation(int quesId) {
		List<LStrMap<Object>> list = pwd.getRelation(quesId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
