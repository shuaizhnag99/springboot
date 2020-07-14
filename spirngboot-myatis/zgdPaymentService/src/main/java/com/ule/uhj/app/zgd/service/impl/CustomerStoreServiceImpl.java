package com.ule.uhj.app.zgd.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.ApplyImageMapper;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CreditRuleMapper;
import com.ule.uhj.app.zgd.dao.CustomerAddressMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerIncomeMapper;
import com.ule.uhj.app.zgd.dao.CustomerStoreMapper;
import com.ule.uhj.app.zgd.model.ApplyImage;
import com.ule.uhj.app.zgd.model.ApplyImageExample;
import com.ule.uhj.app.zgd.model.CreditRule;
import com.ule.uhj.app.zgd.model.CreditRuleExample;
import com.ule.uhj.app.zgd.model.CustomerAddress;
import com.ule.uhj.app.zgd.model.CustomerAddressExample;
import com.ule.uhj.app.zgd.model.CustomerIncome;
import com.ule.uhj.app.zgd.model.CustomerIncomeExample;
import com.ule.uhj.app.zgd.model.CustomerStore;
import com.ule.uhj.app.zgd.model.CustomerStoreExample;
import com.ule.uhj.app.zgd.model.CustomerWhite;
import com.ule.uhj.app.zgd.service.CustomerStoreService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;

@Service
public class CustomerStoreServiceImpl implements CustomerStoreService {
	
	private static Logger log = LoggerFactory.getLogger(CustomerStoreServiceImpl.class);
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	@Autowired
	private CustomerStoreMapper customerStoreMapper;
	@Autowired
	private CustomerIncomeMapper customerIncomeMapper;
	@Autowired
	private ApplyImageMapper applyImageMapper;
	@Autowired
	private CreditRuleMapper creditRuleMapper;
	

	@Override
	public String saveCustomerStore(Map<String, Object> map) throws Exception {
		//店铺信息有可能被审核退回重新修改
//		String owner=Convert.toStr(map.get("owner"));//owner   店铺所有人(地址的用户类型)   1 是本人  2是配偶       地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断是本人的
//		String addressType=UhjConstant.addressType.store_address;//地址类型
//		String propertyType=Convert.toStr(map.get("propertyType"));//店铺房产类型  0是自有 1：租赁    地址表
//		String storeAddress=Convert.toStr(map.get("storeAddress"));//店铺地址     地址表
		
//		String storeInner=Convert.toStr(map.get("storeInner"));//店铺内部照片    已经保存早图片表了不用保存
//		String storeOutside=Convert.toStr(map.get("storeOutside"));//店铺外部照片   已经保存早图片表了不用保存
		log.info("saveCustomerStore map:"+map.toString());
		String incomeType=Convert.toStr(map.get("incomeType"));//收入类型     收入表
		String incomeSubType=Convert.toStr(map.get("incomeSubType"));//收入子类型    收入表
		String description=Convert.toStr(map.get("description"));//备注        收入表
		BigDecimal incomeAmount=Convert.toBigDecimal(map.get("incomeAmount"));//金额      收入表
		
		String storeInnerGPS=Convert.toStr(map.get("storeInnerGPS"));//店铺内部GPS定位信息    店铺表
		String storeOutsideGPS=Convert.toStr(map.get("storeOutsideGPS"));//店铺外部GPS定位信息     店铺表
		String storeName=Convert.toStr(map.get("storeName"));//店铺名称     店铺表     要和地址表关联地址ID
		BigDecimal storeArea=Convert.toBigDecimal(map.get("storeArea"));//店铺面积          店铺表
		BigDecimal storeTurnover=Convert.toBigDecimal(map.get("storeTurnover"));//店铺营业额（每日）   店铺表
		BigDecimal rent=Convert.toBigDecimal(map.get("rent"));//租赁金额      店铺表
		String businessLicense=Convert.toStr(map.get("businessLicense"));//营业执照   店铺表
		
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String addressId=Convert.toStr(map.get("addressId"));//店铺地址     地址表
		
		String storeType = Convert.toStr(map.get("storeType"));//店铺类型 0:零售，1:批销
		
		String mainBusiness = Convert.toStr(map.get("mainBusiness"));//从商家组获取的掌柜店铺营业类型转化,没有的让掌柜自己选择（参照StoreBusinessType.java）
		
		CustomerIncomeExample customerIncomeExample =new CustomerIncomeExample();
		customerIncomeExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerIncome> customerIncomeList=customerIncomeMapper.selectByExample(customerIncomeExample);
		if(customerIncomeList==null || customerIncomeList.size()<=0){
			CustomerIncome customerIncome =new CustomerIncome();
			customerIncome.setCreateTime(DateUtil.currTimeStr());
			customerIncome.setDescription(description);
			customerIncome.setIncomeAmount(incomeAmount);
//			customerIncome.setIncomeAmountTerm(incomeAmountTerm);
			customerIncome.setIncomeSubType(incomeSubType);
			customerIncome.setIncomeType(incomeType);
			customerIncome.setUserOnlyId(userOnlyId);
			customerIncomeMapper.insertSelective(customerIncome);
		}else{
			CustomerIncome customerIncome =customerIncomeList.get(0);
			if(!Check.isBlank(description)){
				customerIncome.setDescription(description);
			}
			if(!Check.isBlank(incomeAmount)){
				customerIncome.setIncomeAmount(incomeAmount);;
			}
//			customerIncome.setIncomeAmountTerm(incomeAmountTerm);
			if(!Check.isBlank(incomeSubType)){
				customerIncome.setIncomeSubType(incomeSubType);
			}
			if(!Check.isBlank(incomeType)){
				customerIncome.setIncomeType(incomeType);
			}
			customerIncome.setUserOnlyId(userOnlyId);
			customerIncome.setUpdateTime(DateUtil.currTimeStr());
			customerIncomeMapper.updateByExampleSelective(customerIncome, customerIncomeExample);
		}
		
		
		
		CustomerStoreExample customerStoreExample =new CustomerStoreExample();
		customerStoreExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerStore> customerStoreList=customerStoreMapper.selectByExample(customerStoreExample);
		if(customerStoreList==null || customerStoreList.size()<=0){
			CustomerStore customerStore =new CustomerStore();
			customerStore.setCreateTime(DateUtil.currTimeStr());
			customerStore.setAddressId(addressId);
			customerStore.setBusinessLicenceNo(businessLicense);
			customerStore.setLeasingAmount(rent);
			customerStore.setStoreArea(storeArea);
			customerStore.setStoreInnerGps(storeInnerGPS);
			customerStore.setStoreName(storeName);
			customerStore.setStoreOuterGps(storeOutsideGPS);
			customerStore.setStoreTurnover(storeTurnover);
			customerStore.setUserOnlyId(userOnlyId);
			customerStore.setStoreType(storeType);
			customerStore.setMainBusiness(mainBusiness);
			customerStoreMapper.insertSelective(customerStore);
		}else{
			CustomerStore customerStore =customerStoreList.get(0);
			customerStore.setUpdateTime(DateUtil.currTimeStr());
//			customerStore.setAddressId(addressId);
			if(!Check.isBlank(businessLicense)){
				customerStore.setBusinessLicenceNo(businessLicense);
			}
			if(!Check.isBlank(rent)){
				customerStore.setLeasingAmount(rent);
			}
			if(!Check.isBlank(storeArea)){
				customerStore.setStoreArea(storeArea);
			}
			if(!Check.isBlank(storeInnerGPS)){
				customerStore.setStoreInnerGps(storeInnerGPS);
			}
			if(!Check.isBlank(storeName)){
				customerStore.setStoreName(storeName);
			}
			if(!Check.isBlank(storeOutsideGPS)){
				customerStore.setStoreOuterGps(storeOutsideGPS);
			}
			if(!Check.isBlank(storeTurnover)){
				customerStore.setStoreTurnover(storeTurnover);
			}
			if(!Check.isBlank(storeType)){
				customerStore.setStoreType(storeType);
			}
			if(!Check.isBlank(mainBusiness)){
				customerStore.setMainBusiness(mainBusiness);
			}
			customerStore.setUserOnlyId(userOnlyId);
			customerStoreMapper.updateByExampleSelective(customerStore, customerStoreExample);
		}
		return JsonResult.getInstance().addOk().toString();
	}



	@Override
	public Map<String, Object> queryCustomerStore(Map<String, Object> map) throws Exception {
		Map<String, Object> result=new HashMap<String, Object>();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String MainBusiness1=Convert.toStr(map.get("MainBusiness1"));
		CustomerIncomeExample customerIncomeExample =new CustomerIncomeExample();
		customerIncomeExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerIncome> customerIncomeList=customerIncomeMapper.selectByExample(customerIncomeExample);
		if(customerIncomeList!=null && customerIncomeList.size()>0){
			CustomerIncome income=customerIncomeList.get(0);
			String incomeType=income.getIncomeType();//收入类型     收入表
			String incomeSubType=income.getIncomeSubType();//收入子类型    收入表
			String description=income.getDescription();//备注        收入表
			BigDecimal incomeAmount=income.getIncomeAmount();//金额      收入表
			result.put("incomeType", incomeType);
			result.put("incomeSubType", incomeSubType);
			result.put("description", description);
			result.put("incomeAmount", incomeAmount);
		}
		
		CustomerAddressExample customerAddressExample =new CustomerAddressExample();
		customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(UhjConstant.addressType.store_address);
		List<CustomerAddress> customerAddressList=customerAddressMapper.selectByExample(customerAddressExample);
		if(customerAddressList!=null && customerAddressList.size()>0){
			CustomerAddress address=customerAddressList.get(0);
			String owner=address.getCustomerType();//owner   店铺所有人(地址的用户类型)   1 是本人  2是配偶       地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断是本人的
			String propertyType=address.getPropertyType();//店铺房产类型  0是自有 1：租赁    地址表
			String storeAddress=address.getAddress();//店铺地址     地址表
			result.put("owner", owner);
			result.put("propertyType", propertyType);
			result.put("storeAddress", storeAddress);
		}else{
			result.put("owner", UhjConstant.customerType.loanor);
		}
		
		CustomerStoreExample customerStoreExample =new CustomerStoreExample();
		customerStoreExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerStore> customerStoreList=customerStoreMapper.selectByExample(customerStoreExample);
		if(customerStoreList!=null && customerStoreList.size()>0){
			CustomerStore store=customerStoreList.get(0);
			String storeInnerGPS=store.getStoreInnerGps();//店铺内部GPS定位信息    店铺表
			String storeOutsideGPS=store.getStoreOuterGps();//店铺外部GPS定位信息     店铺表
			String storeName=store.getStoreName();//店铺名称     店铺表     要和地址表关联地址ID
			BigDecimal storeArea=store.getStoreArea();//店铺面积          店铺表
			BigDecimal storeTurnover=store.getStoreTurnover();//店铺营业额（每日）   店铺表
			BigDecimal rent=store.getLeasingAmount();//租赁金额      店铺表
			String businessLicense=store.getBusinessLicenceNo();//营业执照   证件表
			String mainBusiness=store.getMainBusiness();
			result.put("mainBusiness", mainBusiness);
			if(Check.isBlank(mainBusiness)){
				result.put("mainBusiness", MainBusiness1);
			}
			result.put("storeInnerGPS", storeInnerGPS);
			result.put("storeOutsideGPS", storeOutsideGPS);
			result.put("storeName", storeName);
			result.put("storeArea", storeArea);
			result.put("storeTurnover", storeTurnover);
			result.put("rent", rent);
			result.put("businessLicense", businessLicense);
			
			if(!Check.isBlank(businessLicense)){
				CreditRuleExample ruleExample =new CreditRuleExample();
				ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.business_license);
				List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
				if(rulelist!=null && rulelist.size()>0){
					CreditRule rule=rulelist.get(0);
					String businessFlag=rule.getRuleOutput();
					result.put("businessFlag", businessFlag);//businessFlag    为true时     营业执照编号不可修改
				}
			}
		}else{//店铺信息没有值判断是否格力专享，是的话带出白名单的值
			CustomerWhite customerWhite=(CustomerWhite) map.get("CustomerWhite");
			String userName=Convert.toStr(map.get("userName"));
			if(customerWhite!=null){
				String ret=checkBusinessLicense(userName, customerWhite.getBusinessLicenseNo(), userOnlyId);
				if("0000".equals(ret)){
					Map<String, Object> retMap=getEnterpriseInformation(customerWhite.getBusinessLicenseNo(), userOnlyId);
					result.put("storeName", Convert.toStr(retMap.get("storeName")));
					result.put("storeAddress", Convert.toStr(retMap.get("storeAddress")));
					result.put("businessLicense", customerWhite.getBusinessLicenseNo());
					result.put("businessFlag", "true");
					result.put("owner", UhjConstant.customerType.loanor);
				}
			}
			result.put("mainBusiness", MainBusiness1);
		}
		
		ApplyImageExample ccExample =new ApplyImageExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<ApplyImage> applyImagelist=applyImageMapper.selectByExample(ccExample);
		if(applyImagelist!=null && applyImagelist.size()>0){
			for(ApplyImage image:applyImagelist){
				if(UhjConstant.imageType.app_storeInner.equals(image.getImageType())){
					String storeInner=image.getImageUrl();//店铺内部照片   
					String innerFlag="false";
					CreditRuleExample creditRuleExample =new CreditRuleExample();
					creditRuleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.store_inside_photos);
					List<CreditRule> creditRulelist=creditRuleMapper.selectByExample(creditRuleExample);
					if(creditRulelist!=null && creditRulelist.size()>0){
						CreditRule rule=creditRulelist.get(0);
						innerFlag=rule.getRuleOutput();
					}
					CreditRuleExample ruleExample =new CreditRuleExample();
					ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.store_inside_reason);
					List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
					if(rulelist!=null && rulelist.size()>0){
						CreditRule rule=rulelist.get(0);
						String innerReason=rule.getRuleOutput();
						result.put("innerReason", innerReason);
					}
					result.put("storeInner", storeInner);
					result.put("innerFlag", innerFlag);
				}else if(UhjConstant.imageType.app_storeOutside.equals(image.getImageType())){
					String storeOutside=image.getImageUrl();//店铺外部照片 
					String outsideFlag="false";
					CreditRuleExample creditRuleExample =new CreditRuleExample();
					creditRuleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.store_outside_photos);
					List<CreditRule> creditRulelist=creditRuleMapper.selectByExample(creditRuleExample);
					if(creditRulelist!=null && creditRulelist.size()>0){
						CreditRule rule=creditRulelist.get(0);
						outsideFlag=rule.getRuleOutput();
					}
					CreditRuleExample ruleExample =new CreditRuleExample();
					ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.store_outside_reason);
					List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
					if(rulelist!=null && rulelist.size()>0){
						CreditRule rule=rulelist.get(0);
						String outsideReason=rule.getRuleOutput();
						result.put("outsideReason", outsideReason);
					}
					result.put("outsideFlag", outsideFlag);
					result.put("storeOutside", storeOutside);
				}
			}
		}
		return result;
	}
	
	private String checkBusinessLicense(String name,String businessLicense,String userOnlyId) {
		try{
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			params.put("tranzCode", "4104");
			params.put("keyWord", businessLicense);
			params.put("userOnlyId", userOnlyId);
			String	ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			log.info("getEnterpriseInformation userOnlyId:"+userOnlyId+";ret"+ret);
			JSONObject js= JSONObject.fromObject(ret);
			String ststus=Convert.toStr(js.get("Status"));
			if("200".equals(ststus)){
				String array= js.getString("Result"); //获取list的值 

			    JSONArray jsonArray = JSONArray.fromObject(array); //吧list的值转为json数组对象 

			    Object[] strs = jsonArray.toArray(); //json转为数组 
			    for(Object object :strs){
			    	JSONObject objs=JSONObject.fromObject(object);
			    	String OperName=Convert.toStr(objs.get("OperName"));
			    	String Status=Convert.toStr(objs.get("Status"));
			    	String StartDate=Convert.toStr(objs.get("StartDate"));
					if(name.equals(OperName)){
						//不包含存续  在业 在营、开业、在册 的提示用户
						if(Check.isBlank(Status) || 
							(Status.indexOf("存续")<0 && Status.indexOf("在业")<0
								&& Status.indexOf("在营")<0
								&& Status.indexOf("开业")<0
								&& Status.indexOf("在册")<0)){
							return "2000"; 
						}
						if(!Check.isBlank(StartDate) && StartDate.length()>10){
							StartDate=StartDate.substring(0, 10);
							if(DateUtil.diffDays(DateUtil.currDateStr(), StartDate, DateUtil.YMD)<365){
								return "3000"; 
							}
						}
						return "0000";
					}
			    }
			}
		}catch(Exception e){
			log.info("checkBusinessLicense  error",e);
		}
		return "1000";

	}
	private Map<String, Object> getEnterpriseInformation(String businessLicense,String userOnlyId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			params.put("tranzCode", "4103");
			params.put("keyWord", businessLicense);
			params.put("userOnlyId", userOnlyId);
			String	ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			log.info("getEnterpriseInformation userOnlyId:"+userOnlyId+";ret"+ret);
			JSONObject js= JSONObject.fromObject(ret);
			JSONObject result = JSONObject.fromObject(js.get("Result"));
			map.put("storeName", result.get("Name"));
			map.put("storeAddress", result.get("Address"));
		} catch (Exception e) {
			log.error("getEnterpriseInformation error",e);
		}
		return map;
		
	}
}
