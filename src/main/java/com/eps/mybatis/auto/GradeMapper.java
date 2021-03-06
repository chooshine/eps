package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.Grade;
import com.eps.mybatis.auto.entity.GradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GradeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(GradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(GradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(Integer gradeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(Grade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(Grade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<Grade> selectByExample(GradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    Grade selectByPrimaryKey(Integer gradeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") Grade record, @Param("example") GradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") Grade record, @Param("example") GradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(Grade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_grade
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(Grade record);
}