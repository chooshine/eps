package com.eps.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
	public static String formatDateTime(Date date, String format) {
		if (date == null)
			return null;
		if (format == null)
			return date.toString();
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String formatY(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy");
	}

	public static String formatYM(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM");
	}

	public static String formatYMD(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd");
	}

	public static String formatYMDHM(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm");
	}

	public static String formatYMDHMS(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将日期字符串转换为 Date类型
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);

		Date result = null;
		try {
			result = df.parse(dateStr);
		} catch (ParseException e) {
		}

		return result;
	}
	/**
	 * 返回当前时间格式：yyyyMMddHHmmssSSS
	 * @return  
	 */
	
	public static String getNowYMDHMSS(){
		return formatDateTime(new Date(), "yyyyMMddHHmmssSSS");
	}
	
	/**
	 * 两个时间的差
	 * @param date1
	 * @param date2
	 * @return long数组 , {天,小时,分钟,秒}
	 */
	public static long[] getDateDiff(Date date1,Date date2){
		try{
			long d1 = date1.getTime();
			long d2 = date2.getTime();
			
			if(d2==d1){
				return new long[]{0,0,0,0};
			}
			long diff = 0l;
			if(d2>d1){
				diff = (d2-d1)/1000;
			}
			if(d2<d1){
				diff = (d1 - d2)/1000;
			}
			
			long day = diff/(24*3600);
			long hour = diff%(24*3600)/3600;
			long min = diff%3600/60;
			long sen = diff%60/60;
			return new long[]{day,hour,min,sen};
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * 时间添加
	 * @param date 要修改的时间
	 * @param field 要添加的时间字段 
	 * @param value 要添加的值 (如要减少可填负值)
	 * @return
	 */
	public static Date add(Date date,int field, int value){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(field, value);
		return gc.getTime();
	}
}
