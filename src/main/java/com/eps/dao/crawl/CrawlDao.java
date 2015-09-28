package com.eps.dao.crawl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.service.crawl.bean.CrawlUrl;
import com.eps.service.crawl.bean.NewsBean;
import com.eps.utils.UStrMap;

@Repository
public class CrawlDao extends BaseDao{
	
	@Value("${crawler.get.init.url}")
	private String getInitUrlSql;
	
	@Value("${crawler.query.url.count}")
	private String getUrlCount;
	
	@Value("${crawler.insert.visited.url}")
	private String insertVistedUrl;
	
	@Value("${crawler.insert.news}")
	private String insertNews;
	
	@Value("${crawler.insert.url}")
	private String insertUrl;
	
	@Value("${crawler.delete.url}")
	private String deleteUrl;
	
	@Value("${crawler.get.queue.url}")
	private String getQueueUrl;
	
	@Value("${crawler.queue.queueurl.count}")
	private String getQueueUrlCount;
	/**
	 * 查询该url是否已用过
	 * @param url
	 * @return
	 */
	public int getUrlCount(CrawlUrl url){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("url", url.getUrl());
		List list = this.find(getUrlCount,args);
		return list==null?0:list.size();
	}
	public int getQueueUrlCount(CrawlUrl url){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("url", url.getUrl());
		List list = this.find(getQueueUrlCount,args);
		return list==null?0:list.size();
	}
	/**
	 * 插入链接
	 * @param url
	 */
	public void insertUrl(String url){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("url", url);
		args.put("date", new Date());
		excute(insertVistedUrl, args);
	}
	
	public List<CrawlUrl> getInitUrl(){
		return this.getJdbcTemplate().query(getInitUrlSql, new CrawlUrlRowMapp());
	}
	public List<CrawlUrl> getQueueUrl(){
		return this.getJdbcTemplate().query(getQueueUrl, new CrawlUrlRowMapp());
	}
	public void insertUrl(List<CrawlUrl> urls){
		SqlParameterSource[] batchArgs = new SqlParameterSource[urls.size()];
		int i=0;
		for(CrawlUrl url:urls){
			UStrMap<Object> map = url.toMap();
			batchArgs[i] = new MapSqlParameterSource(map);
			i++;
		}
		this.getNameParameTemplate().batchUpdate(insertUrl, batchArgs);
	}
	
	public void deleteUrl(String url){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("url", url);
		this.excute(deleteUrl, args);
	}
	/**
	 * 将抓取的新闻存入数据库
	 * @param bean
	 */
	public boolean insertNews(NewsBean bean,String tableName){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("title", bean.getTitle());
		args.put("content", bean.getContent());
		args.put("author", bean.getAuthorId());
		args.put("date", bean.getDate());
		args.put("url", bean.getUrl());
		args.put("ENTERING_DATE", new Date());
		String sql = insertNews;
		if(insertNews.indexOf("@@TABLE_NAME,")!=-1){
			sql = insertNews.replaceAll("@@TABLE_NAME,", tableName);
		}
		return excute(sql, args)==1;
	}
	class CrawlUrlRowMapp implements RowMapper<CrawlUrl>{
		public CrawlUrl mapRow(ResultSet rs, int arg1) throws SQLException {
			CrawlUrl url = new CrawlUrl();
			url.setAuthorId(rs.getInt("AUTHOR_ID"));
			url.setUrl(rs.getString("URL"));
			url.setFilterReg(rs.getString("FILTERREG"));
			url.setLevel(rs.getInt("LEVEL"));
			url.setMaxLevel(rs.getInt("MAXLEVEL"));
			url.setDateFormat(rs.getString("DATEFORMAT"));
			url.setTableName(rs.getString("TABLENAME"));
			url.setCharSet(rs.getString("CHARSET"));
			String titleTag = rs.getString("TITLETAG");
			String contentTag = rs.getString("CONTENTTAG");
			String dateTag = rs.getString("DATETAG");
			url.setTitleFilter(titleTag);
			url.setContentFilter(contentTag);
			url.setDateFilter(dateTag);
			return url;
		}
		
	}
	public static void main(String[] args) {
//		String str = "INSERT INTO @@TABLE_NAME,(TITLE,CONTENT,DATE,AUTHOR_ID,URL) VALUES(:TITLE, :CONTENT, :DATE, :AUTHOR, :URL)";
//		if(str.indexOf("@@TABLE_NAME,")!=-1){
//			str = str.replaceAll("@@TABLE_NAME,", "l_news");
//		}
//		System.out.println(str);
	}
}
