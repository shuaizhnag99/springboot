package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.dao.ApplyDetailMapper;
import com.ule.uhj.app.zgd.dao.ApplyImageMapper;
import com.ule.uhj.app.zgd.dao.CreditPostmemberMapper;
import com.ule.uhj.app.zgd.dao.CustomerAccountMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerPhoneMapper;
import com.ule.uhj.app.zgd.dao.RepayPlanMapper;
import com.ule.uhj.app.zgd.dao.UserControlMapper;
import com.ule.uhj.app.zgd.dao.UserInfoMapper;
import com.ule.uhj.app.zgd.dao.ZgdWhiteMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.ApplyDetail;
import com.ule.uhj.app.zgd.model.ApplyDetailExample;
import com.ule.uhj.app.zgd.model.ApplyImage;
import com.ule.uhj.app.zgd.model.ApplyImageExample;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.app.zgd.model.CreditPostmemberExample;
import com.ule.uhj.app.zgd.model.CustomerAccount;
import com.ule.uhj.app.zgd.model.CustomerAccountExample;
import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;
import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;
import com.ule.uhj.app.zgd.model.RepayPlan;
import com.ule.uhj.app.zgd.model.RepayPlanExample;
import com.ule.uhj.app.zgd.model.UserControl;
import com.ule.uhj.app.zgd.model.UserControlExample;
import com.ule.uhj.app.zgd.model.UserInfo;
import com.ule.uhj.app.zgd.model.UserInfoExample;
import com.ule.uhj.app.zgd.model.ZgdWhite;
import com.ule.uhj.app.zgd.model.ZgdWhiteExample;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.DesUtil;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	private static Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private CustomerPhoneMapper customerPhoneMapper;
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	@Autowired
	private ApplyImageMapper applyImageMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserControlMapper userControlMapper;
	@Autowired
	private CreditPostmemberMapper creditPostmemberMapper;
	@Autowired
	private ZgdWhiteMapper zgdWhiteMapper;
	@Autowired
	private RepayPlanMapper repayPlanMapper;
	
	
	//查询四要素
	@Override
	public Map<String, Object> queryUserInfo(String userOnlyId)
			throws Exception {
		Map<String, Object> para=new HashMap<String, Object>();
		log.info("queryUserInfo userOnlyId:"+userOnlyId);
		
		List<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
		CustomerAccountExample customerAccountExample = new CustomerAccountExample();
		customerAccountExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.valid_useful);
		customerAccounts = customerAccountMapper.selectByExample(customerAccountExample);
		if(customerAccounts!= null && customerAccounts.size()>0){
			para.put("userName", customerAccounts.get(0).getAccountName());
			para.put("cardNo", customerAccounts.get(0).getAccountNo());
			para.put("phone", customerAccounts.get(0).getPhoneNo());
			
			List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
			CustomerCertExample customerCertExample = new CustomerCertExample();
			customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.idcard);
			customerCerts = customerCertMapper.selectByExample(customerCertExample);
			if(customerCerts!= null && customerCerts.size()>0){
				para.put("certNo", customerCerts.get(0).getCertNo());
				log.info("queryUserInfo result:"+para.toString());
				return para;
			}
		}else{
			UserInfoExample userInfoExample = new UserInfoExample();
			userInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
			if(userInfos!= null && userInfos.size()>0){
				para.put("userName", userInfos.get(0).getUserName());
				String certNo=userInfos.get(0).getCertNo();
				if(!Check.isBlank(certNo)){
					para.put("certNo", DesUtil.decrypt(certNo));
				}
				para.put("phone", userInfos.get(0).getMobileNo());
				
				List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
				AccountInfoExample accountInfoExample = new AccountInfoExample();
				accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
				if(accountInfos!= null && accountInfos.size()>0){
					String cardNo=accountInfos.get(0).getHoldingCardNo();
					if(!Check.isBlank(cardNo)){
						para.put("cardNo", DesUtil.decrypt(cardNo));
						log.info("queryUserInfo result:"+para.toString());
						return para;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public Map<String, Object> queryAccountInfo(String userOnlyId)
			throws Exception {
		Map<String, Object> para=new HashMap<String, Object>();
		log.info("queryAccountInfo userOnlyId:"+userOnlyId);
		List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
		AccountInfoExample accountInfoExample = new AccountInfoExample();
		accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
		if(accountInfos!= null && accountInfos.size()>0){
			para.put("loanOfficerName", accountInfos.get(0).getLoanOfficerName());
			para.put("loanOfficerOrg", accountInfos.get(0).getLoanOfficerOrg());
			para.put("loanOfficerPhone", accountInfos.get(0).getLoanOfficerPhone());
		}
		log.info("queryAccountInfo result:"+para.toString());
		return para;
	}
	
	// 更换手机号   userInfo   applyDetail     customerphone  customerAccount
	@Override
	public String updateUserPhone(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String phone=Convert.toStr(map.get("phone"));
		
		UserInfoExample infoExample = new UserInfoExample();
		infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(infoExample);
		if(userInfos!=null&&userInfos.size()>0){
			UserInfo info=userInfos.get(0);
			info.setMobileNo(phone);
			userInfoMapper.updateByExampleSelective(info, infoExample);
		}
		
		ApplyDetailExample applyDetailExample = new ApplyDetailExample();
		applyDetailExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<ApplyDetail> applyDetails = applyDetailMapper.selectByExample(applyDetailExample);
		if(applyDetails!=null&&applyDetails.size()>0){
			ApplyDetail info=applyDetails.get(0);
			info.setMobileNo(phone);
			applyDetailMapper.updateByExampleSelective(info, applyDetailExample);
		}
		
		CustomerAccountExample customerAccountExample = new CustomerAccountExample();
		customerAccountExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.valid_useful);
		List<CustomerAccount> customerAccounts = customerAccountMapper.selectByExample(customerAccountExample);
		if(customerAccounts!=null&&customerAccounts.size()>0){
			CustomerAccount info=customerAccounts.get(0);
			info.setPhoneNo(phone);
			customerAccountMapper.updateByExampleSelective(info, customerAccountExample);
		}
		
		CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
		customerPhoneExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCustomerTypeEqualTo(UhjConstant.customerType.loanor);
		List<CustomerPhone> customerPhones = customerPhoneMapper.selectByExample(customerPhoneExample);
		if(customerPhones!=null&&customerPhones.size()>0){
			CustomerPhone info=customerPhones.get(0);
			info.setPhoneNo(phone);
			customerPhoneMapper.updateByExampleSelective(info, customerPhoneExample);
		}
		return "SUCCESS";
	}
	
	
	@Override
	public Map<String,Object> queryBindCardInfo(Map<String, Object> dataMap)
			throws Exception {
		String userOnlyId=Convert.toStr(dataMap.get("userOnlyId"));
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		CustomerAccountExample accountExample = new CustomerAccountExample();
		accountExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.valid_useful);
		List<CustomerAccount> customerAccounts = customerAccountMapper.selectByExample(accountExample);
		if(customerAccounts!=null&&customerAccounts.size()>0){
			for(CustomerAccount acount:customerAccounts){
//				if(UhjConstant.CustomerAccountStatus.valid_useful.equals(acount.getValidDate())){
					resultMap.put("validCard", acount.getAccountNo());
					resultMap.put("userName", acount.getAccountName());
					resultMap.put("validPhone", acount.getPhoneNo());
//				}else if(UhjConstant.CustomerAccountStatus.invalid.equals(acount.getValidDate())){
//					resultMap.put("invalidCard", acount.getAccountNo());
//					resultMap.put("invalidPhone", acount.getPhoneNo());
//					resultMap.put("invalidStatus", acount.getValidDate());
//				}
			}
			CustomerAccountExample example = new CustomerAccountExample();
			example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.invalid);
			List<CustomerAccount> accounts = customerAccountMapper.selectByExample(example);
			if(accounts!=null&&accounts.size()>0){
				for(CustomerAccount acount:accounts){
					resultMap.put("invalidCard", acount.getAccountNo());
					resultMap.put("invalidPhone", acount.getPhoneNo());
					resultMap.put("invalidStatus", acount.getStatus());
				}
			}
		}else{  
			CustomerAccountExample example = new CustomerAccountExample();
			example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.CustomerAccountStatus.invalid);
			List<CustomerAccount> accounts = customerAccountMapper.selectByExample(example);
			if(accounts!=null&&accounts.size()>0){
				for(CustomerAccount acount:accounts){
					resultMap.put("invalidCard", acount.getAccountNo());
					resultMap.put("invalidPhone", acount.getPhoneNo());
					resultMap.put("invalidStatus", acount.getStatus());
				}
			}else{
				List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
				AccountInfoExample accountInfoExample = new AccountInfoExample();
				accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
				if(accountInfos!= null && accountInfos.size()>0){
					String cardNo=accountInfos.get(0).getHoldingCardNo();
					if(!Check.isBlank(cardNo)){
						resultMap.put("validCard", DesUtil.decrypt(cardNo));
					}
				}
				List<UserInfo> userInfos = new ArrayList<UserInfo>();
				UserInfoExample userInfoExample = new UserInfoExample();
				userInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				userInfos = userInfoMapper.selectByExample(userInfoExample);
				if(userInfos!= null && userInfos.size()>0){
					resultMap.put("userName", userInfos.get(0).getUserName());
					resultMap.put("validPhone", userInfos.get(0).getMobileNo());
				}
			}
		}
		ApplyImageExample ccExample =new ApplyImageExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andImageTypeEqualTo(UhjConstant.imageType.app_personalCenter);
		List<ApplyImage> applyImagelist=applyImageMapper.selectByExample(ccExample);
		if(applyImagelist!=null && applyImagelist.size()>0){
			resultMap.put("personalImage", applyImagelist.get(0).getImageUrl());
		}
		resultMap.put("code", "0000");
		resultMap.put("msg", "绑卡成功");
		return resultMap;
	}

	@Override
	public UserControl queryUserControl(String userOnlyId) throws Exception {
		UserControlExample ccExample =new UserControlExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<UserControl> userControllist=userControlMapper.selectByExample(ccExample);
		if(userControllist!=null && userControllist.size()>0){
			return userControllist.get(0);
		}
		return null;
	}

	@Override
	public boolean booleanUserYzsProvinceCity(String userOnlyId, String StaffId)
			throws Exception {
		log.info("booleanUserYzsProvinceCity 判断地推和掌柜是否同一个市 userOnlyId:"+userOnlyId+"StaffId:"+StaffId);
		CreditPostmemberExample ccExample =new CreditPostmemberExample();
		ccExample.createCriteria().andStaffIdEqualTo(StaffId).andOrgCityIsNotNull();
		List<CreditPostmember> creditPostmemberlist=creditPostmemberMapper.selectByExample(ccExample);
		if(creditPostmemberlist!=null && creditPostmemberlist.size()>0){
			CreditPostmember postmember =creditPostmemberlist.get(0);
			ZgdWhiteExample whiteExample =new ZgdWhiteExample();
			whiteExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<ZgdWhite> zgdWhitelist=zgdWhiteMapper.selectByExample(whiteExample);
			if(zgdWhitelist!=null && zgdWhitelist.size()>0){
				ZgdWhite white= zgdWhitelist.get(0);
				if(!Check.isBlank(white.getCityName()) && !Check.isBlank(white.getProvinceName())){
					String whiteCity=Convert.toStr(white.getCityName(),"").substring(0, 2);
					String postmemberCity=Convert.toStr(postmember.getOrgCity(),"").substring(0, 2);
					String whiteProvince=Convert.toStr(white.getProvinceName(),"").substring(0, 2);
					String postmemberProvince=Convert.toStr(postmember.getOrgProvince(),"").substring(0, 2);
					if(whiteCity.equals(postmemberCity) && whiteProvince.equals(postmemberProvince)){
						log.info("booleanUserYzsProvinceCity 判断地推和掌柜是否同一个市 userOnlyId:"+userOnlyId+"boolean:true");
						return true;
					}else{
						return false;
					}
				}
			}
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			String OrgProvinceName= Convert.toStr(vps.get(VpsInfoService.OrgProvinceName),"");
			String OrgCityName= Convert.toStr(vps.get(VpsInfoService.OrgCityName),"");
			String postmemberCity=Convert.toStr(postmember.getOrgCity(),"").substring(0, 2);
			String whiteProvince=OrgProvinceName.substring(0, 2);
			String postmemberProvince=Convert.toStr(postmember.getOrgProvince(),"").substring(0, 2);
			String whiteCity=OrgCityName.substring(0, 2);
			if(whiteCity.equals(postmemberCity) && whiteProvince.equals(postmemberProvince)){
				log.info("booleanUserYzsProvinceCity 判断地推和掌柜是否同一个市 userOnlyId:"+userOnlyId+"boolean:true");
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer queryOverDueDay(Map<String, Object> map) throws Exception {
		Integer OverDueDay=0;
		try{
			String userOnlyId="";
			String village_no=Convert.toStr(map.get("village_no"));
			ZgdWhiteExample whiteExample =new ZgdWhiteExample();
			whiteExample.createCriteria().andOrgCodeEqualTo(village_no);
			List<ZgdWhite> zgdWhitelist=zgdWhiteMapper.selectByExample(whiteExample);
			if(zgdWhitelist!=null && zgdWhitelist.size()>0){
				userOnlyId= zgdWhitelist.get(0).getUserOnlyId();
			}
			String accNo="200100"+ StringUtils.leftPad(userOnlyId, 20, "0");
			List<Short> list=new ArrayList<Short>();
			list.add((short)2);
			list.add((short)4);
			RepayPlanExample ccExample =new RepayPlanExample();
			ccExample.createCriteria().andAccNoEqualTo(accNo).andStatusIn(list);
			List<RepayPlan> RepayPlanlist=repayPlanMapper.selectByExample(ccExample);
			if(RepayPlanlist!=null && RepayPlanlist.size()>0){
				for(RepayPlan plan:RepayPlanlist){
					int day=DateUtil.diffDays(DateUtil.currDateStr(), plan.getPeriodEnd(), DateUtil.YMD);
					if(OverDueDay<day){
						OverDueDay=day;
					}
				}
			}
			return OverDueDay;
		}catch(Exception e){
			return OverDueDay;
		}
		
	}
}
