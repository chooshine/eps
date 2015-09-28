package com.eps.service.crawl.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;












import com.eps.utils.UStrMap;


public class ZhujuanParse {
	private Logger log = LoggerFactory.getLogger(ZhujuanParse.class);
	public List<UStrMap<Object>> parseCategory(String html,String parentId,String type, String bankId){
		try {
			Parser parser = new Parser(html);
			NodeFilter filter = new TagNameFilter("li");
			NodeList nodes = parser.parse(filter);
			List<UStrMap<Object>> result = new ArrayList<UStrMap<Object>>();
			for(int i=0,len=nodes.size();i<len;i++){
				CompositeTag node = (CompositeTag) nodes.elementAt(i);
				String id = node.getAttribute("id");
				CompositeTag a = (CompositeTag) node.getChild(0);
				String name = a.getStringText();
				UStrMap<Object> item = UStrMap.newInstance();
				item.put("id", id);
				item.put("name", name);
				item.put("parentid", parentId);
				item.put("type",type);
				item.put("bankId", bankId);
				result.add(item);
			}
			log.info("解析完成:{}",result);
			return result;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public List<UStrMap<Object>> parseRoot(String html,String type,String bankId){
		try {
			Parser parser = new Parser(html);
			NodeFilter filter = new TagNameFilter("a");
			NodeList nodes = parser.parse(filter);
			List<UStrMap<Object>> result = new ArrayList<UStrMap<Object>>();
			for(int i=0,len = nodes.size();i<len;i++){
				CompositeTag node = (CompositeTag) nodes.elementAt(i);
				String id = node.getAttribute("textbookid");
				String name = node.getStringText();
				UStrMap<Object> item = UStrMap.newInstance();
				item.put("id", id);
				item.put("name", name);
				item.put("parentid", 0);
				item.put("type",type);
				item.put("bankId", bankId);
				result.add(item);
			}
			return result;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<UStrMap<Object>> parseImageUrl(String html){
		try {
			Parser parser = new Parser(html);
			NodeFilter filter = new TagNameFilter("img");
			NodeList nodes = parser.parse(filter);
			List<UStrMap<Object>> result = new ArrayList<UStrMap<Object>>();
			for (int i = 0,len = nodes.size() ; i < len; i++) {
				ImageTag node = (ImageTag) nodes.elementAt(i);
				String imgUrl = node.getImageURL();
				UStrMap<Object> item = UStrMap.newInstance();
				item.put("imgUrl", imgUrl);
				result.add(item);
			}
			return result;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<UStrMap<Object>> parseCategory(String html, int id){
		List<UStrMap<Object>> result = new ArrayList<UStrMap<Object>>();
		try {
			Document document = DocumentHelper.parseText("<root>"+html+"</root>");
			Element root = document.getRootElement();
			for(Iterator<Element> iteator = root.elementIterator();iteator.hasNext();){
				Element node = iteator.next();
				List<Element> list = node.elements();
				if(list.size()<1) continue;
				Element obj = list.get(list.size()-1);
				String category = obj.attribute("id").getText();
				UStrMap<Object> item = UStrMap.newInstance();
				item.put("categoryid", category);
				item.put("quesid", id);
				result.add(item);
			}
			return result;
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
//		ZhujuanParse parse = new ZhujuanParse();
//		String html = "<item><category id=\"1372\">高中数学综合库</category><category id=\"1373\">集合与常用逻辑用语</category><category id=\"1375\">集合的运算</category></item>"
//				    + "<item><category id=\"2345\">高中数学文科库</category><category id=\"2346\">必修1</category><category id=\"2347\">第一章、集合与函数概念</category><category id=\"2348\">1、集合</category><category id=\"2351\">（3）集合的基本运算</category></item>"
//				    + "<item><category id=\"16621\">高中数学新课标人教A版</category><category id=\"16622\">必修一</category><category id=\"16623\">第一章 集合与函数的概念</category><category id=\"16624\">1.1 集合</category></item>"
//				    + "<item><category id=\"16967\">高中数学新课标人教B版</category><category id=\"16968\">必修一</category><category id=\"16969\">第一章 集合</category><category id=\"16971\">1.2 集合之间的关系与运算</category></item>"
//				    + "<item><category id=\"17194\">高中数学北师大版</category><category id=\"17195\">必修一</category><category id=\"17196\">第一章 集合</category><category id=\"17199\">1.3 集合的基本运算</category></item>";
//		System.out.println(parse.parseCategory(html,1));
	}
}
