package com.eps.web.network;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.dao.Page;
import com.eps.domain.EOption;
import com.eps.domain.EQuestion;
import com.eps.domain.User;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.network.ExamIntelligentlyChooseService;
import com.eps.service.network.NetWorkService;
import com.eps.service.network.PersonalWharehouseService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 这个类主要负责个人题库的功能
 * @author 见冰冰
 *
 */
@Controller
public class PersonalWharehourseController extends BaseController {
	@Autowired ExamIntelligentlyChooseService eics;
	@Autowired NetWorkService nws;
	@Autowired PersonalWharehouseService pws;
	@Autowired ExamAfterService eas;
	
	//进入个人题库
	@RequestMapping(value="/teacher/myWharehouse.html")
	public String toMyWharehouse(HttpServletRequest request, ModelMap mm) {
		long userId = getSessionUser(request).getUserId();
		int subjectId = 0;
		Object sortName = null;
		Object pSortNo = null;
		List<LStrMap<Object>> teachs = nws.getTeachsOfTeacher(userId);//获得教师所教的所有科目
		if (teachs.size() > 0) {
			if (request.getParameter("sortId") != null) {//如果科目参数不为空，得到科目对应的信息
				subjectId = Integer.parseInt(request.getParameter("sortId").toString());
				for(LStrMap<Object> tempMap : teachs) {
					if (((Integer)tempMap.get("sort_id")) == subjectId) {
						sortName = tempMap.get("parent_sort")+"·"+tempMap.get("sort_name");
						pSortNo = tempMap.get("sort_no");
						break;
					}
				}
			} else {//当从“我的题库”这样的入口进入时，需要选择一个默认科目，默认选第一个科目
				LStrMap<Object> tempMap = teachs.get(0);
				subjectId = (Integer)tempMap.get("sort_id");//科目编号
				sortName = tempMap.get("parent_sort")+"·"+tempMap.get("sort_name");//得到“高中·语文”这样的格式的科目名称
				pSortNo = tempMap.get("sort_no");
			}
			mm.put("subjectId", subjectId);
			mm.put("sortList", teachs);
			mm.put("sortName", sortName);
			mm.put("pSortNo", pSortNo);
			mm.put("quesTypes", nws.getTypeSubjects(subjectId));
		}
		
		return "network/teacher/myWharehouse";
	}
	
	//切换科目
	@RequestMapping(value="/teacher/getKnowledgepoints.html", method=RequestMethod.POST)
	public String getKnowledgepoints(int subjectId, int tsId, int flag, String keyword, HttpServletRequest request, ModelMap mm) {
		//得到考点树的数据
		mm.put("knowledgePoints", pws.getStructedKnowledgePoints(getSessionUser(request).getUserId(), subjectId, tsId, flag, keyword));
		return "network/teacher/kpsTree";
	}
	
	//查找我的题库中的小题
	@RequestMapping(value="/teacher/myQuestions.html")
	public String getSuitableQuestions(int subjectId, int tsId, int flag, int kpId, String keyword, int pageNo, HttpServletRequest request, ModelMap mm) {
		try {
			keyword = URLDecoder.decode(keyword, "utf-8");
			List<Object> list = pws.getMyQuestions(getSessionUser(request).getUserId(), subjectId, tsId, flag, kpId, keyword, pageNo);
			mm.put("page", list.get(1));
			mm.put("qiMap", list.get(0));
			mm.put("start", Page.getStartOfPage(pageNo));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "network/teacher/myQuestions";
	}
	
	//新增小题时增加一个空白题目
	@RequestMapping(value="/teacher/addOneQues.html")
	public String addQuestion(int quesType, int tsId, ModelMap mm) {
		mm.put("quesType", quesType);
		mm.put("tsId", tsId);
		return "network/teacher/normalQues";
	}
	
	@RequestMapping(value="/teacher/operateQuestion.json", method=RequestMethod.POST)
	public String operateQuestion(EQuestion eQuestion, EOption eOption, HttpServletRequest request, ModelMap mm) {
		User user = getSessionUser(request);
		eQuestion.setCreator(user.getUserId()+"");
		String operateType = request.getParameter("operateType");
		String artId = request.getParameter("artId");
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		eQuestion.setInputTime(dateformat.format(date));
		
		if("add".equals(operateType)){
			eQuestion.setRemark(artId);
			pws.createQuestion(eQuestion, eOption);
			int oldQuesId = eQuestion.getQuesId();
			if (artId != null) { //如果有artId，说明是更新材料下的小题
				eas.updateArtRemark(Integer.parseInt(artId), eQuestion.getQuesId(), "add", oldQuesId);
			}
		} else if("update".equals(operateType)) {
			eQuestion.setRemark(artId);
			int oldQuesId = eQuestion.getQuesId();
			eas.deleteOneQuestion(eQuestion.getQuesId());//删除原有小题
			pws.createQuestion(eQuestion, eOption);		 //新增一个小题
			if (artId != null) { //如果有artId，说明是更新材料下的小题
				eas.updateArtRemark(Integer.parseInt(artId), eQuestion.getQuesId(), "update", oldQuesId);
			}
		} else if("delete".equals(operateType)) {
			eas.deleteOneQuestion(eQuestion.getQuesId());
			if (artId != null) { //如果有artId，说明是更新材料下的小题
				eas.updateArtRemark(Integer.parseInt(artId), eQuestion.getQuesId(), "delete", eQuestion.getQuesId());
			}
		} else if("articleAdd".equals(operateType)) {
			pws.createArticle(eQuestion);
		} else if("articleUpdate".equals(operateType)) {
			eas.updateArtlicle(eQuestion);
			//更新文章下小题的知识点
			eas.updateQuestionsKnowledgePointOfArticle(eQuestion.getQuesId(), eQuestion.getKnowledgePoint());
		}
		
		mm.put("quesId", eQuestion.getQuesId());
		return "jsonView";
	}
	
	//更新小题的状态
	@RequestMapping(value="/teacher/updateQuesFlag.json", method=RequestMethod.POST)
	public String updateQuesFlag(int quesId, int releaseFlag, int shareFlag, HttpServletRequest request, ModelMap mm) {
		pws.updateQuesFlag(quesId, releaseFlag, shareFlag);
		return "jsonView";
	}
	//更新材料题的状态
	@RequestMapping(value="/teacher/updateArticleFlag.json", method=RequestMethod.POST)
	public String updateArticleFlag(int artId, int releaseFlag, int shareFlag, HttpServletRequest request, ModelMap mm) {
		pws.updateArticleFlag(artId, releaseFlag, shareFlag);
		if (request.getParameter("resource") != null) {
			pws.updateArticleQuestions(Integer.parseInt(request.getParameter("artId")), request.getParameter("resource"), request.getParameter("gradeLevel"));
		}
		return "jsonView";
	}
	
	//判断当前小题是否与试卷或作业建立了联系
	@RequestMapping(value="/teacher/relationExist.json", method=RequestMethod.POST)
	public String relationExist(int quesId, HttpServletRequest request, ModelMap mm) {
		mm.put("flag", pws.isRelationExist(quesId));
		mm.put("relation", pws.getRelation(quesId));
		return "jsonView";
	}
	
	//删除一个材料题的材料及所有小题
	@RequestMapping(value="/teacher/deleteArticle.json", method=RequestMethod.POST)
	public String deleteArticle(int artId, HttpServletRequest request, ModelMap mm) {
		pws.deleteArticle(artId);
		return "jsonView";
	}
	
	//复制材料题
	@RequestMapping(value="/teacher/copyArticle.json", method=RequestMethod.POST)
	public String copyQuestions(String subjectNo, String resource, int gradeLevel,
			String quesTypes, String quesContents, String quesRefers,String oScores, String scores,
			String knowledgePoints, String keywords, String optionNums, String optNos, String optContents, String optRefers, String quesRec, String difficulty, String tsId, HttpServletRequest request, ModelMap mm) {
		Gson gson = new GsonBuilder().create();
		Map quesTypeMap = gson.fromJson(quesTypes, Map.class);
		Map quesContentMap = gson.fromJson(quesContents, Map.class);
		Map quesReferMap = gson.fromJson(quesRefers, Map.class);
		Map oScoreMap = gson.fromJson(oScores, Map.class);
		Map scoreMap = gson.fromJson(scores, Map.class);
		Map knowledgePointMap = gson.fromJson(knowledgePoints, Map.class);
		Map keywordMap = gson.fromJson(keywords, Map.class);
		Map optionNumMap = gson.fromJson(optionNums, Map.class);
		Map optNoMap = gson.fromJson(optNos, Map.class);
		Map optContentMap = gson.fromJson(optContents, Map.class);
		Map optReferMap = gson.fromJson(optRefers, Map.class);
		Map quesRecMap = gson.fromJson(quesRec, Map.class);
		Map difficultyMap = gson.fromJson(difficulty, Map.class);
		Map tsIdMap = gson.fromJson(tsId, Map.class);
		
		int artId = 0;//记录材料编号
		String remark = "";//记录材料的remark
		List<Integer> idList = new ArrayList<Integer>();//记录材料和小题的编号
		for (int i=0,size=quesContentMap.size(); i<size; i++) {
			EQuestion eQuestion = new EQuestion();
			EOption eOption = new EOption();
			
			//创建小题
			eQuestion.setQuesContent(quesContentMap.get(i+"")==null?"":quesContentMap.get(i+"").toString());
			eQuestion.setQuesType(quesTypeMap.get(i+"")==null?"":quesTypeMap.get(i+"").toString());
			eQuestion.setQuesRefer(quesReferMap.get(i+"")==null?null:quesReferMap.get(i+"").toString());
			eQuestion.setOptionNum(optionNumMap.get(i+"")==null?0:(int)Double.parseDouble(optionNumMap.get(i+"").toString()));
			eQuestion.setKnowledgePoint(knowledgePointMap.get(i+"")==null?"":knowledgePointMap.get(i+"").toString());
			eQuestion.setKeyword(keywordMap.get(i+"")==null?null:keywordMap.get(i+"").toString());
			eQuestion.setCreator(getSessionUser(request).getUserId()+"");
			eQuestion.setGradeLevel(gradeLevel);
			eQuestion.setSubjectNo(subjectNo);
			eQuestion.setResource(resource);
			eQuestion.setQuesRec(quesRecMap.get(i+"")==null?"1":quesRecMap.get(i+"").toString());
			eQuestion.setDifficulty(difficultyMap.get(i+"")==null?"1":difficultyMap.get(i+"").toString());
			eQuestion.setTsId(tsIdMap.get(i+"")==null?0:Integer.parseInt(tsIdMap.get(i+"").toString()));
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
			eQuestion.setInputTime(dateformat.format(date));
			if (i != 0) {//如果不是材料，就设置remark
				eQuestion.setRemark(artId+"");
			}
			
			//创建选项
			eOption.setOptNo(optNoMap.get(i+"")==null?"":optNoMap.get(i+"").toString());
			eOption.setOptContent(optContentMap.get(i+"")==null?null:optContentMap.get(i+"").toString());
			eOption.setOptRefer(optReferMap.get(i+"")==null?"":optReferMap.get(i+"").toString());
			
			//保存小题、选项和关系
			if (i == 0) {//如果是材料，就记录材料的编号
				pws.createArticle(eQuestion);
				artId = eQuestion.getQuesId();
			} else {//如果不是材料，就记录材料的remark
				eQuestion.setRemark(artId+"");
				pws.createQuestion(eQuestion, eOption);
				remark = remark+eQuestion.getQuesId()+",";
			}
			
			idList.add(eQuestion.getQuesId());
		}
		
		//更新材料的remark
		eas.updateArtRemark(artId, remark);
		
		mm.put("Ids", idList);
		//返回材料和小题编号
		return "jsonView";
	}
}