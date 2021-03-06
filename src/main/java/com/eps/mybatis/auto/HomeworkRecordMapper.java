package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.HomeworkRecord;
import com.eps.mybatis.auto.entity.HomeworkRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HomeworkRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(Integer hwRecId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(HomeworkRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(HomeworkRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<HomeworkRecord> selectByExampleWithBLOBs(HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<HomeworkRecord> selectByExample(HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    HomeworkRecord selectByPrimaryKey(Integer hwRecId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") HomeworkRecord record, @Param("example") HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleWithBLOBs(@Param("record") HomeworkRecord record, @Param("example") HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") HomeworkRecord record, @Param("example") HomeworkRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(HomeworkRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(HomeworkRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_record
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(HomeworkRecord record);
}