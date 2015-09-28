package com.eps.utils;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 键值为大写的HashMap
 * @author ： hejunwei
 *
 * @param <V> ： 任意类型
 */
@SuppressWarnings("serial")
public class UStrMap<V> extends HashMap<String, V> {
	private UStrMap(){}
	
	public static <V> UStrMap<V> newInstance(){
		return new UStrMap<V>();
	}
	@Override
	public V put(String key, V value)
	{
		return super.put(key.toUpperCase(), value);
	}

	@Override
	public V get(Object key)
	{
		key = key.toString().toUpperCase();
		return super.get(key);
	}
	
	@Override
	public V remove(Object key)
	{
		key = key.toString().toUpperCase();
		return super.remove(key);
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		key = key.toString().toUpperCase();
		return super.containsKey(key);
	}
	
}
