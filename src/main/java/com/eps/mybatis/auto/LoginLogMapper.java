package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.LoginLog;
import com.eps.mybatis.auto.entity.LoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoginLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(LoginLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(LoginLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(Integer loginLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(LoginLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(LoginLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<LoginLog> selectByExample(LoginLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    LoginLog selectByPrimaryKey(Integer loginLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") LoginLog record, @Param("example") LoginLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") LoginLog record, @Param("example") LoginLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(LoginLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_login_log
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(LoginLog record);
}