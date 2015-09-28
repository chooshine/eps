package com.eps.dao.esextends;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.CTeacher;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class TeacherAuthenDao extends BaseDao {
	
	@Value("${authen.save.teacher}")
	private String saveTeacher;
	
	@Value("${authen.update.teacher}")
	private String updateTeacher;
	
	@Value("${authen.update.photo.userId}")
	private String updateUserByUserid;
	
	@Value("${authen.get.authenstatus.userid}")
	private String getAuthenStatus; 
	
	@Value("${authen.delete.teacher}")
	private String deleteTeacher;
	
	@Value("${authen.get.authenInfo.bystatus}")
	private String getAuthenInfoByStatus;
	
	/**
	 * 更新某个老师的认证状态
	 * @param map
	 */
	public void updateAuthenStatus(UStrMap<Object> map){
		this.excute(updateTeacher, map);
	}
	
	/**
	 * 根据认证状态得到教师认证信息
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getAuthenInfoByStatus(UStrMap<Object> map){
		return this.find(getAuthenInfoByStatus, map);
	}
	
	/**
	 * 删除某个教师的信息
	 * @param map
	 * @return
	 */
	public int deleteTeacher(UStrMap<Object> map){
		return this.excute(deleteTeacher, map);
	}
	
	/**
	 * 保存教师表
	 * @param cTeacher
	 * @return
	 */
	public int saveTeacherDao(CTeacher cTeacher){
		return this.excute(saveTeacher, cTeacher.toMap());
	}
	
	/**
	 * 更新教师表
	 * @param cTeacher
	 * @return
	 */
	public int updateTeacherDao(CTeacher cTeacher){
		return this.excute(updateTeacher, cTeacher.toMap());
	}
	
	/**
	 * 更新头像s_user表
	 * @param map
	 * @return
	 */
	public int updateUserByUseridDao(UStrMap<Object> map){
		return this.excute(updateUserByUserid, map);
	}
	
	/**
	 * 获得最后一次认证状态
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getAuthenStatusDao(UStrMap<Object> map){
		return this.find(getAuthenStatus, map);
	}
}
