package com.eps.mybatis.auto.entity;

public class StudentWithBLOBs extends Student {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_student.LICENSE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String license;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_student.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_student.LICENSE
     *
     * @return the value of c_student.LICENSE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getLicense() {
        return license;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_student.LICENSE
     *
     * @param license the value for c_student.LICENSE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_student.REMARK
     *
     * @return the value of c_student.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_student.REMARK
     *
     * @param remark the value for c_student.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}