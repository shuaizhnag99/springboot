package com.ule.uhj.app.zgd.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CustomerContactMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.CustomerPhoneMapper;
import com.ule.uhj.app.zgd.dao.DicMapper;
import com.ule.uhj.app.zgd.model.CustomerContact;
import com.ule.uhj.app.zgd.model.CustomerContactExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;
import com.ule.uhj.app.zgd.model.Dic;
import com.ule.uhj.app.zgd.model.DicExample;
import com.ule.uhj.app.zgd.service.ContactsService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;

@Service("contactsService")
public class ContactsServiceImpl implements ContactsService{
	private static Logger log = LoggerFactory.getLogger(ContactsServiceImpl.class);
	
	private static String contact ="1";

	@Autowired
	private CustomerContactMapper contactMapper;
	
	@Autowired
	private CustomerPhoneMapper phoneMapper;
	
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
	@Autowired
	private DicMapper dicMapper;
	/**
	 * 保存配偶证件类型信息
	 */
	@Override
	public Map<String,Object> saveContactsSpouse(Map<String, Object> dataMap)throws Exception {
		log.info("saveContactsSpouse param:"+dataMap.toString());
		String userOnlyId=Convert.toStr(dataMap.get("userOnlyId"));
		String contactType=Convert.toStr(dataMap.get("contactType"));
		String flag=Convert.toStr(dataMap.get("flag"));
		CustomerContactExample contactExample = new CustomerContactExample();
		contactExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andContactTypeEqualTo(contactType);
		List<CustomerContact> contacts = contactMapper.selectByExample(contactExample);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(contacts!=null && contacts.size()>0){
			CustomerContact customerContact = contacts.get(0);
			customerContact.setContactCertNo(flag);;
			contactMapper.updateByExampleSelective(customerContact, contactExample);
		}
		resultMap.put("code", "0000");
		resultMap.put("msg", "保存成功");
		return resultMap;
	}
	/**
	 * 保存联系人信息
	 */
	@Override
	public Map<String,Object> saveContactsInfo(Map<String, Object> dataMap)throws Exception {
		log.info("saveContactsInfo param:"+dataMap.toString());
		String userOnlyId=Convert.toStr(dataMap.get("userOnlyId"));
		String contactType=Convert.toStr(dataMap.get("contactType"));
		String contactName=Convert.toStr(dataMap.get("contactName"));
		String mobileNo=Convert.toStr(dataMap.get("mobileNo"));
		String maritalStatus=Convert.toStr(dataMap.get("maritalStatus"));
		CustomerContactExample contactExample = new CustomerContactExample();
		contactExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerContact> contacts = contactMapper.selectByExample(contactExample);
		String id="";
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(contacts!=null && contacts.size()>0){
			id=contacts.get(0).getId();
			log.info("saveContactsInfo updateByExampleSelective contactId:"+id);
//			CustomerContactExample customerContactExample = new CustomerContactExample();
//			customerContactExample.createCriteria().andContactTypeEqualTo(contactType).andContactNameEqualTo(contactName);
			CustomerContact customerContact = contacts.get(0);
			customerContact.setContactType(contactType);
			customerContact.setContactName(contactName);
			contactMapper.updateByExampleSelective(customerContact, contactExample);
		}else{
			CustomerContact contact = new CustomerContact();
			contact.setUserOnlyId(Convert.toStr(dataMap.get("userOnlyId")));
			contact.setContactType(Convert.toStr(dataMap.get("contactType")));
			contact.setContactName(Convert.toStr(dataMap.get("contactName")));
			contact.setCreateTime(DateUtil.currTimeStr());
			contactMapper.insert(contact);
			id=contact.getId();
			log.info("saveContactsInfo insert contactId:"+id);
		}
		log.info("saveContactsInfo CustomerContact id"+id);
		CustomerPhoneExample phoneExample = new CustomerPhoneExample();
		phoneExample.createCriteria().andUserOnlyIdEqualTo(Convert.toStr(dataMap.get("userOnlyId")))
					.andCustomerTypeEqualTo(UhjConstant.customerType.contact);
		List<CustomerPhone> phones = phoneMapper.selectByExample(phoneExample);
		if(phones!=null && phones.size()>0){
//			CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
//			customerPhoneExample.createCriteria().andPhoneNoEqualTo(mobileNo);
			CustomerPhone phone = phones.get(0);
			phone.setPhoneNo(mobileNo.trim());
			phoneMapper.updateByExampleSelective(phone, phoneExample);
		}else{
			CustomerPhone phone=new CustomerPhone();
			phone.setContactId(id);
			phone.setUserOnlyId(userOnlyId);
			phone.setCustomerType(UhjConstant.customerType.contact);
			phone.setPhoneNo(mobileNo.trim());
			phone.setPhoneType(UhjConstant.phoneType.carryOn);
			phone.setPhoneNoType(UhjConstant.phoneNoType.mobile);
			phone.setPermanentFlag(UhjConstant.permanentFlag.yes);
			phone.setCreateTime(DateUtil.currTimeStr());
			phoneMapper.insert(phone);
		}
		//保存婚姻状况
		CustomerInfoExample customerInfoExample = new CustomerInfoExample();
		customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
		if(customerInfos!=null && customerInfos.size()>0){
			CustomerInfo info= customerInfos.get(0);
			info.setMaritalStatus(Convert.toBigDecimal(maritalStatus));
			customerInfoMapper.updateByExampleSelective(info, customerInfoExample);
		}
		resultMap.put("code", "0000");
		resultMap.put("msg", "保存成功");
		return resultMap;
	}
	
	/**
	 * 根据字段变量类型集合查询变量信息(参数为list)
	 */
	@Override
	public List<Dic> queryDicElementInfoByList(List<String> elementTypes){
		log.info("queryDicElementInfoByList param:"+elementTypes.toString());
		List<Dic> dics = new ArrayList<Dic>();
		if(elementTypes!=null){
			DicExample dicExample = new DicExample();
			dicExample.createCriteria().andElementTypeIn(elementTypes);
			dics = dicMapper.selectByExample(dicExample);
		}
		return dics;
	}

	/**
	 * 根据字段变量类型查询变量信息(参数为单元素)
	 */
	@Override
	public List<Dic> queryDicElementInfo(String elementType){
		log.info("queryDicElementInfo param:"+elementType);
		List<Dic> dics = new ArrayList<Dic>();
		if(!Check.isBlank(elementType)){
			DicExample dicExample = new DicExample();
			dicExample.createCriteria().andElementTypeEqualTo(elementType);
			dics = dicMapper.selectByExample(dicExample);
		}
		return dics;
	}
}