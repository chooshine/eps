package com.eps.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.system.SYSCodeDao;
import com.eps.domain.SCode;
import com.eps.utils.UStrMap;

@Service
public class SCodeService {
	
	@Autowired
	private SYSCodeDao dao;
	
	private static UStrMap<List<SCode>> CODES;
	
	public List<SCode> getCodeByCodeCate(String codeCate){
		return dao.getCodeByCodeCate(codeCate);
	}
	
	public List<SCode> getCodes(String codeCate){
		if(CODES==null){
			CODES = dao.getAllCode();
		}
		return CODES.get(codeCate.toUpperCase());
	}
}
