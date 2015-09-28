package com.eps.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

//import com.eps.utils.LStrMap;

/**
 * ʵ����� ʵ��Serializable�ӿ�
 * @author hejunwei
 *
 */
@SuppressWarnings("serial")
public abstract class BaseDomain implements Serializable{

	/**
	 * ͳһ��toString()����
	 */
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	//abstract T create(LStrMap<Object> map);
}
