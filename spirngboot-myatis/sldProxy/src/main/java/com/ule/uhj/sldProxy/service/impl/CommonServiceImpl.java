package com.ule.uhj.sldProxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.sldProxy.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.sldProxy.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.sldProxy.service.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private InterfaceAccessInfoMapper interfaceInfoMapper;
	
	public void saveInterfaceAccess(InterfaceAccessInfoWithBLOBs interfaceBlobs)throws Exception{
		interfaceInfoMapper.insert(interfaceBlobs);
	}

}
