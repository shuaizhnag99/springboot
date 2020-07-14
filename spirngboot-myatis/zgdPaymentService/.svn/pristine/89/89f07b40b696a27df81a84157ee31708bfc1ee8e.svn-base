package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.ApplyImage;
import com.ule.uhj.app.zgd.model.ApplyImageExample;

public interface ApplyImageMapper {
    int countByExample(ApplyImageExample example);

    int deleteByExample(ApplyImageExample example);

    int deleteByPrimaryKey(String id);

    int insert(ApplyImage record);

    int insertSelective(ApplyImage record);

    List<ApplyImage> selectByExample(ApplyImageExample example);

    ApplyImage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ApplyImage record, @Param("example") ApplyImageExample example);

    int updateByExample(@Param("record") ApplyImage record, @Param("example") ApplyImageExample example);

    int updateByPrimaryKeySelective(ApplyImage record);

    int updateByPrimaryKey(ApplyImage record);
}