package com.eps.mybatis.auto.entity;

public class CodeKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_code.CODE_CATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String codeCate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_code.CODE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    private String code;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_code.CODE_CATE
     *
     * @return the value of s_code.CODE_CATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getCodeCate() {
        return codeCate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_code.CODE_CATE
     *
     * @param codeCate the value for s_code.CODE_CATE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setCodeCate(String codeCate) {
        this.codeCate = codeCate == null ? null : codeCate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_code.CODE
     *
     * @return the value of s_code.CODE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_code.CODE
     *
     * @param code the value for s_code.CODE
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}