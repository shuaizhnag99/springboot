package com.ule.uhj.app.zgd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import com.ule.uhj.app.yzs.constant.YzsConstants;
import com.ule.uhj.app.yzs.util.ResultUtil;
import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.dao.PostOrgInfoMapper;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.app.zgd.model.CustomerWhite;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.model.PostOrgInfo;
import com.ule.uhj.app.zgd.model.PostOrgInfoExample;
import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.service.BindCardService;
import com.ule.uhj.app.zgd.service.ContactsService;
import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerAddressService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.CustomerWhiteService;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.service.PostOrgInfoService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.ProductInfoNService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.dto.zgd.ProductInfoN;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.ejb.client.ycZgd.YcLimitRenewClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.StringUtil;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/face")
public class FaceIdentifyController {

	private static Logger log = LoggerFactory.getLogger(FaceIdentifyController.class);
	
	
	@Autowired
	private CreditApplyService cerditApplyService;
	
	@Autowired
	private ContactsService contactsService;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private ApplyImageService applyImageService;
	@Autowired
	private CustomerWhiteService customerWhiteService;
	
	@Autowired
	private PostOrgInfoService postOrgInfoService;
	@Autowired
	private PostOrgInfoMapper postMapper;
	
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/aap_userInfo")
	@ResponseBody
	public JSONPObject userInfo(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("userInfo begin.");
		try {
			String result=null;
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("userInfo userOnlyId "+userOnlyId);
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			if(vps==null){
				result=JsonResult.getInstance().addError("VPS is null").toString();
			}else{
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
				map.put("ruleRefId", UhjConstant.ruleRefId.certno_name_matches);
				//身份证号与姓名是否匹配  每天超过三次就不能再申请了
				String ruleOutput=creditRuleService.queryCreditRuleService(map);
				int count=Convert.toInt(ruleOutput,0);
				if(count>=3){
					ruleOutput="1";
				}else{
					ruleOutput="0";
				}
				
				String vps_name=Convert.toStr(vps.get(VpsInfoService.UsrName));
				String vps_certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
				log.info("userInfo userOnlyId:"+userOnlyId+";vps_name:"+vps_name+";vps_certNo"+vps_certNo);
				String orgProvince = Convert.toStr(vps.get(VpsInfoService.OrgProvince));//代码
				log.info("showTown userInfo orgProvince:"+orgProvince);
				Map<String,Class<?>> constantMap = new HashMap<String, Class<?>>();
				constantMap.put("showTown",String.class);
				ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
				Map<String,Object> conMap = zgdQueryClient.queryZgdConstantValue2(constantMap);
				List<String> provinceCodeList = (List<String>)conMap.get("showTown");
				log.info("showTown provinceCodeList:"+provinceCodeList.toString());
				String provinceCode = "";
				if(!CollectionUtils.isEmpty(provinceCodeList)) {
					provinceCode = Convert.toStr(provinceCodeList.get(0));
				}
						
				boolean isShowTownFlag = false;
				String orgProvinceCode = "";
				String orgCityCode = "";
				String orgAreaCode = "";
				if(!StringUtils.isBlank(provinceCode)){
					String[] provinceCodes = provinceCode.split(",");
					if(provinceCodes!=null&&provinceCodes.length>0){
						for(int i = 0 ; i < provinceCodes.length ; i ++){
							log.info("userInfo provinceCode:"+Convert.toStr(provinceCodes[i],""));
							//是否显示支局弹框
							if(orgProvince!=null&&!"".equals(orgProvince)&&orgProvince.equals(Convert.toStr(provinceCodes[i],""))){
								isShowTownFlag = true;
								break;
							}
//							/***写死测试数据，两个演示账号默认弹出---开始***/
//							String orgCode=Convert.toStr(vps.get(VpsInfoService.OrgCode));
//							if("appnb888".equals(orgCode)) {
//								isShowTownFlag = true;
//								log.info("生产测试账号正常弹出！isShowTownFlag："+isShowTownFlag);
//							}
//							/***写死测试数据，两个演示账号默认弹出---结束***/
						}
						orgProvinceCode = queryCode(Convert.toStr(vps.get(VpsInfoService.OrgProvinceName)),"0");
						log.info("userInfo orgProvinceCode:"+orgProvinceCode);
						if(!StringUtils.isBlank(orgProvinceCode)){
							orgCityCode = queryCode(Convert.toStr(vps.get(VpsInfoService.OrgCityName)),orgProvinceCode);
							log.info("userInfo orgCityCode:"+orgCityCode);
							if(!StringUtils.isBlank(orgCityCode)){
								orgAreaCode = queryCode(Convert.toStr(vps.get(VpsInfoService.OrgAreaName)),orgCityCode);
								log.info("userInfo orgAreaCode:"+orgAreaCode);
							}
						}
					}
				}
				if(Check.isBlank(vps_certNo) || Check.isBlank(vps_name)){
					//有一个为空的不做是否已存在的校验，前台会提示用户信息不全
					result=JsonResult.getInstance().addOk().add("name", vps_name)
							.add("certNo", vps_certNo)
							.add("isShowTownFlag", isShowTownFlag).add("orgProvinceCode", orgProvinceCode)
							.add("orgCityCode", orgCityCode).add("orgAreaCode", orgAreaCode)
							.add("orgProvinceName", Convert.toStr(vps.get(VpsInfoService.OrgProvinceName)))
							.add("orgCityName", Convert.toStr(vps.get(VpsInfoService.OrgCityName)))
							.add("orgAreaName", Convert.toStr(vps.get(VpsInfoService.OrgAreaName)))
							.add("ruleOutput", ruleOutput).add("isCredit", "0")
							.toString();
					log.info("userInfo userOnlyId:"+userOnlyId+";result:"+result);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}
				
				String   channelName ="";
				map.clear();
				map.put("userOnlyId", userOnlyId);
				map.put("userName", vps_name);
				map.put("certNo", vps_certNo);
				Map<String, Object> whiteMap=customerWhiteService.queryCustomerWhite(map); 
				if(whiteMap!=null){
					CustomerWhite white=(CustomerWhite) whiteMap.get("CustomerWhite");
					channelName=white.getChannelName();
				}
				String isCredit="0";
				//判断用户是否充分申请，并取到重复申请的机构号
				String orgCode=cerditApplyService.booleanCustomInfo(map);
				if(!"true".equals(orgCode)){
					isCredit="1";
				}
//				String res = WildflyBeanFactory.getZgdAppClient().queryZgUserInfo(map);
//				JSONObject js=JSONObject.fromObject(res);
//				Convert.toStr(js.get("isCredit"));
//				if("0".equals(isCredit)){
//					
//				}
				result=JsonResult.getInstance().addOk().add("name", vps_name)
						.add("certNo", vps_certNo).add("orgCode", Convert.toStr(orgCode))
						.add("isShowTownFlag", isShowTownFlag)
						.add("orgProvinceCode", orgProvinceCode)
						.add("orgCityCode", orgCityCode)
						.add("orgAreaCode", orgAreaCode)
						.add("orgProvinceName", Convert.toStr(vps.get(VpsInfoService.OrgProvinceName)))
						.add("orgCityName", Convert.toStr(vps.get(VpsInfoService.OrgCityName)))
						.add("orgAreaName", Convert.toStr(vps.get(VpsInfoService.OrgAreaName)))
						/*-------------*/
//						.add("isShowTownFlag", true)
//						.add("orgProvinceCode", "34")
//						.add("orgCityCode", "3402")
//						.add("orgAreaCode", "340221")
//						.add("orgProvinceName", "安徽")
//						.add("orgCityName", "芜湖")
//						.add("orgAreaName", "芜湖县")
						/*-------------*/
						
						.add("ruleOutput", ruleOutput).add("isCredit", isCredit)
						.add("channelName", channelName)
						.toString();
			}
			log.info("userInfo userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("userInfo error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	public String queryCode(String postOrg, String parentCode){
		if(!StringUtils.isBlank(postOrg) && postOrg.length() > 2){
			// 使用汉字的前两位进行比较
			postOrg = postOrg.substring(0, 2)+"%";
			log.info("截取的省份名称是："+postOrg);
			PostOrgInfoExample example = new PostOrgInfoExample();
			example.createCriteria().andNameLike(postOrg).andParentCodeEqualTo(parentCode);
			//example.setOrderByClause("to_number(id)");
			List<PostOrgInfo> postOrgInfo = postMapper.selectByExample(example);
			if(postOrgInfo!=null&&postOrgInfo.size()>0){
				return postOrgInfo.get(0).getCode();
			}else{
				PostOrgInfoExample postExample = new PostOrgInfoExample();
				postExample.createCriteria().andParentCodeEqualTo(parentCode);
				//example.setOrderByClause("to_number(id)");
				List<PostOrgInfo> postInfo = postMapper.selectByExample(postExample);
				if(!CollectionUtils.isEmpty(postInfo)){
					return postInfo.get(0).getCode();
				}
			}
		}
		return "";
	}
	
	/**
	 * 额度续期用户判断账户是否冻结，贷款是否结清，身份证是否过有效期
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/xuqiCheckInfo")
	@ResponseBody
	public JSONPObject xuqiCheckInfo(HttpServletRequest request,@RequestParam String jsoncallback){
		Map<String, Object> resultMap =new HashMap<String, Object>();
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			resultMap = WildflyBeanFactory.getYcLimitRenewClient().appEvaluate(userOnlyId);
			String code = Convert.toStr(resultMap.get("code"));
			log.info("xuqiCheckInfo userOnlyId:"+userOnlyId+" code:"+code+";resultMap:"+resultMap);
//			String userOnlyId = "10000024271";
//			String code = "100006";
			Map<String,Object> dataMap = (Map<String,Object>)resultMap.get("dataMap");
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("userOnlyId", userOnlyId);
			String nextStep = creditRuleService.queryCreditRuleToPage(param);
			log.info("xuqiCheckInfo nextStep:"+nextStep);
			if(Check.isBlank(nextStep)){
				if("100006".equals(code)){
					if(!checkIdCardImg(userOnlyId)){//判断历史身份证是否可以使用
						nextStep = "APPCHECK_000";
					}else{
						nextStep = UhjConstant.ruleRefId.face_recognition;
						//判断是否需要同步身份信息
						synApplyDetail(dataMap, userOnlyId);
					}
					
				}else{
					nextStep = "APPCHECK_000";
				}
			}
			log.info("xuqiCheckInfo nextStep:"+nextStep);
			if(!"APPCHECK_000".equals(nextStep)&&!UhjConstant.ruleRefId.face_recognition.equals(nextStep)){
				//creditRule状态改为成功
				Map<String,Object> ruleMap = new HashMap<String, Object>();
				ruleMap.put("userOnlyId", userOnlyId);
				creditRuleService.saveCreditRuleOfBindCard(ruleMap);
				nextStep = creditRuleService.queryCreditRuleToPage(param);
			}else if(!"000000".equals(code) && dataMap!=null && !dataMap.isEmpty()){
				Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
				if(vps!=null){
					String vps_userName=Convert.toStr(vps.get(VpsInfoService.UsrName));
					String vps_certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
					dataMap.put("userName", vps_userName);
					dataMap.put("certNo", vps_certNo);
					resultMap.put("dataMap", dataMap);
				}
			}
			if(UhjConstant.ruleRefId.binding_bank_card.equals(nextStep)){
				log.info("xuqiCheckInfo nextStep:"+nextStep);
				nextStep = UhjConstant.ruleRefId.contact_state;
			}
			//判断是否需要同步账户信息
			if("100006".equals(code)||"100007".equals(code)||"200005".equals(code)){
				synAccountInfo(dataMap, userOnlyId);
			}
			resultMap.put("nextStep", nextStep);
			resultMap.put("isOldUser", "1");
			
			//判断是否是新用户的额度续期
			if(cerditApplyService.queryIsOldUser(userOnlyId)){
				log.info("xuqiCheckInfo 新用户 userOnlyId:"+userOnlyId+" isOld:0");
				resultMap.put("isOldUser", "0");
			}
			
			log.info("xuqiCheckInfo userOnlyId:"+userOnlyId+" nextStep:"+nextStep+" code:"+code);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
		} catch (Exception e) {
			log.error("xuqiCheckInfo error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	private boolean checkIdCardImg(String userOnlyId) {
		try{
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
			log.info("checkIdCardImg queryUserMessage res:"+res);
			JSONObject js =JSONObject.fromObject(res);
			String idCardImgUrl=Convert.toStr(js.get("idCardImgUrl"));
			String userName=Convert.toStr(js.get("userName"));
			String certNo=Convert.toStr(js.get("certNo"));
			if(Check.isBlank(idCardImgUrl) || Check.isBlank(userName) || Check.isBlank(certNo)){
				log.info("checkIdCardImg 11111111111 ");
				return false;
			}
			Map<String, String> paras = new HashMap<String, String>();
			paras.put("tranzCode", "3108");
			paras.put("idCardImgUrl", idCardImgUrl);
			paras.put("userOnlyId", userOnlyId);
			log.info("checkIdCardImg paras:"+paras);
			String ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, paras, UhjConstant.time_out);
			log.info("checkIdCardImg res:"+ret);
			JSONObject retjs=JSONObject.fromObject(ret);
			String rtn=Convert.toStr(retjs.get("rtn"));
			if(!"0".equals(rtn)){
				log.info("checkIdCardImg 222222222222222222 ");
				return false;
			}
			JSONObject idcard_ocr_result=JSONObject.fromObject(retjs.get("idcard_ocr_result"));
			String ocrName=Convert.toStr(idcard_ocr_result.get("name"));
			String ocrCertNo=Convert.toStr(idcard_ocr_result.get("citizen_id"));
			log.info("ocrName:"+ocrName+";ocrCertNo:"+ocrCertNo+";userName:"+userName+";certNo:"+certNo);
			if(Check.isBlank(ocrName) || Check.isBlank(ocrCertNo) || !ocrName.equals(userName) || !ocrCertNo.equals(certNo)){
				log.info("checkIdCardImg 3333333333333333 ");
				return false;
			}
			return true;
		}catch(Exception e){
			log.info("checkIdCardImg error ",e);
			return false;
		}
	}
	
	public Map<String,Object> synApplyDetail(Map<String,Object> dataMap,String userOnlyId) throws Exception{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		log.info("synApplyDetail dataMap:"+dataMap.toString());
		String name = Convert.toStr(dataMap.get("userName"));
		String gender = Convert.toStr(dataMap.get("gender"));
		String birthday = Convert.toStr(dataMap.get("birthday"));
		String certNo = Convert.toStr(dataMap.get("certNo"));
		String province = Convert.toStr(dataMap.get("householdRegisterProvice"));
		String city = Convert.toStr(dataMap.get("householdRegisterCity"));
		String area = Convert.toStr(dataMap.get("householdRegisterArea"));
		String address = Convert.toStr(dataMap.get("householdRegisterAddress"));
		String certPositiveImg = Convert.toStr(dataMap.get("certPositiveImg"));
		String certNegativeImg = Convert.toStr(dataMap.get("certNegativeImg"));
		String valiadIdCert = Convert.toStr(dataMap.get("valiadIdCert"));
		Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
		Map<String, Object> map =new HashMap<String, Object>();
		if(vps==null){
			resultMap.put("code", "9999");
			resultMap.put("msg", "您的邮掌柜信息为NULL，请联系邮政工作人员!");
		}else{
			map.put("name", name);
			map.put("gender", gender);
			map.put("birthday", birthday);
			map.put("certNo", certNo);
			map.put("address", address);
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.idcard);
			map.put("valiadIdCert", valiadIdCert);
			cerditApplyService.saveCertNoPositive(map);
			map.clear();
			map.put("productCode", UhjConstant.productCode.app_code);
			map.put("status", UhjConstant.applyStatus.APPLY_STATUS_UNACTIVE);
			map.put("userOnlyId", userOnlyId);
			cerditApplyService.saveCertApplyInformation(map);
			//保存个人信息  
			map.clear();
			//取渠道号、根据规则取营销渠道(邮储定义) 9-邮政 18-邮乐 3-邮储
			map=returnChannelMessage(vps, userOnlyId, name, certNo);
//			map.put("userName", name);
//			map.put("userOnlyId", userOnlyId);
//			map.put("orgCode", Convert.toStr(vps.get(VpsInfoService.OrgCode)));
			customerInfoService.saveCustomerInfo(map);
			map.clear();
			map.put("userOnlyId", userOnlyId);
			map.put("addressType", UhjConstant.addressType.registration_address);
			map.put("province", province);
			map.put("city", city);
			map.put("area", area);
			map.put("address", address);
			map.put("customerType", UhjConstant.customerType.loanor);
			customerAddressService.saveCustomerCertAddress(map);
			map.clear();
			map.put("userOnlyId", userOnlyId);
			map.put("addressType", UhjConstant.addressType.home_address);
			map.put("province", province);
			map.put("city", city);
			map.put("area", area);
			map.put("address", address);
			map.put("customerType", UhjConstant.customerType.loanor);
			customerAddressService.saveCustomerCertAddress(map);
			//保存身份证照片
			map.clear();
			if(!Check.isBlank(certPositiveImg)){
				map.put("type", UhjConstant.imageType.app_IdCardPositive);
				map.put("url", certPositiveImg);
				map.put("userOnlyId", userOnlyId);
				applyImageService.saveApplyImage(map);
			}
			map.clear();
			if(!Check.isBlank(certNegativeImg)){
				map.put("type", UhjConstant.imageType.app_IdCardOpposite);
				map.put("url", certNegativeImg);
				map.put("userOnlyId", userOnlyId);
				applyImageService.saveApplyImage(map);
			}
			resultMap.put("code", "0000");
			resultMap.put("msg", "同步成功");
			log.info("synApplyDetail resultMap "+resultMap.toString());
		}
		return resultMap;
	}
	
	private Map<String,Object> returnChannelMessage(Map<String, Object> vps,String userOnlyId,String name,String certNo) throws Exception{
		log.info("returnChannelMessage userOnlyId:"+userOnlyId+"; name:"+name+";certNo:"+certNo);
		Map<String, Object> map =new HashMap<String, Object>();
//		try{
			String channelCode="C0001";
			String channelName="邮乐掌柜贷"; 
			map.put("userOnlyId", userOnlyId);
			map.put("userName", name);
			map.put("certNo", certNo);
			Map<String, Object> whiteMap=customerWhiteService.queryCustomerWhite(map);
			if(whiteMap!=null){
				CustomerWhite white =(CustomerWhite) whiteMap.get("CustomerWhite");
				channelCode=white.getChannelCode();
				channelName=white.getChannelName();
			}
			String orgProvinceName=Convert.toStr(vps.get(VpsInfoService.OrgProvinceName));
			if(orgProvinceName.indexOf("河北")>=0){
				channelCode=UhjConstant.channelCode.ZGD_HEBEI_CHANNEL;//如果是河北的传C0002
				channelName="邮储掌柜贷"; 
			}
			//根据规则取营销渠道(邮储定义) 9-邮政 18-邮乐 3-邮储
			String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
			JSONObject js =new JSONObject();
			JSONObject setmap = new JSONObject();
			setmap.put("ruleSetId", "channelSaleRel");
			setmap.put("userOnlyId", userOnlyId);
			setmap.put("channelCode", channelCode);
			js.put("data", setmap.toString());
			log.info("returnChannelMessage sendPostJson:"+js.toString());
			String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
					rule_url, js.toString(), UhjConstant.time_out);
			log.info("returnChannelMessage userOnlyId:"+userOnlyId+"; rule res:"+res);
			JSONObject resJs=JSONObject.fromObject(res);
			if("000000".equals(resJs.get("code"))){
				JSONObject object=(JSONObject) resJs.get("object");
				JSONArray data=object.getJSONArray("data");
				JSONObject strs=(JSONObject) data.toArray()[0];
				String ruleOutputResult=Convert.toStr(strs.get("ruleOutputResult"));
				map.put("ruleOutputResult", ruleOutputResult);
				log.info("returnChannelMessage userOnlyId:"+userOnlyId+";ruleOutputResult:"+ruleOutputResult);
			}
			map.put("channelCode", channelCode);
			map.put("channelName", channelName);
			map.put("orgCode", Convert.toStr(vps.get(VpsInfoService.OrgCode)));
//		}catch (Exception e) {
//			log.error("certNoPositiveInformation ruleOutputResult error! userOnlyId"+userOnlyId,e);
//		}
		return map;
	}
	
	public Map<String,Object> synAccountInfo(Map<String,Object> dataMap,String userOnlyId) throws Exception{
		log.info("synAccountInfo dataMap:"+dataMap.toString()+" userOnlyId:"+userOnlyId);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userOnlyId", userOnlyId);
		map.put("cardNo", Convert.toStr(dataMap.get("cardNo")));
		map.put("certNo", Convert.toStr(dataMap.get("certNo")));
		map.put("userName", Convert.toStr(dataMap.get("userName")));
		map.put("mobileNo", Convert.toStr(dataMap.get("mobileNo")));
		map.put("subbranchName", "appxuqi");
		Map<String,Object> resultMap = bindCardService.saveBindCardInfo(map);
		log.info("synAccountInfo resultMap:"+resultMap);
		return resultMap;
	}
	
	private boolean checkUserAge(String certNo) throws Exception {
		if(Check.isBlank(certNo)){
			return false;
		}
		certNo = certNo.substring(6, 14);// 取身份证的年月日
		double age = DateUtil.getAge(certNo,DateUtil.YMD_SIMPLE, 2);
		if (age <= 65)
			return true;
		return false;
	}
	/**
	 * 保存掌柜身份证正面信息
	 * 并创建申请记录表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/aap_positive")
	@ResponseBody
	public JSONPObject certNoPositiveInformation(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("certNoPositiveInformation begin.");
		try {
			String result=JsonResult.getInstance().addOk().toString();
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("certNoPositiveInformation userOnlyId "+userOnlyId);
			String name=request.getParameter("name");
			String gender=request.getParameter("gender");//性别
			String birthday=request.getParameter("birthday");
			String certNo =request.getParameter("citizen_id");
			String address=request.getParameter("address");
			log.info("certNoPositiveInformation  name="+name+";gender="+gender+";birthday="+birthday+";certNo="+certNo+";address="+address);
			if(name==null || certNo==null || gender==null || birthday==null || address==null){
				result=JsonResult.getInstance().addError("您的身份信息有误，请重新拍照！").toString();
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.px_limit_apply);
			String pxlimitapply=creditRuleService.queryCreditRuleRuleOutput(ruleMap);
			ruleMap.clear();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.binding_bank_card);
			String ruleOutput=creditRuleService.queryCreditRuleRuleOutput(ruleMap);
			if(ruleOutput!=null && pxlimitapply!=null && "true".equals(ruleOutput)   && "true".equals(pxlimitapply)){
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
				map.put("certType", UhjConstant.certType.idcard);
				map=customerInfoService.queryCustomerInfo(map);
				String customer_userName=Convert.toStr(map.get("name"));
				String customer_certNo=Convert.toStr(map.get("certNo"));
				if(customer_userName!=null && customer_certNo!=null && (!customer_userName.equals(name) || !customer_certNo.equals(certNo))){
					result=JsonResult.getInstance().addError("您的身份信息和绑卡信息不一致请重新拍照!").toString();
					log.info("certNoPositiveInformation userOnlyId:"+userOnlyId+"; result "+result);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}
			}
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			if(vps==null){
				result=JsonResult.getInstance().addError("您的邮掌柜信息为NULL，请联系邮政工作人员!").toString();
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}else{
				String vps_userName=Convert.toStr(vps.get(VpsInfoService.UsrName));
				String vps_certNo=Convert.toStr(vps.get(VpsInfoService.CertNo),"");
				vps_certNo=vps_certNo.replaceAll(" ", "").replace("x", "X");
				if(Check.isBlank(vps_userName) || Check.isBlank(vps_certNo) || !name.equals(vps_userName.replaceAll(" ", "")) || !certNo.equals(vps_certNo.replaceAll(" ", ""))){
					result= JsonResult.getInstance().addError("您的身份证信息与邮掌柜系统登记信息不一致，请联系邮政工作人员!").toString();
					log.info("certNoPositiveInformation userOnlyId:"+userOnlyId+"; result "+result);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}else if(!checkUserAge(certNo)){
					result= JsonResult.getInstance().addError("抱歉，您的年龄已经超过65周岁，无法申请掌柜贷!").toString();
					log.info("certNoPositiveInformation userOnlyId:"+userOnlyId+"; result "+result);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}
				
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
				map.put("userName", vps_userName.replaceAll(" ", ""));
				map.put("certNo", vps_certNo.replaceAll(" ", "").replace("x", "X"));
				//判断用户是否充分申请，并取到重复申请的机构号
				String orgCode=cerditApplyService.booleanCustomInfo(map);
				if(!"true".equals(orgCode)){
					result=JsonResult.getInstance().addError("该掌柜信息已申请过掌柜贷业务，请勿重复申请，如有疑问，请拨打11185转4").toString();
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}
			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("name", name);
			map.put("gender", gender);
			map.put("birthday", birthday);
			map.put("certNo", certNo);
			map.put("address", address);
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.idcard);
			cerditApplyService.saveCertNoPositive(map);
			map.clear();
			map.put("productCode", UhjConstant.productCode.app_code);
			map.put("status", UhjConstant.applyStatus.APPLY_STATUS_UNACTIVE);
			map.put("userOnlyId", userOnlyId);
			cerditApplyService.saveCertApplyInformation(map);
			
			map.clear();
			//取渠道号、根据规则取营销渠道(邮储定义) 9-邮政 18-邮乐 3-邮储
			map=returnChannelMessage(vps, userOnlyId, name, certNo);
			//保存个人信息  
//			map.put("userName", name);
//			map.put("userOnlyId", userOnlyId);
//			map.put("channelCode", channelCode);
//			map.put("channelName", channelName);
			map.put("orgCode", Convert.toStr(vps.get(VpsInfoService.OrgCode)));
			customerInfoService.saveCustomerInfo(map);
			
			map.clear();
			map.put("userOnlyId", userOnlyId);
			map.put("addressType", UhjConstant.addressType.registration_address);
			map.put("address", address);
			map.put("customerType", UhjConstant.customerType.loanor);
//			addressMap.put("propertyType", propertyType);
//			addressMap.put("storeInnerGPS", storeInnerGPS);
			String ret=customerAddressService.saveCustomerAddress(map);
			if("1000".equals(ret)){
				map.clear();
				map.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				// 身份证地址识别结果
				map.put("ruleRefId", UhjConstant.ruleRefId.cert_photos);
				map.put("userOnlyId", userOnlyId);
				String returnFlag=creditRuleService.saveCreditRuleService(map);
				if("1".equals(returnFlag)){
					result=JsonResult.getInstance().addOk().toString();
				}else{
					result=JsonResult.getInstance().addError("您的身份证照片模糊，请重新拍照！").toString();
				}
			}else{
				map.clear();
				map.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				map.put("ruleRefId", UhjConstant.ruleRefId.cert_photos);
				map.put("userOnlyId", userOnlyId);
				creditRuleService.saveCreditRuleService(map);
				result=JsonResult.getInstance().addOk().toString();
			}
			//判断是否绑定地推人员，如未绑定则查询在邮乐网是否绑定地推人员信息  如未绑定则定位未绑定
			try{
				Map<String, Object> param =new HashMap<String, Object>();
				param.put("userOnlyId", userOnlyId);
				param.put("certType", UhjConstant.certType.idcard);
				param=customerInfoService.queryCustomerInfo(param);
				String channel=Convert.toStr(param.get("channelCode"));
				log.info("channel "+channel);
				if(Check.isBlank(channel) || UhjConstant.channelCode.ZGD_CHANNEL.equals(channel)|| UhjConstant.channelCode.ZGD_HEBEI_CHANNEL.equals(channel)){
					log.info("queryPostMember userOnlyId:"+userOnlyId);
					boolean hasBindYou = cerditApplyService.queryPostMember(userOnlyId);
					if(!hasBindYou){
						log.info("handleChinaPostMember userOnlyId:"+userOnlyId);
						map.clear();
						map.put("userOnlyId", userOnlyId);
						map.put("flag", "2");
						WildflyBeanFactory.getZgdAppClient().handleChinaPostMember(map);
					}
					boolean hasBind = cerditApplyService.queryHasBindPostMember(userOnlyId);
					if(!hasBind){
						log.info("queryHasBindPostMember userOnlyId:"+userOnlyId);
						map.clear();
						map.put("userOnlyId", userOnlyId);
						map.put("flag", "3");
						WildflyBeanFactory.getZgdAppClient().handleChinaPostMember(map);
					}
					log.info("certNoPositiveInformation userOnlyId:"+userOnlyId+"; result "+result);
				}
			} catch (Exception e) {
				log.error("certNoPositiveInformation error!",e);
			}
			log.info("certNoPositiveInformation userOnlyId:"+userOnlyId+"; result "+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("certNoPositiveInformation error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 保存掌柜身份证反面信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/aap_negative")
	@ResponseBody
	public JSONPObject certNoNegativeInformation(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("certNoNegativeInformation begin.");
		try {
			String result=null;
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("certNoNegativeInformation userOnlyId "+userOnlyId);
			String signOrg  =request.getParameter("agency");
			String validStart =request.getParameter("valid_date_begin");
			String validEnd =request.getParameter("valid_date_end");
			log.info("certNoNegativeInformation  signOrg="+signOrg+";validStart="+validStart+";validEnd="+validEnd);
			if(validEnd==null){
				result=JsonResult.getInstance().addError("您的身份信息有误，请重新拍照！").toString();
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("signOrg", signOrg);
			map.put("validStart", validStart);
			map.put("validEnd", validEnd);
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.idcard);
			cerditApplyService.saveCertNoNegative(map);
			if(validEnd.indexOf("期")<0){
				Integer count = DateUtil.diffDays(validEnd, DateUtil.currDateSimpleStr(), DateUtil.YMD_SIMPLE);
				if(count<1){
					result= JsonResult.getInstance().addError("抱歉，您的身份证已经过了有效期，无法申请掌柜贷!").toString();
				}else{
					result=JsonResult.getInstance().addOk().toString();
				}
			}else{
				result=JsonResult.getInstance().addOk().toString();
			}
			log.info("certNoNegativeInformation userOnlyId:"+userOnlyId+";result "+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("certNoNegativeInformation error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 保存配偶的身份证件正面信息以及手机号
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/aap_spousePositive")
	@ResponseBody
	public JSONPObject spousePositiveInformation(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("spousePositiveInformation begin.");
		try {
			String result="";
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("spousePositiveInformation userOnlyId "+userOnlyId);
			String name=request.getParameter("name");
			String gender=request.getParameter("gender");//性别
			String birthday=request.getParameter("birthday");
			String certNo =request.getParameter("citizen_id");
			String address=request.getParameter("address");
			String mobileNo=request.getParameter("mobileNo");
			log.info("spousePositiveInformation userOnlyId:"+userOnlyId+"; name="+name+";gender="+gender+";birthday="+birthday+";certNo="+certNo+";address="+address);
			if(name==null || certNo==null || gender==null || birthday==null || address==null){
				result=JsonResult.getInstance().addError("您配偶的身份信息核查失败，请重新拍照！").toString();
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("certType", UhjConstant.certType.idcard);
			map.put("userOnlyId", userOnlyId);
			Map<String, Object> resMap=customerInfoService.queryCustomerInfo(map);
			String owrgender=Convert.toStr(resMap.get("gender"));
			if(UhjConstant.gender.female.equals(owrgender)){
				owrgender="女";
			}
			if(UhjConstant.gender.male.equals(owrgender)){
				owrgender="男";
			}
			if(gender.equals(owrgender)){
				String ret=JsonResult.getInstance().addError("您配偶的身份证信息核查失败！").toString();
				log.info("spousePositiveInformation userOnlyId:"+userOnlyId+"; ret:"+ret);
				String env = PropertiesHelper.getDfs("env");
				if(!"beta".equals(env) && !"testing".equals(env)){
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ret));
				}
				
			}
			
			map.clear();
			map.put("userOnlyId", userOnlyId);
			map.put("imageType", UhjConstant.imageType.app_spouseICPositive);
			log.info("spousePositiveInformation userOnlyId:"+userOnlyId+"; map:"+map);
			String idCardImgUrl=applyImageService.queryApplyImageService(map);
			if(idCardImgUrl ==null){
				result=JsonResult.getInstance().addError("图片上传失败，请重新拍照").toString();
				log.info("spousePositiveInformation  userOnlyId "+userOnlyId+"; result:"+result);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}
			log.info("spousePositiveInformation  userOnlyId "+userOnlyId+"; idCardImgUrl:"+idCardImgUrl);
			try{
				String res="";
				Map<String, Object> interfaceMap =new HashMap<String, Object>();
				interfaceMap.put("userOnlyId", userOnlyId);
				interfaceMap.put("tranzCode", "3103");
				List<InterfaceAccessInfoWithBLOBs> interfaceList= interfaceAccessInfoService.queryInterfaceAccessInfoWithBLOBs(interfaceMap);
				if(interfaceList!=null && interfaceList.size()>0){
					for(InterfaceAccessInfoWithBLOBs blob:interfaceList){
						String requestInfo=new String(blob.getRequestInfo());
						log.info("spousePositiveInformation userOnlyId:"+userOnlyId+"; 数据库中依图接口日志 requestInfo:"+requestInfo);
						JSONObject js =JSONObject.fromObject(requestInfo);
						String userName=Convert.toStr(js.get("name"));
						String citizen_id=Convert.toStr(js.get("citizen_id"));
						log.info("spousePositiveInformation userOnlyId:"+userOnlyId+"; 数据库中依图接口日志   name:"+name+";userName:"+userName+ "certNo:"+certNo+";citizen_id:"+citizen_id);
						if(userName.equals(name) && certNo.equals(citizen_id)){
							res=new String(blob.getResponseInfo());
							log.info("spousePositiveInformation userOnlyId:"+userOnlyId+"; 校验姓名和身份证号码返回结果数据库中依图接口日志    result:"+result);
							break;
						}
					}
				}
				if(Check.isBlank(res)){
					//调用亿图接口身份证信息校验
					Map<String, String> paras = new HashMap<String, String>();
					paras.put("tranzCode", "3103");
					paras.put("idCardImgUrl", idCardImgUrl);
					paras.put("name", name);
					paras.put("citizen_id", certNo);
					paras.put("userOnlyId", userOnlyId);
					log.info("spousePositiveInformation 依图接口日志 paras:"+paras);
					res= com.ule.uhj.util.http.HttpClientUtil.sendPost(
							appkey_url, headers, paras, UhjConstant.time_out);
					log.info("spousePositiveInformation 依图接口日志 res:"+res);
				}
				//处理相识度结果
				JSONObject data=JSONObject.fromObject(res);
				JSONObject identify_result =JSONObject.fromObject(data.get("identify_result"));
				String rtn_identify=Convert.toStr(identify_result.get("rtn"));
				String is_valid=Convert.toStr(identify_result.get("is_valid"));
				if(("6300".equals(rtn_identify) || "6301".equals(rtn_identify)) && !"0".equals(rtn_identify)){//身份证号和姓名不一致
					String status=checkIdCardNumber(idCardImgUrl, name, certNo, userOnlyId);
					if(!"01".equals(status)){
						result=JsonResult.getInstance().addError("您的身份信息核查失败，请确保该身份证真实有效！").toString();
						log.info("spousePositiveInformation  userOnlyId "+userOnlyId+"; result:"+result);
						return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
					}
				}
				if(!"0".equals(rtn_identify) && !"6304".equals(rtn_identify)){
					String status=checkIdCardNumber(idCardImgUrl, name, certNo, userOnlyId);
					if(!"01".equals(status)){
						result=JsonResult.getInstance().addError("大数据验证结果异常，请重新拍照！").toString();
						log.info("spousePositiveInformation  userOnlyId "+userOnlyId+"; result:"+result);
						return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
					}
				}
				if(!"true".equals(is_valid)  && !"6304".equals(rtn_identify)){
					String status=checkIdCardNumber(idCardImgUrl, name, certNo, userOnlyId);
					if(!"01".equals(status)){
						result=JsonResult.getInstance().addError("您的身份信息核查失败，请确认姓名和身份证号是否正确。请重新拍照！").toString();
						log.info("spousePositiveInformation  userOnlyId "+userOnlyId+";result:"+result);
						return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
					}
				}
			}catch(Exception e){
				log.info("spousePositiveInformation 依图接口报错使用阿里接口  userOnlyId "+userOnlyId+";result:"+result);
				String status=checkIdCardNumber(idCardImgUrl, name, certNo, userOnlyId);
				if(!"01".equals(status)){
					result=JsonResult.getInstance().addError("您的身份信息核查失败，请确保该身份证真实有效！").toString();
					log.info("spousePositiveInformation  userOnlyId "+userOnlyId+"; result:"+result);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}
			}
			
				
			// 保存配偶的身份证信息	
			map.clear();
			map.put("name", name);
			map.put("gender", gender);
			map.put("birthday", birthday);
			map.put("certNo", certNo);
			map.put("address", address);
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.spouse_idcard);//配偶的身份证
			result=cerditApplyService.saveCertNoPositive(map);
			// 保存到联系人信息表
			map.clear();
			map.put("mobileNo", mobileNo);
			map.put("userOnlyId", userOnlyId);
			map.put("contactType", UhjConstant.contactType.spouse);
			map.put("contactName", name);
			map.put("maritalStatus", UhjConstant.maritalStatus.married);
			contactsService.saveContactsInfo(map);
			
			//保存（已婚）联系人过程结束
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.contact_state);
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
			creditRuleService.saveCreditRuleService(ruleMap);
			log.info("spousePositiveInformation userOnlyId:"+userOnlyId+";result "+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("spousePositiveInformation error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	//调用阿里身份证实名认证接口
	private String checkIdCardNumber(String idCardImgUrl,String name,String certNo,String userOnlyId){
		try{
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> paras = new HashMap<String, String>();
			paras.put("tranzCode", "1104");
			paras.put("idCardImgUrl", idCardImgUrl);
			paras.put("name", name);
			paras.put("citizen_id", certNo);
			paras.put("userOnlyId", userOnlyId);
			log.info("checkIdCardNumber 阿里身份证实名认证接口日志 paras:"+paras);
			String ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, paras, UhjConstant.time_out);
			log.info("checkIdCardNumber 阿里身份证实名认证依图接口日志 ret:"+ret);
			JSONObject ject=JSONObject.fromObject(ret);
			String status=Convert.toStr(ject.get("status"));
			return status;
		} catch (Exception e) {
			log.error("checkIdCardNumber error",e);
		}
		return "02";
	}
	
	/**
	 * 保存掌柜地推人员信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryPostMemberInfo")
	@ResponseBody
	public JSONPObject queryPostMemberInfo(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("queryPostMemberInfo begin.");
		try {
			String result=null;
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("queryPostMemberInfo userOnlyId "+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
//			param.put("userOnlyId", userOnlyId);
//			param.put("certType", UhjConstant.certType.idcard);
//			param=customerInfoService.queryCustomerInfo(param);
//			String channelCode=Convert.toStr(param.get("channelCode"));
//			log.info("queryPostMemberInfo userOnlyId "+userOnlyId+";channelCode:"+channelCode);
//			if(!Check.isBlank(channelCode) && !UhjConstant.channelCode.ZGD_CHANNEL.equals(channelCode)){
//				Map<String, Object> resultMap =new HashMap<String, Object>();
//				resultMap.put("code", 2000);
//				resultMap.put("msg", "非邮乐渠道不绑定地推人员");
//				log.info("queryPostMemberInfo 非邮乐渠道不绑定地推人员 userOnlyId "+userOnlyId+";channelCode:"+channelCode);
//				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
//			}
			//判断是否已有地推人员信息
			boolean hasBind = cerditApplyService.queryPostMember(userOnlyId);
			
			String vps_certNo="";
			String vps_userName="";
			if(hasBind){
				Map<String, Object> resultMap =new HashMap<String, Object>();
				resultMap.put("code", 2000);
				resultMap.put("msg", "已绑定地推人员信息");
				log.info("queryPostMemberInfo  userOnlyId "+userOnlyId+" resultMap "+resultMap);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
			}
			
			
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			String channelCode=UhjConstant.channelCode.ZGD_CHANNEL;
			if(vps==null){
				result=JsonResult.getInstance().addError("您的邮掌柜信息为NULL，请联系邮政工作人员!").toString();
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}else{
				vps_userName=Convert.toStr(vps.get(VpsInfoService.UsrName),"");
				vps_certNo=Convert.toStr(vps.get(VpsInfoService.CertNo),"");
				if(Check.isBlank(vps_userName) || Check.isBlank(vps_certNo)){
					result= JsonResult.getInstance().addError("您的姓名或身份证号码不正确，请联系邮政工作人员!").toString();
					log.info("certNoPositiveInformation userOnlyId:"+userOnlyId+"; result "+result);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
				}
				map.clear();
				map.put("userOnlyId", userOnlyId);
				map.put("userName", vps_userName.trim());
				map.put("certNo", vps_certNo.trim());
				Map<String, Object> whiteMap=customerWhiteService.queryCustomerWhite(map);
				if(whiteMap!=null){
					CustomerWhite white =(CustomerWhite) whiteMap.get("CustomerWhite");
					channelCode=white.getChannelCode();
				}
				log.info("queryPostMemberInfo userOnlyId "+userOnlyId+";channelCode:"+channelCode);
				if(!Check.isBlank(channelCode) && getBingStaff(channelCode,"1")){
					Map<String, Object> resultMap =new HashMap<String, Object>();
					resultMap.put("code", 2000);
					resultMap.put("msg", "非邮乐渠道不绑定地推人员");
					log.info("queryPostMemberInfo 非邮乐渠道不绑定地推人员 userOnlyId "+userOnlyId+";channelCode:"+channelCode+";resultMap:"+resultMap);
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
				}
			}
			
			String mobileNo  =request.getParameter("mobileNo");
			log.info("queryPostMemberInfo  userOnlyId "+userOnlyId+" mobileNo="+mobileNo);
			if(Check.isBlank(mobileNo)){
				result=JsonResult.getInstance().addError("地推人员手机号为空，请重新输入！").toString();
				log.info("queryPostMemberInfo  userOnlyId "+userOnlyId+" result "+result);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
			}
			boolean flag=false;
//			String value=customerInfoService.getIsSuperKeyWord("hebei_white_phone");
//			log.info("queryPostMemberInfo  userOnlyId "+userOnlyId+" value "+value);
//			String values[]=value.split("@");
//			for(String key:values){
//				if(mobileNo.equals(key)){
//					flag=true;
//				}
//			}
			String orgProvinceName=Convert.toStr(vps.get(VpsInfoService.OrgProvinceName),"");
			String OrgCityName=Convert.toStr(vps.get(VpsInfoService.OrgCityName),"");
			
			
			//productInfoN 现金
			ProductInfoN productInfo = ProductInfoNService.getProductInfoN(channelCode,"1");
			log.info("查询产品表结果 : "+JSON.toJSONString(productInfo));
			
			if(productInfo!=null){
				//产品配置化
				try {
					String personBelongApply = productInfo.getPersonBelongApply();//特定的申请人
					List<String> certNoList = StringUtil.getListByString(personBelongApply, ",");
					if(certNoList!=null && certNoList.contains(vps_certNo.replace("x", "X"))){
						flag = true;
					}else{
						String scopeBelongApply = productInfo.getScopeBelongApply();//申请范围
						String orgprovince = orgProvinceName.substring(0, 2);
						if(StringUtils.isNotBlank(scopeBelongApply) && scopeBelongApply.indexOf(orgprovince)<0){
							log.info("申请范围不在产品配置中 不予申请");
							flag = false;
						}else{
							flag=true;
						}		
					}
				} catch (Exception e) {
					//代码出现问题，走原有逻辑
					if(orgProvinceName.indexOf("广东")>0){
						flag=false;
					}else if(orgProvinceName.indexOf("河北")>0 && (orgProvinceName.indexOf("河北")>=0 && (OrgCityName.indexOf("张家口")>=0 || OrgCityName.indexOf("唐山")>=0))){
						flag=false;
					}else{
						flag=true;
					}
				}
			}else{
				//产品没有配置走原有逻辑
				if(orgProvinceName.indexOf("广东")>=0){//广东的不给申请
					flag=false;
				}else if(orgProvinceName.indexOf("河北")>=0 && (OrgCityName.indexOf("张家口")>=0 || OrgCityName.indexOf("唐山")>=0)){
					//河北的张家口和唐山的也都不给申请
					flag=false;
				}else{
					flag=true;
				}
			}
			
			if(flag){
				map.clear();
				map.put("mobileNo", mobileNo);
				map.put("userOnlyId", userOnlyId);
				Map<String,Object> resultMap = WildflyBeanFactory.getZgdAppClient().handleChinaPostMember(map);
				log.info("queryPostMemberInfo  userOnlyId "+userOnlyId+" resultMap "+resultMap);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
			}else{
				Map<String, Object> resultMap =new HashMap<String, Object>();
				resultMap.put("code", 3000);
				resultMap.put("msg", "该地区不可以申请掌柜贷"+orgProvinceName);
				log.info("queryPostMemberInfo  userOnlyId "+userOnlyId+" resultMap "+resultMap);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
			}
		} catch (Exception e) {
			log.error("queryPostMemberInfo error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询全国所有机构并拼接成树形结构
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryOrgInfo")
	@ResponseBody
	public JSONPObject queryOrgInfo(@RequestParam String jsoncallback){
		log.info("queryOrgInfo begin.");
		Map<String, Object> map = ResultUtil.successMap();
		try {
			List<PostOrgInfo> list = postOrgInfoService.buildOrgInfoTree();
			map.put(YzsConstants.DATA, list);
//			log.info("queryOrgInfo succeed! and the orgInfo is : "+JsonUtil.getJsonStringFromMap(map));
		} catch (Exception e) {
			log.info("queryOrgInfo error!",e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询全国所有机构并拼接成树形结构
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/saveOrgInfo")
	@ResponseBody
	public JSONPObject saveOrgInfo(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("saveOrgInfo begin.");
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			String provinceOrg = request.getParameter("provinceOrg");
			String cityOrg = request.getParameter("cityOrg");
			String areaOrg = request.getParameter("areaOrg");
			String townOrg = request.getParameter("townOrg");
			String orgCode = null;
			// 获取vps信息
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			if(!CollectionUtils.isEmpty(vps)) {
				orgCode=Convert.toStr(vps.get(VpsInfoService.OrgCode));
			}
			
			CreditPostmember member = new CreditPostmember();
			member.setUserOnlyId(userOnlyId);
			member.setOrgProvince(provinceOrg);
			member.setOrgCity(cityOrg);
			member.setOrgArea(areaOrg);
			member.setOrgTown(townOrg);
			member.setMemberType("1");
			
			postOrgInfoService.saveOrgInfo(member, orgCode);
			
			log.info("saveOrgInfo succeed!");
		} catch (Exception e) {
			log.info("saveOrgInfo error!",e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	
	/**;
	 * 暂没用到
	 * 保存配偶的身份证件反面信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	/*@RequestMapping("/aap_spouseNegative")
	@ResponseBody
	public JSONPObject spouseNegativeInformation(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("spouseNegativeInformation begin.");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("spouseNegativeInformation userOnlyId "+userOnlyId);
			String signOrg  =request.getParameter("agency");
			String validStart =request.getParameter("valid_date_begin");
			String validEnd =request.getParameter("valid_date_end");
			log.info("spouseNegativeInformation  signOrg="+signOrg+";validStart="+validStart+";validEnd="+validEnd);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("signOrg", signOrg);
			map.put("validStart", validStart);
			map.put("validEnd", validEnd);
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.spouse_idcard);//配偶的身份证
			String result=cerditApplyService.certNoNegativeInformation(map);
			log.info("spouseNegativeInformation result "+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("spouseNegativeInformation error!",e);
			return new JSONPObject(jsoncallback, "系统异常");
		}
	}*/
	
	
	public static void main(String[] args){
		String certNo = "362226199009140632";
		String birthday = certNo.substring(6, 14);    
        Date birthdate = null;
		try {
			birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
		} catch (ParseException e) {
//			e.printStackTrace();
		}
        birthday =  new SimpleDateFormat("yyyy-MM-dd").format(birthdate);
	    System.out.println(birthday) ; 
		 // 获取性别    
        String id17 = certNo.substring(16, 17);    
        if (Integer.parseInt(id17) % 2 != 0) {    
            System.out.println("男");   
        } else {    
        	 System.out.println("女");      
        } 
	}
	
	
	public boolean getBingStaff(String channelCode, String useType) {
		boolean rs=false;
		ProductInfoN productInfo = ProductInfoNService.getProductInfoN(channelCode,useType);
		log.info("查询产品表结果 :"+JSON.toJSONString(productInfo));
		if(productInfo!=null && StringUtil.isNotEmpty(productInfo.getBindStaff())){
			if("0".equals(productInfo.getBindStaff())){
				rs=true;
			}else if("1".equals(productInfo.getBindStaff())){
				rs=false;
			}
		}else{
			if(!UhjConstant.channelCode.ZGD_CHANNEL.equals(channelCode) && !UhjConstant.channelCode.ZGD_HEBEI_CHANNEL.equals(channelCode)){
				rs=true;
			}else{
				rs=false;
			}
		}
		return rs;
	}
}