package com.ule.uhj.sldProxy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.sld.model.UserCallLog;
import com.ule.uhj.sldProxy.dao.UserCallLogMapper;
import com.ule.uhj.sldProxy.service.UserCallLogService;
@Service("userCallLogService")
public class UserCallLogServiceImpl implements UserCallLogService{
	private static final Logger log = LoggerFactory.getLogger(UserCallLogServiceImpl.class);

	@Autowired
	private UserCallLogMapper dao;
	
	@Override
	public UserCallLog save(UserCallLog info) throws Exception {
		log.info("UserCallLog...save...start..!");
		dao.insertSelective(info);
		return info;
	}
}
