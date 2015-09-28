package com.eps.service.corpus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.eps.cons.CommonConstant;


public class SearchUtils {
	private static final String PLACETYPE1 = "1"; // 两个关键词都存在
	private static final String PLACETYPE2 = "2"; // 只有第一关键词
	private static final String PLACETYPE3 = "3"; // 第二关键词在第一关键词后
	private static final String PLACETYPE4 = "4"; // 第二关键词在第一关键词前
	private static final String PLACETYPE5 = "5"; // 第二关键词在第一关键词后，且第一关键词前无第二关键词
	private static final String PLACETYPE6 = "6"; // 第二关键词在第一关键词前，且第一关键词后无第二关键词

	public final static String AUTHOR = "AUTHOR";

	public final static String CONTENT = "CONTENT";
	public final static String MAINKEY = "MAINKEY";
	public final static String ARTICLE_NAME = "ARTICLE_NAME";
	public final static String PUBLICATION_DATE = "PUBLICATION_DATE";
	public final static String AUTHOR_ID = "AUTHOR_ID";
	public final static String ROW = "ROW";
	
	// 判断关键词是否符合条件
	public static boolean checkKeys(String placeType, int intervalNum,
			String str, String otherKey) {
		int index = str.indexOf(CommonConstant.KEYCHAR);
		int sindex = str.indexOf(otherKey, 0);
		int eindex = str.indexOf(otherKey, index);
		if (PLACETYPE1.equals(placeType)) {
			if (sindex != -1 || eindex != -1)
				return true;
		} else if (PLACETYPE2.equals(placeType)) {
			if (sindex == -1 && eindex == -1)
				return true;
		} else if (PLACETYPE3.equals(placeType)) {
			if (eindex != -1 && index < eindex /*&& eindex - index == intervalNum*/)
				return true;
		} else if (PLACETYPE4.equals(placeType)) {
			if (sindex != -1 && index > sindex /*&& index - sindex == intervalNum*/)
				return true;
		} else if (PLACETYPE5.equals(placeType)) {
			if (sindex > index && eindex != -1 && eindex - index - CommonConstant.KEYCHAR.length() <= intervalNum)
				return true;
		} else if (PLACETYPE6.equals(placeType)) {
			if (sindex != -1 && sindex < index && eindex == -1 && index - sindex - otherKey.length()<= intervalNum)
				return true;
		} else {
			if (sindex != -1 || eindex != -1)
				return true;
		}
		return false;
	}

	public static String getExpressions(String content, String key,
			int leftNum, int rightNum,int maxLeftNum) {
		String[] contents = content.split(CommonConstant.KEYCHAR);
		if (leftNum>0 && contents.length == 2) {
			String s = contents[0];
			String e = contents[1];
			if (s.length() == maxLeftNum) {
				s = s.substring(maxLeftNum - leftNum);
			} else if (s.length() < maxLeftNum && s.length() > leftNum) {
				s = s.substring(s.length() - leftNum);
			}
			if (rightNum >0 && e.length() > rightNum) {
				e = e.substring(0, rightNum);
			}
			content = s + CommonConstant.KEYCHAR + e;
		}
		return content;
	}

	public static String changeRed(String str, String key) {
		return str.replaceAll(key, "<font color='red'><b>" + key
				+ "</b></font>");
	}
	public static String changeGreen(String str,String key){
		Pattern p = Pattern.compile(key);
		Matcher m = p.matcher(str);
		boolean flag = false;
		while(m.find()){
			flag = true;
			String temp = m.group(0);
			str = str.replaceAll(temp, "<font color='green'><b>" + temp
					+ "</b></font>");
		}
		if(!flag){
			return str.replaceAll(key, "<font color='green'><b>" + key
					+ "</b></font>");
		}
		return str;
//		return str.replaceAll(key, "<font color='green'><b>" + key
//				+ "</b></font>");
	}
	
	public static void main(String[] args) {
		String str = "aaaabbbcccssdadws";
		Pattern p = Pattern.compile("aa.{1,10}c");
		Matcher m = p.matcher(str);
		while(m.find()){
			String temp = m.group(0);
			str = str.replaceAll(temp, "xxx"+temp+"XXX");
		}
		System.out.println(str);
	}
}
