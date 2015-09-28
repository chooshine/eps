package com.eps.web.scorerecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.eps.dao.MaxValueINcrementer;
import com.eps.domain.User;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.network.NetWorkService;
import com.eps.service.scorerecord.ScoreRecordService;
import com.eps.utils.HttpHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
@Controller
public class ScoreRecordController extends BaseController {
	@Autowired
	private ScoreRecordService scoreRecordService;
	
	@Autowired
	private ExamAfterService examAfterService;
	
	@Autowired
	private MaxValueINcrementer seq_testRecId;
	
	@Autowired
	private NetWorkService netService;
	
	@RequestMapping(value="/scorerecord/init.html")
	public String toInit(HttpServletRequest request, ModelMap mm){
		mm.put("title", "成绩管家");
		
		User user = this.getSessionUser(request);
		long user_id = user.getUserId();
		//得到教的所有班级
		List<LStrMap<Object>> classes = scoreRecordService.getClasses(user_id);
		//得到该老师所在学校所有班级
		Map<String,List<LStrMap<Object>>> allClass = scoreRecordService.getAllClass(user_id); 
		//得到老师所在学校的id
		List<LStrMap<Object>> schoolList = allClass.get("schoolId");
		int schoolId = (Integer)schoolList.get(0).get("school_id");
		allClass.remove("schoolId");
		//得到教的所有科目
		List<LStrMap<Object>> sorts = scoreRecordService.getSorts(user_id);
		//得到所有科目
		Map<String,List<LStrMap<Object>>> allSorts = examAfterService.getAllSortNoAndNameService();
		//试卷年份
		List<Integer> dateList = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		for (int i = 2000; i <= cal.get(Calendar.YEAR); i++) {
			dateList.add(i);
		}
		//获得己有的所有考试分类
		mm.put("sortList",examAfterService.getAllSortNoAndNameService());
		mm.put("dateList", dateList);
		// 获得老师所教的年级
		List<LStrMap<Object>> grades = netService.getGradeByTeacher(user_id);
		mm.put("schoolId", schoolId);
		mm.put("grades", grades);
		mm.put("classes", classes);
		mm.put("allClass", allClass);
		mm.put("sorts", sorts);
		mm.put("allSorts", allSorts);
		return "scorerecord/init";
	}
	
	@RequestMapping(value="/init/getTest.json")
	public String getTest(HttpServletRequest request, ModelMap mm,String class_id){
		//得到该年级最近一次考试
		List<LStrMap<Object>> test = scoreRecordService.getClosedTest(class_id);
		if (!test.isEmpty()) {
			LStrMap<Object> map = test.get(0);
			mm.put("flag", true);//true代表存在最近一次考试
			mm.put("test_name", map.get("test_name"));
			mm.put("test_id", map.get("test_id"));
			return "jsonView";
		}
		
		mm.put("flag", false);
		return "jsonView";
	}
	
	@RequestMapping(value="/init/getExam.json")
	public String getExam(HttpServletRequest request, ModelMap mm, String test_id, String sort_id){
		
		//得到选中科目在本次考试中的考试卷
		LStrMap<Object> exams = scoreRecordService.getExam(test_id, sort_id);
		if (exams == null) {
			mm.put("flag", false);
			return "jsonView";
		}
		mm.put("flag", true);
		mm.put("exam_id", exams.get("exam_id"));
		mm.put("exam_name", exams.get("exam_name"));
		return "jsonView";
	}
	
	@RequestMapping(value="/scorerecord/creattest.html")
	public String toCreateTest(HttpServletRequest request,ModelMap mm,String class_id){
		//得到年级id和学校id
		LStrMap<Object> gradeAndSchool = scoreRecordService.getGradeSchoolByClassId(class_id);
		mm.put("school_id", gradeAndSchool.get("school_id"));
		mm.put("grade_id", gradeAndSchool.get("grade_id"));
		
		return "scorerecord/createtest";
	}
	
	@RequestMapping(value="/scorerecord/createtest.json")
	public String createTest(ModelMap mm,String school_id,String grade_id,String test_time,String test_name){
		scoreRecordService.createTest();
		return "";
	}
	
	@RequestMapping(value="/scorerecord/report.html")
	public String toReportTable(HttpServletRequest request, ModelMap mm, String classId, String subjectId, String testId, String className, String testName, String subjectName){
		if (classId==null || subjectId==null || className==null) {
			return "redirect:/scorerecord/init.html";
		}
		mm.put("title", "学生信息表");
		
		List<LStrMap<Object>> students = scoreRecordService.getAllStudentAndDownload(classId, testId, subjectId);
		mm.put("classId", classId);
		mm.put("subjectId", subjectId);
		mm.put("testId", testId);
		mm.put("className", className);
		mm.put("testName", testName);
		mm.put("subjectName", subjectName);
		mm.put("students", students);
		return "scorerecord/report";
	}
	
	@RequestMapping(value="/export.html")
	public void export(HttpServletRequest request, HttpServletResponse response){
		scoreRecordService.exportXLS(request, response);
	}
	
	@RequestMapping(value="/read.html")
	public void getReadReport(HttpServletRequest request,HttpServletResponse response, ModelMap mm){
		long userId = this.getSessionUser(request).getUserId();
		//上传表格到服务器
		LStrMap<Object> upResult = scoreRecordService.uploadXls(userId, request, response);
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			try {
				HttpHelper.writeString(response, "<script>parent.showReport("+gson.toJson(upResult)+");</script>", "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value="/parserXls.json")
	public String parseXls(String path,HttpServletRequest request,ModelMap mm){
		FileInputStream fis = null;
		try {
			String xlsPath = request.getSession().getServletContext().getRealPath(path);
			fis = new FileInputStream(xlsPath);
			List<LStrMap<Object>>list = scoreRecordService.readReport(fis);
			//删除表格
			File xls = new File(xlsPath);
			// 判断文件是否存在 
			boolean flag = false; 
			flag = xls.exists(); 
			if (flag == true) {
				xls.delete();
			}
			mm.put("data", list);
		} catch (FileNotFoundException e) {
			mm.put(ERROR_MSG_KEY, "");
		}
		return "jsonView";
	}
	
	/**
	 * 保存表格数据
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/savereport.json", method=RequestMethod.POST)
	public String saveReport(HttpServletRequest request, ModelMap mm) {
		long creator = this.getSessionUser(request).getUserId();				//得到创建人
		String students= (String) request.getParameter("students");			//得到json字符串形式的学生信息
		long test_id = Long.parseLong(request.getParameter("testId"));
		long subject_id = Long.parseLong(request.getParameter("subjectId"));
		long class_id = Long.parseLong(request.getParameter("classId"));
		int count = 0;
		
		if (!students.equals("")) {
			JsonArray jsonArray = scoreRecordService.stringToJson(students);
			
			//遍历jsonArray，取出数据
			Iterator<JsonElement> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				UStrMap<Object> uStrMap = UStrMap.newInstance();	
				uStrMap.put("test_rec_id", seq_testRecId.nextIntValue());
				uStrMap.put("class_id", class_id);
				uStrMap.put("test_id", test_id);
				uStrMap.put("subject_id", subject_id);
				
				JsonElement jsonElement2 = (JsonElement) iterator.next();		//得到一个json形式的学生信息
				JsonObject jsonObject2 = jsonElement2.getAsJsonObject();		//将学生信息转为json对象，用以取值
				
				//将数据存到map中
				long user_id = jsonObject2.get("user_id").getAsLong();
				long student_id = jsonObject2.get("student_id").getAsLong();
				int exam_status = jsonObject2.get("exam_status").getAsInt();
				uStrMap.put("creator_id", creator);
				uStrMap.put("user_id", user_id);
				uStrMap.put("student_id", student_id);
				uStrMap.put("exam_status", exam_status);
				String scoreStr = jsonObject2.get("score").getAsString();
				if (scoreStr.equals("")) {
					uStrMap.put("score", null);
				} else {
					uStrMap.put("score", Float.parseFloat(scoreStr));
				}
				
				int result = scoreRecordService.insertRecord(uStrMap);
				if (result == 1) {
					count++;
				}
			}
		}
		mm.put("count", count);
		return "jsonView";
	}

	
}
