package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.Formula;
import com.eps.mybatis.auto.entity.FormulaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormulaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(FormulaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(FormulaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(Formula record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(Formula record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<Formula> selectByExample(FormulaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    Formula selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") Formula record, @Param("example") FormulaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") Formula record, @Param("example") FormulaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(Formula record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_formula
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(Formula record);
}