package com.ule.uhj.app.zgd.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.ActivityInfo;
import com.ule.uhj.app.zgd.model.ActivityInfoExample;

public interface ActivityInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int countByExample(ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int deleteByExample(ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int insert(ActivityInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int insertSelective(ActivityInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    List<ActivityInfo> selectByExample(ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    ActivityInfo selectByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int updateByExampleSelective(@Param("record") ActivityInfo record, @Param("example") ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int updateByExample(@Param("record") ActivityInfo record, @Param("example") ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int updateByPrimaryKeySelective(ActivityInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_ACTIVITY_INFO
     *
     * @mbggenerated Thu Feb 01 14:59:46 CST 2018
     */
    int updateByPrimaryKey(ActivityInfo record);
}