package com.eps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
	private Integer code;
	private String name;
	public Person(){
		
	}
	public Person(Integer code,String name){
		this.code = code;
		this.name = name;
	}
	@Override
	public int hashCode() {
		return code.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof Person){
			Person other = (Person) obj;
			if(this.code == other.code) return true;
			return false;
		}
		return false;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static void main(String[] args) {
		List<Person> persons = new ArrayList<Person>();
		//往列表中添加10个Person
		for (int i = 1; i <= 10; i++) {
			persons.add(new Person(i,"name"+i));
		}
		Person temp = new Person();
		temp.setCode(5);
		//从列表中查找编号为5的Person
		Person p = persons.get(persons.indexOf(temp));
		System.out.println(p.getName()); //输入  name5
		
	//////////////////////////////////////////////////////
		Map<Integer, String> personsMap = new HashMap<Integer, String>();
		for (int i = 1; i <= 10; i++) {
			personsMap.put(i, "name"+i);
		}
		System.out.println(personsMap.get(5));//输出 name5
	}
}
