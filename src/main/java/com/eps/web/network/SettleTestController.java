package com.eps.web.network;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.eps.dao.Page;
import com.eps.domain.NNetWork_Arrange;
import com.eps.domain.User;
import com.eps.service.network.NetWorkService;
import com.eps.service.network.SettleTestService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;


@Controller
public class SettleTestController extends BaseController{
	
	@Autowired
	SettleTestService stService;
	@Autowired
	private NetWorkService netService;
	private Page page;
	
	/**
	 * 到待安排页面
	 * @return
	 */
	@RequestMapping(value="/netWork/net_plan.html",method=RequestMethod.GET)
	public String toPlan(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		String pageNo=request.getParameter("pageNo");
		if(pageNo!=null) {
			page=stService.getTests(Integer.parseInt(pageNo),user.getUserId());
		} else {
			page=stService.getTests(1,user.getUserId());
		}
		mm.put("testsPage", page);
		
		return "network/plan/net_plan";
	}
	
	//更新自动阅卷字段
	@RequestMapping(value="/network/settle/updateAutoMark.json", method=RequestMethod.POST)
	public String updateAutoMarkFlag(HttpServletRequest request) {
		stService.updateAutoMarkFlag(Integer.parseInt(request.getParameter("testId").toString()), Integer.parseInt(request.getParameter("sortId").toString()), Integer.parseInt(request.getParameter("autoMarkFlag").toString()));
		return "jsonView";
	}
	
	
	//获得老师所教班级所在年级的所有班级的考场安排信息
	@RequestMapping(value="/netWork/getClasses.html",method=RequestMethod.POST)
	public String toPlanClasszz(ModelMap mm,HttpServletRequest request){
		int sortId = Integer.parseInt(request.getParameter("sort_Id"));
		int testId = Integer.parseInt(request.getParameter("test_Id"));
		
		//得到当前选中的考试名称、科目名称、试卷名称、考试开始时间、考试结束时间
		LStrMap<Object> currentInfo = stService.getCurrentInfo(sortId, testId);
		//得到班级列表
		List<LStrMap<Object>> classesList = stService.getClasszz(sortId, testId);
		
		mm.put("currentInfo", currentInfo);
		mm.put("classList", classesList);
		mm.put("automark", stService.getAutoMarkFlag(testId, sortId));//得到是否可以阅卷的标志
		return "network/plan/arrangeRoomDialog_ClassList";
	}
	
	
	/**
	 * 安排考场
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/netWork/saveClass.json",method=RequestMethod.POST)
	public String saveClass(ModelMap mm,HttpServletRequest request,NNetWork_Arrange nnwa) {
		User user=getSessionUser(request);
		NNetWork_Arrange oldplan=new NNetWork_Arrange();
		
		oldplan = stService.getOldPlan(nnwa.getTest_Id(), nnwa.getSubject_Id(), nnwa.getClass_Id());
		if(oldplan!=null){
			oldplan.setDate(nnwa.getDate());
			oldplan.setEndDate(nnwa.getEndDate());
			oldplan.setTeacher_Name(nnwa.getTeacher_Name());
			oldplan.setNetWork_Addr(nnwa.getNetWork_Addr());
			stService.updatePlan(oldplan);
		}else{
			nnwa.setNetWork_Status(0);
			nnwa.setUser_Id(user.getUserId());			//安排人
			int arrId = stService.saveArrange(nnwa);	//记录考场安排信息
			mm.put("arrangeId", arrId);	//记录考场安排编号
		}
		return "jsonView";
	}
	
	/**
	 * 删除安排信息
	 ** @param 
	 * @return
	 */
	@RequestMapping(value="/netWork/delPlan.json",method=RequestMethod.POST)
	public String delPlan(ModelMap mm,HttpServletRequest request,NNetWork_Arrange nnwa){
		stService.delPlan(nnwa);
		return "jsonView";
	}
	
	
	/**
	 * 跳转到已经安排考试的信息
	 ** @param user_id
	 * @return
	 */
	@RequestMapping(value="/network/plantests.html")
	public String toPlanClasses(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		List<LStrMap<Object>> planClazz=stService.getplanTest(user.getUserId());
		//科目分类开始
		List planClazzess=new ArrayList();
		Map map = new LinkedMap();
		for(int i=0;i<planClazz.size();){
			String key = planClazz.get(i).get("test_subj").toString();
			LStrMap<List> tempMap = LStrMap.newInstance();
			List tempList = new LinkedList<LStrMap<Object>>();
			tempList.add(planClazz.get(i));
			for(int j=i+1; j<planClazz.size(); j++) {
				if(key.equals(planClazz.get(j).get("test_subj").toString())) {
					tempList.add(planClazz.get(j));
				}
			}
			
			i = i+tempList.size();
			tempMap.put(key, tempList);
			planClazzess.add(tempMap);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String str=sdf.format(date); 
		mm.put("sysdate", str);
		mm.put("planClazz", planClazzess);
		return "network/plan/planClazz";
	}
	
	/**
	 * 到当前考场页面
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value="/netWork/networking.html")
	public String toNetworkIng(ModelMap mm,HttpServletRequest request){
		return "network/plan/networking";
	}
	
	/**
	 * 查看正在考试
	 ** @param user_id
	 * @return
	 */
	@RequestMapping(value="/netWork/networ.html")
	public String toNetwork(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		String pageNo=request.getParameter("pageNo");
		if(pageNo!=null) {
			page=stService.getTestIng(Integer.parseInt(pageNo), user.getUserId());
		} else {
			page=stService.getTestIng(1,user.getUserId());
		}
		mm.put("testInfo",page);
		return "network/plan/networking";
	}
	
	/**
	 * 即将考试页面
	 ** @param user_id
	 * @return
	 */
	@RequestMapping(value="/netWork/about.html")
	public String toAbout(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		String pageNo=request.getParameter("pageNo");
		if(pageNo!=null) {
			page=stService.getAboutTest(Integer.parseInt(pageNo), user.getUserId());
		} else {
			page=stService.getAboutTest(1,user.getUserId());
		}
		mm.put("aboutList", page);
		return "network/plan/about";
	}
	
	/**
	 * 考试结果查看
	 ** @param user_id
	 * @return
	 */
	@RequestMapping(value="/netWork/result.html")
	public String toResult(ModelMap mm,HttpServletRequest request){
		mm.put("unmarkedInfo", stService.getUnmakedExamsAndClass(getSessionUser(request).getUserId()));
		return "network/plan/result";
	}
	

	/**
	 * 到试卷页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/netWork/room.html")
	public String toRoom(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		List<LStrMap<Object>> list=netService.getTeacherAllNewWork(user.getUserId());	
		//当前共有XX学生正在考试
		List<LStrMap<Object>> listStu=stService.getTeacherStu(user.getUserId());
		//当前共有XX学生参加过考试
		List<LStrMap<Object>> listStus=stService.getTeacherStus(user.getUserId());
		//当前共有XX班级未安排考试
		List<LStrMap<Object>> listNoPlan=stService.getTeacherNoPlan(user.getUserId());
		mm.put("listStu", listStu);
		mm.put("listStus", listStus);
		mm.put("listNoPlan", listNoPlan);
		mm.put("netWorkList", list);
		return "network/room";
	}
	
	//显示某个班级所有参加了考试的学生的试卷阅卷信息
	@RequestMapping(value="/network/plan/showEnterExamStudents.html", method=RequestMethod.POST)
	public String showEnterExamStudents(String className, String testName, String sortName, String classNum, 
			String actualNum, String markedNum, String unmarkedNum, String classId, String testId, String subjectId, ModelMap mm) {
		mm.put("title", "班级信息");
		mm.put("className", className);
		mm.put("testName", testName);
		mm.put("sortName", sortName);
		mm.put("classNum", classNum);
		mm.put("actualNum", actualNum);
		mm.put("markedNum", markedNum);
		mm.put("unmarkedNum", unmarkedNum);
		mm.put("students", stService.getEnterExamStudentsInfo(Long.parseLong(classId), Long.parseLong(testId) ,Long.parseLong(subjectId)));
		return "network/plan/showEnterExamStudents";
	}
	
	//显示某学生所在班级所有参加了考试的学生的试卷阅卷信息
	@RequestMapping(value="/network/showEnterExamStudents.html", method=RequestMethod.POST)
	public String shEnterExamStus(int testId, int subjectId, HttpServletRequest request, ModelMap mm) {
		LStrMap<Object> markingInfo = stService.getMarkingInfo(testId, subjectId, getSessionUser(request).getUserId());
		mm.put("className", markingInfo.get("class_name"));
		mm.put("testName", markingInfo.get("test_name"));
		mm.put("sortName", markingInfo.get("sort_name"));
		mm.put("classNum", markingInfo.get("class_num"));
		mm.put("actualNum", markingInfo.get("actual_num"));
		mm.put("markedNum", markingInfo.get("marked_num"));
		mm.put("unmarkedNum", markingInfo.get("unmarked_num"));
		mm.put("students", stService.getEnterExamStudentsInfo(Long.parseLong(markingInfo.get("class_id").toString()), Long.parseLong(markingInfo.get("test_id").toString()) ,Long.parseLong(markingInfo.get("sort_id").toString())));
		return "network/plan/showEnterExamStudents";
	}
	
	@RequestMapping(value="/network/plan/test.html")
	public String getTestHtml() {
		return "network/plan/test";
	}
	@RequestMapping(value="/network/plan/testJson.html")
	public String getTestHtmlJson() {
		return "network/plan/testJson";
	}

}
