package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CustomerIncome;
import com.ule.uhj.app.zgd.model.CustomerIncomeExample;

public interface CustomerIncomeMapper {
    int countByExample(CustomerIncomeExample example);

    int deleteByExample(CustomerIncomeExample example);

    int deleteByPrimaryKey(String id);

    int insert(CustomerIncome record);

    int insertSelective(CustomerIncome record);

    List<CustomerIncome> selectByExample(CustomerIncomeExample example);

    CustomerIncome selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CustomerIncome record, @Param("example") CustomerIncomeExample example);

    int updateByExample(@Param("record") CustomerIncome record, @Param("example") CustomerIncomeExample example);

    int updateByPrimaryKeySelective(CustomerIncome record);

    int updateByPrimaryKey(CustomerIncome record);
}