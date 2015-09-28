package com.eps.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.eps.dao.Page;
import com.eps.domain.Formula;
import com.eps.service.formula.FormulaService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/applicationContext.xml"})
public class FormulaServiceTest {
	@Autowired
	private FormulaService service;
	
	//@Test
	public void case1(){
		Formula f = new Formula();
		f.setUserId(1);
		f.setLatex("\\dd");
		f.setUrl("/image/formula/aaxxx.png");
		f.setSize(68);
		f.setWidth(25);
		f.setHeight(25);
		f.setCount(0);
		f.setCreateTime(new Date());
		Assert.isTrue(service.saveFormula(f));
	}
//	@Test
	public void case2(){
		Assert.isTrue(service.updateFormulaCount(1, "\\dd"));
	}
	
//	@Test
	public void case3(){
		Formula f = service.getFormula(1, "\\dd");
		Assert.notNull(f);
	}
//	@Test
	public void case4(){
		Page<Formula> pp = service.queryAll(1);
		Assert.notEmpty(pp.getData());
		
		pp = service.queryFormulaByLatex("\\dd", 1);
		Assert.notEmpty(pp.getData());
		
		pp = service.queryFormulaByUser(1, 1);
		Assert.notEmpty(pp.getData());
	}
}
