package com.eps.utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringHelper {
	/**
	 * 去除空格、回车、换行、制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (StringUtils.isBlank(str))return "";
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		str = m.replaceAll("");
		return str;
	}
	/**
	 * 去除所有html标签
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str){
		if(StringUtils.isBlank(str)) return "";
		Pattern p = Pattern.compile("</?[^>]*>");
		Matcher m = p.matcher(str);
		str = m.replaceAll("");
		return str;
	}
	/**
	 * 去除html中的空格 &nbsp;
	 * @param str
	 * @return
	 */
	public static String removeNPSB(String str){
		if(StringUtils.isBlank(str)) return "";
		str = str.replaceAll("&nbsp;", "");
		return str;
	}
	/**
	 * 转义XML中不能识别的物殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeXMLChar(String str) {
		if (StringUtils.isBlank(str))
			return "";
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
				.replaceAll("\'", "&apos;");
	}
	
	/**
	 * 将IP地址转为long
	 * @param ip
	 * @return
	 */
	public static long toNumeric(String ip){
		Scanner sc = new Scanner(ip).useDelimiter("\\.");
		return (sc.nextLong() << 24) + 
	           (sc.nextLong() << 16) + 
	           (sc.nextLong() << 8) + 
	           (sc.nextLong());
	}
	/**
	 * 头部以字符c补齐
	 * 
	 * @param s
	 *            需补齐的字符串
	 * @param length
	 *            需补长度
	 * @param c
	 *            补的字符
	 * @return
	 */
	public static String fillTops(String s, int length, char c) {
		String s_result = s;
		if (s_result == null) {
			s_result = "";
		} else {
			s_result = s_result.trim();
		}

		int lens = s_result.length();
		if (lens < length) {
			for (int i = 0; i < (length - lens); i++) {
				s_result = c + s_result;
			}
		} else {
			s_result = s_result.substring(0, length);
		}

		return s_result;
	}

	/**
	 * 头部补0
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String zeroFillTop(String s, int length) {
		return fillTops(s, length, '0');
	}

	/**
	 * 全解转半角
	 * 
	 * @param str
	 * @return
	 */
	public static String fullChangeHalf(String str) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < str.length(); i++) {
			try {
				Tstr = str.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}

		return outStr;
	}
	
	public static void main(String[] args) {
		System.out.println(StringHelper.toNumeric("127.0.0.1"));
	}
}
