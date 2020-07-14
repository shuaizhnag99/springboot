package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CreditRuleMapper;
import com.ule.uhj.app.zgd.dao.CustomerAddressMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.CreditRule;
import com.ule.uhj.app.zgd.model.CreditRuleExample;
import com.ule.uhj.app.zgd.model.CustomerAddress;
import com.ule.uhj.app.zgd.model.CustomerAddressExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;

@Service
public class CreditRuleServiceImpl implements CreditRuleService {
	
	private static Logger log = LoggerFactory.getLogger(CreditRuleServiceImpl.class);
	@Autowired
	private CreditRuleMapper creditRuleMapper;
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	
	private final String ruleRefIds = "APPCHECK_001,APPCHECK_002,APPCHECK_003,APPCHECK_004,APPCHECK_005,APPCHECK_006";// 去掉运营商

	@Override
	public String saveCreditRuleService(Map<String, Object> map) throws Exception {
		String returnFlag = "";
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String ruleRefId=Convert.toStr(map.get("ruleRefId"));
		String ruleOutput=Convert.toStr(map.get("ruleOutput"));
		String ruleType=Convert.toStr(map.get("ruleType"));
		log.info("saveCreditRuleService userOnlyId:"+userOnlyId+";ruleRefId:"+ruleRefId+";ruleOutput:"+ruleOutput);
		
		//获取申请的ID
		String appId="";
		CreditApplyExample caExample =new CreditApplyExample();
		caExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> calist=creditApplyMapper.selectByExample(caExample);
		if(calist!=null && calist.size()>0){
			appId=calist.get(0).getId();
		}
		if(Check.isBlank(appId)){
			appId=userOnlyId;
		}
		//规则
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(ruleRefId).andAppIdEqualTo(appId);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist==null || cclist.size()<=0){
			CreditRule cc =new CreditRule();
			if(UhjConstant.ruleRefId.face_compare.equals(ruleRefId) && "false".equals(ruleOutput)){//如果是人脸对比失败,输出规则是失败次数
				cc.setRuleOutput("1");
			}else{
				cc.setRuleOutput(ruleOutput);
			}
			
			if(UhjConstant.ruleRefId.certno_name_matches.equals(ruleRefId) && "false".equals(ruleOutput)){//如果是身份证号与姓名不匹配,输出规则是失败次数
				cc.setRuleOutput("1");
			}else{
				cc.setRuleOutput(ruleOutput);
			}
			
			if(UhjConstant.ruleRefId.cert_photos.equals(ruleRefId) && "false".equals(ruleOutput)){//如果是身份证地址解析失败,输出规则是失败次数
				cc.setRuleOutput("1");
			}else{
				cc.setRuleOutput(ruleOutput);
			}
			
			cc.setAppId(appId);
			if(Check.isBlank(ruleType)){
				cc.setRuleType(UhjConstant.ruleType.app_rule_type);
			}else{
				cc.setRuleType(ruleType);
			}
			cc.setRuleRefId(ruleRefId);
			cc.setUserOnlyId(userOnlyId);
			cc.setCreateTime(DateUtil.currTimeStr());
			cc.setUpdateTime(DateUtil.currTimeStr());
			creditRuleMapper.insertSelective(cc);
			
		}else{
			CreditRule cc =cclist.get(0);
			if(UhjConstant.ruleRefId.face_compare.equals(ruleRefId) && "false".equals(ruleOutput)){//如果是人脸对比失败,输出规则是失败次数
				int count=1;
				if(cc.getUpdateTime().substring(0, 10).equals(DateUtil.currDateStr())){//如果修改的时间是当天，则输出次数加1，否则输出次数为当天的第一次
					String out=Convert.toStr(cc.getRuleOutput());
					if(!"true".equals(out)){
						count= Convert.toInt(cc.getRuleOutput(),0);
						count=count+1;
					}
					cc.setRuleOutput(Convert.toStr(count));
					if(count>=3){//人脸对比失败3次，返回前台，默认通过
						returnFlag="1";
					}
				}else{
					cc.setRuleOutput(Convert.toStr("1"));
				}
			}else if(UhjConstant.ruleRefId.certno_name_matches.equals(ruleRefId) && "false".equals(ruleOutput)){//如果是身份证号与姓名不匹配,输出规则是失败次数
				int count=1;
				if(cc.getUpdateTime().substring(0, 10).equals(DateUtil.currDateStr())){
					String out=Convert.toStr(cc.getRuleOutput());
					if(!"true".equals(out)){
						count= Convert.toInt(cc.getRuleOutput(),0);
						count=count+1;
					}
					cc.setRuleOutput(Convert.toStr(count));
//					if(count>=3){
//						returnFlag="1";
//					}
				}else{
					cc.setRuleOutput(Convert.toStr("1"));
				}
			}else if(UhjConstant.ruleRefId.cert_photos.equals(ruleRefId) && "false".equals(ruleOutput)){//如果是身份证识别地址失败,输出规则是失败次数
				int count=1;
				if(cc.getUpdateTime().substring(0, 10).equals(DateUtil.currDateStr())){
					String out=Convert.toStr(cc.getRuleOutput());
					if(!"true".equals(out)){
						count= Convert.toInt(cc.getRuleOutput(),0);
						count=count+1;
					}
					cc.setRuleOutput(Convert.toStr(count));
					if(count>=3){
						returnFlag="1";
					}
				}else{
					cc.setRuleOutput(Convert.toStr("1"));
				}
			}else{
				cc.setRuleOutput(ruleOutput);
			}
			cc.setAppId(appId);
//			cc.setRuleType("6");
			cc.setRuleRefId(ruleRefId);
			cc.setUpdateTime(DateUtil.currTimeStr());
			cc.setUserOnlyId(userOnlyId);
			creditRuleMapper.updateByExampleSelective(cc, ccExample);
		}
		return returnFlag;
	}


//取每天刷脸时身份证和姓名不匹配的次数
	@Override
	public String queryCreditRuleService(Map<String, Object> map)
			throws Exception {
		String ruleOutput="0";
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String ruleRefId=Convert.toStr(map.get("ruleRefId"));
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(ruleRefId);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			String date=cclist.get(0).getUpdateTime();
			if(date!=null && date.length()>=10 && DateUtil.diffDays(DateUtil.currDateStr(), date.substring(0, 10), DateUtil.YMD)==0){
				ruleOutput=cclist.get(0).getRuleOutput();
			}
			
		}
		return ruleOutput;
	}
	@Override
	public String queryCreditRuleRuleOutput(Map<String, Object> map)
			throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String ruleRefId=Convert.toStr(map.get("ruleRefId"));
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(ruleRefId);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			return cclist.get(0).getRuleOutput();
		}
		return null;
	}
	
	@Override
	public CreditRule queryCreditRuleType(Map<String, Object> map)
			throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String ruleType=Convert.toStr(map.get("ruleType"));
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleTypeEqualTo(ruleType);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			return cclist.get(0);
		}
		return null;
	}

	@Override
	public String queryCreditRuleToPage(Map<String, Object> map)throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		log.info("queryCreditRuleToPage userOnlyId:"+userOnlyId);
		String ruleRefId="";
		//获取申请的ID
		String appId="";
		CreditApplyExample caExample =new CreditApplyExample();
		caExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> calist=creditApplyMapper.selectByExample(caExample);
		if(calist!=null && calist.size()>0){
			appId=calist.get(0).getId();
		}else{
			return ruleRefId;
		}
		String[] ruleRefIdStr = ruleRefIds.split(",");
		List<String> ruleRefIdList = new ArrayList<String>();
		ruleRefIdList.add("APPCHECK_001");
		ruleRefIdList.add("APPCHECK_002");
		ruleRefIdList.add("APPCHECK_003");
		ruleRefIdList.add("APPCHECK_004");
		ruleRefIdList.add("APPCHECK_005");
		ruleRefIdList.add("APPCHECK_006"); //去掉运营商
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.setOrderByClause("rule_ref_id");
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAppIdEqualTo(appId)
		.andRuleRefIdIn(ruleRefIdList).andRuleOutputEqualTo(UhjConstant.ruleOutput.ruleOutput_true);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			int count = cclist.size();
			if(count == 6 ){
				ruleRefId = "APPCHECK_015";
			}else{
				ruleRefId = ruleRefIdStr[count];
			}
			log.info("queryCreditRuleToPage ruleRefId:"+ruleRefId);
			if(UhjConstant.ruleRefId.spouse_authorization.equals(ruleRefId)){
				List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
				CustomerInfoExample customerInfoExample = new CustomerInfoExample();
				customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
				if(customerInfos!= null && customerInfos.size()>0){
					if(customerInfos.get(0).getMaritalStatus()!=null && UhjConstant.maritalStatus.married.compareTo(customerInfos.get(0).getMaritalStatus())==0){
						ruleRefId=UhjConstant.ruleRefId.spouse_authorization;
					}else{
//						ruleRefId = "APPCHECK_015";
						ruleRefId = UhjConstant.ruleRefId.operator_authorization;
					}
				}
			}
			log.info("queryCreditRuleToPage ruleRefId:"+ruleRefId);
			if(UhjConstant.ruleRefId.spouse_authorization.equals(ruleRefId)){
				List<CustomerAddress> customerAddresss = new ArrayList<CustomerAddress>();
				CustomerAddressExample customerAddressExample = new CustomerAddressExample();
				customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				customerAddresss = customerAddressMapper.selectByExample(customerAddressExample);
				if(customerAddresss!= null && customerAddresss.size()>0){
					for(CustomerAddress customerAddress:customerAddresss){
						if(UhjConstant.addressType.store_address.equals(customerAddress.getAddressType())&&UhjConstant.customerType.contact.equals(customerAddress.getCustomerType())){
							ruleRefId=UhjConstant.ruleRefId.spouse_authorization;
							break;
						}else{
//							ruleRefId = "APPCHECK_015";
							ruleRefId = UhjConstant.ruleRefId.operator_authorization;
						}
					}
				}
			}
		}
		log.info("queryCreditRuleToPage result:"+ruleRefId);
		return ruleRefId;
	}

	/**
	 * 保存绑卡规则
	 */
	@Override
	public void saveCreditRuleOfBindCard(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		log.info("saveCreditRule userOnlyId"+userOnlyId);
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleTypeEqualTo("6").andRuleRefIdEqualTo(UhjConstant.ruleRefId.binding_bank_card);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist==null || cclist.size()==0){
			//creditRule状态改为成功
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.binding_bank_card);
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
			saveCreditRuleService(ruleMap);
		}
	}
	
	@Override
	public void updateCreditRuleService(String userOnlyId) throws Exception {
		//规则
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			for(CreditRule cc:cclist){
				if(cc.getRuleRefId().indexOf("APPCHECK_0")>=0){
					cc.setUpdateTime(DateUtil.currTimeStr());
					cc.setRuleOutput("false");
					creditRuleMapper.updateByPrimaryKey(cc);
				}
			}
		}
	}
}
