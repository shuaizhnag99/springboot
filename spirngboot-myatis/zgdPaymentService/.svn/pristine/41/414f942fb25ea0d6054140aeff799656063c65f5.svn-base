package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private InterfaceAccessInfoMapper infoMapper;

	@Override
	public String queryReport(String userOnlyId) throws Exception {
		List<InterfaceAccessInfoWithBLOBs> infos = new ArrayList<InterfaceAccessInfoWithBLOBs>();
		InterfaceAccessInfoExample example = new InterfaceAccessInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo("100001").andInterfaceTypeEqualTo("4103");
		infos = infoMapper.selectByExampleWithBLOBs(example);
		String responseStr = new String(infos.get(0).getResponseInfo());
		return responseStr;
	}

	@Override
	public Map<String, Object> testService(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
