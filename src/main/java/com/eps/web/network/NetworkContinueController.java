package com.eps.web.network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.dao.Page;
import com.eps.domain.EExam;
import com.eps.domain.EOption;
import com.eps.domain.EQuesType;
import com.eps.domain.EQuestion;
import com.eps.domain.ETestEexam;
import com.eps.domain.User;
import com.eps.service.network.NetWorkContinueService;
import com.eps.service.network.NetWorkService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;

@Controller
public class NetworkContinueController extends BaseController{

	@Autowired
	NetWorkContinueService nwsService;
	private Page page;
	@Autowired
	private NetWorkService netService;	
	
	
	/**
	 * 进入继续出卷
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/network/continue.html")
	public String viewAllNetwork(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		String pageNo=request.getParameter("pageNo");
		if(pageNo!=null) {
			page=nwsService.getUserFromExamByReleasestatus(Integer.parseInt(pageNo),user.getUserId());
		} else {
			page=nwsService.getUserFromExamByReleasestatus(1,user.getUserId());
		}
		mm.put("examList", page);
		return "network/net_continue";
	}
	
	/**
	 * 进入继续出卷布页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/networkContinue.html")
	public String enterOrderRelease(HttpServletRequest request,ModelMap mm,int release,int pageSize,int pageNo){
		return "network/net_continue";
	}
	
	/**
	 * 删除考卷
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/delExam.html")
	public String delExam(HttpServletRequest request,ModelMap mm,int examId,EQuestion eq,EOption eo,EQuesType eqt,EExam eex,String pageType){
		String pageNo=request.getParameter("pageNo");
		eex.setExamId(examId);
		User user=getSessionUser(request);
		List<LStrMap<Object>> examList=nwsService.getexams(examId);
		eex.setExamId(Integer.parseInt(examList.get(0).get("exam_id").toString()));
		eex.setExamName(examList.get(0).get("exam_name").toString());
		eex.setYear(Integer.parseInt(examList.get(0).get("year").toString()));
		eex.setSemester(Integer.parseInt(examList.get(0).get("semester").toString()));
		eex.setSubjectNo(examList.get(0).get("subject_no").toString());
		eex.setExamType(Integer.parseInt(examList.get(0).get("exam_type").toString()));
		eex.setbTopicNum(Integer.parseInt(examList.get(0).get("b_topic_num").toString()));
		eex.setmTopicNum(Integer.parseInt(examList.get(0).get("m_topic_num").toString()));
		eex.setTotal(Integer.parseInt(examList.get(0).get("total").toString()));
		eex.setCareaor(examList.get(0).get("careaor").toString());
		eex.setCostTime(Integer.parseInt(examList.get(0).get("cost_time").toString()));
		eex.setRemark(examList.get(0).get("remark").toString());
		eex.setqRandom(Integer.parseInt(examList.get(0).get("q_random").toString()));
		//eex.setoRandom(Integer.parseInt(examList.get(0).get("o_random").toString()));
		eex.setbCodeType(Integer.parseInt( examList.get(0).get("b_code_type").toString()));
		eex.setsCodeType(Integer.parseInt( examList.get(0).get("s_code_type").toString()));
		eex.setoCodeType(Integer.parseInt( examList.get(0).get("o_code_type").toString()));
		eex.setExamArea(examList.get(0).get("exam_area").toString());
		eex.setPassScore(Float.parseFloat(examList.get(0).get("pass_score").toString()));
		eex.setReleaseStatus(Integer.parseInt(examList.get(0).get("release_status").toString()));
		eex.setTestId(Integer.parseInt( examList.get(0).get("test_id").toString()));
		eex.setRelease_time(examList.get(0).get("release_time").toString());
		eex.setE_status(Integer.parseInt(examList.get(0).get("e_status").toString()));
		if(eex.getReleaseStatus()==0){		
			eex.setReleaseStatus(2);
			nwsService.updataToDel(eex);
			page=nwsService.getUserFromExamByReleasestatus(1,user.getUserId());
			mm.put("examList", page);
		}else if(eex.getReleaseStatus()==1){
			eex.setReleaseStatus(2);
			nwsService.updataToDel(eex);
		}

		pageType=request.getParameter("pageType");
		if(pageType.equals("delCon")){
			return "redirect:/network/continue.html";
		}
		
		if(pageType.equals("delMod")){
			return "redirect:/network/modification.html";
		}
		
		if(pageType.equals("del")){
			return "redirect:/network/paperLibrary.html";
		}
		
		return null;
	}
	
	
	
	/**
	 * 进入修改试卷
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/network/modification.html")
	public String toModification(ModelMap mm,HttpServletRequest request){
		User user = getSessionUser(request);
		String pageNo = request.getParameter("pageNo");
		LStrMap<List<LStrMap<Object>>> map = null;
		if(pageNo!=null){
			map = nwsService.getUserFromExamByReleasestatusIsOne(Integer.parseInt(pageNo),user.getUserId());
		}else{
			map = nwsService.getUserFromExamByReleasestatusIsOne(1,user.getUserId());
		}
		
		List<Integer> dateList=new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		for (int i = 2000; i <= cal.get(Calendar.YEAR); i++) {
			dateList.add(i);
		}
		mm.put("dateList",dateList);
		mm.put("grades",netService.getGradeByTeacher(user.getUserId()));	//获得老师所教的年级
		mm.put("testTypeList",netService.getTestTypes("TESTTYPE"));			//获得考试类型
		mm.put("examMap", map);
		return "/network/modification";
	}
	
	/**
	 * 给试卷安排考试
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/network/planTests.json")
	public String toPlanTest(ModelMap mm,HttpServletRequest request,int examId){
		User user=getSessionUser(request);
		List<LStrMap<Object>> tests=netService.getTestsByExamId(user.getUserId(),examId);
		mm.put("testList",tests);
		return "jsonView";
	}

	//给试卷安排考试
	@RequestMapping(value="/network/arrangeTest.json",method=RequestMethod.POST)
	public String toUpdateExamTest(ModelMap mm,HttpServletRequest request,ETestEexam eTestEexam){
		netService.deleteTestExamRelation(eTestEexam.getTestId(), eTestEexam.getExamId()); //1、清除该表中原有的当前试卷对应科目的安排的考试
		eTestEexam.setHis_flag(1);
		netService.saveTestExamDao(eTestEexam);	//2、向表中新增关系数据
		return "jsonView";
	}
	
	@RequestMapping(value="/network/topaperFactory.html")
	public String toPaperFactory(ModelMap mm,HttpServletRequest request){
		return "network/paperFactory";
	}
	
	
}
