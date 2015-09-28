package com.eps.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

import com.eps.cons.CommonConstant;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*@Autowired
	private SimpleJdbcDaoSupport simpleJdbcDao;*/
	
	@Autowired
	private NamedParameterJdbcTemplate nameParameTemplate;
	public BaseDao(){}

	public void setAutoCommit(boolean isauto) throws SQLException{
		jdbcTemplate.getDataSource().getConnection().setAutoCommit(isauto);
	}
	
	/**
	 * 手动提交数据
	 * @throws SQLException
	 */
	public void commit() throws SQLException{
		if(!jdbcTemplate.getDataSource().getConnection().getAutoCommit())
			jdbcTemplate.getDataSource().getConnection().commit();
		else{
			jdbcTemplate.getDataSource().getConnection().setAutoCommit(false);
			jdbcTemplate.getDataSource().getConnection().commit();
		}
	}
	public List<LStrMap<Object>> find(String sql){
		return getJdbcTemplate().query(sql,new LStrMapRowMapper());
	}
	
	public int excute(String sql, UStrMap args){
		return getNameParameTemplate().update(sql, args);
	}
	
	public List<LStrMap<Object>> find(String sql, UStrMap args){
		return getNameParameTemplate().query(sql,args,new LStrMapRowMapper());
	}

	/**
	 * 返回单条记录，如查询有多条记录将抛出IncorrectResultSizeDataAccessException异常
	 * @param sql
	 * @param args
	 * @return
	 */
	public LStrMap<Object> readRecode(String sql, UStrMap args) throws DataAccessException{
		LStrMap<Object> result = LStrMap.newInstance();
		result = getNameParameTemplate().queryForObject(sql, args, new LStrMapRowMapper());
		return result;
	}
	/**
	 * 分页查询
	 * @param sql 查询语句
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @param args 参数列表
	 * @return
	 * @throws Exception
	 */
	public Page pageQuery(String sql, int pageNo,  int pageSize, UStrMap args){
		Assert.hasText(sql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		int totalCount = getNameParameTemplate().queryForInt("select count(*) totalCount from ("+ removeOrders(sql) + ") tt", args);
		//如无记录，返回空页
		if(totalCount<1) return new Page();
		//获取当前页第一条记录的索引
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		
		List<LStrMap<Object>> data = getNameParameTemplate().query("select * from (" + sql + ")tt limit "+startIndex+", "+pageSize+" ", args, new LStrMapRowMapper());
		
		return new Page(startIndex,totalCount, pageSize, data);
	}
	public <T> Page<T> pageQuery(String sql, int pageNo, int pageSize, UStrMap args, RowMapper<T> mapper){
		Assert.hasText(sql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		int totalCount = getNameParameTemplate().queryForInt("select count(*) totalCount from ("+ removeOrders(sql) + ") tt", args);
		//如无记录，返回空页
		if(totalCount<1) return new Page();
		//获取当前页第一条记录的索引
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<T> list = getNameParameTemplate().query("select * from (" + sql + ")tt limit "+startIndex+", "+pageSize+" ", args, mapper);
		return new Page(startIndex,totalCount,pageSize,list);
	}
	public Page pageQuery(String sql, int pageNo, UStrMap args){
		return pageQuery(sql,pageNo, CommonConstant.PAGE_SIZE, args);
	}
	
	public int[] batchUpdate(String sql, UStrMap<Object>[] args){
		return getNameParameTemplate().batchUpdate(sql, args);
	}
	public int [] batchUpdate(String sql, List<UStrMap<Object>> maps){
		SqlParameterSource[] batchArgs = new SqlParameterSource[maps.size()];
		int i=0;
		for(UStrMap<Object> map:maps){
			batchArgs[i] = new MapSqlParameterSource(map);
			i++;
		}
		return getNameParameTemplate().batchUpdate(sql, batchArgs);
	}
	public int[] batchUpdate(String[] sqls){
		return getJdbcTemplate().batchUpdate(sqls);
	}
	/**
	 * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String sql) {
		Assert.hasText(sql);
		int beginPos = sql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " sql : " + sql + " must has a keyword 'from'");
		return sql.substring(beginPos);
	}
	
	/**
	 * 去除sql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 执行过程
	 * @param callStr 过程语句  
	 * @param parameters 参数 {@link ProcedureParameter}
	 * @return
	 */
	public Map<String,Object> call(String callStr,final Set<ProcedureParameter> parameters){
		Map<String,Object> r = (Map<String,Object>)getJdbcTemplate().execute(callStr, new CallableStatementCallback() {
			public Object doInCallableStatement(
					CallableStatement cs)
					throws SQLException, DataAccessException {
				List<Integer> outIndex = new ArrayList<Integer>(); 
				Map<String, Object> result = new HashMap<String, Object>();
				for (Iterator iterator = parameters.iterator(); iterator
						.hasNext();) {
					ProcedureParameter pp = (ProcedureParameter) iterator
							.next();
					
					switch (pp.getInout()) {
					case IN:
						cs.setObject(pp.getIndex(), pp.getValue());
						break;
					case OUT:
						cs.registerOutParameter(pp.getIndex(),pp.getType());
						outIndex.add(pp.getIndex());
						break;
					case INOUT:
						cs.setObject(pp.getIndex(), pp.getValue());
						cs.registerOutParameter(pp.getIndex(),pp.getType());
						outIndex.add(pp.getIndex());
						break;
					default:
						
						break;
					}
				}
				cs.execute();
				for(int m = 0;m< outIndex.size();m++){
					result.put(""+outIndex.get(m), cs.getObject(outIndex.get(m)));
				}
				return result;
			}
		});
		return r;
	}
//	public void call(String callString){
//		getJdbcTemplate().execute(callString);
//	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public NamedParameterJdbcTemplate getNameParameTemplate() {
		return nameParameTemplate;
	}
	public void setNameParameTemplate(NamedParameterJdbcTemplate nameParameTemplate) {
		this.nameParameTemplate = nameParameTemplate;
	}

}
