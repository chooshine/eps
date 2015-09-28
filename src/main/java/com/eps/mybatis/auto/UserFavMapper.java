package com.eps.mybatis.auto;

import com.eps.mybatis.auto.entity.UserFav;
import com.eps.mybatis.auto.entity.UserFavExample;
import com.eps.mybatis.auto.entity.UserFavKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFavMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int countByExample(UserFavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByExample(UserFavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int deleteByPrimaryKey(UserFavKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insert(UserFav record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int insertSelective(UserFav record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    List<UserFav> selectByExample(UserFavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    UserFav selectByPrimaryKey(UserFavKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserFav record, @Param("example") UserFavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByExample(@Param("record") UserFav record, @Param("example") UserFavExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKeySelective(UserFav record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_user_fav
     *
     * @mbggenerated Tue May 26 14:51:53 CST 2015
     */
    int updateByPrimaryKey(UserFav record);
}