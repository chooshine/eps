package com.eps.mybatis.auto.entity;

import java.util.Date;

public class Note {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_note.NOTE_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer noteId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_note.USER_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_note.QUES_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer quesId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_note.COMMIT_TIME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Date commitTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_note.CONTENT
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_note.NOTE_ID
     *
     * @return the value of e_note.NOTE_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getNoteId() {
        return noteId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_note.NOTE_ID
     *
     * @param noteId the value for e_note.NOTE_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_note.USER_ID
     *
     * @return the value of e_note.USER_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_note.USER_ID
     *
     * @param userId the value for e_note.USER_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_note.QUES_ID
     *
     * @return the value of e_note.QUES_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getQuesId() {
        return quesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_note.QUES_ID
     *
     * @param quesId the value for e_note.QUES_ID
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setQuesId(Integer quesId) {
        this.quesId = quesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_note.COMMIT_TIME
     *
     * @return the value of e_note.COMMIT_TIME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Date getCommitTime() {
        return commitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_note.COMMIT_TIME
     *
     * @param commitTime the value for e_note.COMMIT_TIME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_note.CONTENT
     *
     * @return the value of e_note.CONTENT
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_note.CONTENT
     *
     * @param content the value for e_note.CONTENT
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}