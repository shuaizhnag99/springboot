package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.UserInfo;
import com.ule.uhj.app.zgd.model.UserInfoExample;

public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int countByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int deleteByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int deleteByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int insertSelective(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    List<UserInfo> selectByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    UserInfo selectByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_USER_INFO
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByPrimaryKey(UserInfo record);
}