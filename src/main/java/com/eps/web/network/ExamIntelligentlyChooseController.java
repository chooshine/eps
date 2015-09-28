package com.eps.web.network;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.dao.Page;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.homework.TeacherHomeworkService;
import com.eps.service.network.ExamIntelligentlyChooseService;
import com.eps.service.network.NetWorkService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
/**
 * 试卷智能选题
 * @author 见冰冰
 */
@Controller
public class ExamIntelligentlyChooseController extends BaseController {

	@Autowired private ExamIntelligentlyChooseService eics;
	@Autowired private ExamAfterService eas;
	@Autowired private TeacherHomeworkService ths;
	@Autowired private NetWorkService nws;
	
	//进入试卷选题界面
	@RequestMapping(value="/network/teacher/chooseQuestions.html")
	public String toChoosePage(int examId,int subjectId, int typeId, int quesType, int tsId, int nowScore, int typeLastMtopic, HttpServletRequest request, ModelMap mm) {
		mm.put("sortNamesInfo", eics.getSortNamesInfo(subjectId));
		//得到试卷信息，包括大题数、小题数、总分
		mm.put("examInfo", eas.getExamInfoByExamidServices(examId));
		mm.put("nowScore", nowScore);//当前分数
		mm.put("typeInfo", eics.getTypeInfo(examId, typeId));
		mm.put("quesType", quesType);
		mm.put("tsId", tsId);
		mm.put("currentMtopic", typeLastMtopic);
		mm.put("quesTypes", nws.getTypeSubjects(subjectId));
		return "network/teacher/chooseQuestions";
	}
	
	//进入作业的选题界面
	@RequestMapping(value="/homework/chooseQuestions.html")
	public String toHomeworkChoosePage(int hwId,int subjectId, int lastMtopic, HttpServletRequest request, ModelMap mm) {
		mm.put("sortNamesInfo", eics.getSortNamesInfo(subjectId));
		//得到知识点树的数据，默认是全部题库、单选题
		mm.put("hwInfo", ths.getHwInfoByHwId(hwId));
		mm.put("currentMtopic", lastMtopic);
		mm.put("quesTypes", nws.getTypeSubjects(subjectId));
		return "network/teacher/chooseQuestions";
	}
	
	//点击题库
	@RequestMapping(value="/network/teacher/getKnowledgepoints.html")
	public String getKnowledgepoints(int subjectId, int tsId, int range, String keyword, HttpServletRequest request, ModelMap mm) {
		try {
			keyword = URLDecoder.decode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//得到知识点树的数据
		mm.put("knowledgePoints", eics.getStructedKnowledgePoints(subjectId, tsId, range, keyword, getSessionUser(request).getUserId()));
		return "network/teacher/kpsTree";
	}
	
	//试卷的智能选题，得到题库中符合条件的小题
	@RequestMapping(value="/network/teacher/optionalQuestions.html")
	public String getSuitableQuestions(int examId, int subjectId, int tsId, int kpId, int range, String keyword, int pageNo, HttpServletRequest request, ModelMap mm) {
		try {
			keyword = URLDecoder.decode(keyword, "utf-8");
			List<Object> list = eics.getOptionalQuestions(examId, getSessionUser(request).getUserId(), subjectId, kpId, tsId, range, keyword, pageNo, 1);
			mm.put("page", list.get(1));
			mm.put("qiMap", list.get(0));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "network/teacher/optionalQuestions";
	}
	
	//作业的智能选题，得到题库中符合条件的小题
	@RequestMapping(value="/homework/optionalQuestions.html")
	public String getSuitableQuestionsForHomework(int hwId, int subjectId, int tsId, int kpId, int range, String keyword, int pageNo, HttpServletRequest request, ModelMap mm) {
		try {
			keyword = URLDecoder.decode(keyword, "utf-8");
			List<Object> list = eics.getOptionalQuestions(hwId, getSessionUser(request).getUserId(), subjectId, kpId, tsId, range, keyword, pageNo, 2);
			mm.put("page", list.get(1));
			mm.put("qiMap", list.get(0));
			mm.put("start", Page.getStartOfPage(pageNo));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "network/teacher/optionalQuestions";
	}
	
	//将小题加入试卷
	@RequestMapping(value="/network/teacher/joinQuestions.json", method=RequestMethod.POST)
	public String joinExam(int examId, int typeId, String quesIds, String mTopics, String oScores, String scores, int count, int lastTypeMtopic,HttpServletRequest request, ModelMap mm) {
		//更新当前大题最后一个小题之后的小题的小题号
		eics.updateMtopicsAfterOneQuestion(examId, lastTypeMtopic, count, "exam");
		//将新增的小题与试卷建立关系
		eics.createEExamQuestions(examId, typeId, quesIds, mTopics, oScores, scores);
		//更新试卷的整体信息
		eas.updateExamNumService(0, count, examId);
		return "jsonView";
	}
	//将小题移出试卷
	@RequestMapping(value="/network/teacher/removeQuestionsFromExam.json", method=RequestMethod.POST)
	public String removeFromExam(int examId, int quesId, int count,HttpServletRequest request, ModelMap mm) {
		//获得传入的小题的小题号及小题的remark字段值
		LStrMap<Object> map = eics.getQuestionInfo(examId, quesId, "exam");
		//删除符合条件的小题
		eics.deleteQuestions(examId, count, (Integer)map.get("m_topic"), map.get("remark")==null?"":map.get("remark").toString());
		//更新传入小题之后的所有小题的小题号
		eics.updateMtopicsAfterOneQuestion(examId, (Integer)map.get("m_topic"), -count, "exam");
		//更新试卷的小题数量
		eas.updateExamNumService(0, -count, examId);
		return "jsonView";
	}
	
	//将小题加入作业
	@RequestMapping(value="/homework/joinQuestions.json", method=RequestMethod.POST)
	public String joinHomework(int hwId, String quesIds, String mTopics, String oScores, String scores, int count, int lastTypeMtopic,HttpServletRequest request, ModelMap mm) {
		//更新当前大题最后一个小题之后的小题的小题号
		eics.updateMtopicsAfterOneQuestion(hwId, lastTypeMtopic, count, "hw");
		//将新增的小题与试卷建立关系
		eics.createEHomeworQuestions(hwId, quesIds, mTopics, oScores, scores);
		//更新试卷的整体信息
		ths.updateHomeworkTopicNumByHwId(hwId, count);
		return "jsonView";
	}
	//将小题移出作业
	@RequestMapping(value="/homework/removeQuestionsFromHw.json", method=RequestMethod.POST)
	public String removeFromHomework(int hwId, int quesId, int count,HttpServletRequest request, ModelMap mm) {
		//获得传入的小题的小题号及小题的remark字段值
		LStrMap<Object> map = eics.getQuestionInfo(hwId, quesId, "hw");
		//删除符合条件的小题
		eics.deleteQuestions(hwId, count, (Integer)map.get("m_topic"));
		//更新传入小题之后的所有小题的小题号
		eics.updateMtopicsAfterOneQuestion(hwId, (Integer)map.get("m_topic"), -count, "hw");
		//更新试卷的小题数量
		ths.updateHomeworkTopicNumByHwId(hwId, -count);
		return "jsonView";
	}
	
}
