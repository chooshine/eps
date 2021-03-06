package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.LessonClass;
import com.eps.mybatis.auto.entity.LessonClassExample;
import com.eps.mybatis.auto.entity.LessonClassKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LessonClassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(LessonClassKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(LessonClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(LessonClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<LessonClass> selectByExampleWithBLOBs(LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<LessonClass> selectByExample(LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    LessonClass selectByPrimaryKey(LessonClassKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") LessonClass record, @Param("example") LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleWithBLOBs(@Param("record") LessonClass record, @Param("example") LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") LessonClass record, @Param("example") LessonClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(LessonClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(LessonClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_lesson_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(LessonClass record);
}