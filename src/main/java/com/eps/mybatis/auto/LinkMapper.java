package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.Link;
import com.eps.mybatis.auto.entity.LinkExample;
import com.eps.mybatis.auto.entity.LinkKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(LinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(LinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(LinkKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(Link record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(Link record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<Link> selectByExample(LinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    Link selectByPrimaryKey(LinkKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") Link record, @Param("example") LinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") Link record, @Param("example") LinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(Link record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_link
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(Link record);
}