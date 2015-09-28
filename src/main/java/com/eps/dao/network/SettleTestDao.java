package com.eps.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.domain.NNetWork_Arrange;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class SettleTestDao extends BaseDao{
	
	@Value("${settle.test.getTests}")
	private String getTests;//待安排
	@Value("${settle.class.getClasszz}")
	private String getClasszz;//待安排的安排考试
	@Value("${settle.currentinfo.sortid.testid}")
	private String getCurrentInfo;
	@Value("${settle.class.getClass}")
	private String getClass;
	@Value("${settle.save.arrange}")
	private String saveArrange;
	@Value("${settle.get.planTest}")
	private String getplanTest;//已安排的考试
	@Value("${settle.about.tests}")
	private String about;//即将考试
	@Value("${settle.testing.get}")
	private String testing;
	@Value("${settle.arrange.getResult}")
	private String result;
	@Value("${settle.get.oldPlan}")
	private String oldplan;
	@Value("${settle.update.arrange}")
	private String updatePlan;
	@Value("${settle.delete.narrange}")
	private String delPlan;
	@Value("${settle.update.automarkflag}")
	private String updateAutoMarkFlag;
	@Value("${settle.get.automarkflag.testid.sortid}")
	private String getAutoMarkFlagByTestIdAndSortId;
	@Value("${network.school.stu}")
	private String stu;
	@Value("${network.school.stus}")
	private String stus;
	@Value("${network.school.noplan}")
	private String noplan;
	
	@Value("${gradepaper.unmakedpaper.info.userid}")
	private String getUnmarkedPapersInfoByUserId;
	@Value("${gradepaper.enterexam.studentsinfo.classid.subjectid}")
	private String getAllEnterExamStudents;
	@Value("${gradepaper.get.markinginfo.of.oneclass}")
	private String getMarkingInfo;
	
	public List<LStrMap<Object>> getTeacherStu(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(stu, params);
	}
	public List<LStrMap<Object>> getTeacherStus(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(stus, params);
	}
	public List<LStrMap<Object>> getTeacherNoPlan(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(noplan, params);
	}
	
	/**
	 * 通过用户编号查询老师所教的科目下的考试
	 ** @param user_id
	 * @return
	 */
	public Page getTests(int pageNo,long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(getTests,pageNo,params);
	}

	/**
	 * 通过用户编号查询老师所教的所有班级
	 ** @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getClasszz(int subject_id ,int test_Id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("subject_id", subject_id);
		params.put("test_Id", test_Id);
		return find(getClasszz, params);
	}
	public List<LStrMap<Object>> getClasses(long user_id,int subject_id,int grade_id,int test_Id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		params.put("subject_id", subject_id);
		params.put("grade_id", grade_id);
		params.put("test_Id", test_Id);
		return find(getClass, params);
	}
	
	/**
	 * 插入安排考场信息
	 ** @param user_id
	 * @return
	 */
	public int saveArrange(NNetWork_Arrange nnwa){
		return this.excute(saveArrange, nnwa.toMap());
	}
	
	/**
	 * 查询已安排的班级
	 ** @param user_id
	 * @
	 */
	public List<LStrMap<Object>> getplanTest(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getplanTest, params);
	}
	
	/**
	 * 查询即将考试的信息
	 ** @param user_id
	 * @
	 */
	public Page getAboutTest(int pageNo,long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(about, pageNo,params);
	}
	
	/**
	 * 查询正在考试
	 ** @param user_id
	 * @
	 */
	public Page getTestIng(int pageNo,long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(testing, pageNo,params);
	}
	
	/**
	 * 查看考试结果
	 ** @param user_id
	 * @
	 */
	public Page getResult(int pageNo,long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(result, pageNo,params);
	}
	
	/**
	 * 查看是否已经安排
	 ** @param user_id
	 * @
	 */
	public NNetWork_Arrange getOldPlan(int TEST_ID,int SUBJECT_ID,int class_Id){
		NNetWork_Arrange nnwa=null;
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("TEST_ID", TEST_ID);
		params.put("SUBJECT_ID", SUBJECT_ID);
		params.put("class_Id", class_Id);
		try {
			nnwa=getNameParameTemplate().queryForObject(oldplan,params,new narrageRowMapper());
			return nnwa;
		} catch (Exception e) {
			return null;
		}
		
		
	}
	
	class narrageRowMapper implements RowMapper<NNetWork_Arrange>{
		public NNetWork_Arrange mapRow(ResultSet rs, int arg1) throws SQLException {
			NNetWork_Arrange nnwa=new NNetWork_Arrange();
			nnwa.setArrange_Id(rs.getInt("arrange_Id"));
			nnwa.setDate(rs.getString("date"));
			nnwa.setTeacher_Name(rs.getString("teacher_Name"));
			nnwa.setGrade_Id(rs.getInt("grade_Id"));
			nnwa.setClass_Id(rs.getInt("class_Id"));
			nnwa.setTest_Id(rs.getInt("test_Id"));
			nnwa.setNetWork_Addr(rs.getString("netWork_Addr"));
			nnwa.setNetWork_Status(rs.getInt("netWork_Status"));
			nnwa.setUser_Id(rs.getInt("user_Id"));
			nnwa.setEndDate(rs.getString("endDate"));
			nnwa.setSubject_Id(rs.getInt("subject_Id"));
			return nnwa;
		}
	}
	
	/**
	 * 查更新安排
	 ** @param 
	 * @
	 */
	public int updatePlan(NNetWork_Arrange nna){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("DATE", nna.getDate());
		paramMap.put("ENDDATE", nna.getEndDate());
		paramMap.put("NETWORK_ADDR", nna.getNetWork_Addr());
		paramMap.put("TEACHER_NAME", nna.getTeacher_Name());
		paramMap.put("TEST_ID",nna.getTest_Id());
		paramMap.put("SUBJECT_ID",nna.getSubject_Id());
		paramMap.put("CLASS_ID", nna.getClass_Id());
	    return this.excute(updatePlan, paramMap);
	}
	
	
	/**
	 * 删除安排
	 ** @param 
	 * @
	 */
	public void delPlan(NNetWork_Arrange mmwa){
		this.excute(delPlan, mmwa.toMap());
	}
	
	/**
	 * 得到老师所教的班级、所教科目的所有未阅的试卷及班级信息
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getUnmarkedExamsAndClass(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		return this.find(getUnmarkedPapersInfoByUserId, map);
	}
	
	/**
	 * 得到某班参加了某门科目某次考试的学生的信息
	 * @param classId
	 * @param testId
	 * @param subjectId
	 * @return
	 */
	public List<LStrMap<Object>> getEnterExamStudentsInfo(long classId, long testId, long subjectId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("class_id", classId);
		map.put("test_id", testId);
		map.put("subject_id", subjectId);
		return find(getAllEnterExamStudents, map);
	}
	
	/**
	 * 得到当前选中的试卷及考试的信息
	 * @param sortId
	 * @param testId
	 * @return
	 */
	public List<LStrMap<Object>> getCurrentInfo(int sortId, int testId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("sort_id", sortId);
		map.put("test_id", testId);
		return this.find(getCurrentInfo, map);
	}
	
	//根据testId和sortId得到试卷是否可以自动阅卷的标记
	public List<LStrMap<Object>> getAutoMarkFlag(int testId, int sortId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("sort_id", sortId);
		map.put("test_id", testId);
		return this.find(getAutoMarkFlagByTestIdAndSortId, map);
	}
	//更新自动阅卷字段
	public int updateAutoMarkFlag(int testId, int sortId, int autoMarkFlag) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_id", testId);
		map.put("sort_Id", sortId);
		map.put("automark_flag", autoMarkFlag);
		return this.excute(updateAutoMarkFlag, map);
	}
	
	/**
	 * 查询班级阅卷信息
	 * @param testId 考试编号
	 * @param subjectId 科目编号
	 * @param userId 教师用户编号
	 * @return 如果有阅卷信息就返回存有班级阅卷信息的List<LStrMap<Object>>，否则返回null
	 */
	public List<LStrMap<Object>> getMarkingInfo(int testId, int subjectId, long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_id", testId);
		map.put("subject_id", subjectId);
		map.put("user_id", userId);
		return this.find(getMarkingInfo, map);
	}
	
	
}
