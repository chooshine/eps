package com.eps.utils;

import java.util.HashMap;

/**
 * 所有键值都为小写的HashMap
 * @author hejunwei
 *
 * @param <V> : 任意类型
 */
@SuppressWarnings("serial")
public class LStrMap<V> extends HashMap<String, V>{
	
	private LStrMap(){}
	
	public static <V> LStrMap<V> newInstance(){
		return new LStrMap<V>();
	}
	
	@Override
	public V put(String key, V value)
	{
		return super.put(key.toLowerCase(), value);
	}
	
	@Override
	public V get(Object key)
	{
		key = key.toString().toLowerCase();
		return super.get(key);
	}
	
	@Override
	public V remove(Object key)
	{
		key = key.toString().toLowerCase();
		return super.remove(key);
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		key = key.toString().toLowerCase();
		return super.containsKey(key);
	}
}
