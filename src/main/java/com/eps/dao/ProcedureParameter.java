package com.eps.dao;

import java.sql.Types;

/**
 * 过程参数类
 * 
 */
public class ProcedureParameter{
	
	private INOUT inout;
	
	private int index;
	
	private int type;
	
	private Object value;
	
	private ProcedureParameter(){
		
	}
	
	/**
	 * 创建过程输入参数
	 * @param index 参数索引位置, 从1开始
	 * @param value 参数值
	 * @return ProcedureParameter
	 */
	public static ProcedureParameter createInParameter(int index,Object value){
		return new ProcedureParameter(INOUT.IN,index,value);
	}
	/**
	 * 创建输出参数
	 * @param index 参数索引位置, 从1开始
	 * @param type  参数类型  {@link Types}
	 * @return ProcedureParameter
	 */
	public static ProcedureParameter createOutParameter(int index,int type){
		return new ProcedureParameter(INOUT.OUT,index,type);
	}
	
	/**
	 * 创建输入输出参数
	 * @param index 参数索引位置, 从1开始
	 * @param type  参数类型  {@link Types}
	 * @param value 参数值
	 * @return ProcedureParameter
	 */
	public static ProcedureParameter createInOutParameter(int index, int type, Object value){
		return new ProcedureParameter(INOUT.INOUT, index, type, value);
	}
	/**
	 * 
	 * @param inout 参数输入输出类型   {@link INOUT }
	 * @param index 位置 从1开始
	 * @param value  参数值
	 */
	private ProcedureParameter(INOUT inout,int index, Object value){
		this.index = index;
		this.inout = inout;
		this.value = value;
	}
	/**
	 * 
	 * @param inout 参数输入输出类型   {@link INOUT }
	 * @param index 参数位置 从1开始
	 * @param type  参数类型
	 * @param value 参数值
	 */
	private ProcedureParameter(INOUT inout,int index, int type, Object value){
		this.index = index;
		this.inout = inout;
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 
	 * @param inout 参数输入输出类型   {@link INOUT }
	 * @param index 位置 从1开始
	 * @param type  类型
	 */
	private ProcedureParameter(INOUT inout,int index, int type){
		this.index = index;
		this.inout = inout;
		this.type = type;
	}
	public INOUT getInout() {
		return inout;
	}
	public void setInout(INOUT inout) {
		this.inout = inout;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	 

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}


	/**
	 * 参数输入输出类型
	 * <BR> IN : 输入
	 * <br> OUT: 输出 
	 * <br> INOUT : 输入输出型
	 * @author hejunwei
	 *
	 */
	public enum INOUT {
		/**
		 * 输入参数
		 */
		IN,
		/**
		 * 输出参数
		 */
		OUT,
		/**
		 * 输入输出参数
		 */
		INOUT;
	}
	
}
