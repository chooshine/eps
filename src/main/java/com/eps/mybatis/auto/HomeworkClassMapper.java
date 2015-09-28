package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.HomeworkClass;
import com.eps.mybatis.auto.entity.HomeworkClassExample;
import com.eps.mybatis.auto.entity.HomeworkClassKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HomeworkClassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(HomeworkClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(HomeworkClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(HomeworkClassKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(HomeworkClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(HomeworkClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<HomeworkClass> selectByExample(HomeworkClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    HomeworkClass selectByPrimaryKey(HomeworkClassKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") HomeworkClass record, @Param("example") HomeworkClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") HomeworkClass record, @Param("example") HomeworkClassExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(HomeworkClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_hw_class
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(HomeworkClass record);
}