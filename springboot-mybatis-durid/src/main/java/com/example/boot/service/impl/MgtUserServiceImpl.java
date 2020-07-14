package com.example.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boot.entity.MgtUser;
import com.example.boot.mapper.MgtUserMapper;
import com.example.boot.service.MgtUserService;

@Service
public class MgtUserServiceImpl implements MgtUserService {
    
	@Autowired
	private MgtUserMapper  mgtUserMapper;
	@Override
	public List<MgtUser> selectAll() {
		return mgtUserMapper.selectAll();
	}

}
