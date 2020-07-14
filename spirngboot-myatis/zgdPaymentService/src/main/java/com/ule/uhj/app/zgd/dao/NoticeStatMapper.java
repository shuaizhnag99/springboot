package com.ule.uhj.app.zgd.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.NoticeStat;
import com.ule.uhj.app.zgd.model.NoticeStatExample;

public interface NoticeStatMapper {
    int countByExample(NoticeStatExample example);

    int deleteByExample(NoticeStatExample example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(NoticeStat record);

    int insertSelective(NoticeStat record);

    List<NoticeStat> selectByExample(NoticeStatExample example);

    NoticeStat selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") NoticeStat record, @Param("example") NoticeStatExample example);

    int updateByExample(@Param("record") NoticeStat record, @Param("example") NoticeStatExample example);

    int updateByPrimaryKeySelective(NoticeStat record);

    int updateByPrimaryKey(NoticeStat record);
}