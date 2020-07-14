package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CustomerAccountMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerContactMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.CustomerPhoneMapper;
import com.ule.uhj.app.zgd.model.CustomerAccount;
import com.ule.uhj.app.zgd.model.CustomerAccountExample;
import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;
import com.ule.uhj.app.zgd.model.CustomerContact;
import com.ule.uhj.app.zgd.model.CustomerContactExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {
	
	private static Logger log = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private CustomerContactMapper customerContactMapper;
	@Autowired
	private CustomerPhoneMapper customerPhoneMapper;
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	 //营业执照店铺经营范围   商超类型关键字
    private final String [] shangchao={"百货","百货零售","副食","副食品","烟","卷烟零售","国产卷烟","雪茄烟","烟草制品","冷饮","零食","日用百货","日用品零售",
				"日用品批发零售","日杂百货","日杂用品","乳制品","文化用品","洗化用品","食品","散装食品","食品零售","预包装食品","批发零售","水果","蔬菜水果","猪肉零售",
				"冷鲜肉零售","蔬菜销售","饲料零售","化肥、农药","零售食品","日用品"};
	
	
	@Override
	public String saveCustomerInfo(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String userName=Convert.toStr(map.get("userName"));
		String orgCode=Convert.toStr(map.get("orgCode"));
		String channelCode=Convert.toStr(map.get("channelCode"),"C0001");
		String channelName=Convert.toStr(map.get("channelName"),"邮乐掌柜贷");
		String ruleOutputResult=Convert.toStr(map.get("ruleOutputResult"));
		//保存用户信息
		CustomerInfoExample infoExample = new CustomerInfoExample();
		infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(infoExample);
		if(customerInfos!=null&&customerInfos.size()>0){
			CustomerInfo info=customerInfos.get(0);
			info.setCustomerName(userName);
			info.setOrgCode(orgCode);
			info.setSalesChannel(ruleOutputResult);
			info.setChannelCode(channelCode);
			info.setChannelName(channelName);
			customerInfoMapper.updateByExampleSelective(info, infoExample);
		}else{
			CustomerInfo info =new CustomerInfo();
			info.setUserOnlyId(userOnlyId);
			info.setOrgCode(orgCode);;
			info.setCreateTime(DateUtil.currTimeStr());
			info.setCustomerName(userName);
			info.setChannelCode(channelCode);
			info.setChannelName(channelName);
			info.setSalesChannel(ruleOutputResult);
			customerInfoMapper.insertSelective(info);
		}
	
		return null;
	}
	@Override
	public Map<String, Object> queryCustomerInfo(Map<String, Object> map)
			throws Exception {
		Map<String, Object> para=new HashMap<String, Object>();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String certType=Convert.toStr(map.get("certType"));
		log.info("queryCustomerInfo param:"+map);
		List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
		CustomerInfoExample customerInfoExample = new CustomerInfoExample();
		customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
		if(customerInfos!= null && customerInfos.size()>0){
			para.put("name", customerInfos.get(0).getCustomerName());
			para.put("maritalStatus", customerInfos.get(0).getMaritalStatus());
			para.put("channelCode", customerInfos.get(0).getChannelCode());
			para.put("orgCode", customerInfos.get(0).getOrgCode());
		}
		
		if(!Check.isBlank(certType)){
			List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
			CustomerCertExample customerCertExample = new CustomerCertExample();
			customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(certType);
			customerCerts = customerCertMapper.selectByExample(customerCertExample);
			if(customerCerts!= null && customerCerts.size()>0){
				para.put("certNo", customerCerts.get(0).getCertNo());
				para.put("gender", customerCerts.get(0).getGender());
			}
				
		}
		
		List<CustomerPhone> customerPhones = new ArrayList<CustomerPhone>();
		CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
		customerPhoneExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCustomerTypeEqualTo(UhjConstant.customerType.loanor);
		customerPhones = customerPhoneMapper.selectByExample(customerPhoneExample);
		if(customerPhones!= null && customerPhones.size()>0)
			para.put("phone", customerPhones.get(0).getPhoneNo());
		
		List<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
		CustomerAccountExample customerAccountExample = new CustomerAccountExample();
		customerAccountExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.valid_useful);
		customerAccounts = customerAccountMapper.selectByExample(customerAccountExample);
		if(customerAccounts!= null && customerAccounts.size()>0)
			para.put("subbranchName", customerAccounts.get(0).getSubbranchName());
		log.info("queryCustomerInfo result:"+para.toString());
		return para;
	}
	
	@Override
	public Map<String, Object> queryCustomerSpouseInfo(Map<String, Object> map)
			throws Exception {
		Map<String, Object> para=new HashMap<String, Object>();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		log.info("queryCustomerCert param:"+map);
		List<CustomerContact> customerContacts = new ArrayList<CustomerContact>();
		CustomerContactExample customerContactExample = new CustomerContactExample();
		customerContactExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);//.andContactTypeEqualTo(contactType)
		customerContacts = customerContactMapper.selectByExample(customerContactExample);
		if(customerContacts!= null && customerContacts.size()>0){
			para.put("name", customerContacts.get(0).getContactName());
//			para.put("certNo", customerContacts.get(0).getContactCertNo());
			
			List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
			CustomerCertExample customerCertExample = new CustomerCertExample();
			customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.spouse_idcard);
			customerCerts = customerCertMapper.selectByExample(customerCertExample);
			if(customerCerts!= null && customerCerts.size()>0)
				para.put("certNo", customerCerts.get(0).getCertNo());
			
			List<CustomerPhone> customerPhones = new ArrayList<CustomerPhone>();
			CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
			customerPhoneExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andContactIdEqualTo(customerContacts.get(0).getId());
			customerPhones = customerPhoneMapper.selectByExample(customerPhoneExample);
			if(customerPhones!= null && customerPhones.size()>0)
				para.put("phone", customerPhones.get(0).getPhoneNo());
		}
		return para;
	}
	
	
	@Override
	public String getIsSuperKeyWord(String key)throws Exception {
		log.info(String.format("getIsSuperKeyWord key%s", key));
		String str="";
		try {
			Map<String,Object> par=new HashMap<String, Object>();
			par.put("elementKey", key);
			List<Map<String,Object>> o=customerInfoMapper.selectOraceContant(par);
			if(null!=o&&o.size()>0){
				for(Map<String,Object> m:o){
					String element_value=Convert.toStr(m.get("ELEMENT_VALUE"));//element_value
					log.info(String.format("getIsSuperKeyWord s数据库关键字%s", element_value));
					if(!Check.isBlank(element_value)){
						if(!Check.isBlank(str)){
							str=str+","+element_value;
						}else{
							str=element_value;
						}
					 
					}
				}
			}
			
		} catch (Exception e) {
			log.error("数据库常量查询失败",e);
			str="";
		}
		return str;
	}
}