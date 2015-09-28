package com.eps.android.service.user;

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

import com.eps.android.dao.user.AUserDao;
import com.eps.android.service.homework.NoteNameGenerator;
import com.eps.utils.FileUploader;
import com.eps.utils.LStrMap;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;

@Service
public class AUserService {

	@Autowired private AUserDao aUserDao;

	public LStrMap<Object> getUserInfo(int userId) {
		List<LStrMap<Object>> list = aUserDao.getUserInfo(userId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, String> uploadAdviceImg(String imgPath, HttpServletRequest request, HttpServletResponse response) {
		FileUploader photoUpload=new FileUploader();
		//设置保存路径
		photoUpload.setSavePath(imgPath);
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
		FileNameGenerator fileNameGenerator=new NoteNameGenerator();
		photoUpload.setFileNameGenerator(fileNameGenerator);
		Result resut=photoUpload.upload(request, response);
		Set<String> names=photoUpload.getFileNames();
		Iterator iterator=names.iterator();
		StringBuffer sbf=new StringBuffer();
		while (iterator.hasNext()) {
			sbf.append(imgPath+iterator.next().toString()+",");
		}
		String errorInfo="上传失败";
		if("SUCCESS".equals(resut.toString())){
			errorInfo="上传成功";
		}else if("FILE_SIZE_EXCEEDED".equals(resut.toString())){
			errorInfo="上传失败，文件大小超过限制";
		}
		Map<String,String> map=new HashMap<String, String>();
		map.put("errorInfo", errorInfo);
		map.put("path", new String(sbf));
		return map;
	}
}
