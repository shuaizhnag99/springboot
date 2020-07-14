package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.SendMessageClient;
import com.ule.uhj.ejb.client.zgd.ZgdCacheClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/personal")
public class PersonalCenterController {

	private static Logger log = LoggerFactory.getLogger(PersonalCenterController.class);
	
	
	@Autowired
	private CreditApplyService creditApplyService;
	@Autowired
	private UserInfoService userInfoService;
	
	
	/**
	 * 个人中心首页
	 */
	@RequestMapping("/aap_personalCenter")
	@ResponseBody
	public JSONPObject aapPersonalCenter(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("aapPersonalCenter begin.");
		try {
			String result=null;
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("aapPersonalCenter userOnlyId "+userOnlyId);
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map=userInfoService.queryBindCardInfo(map);
			log.info("aapPersonalCenter userOnlyId "+userOnlyId+";map:"+map);
			if(map!=null){
				String cardNo="";
				String phone="";
				String status=UhjConstant.CustomerAccountStatus.valid_useful;
				String personalImage=Convert.toStr(map.get("personalImage"));
				String validCard=Convert.toStr(map.get("validCard"));
				String userName=Convert.toStr(map.get("userName"));
				String validPhone=Convert.toStr(map.get("validPhone"));
				String invalidCard=Convert.toStr(map.get("invalidCard"));
				String invalidPhone=Convert.toStr(map.get("invalidPhone"));
				String invalidStatus=Convert.toStr(map.get("invalidStatus"));
				if(!Check.isBlank(invalidStatus) && UhjConstant.CustomerAccountStatus.invalid.equals(invalidStatus)){//没有变更过，已经变更成功的
					status=invalidStatus;
					cardNo=invalidCard;
					phone=invalidPhone;
				}else{
					cardNo=validCard;
					phone=validPhone;
				}
				result = JsonResult.getInstance().addOk().add("cardNo", cardNo)
						.add("phone", phone).add("userName", userName)
						.add("status", status).add("personalImage", personalImage).toJsonStr();
			}
			log.info("aapPersonalCenter userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("aapPersonalCenter error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 个人中心查询信贷员信息
	 */
	@RequestMapping("/aap_queryCreditManager")
	@ResponseBody
	public JSONPObject appQueryCreditManager(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("appQueryCreditManager begin.");
		try {
			String result = JsonResult.getInstance().addOk().toJsonStr();
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("appQueryCreditManager userOnlyId "+userOnlyId);
			//查询信贷员信息
			Map<String, Object> map =userInfoService.queryAccountInfo(userOnlyId);
			if(map!=null){
				String creditName=Convert.toStr(map.get("loanOfficerName"),"");
				String creditBank=Convert.toStr(map.get("loanOfficerOrg"),"");
				String creditPhone=Convert.toStr(map.get("loanOfficerPhone"),"");
				result = JsonResult.getInstance().addOk().add("creditName", creditName)
						.add("creditPhone", creditPhone)
						.add("creditBank", creditBank).toJsonStr();
			}
			log.info("appQueryCreditManager userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("appQueryCreditManager error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 个人中心查询联络员信息
	 */
	@RequestMapping("/aap_queryLoanOfficer")
	@ResponseBody
	public JSONPObject appQueryLoanOfficer(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("appQueryLoanOfficer begin.");
		try {
			String result = JsonResult.getInstance().addOk().toJsonStr();
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("appQueryLoanOfficer userOnlyId "+userOnlyId);
			Map<String, Object> vpsBzg=creditApplyService.queryBZGCreditPostMember(userOnlyId);
			if(vpsBzg!=null){
				String bzgMobile=Convert.toStr(vpsBzg.get("bzgMobile"),"");
				String bzgName=Convert.toStr(vpsBzg.get("bzgName"),"");
				result = JsonResult.getInstance().addOk().add("bzgMobile", bzgMobile)
						.add("bzgName", bzgName).toJsonStr();
			}
			log.info("appQueryLoanOfficer userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("appQueryLoanOfficer error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 更换手机号的功能
	 * 向掌柜手机发送短信
	 * @param phone
	 * @return none
	 * response json
	 * returnCode 0000(成功)
	 * randomCode 校验码
	 */
	@RequestMapping("/smsSendRandomCode")
	@ResponseBody
	public JSONPObject smsSendRandomCode(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String phone = request.getParameter("phone");
			log.info("smsSendRandomCode -->" + phone);
			String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
			log.info("smsSendRandomCode ret:"+ret);
			JSONObject js=JSONObject.fromObject(ret);
			result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
			log.info("smsSendRandomCode --> result" + result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("smsSendRandomCode error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 更换手机号的功能
	 * 验证手机校验码是否正确
	 * @param phone   手机号
	 * @param validCode 验证码
	 * @return none
	 * response json
	 * returnCode  0000 成功
	 * returnMessage 
	 * verifyResult  true or false
	 *  {"returnCode":"0000","returnMessage":"操作成功","verifyResult":"true"}
	 */
	@RequestMapping("/sendmsg_verifyRandomCode")
	@ResponseBody
	public JSONPObject verifyRandomCode(HttpServletRequest request,@RequestParam String jsoncallback) {
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			String phone = request.getParameter("phone");
			String validCode = request.getParameter("validatecode");
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			log.info("verifyRandomCode -->" + phone + ":" + validCode);
			String result = WildflyBeanFactory.getSendMessageClient().verifyRandomCode(phone, validCode);
			log.info("verifyRandomCode -->" + result);
			JSONObject js=JSONObject.fromObject(result);
			if("0000".equals(js.get("returnCode"))){
				//查询四要素 病进行四要素验证
				Map<String, Object> map =new HashMap<String, Object>();
				map=userInfoService.queryUserInfo(userOnlyId);
				if(map==null){
					result=JsonResult.getInstance().add("code", "1000").add("msg", "身份信息不全，无法更改手机号，请联系客服").toJsonStr();
					log.info("verifyRandomCode --> result" + result);
					return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
				}
				String userName=Convert.toStr(map.get("userName"));
				String cardNo=Convert.toStr(map.get("cardNo"));
				String certNo=Convert.toStr(map.get("certNo"));
				Map<String, String> headers = new HashMap<String, String>();
				Map<String, String> params = new HashMap<String, String>();
				params.put("tranzCode", "1101");
				params.put("id", certNo);//身份证号
				params.put("cell", phone);//手机号
				params.put("name", userName);//姓名
				params.put("bankId", cardNo);//银行卡号
				params.put("userOnlyId", userOnlyId);
				log.info("verifyRandomCode params:" + params);
				String	res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
				log.info("verifyRandomCode res:" + res);
				Map<String, Object> resultMap=(Map<String, Object>) JsonUtil.getMapFromJsonString(res).get("product");
				if(resultMap!=null && "00".equals(Convert.toStr(resultMap.get("result")))){
					map.clear();
					map.put("userOnlyId", userOnlyId);
					map.put("phone", phone);
					userInfoService.updateUserPhone(map);
					result=JsonResult.getInstance().addOk().toJsonStr();
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
							map.clear();
							map.put("userOnlyId", userOnlyId);
							map.put("phone", phone);
							userInfoService.updateUserPhone(map);
							result=JsonResult.getInstance().addOk().toString();
						}else{
							result=JsonResult.getInstance().add("code", null!=aliRetMap?aliRetMap.get("error_code"):"").add("msg", null!=aliRetMap?aliRetMap.get("reason"):"").toJsonStr();
						}
					}else{
						result=JsonResult.getInstance().add("code",result).add("msg", null!=resultMap?resultMap.get("reason"):"").toJsonStr();
					}
				}
			}else{
				result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
			}
			
			log.info("verifyRandomCode --> result" + result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("verifyRandomCode error", e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
}
