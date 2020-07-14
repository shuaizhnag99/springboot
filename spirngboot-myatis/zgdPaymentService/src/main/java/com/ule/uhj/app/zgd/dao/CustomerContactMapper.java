package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CustomerContact;
import com.ule.uhj.app.zgd.model.CustomerContactExample;

public interface CustomerContactMapper {
    int countByExample(CustomerContactExample example);

    int deleteByExample(CustomerContactExample example);

    int deleteByPrimaryKey(String id);

    int insert(CustomerContact record);

    int insertSelective(CustomerContact record);

    List<CustomerContact> selectByExample(CustomerContactExample example);

    CustomerContact selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CustomerContact record, @Param("example") CustomerContactExample example);

    int updateByExample(@Param("record") CustomerContact record, @Param("example") CustomerContactExample example);

    int updateByPrimaryKeySelective(CustomerContact record);

    int updateByPrimaryKey(CustomerContact record);
}