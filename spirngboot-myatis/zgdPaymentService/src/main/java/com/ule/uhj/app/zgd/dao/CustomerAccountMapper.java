package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CustomerAccount;
import com.ule.uhj.app.zgd.model.CustomerAccountExample;

public interface CustomerAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int countByExample(CustomerAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int deleteByExample(CustomerAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int insert(CustomerAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int insertSelective(CustomerAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    List<CustomerAccount> selectByExample(CustomerAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    CustomerAccount selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByExampleSelective(@Param("record") CustomerAccount record, @Param("example") CustomerAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByExample(@Param("record") CustomerAccount record, @Param("example") CustomerAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByPrimaryKeySelective(CustomerAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_CUSTOMER_ACCOUNT
     *
     * @mbggenerated Fri Oct 27 15:39:49 CST 2017
     */
    int updateByPrimaryKey(CustomerAccount record);
}