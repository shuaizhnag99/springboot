package com.ule.uhj.app.zgd.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.dao.ActivityInfoMapper;
import com.ule.uhj.app.zgd.dao.ApplyDetailMapper;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CreditPostmemberMapper;
import com.ule.uhj.app.zgd.dao.CreditReturnMapper;
import com.ule.uhj.app.zgd.dao.CreditRuleMapper;
import com.ule.uhj.app.zgd.dao.CustomerAddressMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerContactMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.CustomerStoreMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.ActivityInfo;
import com.ule.uhj.app.zgd.model.ActivityInfoExample;
import com.ule.uhj.app.zgd.model.ApplyDetail;
import com.ule.uhj.app.zgd.model.ApplyDetailExample;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.app.zgd.model.CreditPostmemberExample;
import com.ule.uhj.app.zgd.model.CreditRule;
import com.ule.uhj.app.zgd.model.CreditRuleExample;
import com.ule.uhj.app.zgd.model.CustomerAddress;
import com.ule.uhj.app.zgd.model.CustomerAddressExample;
import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;
import com.ule.uhj.app.zgd.model.CustomerContact;
import com.ule.uhj.app.zgd.model.CustomerContactExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.CustomerStore;
import com.ule.uhj.app.zgd.model.CustomerStoreExample;
import com.ule.uhj.app.zgd.model.CustomerWhite;
import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerWhiteService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.ProductInfoNService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.dto.zgd.ProductInfoN;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.sld.util.HttpClientUtil;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.StringUtil;

@Service
public class CerditApplyServiceImpl implements CreditApplyService {
	
	private static Logger log = LoggerFactory.getLogger(CerditApplyServiceImpl.class);
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private CreditRuleMapper creditRuleMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private CustomerContactMapper contactMapper;
	@Autowired
	private CustomerStoreMapper customerStoreMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	@Autowired
	private CreditReturnMapper creditReturnMapper;
	@Autowired
	private CreditPostmemberMapper creditPostMapper;
	@Autowired
	private ActivityInfoMapper activityInfoMapper;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private CustomerWhiteService customerWhiteService;
	
	@Override
	public String saveCertApplyInformation(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String productCode=Convert.toStr(map.get("productCode"));
		String status=Convert.toStr(map.get("status"));
		CreditApplyExample applyExample =new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> list=creditApplyMapper.selectByExample(applyExample);
		if(list==null || list.size()<=0){
			CreditApply apply =new CreditApply();
			apply.setApplyTime(DateUtil.currTimeStr());
			apply.setCreateTime(DateUtil.currTimeStr());
			apply.setProductCode(productCode);
			apply.setStatus(status);
			apply.setUserOnlyId(userOnlyId);
			creditApplyMapper.insertSelective(apply);
		}else{
			//TODO
		}
		return JsonResult.getInstance().addOk().toString();
	}

	@Override
	public String saveCertNoPositive(Map<String, Object> map) throws Exception {
		log.info("saveCertNoPositive map:"+map.toString());
//		String name=Convert.toStr(map.get("name"));
		String gender=Convert.toStr(map.get("gender"));//性别
		if("男".equals(gender)){
			gender=UhjConstant.gender.male;
		}else{
			gender=UhjConstant.gender.female;
		}
		String birthday=Convert.toStr(map.get("birthday"));
		if(!Check.isBlank(birthday))
			birthday=birthday.replaceAll("\\.", "-");//
		String certNo =Convert.toStr(map.get("certNo"));
		String address=Convert.toStr(map.get("address"));
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String certType=Convert.toStr(map.get("certType"));
		String valiadIdCert =Convert.toStr(map.get("valiadIdCert")); 
		
		CustomerCertExample ccExample =new CustomerCertExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(certType);
		List<CustomerCert> cclist=customerCertMapper.selectByExample(ccExample);
		if(cclist==null || cclist.size()<=0){
			log.info("saveCertNoPositive insert begin...");
			CustomerCert cc =new CustomerCert();
			cc.setAddress(address);
			cc.setBirthday(birthday);
			cc.setCertNo(certNo);
			cc.setCertType(certType);
			cc.setCreateTime(DateUtil.currTimeStr());
			cc.setGender(gender);
			cc.setSignOrg(null);
			if(!Check.isBlank(valiadIdCert)){
				cc.setValidEnd(valiadIdCert);
			}
			cc.setValidStart(null);
			cc.setUserOnlyId(userOnlyId);
			customerCertMapper.insertSelective(cc);
			
		}else{
			log.info("saveCertNoPositive update begin...");
			CustomerCert cc =cclist.get(0);
			cc.setAddress(address);
			cc.setBirthday(birthday);
			cc.setCertNo(certNo);
			cc.setCertType(certType);
			cc.setUpdateTime(DateUtil.currTimeStr());
			cc.setGender(gender);
			cc.setSignOrg("");
			if(!Check.isBlank(valiadIdCert)){
				cc.setValidEnd(valiadIdCert);
			}
//			cc.setValidEnd("");
			cc.setValidStart("");
			cc.setUserOnlyId(userOnlyId);
			customerCertMapper.updateByExampleSelective(cc, ccExample);
		}
		return JsonResult.getInstance().addOk().toString();
	}

	@Override
	public String saveCertNoNegative(Map<String, Object> map) throws Exception {
		String signOrg=Convert.toStr(map.get("signOrg"));
		String validStart =Convert.toStr(map.get("validStart"));
		String validEnd=Convert.toStr(map.get("validEnd"));
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String certType=Convert.toStr(map.get("certType"));
		if(validEnd.indexOf("期")>=0){
			validEnd="20991231";
		}
		try{
			if(validStart!=null && validStart.length()==8){
				validStart=validStart.substring(0,4)+"-"+validStart.substring(4,6)+"-"+validStart.substring(6,8);
			}
			if(validEnd!=null && validEnd.length()==8){
				validEnd=validEnd.substring(0,4)+"-"+validEnd.substring(4,6)+"-"+validEnd.substring(6,8);
			}
		}catch(Exception e){
			log.info("certNoNegativeInformation valid null userOnlyId:"+userOnlyId);
			return JsonResult.getInstance().addError("无法识别身份证有效起止期,请返回重新扫描您的身份证正面").toString();
		}

		CustomerCertExample ccExample =new CustomerCertExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(certType);
		List<CustomerCert> cclist=customerCertMapper.selectByExample(ccExample);
		if(cclist==null || cclist.size()<=0){
			log.info("certNoNegativeInformation  CustomerCert is null userOnlyId:"+userOnlyId);
			return JsonResult.getInstance().addError("系统异常,请返回重新扫描您的身份证正面").toString();
		}else{
			CustomerCert cc =new CustomerCert();
			cc.setUpdateTime(DateUtil.currTimeStr());
			cc.setSignOrg(signOrg);
			cc.setValidEnd(validEnd);
			cc.setValidStart(validStart);
			cc.setUserOnlyId(userOnlyId);
			customerCertMapper.updateByExampleSelective(cc, ccExample);
		}
		return JsonResult.getInstance().addOk().toJsonStr();
	}
	@Override
	public String queryApplyStatus(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String result=queryUserStatus(userOnlyId);
		JSONObject js =JSONObject.fromObject(result);
		if("0".equals(Convert.toStr(js.get("applyStatus")))){
			// 查询credit_apply表中是否含有额度
			// 如果可以使用 js.put("applyStatus", "10");
			List<CreditApply> list = queryInfoByUserOnlyId(userOnlyId);
			if(!CollectionUtils.isEmpty(list)) {
				CreditApply ca = list.get(0);
				if("A".equals(ca.getStatus())) {
					String usrName="";
					String certNo="";
					String leagueChannel="";
					String orgProvinceName="";
					Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
					if(vps==null){
						log.info("toAppPage vps is NULL");
						return result;
					}else{
						usrName=Convert.toStr(vps.get(VpsInfoService.UsrName));
						certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
						leagueChannel=Convert.toStr(vps.get(VpsInfoService.LeagueChannel));//渠道
						orgProvinceName=Convert.toStr(vps.get(VpsInfoService.OrgProvinceName));
						
						
						//----------------------------------------start
						String channelCode="C0001";
						map.clear();
						map.put("userOnlyId", userOnlyId);
						map.put("userName", usrName);
						certNo=certNo.trim().replaceAll(" ", "").replace("x", "X");
						map.put("certNo", certNo);
						Map<String, Object> whiteMap=customerWhiteService.queryCustomerWhite(map);
						if(whiteMap!=null){
							CustomerWhite white =(CustomerWhite) whiteMap.get("CustomerWhite");
							channelCode=white.getChannelCode();
						}
						
						//productInfoN 批销
						ProductInfoN productInfo = ProductInfoNService.getProductInfoN(channelCode,"0");
						log.info("查询产品表结果 : "+JSON.toJSONString(productInfo));
						if(productInfo!=null){
							
							try {
								String personBelongApply = productInfo.getPersonBelongApply();//特定的申请人
								List<String> certNoList = StringUtil.getListByString(personBelongApply, ",");
								if(certNoList!=null && certNoList.contains(certNo)){
									
								}else{
									String scopeBelongApply = productInfo.getScopeBelongApply(); //申请范围
									String orgprovince = orgProvinceName.substring(0, 2);
									if(!StringUtils.isEmpty(scopeBelongApply) && scopeBelongApply.indexOf(orgprovince)<0){
										log.info("申请范围不在产品配置中 不予申请");
										return result;
									}						
								}
							} catch (Exception e) {
								if(orgProvinceName!=null && orgProvinceName.indexOf("江苏省")>=0){
									return result;
								}
							}
						}else{
							if(orgProvinceName!=null && orgProvinceName.indexOf("江苏省")>=0){
								return result;
							}							
						}
						//----------------------------------------end 
						
						log.info("toAppPage vps userOnlyId:"+userOnlyId+"; usrName:"+usrName+";certNo:"+certNo+";leagueChannel:"+leagueChannel);
						if(usrName==null || certNo==null){
							log.info("toAppPage usrName idNo null false userOnlyId:"+userOnlyId);
						}
						usrName=null!=usrName?usrName.trim().replaceAll(" ", ""):"";
						
					}
					if(leagueChannel==null || (!"3".equals(leagueChannel) && !"4".equals(leagueChannel) 
							&& !"5".equals(leagueChannel) && !"6".equals(leagueChannel))){
						String channelCode="C0001";
						map.clear();
						map.put("userOnlyId", userOnlyId);
						map.put("userName", usrName);
						map.put("certNo", certNo);
						Map<String, Object> whiteMap=customerWhiteService.queryCustomerWhite(map);
						if(whiteMap!=null){
							CustomerWhite white =(CustomerWhite) whiteMap.get("CustomerWhite");
							channelCode=white.getChannelCode();
							log.info("toAppPage 格力用户 channelCode"+channelCode+";userOnlyId:"+userOnlyId);
						}
						if(Check.isBlank(channelCode) || UhjConstant.channelCode.ZGD_CHANNEL.equals(channelCode)){
							js.put("applyStatus", "10");
							return js.toString();
//							Map<String, String> returnMap = new HashMap<String, String>();
//							returnMap.put("userOnlyId", userOnlyId);
//							returnMap.put("result", js.toString());
//							log.info("toAppPage userOnlyId:"+userOnlyId+";returnMap:"+returnMap);
//							return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(returnMap));
						}
					}
				}
			}
		}
		return result;
	}
	
	
	private String queryUserStatus(String userOnlyId) throws Exception {
		// 查询applyDetail确认signSubmit接口是否还处于阻塞中
		ApplyDetailExample detailExample = new ApplyDetailExample();
		detailExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<ApplyDetail> detailList = applyDetailMapper.selectByExample(detailExample);
		if(!CollectionUtils.isEmpty(detailList)) {
			List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
			AccountInfoExample accountInfoExample = new AccountInfoExample();
			accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
			if(!CollectionUtils.isEmpty(accountInfos)){
				ApplyDetail detailInfo = detailList.get(0);
				if(null != detailInfo.getLineProgress() && detailInfo.getLineProgress().intValue() == 3 && "200".equals(accountInfos.get(0).getLimitType())) {
					log.info("当前发送邮储接口还在阻塞中...返回状态码为：6");
					return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.SIGN_SUBMIT_BLOCKING).toString();
				}
				
				//如果是批销快速额度的用户直接进批销的支用
				if(null != detailInfo.getLineProgress() && detailInfo.getLineProgress().intValue() == 1 && "200".equals(accountInfos.get(0).getLimitType())) {
					return JsonResult.getInstance().addOk().add("applyStatus","77").toString();
				}
			}
			
		}
		String status="";
		AccountInfo ai =null;
		List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
		AccountInfoExample accountInfoExample = new AccountInfoExample();
		accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
		if(!CollectionUtils.isEmpty(accountInfos)){
			ai=accountInfos.get(0);
			status=Convert.toStr(accountInfos.get(0).getApplyStatus());
		}
		// 建额失败跳转状态
		Map<String,Object> ruleMap = new HashMap<String, Object>();
		ruleMap.put("userOnlyId", userOnlyId);
		ruleMap.put("ruleRefId", UhjConstant.ruleRefId.px_limit_apply);
		String ruleOutput=creditRuleService.queryCreditRuleRuleOutput(ruleMap);
		if(ruleOutput!=null && "false".equals(ruleOutput)){
			return JsonResult.getInstance().addOk().add("applyStatus", "7").toString();
		}
		
		CreditApplyExample applyExample =new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> list=creditApplyMapper.selectByExample(applyExample);
		if(list!=null && list.size()>0){
			CreditApply apply=list.get(0);
			//判断accountInfo表的状态是否已授信，已授信的跳支用页面
			log.info("queryApplyStatus  userOnlyId " + userOnlyId + ", status " + status+";apply.getStatus():"+apply.getStatus());
			if (!Check.isBlank(status)){
				if(("1".equals(status) ||"101".equals(status)) 
						&& !UhjConstant.applyStatus.APPLY_STATUS_RETURN.equals(apply.getStatus())
						&& !UhjConstant.applyStatus.APPLY_STATUS_REVIEW.equals(apply.getStatus())
						&& !UhjConstant.applyStatus.APPLY_STATUS_NORMAL.equals(apply.getStatus())){
					return JsonResult.getInstance().addOk().add("applyStatus", "77").toString();
				}
			}
			if(UhjConstant.applyStatus.APPLY_STATUS_UNACTIVE.equals(apply.getStatus()) || UhjConstant.applyStatus.APPLY_STATUS_A.equals(apply.getStatus())){
				if(!booleanCustomerInfo(userOnlyId) && !"1".equals(status)){
					//修改rule表的申请过程的状态
					creditRuleService.updateCreditRuleService(userOnlyId);
					return JsonResult.getInstance().addOk().add("applyStatus","0").toString();
				}
				CreditRuleExample ruleExample =new CreditRuleExample();
				ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAppIdEqualTo(apply.getId());
				List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
				String faceFlag="0";
				if(rulelist!=null && rulelist.size()>0)
				for(CreditRule rule:rulelist){
					if(UhjConstant.ruleRefId.face_recognition.equals(rule.getRuleRefId()) && UhjConstant.ruleOutput.ruleOutput_true.equals(rule.getRuleOutput())){
						faceFlag="100";//进入中断页面的状态
					}
				}
				if(!"100".equals(faceFlag)){
					if(ai!=null && ai.getYouChuLimit()!=null && ai.getYouChuLimit().compareTo(BigDecimal.ZERO)>0 && ai.getExpireDate()!=null){
						log.info("queryApplyStatus  userOnlyId " + userOnlyId + ", YouChuLimit " + ai.getYouChuLimit());
						return JsonResult.getInstance().addOk().add("applyStatus","77").toString();
					}
				}
				return JsonResult.getInstance().addOk().add("applyStatus", faceFlag).toString();
			}else if(UhjConstant.applyStatus.APPLY_STATUS_REFUSE.equals(apply.getStatus())){//审核拒绝（拒绝的状态看AccountInfo表的状态，这里应该是走不到的）
				String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
				JSONObject js=JSONObject.fromObject(res);
				String reactivatedDate=Convert.toStr(js.get("reactivatedDate"));
				return JsonResult.getInstance().addOk().add("applyStatus", apply.getStatus()).add("reactivatedDate", "额度申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷。").toString();
			}else if(UhjConstant.applyStatus.APPLY_STATUS_RETURN.equals(apply.getStatus())){//审核退回
				//如果借现金退回，但是快速批销额度建立成功了，直接去批销的页面
				ApplyDetail detailInfo = detailList.get(0);
				if(null != detailInfo.getLineProgress() && detailInfo.getLineProgress().intValue() == 1) {
					if(ai!=null && "200".equals(ai.getLimitType())){
						return JsonResult.getInstance().addOk().add("applyStatus","77").toString();
					}
				}
				return JsonResult.getInstance().addOk().add("applyStatus", apply.getStatus()).toString();
			}else if(UhjConstant.applyStatus.APPLY_STATUS_REVIEW.equals(apply.getStatus()) ){//等待审核、运营商授权中，都是到等待审核页面
				if("1".equals(status) || "101".equals(status)  ){//  
					return JsonResult.getInstance().addOk().add("applyStatus", "77").toString();
				}else if("5".equals(status) || "2".equals(status)){
					//如果借现金拒绝，但是快速批销额度建立成功了，直接去批销的页面
					ApplyDetail detailInfo = detailList.get(0);
					if(null != detailInfo.getLineProgress() && detailInfo.getLineProgress().intValue() == 1) {
						if(ai!=null && "200".equals(ai.getLimitType())){
							return JsonResult.getInstance().addOk().add("applyStatus","77").toString();
						}
					}
					String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
					JSONObject object=JSONObject.fromObject(res);
					String reactivatedDate=Convert.toStr(object.get("reactivatedDate"));
					String reason=Convert.toStr(object.get("reason"));//审核被打回的原因
					if(!Check.isBlank(reason) && reason.indexOf("营业执照注册未满一年")>=0){
						return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_REFUSE).add("reactivatedDate", "亲，您的营业执照注册未满一年，请于"+reactivatedDate+"再次申请掌柜贷。").toString();
					}
					return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_REFUSE).add("reactivatedDate", "额度申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷。").toString();
				}
				//app申请过的掌柜账户处于掌柜贷【等待审核】和【申请成功，额度已生效】状态下，点击掌柜贷入口时，校验当前登陆掌柜的姓名和身份证号与掌柜贷系统所存信息是否一致，如不一致则跳到提示信息页面
				if(!booleanCustomerInfo(userOnlyId)){
					Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
					if(vps==null){
						return JsonResult.getInstance().addOk().add("applyStatus","9999").add("userName", "").add("certNo", "").toString();
					}else{
						String usrName=Convert.toStr(vps.get(VpsInfoService.UsrName));
						String certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
						return JsonResult.getInstance().addOk().add("applyStatus","9999").add("userName", usrName).add("certNo", certNo).toString();
					}
				}
				return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_REVIEW).toString();
			}else if ( UhjConstant.applyStatus.APPLY_STATUS_NORMAL.equals(apply.getStatus())){//等待审核、运营商授权中，都是到等待审核页面
				if(!booleanCustomerInfo(userOnlyId)){
					Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
					if(vps==null){
						return JsonResult.getInstance().addOk().add("applyStatus","9999").add("userName", "").add("certNo", "").toString();
					}else{
						String usrName=Convert.toStr(vps.get(VpsInfoService.UsrName));
						String certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
						return JsonResult.getInstance().addOk().add("applyStatus","9999").add("userName", usrName).add("certNo", certNo).toString();
					}
				}
				return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_REVIEW).toString();
			}else{
				if(ai!=null && ai.getYouChuLimit()!=null && ai.getYouChuLimit().compareTo(BigDecimal.ZERO)>0){
					log.info("queryApplyStatus  userOnlyId " + userOnlyId + ", YouChuLimit " + ai.getYouChuLimit());
					return JsonResult.getInstance().addOk().add("applyStatus","77").toString();
				}
				if(!booleanCustomerInfo(userOnlyId)){
					//修改rule表的申请过程的状态
					creditRuleService.updateCreditRuleService(userOnlyId);
					return JsonResult.getInstance().addOk().add("applyStatus","0").toString();
				}
				return JsonResult.getInstance().addOk().add("applyStatus", apply.getStatus()).toString();
			}
		}else{
//			String flag = WildflyBeanFactory.getYCZgdQueryClient().queryZgdUserUseStatus(userOnlyId);
//			JSONObject js = JSONObject.fromObject(flag);
//			if ("0000".equals(js.get("code").toString())){
//				log.info("queryApplyStatus2 " + userOnlyId + ", queryStatus " + flag);
//				String status= js.get("status").toString();
				if("1".equals(status) || "101".equals(status)){
					return JsonResult.getInstance().addOk().add("applyStatus", "77").toString();
				}else if("5".equals(status) || "2".equals(status)){
					String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
					JSONObject object=JSONObject.fromObject(res);
					String reactivatedDate=Convert.toStr(object.get("reactivatedDate"));
					String reason=Convert.toStr(object.get("reason"));//审核被打回的原因
					if(!Check.isBlank(reason) && reason.indexOf("营业执照注册未满一年")>=0){
						return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_REFUSE).add("reactivatedDate", "亲，您的营业执照注册未满一年，请于"+reactivatedDate+"再次申请掌柜贷。").toString();
					}
					return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_REFUSE).add("reactivatedDate", "额度申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷。").toString();
				}
//			}
			return JsonResult.getInstance().addOk().add("applyStatus", UhjConstant.applyStatus.APPLY_STATUS_UNACTIVE).toString();
		}
	}
	
	//判断邮掌柜的信息和掌柜贷的信息是否一致
	private boolean booleanCustomerInfo(String userOnlyId)
			throws Exception {
		
		String env = PropertiesHelper.getDfs("env");
		if("beta".equals(env) || "testing".equals(env)){
			return true;
		}
		log.info("booleanCustomerInfo userOnlyId:"+userOnlyId);
		String name="";
		String certNo="";
		List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
		CustomerInfoExample customerInfoExample = new CustomerInfoExample();
		customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
		if(customerInfos!= null && customerInfos.size()>0){
			name= customerInfos.get(0).getCustomerName();
		}else{
			return false;
		}
		List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
		CustomerCertExample customerCertExample = new CustomerCertExample();
		customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.idcard);
		customerCerts = customerCertMapper.selectByExample(customerCertExample);
		if(customerCerts!= null && customerCerts.size()>0){
			certNo= customerCerts.get(0).getCertNo();
		}else{
			return false;
		}
		log.info("booleanCustomerInfo name:"+name+";certNo:"+certNo);
		Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
		if(vps==null){
			log.info("booleanCustomerInfo vps is NULL");
			return false;
		}else{
			String usrName=Convert.toStr(vps.get(VpsInfoService.UsrName));
			String idNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
			log.info("booleanCustomerInfo vps usrName:"+usrName+";idNo:"+idNo);
			if(usrName==null || idNo==null){
				log.info("booleanCustomerInfo usrName idNo null false userOnlyId:"+userOnlyId);
				return false;
			}
			usrName=usrName.trim().replaceAll(" ", "");
			idNo=idNo.trim().replaceAll(" ", "").replace("x", "X");
			certNo=certNo.replace("x", "X");
			if(usrName.equals(name) && idNo.equals(certNo)){
				log.info("booleanCustomerInfo true userOnlyId:"+userOnlyId);
				return true;
			}else{
				log.info("booleanCustomerInfo false userOnlyId:"+userOnlyId);
				return false;
			}
		}
	}
	
	@Override
	public String querySuspendStatus(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		BigDecimal maritalStatus=UhjConstant.maritalStatus.unmarried;
		String userName="";
		String contactName="";//联系人姓名
		String storeName="";//店铺名称
		List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
		CustomerInfoExample customerInfoExample = new CustomerInfoExample();
		customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
		if(customerInfos!= null && customerInfos.size()>0){
			maritalStatus=customerInfos.get(0).getMaritalStatus();
			userName=customerInfos.get(0).getCustomerName();
		}
		if(!Check.isBlank(maritalStatus)&&UhjConstant.maritalStatus.married.compareTo(maritalStatus)==0){
			CustomerAddressExample customerAddressExample =new CustomerAddressExample();
			customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(UhjConstant.addressType.store_address);
			List<CustomerAddress> customerAddressList=customerAddressMapper.selectByExample(customerAddressExample);
			if(customerAddressList !=null && customerAddressList.size()>0){
				//地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断店铺是本人的
				CustomerAddress address=customerAddressList.get(0);
				if(UhjConstant.customerType.loanor.equals(address.getCustomerType())){//如果店铺是掌柜本人的，不需要显示配偶授权
					maritalStatus=UhjConstant.maritalStatus.unmarried;
				}
			}
			
		}
		CreditApplyExample applyExample =new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> list=creditApplyMapper.selectByExample(applyExample);
		if(list !=null && list.size()>0){
			  
			CreditApply apply=list.get(0);
			CreditRuleExample ruleExample =new CreditRuleExample();
			ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAppIdEqualTo(apply.getId());
			List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
			String face_recognition="false";
			String binding_bank_card="false";
			String contact_state="false";
			String shops_fill="false";
			String spouse_authorization="false";
			String operator_authorization="false";
			if(rulelist!=null && rulelist.size()>0)
			for(CreditRule rule:rulelist){
				if(UhjConstant.ruleRefId.face_recognition.equals(rule.getRuleRefId())){//人脸识别
					face_recognition=rule.getRuleOutput();
				}else if(UhjConstant.ruleRefId.binding_bank_card.equals(rule.getRuleRefId())){//绑定银行卡
					binding_bank_card=rule.getRuleOutput();
				}else if(UhjConstant.ruleRefId.contact_state.equals(rule.getRuleRefId())){// 联系人状态
					contact_state=rule.getRuleOutput();
				}else if(UhjConstant.ruleRefId.shops_fill.equals(rule.getRuleRefId())){//店铺信息填写
					shops_fill=rule.getRuleOutput();
				}else if(UhjConstant.ruleRefId.spouse_authorization.equals(rule.getRuleRefId())){//配偶授权
					spouse_authorization=rule.getRuleOutput();
				}else if(UhjConstant.ruleRefId.operator_authorization.equals(rule.getRuleRefId())){//运营商授权
					operator_authorization=rule.getRuleOutput();
				}
			}
			
			CustomerContactExample contactExample = new CustomerContactExample();
			contactExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<CustomerContact> contacts = contactMapper.selectByExample(contactExample);
			if(contacts!=null && contacts.size()>0){
				contactName=contacts.get(0).getContactName();
			}
			CustomerStoreExample customerStoreExample =new CustomerStoreExample();
			customerStoreExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<CustomerStore> customerStoreList=customerStoreMapper.selectByExample(customerStoreExample);
			if(customerStoreList!=null && customerStoreList.size()>0){
				storeName=customerStoreList.get(0).getStoreName();
			}
			String status=apply.getStatus();
			if(("true".equals(operator_authorization)||operator_authorization.startsWith("skip")) && "3".equals(status)){
				status="1";
			}
			String reactivatedDate="";
			//点击借现金的时候 拒绝的去拒绝页面
			ApplyDetailExample detailExample = new ApplyDetailExample();
			detailExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<ApplyDetail> detailList = applyDetailMapper.selectByExample(detailExample);
			if(!CollectionUtils.isEmpty(detailList)) {
				ApplyDetail detailInfo = detailList.get(0);
				if(null != detailInfo.getLineProgress() && detailInfo.getLineProgress().intValue() == 1) {
					List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
					AccountInfoExample accountInfoExample = new AccountInfoExample();
					accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
					accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
					if(accountInfos!= null && accountInfos.size()>0){
						if("5".equals(Convert.toStr(accountInfos.get(0).getApplyStatus())) && "200".equals(accountInfos.get(0).getLimitType())){
							status="5";
							String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
							JSONObject js=JSONObject.fromObject(res);
							reactivatedDate=Convert.toStr(js.get("reactivatedDate"));//拒绝原因
						}
					}
				}
			}
			
			return JsonResult.getInstance().addOk().add("face_recognition", face_recognition)
					.add("binding_bank_card", binding_bank_card)
					.add("contact_state", contact_state)
					.add("shops_fill", shops_fill)
					.add("spouse_authorization", spouse_authorization)
					.add("operator_authorization", operator_authorization) //去掉运营商
					.add("maritalStatus", maritalStatus)//婚姻是已婚的显示配偶授权状态，否则不显示
					.add("userName", userName)//申请人姓名
					.add("contactName", contactName)//联系人姓名
					.add("storeName", storeName)//店铺名称
					.add("userOnlyId", userOnlyId)//用户ID
					.add("status", status)//用户状态  1去等待审核   4 去退回   5 去拒绝页面
					.add("reactivatedDate", "额度申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷。")
					.toString();
		}else{
			return JsonResult.getInstance().addError("没有查询到掌柜贷申请信息").toString();
		}
		
	}

	//判断用户是否充分申请，并取到重复申请的机构号
	@Override
	public String booleanCustomInfo(Map<String, Object> map) throws Exception {
		String env = PropertiesHelper.getDfs("env");
		if("beta".equals(env) || "testing".equals(env)){
			return "true";
		}
		String userName=Convert.toStr(map.get("userName"));
		String certNo=Convert.toStr(map.get("certNo"));
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		log.info("booleanCustomInfo userOnlyId:"+userOnlyId);
		log.info("booleanCustomInfo userName:"+userName+";certNo:"+certNo);
		
		if(Check.isBlank(userName) || Check.isBlank(certNo)){
			return "true";
		}
		
		List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
		CustomerCertExample customerCertExample = new CustomerCertExample();
		customerCertExample.createCriteria().andUserOnlyIdNotEqualTo(userOnlyId).
							andCertTypeEqualTo(UhjConstant.certType.idcard)
							.andCertNoEqualTo(certNo);
		customerCerts = customerCertMapper.selectByExample(customerCertExample);
		if(customerCerts!= null && customerCerts.size()>0){
			for(CustomerCert cert:customerCerts){
				CreditApplyExample applyExample =new CreditApplyExample();
				applyExample.createCriteria().andUserOnlyIdEqualTo(cert.getUserOnlyId()).andStatusEqualTo("A");
				List<CreditApply> list=creditApplyMapper.selectByExample(applyExample);
				if(list!=null && list.size()>0){//如果是状态为A的自动跑出来的快速批销额度用户 ，不需要做比较
					continue;
				}
				List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
				CustomerInfoExample customerInfoExample = new CustomerInfoExample();
				customerInfoExample.createCriteria().andCustomerNameEqualTo(userName).andUserOnlyIdEqualTo(cert.getUserOnlyId());
				customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
				if(customerInfos!= null && customerInfos.size()>0){
					return customerInfos.get(0).getOrgCode();
				}
			}
		}
		return "true";
	}

	@Override
	public boolean queryPostMember(String userOnlyId) throws Exception {
		log.info("savePostMember userOnlyId:"+userOnlyId);
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andMemberTypeEqualTo("1");
		List<CreditPostmember> creditPostmember = creditPostMapper.selectByExample(example);
		if(creditPostmember!=null&&creditPostmember.size()>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public Map<String, Object> queryBZGCreditPostMember(String userOnlyId) throws Exception {
		log.info("queryBZGCreditPostMember userOnlyId:"+userOnlyId);
		Map<String, Object> map =new HashMap<String, Object>();
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andMemberTypeEqualTo("1");
		List<CreditPostmember> creditPostmember = creditPostMapper.selectByExample(example);
		if(creditPostmember!=null&&creditPostmember.size()>0){
			map.put("bzgMobile", creditPostmember.get(0).getMobile());
			map.put("bzgName", creditPostmember.get(0).getName());
			map.put("staffId", creditPostmember.get(0).getStaffId());
			return map;
		}else{
			CreditPostmemberExample example2 = new CreditPostmemberExample();
			example2.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andMemberTypeEqualTo("2");
			List<CreditPostmember> creditPostmember2 = creditPostMapper.selectByExample(example2);
			if(creditPostmember2!=null&&creditPostmember2.size()>0){
				map.put("bzgMobile", creditPostmember2.get(0).getMobile());
				map.put("bzgName", creditPostmember2.get(0).getName());
				map.put("staffId", creditPostmember2.get(0).getStaffId());
				return map;
			}
		}
		return map;
	}
	
	@Override
	public boolean queryHasBindPostMember(String userOnlyId) throws Exception {
		log.info("savePostMember userOnlyId:"+userOnlyId);
		List<String> memberTypes = new ArrayList<String>();
		memberTypes.add("1");
		memberTypes.add("2");
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andMemberTypeIn(memberTypes);
		List<CreditPostmember> creditPostmember = creditPostMapper.selectByExample(example);
		if(creditPostmember!=null&&creditPostmember.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<CreditApply> queryInfoByUserOnlyId(String userOnlyId) throws Exception {
		List<CreditApply> list = new ArrayList<CreditApply>();
		if(!StringUtils.isEmpty(userOnlyId)) {
			CreditApplyExample example = new CreditApplyExample();
			example.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			list = creditApplyMapper.selectByExample(example);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCustomerCouponInfo(Map<String, Object> couponParamters) throws Exception {
		Map<String,Object> activityMap = new HashMap<String, Object>();
		List<Map<String,String>> couponList = new ArrayList<Map<String,String>>();
		List<String> activityInfoList = new ArrayList<String>();
		activityMap.put("coupon", couponList);
		activityMap.put("innerActivity", activityInfoList);
		log.info("getCustomerCouponInfo paramters="+couponParamters);
		ActivityInfo activityInfo = null;
		if((Boolean)couponParamters.get("checkFirstLoad")){
			ActivityInfoExample activityInfoExample = new ActivityInfoExample();
			activityInfoExample.createCriteria().andActivityCodeEqualTo("DG-YHQ")
				.andUserOnlyIdEqualTo(Convert.toStr(couponParamters.get("userOnlyId"),"")).andContentEqualTo("0");
			List<ActivityInfo> activityList = activityInfoMapper.selectByExample(activityInfoExample);
			if(activityList!=null&&activityList.size()>0){
				activityInfo = activityList.get(0);
				couponParamters.put("firstCreditLimit", "1");
				activityInfo.setContent("1");
				activityInfoMapper.updateByPrimaryKey(activityInfo);
			}else{//登陆校验活动券，没有直接退出
				return activityMap;
			}
		}
		
		//功能开放性控制
		Map<String,Class<?>> zgdCouponAllowMap = new HashMap<String, Class<?>>();
		String zgdCouponAllowKey="ZgdCouponAllow";
		zgdCouponAllowMap.put(zgdCouponAllowKey,String.class);
		ZgdQueryClient client = WildflyBeanFactory.getZgdQueryClient();
		Map<String,Object> allowMap = client.queryZgdConstantValue(zgdCouponAllowMap);
		log.info("getCustomerCouponInfo ZgdCouponAllow ="+allowMap);
		if(allowMap!=null){
			String allowInfo = ((List<String>)allowMap.get(zgdCouponAllowKey)).get(0);
			if(allowInfo.startsWith("false")&&allowInfo.indexOf(Convert.toStr(couponParamters.get("userOnlyId")))<0){
				return activityMap;
			}
		}
		
		log.info("getCustomerCouponInfo RULE_SET query start...");
		String ruleSetId = "coupon001";
		final String rule_url =  PropertiesHelper.getDfs("RULE_SET_URL");
		JSONObject js =new JSONObject();
		JSONObject setmap = new JSONObject();
		setmap.put("ruleSetId", ruleSetId);
		setmap.put("loanAmount", Convert.toBigDecimal(couponParamters.get("loanAmount")));
		setmap.put("firstCreditLimit", Convert.toInt(couponParamters.get("firstCreditLimit")));
		js.put("data", setmap.toString());
		String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
				rule_url, js.toString(), UhjConstant.time_out);
		JSONObject resJs=JSONObject.fromObject(res);
		log.info("getCustomerCouponInfo result:"+res);
		if("000000".equals(resJs.get("code"))){
			JSONObject object=(JSONObject) resJs.get("object");
			JSONArray data=object.getJSONArray("data");
			JSONObject ruleOutputResult=(JSONObject) ((JSONObject) data.toArray()[0]).get("ruleOutputResult");
			if("0000".equals(Convert.toStr(ruleOutputResult.get("code")))){
				List<JSONObject> list = (List<JSONObject>) ruleOutputResult.get("data");
				List<JSONObject> activityList = (List<JSONObject>) ruleOutputResult.get("innerData");
				for(Map<String,Object> tempActivity:activityList){
					activityInfoList.add(Convert.toStr(tempActivity.get("activity")));
					activityMap.put("innerActivity", activityInfoList);
					if(Boolean.valueOf(Convert.toStr(tempActivity.get("existOnly"),"false"))){
						return activityMap;
					}
				}
				
				for(Map<String,Object> temp:list){
					int startPage = 1;
					boolean end = false;
					List<Map<String,String>> oneActivityList = new ArrayList<Map<String,String>>();
					while(!end){
						Map<String,Object> paramterMap = new HashMap<String, Object>();
						paramterMap.put("URL",PropertiesHelper.getDfs("COUPON_QUERY_URL"));
				        temp.put("pageIndex", startPage);
				        temp.put("pageSize", 10*startPage);
				        paramterMap.put("paramer", temp);
				        Map<String, Object> ret = HttpClientUtil.httpPost(paramterMap);
				        if("success".equals(Convert.toStr(ret.get("status")))){
				        	Map<String, Object> dataMap = JsonUtil.getMapFromJsonString(Convert.toStr(ret.get("result")));
				        	if("0000".equals(Convert.toStr(dataMap.get("code")))){
					        	JSONArray array = JSONArray.fromObject(dataMap.get("result"));
					        	JSONObject jsonObj = (JSONObject) array.get(0);
					        	int total = (Integer) JSONObject.fromObject(jsonObj.get("confList")).get("totalFound");
					        	if(total<=startPage*10){
					        		end = true;
					        	}else{
					        		startPage++;
					        	}
					        	JSONArray couponStoreList = JSONArray.fromObject(JSONObject
					        			.fromObject(jsonObj.get("confList")).get("couponStoreList"));
					        	for(int i=0;i<couponStoreList.size();i++){
					        		JSONObject couponStore = couponStoreList.getJSONObject(i);
					        		JSONArray prizeCouponList = JSONArray.fromObject(couponStore.get("prizeCouponList"));
					        		for(int j=0;j<prizeCouponList.size();j++){
					        			JSONObject coupon = prizeCouponList.getJSONObject(j);
						        		if(Convert.toLong(coupon.get("startTime")).compareTo(System.currentTimeMillis())<=0&&
						        				Convert.toLong(coupon.get("endTime")).compareTo(System.currentTimeMillis())>=0
						        				&&(!"0".equals(Convert.toStr(coupon.get("currentStock"))))//剩余券数
//						        				&&(Boolean)coupon.get("dayIsHaveStock")
//						        				&&(Boolean)coupon.get("allIsHaveStock")
						        				){
						        			Map<String,String> couponMap = new HashMap<String, String>();
						        			couponMap.put("activityCode", Convert.toStr(coupon.get("activityCode")));
						        			couponMap.put("couponDetailId", Convert.toStr(coupon.get("detailId")));
						        			couponMap.put("useConditions", Convert.toStr(coupon.get("useConditions")));
						        			couponMap.put("amount", Convert.toStr(coupon.get("amount")));
						        			couponMap.put("couponInfo", Convert.toStr(coupon.get("couponInfo")));
						        			couponMap.put("storeId", Convert.toStr(couponStore.get("storeId")));
						        			oneActivityList.add(couponMap);
						        		}
					        		}
					        	}
					        	continue;
					        }
				        }
				        end = true;
					}
					if(oneActivityList!=null&&oneActivityList.size()>0){
						Map<String,String> oneCoupon = oneActivityList.get(new Random().nextInt(oneActivityList.size()));
						Map<String,Class<?>> constantQueryMap = new HashMap<String, Class<?>>();
						String constantKey="coupon_logo_"+oneCoupon.get("storeId");
						constantQueryMap.put(constantKey,String.class);
						Map<String,Object> constantMap = client.queryZgdConstantValue(constantQueryMap);
						if(constantMap!=null){
							String logoUrl = ((List<String>)constantMap.get(constantKey)).get(0);
							oneCoupon.put("logoUrl", logoUrl);
						}
						couponList.add(oneCoupon);
					}
				}
			}
		}
		activityMap.put("coupon", couponList);
		return activityMap;
	}

	@Override
	public String toXuqiPage(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		map.put("userOnlyId", userOnlyId);
		map.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
		String ruleOutput=creditRuleService.queryCreditRuleRuleOutput(map);
		CustomerStoreExample customerStoreExample =new CustomerStoreExample();
		customerStoreExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerStore> customerStoreList=customerStoreMapper.selectByExample(customerStoreExample);
		if(customerStoreList!=null && customerStoreList.size()>0){
			CustomerStore store=customerStoreList.get(0);
			if(store==null || store.getIsStillOpen()==null || !"true".equals(ruleOutput)){
				return JsonResult.getInstance().addOk().add("process", "isStillOpen").toJsonStr();
			}else if(store.getIsStillOpen()!=null && "1".equals(store.getIsStillOpen())){
				return JsonResult.getInstance().addOk().add("process", "storeImg").toJsonStr();
			}else{
				return JsonResult.getInstance().addOk().add("process", "store").toJsonStr();
			}
		}
		return JsonResult.getInstance().addError().toJsonStr();
	}

	@Override
	public String toXuqiStatuPage(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String isStillOpen=Convert.toStr(map.get("isStillOpen"));
		CustomerStoreExample customerStoreExample =new CustomerStoreExample();
		customerStoreExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerStore> customerStoreList=customerStoreMapper.selectByExample(customerStoreExample);
		if(customerStoreList!=null && customerStoreList.size()>0){
			CustomerStore customerStore =customerStoreList.get(0);
			customerStore.setUpdateTime(DateUtil.currTimeStr());
			customerStore.setIsStillOpen(isStillOpen);
			customerStoreMapper.updateByExampleSelective(customerStore, customerStoreExample);
		}
		
		CreditApplyExample applyExample =new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> list=creditApplyMapper.selectByExample(applyExample);
		if(list!=null && list.size()>0){
			CreditApply apply=list.get(0);
			apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_UNACTIVE);
			apply.setUpdateTime(DateUtil.currTimeStr());
			apply.setUpdateUser("额度续期");
			creditApplyMapper.updateByPrimaryKey(apply);
		}
		
		List<String> ruleRefIdList = new ArrayList<String>();
		ruleRefIdList.add("APPCHECK_001");
		ruleRefIdList.add("APPCHECK_004");
//		ruleRefIdList.add("APPCHECK_011");
//		ruleRefIdList.add("APPCHECK_012");
		if("0".equals(isStillOpen)){
			ruleRefIdList.add("APPCHECK_005");
		}
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.setOrderByClause("rule_ref_id");
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId)
		.andRuleRefIdIn(ruleRefIdList).andRuleOutputEqualTo(UhjConstant.ruleOutput.ruleOutput_true);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			for(CreditRule rule:cclist){
				rule.setRuleOutput("false");
				rule.setUpdateTime(DateUtil.currTimeStr());
				creditRuleMapper.updateByPrimaryKey(rule);
			}
		}
		return JsonResult.getInstance().addOk().toJsonStr();
	}

	@Override
	public boolean queryIsOldUser(String userOnlyId) throws Exception {
		CreditRuleExample ccExample =new CreditRuleExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.operator_authorization);
		List<CreditRule> cclist=creditRuleMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			return true;
		}
		return false;
	}
	
}