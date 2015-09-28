package com.eps.web.examsystem;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.examsystem.ExamBeforeService;
import com.eps.service.examsystem.ViewExamService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Controller
public class ViewExamController extends BaseController {

	@Autowired
	private ViewExamService viewExamService;
	
	@Autowired
	private ExamAfterService eas;
	
	@Autowired
	private ExamBeforeService ebserivce;
	
	//查看某次考试记录的统计数据
	@RequestMapping(value="/examsystem/viewresult.html")
	public String toViewResult(HttpServletRequest request, ModelMap mm, String examId, int testRecId){
		long userId = this.getSessionUser(request).getUserId();
		
		//得到考试记录的统计信息
		LStrMap<Object> recordStatistics = viewExamService.getRecordStatistics(Long.parseLong(examId), testRecId, userId);
		//得到大题和小题的得分及耗时
		List<LStrMap<Object>> typesAndQuestionsInfo = viewExamService.getTypesAndQuestionsInfo(testRecId);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		mm.put("recordStatistics", recordStatistics);
		mm.put("typesAndQuestionsInfo", typesAndQuestionsInfo);
		mm.put("examId", examId);
		mm.put("testRecId", testRecId);
		mm.put("pieData", gson.toJson(typesAndQuestionsInfo));
		return "examsystem/viewresult";
	}
	
	//查看某次考试的做题详情，即展现试卷信息及考生的答题信息
	@RequestMapping(value="/examsystem/viewDetail.html")
	public String toExamAnaly(HttpServletRequest request, ModelMap mm, int examId, long testRecId, String viewType) {
		//得到要展示的类型，如果是all，代表要查看所有小题的解析，否则是展示错误小题的解析
		List list = null;	//试卷信息
		if (viewType.equals("all")) {
			list = viewExamService.getExamAllInfoToShow(testRecId, examId, -1);
		} else {
			list = viewExamService.getExamErrorQuestionsToShow(testRecId, examId);
		}
		
		//用于显示答题卡的数据
		List<LStrMap<Object>> typesAndQuestionsInfo = viewExamService.getTypesAndQuestionsInfo(testRecId);
		
		mm.put("viewType", viewType);
		mm.put("title", "查看考试结果");
		mm.put("examId",examId);
		mm.put("testRecId",testRecId);
		mm.put("qtList", (List)list.get(0));
		mm.put("bctList", (List)list.get(1));
		mm.put("sctList", (List)list.get(2));
		mm.put("qiMap", list.get(3));
		mm.put("examInfoMap",eas.getExamInfoByExamidServices(examId));
		mm.put("typesAndQuestionsInfo", typesAndQuestionsInfo);
		mm.put("mtopicId", request.getParameter("mtopicId"));
		mm.put("typeInfo", new GsonBuilder().create().toJson(viewExamService.getTypeInfo(examId)));//获得大题信息，共有几个小题多少分
		mm.put("stuScore", viewExamService.getRecordStatistics(examId, testRecId, getSessionUser(request).getUserId()).get("score"));
		return "examsystem/viewDetail";
	}
	
	//教师阅卷
	@RequestMapping(value="/examsystem/gradePapers.html")
	public String gradePapers(HttpServletRequest request, ModelMap mm, int examId, long testRecId, long classId, long testId, long subjectId) {
		//得到本次查看的类型，查看全部或只查看主观题，如果是只查看主观题，就设置quesType为0，否则，quesType为-1
		int quesType = 0;
		mm.put("view", "查看全部");
		if (request.getParameter("questype") == null) {
			quesType = -1;
			mm.put("view", "批主观题");
		}
		List list = viewExamService.getExamAllInfoToShow(testRecId, examId, quesType);
		
		//用于显示答题卡的数据
		List<LStrMap<Object>> typesAndQuestionsInfo = viewExamService.getGradePaperAnswerCardData(testRecId);
		
		mm.put("examId",examId);
		mm.put("testRecId",testRecId);
		mm.put("qtList", (List)list.get(0));
		mm.put("bctList", (List)list.get(1));
		mm.put("sctList", (List)list.get(2));
		mm.put("qiMap", list.get(3));
		mm.put("examInfoMap",eas.getExamInfoByExamidServices(examId));
		mm.put("typesAndQuestionsInfo", typesAndQuestionsInfo);
		mm.put("questionNumber", request.getParameter("questionNumber"));
		mm.put("markInfo", viewExamService.getMarkNum(testRecId));
		mm.put("classInfo", viewExamService.getClassUnmarkedInfo(getSessionUser(request).getUserId(), classId, testId, subjectId));
		mm.put("typeInfo", new GsonBuilder().create().toJson(viewExamService.getTypeInfo(examId)));//获得大题信息，共有几个小题多少分
		mm.put("mtopicId", request.getParameter("mtopicId"));
		return "examsystem/gradePapers";
	}
	
	//老师评分之后选择保存或者提交评分结果的处理
	@RequestMapping(value="/examsystem/gradePapers.json")
	public String saveGradeResult(HttpServletRequest request, ModelMap mm) {
		long testRecId = Long.parseLong(request.getParameter("testRecId"));
		long userId = this.getSessionUser(request).getUserId();
		
		Gson gson = new GsonBuilder().create();
		Map quesScoreMap = gson.fromJson(request.getParameter("quesScoreJson"), Map.class);
		Map oScorenMap = gson.fromJson(request.getParameter("oScoreJson"), Map.class);
		Map commentMap = gson.fromJson(request.getParameter("commentJson"), Map.class);
		
		//保存和提交的不同处理
		if(request.getParameter("operateType").equals("save")){
			viewExamService.updateTestRecordDetailInfo(testRecId, quesScoreMap, oScorenMap, commentMap);
			//更新考生的考试记录的评分字段，设为已阅部分
			viewExamService.updateRecordMarkFlag(testRecId, 1, userId);
		} else {
			viewExamService.updateTestRecordDetailInfo(testRecId, quesScoreMap, oScorenMap, commentMap);
			//更新考生的考试记录的评分字段，设为已阅卷
			viewExamService.updateRecordMarkFlagAndScore(testRecId, 2, userId);
		}
		
		return "jsonView";
	}
	
	//进入到阅卷完成页面
	@RequestMapping(value="/examsystem/showMarkedResult.html", method=RequestMethod.POST)
	public String showFishedMark(HttpServletRequest request, ModelMap mm, long examId, long testRecId, long classId, long testId, long subjectId) {
		mm.put("title", "阅卷成功");
		mm.put("examId", examId);
		mm.put("testRecId", testRecId);
		//得到学生信息及得分
		mm.put("student", viewExamService.getStudentInfo(testRecId));
		//得到班级未评阅信息
		mm.put("classInfo", viewExamService.getClassUnmarkedInfo(getSessionUser(request).getUserId(), classId, testId, subjectId));
		
		return "examsystem/showMarkedResult";
	}
	
	//设置做卷记录的某个小题的评语录音
	@RequestMapping(value="/setTestRecordDetailCommentRec.json")
	public String setTestRecordDetailCommentRec(int testRecId, int quesId, String path) {
		viewExamService.updateTestRecordDetailCommentRec(testRecId, quesId, path);
		return "jsonView";
	}
}