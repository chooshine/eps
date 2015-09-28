package com.eps.service.corpus;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eps.cons.CommonConstant;
import com.eps.dao.corpus.KeyWordDao;
import com.eps.domain.KeyWord;

@Service
public class KeyWordService {
	@Autowired
	public KeyWordDao dao;
	
	@Value("${search.cache.dir}")
	private String path;
	
	public KeyWord getKeyWord(String key, String language, String repType){
		return dao.getKeyWord(key, language, repType);
	}
	public KeyWord getKeyWord(HttpServletRequest request){
		Object obj = request.getSession().getAttribute(CommonConstant.KEYWORD);
		if(obj!=null) return (KeyWord)obj;
		return null;
	}
	public void saveKeyWord(HttpServletRequest request,KeyWord kw){
//		dao.saveKeyWord(kw);
		if(getKeyWord(request)!=null){
			deleteKeyWord(getKeyWord(request));
		}
		request.getSession().setAttribute(CommonConstant.KEYWORD, kw);
	}
	public List<KeyWord> getAllKeyWord(){
		return dao.findKeyWord();
	}
	public void updateKeyWord(KeyWord kw){
		dao.updateKeyWord(kw);
	}
	
	public void deleteKeyWord(KeyWord kw){
		File file = new File(path + File.separator + kw.getFileName());
		if(file.exists()){
			file.delete();
		}
	}
}
