package com.eps.service.formula;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.formula.FormulaDao;
import com.eps.domain.Formula;
import com.eps.utils.LStrMap;

@Service
public class FormulaService {
	
	@Autowired
	private FormulaDao dao;
	
	/**
	 * 保存公式
	 * @param f
	 * @return
	 */
	public boolean saveFormula(Formula f){
		return dao.insertFormula(f) == 1;
	}
	public boolean updateFormulaCount(long userId, String latex){
		return dao.updateFormulaCount(userId, latex) == 1;
	}
	/**
	 * 根据用户和公式编码查找公式
	 * @param userId
	 * @param latex
	 * @return
	 */
	public Formula getFormula(long userId, String latex){
		return dao.getFormula(userId, latex);
	}
	
	public Page<Formula> queryFormulaByUser(long userId, int pageNo){
		return queryFormulaByUser(userId, pageNo,Page.DEFAULT_PAGE_SIZE);
	}
	public Page<Formula> queryFormulaByUser(long userId, int pageNo, int pageSize){
		return dao.getFormulasByUser(userId, pageNo, pageSize);
	}
	
	public Page<Formula> queryAll(int pageNo){
		return dao.getAll(pageNo, Page.DEFAULT_PAGE_SIZE);
	}
	public Page<Formula> queryFormulaByLatex(String latex, int pageNo){
		return queryFormulaByLatex(latex, pageNo,Page.DEFAULT_PAGE_SIZE);
	}
	public Page<Formula> queryFormulaByLatex(String latex, int pageNo, int pageSize){
		return dao.getFormulasByLatex(latex, pageNo, pageSize);
	}
	
	/**
	 * 返回用户最近使用的公式
	 * @param userId 用户编号
	 * @return 用户最近使用的公式
	 */
	public List<LStrMap<Object>> getFormulas(long userId) {
		return dao.getFormulas(userId);
	}
	
	/**
	 * 更新指定公式的使用次数
	 * @param userId 公式创建人用户编号
	 * @param id 公式编号
	 */
	public void updateFormulaCount(long userId, int id) {
		dao.updateFormulaCount(userId, id);
	}
}
