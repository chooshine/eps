package com.eps.mybatis.auto.entity;

public class HomeworkRecordDetail extends HomeworkRecordDetailKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_hw_record_detail.SCORE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Double score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_hw_record_detail.O_SCORE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String oScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_hw_record_detail.ANSWER_TIME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String answerTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_hw_record_detail.COMMENT_REC
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String commentRec;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_hw_record_detail.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_hw_record_detail.SCORE
     *
     * @return the value of e_hw_record_detail.SCORE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Double getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_hw_record_detail.SCORE
     *
     * @param score the value for e_hw_record_detail.SCORE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_hw_record_detail.O_SCORE
     *
     * @return the value of e_hw_record_detail.O_SCORE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getoScore() {
        return oScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_hw_record_detail.O_SCORE
     *
     * @param oScore the value for e_hw_record_detail.O_SCORE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setoScore(String oScore) {
        this.oScore = oScore == null ? null : oScore.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_hw_record_detail.ANSWER_TIME
     *
     * @return the value of e_hw_record_detail.ANSWER_TIME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getAnswerTime() {
        return answerTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_hw_record_detail.ANSWER_TIME
     *
     * @param answerTime the value for e_hw_record_detail.ANSWER_TIME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime == null ? null : answerTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_hw_record_detail.COMMENT_REC
     *
     * @return the value of e_hw_record_detail.COMMENT_REC
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getCommentRec() {
        return commentRec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_hw_record_detail.COMMENT_REC
     *
     * @param commentRec the value for e_hw_record_detail.COMMENT_REC
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setCommentRec(String commentRec) {
        this.commentRec = commentRec == null ? null : commentRec.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_hw_record_detail.REMARK
     *
     * @return the value of e_hw_record_detail.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_hw_record_detail.REMARK
     *
     * @param remark the value for e_hw_record_detail.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}