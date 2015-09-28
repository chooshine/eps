package com.eps.service.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.type.MapLikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.network.NetWorkContinueDao;
import com.eps.domain.EExam;
import com.eps.domain.EOption;
import com.eps.domain.EQuesType;
import com.eps.domain.EQuestion;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Service
public class NetWorkContinueService {
	
	public Page getUserFromExamByReleasestatus(int pageNo,long user_id){
		return nwcDao.getUserFromExamByReleasestatus(pageNo,user_id);
	}
	
	public List<LStrMap<Object>> getExamQuestionByQuesId(long EXAM_ID){
		return nwcDao.getExamQuestionByQuesId(EXAM_ID);
	}
	
	public List<LStrMap<Object>> getexams(long EXAM_ID){
		return nwcDao.getexams(EXAM_ID);
	}
	
	public int updataToDel(EExam eex){
		return nwcDao.updataToDel(eex);
	}
	public int updateExamTest(EExam eex){
		return nwcDao.updateExamTest(eex);
	}
	
	public LStrMap<List<LStrMap<Object>>> getUserFromExamByReleasestatusIsOne(int pageNo,long user_id){
		Page page = nwcDao.getUserFromExamByReleasestatusIsOne(pageNo,user_id);
		List<LStrMap<Object>> list = page.getData();
		LStrMap<List<LStrMap<Object>>> map = LStrMap.newInstance();
		for (int i = 0; i < list.size(); i++) {
			LStrMap<Object> tempMap = list.get(i);
			if (map.containsKey(tempMap.get("exam_id").toString())) {
				map.get(tempMap.get("exam_id").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(tempMap);
				map.put(tempMap.get("exam_id").toString(), tempList);
			}
		}
		
		return map;
	}
	

	
	@Autowired
	NetWorkContinueDao nwcDao;

}
