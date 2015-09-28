package com.eps.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

//import com.eps.utils.LStrMap;

/**
 * 实体基类 实现Serializable接口
 * @author hejunwei
 *
 */
@SuppressWarnings("serial")
public abstract class BaseDomain implements Serializable{

	/**
	 * 统一的toString()方法
	 */
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	//abstract T create(LStrMap<Object> map);
}
