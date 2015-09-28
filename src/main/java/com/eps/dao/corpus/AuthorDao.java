package com.eps.dao.corpus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.Author;
import com.eps.utils.LStrMap;

@Repository
public class AuthorDao extends BaseDao{
	
	@Value("${author.get.all}")
	private String getAllSql;
	
	@Value("${author.get.report}")
	private String getAuthorByReport;
	@Value("${author.get.namecode}")
	private String getAllNamecode;
	
	@Value("${author.get.all.articlenum}")
	private String getAllAuthorArticleNum;
	
	@Value("${author.query.article.top}")
	private String getTopAuthor;
	/**
	 * 获取所有作者信息
	 * @return
	 */
	public List<Author> getAllAuthor(){
		return this.getJdbcTemplate().query(getAllSql, new AuthorMapRow());
	}
	public List<Author> getAuthorByReport(String reportName){
		String sql = getAuthorByReport.replaceAll("@@REPORT_NAME,", reportName);
		return this.getJdbcTemplate().query(sql, new AuthorMapRow());
	}
	/**
	 * 获取作者姓名分类
	 * @return
	 */
	public List<LStrMap<Object>> getAllNameCode(){
		return find(getAllNamecode);
	}
	
	/**
	 * 获取所有作者信息及作品数量
	 * @return
	 */
	public List<Author> getAllAuthorArticleNum(){
		return this.getJdbcTemplate().query(getAllAuthorArticleNum, new AuthorMapRow());
	}
	/**
	 * 获取作品数量前10的作者及作品数量
	 * @return
	 */
	public List<LStrMap<Object>> getTopAuthor(){
		return find(getTopAuthor);
	}
	class AuthorMapRow implements RowMapper<Author>{
		public Author mapRow(ResultSet rs, int index) throws SQLException {
			Author author = new Author();
			author.setAuthorId(rs.getLong("AUTHOR_ID"));
			author.setNameCode(rs.getString("NAME_CODE"));
			author.setFirstName(rs.getString("FIRST_NAME"));
			author.setLastName(rs.getString("LAST_NAME"));
			author.setFirstNameOr(rs.getString("FIRST_NAME_OR"));
			author.setLastNameOr(rs.getString("LAST_NAME_OR"));
			author.setFirstNameEn(rs.getString("FIRST_NAME_EN"));
			author.setLastNameEn(rs.getString("LAST_NAME_EN"));
			if(rs.getMetaData().getColumnCount()>8)
				author.setArticleNum(rs.getInt("ARTICLE_NUM"));
			return author;
		}
		
	}
}
