package com.eps.service.crawl.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eps.cons.CommonConstant;
import com.eps.service.crawl.bean.CrawlUrl;
import com.eps.service.crawl.bean.NewsBean;
import com.eps.utils.DateHelper;

public class AsahiNews {

	private NewsBean bean = new NewsBean();
	private static final String DEFAULT_CHARSET = "utf-8";
	private Parser parser;

	private CrawlUrl url;
	private String charset;
	private static Logger log = LoggerFactory.getLogger(AsahiNews.class);
	public AsahiNews(CrawlUrl url) throws Exception {
		this.url = url;
		InputStream is = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			parser = new Parser();
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CommonConstant.CONNECT_TIMEOUT);
			client.getHttpConnectionManager().getParams().setSoTimeout(CommonConstant.READ_TIMEOUT);
			GetMethod method = new GetMethod(url.getUrl());
			log.info("请求地址：[{}]",url.getUrl());
			client.executeMethod(method);
			
			is = method.getResponseBodyAsStream();
			charset = StringUtils.isBlank(url.getCharSet())?DEFAULT_CHARSET:url.getCharSet();
			isr = new InputStreamReader(is,charset);
			br = new BufferedReader(isr);
			String str = "";
			StringBuffer sb = new StringBuffer();
			while((str = br.readLine())!= null){
				sb.append(str);
			}
			String html = sb.toString();
			parser.setInputHTML(html);
			String tempcharset;
			tempcharset = getCharSet();
			if(!tempcharset.toLowerCase().equals(charset.toLowerCase())){
				parser.reset();
				html = new String(html.getBytes(charset),tempcharset);
				parser.setInputHTML(html);
			}
			log.info("请求地址：[{}]结束",url.getUrl());
		} catch (Exception e) {
			log.info("初始化解析类出错",e);
			throw new Exception("初始化解析类出错");
		} finally{
			try {
				if(is!=null)is.close();
				if(br!=null)br.close();
				if(isr!=null)isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public NewsBean getBean() {
		return bean;
	}

	public void setNews(String newsTitle, int authorId, String newsContent,
			String newsDate, String url) {
		bean.setTitle(newsTitle);
		bean.setAuthorId(authorId);
		bean.setContent(newsContent);
		bean.setDate(newsDate);
		bean.setUrl(url);
		// System.out.println(bean);
	}

	/**
	 * 获得新闻的标题
	 * 
	 * @param titleFilter
	 * @param parser
	 * @return
	 * @throws ParserException
	 */
	private String getTitle(List<NodeFilter> titleFilter)
			throws ParserException {
		log.info("开始解析标题");
		String titleName = "";
		if (titleFilter == null || titleFilter.size() < 1)
			return titleName;
		for (NodeFilter nodeFilter : titleFilter) {
			NodeList titleNodeList = (NodeList) parser.parse(nodeFilter);
			if (titleNodeList.size() < 1) {
				parser.reset();
				continue;
			}
			for (int i = 0; i < titleNodeList.size(); i++) {
				if (titleNodeList.elementAt(i) instanceof MetaTag) {
					MetaTag meta = (MetaTag) titleNodeList.elementAt(i);
					titleName = meta.getAttribute("content");
				} else {
					CompositeTag title = (CompositeTag) titleNodeList
							.elementAt(i);
					titleName = title.getStringText();
				}
			}
			break;
		}
		log.info("解析标题结束");
		return titleName;
	}

	/**
	 * 获得新闻的日期
	 * 
	 * @throws ParserException
	 */
	private String getNewsDate(List<NodeFilter> dateFilter)
			throws ParserException {
		log.info("开始解析时间");
		String newsDate = null;
		if (dateFilter == null || dateFilter.size() < 1)
			return newsDate;
		for (NodeFilter nodeFilter : dateFilter) {
			NodeList dateList = (NodeList) parser.parse(nodeFilter);
			if (dateList.size() < 1) {
				parser.reset();
				continue;
			}
			for (int i = 0; i < dateList.size(); i++) {
				if (dateList.elementAt(i) instanceof MetaTag) {
					MetaTag meta = (MetaTag) dateList.elementAt(i);
					newsDate = meta.getAttribute("content");
				} else {
					CompositeTag dateTag = (CompositeTag) dateList.elementAt(i);
					newsDate = dateTag.getStringText();
				}
			}
			break;
		}
		log.info("解析时间结束");
		return newsDate;
	}

	/**
	 * 获取新闻的内容
	 * 
	 * @param newsContentFilter
	 * @param parser
	 * @return content 新闻内容
	 * @throws ParserException
	 */
	private String getNewsContent(List<NodeFilter> newsContentFilter)
			throws ParserException {
		log.info("开始解析新闻内容");
		String content = null;
		if (newsContentFilter == null || newsContentFilter.size() < 1)
			return content;
		StringBuilder builder = new StringBuilder();
		for (NodeFilter nodeFilter : newsContentFilter) {
			NodeList newsContentList = (NodeList) parser.parse(nodeFilter);
			if (newsContentList.size() < 1) {
				parser.reset();
				continue;
			}
			for (int i = 0; i < newsContentList.size(); i++) {
				Node node = newsContentList.elementAt(i);
				if(node instanceof CompositeTag){
					CompositeTag newsContenTag = (CompositeTag) newsContentList
							.elementAt(i);
					builder = builder.append(newsContenTag.getStringText());
				}else{
					TagNode tagnode = (TagNode) node;
					builder = builder.append(tagnode.getFirstChild().getText());
				}
			}
			content = builder.toString(); // 转换为String 类型。
			if (!StringUtils.isBlank(content)) {
				parser.reset();
				parser = Parser.createParser(content, parser.getEncoding());
				StringBean sb = new StringBean();
				sb.setCollapse(true);
				parser.visitAllNodesWith(sb);
				content = sb.getStrings();
				content = content.replaceAll("\\\".*[a-z].*\\}", "");
			} else {
			}
			break;
		}
		log.info("解析内容结束");
		return content;
	}

	public boolean parser() {
		log.info("开始解析");
		try {
			List<NodeFilter> titleFilters = parseStr(url.getTitleFilter());
			List<NodeFilter> contentFilters = parseStr(url.getContentFilter());
			List<NodeFilter> dateFilters = parseStr(url.getDateFilter());
			parser.reset();
			String title = getTitle(titleFilters);
//			title = new String(title.getBytes("ISO-8859-1"),charset);
			parser.reset();
			String date = getNewsDate(dateFilters);
			parser.reset();
			String content = getNewsContent(contentFilters);
//			content = new String(content.getBytes("ISO-8859-1"),charset);
			parser.reset();
			if (!StringUtils.isBlank(content) && !StringUtils.isBlank(title)) {
				if (!StringUtils.isBlank(date)) {
					date = DateHelper.formatDateTime(
							DateHelper.parseDate(date, url.getDateFormat()),
							"yyyyMMdd");
				} else
					date = DateHelper.formatDateTime(new Date(), "yyyyMMdd");
				setNews(title, url.getAuthorId(), content, date, url.getUrl());
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("解析结束");
		return false;
	}

	public List<CrawlUrl> extracLinks() {
		log.info("开始解析链接");
		List<CrawlUrl> links = new ArrayList<CrawlUrl>();
		NodeFilter frameFilter = new NodeFilter() {
			public boolean accept(Node node) {
				try {
					String nodeStr = node.getText();
					// nodeStr = StringHelper.replaceBlank(nodeStr);
					if (!StringUtils.isBlank(nodeStr)
							&& nodeStr.toLowerCase().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
		};
		OrFilter orfilter = new OrFilter(new NodeClassFilter(LinkTag.class),
				frameFilter);
		NodeList nodeList;
		try {
			// parser.reset();
			nodeList = parser.extractAllNodesThatMatch(orfilter);
			for (int i = 0; i < nodeList.size(); i++) {
				Node tag = nodeList.elementAt(i);
				String currUrl = url.getUrl();
				if (tag instanceof LinkTag) {// <a> 标签
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// url
					if (linkUrl.toLowerCase().indexOf("javascript") != -1)
						continue;// 过滤掉a标签中是javascript的链接
					checkAndAddUrl(linkUrl, links);

				} else { // <frame> 标签
					String frame = tag.getText();
					int start = frame.toLowerCase().indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if (end == -1) {
						end = frame.indexOf(">");
					}
					String frameUrl = frame.substring(5, end - 1);
					checkAndAddUrl(frameUrl, links);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		log.info("解析链接结束");
		return links;
	}
	private void checkAndAddUrl(String linkUrl,List<CrawlUrl> links){
//		log.info("新地址[{}]",linkUrl);
		CrawlUrl newUrl = url.clone();
//		String currUrl = url.getUrl();
		String reg = "^[[.]/]{1,}";
		if(linkUrl.startsWith("/") || linkUrl.startsWith("./") || linkUrl.startsWith("../")){
			linkUrl = linkUrl.replaceAll(reg, "http://" + url.getURL().getHost()+"/");
		}
//		if (linkUrl.startsWith("./") || linkUrl.startsWith("../"))
//			linkUrl = currUrl
//					.substring(0, currUrl.lastIndexOf("/"))
//					+ linkUrl.substring(linkUrl.indexOf("/"), linkUrl.length());
		if (accept(linkUrl, url.getFilterReg())) {
			newUrl.setUrl(linkUrl);
			newUrl.setLevel(url.getLevel() + 1);
			links.add(newUrl);
//			log.info("新地址[{}]通过",linkUrl);
		}
		
	}
	private boolean accept(String url, String filterReg) {
		Pattern p = Pattern.compile(filterReg);
		Matcher m = p.matcher(url);
		return m.matches();
	}

	private NodeFilter initFilter(String str) {
		NodeFilter nodeFilter = null;
		if (StringUtils.isBlank(str))
			return nodeFilter;
		String[] strs = str.split("#");
		if (strs.length == 1) {
			nodeFilter = new TagNameFilter(strs[0]);
		} else {
			String[] attr = strs[1].split("=");
			nodeFilter = new AndFilter(new TagNameFilter(strs[0]),
					new HasAttributeFilter(attr[0], attr[1]));
		}
		return nodeFilter;
	}

	private String getCharSet() throws Exception {
		String charset = "utf-8";
		Node node = null;
		while((node = parser.getLexer().nextNode())!=null){
			if(node instanceof MetaTag){
				MetaTag meta = (MetaTag)node;
				String type = meta.getAttribute("http-equiv");
				if(type != null && "content-type".equals(type.toLowerCase())){
					String content = meta.getAttribute("content");
					charset = content.toLowerCase().split("charset=")[1];
					break;
				}else if(meta.getAttribute("charset")!=null){
					charset = meta.getAttribute("charset");
					break;
				}
			}
		}
		if(charset.indexOf(";")!=-1)charset = charset.replaceAll(";", "");
		return charset;
	}

	private List<NodeFilter> parseStr(String str) {
		List<NodeFilter> filters = new ArrayList<NodeFilter>();
		if (StringUtils.isBlank(str))
			return filters;
		String[] strs = str.split("@@");
		for (String string : strs) {
			filters.add(initFilter(string));
		}
		return filters;
	}

	public static void main(String[] args) {
		String reg = "^[[.]/]{1,}";
		 CrawlUrl url = new CrawlUrl();
		 url.setUrl("http://www.sankei.com/economy/economy.html");
//		 url.setTitleFilter("meta#property=og:title");
//		 url.setContentFilter("div#class=fontMiddiumText");
//		 url.setDateFilter("span#id=__r_publish_date__");
//		 url.setDateFormat("yyyy.MM.dd HH:mm");
//		 url.setFilterReg("^http://www.sankei.com(/(?!english|photo|gallery|multimedia|advertising|rss)[A-Za-z0-9_\\-=#@%&,.!]*)*(\\.(?:html|htm|xhtml|shtml))?(#?[A-Za-z0-9_\\-=#@%&,.!]*)*(\\?[A-Za-z0-9_\\-=#@%&,.!]*)?");
//		 url.setCharSet("UTF-8");
//		 url.setLevel(3);
//		 url.setMaxLevel(10);
//		 AsahiNews news = null;
//		try {
//			news = new AsahiNews(url);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		 List<CrawlUrl> list = news.extracLinks();
//		 System.out.println(list.size());
//		 news.parser();
//		 System.out.println(news.getBean());
		String link = "../../test/test.htlm";
//		link.indexOf(reg);
		System.out.println(link.indexOf(reg));
		if(link.startsWith("/") || link.startsWith("./") || link.startsWith("../")){
			link = link.replaceAll(reg, "http://"+url.getURL().getHost()+"/");
		}
//		String s = link.replaceAll(reg, "/");
		System.out.println(link);
//		System.out.println(link.substring(link.indexOf("/"), link.length()));
//		System.out.println(link.substring(link.indexOf("./"), link.length()));
	}
}
