package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.CreditRule;
import com.ule.uhj.app.zgd.model.CreditRuleExample;

public interface CreditRuleMapper {
    int countByExample(CreditRuleExample example);

    int deleteByExample(CreditRuleExample example);

    int deleteByPrimaryKey(String id);

    int insert(CreditRule record);

    int insertSelective(CreditRule record);

    List<CreditRule> selectByExample(CreditRuleExample example);

    CreditRule selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CreditRule record, @Param("example") CreditRuleExample example);

    int updateByExample(@Param("record") CreditRule record, @Param("example") CreditRuleExample example);

    int updateByPrimaryKeySelective(CreditRule record);

    int updateByPrimaryKey(CreditRule record);
}