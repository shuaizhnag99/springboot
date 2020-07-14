package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;

public interface CustomerPhoneMapper {
    int countByExample(CustomerPhoneExample example);

    int deleteByExample(CustomerPhoneExample example);

    int deleteByPrimaryKey(String id);

    int insert(CustomerPhone record);

    int insertSelective(CustomerPhone record);

    List<CustomerPhone> selectByExample(CustomerPhoneExample example);

    CustomerPhone selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CustomerPhone record, @Param("example") CustomerPhoneExample example);

    int updateByExample(@Param("record") CustomerPhone record, @Param("example") CustomerPhoneExample example);

    int updateByPrimaryKeySelective(CustomerPhone record);

    int updateByPrimaryKey(CustomerPhone record);
}