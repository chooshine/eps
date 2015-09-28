package com.eps.web.achievement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.CClass;
import com.eps.domain.Student;
import com.eps.domain.Teacher;
import com.eps.domain.User;
import com.eps.job.WeatherCity;
import com.eps.service.achievement.AchievementService;
import com.eps.service.weather.WeatherService;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.web.BaseController;
import com.eps.web.CookieUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AchievementController extends BaseController {
	
	@Autowired
	private AchievementService service;
	
	@Autowired
	private WeatherService weatherService;
	
	@RequestMapping(value="/gradeManager.html")
	public String toDifferentUserGradeManager(ModelMap mm, HttpServletRequest request) {
		User user = getSessionUser(request);
		if (user.getUserType() == 10) {	//如果是老师
			return "redirect:/achievement/myoffice.html";
		} else if (user.getUserType() == 20) {	//如果是学生
			return "redirect:/studentGrade/myDesk.html";
		} else {	//既不是老师也不是学生
			mm.put("title", "无效用户");
			mm.put("errorInfo", "抱歉！您还未开通成绩管家，请联系客服开通，欢迎您来电咨询！");
			return "/network/NotFindUser";
		}
	}
	
	@RequestMapping(value="/achievement/myoffice.html",method=RequestMethod.GET)
	public String toIndex(HttpServletRequest request,ModelMap mm){
		//mm.put("title", "成绩管家");
		getTeacherAndClass(request, mm);
		Teacher teacher = (Teacher) mm.get("teacher");
		if(teacher==null) return "redirect:/inputAuthenInfo.html";
		//获取当天教师带课情况
		List<LStrMap<Object>> lessons = service.getTodayLessonByTeacher(teacher.getTeacherId());
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
		
		//得到天气状况
		String city = CookieUtils.getCookie(request, "weatherCity")==null?"杭州":CookieUtils.getCookie(request, "weatherCity").getValue();
		LStrMap<String> weather = weatherService.getWeatherByCityName(city);
		
		mm.put("weather", weather);
		mm.put("lessons", lessons);
		//mm.put("citys", WeatherCity.getCitys());
		return "/achievement/index";
	}
	
	@RequestMapping(value="/achievement/seating.html",method=RequestMethod.GET)
	public String toSeating(HttpServletRequest request,ModelMap mm){
		getTeacherAndClass(request, mm);
		return "/achievement/seating";
	}
	
	@RequestMapping(value="/achievement/getStudnet.html")
	public String getStudentByClass(int classId,ModelMap mm){
		List<Student> students = service.getStudent(classId);
		String flag = "";
		List<Student> list = null;
		List<List<Student>> result = new ArrayList<List<Student>>();
		for(Student student:students){
			String seatNo = student.getSeatNo();
			String temp = seatNo.substring(0, 1);
			if(!flag.equals(temp)){
				list = new ArrayList<Student>();
				result.add(list);
			}
			list.add(student);
			flag = temp;
		}
		mm.put("seats", result);
		return "/achievement/seatTable";
	}
	
	@RequestMapping(value="/achievement/class/ability.html")
	public String toClassAbility(HttpServletRequest request,ModelMap mm,String class_id){
		getTeacherAndClass(request, mm);
		Teacher teacher = (Teacher) mm.get("teacher");
		List<LStrMap<Object>> list = null;
		if (class_id==null) {
			List<CClass> classes = (List<CClass>)mm.get("classes");
			if (!classes.isEmpty()) {
				int classId = classes.get(0).getClassId();
				list = service.getClassAnalyByTeacherClass(classId, teacher.getTeacherId());
				mm.put("class_id", classId);
			}
		} else {
			mm.put("class_id", class_id);
			list = service.getClassAnalyByTeacherClass(Integer.parseInt(class_id), teacher.getTeacherId());
		}
		//List<LStrMap<Object>> list = service.getClassAnalyByTeacher(1, teacher.getTeacherId());
		
		LStrMap<Object> min = null;
		LStrMap<Object> max = null;
		if (list != null) {
			for (LStrMap<Object> lStrMap : list) {
				int subjectId = (Integer) lStrMap.get("subject_id"); 
				if(subjectId == 4 || subjectId == 5 || subjectId == 6 ){
					mm.put("classInfo", lStrMap);
					continue;
				}
				if(min == null)min = lStrMap;
				if(max == null) max = lStrMap;
				if(min!= null && (Double)min.get("score_avg") > (Double)lStrMap.get("score_avg")){
					min = lStrMap;
				}
				if(max!= null && (Double)max.get("score_avg") < (Double)lStrMap.get("score_avg")){
					max = lStrMap;
				}
			}
		}
		
		Gson gson = new GsonBuilder().create();
		mm.put("max", max);
		mm.put("min", min);
		mm.put("charData", gson.toJson(list));
		//LStrMap<Object> classInfo = list.get(0);
		return "/achievement/classAbility";
	}
	
	private void getTeacherAndClass(HttpServletRequest request,ModelMap mm){
		mm.put("title", "成绩管家");
		User user = getSessionUser(request);
		Teacher teacher = service.getTeacher(user.getUserId());
		//获取登陆用户所带班级
		List<CClass> classes = teacher!=null?service.getClasses(teacher.getTeacherId()):null;
		mm.put("teacher", teacher);
		mm.put("classes", classes);
	}
	
	@RequestMapping(value="/achievement/scoreNotice.html")
	public String toScoreNotice(HttpServletRequest request,ModelMap mm){
		getTeacherAndClass(request, mm);
		Teacher teacher = (Teacher) mm.get("teacher");
		UStrMap<List<LStrMap<Object>>> map = service.getTestClassAndSubject(teacher.getTeacherId());
		List<LStrMap<Object>> subjects = map.get("subject");
		List<LStrMap<Object>> tests = map.get("test");
		List<LStrMap<Object>> classes = map.get("clazz");
		int testId = request.getParameter("testId") == null ? Integer.parseInt((String)tests.get(0).get("id")) : Integer.parseInt((String)request.getParameter("testId"));
		int classId = request.getParameter("classId") == null ? Integer.parseInt((String)classes.get(0).get("id")) : Integer.parseInt((String)request.getParameter("classId"));
		String[] subject = request.getParameter("subject") == null ? null: request.getParameter("subject").split(",");
		List<Integer> subs = new ArrayList<Integer>();
		for (LStrMap<Object> item : subjects) {
			String id = (String) item.get("id");
			boolean seledted = (Boolean) item.get("selected");
			if(subject != null && !ArrayUtils.contains(subject, id)){
				item.put("selected", false);
			}else{
				subs.add(Integer.parseInt(id));
			}
		}
		for (LStrMap<Object> item : tests) {
			int id = Integer.parseInt((String) item.get("id"));
			if(id == testId){
				item.put("selected", true);
			}else{
				item.put("selected", false);
			}
		}
		for (LStrMap<Object> item : classes) {
			int id = Integer.parseInt((String) item.get("id"));
			if(id == classId){
				item.put("selected", true);
			}else{
				item.put("selected", false);
			}
		}
		List<LStrMap<Object>> page = service.getStudentScoreNotice(testId, teacher.getTeacherId(), classId, subs);
		mm.put("page", page);
		mm.put("maps",map);
		mm.put("testid", testId);
		mm.put("classid", classId);
		return "/achievement/scoreNotice";
	}
	@RequestMapping(value="/achievement/distribution.html")
	public String toDistribution(HttpServletRequest request,ModelMap mm){
		getTeacherAndClass(request, mm);
		Teacher teacher = (Teacher) mm.get("teacher");
		//得到所有可供选择的考试、班级和科目
		UStrMap<List<LStrMap<Object>>> map = service.getTestClassAndSubject(teacher.getTeacherId());
		List<LStrMap<Object>> subjects = map.get("subject");
		List<LStrMap<Object>> tests = map.get("test");
		List<LStrMap<Object>> classes = map.get("clazz");
		int testId = request.getParameter("testId") == null ? Integer.parseInt((String)tests.get(0).get("id")) : Integer.parseInt((String)request.getParameter("testId"));
		int classId = request.getParameter("classId") == null ? Integer.parseInt((String)classes.get(0).get("id")) : Integer.parseInt((String)request.getParameter("classId"));
		int subjectId = request.getParameter("subject") == null ? Integer.parseInt((String)subjects.get(0).get("id")) : Integer.parseInt((String)request.getParameter("subject"));
		String testName = "";
		String subjectName = "";
		String className = "";
		for (LStrMap<Object> item : subjects) {
			int id = Integer.parseInt((String) item.get("id"));
			boolean seledted = (Boolean) item.get("selected");
			if(id == subjectId){
				item.put("selected", true);
				subjectName = (String) item.get("name");
			}else{
				item.put("selected", false);
			}
		}
		for(LStrMap<Object> item : tests){
			int id = Integer.parseInt((String) item.get("id"));
			if(id == testId){
				item.put("selected", true);
				testName = (String) item.get("name");
			}else{
				item.put("selected", false);
			}
		}
		for (LStrMap<Object> item : classes) {
			int id = Integer.parseInt((String) item.get("id"));
			if(id == classId){
				item.put("selected", true);
				className = (String) item.get("name");
			}else{
				item.put("selected", false);
			}
		}
		
		//得到某次考试某班的某科的成绩总结
		List<LStrMap<Object>> page = service.getClassAndGradeAnaly(teacher.getTeacherId(), testId, classId, subjectId);
		mm.put("maps",map);
		mm.put("page", page);
		mm.put("testName", testName);
		mm.put("subjectName", subjectName);
		mm.put("className", className);
		mm.put("testid", testId);
		mm.put("classid", classId);
		mm.put("subjectid", subjectId);
		return "/achievement/distribution";
	}
	@RequestMapping(value="/achievement/lesson.html")
	public String toTeacherLessonTable(HttpServletRequest request,ModelMap mm){
		getTeacherAndClass(request, mm);
		Teacher teacher = (Teacher) mm.get("teacher");
		List<LStrMap<Object>> list = service.getLessonByTeacher(teacher.getTeacherId());
		mm.put("lessons", list);
		return "/achievement/lessonTable";
	}
	
	@RequestMapping(value="/achievement/setWeatherCity.json")
	public String getWeatherCity(String city,ModelMap mm,HttpServletRequest request,HttpServletResponse response){
//		UStrMap<List<String>> citys = WeatherCity.citys;
//		mm.put("citys", citys);
		LStrMap<String> weather = weatherService.getWeatherByCityName(city);
		CookieUtils.addCookie(response, "weatherCity",city,Integer.MAX_VALUE);
		mm.put("weather", weather);
		return "jsonView";
	}
}
