package com.eps.service.network;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.Page;
import com.eps.dao.network.SettleTestDao;
import com.eps.domain.NNetWork_Arrange;
import com.eps.utils.LStrMap;

@Service
public class SettleTestService {

	@Autowired
	private SettleTestDao stDap;
	//安排
	@Autowired
	private MaxValueINcrementer seq_arrangeId;
	
	public List<LStrMap<Object>> getTeacherStu(long user_id){
		return stDap.getTeacherStu(user_id);
	}
	public List<LStrMap<Object>> getTeacherStus(long user_id){
		return stDap.getTeacherStus(user_id);
	}
	public List<LStrMap<Object>> getTeacherNoPlan(long user_id){
		return stDap.getTeacherNoPlan(user_id);
	}
	
	public Page getTests(int pageNo,long user_id){
		return stDap.getTests(pageNo,user_id);
	}
	
	public List<LStrMap<Object>> getClasszz(int subject_id,int test_Id){
		return stDap.getClasszz(subject_id, test_Id);
	}
	
	public List<LStrMap<Object>> getClasses(long user_id,int subject_id,int grade_id,int test_Id){
		return stDap.getClasses(user_id, subject_id,grade_id,test_Id);
	}
	
	public int saveArrange(NNetWork_Arrange nnwa){
		int arrange_Id = seq_arrangeId.nextIntValue();
		nnwa.setArrange_Id(arrange_Id);
		stDap.saveArrange(nnwa);
		return arrange_Id;
	}
	
	
	public List<LStrMap<Object>> getplanTest(long user_id){
		return stDap.getplanTest(user_id);
	}
	
	public Page getAboutTest(int pageNo,long user_id){
		return stDap.getAboutTest(pageNo,user_id);
	}
	
	public Page getTestIng(int pageNo,long user_id){
		return stDap.getTestIng(pageNo, user_id);
	}
	public Page getResult(int pageNo,long user_id){
		return stDap.getResult(pageNo, user_id);
	}
	
	public NNetWork_Arrange getOldPlan(int TEST_ID,int SUBJECT_ID,int class_Id){
		return stDap.getOldPlan(TEST_ID, SUBJECT_ID, class_Id);
	}
	
	public int updatePlan(NNetWork_Arrange nna){
		return stDap.updatePlan(nna);
	}
	
	public void delPlan(NNetWork_Arrange mmwa){
		stDap.delPlan(mmwa);
	}
	
	public Map<String, List<LStrMap<Object>>> getUnmakedExamsAndClass(long userId) {
		List<LStrMap<Object>> list = stDap.getUnmarkedExamsAndClass(userId);
		Map<String, List<LStrMap<Object>>> result = new LinkedHashMap<String, List<LStrMap<Object>>>();
		for (int i = 0; i < list.size(); i++) {
			LStrMap<Object> tempMap = list.get(i);
			if (result.containsKey(tempMap.get("test_subject"))) {
				result.get(tempMap.get("test_subject").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(tempMap);
				result.put(tempMap.get("test_subject").toString(), tempList);
			}
		}
		return result;
	}
	
	public List<LStrMap<Object>> getEnterExamStudentsInfo(long classId, long testId, long subjectId) {
		return stDap.getEnterExamStudentsInfo(classId, testId, subjectId);
	}
	
	public LStrMap<Object> getCurrentInfo(int sortId, int testId) {
		List<LStrMap<Object>> list = stDap.getCurrentInfo(sortId, testId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	public LStrMap<Object> getAutoMarkFlag(int testId, int sortId) {
		List<LStrMap<Object>> list = stDap.getAutoMarkFlag(testId, sortId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	public void updateAutoMarkFlag(int testId, int sortId, int autoMarkFlag) {
		stDap.updateAutoMarkFlag(testId, sortId, autoMarkFlag);
	}
	
	/**
	 * 查询班级阅卷信息
	 * @param testId 考试编号
	 * @param subjectId 科目编号
	 * @param userId 教师用户编号
	 * @return 如果有阅卷信息就返回存有班级阅卷信息的LStrMap，否则返回null
	 */
	public LStrMap<Object> getMarkingInfo(int testId, int subjectId, long userId) {
		List<LStrMap<Object>> list = stDap.getMarkingInfo(testId, subjectId, userId);
		if (list.size() > 0) return list.get(0);
		return null;
	}
	
	
}
