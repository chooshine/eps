package com.eps.dao.formula;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.domain.Formula;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class FormulaDao extends BaseDao{
	
	@Value("${formula.get.latex}")
	private String getFormulasByLatex;
	@Value("${formula.get.userid}")
	private String getFormulasByUser;
	@Value("${formula.insert}")
	private String insertFormula;
	@Value("${formula.get.all}")
	private String getAll;
	@Value("${formula.get.userid.latex}")
	private String getFormula;
	@Value("${formula.update.count}") private String updateFormulaCount;
	@Value("${formula.get.formulas}") private String getFormulas;
	@Value("${formula.update.count.by.id}") private String updateFormulaCountById;
	public List<Formula> getFormulasByLatex(String latex){
		UStrMap<String> params = UStrMap.newInstance();
		params.put("latex", latex);
		this.getNameParameTemplate().query(getFormulasByLatex, params, new Formula());
		return null;
	}
	public Page<Formula> getFormulasByLatex(String latex,int pageNo, int pageSize){
		UStrMap<String> params = UStrMap.newInstance();
		params.put("latex", latex);
		return this.pageQuery(getFormulasByLatex, pageNo, pageSize, params, new Formula());
	}
	public int insertFormula(Formula f){
		int s = this.excute(insertFormula, f.toMap());
		return s;
	}
	
	public int updateFormulaCount(long userId, String latex){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("userId", userId);
		params.put("latex", latex);
		return this.excute(updateFormulaCount, params);
	}
//	public int updateFormula(Formula f){
//		int s = this.excute(updateFormula, f.toMap());
//		return s;
//	}
	
	public List<Formula> getFormulasByUser(long userId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("userId", userId);
		return this.getNameParameTemplate().query(getFormulasByUser, params, new Formula());
	}
	
	public Page<Formula> getFormulasByUser(long userId, int pageNo, int pageSize){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("userId", userId);
		return this.pageQuery(getFormulasByUser, pageNo, pageSize, params, new Formula());
	}
	
	public Page<Formula> getAll(int pageNo, int pageSize){
		return this.pageQuery(getAll, pageNo, pageSize, null, new Formula());
	}
	
	public Formula getFormula(long userId, String latex){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("userId", userId);
		params.put("latex", latex);
		List<Formula> list =  this.getNameParameTemplate().query(getFormula, params, new Formula());
		return list.size()>0 ? list.get(0):null;
	}
	
	/**
	 * 返回用户最近使用的公式
	 * @param userId 用户编号
	 * @return 用户最近使用的公式
	 */
	public List<LStrMap<Object>> getFormulas(long userId) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		return this.find(getFormulas, params);
	}
	
	/**
	 * 更新指定公式的使用次数
	 * @param userId 公式创建人用户编号
	 * @param id 公式编号
	 */
	public void updateFormulaCount(long userId, int id) {
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		params.put("id", id);
		this.excute(updateFormulaCountById, params);
	}
}
