package com.eps.service.esextends;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.achievement.TeacherDao;
import com.eps.dao.esextends.TeacherAuthenDao;
import com.eps.domain.CTeacher;
import com.eps.utils.FileUploader;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;

@Service
public class TeacherAuthenService {

	@Autowired
	private TeacherAuthenDao tad;
	
	@Autowired
	private TeacherDao tDao;
	
	/**
	 * 修改某老师的认证状态
	 * @param teacher_id
	 */
	public void updateAuthenStatus(long teacher_id, String status){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("teacher_id", teacher_id);
		map.put("authen_status", Integer.parseInt(status));
		tad.updateAuthenStatus(map);
	}
	
	/**
	 * 根据认证状态得到老师信息
	 * @param status
	 * @return
	 */
	public List<LStrMap<Object>> getAuthenInfoByStatus(int status){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("status",status);
		return tad.getAuthenInfoByStatus(map);
	}
	
	/**
	 * 删除某条老师信息
	 * @param userId
	 */
	public void deleteTeacher(long userId) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", userId);
		tad.deleteTeacher(map);
	}
	
	/**
	 * 得到用户的认证信息
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getAuthenInfo(long userId) {
		return tDao.getAuthenInfo(userId);
	}
	
	/**
	 * 保存教师表
	 * @param cTeacher
	 * @return
	 */
	public boolean saveTeacherService(CTeacher cTeacher){
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		cTeacher.setAuthenTime(dateformat.format(date));
		cTeacher.setAuthenStatus(0);
		return tad.saveTeacherDao(cTeacher)==1;
	}
	
	/**
	 * 更新用户头像
	 * @param userId
	 * @return
	 */
	public boolean updateUserByUseridService(long userId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		return tad.updateUserByUseridDao(map)==1;
	}
	
	/**
	 * 获得最后一次认证状态
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getAuthenStatusService(long userId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		return tad.getAuthenStatusDao(map);
	}
	
	/**
	 * 上传头像
	 * @param userId
	 * @return
	 */
	public String upPhotoService(long userId,HttpServletRequest request,HttpServletResponse response){
		FileUploader photoUpload=new FileUploader();
		//设置保存路径
		photoUpload.setSavePath("/images/headImage");
		//设置最大上传大小
		photoUpload.setFileSizeMax(1024000);
		photoUpload.setSizeMax(1024000);
		//设置文件格式
		Set<String> set=new HashSet<String>();
		set.add(".jpg");
		set.add(".png");
		set.add(".gif");
		set.add(".tiff");
		set.add(".bmp");
		set.add(".jpeg");
		set.add(".tif");
		set.add(".swf");
		set.add(".dib");
		photoUpload.setAcceptTypes(set);
		//设置文件名
		FileNameGenerator fileNameGenerator=new IdFileNameGenerator(userId);
		photoUpload.setFileNameGenerator(fileNameGenerator);
		Result resut=photoUpload.upload(request, response);
		Set<String> names=photoUpload.getFileNames();
		Iterator iterator=names.iterator();
		String str="";
		while (iterator.hasNext()) {
			str=iterator.next().toString();
		}
		String errorInfo="上传失败！";
		if("SUCCESS".equals(resut.toString())){
			UStrMap<Object> map=UStrMap.newInstance();
			map.put("USER_ID", userId);
			map.put("PHOTO","images/headImage/"+str);
			tad.updateUserByUseridDao(map);
			errorInfo="上传成功！";
		}else if("FILE_SIZE_EXCEEDED".equals(resut.toString())){
			errorInfo="上传失败，单个文件大小超过限制";
		}
		return errorInfo;
	}
	
	
	/**
	 * 上传证书
	 * @param userId
	 * @return
	 */
	public Map<String,String> upCertificateService(long userId,HttpServletRequest request,HttpServletResponse response){
		CTeacher cTeacher=new CTeacher();
		FileUploader photoUpload=new FileUploader();
		//设置保存路径
		photoUpload.setSavePath("/images/certificate");
		//设置最大上传大小
		photoUpload.setFileSizeMax(1024000);
		photoUpload.setSizeMax(3720000);
		//设置文件格式
		Set<String> set=new HashSet<String>();
		set.add(".jpg");
		set.add(".png");
		set.add(".gif");
		set.add(".tiff");
		set.add(".bmp");
		set.add(".jpeg");
		set.add(".tif");
		set.add(".swf");
		set.add(".dib");
		photoUpload.setAcceptTypes(set);
		//设置文件名
		FileNameGenerator fileNameGenerator=new CertificateNameGenerator(userId);
		photoUpload.setFileNameGenerator(fileNameGenerator);
		Result resut=photoUpload.upload(request, response);
		Set<String> names=photoUpload.getFileNames();
		Iterator iterator=names.iterator();
		StringBuffer sbf=new StringBuffer();
		while (iterator.hasNext()) {
			sbf.append("images/certificate/"+iterator.next().toString()+",");
		}
		String errorInfo="上传失败！";
		if("SUCCESS".equals(resut.toString())){
//			cTeacher.setTeacherId(teacherId);
//			cTeacher.setAuthenStatus(2);
//			cTeacher.setLicense(new String(sbf));
//			tad.saveTeacherDao(cTeacher);
			errorInfo="上传成功！";
		}else if("FILE_SIZE_EXCEEDED".equals(resut.toString())){
			errorInfo="上传失败，单个或多个文件大小超过限制";
		}
		Map<String,String> map=new HashMap<String, String>();
		map.put("errorInfo", errorInfo);
		map.put("path", new String(sbf));
		return map;
	}
}
