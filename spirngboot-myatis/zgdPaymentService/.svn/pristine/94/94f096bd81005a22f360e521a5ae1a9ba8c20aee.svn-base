package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CustomerWhiteMapper;
import com.ule.uhj.app.zgd.model.CustomerWhite;
import com.ule.uhj.app.zgd.model.CustomerWhiteExample;
import com.ule.uhj.app.zgd.service.CustomerWhiteService;
import com.ule.uhj.util.Convert;

@Service
public class CustomerWhiteServiceImpl implements CustomerWhiteService {
	
	private static Logger log = LoggerFactory.getLogger(CustomerWhiteServiceImpl.class);
	@Autowired
	private CustomerWhiteMapper customerWhiteMapper;
	
	@Override
	public Map<String, Object> queryCustomerWhite(Map<String, Object> map)
			throws Exception {
		Map<String, Object> para=new HashMap<String, Object>();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String legalName=Convert.toStr(map.get("userName"));
		String legalCardNo=Convert.toStr(map.get("certNo"));
		log.info("queryCustomerWhite param:"+map);
		List<CustomerWhite> customerWhites = new ArrayList<CustomerWhite>();
		CustomerWhiteExample customerWhiteExample = new CustomerWhiteExample();
		customerWhiteExample.createCriteria().andLegalNameEqualTo(legalName).andLegalCardNoEqualTo(legalCardNo);
		customerWhites = customerWhiteMapper.selectByExample(customerWhiteExample);
		if(customerWhites!= null && customerWhites.size()>0){
			para.put("userOnlyId", userOnlyId);
			para.put("CustomerWhite", customerWhites.get(0));
//			para.put("legalName", customerWhites.get(0).getLegalName());
//			para.put("legalCardNo", customerWhites.get(0).getLegalCardNo());
//			para.put("businessName", customerWhites.get(0).getBusinessName());
//			para.put("businessLicenseNo", customerWhites.get(0).getBusinessLicenseNo());
//			para.put("legalMobilePhone", customerWhites.get(0).getLegalMobilePhone());
//			para.put("channelName", customerWhites.get(0).getChannelName());
//			para.put("legalMobilePhone", customerWhites.get(0).getLegalMobilePhone());
		}else{
			log.info("queryCustomerWhite userOnlyId :"+userOnlyId+"; result is null ");
			return null;
		}
		log.info("queryCustomerWhite result:"+para.toString());
		return para;
	}

}
