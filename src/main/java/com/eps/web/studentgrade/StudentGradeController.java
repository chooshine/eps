package com.eps.web.studentgrade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.Student;
import com.eps.domain.User;
import com.eps.job.WeatherCity;
import com.eps.service.studentgrade.StudentGradeService;
import com.eps.service.weather.WeatherService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
import com.eps.web.CookieUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Controller
public class StudentGradeController extends BaseController {
	
	@Autowired
	StudentGradeService sgs;
	
	@Autowired
	private WeatherService weatherService;
	
	/**
	 * 学生个人能力
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/studentGrade/studentAbility.html")
	public String toStudentAbility(HttpServletRequest request, ModelMap mm) {
		mm.put("title", "成绩管家");
		//得到学生基本信息,姓名，班主任，班级
		User user = this.getSessionUser(request);
		long user_id = user.getUserId();
		List<LStrMap<Object>> listBasicInfo = sgs.getStudentBasicInfo(user_id);
		if(!listBasicInfo.isEmpty()) {
			LStrMap<Object> lStrMap = listBasicInfo.get(0);
			mm.put("basicInfo", lStrMap);
		} else {
			return "studentGrade/studentAbility";
		}
		
		//得到最近一次学生各科成绩
		List<LStrMap<Object>> listClosedInfo = sgs.getClosedTestInfo(user_id);
		LStrMap<Object> min = null;
		LStrMap<Object> max = null;
		for (LStrMap<Object> lStrMap1 : listClosedInfo) {
			//得到总分和总分班级排名、年级排名以及班级参考人数、年级参考人数
			int subjectId = (Integer) lStrMap1.get("subject_id"); 
			if(subjectId == 4 || subjectId == 5 || subjectId == 6 ){
				//保存总分信息
				mm.put("total_point_info", lStrMap1);
				
				//得到班级参考人数
				int joinNum = (Integer)lStrMap1.get("join_cr");
				//得到班级排名
				int score_cr = (Integer)lStrMap1.get("score_cr");
				//得到每个头像代表的范围
				int range = 0;
				for(int i=0; ;i++){
					if (i*14<joinNum && (i+1)*14>=joinNum) {
						range = i+1;
						break;
					}
				}
				//得到突出头像的位置
				for (int i=0; i<=14; i++) {
					if ((i*range)<score_cr && (i+1)*range>=score_cr) {
						mm.put("position", i+1);
						break;
					}
				}
			}
			
			//得到本次考试最弱科和最强科,比较的是学生得分和班级平均分的比例
			if(min == null)min = lStrMap1;
			if(max == null) max = lStrMap1;
			double minScore = (Double)min.get("score")/(Double)min.get("score_avg");
			double maxScore = (Double)max.get("score")/(Double)max.get("score_avg");
			double lsmapScore = (Double)lStrMap1.get("score")/(Double)lStrMap1.get("score_avg");
			
			//保存考试分数与班级平均分的比值
			lStrMap1.put("percents", String.format("%.2f",lsmapScore));
			
			if(min!= null && minScore > lsmapScore){
				min = lStrMap1;
			}
			if(max!= null && maxScore < lsmapScore){
				max = lStrMap1;
			}
		}
		
		Gson gson = new GsonBuilder().create();
		mm.put("max", max);
		mm.put("min", min);
		mm.put("charData", gson.toJson(listClosedInfo));
		
		//学习成绩分科情况=============================================================
		//得到学生在当前班级的倒数第二次考试成绩信息
		List<LStrMap<Object>> listLastInfo = sgs.getLastTestInfo(user_id);
		
		//删除上次考试的总分
		Iterator<LStrMap<Object>> iterator1 = listLastInfo.iterator();
		while (iterator1.hasNext()) {
			int subject_id = (Integer)iterator1.next().get("subject_id");
			if (subject_id==4 || subject_id==5 || subject_id==6) {
				iterator1.remove();
			}
		}
		//删除最近一次考试的总分
		Iterator<LStrMap<Object>> iterator2 = listClosedInfo.iterator();
		while (iterator2.hasNext()) {
			int subject_id = (Integer)iterator2.next().get("subject_id");
			if (subject_id==4 || subject_id==5 || subject_id==6) {
				iterator2.remove();
			}
		}
		
		//将最近一次考试和上一次考试的信息放到一条记录中
		for(int i=0; !listClosedInfo.isEmpty()&&i<listClosedInfo.size(); i++) {
			LStrMap<Object> lStrMapClosed = listClosedInfo.get(i);
			
			for (LStrMap<Object> lStrMap : listLastInfo) {
				if(lStrMap.get("sort_name").equals(lStrMapClosed.get("sort_name"))) {
					lStrMapClosed.put("test_name2", lStrMap.get("test_name"));
					lStrMapClosed.put("score2", lStrMap.get("score"));
					lStrMapClosed.put("score_cr2", lStrMap.get("score_cr"));
					int score1 = (Integer)lStrMapClosed.get("score_cr");
					int score2 = (Integer)lStrMapClosed.get("score_cr2");
					int progress = score1-score2;
					if(progress>0){
						lStrMapClosed.put("progress", "退步"+progress+"名");
					} else if(progress<0){
						lStrMapClosed.put("progress", "进步"+(-progress)+"名");
					} else {
						lStrMapClosed.put("progress", "与上次持平");
					}
				}
			}
		}
		
		Gson gsonSubject= new GsonBuilder().create();
		mm.put("columnData", gsonSubject.toJson(listClosedInfo));
		return "studentGrade/studentAbility";
	}
	
	
	/**
	 * 试卷分析
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/studentGrade/examAnaly.html")
	public String toExamAnaly(HttpServletRequest request, ModelMap mm) {
		mm.put("title", "成绩管家");
		User user = this.getSessionUser(request);
		long user_id = user.getUserId();
		
		
		//*********************试卷分析最新设计******************************************************************
		//得到最近一次考试的信息
		List<LStrMap<Object>> list_closed = sgs.getClosedTestInfo(user_id);
		//将所有科目id和科目名称放到一个list中
		List<LStrMap<Object>> subjectList = new ArrayList<LStrMap<Object>>();
		//得到所有科目id和科目名称
		for(LStrMap<Object> map2 : list_closed){
			//用一个map存储一个科目id及其对应的科目名称
			LStrMap<Object> subjectMap = LStrMap.newInstance();
			subjectMap.put("subject_id", map2.get("subject_id"));
			subjectMap.put("subject_name", map2.get("sort_name"));
			subjectList.add(subjectMap);
		}
		mm.put("allsubjects", subjectList);
		
		//得到四次考试的考试id和考试名称
		List<LStrMap<Object>> fourTestNameId = sgs.getFourTestNameId(user_id);
		mm.put("testnameids", fourTestNameId);
		return "studentGrade/examAnaly";
	}
	
	@RequestMapping(value="/developTable.html")
	public String getTwoTestInfo(HttpServletRequest request, ModelMap mm, String test_id1, String test_id2, String subject_ids){
		long user_id = this.getSessionUser(request).getUserId();
		List<LStrMap<Object>> testInfo1 = sgs.getOneTestInfo(user_id, Integer.parseInt(test_id1), subject_ids);
		List<LStrMap<Object>> testInfo2 = sgs.getOneTestInfo(user_id, Integer.parseInt(test_id2), subject_ids);
		
		String testname = (String)testInfo1.get(0).get("test_name");
		mm.put("testname", testname);

		String sortnames = "";
		//将两次考试信息联接
		for(int i=0; !testInfo2.isEmpty()&&i<testInfo2.size(); i++) {
			LStrMap<Object> lStrMap1 = testInfo1.get(i);
			sortnames = sortnames+(String)lStrMap1.get("sort_name")+",";
			for (LStrMap<Object> lStrMap : testInfo2) {
				if(lStrMap.get("sort_name").equals(lStrMap1.get("sort_name"))) {
					lStrMap1.put("test_name2", lStrMap.get("test_name"));
					lStrMap1.put("score2", lStrMap.get("score"));
					lStrMap1.put("score_cr2", lStrMap.get("score_cr"));
					lStrMap1.put("score_gr2", lStrMap.get("score_gr"));
					int cr1 = (Integer)lStrMap1.get("score_cr");
					int cr2 = (Integer)lStrMap1.get("score_cr2");
					int class_progress = cr1-cr2;
					if(class_progress>0){
						lStrMap1.put("cr_progress", "退步"+class_progress+"名");
					} else if(class_progress<0){
						lStrMap1.put("cr_progress", "进步"+(-class_progress)+"名");
					} else {
						lStrMap1.put("cr_progress", "与上次持平");
					}
					int gr1 = (Integer)lStrMap1.get("score_gr");
					int gr2 = (Integer)lStrMap1.get("score_gr2");
					int grade_progress = gr1-gr2;
					if(grade_progress>0){
						lStrMap1.put("gr_progress", "退步"+grade_progress+"名");
					} else if(grade_progress<0){
						lStrMap1.put("gr_progress", "进步"+(-grade_progress)+"名");
					} else {
						lStrMap1.put("gr_progress", "与上次持平");
					}
					
					break;
				}
			}
		}
		
		mm.put("sortnames", sortnames.substring(0, sortnames.length()-1));
		mm.put("twoInfo", testInfo1);
		Gson gson = new GsonBuilder().create();
		mm.put("chartdata", gson.toJson(testInfo1));
		return "studentGrade/developTable";
	}
	
	private void getStudent(HttpServletRequest request,ModelMap mm){
		mm.put("title", "成绩管家");
		User user = getSessionUser(request);
		Student student = sgs.getStudent(user.getUserId());
		String phoneNumber =  user.getPhone();
		if(phoneNumber != null) {
			mm.put("phoneNumber", phoneNumber);
		}
		mm.put("student", student);
	}
	
	@RequestMapping(value="/studentGrade/studentLesson.html")
	public String toTeacherLessonTable(HttpServletRequest request,ModelMap mm){
		getStudent(request, mm);
		Student student = (Student) mm.get("student");
		List<LStrMap<Object>> list = sgs.getLessonByStudent(student.getStudentId());
		mm.put("lessons", list);
		return "studentGrade/studentlessonTable";
	}
	
	
	@RequestMapping(value="/studentGrade/myDesk.html",method=RequestMethod.GET)
	public String toIndex(HttpServletRequest request,ModelMap mm){
		//得到学生基本信息
		getStudent(request, mm);
		Student student = (Student) mm.get("student");
	
		//获取学生当天课程安排
		List<LStrMap<Object>> lessons = sgs.getTodayLessonByStudent(student.getStudentId());
		
		//得到下一节课
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		for (LStrMap<Object> lesson : lessons) {
			String time = (String)lesson.get("time");
			String[] str = time.split("-");
			int temphour = Integer.parseInt(str[0].split(":")[0]);
			int tempMin = Integer.parseInt(str[0].split(":")[1]);
			if(temphour > hour || ( hour==temphour && tempMin > min)){
				mm.put("nextLesson", lesson);
				break;
			}
		}
		
		String city = CookieUtils.getCookie(request, "weatherCity")==null?"杭州":CookieUtils.getCookie(request, "weatherCity").getValue();
		LStrMap<String> weather = weatherService.getWeatherByCityName(city);
		mm.put("weather", weather);
		mm.put("lessons", lessons);
		//mm.put("citys", WeatherCity.getCitys());
		return "studentGrade/myDesk";
	}
	
	@RequestMapping(value="/getCityWeather.json")
	public String getCityWeather(ModelMap mm,HttpServletRequest request){
		String cityName = request.getParameter("cityName");
		String city=cityName.split("\\(")[0];
		LStrMap<String> weather = weatherService.getWeatherByCityName(city);
		String currenWeather = weather.get("currenWeather");
		String minandmax = weather.get("minandmax");
		String windpower = weather.get("windpower");
		String icon = weather.get("icon");
		String icon2 = weather.get("icon2");
		String todayWeather = weather.get("todayweather");
		mm.put("currenWeather", currenWeather);
		mm.put("minandmax", minandmax);
		mm.put("windpower", windpower);
		mm.put("icon", icon);
		mm.put("icon2", icon2);
		mm.put("todayweather", todayWeather);
		return "jsonView";
	}
	
	@RequestMapping(value="/studentGrade/studentDevelopment.html")
	public String toDevelopment(HttpServletRequest request, ModelMap mm) {
		getStudent(request, mm);
		Student student = (Student) mm.get("student");
		
		//得到科目
		List<LStrMap<Object>> subjects = sgs.getAllSubject(student.getStudentId());
		
		//从页面得到科目编号
		int subject_id = 0;
		if(request.getParameter("subject_id") == null) {
			subject_id = (Integer) subjects.get(0).get("sort_id");
		} else {
			subject_id = Integer.parseInt(request.getParameter("subject"));
		}
		
		for(LStrMap<Object> subject : subjects) {
			if(subject.get("sort_id").equals(subject_id)) {
				subject.remove("selected");
				subject.put("selected", true);
				mm.put("sort_name", subject.get("sort_name"));
				break;
			}
		}
		
		//从页面得到选择的时间
		Calendar minTime = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		if(request.getParameter("small_time") != null) {
			String[] time =  request.getParameter("small_time").split("-");
			minTime.set(Calendar.YEAR, Integer.parseInt(time[0]));
			minTime.set(Calendar.MONTH, Integer.parseInt(time[1]));
		} else {
			minTime.set(Calendar.YEAR, year-5);
			minTime.set(Calendar.MONTH, 1);
		}
		//得到单个科目多次考试信息
		List<LStrMap<Object>> list = sgs.getSingleSubject(student.getStudentId(), minTime.getTime(), now.getTime(), subject_id);
		
		//设置页面下拉框的时间
		List<LStrMap<Object>> times = new ArrayList<LStrMap<Object>>();
		int minTime_year = minTime.get(Calendar.YEAR);
		for(int i=0; i<=5; i++) {
			LStrMap<Object> time = LStrMap.newInstance();
			int temp = year-i;
			time.put("time", temp+"-01");
			if(temp == minTime_year) {
				time.put("selected", true);
			} else {
				time.put("selected", false);
			}
			times.add(time);
		}
		
		Gson gson = new GsonBuilder().create();
		mm.put("student", student);
		mm.put("times", times);
		mm.put("subjects", subjects);
		mm.put("subjectInfos", list);
		mm.put("subjectInfosChart", gson.toJson(list));
		return "studentGrade/studentDevelopment";
	}
}
