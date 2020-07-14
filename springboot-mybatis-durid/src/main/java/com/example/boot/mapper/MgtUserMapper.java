package com.example.boot.mapper;

import java.util.List;

import com.example.boot.entity.MgtUser;

public interface MgtUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MgtUser record);

    int insertSelective(MgtUser record);

    MgtUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MgtUser record);

    int updateByPrimaryKey(MgtUser record);
    
    List<MgtUser> selectAll();
}