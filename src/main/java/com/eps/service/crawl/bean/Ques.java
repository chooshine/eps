package com.eps.service.crawl.bean;

import java.util.Date;

public class Ques {
	private int id;
	private QuesAbility quesAbility;
	private QuesDiff quesDiff;
	private QuesType quesType;
	private String quesAnswer;
	private String quesBody;
	private String quesParse;
	private String title;
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public QuesAbility getQuesAbility() {
		return quesAbility;
	}
	public void setQuesAbility(String quesAbility) {
		if(quesAbility.indexOf("#")!=-1){
			String[] str = quesAbility.split("#");
			this.quesAbility = new QuesAbility(Integer.parseInt(str[0]), str[1]);
		}
	}
	public QuesDiff getQuesDiff() {
		return quesDiff;
	}
	public void setQuesDiff(String quesDiff) {
		if(quesDiff.indexOf("#")!=-1){
			String[] str = quesDiff.split("#");
			this.quesDiff = new QuesDiff(Integer.parseInt(str[0]), str[1]);
		}
	}
	public QuesType getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		if(quesType.indexOf("#")!=-1){
			String[] str = quesType.split("#");
			this.quesType = new QuesType(Integer.parseInt(str[0]), str[1]);
		}
	}
	public String getQuesAnswer() {
		return quesAnswer;
	}
	public void setQuesAnswer(String quesAnswer) {
		this.quesAnswer = quesAnswer;
	}
	public String getQuesBody() {
		return quesBody;
	}
	public void setQuesBody(String quesBody) {
		this.quesBody = quesBody;
	}
	public String getQuesParse() {
		return quesParse;
	}
	public void setQuesParse(String quesParse) {
		this.quesParse = quesParse;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public static class QuesAbility{
		private int id;
		private String name;
		
		public QuesAbility(int id,String name){
			this.id = id;
			this.name = name;
		}
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	public static class QuesDiff{
		private int id;
		private String name;
		
		public QuesDiff(int id,String name){
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	public static class QuesType{
		private int id;
		private String name;
		
		public QuesType(int id,String name){
			this.id = id;
			this.name = name;
		}
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
}
