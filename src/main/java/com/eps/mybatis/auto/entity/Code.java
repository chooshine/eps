package com.eps.mybatis.auto.entity;

public class Code extends CodeKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_code.NAME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_code.ORDER_NO
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private Integer orderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_code.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_code.NAME
     *
     * @return the value of s_code.NAME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_code.NAME
     *
     * @param name the value for s_code.NAME
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_code.ORDER_NO
     *
     * @return the value of s_code.ORDER_NO
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_code.ORDER_NO
     *
     * @param orderNo the value for s_code.ORDER_NO
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_code.REMARK
     *
     * @return the value of s_code.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_code.REMARK
     *
     * @param remark the value for s_code.REMARK
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}