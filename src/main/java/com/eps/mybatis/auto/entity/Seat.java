package com.eps.mybatis.auto.entity;

import java.util.Date;

public class Seat extends SeatKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_seat.SEAT_NAME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String seatName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_seat.SEAT_NO
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String seatNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_seat.START_DATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_seat.END_DATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_seat.EXIST_FLAG
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer existFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_seat.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_seat.SEAT_NAME
     *
     * @return the value of c_seat.SEAT_NAME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getSeatName() {
        return seatName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_seat.SEAT_NAME
     *
     * @param seatName the value for c_seat.SEAT_NAME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setSeatName(String seatName) {
        this.seatName = seatName == null ? null : seatName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_seat.SEAT_NO
     *
     * @return the value of c_seat.SEAT_NO
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getSeatNo() {
        return seatNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_seat.SEAT_NO
     *
     * @param seatNo the value for c_seat.SEAT_NO
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo == null ? null : seatNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_seat.START_DATE
     *
     * @return the value of c_seat.START_DATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_seat.START_DATE
     *
     * @param startDate the value for c_seat.START_DATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_seat.END_DATE
     *
     * @return the value of c_seat.END_DATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_seat.END_DATE
     *
     * @param endDate the value for c_seat.END_DATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_seat.EXIST_FLAG
     *
     * @return the value of c_seat.EXIST_FLAG
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getExistFlag() {
        return existFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_seat.EXIST_FLAG
     *
     * @param existFlag the value for c_seat.EXIST_FLAG
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setExistFlag(Integer existFlag) {
        this.existFlag = existFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_seat.REMARK
     *
     * @return the value of c_seat.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_seat.REMARK
     *
     * @param remark the value for c_seat.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}