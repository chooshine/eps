package com.eps.web.examsystem;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.ETestRecord;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.examsystem.ExamBeforeService;
import com.eps.service.examsystem.ViewExamService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
@Controller
public class ExamBeforeController extends BaseController{
	@Autowired
	private ExamBeforeService ebserivce;
	
	@Autowired
	private ExamAfterService eas;
	
	@Autowired
	private ViewExamService viewExamService;
	
	/**
	 * 试卷分类
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/exam_index.html")
	public String getExamType(ModelMap mm){
		mm.put("title", "考试系统");
		mm.put("examType",ebserivce.getExamTypeService());
		return "examsystem/exam_index";
	}
	
	/**
	 * 进入试卷选择界面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/exam_examChoose.html")
	public String intoExamChooseHtm(ModelMap mm,HttpServletRequest request){
		String examType=request.getParameter("examType");
		String subjectNo=request.getParameter("subjectNo");
		String examArea=request.getParameter("examArea");
		mm.put("title", "试卷分类");
		mm.put("sortName",request.getParameter("sortName"));
		mm.put("examType", ebserivce.getExamTypeByAnyService(subjectNo, examArea, examType));
		mm.put("examArea", ebserivce.getExamAreaByAnyService(subjectNo, examArea, examType));
		mm.put("subjectNo", ebserivce.getSubjectNoByAnyService(subjectNo, examArea, examType));
		mm.put("examInfo", ebserivce.getExamInfoByAnyService(subjectNo, examArea, examType));
		return "examsystem/exam_examChoose";
	}
	
}