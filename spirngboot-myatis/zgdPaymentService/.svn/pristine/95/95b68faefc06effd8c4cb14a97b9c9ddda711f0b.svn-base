package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;

public interface CustomerCertMapper {
    int countByExample(CustomerCertExample example);

    int deleteByExample(CustomerCertExample example);

    int deleteByPrimaryKey(String id);

    int insert(CustomerCert record);

    int insertSelective(CustomerCert record);

    List<CustomerCert> selectByExample(CustomerCertExample example);

    CustomerCert selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CustomerCert record, @Param("example") CustomerCertExample example);

    int updateByExample(@Param("record") CustomerCert record, @Param("example") CustomerCertExample example);

    int updateByPrimaryKeySelective(CustomerCert record);

    int updateByPrimaryKey(CustomerCert record);
}