package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CreditCardSms;
import com.ule.uhj.app.zgd.model.CreditCardSmsExample;

public interface CreditCardSmsMapper {
    int countByExample(CreditCardSmsExample example);

    int deleteByExample(CreditCardSmsExample example);

    int deleteByPrimaryKey(String id);

    int insert(CreditCardSms record);

    int insertSelective(CreditCardSms record);

    List<CreditCardSms> selectByExample(CreditCardSmsExample example);

    CreditCardSms selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CreditCardSms record, @Param("example") CreditCardSmsExample example);

    int updateByExample(@Param("record") CreditCardSms record, @Param("example") CreditCardSmsExample example);

    int updateByPrimaryKeySelective(CreditCardSms record);

    int updateByPrimaryKey(CreditCardSms record);
}