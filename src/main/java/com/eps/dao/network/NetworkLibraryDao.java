package com.eps.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.domain.EExam;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class NetworkLibraryDao extends BaseDao{

	@Value("${network.e_exam.getExamStatues}")
	private String getExamStatues;
	@Value("${network.e_exam.getExamsbyUp}")
	private String getExams;
	@Value("${network.e_exam.upExam}")
	private String updateExamByStatues;
	@Value("${network.e_exam.selectExamStatusOne}")
	private String selectExamStatusOne;
	@Value("${network.share.getschools}")
	private String getSchools;
	@Value("${network.school.byCode}")
	private String byCode;
	@Value("${network.e_exam.updateExamEStatue}")
	private String updateExamEStatue;
	
	/**
	 * 查询老师发布的页面
	 * @param user_id
	 * @return
	 */
	public Page getExamStatues(int pageNo,long user_id,String release_time){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", user_id);
		paramMap.put("release_time", release_time);
		return pageQuery(getExamStatues, pageNo, paramMap);
	}
	
	public EExam getExams(long exam_id){
		EExam eexam=null;
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("exam_id", exam_id);
		try {
			eexam = getNameParameTemplate().queryForObject(getExams,paramMap, new examRowMapper());
			return eexam;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	class examRowMapper implements RowMapper<EExam>{
		public EExam mapRow(ResultSet rs, int arg1) throws SQLException {
			EExam eExam=new EExam();
			eExam.setExamId(rs.getInt("exam_id"));
			eExam.setExamName(rs.getString("exam_name"));
			eExam.setYear(rs.getInt("year"));
			eExam.setSemester(rs.getInt("semester"));
			eExam.setSubjectNo(rs.getString("subject_no"));
			eExam.setExamType(rs.getInt("exam_type"));
			eExam.setbTopicNum(rs.getInt("b_topic_num"));
			eExam.setmTopicNum(rs.getInt("m_topic_num"));
			eExam.setTotal(rs.getInt("total"));
			eExam.setCareaor(rs.getString("careaor"));
			eExam.setCostTime(rs.getInt("cost_time"));
			eExam.setRemark(rs.getString("remark"));
			eExam.setqRandom(rs.getInt("q_random"));
			eExam.setoRandom(rs.getInt("o_random"));
			eExam.setbCodeType(rs.getInt("b_code_type"));
			eExam.setsCodeType(rs.getInt("s_code_type"));
			eExam.setoCodeType(rs.getInt("o_code_type"));
			eExam.setExamArea(rs.getString("exam_area"));
			eExam.setPassScore(rs.getInt("pass_score"));
			eExam.setReleaseStatus(rs.getInt("release_status"));
			eExam.setTestId(rs.getInt("test_id"));
			eExam.setTestId(rs.getInt("release_time"));
			eExam.setTestId(rs.getInt("e_status"));
			return eExam;
		}
	}
	
	public int updateExamByStatues(EExam eex){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("exam_id", eex.getExamId());
	    return this.excute(updateExamByStatues, paramMap);
	}
	
	/**
	 * 得到符合条件的他人分享的试卷
	 * @param pageNo
	 * @param releaseTime
	 * @param schoolId
	 * @param subjectId
	 * @param userId
	 * @return
	 */
	public Page selectExamStatusOne(int pageNo, String releaseTime, int schoolId, int subjectId, long userId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("release_time", releaseTime);
		paramMap.put("school_id", schoolId);
		paramMap.put("subject_id", subjectId);
		paramMap.put("user_id", userId);
		return pageQuery(selectExamStatusOne, pageNo, paramMap);
	}
	
	public List<LStrMap<Object>> byCode(int code){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("code", code);
		return this.find(byCode, params);
	}
	
	public int updateExamEStatue(EExam eex){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("exam_id", eex.getExamId());
		return this.excute(updateExamEStatue, paramMap);
	}

	//根据地区获得学校
	public List<LStrMap<Object>> getSchoolsByArea(String areaCode) {
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("resp_dist", areaCode);
		return this.find(getSchools, paramMap);
	}
	
}
