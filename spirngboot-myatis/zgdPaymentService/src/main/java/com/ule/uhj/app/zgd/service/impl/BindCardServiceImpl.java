package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.dao.ApplyImageMapper;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CreditCardSmsMapper;
import com.ule.uhj.app.zgd.dao.CustomerAccountMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.CustomerPhoneMapper;
import com.ule.uhj.app.zgd.dao.UserInfoMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.CreditCardSms;
import com.ule.uhj.app.zgd.model.CreditCardSmsExample;
import com.ule.uhj.app.zgd.model.CustomerAccount;
import com.ule.uhj.app.zgd.model.CustomerAccountExample;
import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;
import com.ule.uhj.app.zgd.model.UserInfo;
import com.ule.uhj.app.zgd.model.UserInfoExample;
import com.ule.uhj.app.zgd.service.BindCardService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.DesUtil;

@Service("bindCardService")
public class BindCardServiceImpl implements BindCardService{
	private static Logger log = LoggerFactory.getLogger(BindCardServiceImpl.class);

	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
	@Autowired
	private CustomerCertMapper customerCertMapper;
	
	@Autowired
	private CustomerPhoneMapper customerPhoneMapper;
	
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private CreditCardSmsMapper creditCardSmsMapper;
	@Autowired
	private ApplyImageMapper applyImageMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	
	@Override
	public Map<String, Object> queryCustomerBasicInfo(String userOnlyId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("queryCustomerBasicInfo userOnlyId:"+userOnlyId);
		CustomerInfoExample infoExample = new CustomerInfoExample();
		infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(infoExample);
		if(customerInfos!=null && customerInfos.size()>0){
			resultMap.put("userName", customerInfos.get(0).getCustomerName());
			
			CustomerCertExample certExample = new CustomerCertExample();
			certExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.idcard);
			List<CustomerCert> certs = customerCertMapper.selectByExample(certExample);
			if(certs!=null && certs.size()>0){
				resultMap.put("certNo", certs.get(0).getCertNo());
			}
			CreditApplyExample applyExample = new CreditApplyExample();
			applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
			if(applys!=null && applys.size()>0){
				resultMap.put("appId", applys.get(0).getId());
			}
		}else{
			UserInfoExample userInfoExample = new UserInfoExample();
			userInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
			if(userInfos!= null && userInfos.size()>0){
				resultMap.put("userName", userInfos.get(0).getUserName());
				String certNo=userInfos.get(0).getCertNo();
				if(!Check.isBlank(certNo)){
					resultMap.put("certNo", DesUtil.decrypt(certNo));
				}
				resultMap.put("phone", userInfos.get(0).getMobileNo());
				
				List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
				AccountInfoExample accountInfoExample = new AccountInfoExample();
				accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
				if(accountInfos!= null && accountInfos.size()>0){
					String cardNo=accountInfos.get(0).getHoldingCardNo();
					if(!Check.isBlank(cardNo)){
						resultMap.put("cardNo", DesUtil.decrypt(cardNo));
						log.info("queryUserInfo result:"+resultMap.toString());
					}
				}
			}
		}
		return resultMap;
	}

	/**
	 * 确认绑卡，保存信息
	 */
	@Override
	public Map<String,Object> saveBindCardInfo(Map<String, Object> dataMap)
			throws Exception {
		String subbranchName=Convert.toStr(dataMap.get("subbranchName"));
		String userOnlyId=Convert.toStr(dataMap.get("userOnlyId"));
		String cardNo=Convert.toStr(dataMap.get("cardNo"));
		String userName=Convert.toStr(dataMap.get("userName"));
		String mobileNo=Convert.toStr(dataMap.get("mobileNo"));
		String source=Convert.toStr(dataMap.get("source"));
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(source==null || !"personalCenter".equals(source)){
			// 保存银行卡信息
			CustomerAccountExample accountExample = new CustomerAccountExample();
			accountExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.valid_useful);
			List<CustomerAccount> customerAccounts = customerAccountMapper.selectByExample(accountExample);
			if(customerAccounts!=null&&customerAccounts.size()>0){
				CustomerAccount account =customerAccounts.get(0);
				account.setAccountNo(cardNo);
				account.setPhoneNo(mobileNo);
				account.setAccountName(userName);
				if(!Check.isBlank(subbranchName)){
					account.setSubbranchName(subbranchName);
				}
				customerAccountMapper.updateByExampleSelective(account, accountExample);
			}else{
				CustomerAccount account = new CustomerAccount();
				account.setAccountName(userName);
				account.setAccountNo(cardNo);
				account.setPhoneNo(mobileNo);
				account.setUserOnlyId(userOnlyId);
				if(!Check.isBlank(subbranchName)){
					account.setSubbranchName(subbranchName);
				}
				account.setStatus(UhjConstant.CustomerAccountStatus.valid_useful);
				customerAccountMapper.insert(account);
			}

			CustomerPhoneExample phoneExample = new CustomerPhoneExample();
			phoneExample.createCriteria().andUserOnlyIdEqualTo(Convert.toStr(dataMap.get("userOnlyId")))
					.andCustomerTypeEqualTo(UhjConstant.customerType.loanor);
			List<CustomerPhone> phones = customerPhoneMapper.selectByExample(phoneExample);
			if(phones!=null && phones.size()>0){
				CustomerPhone phone =phones.get(0);
				phone.setUserOnlyId(Convert.toStr(dataMap.get("userOnlyId")));
				phone.setCustomerType(UhjConstant.customerType.loanor);
				phone.setPhoneNo(Convert.toStr(dataMap.get("mobileNo")));
				phone.setPhoneType(UhjConstant.phoneType.carryOn);
				phone.setPhoneNoType(UhjConstant.phoneNoType.mobile);
				phone.setPermanentFlag(UhjConstant.permanentFlag.yes);
				phone.setUpdateTime(DateUtil.currTimeStr());
				CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
				customerPhoneExample.createCriteria().andUserOnlyIdEqualTo(Convert.toStr(dataMap.get("userOnlyId")))
										.andCustomerTypeEqualTo(UhjConstant.customerType.loanor);
				customerPhoneMapper.updateByExampleSelective(phone, phoneExample);
			}else{
				CustomerPhone phone=new CustomerPhone();
				phone.setUserOnlyId(Convert.toStr(dataMap.get("userOnlyId")));
				phone.setCustomerType(UhjConstant.customerType.loanor);
				phone.setPhoneNo(Convert.toStr(dataMap.get("mobileNo")));
				phone.setPhoneType(UhjConstant.phoneType.carryOn);
				phone.setPhoneNoType(UhjConstant.phoneNoType.mobile);
				phone.setPermanentFlag(UhjConstant.permanentFlag.yes);
				phone.setCreateTime(DateUtil.currTimeStr());
				customerPhoneMapper.insert(phone);
			}
		}else{
			CustomerAccountExample accountExample = new CustomerAccountExample();
			accountExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.invalid);
			List<CustomerAccount> customerAccounts = customerAccountMapper.selectByExample(accountExample);
			if(customerAccounts!=null&&customerAccounts.size()>0){
				CustomerAccount account =customerAccounts.get(0);
				account.setAccountNo(cardNo);
				account.setAccountName(userName);
				account.setPhoneNo(mobileNo);
				account.setStatus(UhjConstant.CustomerAccountStatus.invalid);
				customerAccountMapper.updateByExampleSelective(account, accountExample);
			}else{
				CustomerAccount account = new CustomerAccount();
				CustomerAccountExample example = new CustomerAccountExample();
				example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.valid_useful);
				List<CustomerAccount> accounts = customerAccountMapper.selectByExample(example);
				if(accounts!=null&&accounts.size()>0){
					account.setSubbranchName(accounts.get(0).getSubbranchName());
				}
				account.setAccountName(userName);
				account.setAccountNo(cardNo);
				account.setPhoneNo(mobileNo);
				account.setUserOnlyId(userOnlyId);
				account.setStatus(UhjConstant.CustomerAccountStatus.invalid);
				customerAccountMapper.insert(account);
			}
		}
		resultMap.put("code", "0000");
		resultMap.put("msg", "绑卡成功");
		return resultMap;
	}
	
	/**
	 * 确认绑卡，保存信息
	 */
	@Override
	public String saveSmsContent(Map<String, Object> dataMap)
			throws Exception {
		String imei  =Convert.toStr(dataMap.get("imei"));
		String phoneModel =Convert.toStr(dataMap.get("phoneModel"));
		String successFlag  =Convert.toStr(dataMap.get("successFlag"));
		String sendPhoneNo  =Convert.toStr(dataMap.get("sendPhoneNo"));
		String smsContent  =Convert.toStr(dataMap.get("smsContent"));
		String userOnlyId  =Convert.toStr(dataMap.get("userOnlyId")); 
		//获取申请的ID
		String appId="";
		CreditApplyExample caExample =new CreditApplyExample();
		caExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> calist=creditApplyMapper.selectByExample(caExample);
		if(calist!=null && calist.size()>0){
			appId=calist.get(0).getId();
		}
		
		// 保存银行卡信息
		CreditCardSmsExample creditCardSmssExample = new CreditCardSmsExample();
		creditCardSmssExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditCardSms> creditCardSmsList = creditCardSmsMapper.selectByExample(creditCardSmssExample);
		if(creditCardSmsList!=null&&creditCardSmsList.size()>0){
			CreditCardSms creditCardSms =creditCardSmsList.get(0);
			creditCardSms.setAppId(appId);
			creditCardSms.setUpdateTime(DateUtil.currTimeStr());
			creditCardSms.setImei(imei);
			creditCardSms.setPhoneModel(phoneModel);
			creditCardSms.setSendPhoneNo(sendPhoneNo);
			creditCardSms.setSmsContent(smsContent);
			creditCardSms.setSuccessFlag(successFlag);
			creditCardSms.setUserOnlyId(userOnlyId);
			creditCardSms.setUpdateTime(DateUtil.currTimeStr());
			creditCardSmsMapper.updateByExampleSelective(creditCardSms, creditCardSmssExample);
		}else{
			CreditCardSms creditCardSms = new CreditCardSms();
			creditCardSms.setUserOnlyId(userOnlyId);
			creditCardSms.setAppId(appId);
			creditCardSms.setCreateTime(DateUtil.currTimeStr());
			creditCardSms.setImei(imei);
			creditCardSms.setPhoneModel(phoneModel);
			creditCardSms.setSendPhoneNo(sendPhoneNo);
			creditCardSms.setSmsContent(smsContent);
			creditCardSms.setSuccessFlag(successFlag);
			creditCardSmsMapper.insert(creditCardSms);
		}
		return "Success";
	}

	@Override
	public void saveCustomerCertInfo(Map<String, Object> dataMap) throws Exception {
		//String subbranchName=Convert.toStr(dataMap.get("subbranchName"));
		String userOnlyId=Convert.toStr(dataMap.get("userOnlyId"));
		String certNo=Convert.toStr(dataMap.get("certNo"));
		String userName=Convert.toStr(dataMap.get("userName"));
		//String mobileNo=Convert.toStr(dataMap.get("mobileNo"));
		//String source=Convert.toStr(dataMap.get("source"));
		
		// 更改customerInfo
		CustomerInfoExample infoExample = new CustomerInfoExample();
		infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerInfo> customerList = customerInfoMapper.selectByExample(infoExample);
		if(!CollectionUtils.isEmpty(customerList)) {
			CustomerInfo info = customerList.get(0);
			info.setCustomerName(userName);
			customerInfoMapper.updateByPrimaryKey(info);
		}
		// 更新customerCert
		CustomerCertExample certExample = new CustomerCertExample();
		certExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerCert> certList = customerCertMapper.selectByExample(certExample);
		if(!CollectionUtils.isEmpty(certList)) {
			CustomerCert cert = certList.get(0);
			cert.setCertNo(certNo);
			customerCertMapper.updateByPrimaryKey(cert);
		}
	}
	

}
