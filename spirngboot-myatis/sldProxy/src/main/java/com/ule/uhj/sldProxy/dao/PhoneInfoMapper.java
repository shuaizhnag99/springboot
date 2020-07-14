package com.ule.uhj.sldProxy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.sld.model.PhoneInfo;
import com.ule.uhj.sld.model.PhoneInfoExample;

public interface PhoneInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int countByExample(PhoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int deleteByExample(PhoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int insert(PhoneInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int insertSelective(PhoneInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    List<PhoneInfo> selectByExample(PhoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    PhoneInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int updateByExampleSelective(@Param("record") PhoneInfo record, @Param("example") PhoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int updateByExample(@Param("record") PhoneInfo record, @Param("example") PhoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int updateByPrimaryKeySelective(PhoneInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_U_PHONE_INFO
     *
     * @mbggenerated Mon Jun 27 16:58:16 CST 2016
     */
    int updateByPrimaryKey(PhoneInfo record);
}