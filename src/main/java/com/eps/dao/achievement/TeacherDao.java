package com.eps.dao.achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.Teacher;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class TeacherDao extends BaseDao {
	
	@Value("${teacher.load.userid}")
	private String loadTeacherByUserId;
	
	@Value("${teacher.load.teacherid}")
	private String loadTeacherByTeacherId;
	
	@Value("${teacher.authen.userid}")
	private String getAuthenInfoByUserId;
	
	
	public List<LStrMap<Object>> getAuthenInfo(long user_id){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", user_id);
		return this.find(getAuthenInfoByUserId, paramMap);
	}
	
	/**
	 * 根据用户id加载教师信息
	 * @param userId
	 * @return
	 */
	public Teacher load(long userId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", userId);
		List<Teacher> list = getNameParameTemplate().query(loadTeacherByUserId, paramMap, new TeacherRowMapper());
		if(list.size()==1) return list.get(0);
		return null;
	}
	/**
	 * 根据老师ID加载老师信息
	 * @param teacherId
	 * @return
	 */
	public Teacher load(int teacherId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("teacher_id", teacherId);
		List<Teacher> list = getNameParameTemplate().query(loadTeacherByTeacherId, paramMap, new TeacherRowMapper());
		if(list.size()==1) return list.get(0);
		return null;
	}
	
	/**
	 * 查找所有老师
	 * @return
	 */
	public List<Teacher> getTeachers(){
		return null;
	}
	class TeacherRowMapper implements RowMapper<Teacher>{
		public Teacher mapRow(ResultSet rs, int arg1) throws SQLException {
			Teacher teacher = new Teacher();
			teacher.setTeacherName(rs.getString("TEACHER_NAME"));
			teacher.setUserId(rs.getLong("USER_ID"));
			teacher.setTeacherId(rs.getInt("TEACHER_ID"));
			teacher.setSchoolId(rs.getInt("SCHOOL_ID"));
			return teacher;
		}
		
	}
}
