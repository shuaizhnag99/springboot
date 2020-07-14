package com.ule.uhj.sldProxy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.sld.model.UserGeolocation;
import com.ule.uhj.sldProxy.dao.UserGeolocationMapper;
import com.ule.uhj.sldProxy.service.UserGeolocationService;
@Service("userGeolocationService")
public class UserGeolocationServiceImpl implements UserGeolocationService{
	private static final Logger log = LoggerFactory.getLogger(UserGeolocationServiceImpl.class);
	
	@Autowired
	private UserGeolocationMapper dao;

	@Override
	public UserGeolocation save(UserGeolocation info) throws Exception {
		dao.insertSelective(info);
		log.info("save user geolocation,return id:"+info.getId());
		return info;
	}
	
}
