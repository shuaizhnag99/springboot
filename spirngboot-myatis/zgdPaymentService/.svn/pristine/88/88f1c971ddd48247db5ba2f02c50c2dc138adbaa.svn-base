package com.ule.uhj.app.zgd.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CreditReturn;
import com.ule.uhj.app.zgd.model.CreditReturnExample;

public interface CreditReturnMapper {
    int countByExample(CreditReturnExample example);

    int deleteByExample(CreditReturnExample example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(CreditReturn record);

    int insertSelective(CreditReturn record);

    List<CreditReturn> selectByExample(CreditReturnExample example);

    CreditReturn selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") CreditReturn record, @Param("example") CreditReturnExample example);

    int updateByExample(@Param("record") CreditReturn record, @Param("example") CreditReturnExample example);

    int updateByPrimaryKeySelective(CreditReturn record);

    int updateByPrimaryKey(CreditReturn record);
}