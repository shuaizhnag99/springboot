package com.ule.uhj.sldProxy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.sld.model.PhoneInfo;
import com.ule.uhj.sldProxy.dao.PhoneInfoMapper;
import com.ule.uhj.sldProxy.service.PhoneInfoService;
@Service("phoneInfoService")
public class PhoneInfoServiceImpl implements PhoneInfoService{
	private static final Logger log = LoggerFactory.getLogger(PhoneInfoServiceImpl.class);

	@Autowired
	private PhoneInfoMapper dao;
	
	@Override
	public PhoneInfo save(PhoneInfo info) throws Exception {
		log.info("Phoneinfo...save...start..!");
		dao.insertSelective(info);
		return info;
	}
}
