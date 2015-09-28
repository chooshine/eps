package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.QuestionType;
import com.eps.mybatis.auto.entity.QuestionTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(QuestionTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(QuestionTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(QuestionType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(QuestionType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<QuestionType> selectByExample(QuestionTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    QuestionType selectByPrimaryKey(Integer typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") QuestionType record, @Param("example") QuestionTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") QuestionType record, @Param("example") QuestionTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(QuestionType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_ques_type
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(QuestionType record);
}