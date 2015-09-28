package com.eps.dao.achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.CClass;
import com.eps.utils.UStrMap;

@Repository
public class ClassDao extends BaseDao {
	
	@Value("${class.query.teacherid}")
	private String queryClassByTeacher;
	/**
	 * 查找某一教师所带班级
	 * @param teacherId
	 * @return
	 */
	public List<CClass> getClassByTeacher(int teacherId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("teacher_id", teacherId);
		return getNameParameTemplate().query(queryClassByTeacher, paramMap, new RowMapper<CClass>() {
			public CClass mapRow(ResultSet rs, int arg1) throws SQLException {
				CClass clazz = new CClass();
				clazz.setClassId(rs.getInt("CLASS_ID"));
				clazz.setClassName(rs.getString("CLASS_NAME"));
				clazz.setClassType(rs.getInt("CLASS_TYPE"));
				clazz.setGradeId(rs.getInt("GRADE_ID"));
				clazz.setTeacherId(rs.getInt("TEACHER_ID"));
				clazz.setStartTime(rs.getDate("START_TIME"));
				clazz.setExistFlag(rs.getInt("EXIST_FLAG"));
				return clazz;
			}
		});
	}
	
	public CClass getClassInfo(int classId){
		
		return null;
	}
}
