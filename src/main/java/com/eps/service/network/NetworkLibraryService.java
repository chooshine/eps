package com.eps.service.network;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.network.NetworkLibraryDao;
import com.eps.domain.EExam;
import com.eps.utils.LStrMap;

@Service
public class NetworkLibraryService {

	@Autowired
	private NetworkLibraryDao mwlDao;
	
	public Page getExamStatues(int pageNo,long user_id,String RELEASE_TIME){
		return mwlDao.getExamStatues(pageNo,user_id, RELEASE_TIME);
	}
	
	public  EExam getExams(long exam_id){
		return mwlDao.getExams(exam_id);
	}
	
	public int updateExamByStatues(EExam eex){
		return mwlDao.updateExamByStatues(eex);
	}
	
	public Page selectExamStatusOne(int pageNo, String releaseTime, int schoolId, int subjectId, long userId){
		return mwlDao.selectExamStatusOne(pageNo,releaseTime, schoolId, subjectId, userId);
	}
	
	public List<LStrMap<Object>> byCode(int code){
		return mwlDao.byCode(code);
	}
	
	public int updateExamEStatue(EExam eex){
		return mwlDao.updateExamEStatue(eex);
	}

	public List<LStrMap<Object>> getSchoolsByArea(String areaCode) {
		return mwlDao.getSchoolsByArea(areaCode);
	}
	
	
	
}
