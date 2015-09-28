package com.eps.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eps.utils.LStrMap;

public class Article extends BaseDomain {
	private long articleId;
	/**
	 * 作品名称
	 */
	private String articleName;
	/**
	 * 作者
	 */
	private int authorId;
	/**
	 * 内容
	 */
	private List<Map<String,String>> content;

	/**
	 * 出版时间
	 */
	private String publisherDate;
	
	private String authorName;

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public List<Map<String, String>> getContent() {
		return content;
	}

	public void setContent(List<Map<String, String>> content) {
		this.content = content;
	}

	public String getPublisherDate() {
		return publisherDate;
	}

	public void setPublisherDate(String publisherDate) {
		this.publisherDate = publisherDate;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public int hashCode() {
		String s = this.articleName + "_" + this.articleId;
		return s.hashCode();
//		return Integer.parseInt(String.valueOf(this.articleId));
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
//	public static void main(String[] args) {
//		Article a1 = new Article();
//		a1.setArticleId(1);
//		a1.setArticleName("aaa");
//		Article a2 = new Article();
//		a2.setArticleId(1);
//		a2.setArticleName("aab");
//		
//		System.out.println(a1.equals(a2));
//	}
}
