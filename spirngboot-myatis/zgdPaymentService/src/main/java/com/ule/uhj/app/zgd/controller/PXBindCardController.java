package com.ule.uhj.app.zgd.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditRule;
import com.ule.uhj.app.zgd.service.BindCardService;
import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.ManualCreditService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.SendMessageClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.ejb.client.zgd.ZgdCacheClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;
import com.ule.web.util.Tools;

@Controller
@RequestMapping("/pxZgd")
public class PXBindCardController {

	private static Logger log = LoggerFactory.getLogger(PXBindCardController.class);
	
	/**
	 * 随机码短信类型 
	 */
	private static String SMT_VALIDCODE = "280102";
	
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private CreditApplyService creditApplyService;
	@Autowired
	private ManualCreditService manualCreditService;
	@Autowired
	private CustomerInfoService customerInfoService;
	
	/**
	 * status   0    提示没有批销额度的资质 （点击掌柜贷按钮的时候就处理了,按理说这种情况进不到这里）
	 * 			2    跳转建额失败页面（点击掌柜贷按钮的时候就处理了,按理说这种情况进不到这里）
	 * 			1    跳转批销绑卡建额页面
	 * 			3、跳转完善资料页面
	 */
	@RequestMapping("/queryPXLimit")
	@ResponseBody
	public JSONPObject px_queryPXLimit(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		try{
			String result =JsonResult.getInstance().addOk().add("status", "0").toJsonStr();
			String userOnlyId=getUserOnlyId(request);
			log.info("px_queryPXLimit param:userOnlyId:"+userOnlyId);
			List<CreditApply> list = creditApplyService.queryInfoByUserOnlyId(userOnlyId);
			if(list!=null && list.size()>0) {
				CreditApply ca = list.get(0);
				if((null != ca.getCreditLimit() && ca.getCreditLimit().doubleValue() > 0.0) || (null != ca.getCreditBaseAmount() && ca.getCreditBaseAmount().doubleValue() > 0.0)) {
					//  判断是否已经建额失败了的，去失败页面    status=2
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.px_limit_apply);
					String ruleOutput=creditRuleService.queryCreditRuleRuleOutput(ruleMap);
					if(ruleOutput!=null && "false".equals(ruleOutput)){
						result =JsonResult.getInstance().addOk().add("status", "2").toJsonStr();
					}else{
						result =JsonResult.getInstance().addOk().add("status", "1").toJsonStr();
					}
					/*Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
					if(vps==null){
						result=JsonResult.getInstance().addError("网络异常，请稍后再试！").add("status", "0").toString();
						log.info("px_queryPXLimit result:"+result);
						return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
					}
					Map<String, Object> villageAddress = VpsInfoService.getVillageAddressInfoByUserOnlyId(userOnlyId);
					if(villageAddress==null){
						result=JsonResult.getInstance().addError("网络异常，请稍后再试！").add("status", "0").toString();
						log.info("px_queryPXLimit result:"+result);
						return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
					}else{
						String provinceName = Convert.toStr(villageAddress.get("provinceName"));
						String cityName = Convert.toStr(villageAddress.get("cityName"));
						String areaName = Convert.toStr(villageAddress.get("areaName"));
						String stationAddress = Convert.toStr(villageAddress.get("stationAddress"));
						String UsrName = Convert.toStr(vps.get(VpsInfoService.UsrName));
						String CertNo = Convert.toStr(vps.get(VpsInfoService.CertNo));
						if(Check.isBlank(provinceName) || Check.isBlank(cityName) || 
								Check.isBlank(areaName) || Check.isBlank(stationAddress)||
								Check.isBlank(UsrName) || Check.isBlank(CertNo)){
							String bzgMobile="";
							String bzgName="";
							Map<String, Object> vpsBzg=creditApplyService.queryBZGCreditPostMember(userOnlyId);
							if(vpsBzg!=null){
								bzgMobile=Convert.toStr(vpsBzg.get("bzgMobile"));
								bzgName=Convert.toStr(vpsBzg.get("bzgName"));
							}
							//跳转完善资料页面
							result =JsonResult.getInstance().addOk().add("status", "3")
									.add("bzgName", bzgName).add("bzgMobile", bzgMobile).toJsonStr();
							log.info("px_queryPXLimit result:"+result);
							return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
						}
					}
					//  判断是否已经建额失败了的，去失败页面    status=2
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.px_limit_apply);
					String ruleOutput=creditRuleService.queryCreditRuleRuleOutput(ruleMap);
					if(ruleOutput!=null && "false".equals(ruleOutput)){
						result =JsonResult.getInstance().addOk().add("status", "2").toJsonStr();
					}else{
						result =JsonResult.getInstance().addOk().add("status", "1").toJsonStr();
					}*/
				}else{
					//去帮掌柜信息
					String bzgMobile="";
					String bzgName="";
					String ruleRefId="";
					Map<String, Object> vpsBzg=creditApplyService.queryBZGCreditPostMember(userOnlyId);
					if(vpsBzg!=null){
						bzgMobile=Convert.toStr(vpsBzg.get("bzgMobile"));
						bzgName=Convert.toStr(vpsBzg.get("bzgName"));
					}
					//需要取错误原因  1、身份证   2、地址
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleType", "N");
					CreditRule rule=creditRuleService.queryCreditRuleType(ruleMap);
					if(rule!=null){
						ruleRefId=rule.getRuleRefId();
					}
					//跳转完善资料页面
					result =JsonResult.getInstance().addOk().add("status", "3").add("ruleRefId", ruleRefId)
							.add("bzgName", bzgName).add("bzgMobile", bzgMobile).toJsonStr();
					log.info("px_queryPXLimit 没有批销额度  result:"+result);
				}
			}else{
				result =JsonResult.getInstance().addOk().add("status", "0").toJsonStr();
			}
			log.info("px_queryPXLimit result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("px_queryPXLimit error", e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	@RequestMapping("/queryCardNos")
	@ResponseBody
	public JSONPObject px_queryCardNos(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		List<Map<String,Object>> cardNos = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			String userOnlyId=getUserOnlyId(request);
			log.info("px_queryCardNos param:userOnlyId:"+userOnlyId);
			BigDecimal creditBaseAmount=BigDecimal.ZERO;
			List<CreditApply> list = creditApplyService.queryInfoByUserOnlyId(userOnlyId);
			if(list!=null && list.size()>0) {
				CreditApply ca = list.get(0);
				if(null != ca.getCreditLimit() && ca.getCreditLimit().doubleValue() > 0.0) {
					creditBaseAmount=ca.getCreditLimit();
//					// 对额度除以15.并且千位以后全部置0
//					creditBaseAmount = creditBaseAmount.divide(new BigDecimal(15), -3, BigDecimal.ROUND_DOWN);
				}else if(null != ca.getCreditBaseAmount() && ca.getCreditBaseAmount().doubleValue() > 0.0){
					creditBaseAmount=ca.getCreditBaseAmount();
//					// 对额度除以15.并且千位以后全部置0
					creditBaseAmount = creditBaseAmount.divide(new BigDecimal(8), -3, BigDecimal.ROUND_DOWN);
				}
			}
			resultMap.put("creditBaseAmount", creditBaseAmount);
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			if(vps==null){
				log.info("px_queryCardNos userOnlyId:"+userOnlyId+";vps:"+vps);
				String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			String userName = Convert.toStr(vps.get(VpsInfoService.UsrName));
			String certNo = Convert.toStr(vps.get(VpsInfoService.CertNo));
			if(Check.isBlank(userName) || Check.isBlank(certNo)){
				String result = JsonResult.getInstance().addError("您的邮掌柜信息不全,需补全信息后才能再次操作。").toJsonStr();
				log.info("px_queryCardNos userOnlyId:"+userOnlyId+";result:"+result);
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			log.info("px_queryCardNos userOnlyId:"+userOnlyId+";userName:"+userName+" certNo:"+certNo);
			resultMap.put("userName", userName);
			resultMap.put("certNo", certNo);
			resultMap.put("userOnlyId", userOnlyId);
			cardNos = WildflyBeanFactory.getYCZgdQueryClient().getCardNosAndMobileNo(userOnlyId,userName,certNo);
			if(cardNos==null || cardNos.size() == 0){
				resultMap.put("code", "0000");
				resultMap.put("msg", "用户未绑定银行卡");
				resultMap.put("cardNos", "");
			}else{
				resultMap.put("cardNos", cardNos);
				resultMap.put("code", "0000");
				resultMap.put("msg", "查询成功");
			}
			log.info("px_queryCardNos userOnlyId:"+userOnlyId+";resultMap:"+resultMap);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
		}catch(Exception e){
			log.error("px_queryCardNos error", e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 发送验证码(用户绑过卡则调用公司发送接口，没有绑过卡则调用支付组接口)
	 * @return
	 */
	@RequestMapping("/sendSmsRandomCode")
	@ResponseBody
	public JSONPObject px_sendSmsRandomCode(HttpServletRequest request,@RequestParam String jsoncallback){
		String result= "";
		try{
			String certNo = request.getParameter("certNo");
			String userOnlyId=getUserOnlyId(request);
			String userName = request.getParameter("userName");
			String cardNo = request.getParameter("cardNo");
			String cardNos = request.getParameter("cardNos");
			String mobileNo = request.getParameter("mobileNo");
			log.info("px_sendSmsRandomCode userName:"+userName+" certNo:"+certNo+" cardNo:"+cardNo);
			log.info("px_sendSmsRandomCode cardNos:"+cardNos+" mobileNo:"+mobileNo+" userOnlyId:"+userOnlyId);
			//用户没有绑定卡号（调用支付组接口）
			if(!Check.isBlank(userOnlyId)){
				if(!checkCardNoHasBinded(cardNo,cardNos)){
					String value=customerInfoService.getIsSuperKeyWord("bind_card_control");
					log.info(" px_sendSmsRandomCode userOnlyId:"+userOnlyId+" ;value:"+value);
					if(Check.isBlank(value) || "PSBC".equals(value)){
						log.info("用户没有绑定卡号,调用支付组接口开始。。。");
						Map<String,String> paraMap = new HashMap<String, String>();
						paraMap.put("usrOnlyid", userOnlyId);
						paraMap.put("account_name", userName);
						paraMap.put("account_number", cardNo);
						paraMap.put("account_id", "1");
						paraMap.put("account_idNumber", certNo);
						paraMap.put("account_mobile", mobileNo);
						paraMap.put("card_type", "D");
						paraMap.put("bank_remark", "ZGD");
						paraMap.put("bank_code", "PSBC");
						paraMap.put("requestIp", Tools.getIpAddr(request));
						String ret = HttpClientUtil.sendPost(PropertiesHelper.getDfs("MYPURSE_SENDCODE_URL"), paraMap);
						log.info("px_sendSmsRandomCode return:"+ret);
						JSONObject js=JSONObject.fromObject(ret);
						result=JsonResult.getInstance().add("code",  js.get("returnCode")).add("msg", js.get("returnMsg")).add("validCode", "000000").toString();
					}else{
						String phone = request.getParameter("mobileNo");
						log.info("smsSendRandomCode -->" + mobileNo);
						String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
						log.info("smsSendRandomCode ret:"+ret);
						JSONObject js=JSONObject.fromObject(ret);
						result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).add("validCode", js.get("randomCode")).toJsonStr();
					}
				}else{
					//用户绑定过卡号(调用公司的发送接口)
					String phone = request.getParameter("mobileNo");
					log.info("smsSendRandomCode -->" + mobileNo);
					String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
					log.info("smsSendRandomCode ret:"+ret);
					JSONObject js=JSONObject.fromObject(ret);
					result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).add("validCode", js.get("randomCode")).toJsonStr();
				}
			}
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("px_sendSmsRandomCode error:",e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId="10000000391";
		return usronlyId;
	}
	public static boolean betweenHour(String before, String after){
		String curr = Convert.toStr(new SimpleDateFormat("HHmm").format(new Date()), "");
		if(curr.compareTo(before)>0 && curr.compareTo(after)<0){
			return true;
		}
		return false;
	}
	/**
	 * 校验验证码(绑过卡则调用公司的接口校验，没有绑过卡则调用支付组接口并发送邮储)
	 */
	@RequestMapping("/signSubmit")
	@ResponseBody
	public JSONPObject px_signSubmit(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("进入发邮储接口");
		String result= "";
		try{
			String certNo = request.getParameter("certNo");
			String userOnlyId=getUserOnlyId(request);
			String userName = request.getParameter("userName");
			String cardNo = request.getParameter("cardNo");
			String cardNos = request.getParameter("cardNos");
			String mobileNo = request.getParameter("mobileNo");
			String validCode = request.getParameter("validCode");
			log.info("px_signSubmit certNo:"+certNo+" userName:"+userName+" cardNos:"+cardNos+" mobileNo:"+mobileNo+" userOnlyId:"+userOnlyId);
			
			if(!betweenHour("0700", "1800")){
				result= JsonResult.getInstance().addError("对不起！激活时间必须在早上7点到晚上18:00点之间！").toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userOnlyId", userOnlyId);
			dataMap.put("cardNo", cardNo);
			dataMap.put("certNo", certNo);
			dataMap.put("userName", userName);
			dataMap.put("mobileNo", mobileNo);
//			dataMap.put("userOnlyId", userOnlyId);
			//用户没有绑定卡号（调用支付组接口）
			if(!checkCardNoHasBinded(cardNo,cardNos)){
				String value=customerInfoService.getIsSuperKeyWord("bind_card_control");
				log.info(" px_sendSmsRandomCode userOnlyId:"+userOnlyId+" ;value:"+value);
				if(Check.isBlank(value) || "PSBC".equals(value)){
					log.info("px_signSubmit userOnlyId:"+userOnlyId+"; checkCardNoHasBinded false");
					Map<String,String> paraMap = new HashMap<String, String>();
					paraMap.put("usrOnlyid", userOnlyId);
					paraMap.put("account_name", userName);
					paraMap.put("account_number", cardNo);
					paraMap.put("account_id", "1");
					paraMap.put("account_idNumber", certNo);
					paraMap.put("account_mobile", mobileNo);
					paraMap.put("card_type", "D");
					paraMap.put("account_validatecode", validCode);
					paraMap.put("bank_code", "PSBC");
					paraMap.put("bank_remark", "ZGD");
					paraMap.put("requestIp", Tools.getIpAddr(request));
					String ret = HttpClientUtil.sendPost(PropertiesHelper.getDfs("MYPURSE_SIGNSUBMIT_URL"), paraMap);
					log.info("px_signSubmit zhifuzu userOnlyId:"+userOnlyId+"; ret"+ret);
					JSONObject js=JSONObject.fromObject(ret);
					if("0000".equals(js.get("returnCode"))){
						result=JsonResult.getInstance().addOk().toString();
					}else{
						result=JsonResult.getInstance().add("code",  js.get("returnCode")).add("msg", js.get("returnMsg")).toString();
					}
				}else{
					log.info("signSubmit 四要素验证 userOnlyId:"+userOnlyId+"; checkCardNoHasBinded false");
					//走验证码
					String ret = WildflyBeanFactory.getSendMessageClient().verifyRandomCode(mobileNo, validCode);
					log.info("signSubmit 四要素验证验证码结果 userOnlyId:"+userOnlyId+";ret:"+ret);
					Map<String,Object> retMap = JsonUtil.getMapFromJsonString(ret);
					if(retMap!=null&&retMap.containsKey("returnCode")){
						String code = Convert.toStr(retMap.get("returnCode"));
						if("0035".equals(code)){
							retMap.put("returnMessage", "输入的验证码不正确");
							ret = JsonUtil.getJsonStringFromMap(retMap);
						}
					}
					JSONObject js=JSONObject.fromObject(ret);
			
					if("0000".equals(js.get("returnCode"))){
						String appkey_url = PropertiesHelper.getDfs("app_interface_url");
						//走四要素验证
						Map<String, String> headers = new HashMap<String, String>();
						Map<String, String> params = new HashMap<String, String>();
						params.put("tranzCode", "1101");
						params.put("id", certNo);//身份证号
						params.put("cell", mobileNo);//手机号
						params.put("name", userName);//姓名
						params.put("bankId", cardNo);//银行卡号
						params.put("userOnlyId", userOnlyId);
						log.info("signSubmit 四要素验证 params:" + params);
						String	res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
								appkey_url, headers, params, UhjConstant.time_out);
						log.info("signSubmit 四要素验证 res:" + res);
						Map<String, Object> resultMap=(Map<String, Object>) JsonUtil.getMapFromJsonString(res).get("product");
						if(resultMap!=null && "00".equals(Convert.toStr(resultMap.get("result")))){
							result=JsonResult.getInstance().addOk().toString();
						}else{
							ZgdCacheClient client = WildflyBeanFactory.getZgdCacheClient();
							String cacheValue = client.get("UHJ_EJB_BankFourErrorUser_"+userOnlyId);
							//针对百融四要素有误的用户，走阿里接口，查询缓存存入指定的用户
							if(cacheValue!=null&&"true".equals(cacheValue)){
								params.put("tranzCode", "1103");
								String aliRet = HttpClientUtil.sendPost(
										appkey_url, headers, params, UhjConstant.time_out);
								Map<String,Object> aliRetMap = JsonUtil.getMapFromJsonString(aliRet);
								if(aliRetMap!=null && "0".equals(Convert.toStr(aliRetMap.get("error_code")))){
									result=JsonResult.getInstance().addOk().toString();
								}else{
									result=JsonResult.getInstance().add("code", aliRetMap.get("error_code")).add("msg", aliRetMap.get("reason")).toJsonStr();
								}
							}else{
								result=JsonResult.getInstance().add("code", resultMap.get("result")).add("msg", resultMap.get("msg")).toJsonStr();
							}
						}
					}else{
						result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
					}
				}
			}else{
				log.info("px_signSubmit userOnlyId:"+userOnlyId+"; checkCardNoHasBinded true");
				String ret = WildflyBeanFactory.getSendMessageClient().verifyRandomCode(mobileNo, validCode);
				log.info("px_signSubmit userOnlyId:"+userOnlyId+";ret:"+ret);
				Map<String,Object> retMap = JsonUtil.getMapFromJsonString(ret);
				if(retMap!=null&&retMap.containsKey("returnCode")){
					String code = Convert.toStr(retMap.get("returnCode"));
					if("0035".equals(code)){
						retMap.put("returnMessage", "输入的验证码不正确");
						ret = JsonUtil.getJsonStringFromMap(retMap);
					}
				}
				JSONObject js=JSONObject.fromObject(ret);
		
				if("0000".equals(js.get("returnCode"))){
					result=JsonResult.getInstance().addOk().toString();
				}else{
					result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
				}
			}
			JSONObject object=JSONObject.fromObject(result);
			if("0000".equals(object.get("code"))){
				Map<String,Object> map = bindCardService.saveBindCardInfo(dataMap);
				// 新增更改customerInfo和customerCert --weisihua
				bindCardService.saveCustomerCertInfo(dataMap);
				
				BigDecimal creditBaseAmount=BigDecimal.ZERO;
				List<CreditApply> list = creditApplyService.queryInfoByUserOnlyId(userOnlyId);
				if(list!=null && list.size()>0) {
					CreditApply ca = list.get(0);
					if(null != ca.getCreditBaseAmount() && ca.getCreditBaseAmount().doubleValue() > 0.0) {
						creditBaseAmount=ca.getCreditBaseAmount();
					}
				}
				//String rescode=manualCreditService.manualCredit(userOnlyId, Convert.toStr(creditBaseAmount));
				String rescode=manualCreditService.manualCredit(userOnlyId, "100");
				if("000000".equals(rescode)){
					//保存绑卡过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.binding_bank_card);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
					
					//保存批销建额结果   成功或者失败
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.px_limit_apply);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					//保存批销建额结果   成功或者失败
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.px_limit_apply);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
			}else{
				//保存绑卡过程结束
				Map<String,Object> ruleMap = new HashMap<String, Object>();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.binding_bank_card);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				creditRuleService.saveCreditRuleService(ruleMap);
			}
			log.info("signSubmit result"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("signSubmit error:",e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 校验银行卡是否已绑定
	 * @param request
	 * @return
	 */
	public static boolean checkCardNoHasBinded(String cardNo,String cardNos){
		try{
			if(StringUtils.isNotBlank(cardNos)&&cardNos.indexOf(cardNo)>=0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			log.error("checkCardNoHasBinded error:",e);
		}
		return false;
	}
	
	public static void main(String[] args) {
		BigDecimal b = new BigDecimal("1522265");
		b = b.divide(new BigDecimal(15), -3, BigDecimal.ROUND_DOWN);
		System.out.println(b);
	}
	
	@RequestMapping("/toPersonalInfo")
	public ModelAndView personalInfo(HttpServletRequest request, ModelMap mv) {
			log.info("toYouLeKuaiJie begin");
			Map<String,Object> map = new HashMap<String, Object>();
//			String userOnlyId = Convert.toStr(request.getParameter("userOnlyId"));
			String flag = Convert.toStr(request.getParameter("flage"),"");
//			log.info("contractInfo userOnlyId:"+userOnlyId+" flag:"+flag);
//			String customerName = "";
//			String certNo = "";
//			//查询姓名
//			List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
//			CustomerInfoExample infoExample = new CustomerInfoExample();
//			infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
//			customerInfos =  customerInfoMapper.selectByExample(infoExample);
//			if(customerInfos!=null&&customerInfos.size()>0){
//				customerName = customerInfos.get(0).getCustomerName();		//用户姓名
//			}
//			//查询个人身份信息
//			List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
//			CustomerCertExample customerCertExample = new CustomerCertExample();
//			customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.idcard);
//			customerCerts = customerCertMapper.selectByExample(customerCertExample);
//			if(customerCerts!= null && customerCerts.size()>0){
//				certNo = customerCerts.get(0).getCertNo();
//			}
//			map.put("customerName", customerName);
//			map.put("certNo", certNo);
//			String date = DateUtil.currDateStr();
//			map.put("year", date.substring(0, 4));
//			map.put("month", date.substring(6, 7));
//			map.put("day", date.substring(9, 10));
			map.put("nowTime", new Date().getTime());
			map.put("version", "ZGDFW-1.1.170606");
	         if("1".equals(flag)){//绑卡
				mv.putAll(map);
				return new ModelAndView("/yc/serviceAgreement");
			}else {
				mv.putAll(map);//征信协议
				return new ModelAndView("/yc/personalInfo");
			}
	 
			 
		}
	/**
	 * 跳转首页（进货快速建额）
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/toFisetPage")
	public String toFisetPage(HttpServletRequest request) {
		return "yc/yc_guidepage";
	}
	/**
	 * 跳转绑卡页面（进货快速建额
	 * @param request
	 * @return
	 */
	@RequestMapping("/toPXBank")
	public String toHodepage(HttpServletRequest request) {
		return "yc/yc_pxBank";
	}
}
