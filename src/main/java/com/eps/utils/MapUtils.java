package com.eps.utils;

import java.util.Map;

public class MapUtils {
	
	public static String getValue(Map<String,Object> map, String key){
		if(map == null || !map.containsKey(key))return "";
		Object obj = map.get(key);
		if(obj!=null){
			if (obj instanceof Double) {
				double d = (Double) obj;
				int value = (int) d;
				return String.valueOf(value);
			}
			return obj.toString();
		}
		return "";
	}
	public static Map<String,Object> getMap(Map<String,Object> map, String key){
		if(map == null || !map.containsKey(key))return null;
		Object obj = map.get(key);
		if(obj!=null && obj instanceof Map){
			Map<String,Object> value = (Map<String, Object>) obj;
			return value;
		}
		return null;
	}
}
