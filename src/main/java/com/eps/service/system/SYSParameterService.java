package com.eps.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.system.SYSParameterDao;
import com.eps.domain.SParameter;
import com.eps.utils.LStrMap;

@Service
public class SYSParameterService {
	
	
	public static LStrMap<SParameter> PARAMETERS = null;
	
	@Autowired
	private SYSParameterDao dao;
	
//	static{
//		PARAMETERS = LStrMap.newInstance();
//		List<SParameter> params = dao.getAllSystemParm();
//		for (SParameter sParameter : params) {
//			PARAMETERS.put(sParameter.getName(), sParameter);
//		}
//	}
	
	public SParameter getSystemParameter(String name){
		if(PARAMETERS==null){
			PARAMETERS = LStrMap.newInstance();
			List<SParameter> params = dao.getAllSystemParm();
			for (SParameter sParameter : params) {
				PARAMETERS.put(sParameter.getName(), sParameter);
			}
		}
		return PARAMETERS.get(name);
	}
}
