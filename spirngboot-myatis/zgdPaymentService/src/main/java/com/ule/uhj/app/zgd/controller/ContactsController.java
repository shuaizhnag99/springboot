package com.ule.uhj.app.zgd.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.CarrierOperatorAuthService;
import com.ule.uhj.app.zgd.service.ContactsService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.zgd.ZgdCacheClient;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.SldService;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;
import com.ule.uhj.sld.constant.BRTransCodeEnum;
import com.ule.uhj.sld.constant.ZXTransCodeEnum;
import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.StringUtil;
import com.ule.uhj.util.UhjWebJsonUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/contact")
public class ContactsController {
	private static Logger log = LoggerFactory.getLogger(ContactsController.class);
	
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	@Autowired
	private CarrierOperatorAuthService carrieroperatorService;

	private static final String[][] maritalStatusArray = {{"10","40","30"},{"20","21","22","23"}};
	private static final int MARITAL_STATUS_Y = 1;

	/**
	 * 查询配偶的姓名
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/querySpouseInfo")
	@ResponseBody
	public JSONPObject querySpouseInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		String result= "";
		try{
			String userOnlyId=getUserOnlyId(request);
			log.info("querySpouseInfo userOnlyId:"+userOnlyId);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map=customerInfoService.queryCustomerSpouseInfo(map);
			String spouseName=Convert.toStr(map.get("name"));
			result = JsonResult.getInstance().addOk().add("spouseName",spouseName).toJsonStr();
			log.info("querySpouseInfo userOnlyId:"+userOnlyId+";result:"+result);
		}catch(Exception e){
			log.error("querySpouseInfo error:"+e.getMessage());
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
		return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
	}
	/**
	 * 校验用户上传的证件照片
	 * marriage_certificate   结婚证
     * household_register_home   户口本首页
     * household_register_spouse 户口本配偶页
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/checkSpousePicture")
	@ResponseBody
	public JSONPObject checkSpousePicture(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		String result= "";
		try{
			String userOnlyId=getUserOnlyId(request);
			String type=Convert.toStr(request.getParameter("type"));
			String url=Convert.toStr(request.getParameter("url"));
			//TODO  调用微软的图片识别接口
			log.info("checkSpousePicture userOnlyId:"+userOnlyId+";type:"+type+";url:"+url);
			result = JsonResult.getInstance().addOk().toJsonStr();
			log.info("checkSpousePicture userOnlyId:"+userOnlyId+";result:"+result);
		}catch(Exception e){
			log.error("checkSpousePicture error:"+e.getMessage());
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
		return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
	}
	
	/**
	 * 保存上传的证件类型
	 * flag
	 * 0   结婚证
	 * 1   户口本
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/saveSpousePicture")
	@ResponseBody
	public JSONPObject saveSpousePicture(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		String result= "";
		try{
			String userOnlyId=getUserOnlyId(request);
			String flag=Convert.toStr(request.getParameter("flag"));
			log.info("saveSpousePicture userOnlyId:"+userOnlyId+";flag:"+flag);
			Map<String,Object> contactsMap = new HashMap<String, Object>();
			contactsMap.put("userOnlyId", userOnlyId);
			contactsMap.put("contactType", UhjConstant.contactType.spouse);
			contactsMap.put("flag", flag);
			contactsService.saveContactsSpouse(contactsMap);
			//保存配偶授权过程结束
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
			creditRuleService.saveCreditRuleService(ruleMap);
			result = JsonResult.getInstance().addOk().toJsonStr();
			log.info("saveSpousePicture userOnlyId:"+userOnlyId+";result:"+result);
		}catch(Exception e){
			log.error("saveSpousePicture error:"+e.getMessage());
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
		return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
	}
	
	@RequestMapping("/queryUserPhone")
	@ResponseBody
	public JSONPObject queryUserPhone(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		String result= "";
		try{
			String userOnlyId=getUserOnlyId(request);
			log.info("queryUserPhone userOnlyId:"+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map=customerInfoService.queryCustomerInfo(map);
			String userPhone=Convert.toStr(map.get("phone"));
			result = JsonResult.getInstance().addOk().add("userPhone", userPhone).toJsonStr();
			log.info("queryUserPhone result:"+result);
		}catch(Exception e){
			log.error("queryUserPhone error:"+e.getMessage());
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
		return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
	}
	@RequestMapping("/queryMaritalStatus")
	@ResponseBody
	public JSONPObject queryMaritalStatus(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", "0000");
		result.put("msg", "婚姻状况查询成功");
		result.put("maritalStatus", null);
		return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
	/*	Map<String,Object> param = new HashMap<String, Object>();
		try{
			String userOnlyId=getUserOnlyId(request);
			log.info("queryMaritalStatus userOnlyId:"+userOnlyId);
			param.put("userOnlyId", userOnlyId);
			param.put("certType", UhjConstant.certType.idcard);
			Map<String,Object> customerMap = customerInfoService.queryCustomerInfo(param);
			param.put("cid", Convert.toStr(customerMap.get("certNo")));
			param.put("name", Convert.toStr(customerMap.get("name")));
			//调用易微户籍信息接口校验用户婚姻状况
			List resList = new ArrayList();
			resList.add(Convert.toStr(customerMap.get("name")));
			resList.add(Convert.toStr(customerMap.get("certNo")));
			String maritalStatus = queryMaritalStatusInfo(userOnlyId,resList);
			result.put("code", "0000");
			result.put("msg", "婚姻状况查询成功");
			result.put("maritalStatus", maritalStatus);
		}catch(Exception e){
			log.error("queryMaritalStatus error:"+e.getMessage());
			return new JSONPObject(jsoncallback, e);
		}
		return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));*/
	}
	
	@RequestMapping("/setCache")
	@ResponseBody
	public String setCache(HttpServletRequest request, HttpServletResponse response){
		String result= "";
		try{
			String key = Convert.toStr(request.getParameter("key"),"");
			String value = Convert.toStr(request.getParameter("value"),"");
			Integer expireTime = Convert.toInt(request.getParameter("expire"));
			if(!Check.isBlank(key)){
				ZgdCacheClient client = WildflyBeanFactory.getZgdCacheClient();
				if(Check.isBlank(expireTime)){
					client.set(key, value);
				}else{
					client.setWithExpire(key, value, expireTime);
				}
			}
			result = JsonResult.getInstance().addOk().toJsonStr();
		}catch(Exception e){
			log.error("setCache error:"+e.getMessage());
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
		}
		return result;
	}
	
	
	/**
	  * 查询个人户籍信息
	  * @param dataMap
	  * @return
	 * @throws ParseException 
	  */
	 @SuppressWarnings("unchecked")
	private Map<String,Object> queryResidenceInfo(Map<String,Object> dataMap) throws ParseException{
		 log.info("queryResidenceInfo param:"+dataMap.toString());
		 SldService sldService = new DefaultSldService();
		 Map<String,Object>  responseMap =new HashMap<String, Object>();
		 Map<String,Object>  resultMap =new HashMap<String, Object>();
		 Request request = sldService.getRequest(DefaultSldService.REQUEST_TYPE_BR, BRTransCodeEnum.BR_RESIDENCE_QUERY.getTransCode(),dataMap);
	     SldOperateLog sldOperateLog = new SldOperateLog("掌柜贷APP", "调用者APP账户", "BR", "", "MGT审核后台查询指定用户的百融征信个人户籍信息产品数据。", "");
	     request.setOpeartor(sldOperateLog);
	     Response response = sldService.doService(request);
	     List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
	     if("000000".equals(response.getResponseCode())){  //000000,处理成功！ "000000".equals(response.getResponseCode())
	 	     responseMap = response.getResponseMap();//我是处理后的数据,Map格式
	 	     log.info("queryResidenceInfo responseMap=========="+responseMap);
	 	    if(responseMap!=null && responseMap.size()>=1){
	 	    	 Map<String,Object> productMap = (Map<String,Object>)responseMap.get("product");
				 if(productMap!=null && productMap.size()>=1 && productMap.containsKey("data")){
					 Map<String,Object> statusMap = (Map<String,Object>)productMap.get("api_status");
					 resultMap.put("checkCode", statusMap.get("code	"));
					 resultMap.put("checkMsg", statusMap.get("description"));
					 Map<String,Object> daMap = (Map<String,Object>)responseMap.get("data");
					 if(daMap!=null && daMap.size()>=1 && daMap.containsKey("residence")){
						 resultList=(List<Map<String,Object>>)daMap.get("residence");
						 if(resultList!= null && resultList.size() > 0){
							 resultMap.put("maritalList", resultList);
							 log.info("queryResidenceInfo resultMap==========" + resultMap);
						 }
					 }
				 }
	 	     }
	    }
		 return resultMap;
	 }
	 
	/**
	 * 保存联系人
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/saveContactsInfo")
	@ResponseBody
	public JSONPObject saveContactsInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> contactsMap = new HashMap<String, Object>();
		try{
			String userOnlyId=getUserOnlyId(request);
			log.info("saveContactsInfo param userOnlyId:"+userOnlyId
					+"contactType:"+Convert.toStr(request.getParameter("contactType"))
					+ " contactName:"+Convert.toStr(request.getParameter("contactName"))
							+ " mobileNo:"+Convert.toStr(request.getParameter("mobileNo"))
							+ " maritalStatus:"+Convert.toStr(request.getParameter("maritalStatus")));
			if(!Check.isBlank(userOnlyId)){
				String contactName = Convert.toStr(request.getParameter("contactName"));
				contactsMap.put("userOnlyId", userOnlyId);
				contactsMap.put("contactType", Convert.toStr(request.getParameter("contactType")));
				contactsMap.put("contactName", contactName);
				contactsMap.put("mobileNo", Convert.toStr(request.getParameter("mobileNo")));
				contactsMap.put("maritalStatus", Convert.toStr(request.getParameter("maritalStatus")));
				resultMap=contactsService.saveContactsInfo(contactsMap);
			}
			
			//保存（未婚）联系人过程结束
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.contact_state);
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
			creditRuleService.saveCreditRuleService(ruleMap);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(resultMap));
		}catch(Exception e){
			log.error("saveContactsInfo error:"+e.getMessage());
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}

	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId ="10000000391";
		return usronlyId;
	}


	/***
	 * 查询婚姻状况
	 * @param dataMap
	 * @return
	 * @throws ParseException
	 */
	private String queryMaritalStatusInfo(String userOnlyId,List dataMap) throws Exception{
		log.info("queryMaritalStatusInfo param:"+dataMap.toString());
		SldService sldService = new DefaultSldService();
		Map<String,Object>  responseMap =new HashMap<String, Object>();
		Map<String,Object>  resultMap =new HashMap<String, Object>();
		Request request = sldService.getRequest(DefaultSldService.REQUEST_TYPE_ZX, ZXTransCodeEnum.identityNameMultiInfoCheck.getTransCode(),dataMap);
		SldOperateLog sldOperateLog = new SldOperateLog("掌柜贷APP", "调用者APP账户", "ZX", "", "MGT审核后台查询指定用户的易微征信身份信息多项查询产品数据。", "");
		request.setOpeartor(sldOperateLog);
		Response response = sldService.doService(request);
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		if("000000".equals(response.getResponseCode())){  //000000,处理成功！ "000000".equals(response.getResponseCode())
			responseMap = response.getResponseMap();//我是处理后的数据,Map格式
			String responseDataStr = response.getResponseMap()!=null ? Convert.toStr(response.getResponseMap().get("data")).replace("\\", ""):null;
			log.info("queryMaritalStatusInfo responseDataStr "+responseDataStr);
			responseMap = StringUtil.isEmpty(responseDataStr)? null: JsonUtil.getMapFromJsonString(responseDataStr);
			//保存接口返回数据
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("name", dataMap.get(0));
			param.put("certNo", dataMap.get(1));
			saveidentityNameMultiInfo(userOnlyId,param,responseMap);
			String MaritalStatus = Convert.toStr(null!=responseMap?responseMap.get("maritalStatus"):"", "");
			if(!Check.isBlank(MaritalStatus)){
				for(int i =0;i<maritalStatusArray.length;i++){
					for(int j=0;j<maritalStatusArray[i].length;j++){
						if(maritalStatusArray[i][j].equals(MaritalStatus)){
							return i==MARITAL_STATUS_Y ? UhjConstant.maritalStatus.married.toString() : UhjConstant.maritalStatus.unmarried.toString();
						}
					}
				}
			}
		}
		return "";
	}
	
	private void saveidentityNameMultiInfo(String userOnlyId,Map<String,Object> data,Map<String,Object> dataMap) throws Exception{
		 InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
		 Map<String,Object> creditMap = carrieroperatorService.queryCreditApplyInfo(userOnlyId);
			String appId = Convert.toStr(creditMap.get("appId"));
			interfaceAccessBlob.setAppId(appId);
			interfaceAccessBlob.setUserOnlyId(userOnlyId);
			interfaceAccessBlob.setInterfaceType(ZXTransCodeEnum.identityNameMultiInfoCheck.getTransCode());
			interfaceAccessBlob.setRequestInfo(JSONObject.fromObject(data).toString().getBytes());
			interfaceAccessBlob.setResponseInfo(JSONObject.fromObject(dataMap).toString().getBytes());
			interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
			interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.success);
			interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
	 }
	
	
}
