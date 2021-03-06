package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.ZhujuanImages;
import com.eps.mybatis.auto.entity.ZhujuanImagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZhujuanImagesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(ZhujuanImagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(ZhujuanImagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(ZhujuanImages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(ZhujuanImages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<ZhujuanImages> selectByExample(ZhujuanImagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    ZhujuanImages selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") ZhujuanImages record, @Param("example") ZhujuanImagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") ZhujuanImages record, @Param("example") ZhujuanImagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(ZhujuanImages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table z_image
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(ZhujuanImages record);
}