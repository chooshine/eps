package com.eps.service.corpus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.eps.cons.CommonConstant;
import com.eps.utils.UStrMap;
import com.eps.web.corpus.SearchParamComm;

public class ParseArticleXmlHandler extends DefaultHandler{
	Logger log = LoggerFactory.getLogger(ParseArticleXmlHandler.class);
	List<UStrMap<String>> result = new ArrayList<UStrMap<String>>();
	String nodeName;
	UStrMap<String> item;
	int totalCount = 0;

	int start; // 开始记录数
	int limit; // 每页记录数
	int maxLeftNum = 200;
	SearchParamComm params;
	private long s;
	private long e;

	public ParseArticleXmlHandler() {
	}

	public ParseArticleXmlHandler(int start, int limit,SearchParamComm params) {
		this.start = start;
		this.limit = limit;
		this.params = params;
	}

	@Override
	public void startDocument() throws SAXException {
		log.info("解析开始");
		s = System.currentTimeMillis();
	}

	@Override
	public void endDocument() throws SAXException {
		e = System.currentTimeMillis();
		log.info("解析结束,共用时[" + (e - s) + "]毫秒");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// 解析到ROW节点时创建一个map对像，用来存入记录信息
		if (SearchUtils.ROW.equals(localName))
			item = UStrMap.newInstance();
		// 如果为作者、内容、作品名称节点则将节点名重置
		if (SearchUtils.AUTHOR.equals(localName) || SearchUtils.CONTENT.equals(localName)
				|| SearchUtils.ARTICLE_NAME.equals(localName) || SearchUtils.PUBLICATION_DATE.equals(localName)
				|| SearchUtils.AUTHOR_ID.equals(localName)||SearchUtils.MAINKEY.equals(localName)) {
			nodeName = localName;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (SearchUtils.ROW.equals(localName) && item != null) {
			if (validItem()) {
				if (totalCount >= start && totalCount < start + limit) {
					String content = item.get(SearchUtils.CONTENT);
					//将在词条中出现的其它主关键词也标记为绿色加粗
					content  = SearchUtils.changeGreen(content, params.getKeys()[0]);
					String mainkey = item.get(SearchUtils.MAINKEY);
					if(params.getKeys().length == 2 && 
							!StringUtils.isBlank(params.getKeys()[1]) && 
							!(params.getKeys()[0].equals(params.getKeys()[1]))){
						//将副关键词标记为绿色加粗
						content = SearchUtils.changeGreen(content, params.getKeys()[1]);
					}
					//将本次查询的主关键词标记为红色加粗
					content = SearchUtils.changeRed(content, CommonConstant.KEYCHAR);
					content = content.replaceAll(CommonConstant.KEYCHAR, mainkey);
					item.put(SearchUtils.CONTENT, content);
					result.add((UStrMap<String>) item.clone());
				}
				totalCount++;
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		StringBuffer sb = new StringBuffer();
		String[] keys = params.getKeys();
		for (int i = start; i < start + length; i++) {
			sb.append(ch[i]);
		}
		String value = sb.toString();
		if(SearchUtils.CONTENT.equals(nodeName)){
			//根据左右长度截取相应内容
			value = SearchUtils.getExpressions(value, keys[0], params.getLeftNum(), params.getRightNum(),maxLeftNum);	
		}
		item.put(nodeName, value);
	}

	/**
	 * 验证记录是否附合条件
	 * 
	 * @return
	 */
	protected boolean validItem() {
		if (item != null) {
			String content = item.get(SearchUtils.CONTENT);
			String mainKey = item.get(SearchUtils.MAINKEY);
			if(params.getKeys().length==2 && !StringUtils.isBlank(params.getKeys()[1])){
				if(!SearchUtils.checkKeys(params.getPlaceType(), params.getIntervalNum(), content,params.getKeys()[1] )){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public List<UStrMap<String>> getResult() {
		return this.result;
	}

	public int getTotalCount() {
		return this.totalCount;
	}


	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
