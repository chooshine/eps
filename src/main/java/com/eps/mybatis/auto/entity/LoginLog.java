package com.eps.mybatis.auto.entity;

import java.util.Date;

public class LoginLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_login_log.login_log_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer loginLogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_login_log.user_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_login_log.ip
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_login_log.login_datetime
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Date loginDatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_login_log.login_log_id
     *
     * @return the value of s_login_log.login_log_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLoginLogId() {
        return loginLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_login_log.login_log_id
     *
     * @param loginLogId the value for s_login_log.login_log_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_login_log.user_id
     *
     * @return the value of s_login_log.user_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_login_log.user_id
     *
     * @param userId the value for s_login_log.user_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_login_log.ip
     *
     * @return the value of s_login_log.ip
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_login_log.ip
     *
     * @param ip the value for s_login_log.ip
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_login_log.login_datetime
     *
     * @return the value of s_login_log.login_datetime
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Date getLoginDatetime() {
        return loginDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_login_log.login_datetime
     *
     * @param loginDatetime the value for s_login_log.login_datetime
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLoginDatetime(Date loginDatetime) {
        this.loginDatetime = loginDatetime;
    }
}