package com.eps.dao.corpus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.cons.CommonConstant;
import com.eps.dao.BaseDao;
import com.eps.domain.Article;
import com.eps.utils.HttpHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.web.corpus.SearchParamComm;

@Repository
public class ArticleDao extends BaseDao {
	private final static int RANDOM = 500;
	@Value("${article.query.key}")
	private String findArticleBykey;
	//private List<Article> query;
	
	@Value("${search.max.left}")
	private int leftNum;
	
	@Value("${search.max.right}")
	private int rightNum;
	
	@Value("${article.query.author}")
	private String findArticleByAuthor;
	
	@Value("${article.query.distribute}")
	private String findArticleDistribute;
	
	public List<Article> findArticleByKey(SearchParamComm comm){
		return findArticleByKey(comm, 0, Integer.MAX_VALUE);
	}
	public List<Article> findArticleByKey(SearchParamComm comm, int start,int limit){
		UStrMap<Object> params = UStrMap.newInstance();
		final String k = comm.getKeys()[0];
		String repType = comm.getRepositoryType();
		String value = k;
		if(!"".equals(comm.getKeyTogether())){
			value =  k+"("+comm.getKeyTogether()+")";
		}
		final String regex = value;
		params.put("key", regex);
		params.put("article_name", StringUtils.isBlank(comm.getArticleName())?"-1":comm.getArticleName());
		params.put("startDate", StringUtils.isBlank(comm.getStartPublicationDate())?"-1":comm.getStartPublicationDate());
		params.put("endDate", StringUtils.isBlank(comm.getEndPublicationDate())?"-1":comm.getEndPublicationDate());
		params.put("searchLimit", comm.getSearchLimit());
		String authors = "-1";
		if(comm.getAuthors()!= null && comm.getAuthors().length>0){
			String temp = ArrayUtils.toString(comm.getAuthors());
			authors = temp.substring(1, temp.length()-1);
		}
		String sql = findArticleBykey;
		if(findArticleBykey.indexOf("@@TABLE_NAME,") != -1)
			sql = sql.replaceAll("@@TABLE_NAME,", repType);
		if(findArticleBykey.indexOf("@@AUTHORS,")!=-1)
			sql = sql.replaceAll("@@AUTHORS,", authors);
		sql = sql + " limit "+ start + "," + limit;
//		System.out.println(sql);
		List<Article> query = this.getNameParameTemplate().query(sql,params, new RowMapper<Article>() {
			public Article mapRow(ResultSet rs, int index) throws SQLException {
				//LStrMap<Object> item = LStrMap.newInstance();
				Article article = new Article();
				String content = rs.getString("CONTENT");
				String articleName= rs.getString("ARTICLE_NAME");
				//String publish = rs.getString("PUBLISHER");
				String publicationDate = rs.getString("PUBLICATION_DATE");
				String author = rs.getString("AUTHOR");
				int authorId = rs.getInt("AUTHOR_ID");
				int articleId = rs.getInt("ARTICLE_ID");
				String[] contents = content.split(regex);
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(content);
				List<String> keys = new ArrayList<String>();
				while(m.find()){
					keys.add(m.group(0));
				}
	            String start = "", end = "";
	            String s = "";
	            List<Map<String,String>> contentSet = new ArrayList<Map<String,String>>();
	            for (int i = 0; i < contents.length; i++) {
	                String object = contents[i].trim();
	                if (i == 0) {
	                        start = object.length()>leftNum?object.substring(object.length() - leftNum):object;
	                } else {
	                	String mainKey = keys.get(i-1);
	                	Map<String,String> item = new HashMap<String, String>();
	                	String endTemp  = foo(contents,i,keys,object,rightNum);
	                    end = endTemp.length() > rightNum?endTemp.substring(0, rightNum):endTemp;
	                    s = start + CommonConstant.KEYCHAR + end;
	                    item.put(mainKey, HttpHelper.regulateXMLStr(s.replaceAll("\\s{2,}|\t|\r|\n|[\u3000]{2,}", "")));
	                    contentSet.add(item);
	                    //contentSet.add(HttpHelper.regulateXMLStr(s.replaceAll("\\s{2,}|\t|\r|\n|[\u3000]{2,}", "")));
	                    String startTemp = too(contents,i,keys,object,leftNum);
	                    start = startTemp.length()>leftNum?startTemp.substring(startTemp.length() - leftNum):startTemp;
	                }
	            }
	            article.setArticleId(articleId);
	            article.setArticleName(articleName);
	            article.setAuthorId(authorId);
	            article.setAuthorName(author);
	            article.setContent(contentSet);
	            article.setPublisherDate(publicationDate);
				return article;
			}
		});
		return query;
	}
	private String foo(String[] data, int index, List<String> keys,String str, int length){
		if(str.length() > length) return str;
		if(index < data.length - 1){
			String temp  = data[index + 1];
			str = str + keys.get(index)+ temp;
			if(str.length() < length){
				str = foo(data,index+1,keys,str,length);
			}
			return str;
		}
		return str;
	}
	private String too(String[] data,int index,List<String> keys,String str, int length){
		if(str.length()> length) return str;
		if(index > 0){
			String temp = data[index - 1];
			str = temp + keys.get(index-1) + str;
			if(str.length() < length){
				too(data,index-1,keys,str,length);
			}
			return str;
		}
		return str;
	}
	/**
	 * 根据作者获取作品列表
	 * @param authorId
	 * @return
	 */
	public List<Article> getArticleByAuthor(int authorId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("AUTHOR_ID", authorId);
		return getNameParameTemplate().query(findArticleByAuthor, params, new RowMapper<Article>() {
			public Article mapRow(ResultSet rs, int arg1) throws SQLException {
				Article article = new Article();
				article.setArticleId(rs.getLong("ARTICLE_ID"));
				article.setArticleName(rs.getString("ARTICLE_NAME"));
				article.setPublisherDate(rs.getString("PUBLICATION_DATE"));
				return article;
			}
		});
	}
	
	/**
	 * 获取作品年代分布
	 * @return
	 */
	public List<LStrMap<Object>>  getArticleDistribute(){
		return find(findArticleDistribute);
	}
}
