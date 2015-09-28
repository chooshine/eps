package com.eps.dao.achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.Student;
import com.eps.utils.UStrMap;

@Repository
public class StudentDao extends BaseDao {
	
	@Value("${student.query.class}")
	private String getStudentByClass;
	
	@Value("${student.load.userid}")
	private String loadByUserId;
	
	@Value("${student.load.studentid}")
	private String loadByStudentId;
	
	@Value("${student.query.phone}")
	private String queryStudentByGuardianPhone;
	/**
	 * 查找某一班级的学生
	 * @param classId
	 * @return
	 */
	public List<Student> getStudentByClass(int classId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("class_id", classId);
		return getNameParameTemplate().query(getStudentByClass, paramMap, new StudentRowMapper());
	}
	
	public Student load(long userId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", userId);
		return getNameParameTemplate().queryForObject(loadByUserId, paramMap, new StudentRowMapper());
	}
	
	public Student load(int studentId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("student_id", studentId);
		return getNameParameTemplate().queryForObject(loadByStudentId, paramMap, new StudentRowMapper());
	}
	public List<Student> queryStudentByGuardianPhone(String phone){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("phone", phone);
		return getNameParameTemplate().query(queryStudentByGuardianPhone, paramMap,new StudentRowMapper());
	}
	class StudentRowMapper implements RowMapper<Student>{
		public Student mapRow(ResultSet rs, int arg1) throws SQLException {
			Student student = new Student();
			student.setUserId(rs.getLong("USER_ID"));
			student.setStudentId(rs.getInt("STUDENT_ID"));
			student.setStudentNo(rs.getString("STUDENT_NO"));
			student.setStudentName(rs.getString("STUDENT_NAME"));
			student.setClassId(rs.getInt("CLASS_ID"));
			student.setSeatNo(rs.getString("SEAT_NO"));
			student.setGuardian(rs.getString("GUARDIAN"));
			student.setGuardianPhone(rs.getString("GUARDIAN_PHONE"));
			return student;
		}
		
	}
}
