package com.eps.mybatis.auto.entity;

import java.util.Date;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.user_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.user_no
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String userNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.user_name
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.mail_address
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String mailAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.password
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.photo
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.user_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer userType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.locked
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer locked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.last_visit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Date lastVisit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.last_ip
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String lastIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.credit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer credit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.regist_time
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Date registTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.is_enterprise_user
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer isEnterpriseUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.session_num
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer sessionNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.ip_limit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String ipLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_user.isAuthenMail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer isauthenmail;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.user_id
     *
     * @return the value of s_user.user_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.user_id
     *
     * @param userId the value for s_user.user_id
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.user_no
     *
     * @return the value of s_user.user_no
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.user_no
     *
     * @param userNo the value for s_user.user_no
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.user_name
     *
     * @return the value of s_user.user_name
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.user_name
     *
     * @param userName the value for s_user.user_name
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.mail_address
     *
     * @return the value of s_user.mail_address
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.mail_address
     *
     * @param mailAddress the value for s_user.mail_address
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress == null ? null : mailAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.password
     *
     * @return the value of s_user.password
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.password
     *
     * @param password the value for s_user.password
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.photo
     *
     * @return the value of s_user.photo
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.photo
     *
     * @param photo the value for s_user.photo
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.user_type
     *
     * @return the value of s_user.user_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.user_type
     *
     * @param userType the value for s_user.user_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.locked
     *
     * @return the value of s_user.locked
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.locked
     *
     * @param locked the value for s_user.locked
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.last_visit
     *
     * @return the value of s_user.last_visit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Date getLastVisit() {
        return lastVisit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.last_visit
     *
     * @param lastVisit the value for s_user.last_visit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.last_ip
     *
     * @return the value of s_user.last_ip
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.last_ip
     *
     * @param lastIp the value for s_user.last_ip
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.credit
     *
     * @return the value of s_user.credit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getCredit() {
        return credit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.credit
     *
     * @param credit the value for s_user.credit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.regist_time
     *
     * @return the value of s_user.regist_time
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Date getRegistTime() {
        return registTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.regist_time
     *
     * @param registTime the value for s_user.regist_time
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.is_enterprise_user
     *
     * @return the value of s_user.is_enterprise_user
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getIsEnterpriseUser() {
        return isEnterpriseUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.is_enterprise_user
     *
     * @param isEnterpriseUser the value for s_user.is_enterprise_user
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setIsEnterpriseUser(Integer isEnterpriseUser) {
        this.isEnterpriseUser = isEnterpriseUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.session_num
     *
     * @return the value of s_user.session_num
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getSessionNum() {
        return sessionNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.session_num
     *
     * @param sessionNum the value for s_user.session_num
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setSessionNum(Integer sessionNum) {
        this.sessionNum = sessionNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.ip_limit
     *
     * @return the value of s_user.ip_limit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getIpLimit() {
        return ipLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.ip_limit
     *
     * @param ipLimit the value for s_user.ip_limit
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setIpLimit(String ipLimit) {
        this.ipLimit = ipLimit == null ? null : ipLimit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_user.isAuthenMail
     *
     * @return the value of s_user.isAuthenMail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getIsauthenmail() {
        return isauthenmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_user.isAuthenMail
     *
     * @param isauthenmail the value for s_user.isAuthenMail
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setIsauthenmail(Integer isauthenmail) {
        this.isauthenmail = isauthenmail;
    }
}