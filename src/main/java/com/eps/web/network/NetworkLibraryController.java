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
import com.eps.domain.User;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.network.NetWorkService;
import com.eps.service.network.NetworkLibraryService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class NetworkLibraryController extends BaseController{
		
	@Autowired
	NetworkLibraryService nklServic;
	private Page page;
	@Autowired
	private ExamAfterService eas;
	@Autowired
	private NetWorkService netService;	
	/**
	 * 查询我的试卷(两种1未发布2已发布)
	 * get请求
	 * @return
	 */
	@RequestMapping(value="/network/paperLibrary.html")
	public String toPaperLibrary(ModelMap mm,HttpServletRequest request){
		User user = getSessionUser(request);
		String pageNo = request.getParameter("pageNo");
		String release_time = request.getParameter("releaseTime");
		List<Integer> dateList=new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		for (int i = 2000; i <= cal.get(Calendar.YEAR); i++) {
			dateList.add(i);
		}
		if(release_time==null || release_time.equals("")){
			release_time=dateList.get(dateList.size()-1).toString();
		}
		if(pageNo!=null) {
			page=nklServic.getExamStatues(Integer.parseInt(pageNo),user.getUserId(),release_time);
		} else {
			page=nklServic.getExamStatues(1,user.getUserId(),release_time);
		}
		
		mm.put("libList", page);
		mm.put("dateList",dateList);
		mm.put("grades",netService.getGradeByTeacher(user.getUserId()));	//获得老师所教的年级
		mm.put("testTypeList",netService.getTestTypes("TESTTYPE"));			//获得考试类型
		return "network/paperLibrary";
	}
	
	/**
	 * 搜索试卷
	 * post请求
	 * @return
	 */
	@RequestMapping(value="/network/paperLibrary.html",method=RequestMethod.POST)
	public String toPaperLibraryJson(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		String pageNo=request.getParameter("pageNo");
		String release_time=request.getParameter("releaseTime");
		if(pageNo!=null) {
			page=nklServic.getExamStatues(Integer.parseInt(pageNo),user.getUserId(),release_time);
		} else {
			page=nklServic.getExamStatues(1,user.getUserId(),release_time);
		}
		mm.put("libList", page);
		return "network/libList";
	}
	
	//获得符合条件的分享试卷
	private void getAskedShareExam(ModelMap mm, int pageNo, String releaseTime, int schoolId, int subjectId, long userId) {
		Page page = nklServic.selectExamStatusOne(pageNo, releaseTime, schoolId, subjectId, userId);
		mm.put("liberList", page);
	}
	
	//搜索共享的试卷
	@RequestMapping(value="/network/sharelList.html", method=RequestMethod.POST)
	public String viewAllNetworkJson(ModelMap mm,HttpServletRequest request){
		long userId = getSessionUser(request).getUserId();
		String pageNo = request.getParameter("pageNo");
		String releaseTime = request.getParameter("releaseTime");
		int schoolId = Integer.parseInt(request.getParameter("schoolId"));
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		
		if(pageNo!=null){
			getAskedShareExam(mm, Integer.parseInt(pageNo), releaseTime, schoolId, subjectId, userId);
		}else{
			getAskedShareExam(mm, 1, releaseTime, schoolId, subjectId, userId);
		}
		return "/network/shareList";
	}
	
	//进入“他人分享”页面
	@RequestMapping(value="/network/net_share.html")
	public String viewAllNetwork(ModelMap mm,HttpServletRequest request){
		long userId = getSessionUser(request).getUserId();
		
		//获得年份，筛选条件及新建考试都会用到
		List<Integer> dateList=new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		for (int i = 2000; i <= cal.get(Calendar.YEAR); i++) {
			dateList.add(i);
		}
		//获得地区
		List<LStrMap<Object>> areaList = eas.getAreaFromCodeService("area");
		//获得学校，默认取得浙江的学校，5是浙江的地区代码
		List<LStrMap<Object>> schoolList = nklServic.getSchoolsByArea("5");
		//获得科目，筛选条件及新建考试都会用到
		List<LStrMap<Object>> sortList = netService.getTeachsOfTeacher(userId);
		
		String releaseTime = dateList.get(dateList.size()-2).toString();
		int schoolId = -1;
		if (schoolList.size() >0) {
			schoolId = (Integer)schoolList.get(0).get("school_id");
		}
		int sortId = -1;
		if (sortList.size() > 0) {
			sortId = (Integer)sortList.get(0).get("sort_id");
		}
		getAskedShareExam(mm, 1, releaseTime, schoolId, sortId, userId);
		
		//筛选条件和新建考试都需要的数据
		mm.put("dateList",dateList);
		mm.put("sortList",eas.getAllSortNoAndNameService());
		
		//筛选条件需要的数据
		mm.put("areaList",areaList);
		mm.put("schoolList",schoolList);
		mm.put("teacherSortList", sortList);
		
		//新建考试需要的数据
		mm.put("grades",netService.getGradeByTeacher(userId));		//获得老师所教的年级，新建考试用
		mm.put("testTypeList",netService.getTestTypes("TESTTYPE"));	//获得考试类型，新建考试用
		return "/network/net_share";
	}
	
	@RequestMapping(value="/network/upPaperLibrary.json")
	public String updateExamByStatues(ModelMap mm,HttpServletRequest request,EExam eex){
		nklServic.updateExamByStatues(eex);
		return "JsonView";
	}
	
	/**
	 * 地区和学校的级联
	 * @param mm
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/network/net_shareSelect.json",method=RequestMethod.POST)
	public String toSelect(ModelMap mm,HttpServletRequest request,int codeId){
		List<LStrMap<Object>> schoolList=nklServic.byCode(codeId);
		GsonBuilder gsonBuilder =new GsonBuilder();
		Gson gson = gsonBuilder.create();
		mm.put("schoolList", gson.toJson(schoolList));
		return "jsonView";
	}
	
	/**
	 * 取消共享
	 * @param mm
	 */
	@RequestMapping(value="/network/net_cancelShare.json",method=RequestMethod.POST)
	public String cancelShare(ModelMap mm,HttpServletRequest request,EExam exam){
		nklServic.updateExamEStatue(exam);
		return "JsonView";
	}
	
	
	
}
