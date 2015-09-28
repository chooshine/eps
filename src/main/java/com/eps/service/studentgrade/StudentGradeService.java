package com.eps.service.studentgrade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.cons.CommonConstant;
import com.eps.dao.achievement.StudentDao;
import com.eps.dao.studentgrade.DevelopmentDao;
import com.eps.dao.studentgrade.ExamAnalyDao;
import com.eps.dao.studentgrade.StudentAbilityDao;
import com.eps.dao.studentgrade.StudentLessonDao;
import com.eps.domain.Student;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Service
public class StudentGradeService {

	@Autowired
	private StudentLessonDao sld;
	
	@Autowired
	private StudentDao sdao;
	
	@Autowired
	private DevelopmentDao ddao;
	
	@Autowired
	private StudentAbilityDao sad;
	
	@Autowired 
	private ExamAnalyDao ead;
	
	/**
	 * 得到一次考试的信息
	 * @param user_id
	 * @param test_id
	 * @return
	 */
	public List<LStrMap<Object>> getOneTestInfo(long user_id, int test_id, String subjects){
		//存储科目编号
		List<Integer> subs = new ArrayList<Integer>();
		for (String item : subjects.split(",")) {
			subs.add(Integer.parseInt(item));
		}
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", user_id);
		map.put("test_id", test_id);
		map.put("subject_id", subs);
		return ead.getOneTestInfo(map);
	}
	
	/**
	 * 得到四次考试的考试名和编号
	 * @return
	 */
	public List<LStrMap<Object>> getFourTestNameId(long user_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", user_id);
		return ead.getFourTestNameId(map);
	}
	
	/**
	 * 得到四次考试时间
	 * @param user_id
	 * @param test_id
	 * @return
	 */
	public List<LStrMap<Object>> getTime(long user_id,int test_id) {
		return ead.getTime(user_id, test_id);
	} 
	
	/**
	 * 得到前两次考试成绩的连接和多次考试成绩的分解
	 * @param user_id
	 * @param test_id
	 * @param subjects
	 * @return
	 */
	public UStrMap<List<LStrMap<Object>>> getFourTestInfo(long user_id, int test_id, List<Integer> subjects) {
		UStrMap<List<LStrMap<Object>>> result = UStrMap.newInstance();
		List<LStrMap<Object>> times = this.getTime(user_id, test_id);
		List<List<LStrMap<Object>>> lists = new ArrayList<List<LStrMap<Object>>>();
		
		for (int i = 0; i<times.size(); i++) {
			if (times.get(i) != null) {
//				lists.add(ead.getOneTestInfo(user_id, (Date)times.get(i).get("test_time"), subjects));
			}
		}
		
		List<LStrMap<Object>> firstList = lists.get(0);
		//如果有第二次考试，则将第一次考试和第二次考试根据科目名连接
		if(lists.size()>1 && (lists.get(1) != null)) {
			for(int i=0; i<lists.get(0).size(); i++) 
				for(int j=0; j<lists.get(1).size(); j++){
				if(lists.get(1).get(j).get("sort_name").equals(lists.get(0).get(i).get("sort_name"))) {
					lists.get(0).get(i).put("score2", lists.get(1).get(j).get("score"));
					lists.get(0).get(i).put("score_cr2", lists.get(1).get(j).get("score_cr"));
					lists.get(0).get(i).put("score_gr2", lists.get(1).get(j).get("score_gr"));
					//得到进步名次
					int first = (Integer) lists.get(0).get(i).get("score_cr");
					int second = (Integer) lists.get(1).get(j).get("score_cr");
					lists.get(0).get(i).put("progress",first-second);
					break;
				}
			}
		}
		result.put("two", lists.get(0));
		
		// 用一个ArrayList存储所有考试名称
		List<LStrMap<Object>> test_names = new ArrayList<LStrMap<Object>>();
		// 用一个ArrayList存储所有考试科目及分数
		List<LStrMap<Object>> subjectsAndScores = new ArrayList<LStrMap<Object>>();

		// 得到考试名称
		for (int i = 0; i < lists.size(); i++) {
			LStrMap<Object> test_name = LStrMap.newInstance();
			test_name.put("test_name", lists.get(i).get(0).get("test_name"));
			if (!ArrayUtils.contains(test_names.toArray(), test_name)) {
				test_names.add(test_name);
			}
		}

		// 得到考试科目名及考试分数
		for (int i = 0; i < lists.get(0).size(); i++) {
			LStrMap<Object> record = lists.get(0).get(i);
			// 用一个map存储一个科目的科目名和所有分数
			LStrMap<Object> subjectAndScores = LStrMap.newInstance();
			subjectAndScores.put("subject", record.get("sort_name"));

			// 用一个ArrayList存储一个科目的多个分数
			List<Object> scores = new ArrayList<Object>();
			scores.add(record.get("score"));

			for (int j = 1; j < lists.size(); j++) {
				for (int k = 0; k < lists.get(j).size(); k++) {
					LStrMap<Object> otherRecord = lists.get(j).get(k);
					if (record.get("sort_name").equals(otherRecord.get("sort_name"))) {
						scores.add(otherRecord.get("score"));
						break;
					}
				}
			}
			// 得到某科成绩的所有分数
			subjectAndScores.put("scores", scores);
			// 得到所有科目的所有分数
			subjectsAndScores.add(subjectAndScores);
		}
		
		//将多次考试成绩根据科目名连接
				for (int j = 0; j < firstList.size(); j++) {
					firstList.get(j).put("score1", firstList.get(j).get("score"));
					firstList.get(j).put("test_name1", firstList.get(j).get("test_name"));
					for (int i = 1; i < lists.size(); i++)
						for (int k = 0; k < lists.get(i).size(); k++) {
							if (lists.get(i).get(k).get("sort_name").equals(firstList.get(j).get("sort_name"))) {
								firstList.get(j).put("score"+(i+1),	lists.get(i).get(k).get("score"));
								firstList.get(j).put("test_name"+(i+1), lists.get(i).get(k).get("test_name"));
								break;
							}
						}
				}
				
			
		result.put("testNames", test_names);
		result.put("subjectsAndScores", subjectsAndScores);
		result.put("severalInfo", firstList);
		return result;
	}
	
	/**
	 * 得到学生所在班级的所有考试的考试号和科目
	 * @param user_id
	 * @return
	 */
	public UStrMap<List<LStrMap<Object>>> getTestSort(long user_id){
		List<LStrMap<Object>> list = ead.getTestSort(user_id);
		List<LStrMap<Object>> tests = new ArrayList<LStrMap<Object>>();
		List<LStrMap<Object>> subjects = new ArrayList<LStrMap<Object>>();
		
		List<String> temp1 = new ArrayList<String>();
		List<String> temp2 = new ArrayList<String>();
		
		for (LStrMap<Object> item : list) {
			String testId = String.valueOf(item.get("TEST_ID"));
			String testName = String.valueOf(item.get("test_name"));
			String subjectId = String.valueOf(item.get("sort_id"));
			String subjectName = String.valueOf(item.get("sort_name"));
			boolean isSelected = false;
			if(temp1.equals("")){
				isSelected = true;
			}
			if(!ArrayUtils.contains(temp1.toArray(),testId)){
				LStrMap<Object> map = LStrMap.newInstance();
				map.put("id", testId);
				map.put("name", testName);
				map.put("selected", isSelected);
				tests.add(map);
				temp1.add(testId);
			}
			if(!ArrayUtils.contains(temp2.toArray(),subjectId)){
				LStrMap<Object> map = LStrMap.newInstance();
				map.put("id", subjectId);
				map.put("name", subjectName);
				map.put("selected", true);
				subjects.add(map);
				temp2.add(subjectId);
			}
		}
		UStrMap<List<LStrMap<Object>>> result = UStrMap.newInstance();
		result.put("test", tests);
		result.put("subject", subjects);
		return result;
	}
	/**
	 * 得到学生基本信息
	 */
	public List<LStrMap<Object>> getStudentBasicInfo(long user_id){
		return sad.getStudentBasicInfo(user_id);
	}

	/**
	 * 得到学生最近一次考试，各科分数
	 */
	public List<LStrMap<Object>> getClosedTestInfo(long user_id){
		return sad.getClosedTestInfo(user_id);
	} 
	
	/**
	 * 得到学生上一次考试，各科分数
	 */
	public List<LStrMap<Object>> getLastTestInfo(long user_id){
		return sad.getLastTestInfo(user_id);
	} 
	/**
	 * 获取学生今天有的课
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTodayLessonByStudent(int student_id){
		List<LStrMap<Object>> list = sld.getTodayLessonByStudent(student_id);
		for (LStrMap<Object> item : list) {
			int lessonTime = (Integer)item.get("lesson_time");
			item.put("time", CommonConstant.LESSON_TIME[lessonTime-1]);
		}
		return list;
	}
	/**
	 * 获取学生课表
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getLessonByStudent(int student_id){
		List<LStrMap<Object>> list = sld.getLessonByStudent(student_id);
		List<LStrMap<Object>> result = new ArrayList<LStrMap<Object>>();
		for(int i=0;i<10;i++){
			LStrMap<Object> item = LStrMap.newInstance();
			item.put("time", CommonConstant.LESSON_TIME[i]);
			for(int j=1;j<7;j++){
				item.put(String.valueOf(j), null);
			}
			result.add(item);
		}
		for (LStrMap<Object> lesson : list) {
			int week = (Integer) lesson.get("weekday");
			int time = (Integer) lesson.get("lesson_time");
			LStrMap<Object> item = result.get(time-1);
			item.put(String.valueOf(week), lesson);
			
			//item.put("time", CommonConstant.LESSON_TIME[time-1]);
		}
		return result;
	}
	
	public Student getStudent(long userId) {
		return sdao.load(userId);
	}
	
	/**
	 * 得到学生参加过的所有考试科目
	 * @param student_id
	 * @return
	 */
	public List<LStrMap<Object>> getAllSubject(int student_id) {
		List<LStrMap<Object>> list = ddao.getAllSubject(student_id);
		for(LStrMap<Object> temp : list){
			temp.put("selected", false);
		}
		return list;
	}
	
	/**
	 * 得到学生某科成绩在所有考试中的成绩
	 * @param student_id
	 * @param time1
	 * @param time2
	 * @param subject_id
	 * @return
	 */
	public List<LStrMap<Object>> getSingleSubject(int student_id, Date time1, Date time2, int subject_id) {
		List<LStrMap<Object>> list = ddao.getSingleSubject(student_id, time1, time2, subject_id);
		return list;
	}
}
