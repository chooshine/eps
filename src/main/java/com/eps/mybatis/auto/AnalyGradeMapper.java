package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.AnalyGrade;
import com.eps.mybatis.auto.entity.AnalyGradeExample;
import com.eps.mybatis.auto.entity.AnalyGradeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnalyGradeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(AnalyGradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(AnalyGradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(AnalyGradeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(AnalyGrade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(AnalyGrade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<AnalyGrade> selectByExample(AnalyGradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    AnalyGrade selectByPrimaryKey(AnalyGradeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") AnalyGrade record, @Param("example") AnalyGradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") AnalyGrade record, @Param("example") AnalyGradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(AnalyGrade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_analy_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(AnalyGrade record);
}