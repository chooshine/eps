package com.eps.mybatis.auto.entity;

public class TestRecordDetailWithBLOBs extends TestRecordDetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_test_record_detail.ANSWER
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String answer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_test_record_detail.STUDENT_ANSWER
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String studentAnswer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_test_record_detail.TEACHER_COMMENT
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String teacherComment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_test_record_detail.ANSWER
     *
     * @return the value of e_test_record_detail.ANSWER
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_test_record_detail.ANSWER
     *
     * @param answer the value for e_test_record_detail.ANSWER
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_test_record_detail.STUDENT_ANSWER
     *
     * @return the value of e_test_record_detail.STUDENT_ANSWER
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getStudentAnswer() {
        return studentAnswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_test_record_detail.STUDENT_ANSWER
     *
     * @param studentAnswer the value for e_test_record_detail.STUDENT_ANSWER
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer == null ? null : studentAnswer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_test_record_detail.TEACHER_COMMENT
     *
     * @return the value of e_test_record_detail.TEACHER_COMMENT
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getTeacherComment() {
        return teacherComment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_test_record_detail.TEACHER_COMMENT
     *
     * @param teacherComment the value for e_test_record_detail.TEACHER_COMMENT
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment == null ? null : teacherComment.trim();
    }
}