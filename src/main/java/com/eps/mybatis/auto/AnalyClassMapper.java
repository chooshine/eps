package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.AnalyClass;
import com.eps.mybatis.auto.entity.AnalyClassExample;
import com.eps.mybatis.auto.entity.AnalyClassKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnalyClassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(AnalyClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(AnalyClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(AnalyClassKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(AnalyClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(AnalyClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<AnalyClass> selectByExample(AnalyClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    AnalyClass selectByPrimaryKey(AnalyClassKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") AnalyClass record, @Param("example") AnalyClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") AnalyClass record, @Param("example") AnalyClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(AnalyClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(AnalyClass record);
}