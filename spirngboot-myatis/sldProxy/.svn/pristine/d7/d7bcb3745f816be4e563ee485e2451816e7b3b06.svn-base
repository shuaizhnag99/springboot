package com.ule.uhj.sldProxy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.sld.model.ContactsInfo;
import com.ule.uhj.sldProxy.dao.ContactsInfoMapper;
import com.ule.uhj.sldProxy.service.ContactsInfoService;
@Service("contactsInfoService")
public class ContactsInfoServiceImpl implements ContactsInfoService{
	private static final Logger log = LoggerFactory.getLogger(ContactsInfoServiceImpl.class);

	@Autowired
	private ContactsInfoMapper dao;
	
	@Override
	public ContactsInfo save(ContactsInfo info) throws Exception {
		log.info("ContactsInfo...save...start..!");
		dao.insertSelective(info);
		return info;
	}
}
