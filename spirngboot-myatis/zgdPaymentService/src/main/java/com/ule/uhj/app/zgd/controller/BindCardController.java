package com.ule.uhj.app.zgd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.BindCardService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
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
@RequestMapping("/bindCard")
public class BindCardController {

	private static Logger log = LoggerFactory.getLogger(BindCardController.class);
	
	/**
	 * 随机码短信类型 
	 */
	private static String SMT_VALIDCODE = "280102";
	
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@RequestMapping("/queryCardNos")
	@ResponseBody
	public JSONPObject queryCardNos(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		List<Map<String,Object>> cardNos = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			String userOnlyId=getUserOnlyId(request);
			String source = request.getParameter("source");//绑卡来源     personalCenter 个人中心里的换卡   其他还是按照原有流程绑卡
			log.info("queryCardNos param:userOnlyId:"+userOnlyId+";source:"+source);
			if(!Check.isBlank(userOnlyId)){
				Map<String,Object> customerMap = bindCardService.queryCustomerBasicInfo(userOnlyId);
				if(!customerMap.containsKey("userName")){
					resultMap.put("code", "0002");
					resultMap.put("msg", "用户名称不能为空");
				}
				if(!customerMap.containsKey("certNo")){
					resultMap.put("code", "0003");
					resultMap.put("msg", "用户证件号不能为空");
				}
				String userName = Convert.toStr(customerMap.get("userName"));
				String certNo = Convert.toStr(customerMap.get("certNo"));
				String appId = Convert.toStr(customerMap.get("appId"));
				log.info("queryCardNos userName:"+userName+" certNo:"+certNo+" appId:"+appId);
				resultMap.put("userName", userName);
				resultMap.put("certNo", certNo);
				resultMap.put("userOnlyId", userOnlyId);
				resultMap.put("appId", appId);
				cardNos = WildflyBeanFactory.getYCZgdQueryClient().getCardNosAndMobileNo(userOnlyId,userName,certNo);
//				Map<String,Object> map = new HashMap<String, Object>();
//				map.put("cardNo","6565979799797979797");
//				map.put("mobileNo","17717371847");
//				map.put("certNo", "310115199208210415");
//				cardNos.add(map);
				if(cardNos==null || cardNos.size() == 0){
					resultMap.put("code", "0000");
					resultMap.put("msg", "用户未绑定银行卡");
					resultMap.put("cardNos", "");
				}else{
					resultMap.put("cardNos", cardNos);
					resultMap.put("code", "0000");
					resultMap.put("msg", "查询成功");
				}
			}else{
				resultMap.put("code", "0001");
				resultMap.put("msg", "用户Id不能为空");
			}
			
			log.info("queryCardNos resultMap:"+resultMap);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
		}catch(Exception e){
			log.error("queryCardNos error", e);
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
	public JSONPObject sendSmsRandomCode(HttpServletRequest request,@RequestParam String jsoncallback){
		String result= "";
		try{
			String certNo = request.getParameter("certNo");
			String userOnlyId=getUserOnlyId(request);
			String userName = request.getParameter("userName");
			String cardNo = request.getParameter("cardNo");
			String cardNos = request.getParameter("cardNos");
			String mobileNo = request.getParameter("mobileNo");
			log.info("sendSmsRandomCode userName:"+userName+" certNo:"+certNo+" cardNo:"+cardNo);
			log.info("sendSmsRandomCode cardNos:"+cardNos+" mobileNo:"+mobileNo+" userOnlyId:"+userOnlyId);
			//用户没有绑定卡号（调用支付组接口）
			if(!Check.isBlank(userOnlyId)){
				if(!checkCardNoHasBinded(cardNo,cardNos)){
					log.info("smsSendRandomCode 用户没有绑定卡号,调用支付组接口开始。。。userOnlyId:"+userOnlyId);
					String value=customerInfoService.getIsSuperKeyWord("bind_card_control");
					log.info(" signSubmit userOnlyId:"+userOnlyId+" ;value:"+value);
					if(Check.isBlank(value) || "PSBC".equals(value)){
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
						log.info("sendSmsRandomCode 邮储获取验证码 userOnlyId:"+userOnlyId+"; paraMap:"+paraMap);
						String ret = HttpClientUtil.sendPost(PropertiesHelper.getDfs("MYPURSE_SENDCODE_URL"), paraMap);
						log.info("sendSmsRandomCode 邮储获取验证码 userOnlyId:"+userOnlyId+"; return:"+ret);
						JSONObject js=JSONObject.fromObject(ret);
						result=JsonResult.getInstance().add("code",  js.get("returnCode")).add("msg", js.get("returnMsg")).add("validCode", "000000").toString();
					}else{
						log.info("smsSendRandomCode 没有绑定卡 userOnlyId:"+userOnlyId+"; -->" + mobileNo);
						String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(mobileNo);
						log.info("smsSendRandomCode 没有绑定卡 userOnlyId:"+userOnlyId+"; ret:"+ret);
						JSONObject js=JSONObject.fromObject(ret);
						result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).add("validCode", js.get("randomCode")).toJsonStr();
					}
				}else{
					//用户绑定过卡号(调用公司的发送接口)
//					String phone = request.getParameter("mobileNo");
					log.info("smsSendRandomCode userOnlyId:"+userOnlyId+"; -->" + mobileNo);
					String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(mobileNo);
					log.info("smsSendRandomCode userOnlyId:"+userOnlyId+"; ret:"+ret);
					JSONObject js=JSONObject.fromObject(ret);
					result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).add("validCode", js.get("randomCode")).toJsonStr();
				}
			}
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("sendSmsRandomCode error:",e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId="10000000391";
		return usronlyId;
	}
	
	/**
	 * 校验验证码(绑过卡则调用公司的接口校验，没有绑过卡则调用支付组接口并发送邮储)
	 */
	@RequestMapping("/signSubmit")
	@ResponseBody
	public JSONPObject signSubmit(HttpServletRequest request,@RequestParam String jsoncallback){
		String result= "";
		try{
			String certNo = request.getParameter("certNo");
			String userOnlyId=getUserOnlyId(request);
			String userName = request.getParameter("userName");
			String cardNo = request.getParameter("cardNo");
			String cardNos = request.getParameter("cardNos");
			String mobileNo = request.getParameter("mobileNo");
			String validCode = request.getParameter("validCode");
			String source = request.getParameter("source");//绑卡来源     personalCenter 个人中心里的换卡   其他还是按照原有流程绑卡
			log.info("signSubmit certNo:"+certNo+" userName:"+userName+" cardNos:"+cardNos+" mobileNo:"+mobileNo+" userOnlyId:"+userOnlyId+";source:"+source);
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userOnlyId", userOnlyId);
			dataMap.put("cardNo", cardNo);
			dataMap.put("certNo", certNo);
			dataMap.put("userName", userName);
			dataMap.put("mobileNo", mobileNo);
			dataMap.put("source", source);
			//用户没有绑定卡号（调用支付组接口）
			if(!checkCardNoHasBinded(cardNo,cardNos)){
				String value=customerInfoService.getIsSuperKeyWord("bind_card_control");
				log.info(" signSubmit userOnlyId:"+userOnlyId+" ;value:"+value);
				if(Check.isBlank(value) || "PSBC".equals(value)){
					log.info("signSubmit userOnlyId:"+userOnlyId+"; checkCardNoHasBinded false");
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
					log.info("signSubmit 邮储获绑卡 zhifuzu   userOnlyId:"+userOnlyId+"; paraMap:"+paraMap);
					String ret = HttpClientUtil.sendPost(PropertiesHelper.getDfs("MYPURSE_SIGNSUBMIT_URL"), paraMap);
					log.info("signSubmit 邮储获绑卡 zhifuzu userOnlyId:"+userOnlyId+"; ret"+ret);
					JSONObject js=JSONObject.fromObject(ret);
					if("0000".equals(js.get("returnCode"))){
						Map<String,Object> map = bindCardService.saveBindCardInfo(dataMap);
						log.info("signSubmit userOnlyId:"+userOnlyId+" map1"+map);
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
						String message = Convert.toStr(retMap.get("returnMessage"),"");
						if("0035".equals(code) && message.indexOf("未找到对应的随机码")>=0){
							retMap.put("returnMessage", "随机码验证失败，请重新获取验证码");
							ret = JsonUtil.getJsonStringFromMap(retMap);
						}else if("0035".equals(code)){
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
								}else if(aliRetMap!=null){
									result=JsonResult.getInstance().add("code", aliRetMap.get("error_code")).add("msg", aliRetMap.get("reason")).toJsonStr();
								}
							}else if(resultMap!=null){
								result=JsonResult.getInstance().add("code", resultMap.get("result")).add("msg", resultMap.get("msg")).toJsonStr();
							}
						}
					}else{
						result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
					}
				}
			}else{
				log.info("signSubmit userOnlyId:"+userOnlyId+"; checkCardNoHasBinded true");
//				log.info("mobileNo:"+mobileNo+"validCode:"+validCode);
//						result = BasicServiceTools.verifyRandomCode(mobileNo, validCode, SMT_VALIDCODE, "2");
				String ret = WildflyBeanFactory.getSendMessageClient().verifyRandomCode(mobileNo, validCode);
				log.info("signSubmit userOnlyId:"+userOnlyId+";ret:"+ret);
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
				bindCardService.saveBindCardInfo(dataMap);
			}
			if(source==null || !"personalCenter".equals(source)){
				if("0000".equals(object.get("code"))){
					//保存绑卡过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.binding_bank_card);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					//保存绑卡过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.binding_bank_card);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
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
			if(cardNos.indexOf(cardNo)>=0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			log.error("checkCardNoHasBinded error:",e);
		}
		return false;
	}
	
	/**
	 * 确认绑卡(更新数据库卡号信息，更改状态)
	 * @return
	 *//*
	@RequestMapping("/confirmBindCard")
	@ResponseBody
	public JSONPObject confirmBindCard(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String userOnlyId = getUserOnlyId(request);
			String cardNo = Convert.toStr(request.getParameter("cardNo"));
			String certNo = request.getParameter("certNo");//身份证号码
			String userName = request.getParameter("userName");
			String mobileNo = request.getParameter("mobileNo");
			log.info("confirmBindCard param userOnlyId:"+userOnlyId+ " cardNo:"+cardNo+" certNo:"+certNo);
			String vpsCertNo = "";
			List<Map<String,Object>> cardNos = WildflyBeanFactory.getYCZgdQueryClient().getCardNosAndMobileNo(userOnlyId,userName,certNo);
			if(cardNos!=null&&cardNos.size()>0){
				for(int i=0;i<cardNos.size();i++){
					Map<String,Object> map = cardNos.get(i);
					vpsCertNo = Convert.toStr(map.get("certNo"));
				}
			}
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userOnlyId", userOnlyId);
			dataMap.put("cardNo", cardNo);
			dataMap.put("certNo", vpsCertNo);
			dataMap.put("userName", userName);
			dataMap.put("mobileNo", mobileNo);
			dataMap.put("userOnlyId", userOnlyId);
			result = bindCardService.saveBindCardInfo(dataMap);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("confirmBindCard error:"+e.getMessage());
			return new JSONPObject(jsoncallback, e);
		}
	}*/
}
