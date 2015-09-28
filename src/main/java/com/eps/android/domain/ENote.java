package com.eps.android.domain;

import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

public class ENote {

	private int noteId;
	private long userId;
	private int quesId;
	private String commitTime;
	private String content;
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getQuesId() {
		return quesId;
	}
	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}
	public String getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public static ENote create(LStrMap<Object> map){
		ENote eNote = new ENote();
		eNote.setNoteId(map.get("note_id")!=null?(Integer) map.get("note_id"):null);
		eNote.setUserId(map.get("user_id")!=null?(Long) map.get("user_id"):null);
		eNote.setQuesId(map.get("ques_id")!=null?(Integer) map.get("ques_id"):null);
		eNote.setCommitTime(map.get("commit_time")!=null?map.get("commit_time").toString():null);
		eNote.setContent(map.get("content")!=null?map.get("content").toString():null);
		return eNote;
	}
	
	public UStrMap<Object> toMap(){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("note_id", noteId);
		map.put("user_id", userId);
		map.put("ques_id", quesId);
		map.put("commit_time", commitTime);
		map.put("content", content);
		return map;
	}
}
