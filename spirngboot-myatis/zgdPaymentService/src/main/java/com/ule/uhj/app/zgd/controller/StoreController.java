package com.ule.uhj.app.zgd.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ule.uhj.app.yzs.service.YzsPostmemberService;
import com.ule.uhj.app.zgd.model.CreditReturn;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.service.BaiDuFaceService;
import com.ule.uhj.app.zgd.service.ContactsService;
import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.CreditReturnService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerAddressService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.CustomerStoreService;
import com.ule.uhj.app.zgd.service.CustomerWhiteService;
import com.ule.uhj.app.zgd.service.ForecastResultService;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.ReturnReasonEnum;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.dto.zgd.ProductInfoN;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.StringUtil;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

@Controller
@RequestMapping("/store")
public class StoreController {
	private static Logger log = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private CustomerStoreService customerStoreService;
	
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CustomerWhiteService customerWhiteService;
	@Autowired
	private CreditReturnService creditReturnService;
	@Autowired
	private ApplyImageService applyImageService;
	@Autowired
	private CreditApplyService creditApplyService;
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	@Autowired
	private BaiDuFaceService baiDuFaceService;
	@Autowired
	private YzsPostmemberService yzsPostmemberService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ForecastResultService forecastResultService;
	
	
	@RequestMapping("/saveInterface")
	@ResponseBody
	public JSONPObject saveInterface(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("saveInterface begin.");
		try {
			String userOnlyId=Convert.toStr(request.getParameter("userOnlyId"));
			log.info("saveInterface userOnlyId="+userOnlyId);
			String tranzCode=Convert.toStr(request.getParameter("tranzCode"));
			String businessLicense=Convert.toStr(request.getParameter("businessLicense"));
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			String	ret="";
			Map<String, String> headers = new HashMap<String, String>();
			if("4104".equals(tranzCode)){
				Map<String, String> params = new HashMap<String, String>();
				params.put("tranzCode", "4104");
				params.put("keyWord", businessLicense);
				params.put("userOnlyId", userOnlyId);
				ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
			}
			if("4103".equals(tranzCode)){
				Map<String, String> params = new HashMap<String, String>();
				params.put("tranzCode", "4103");
				params.put("keyWord", businessLicense);
				params.put("userOnlyId", userOnlyId);
				ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
			}
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(ret));
		} catch (Exception e) {
			log.error("saveInterface error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**婚姻状况 20 已婚 其他未婚
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryStoreInfo")
	@ResponseBody
	public JSONPObject queryStoreInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("queryStoreInfo begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("queryStoreInfo userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.idcard);
			map=customerInfoService.queryCustomerInfo(map);
			String maritalStatus=Convert.toStr(map.get("maritalStatus"));
			String userName=Convert.toStr(map.get("name"));
			String certNo=Convert.toStr(map.get("certNo"));
			String channelCode=Convert.toStr(map.get("channelCode"));
//			String appxuqi=Convert.toStr(map.get("subbranchName"));//判断是否是额度续期的用户
			String orgCode=Convert.toStr(map.get("orgCode"));
			String appxuqi="";
			log.info("queryStoreInfo userOnlyId="+userOnlyId+";channelCode:"+channelCode+";orgCode:"+orgCode);
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			String OrgProvinceName= Convert.toStr(vps.get(VpsInfoService.OrgProvinceName),"");
			String MainBusiness1= Convert.toStr(vps.get(VpsInfoService.MainBusiness1),"");
			String contactName="";
			String contactCertNo="";
			if(Convert.toStr(UhjConstant.maritalStatus.married).equals(maritalStatus)){
				map.clear();
				map.put("userOnlyId", userOnlyId);
				map.put("contactType", UhjConstant.contactType.spouse);
				map=customerInfoService.queryCustomerSpouseInfo(map);
				contactName=Convert.toStr(map.get("name"));
				contactCertNo=Convert.toStr(map.get("certNo"));
			}
			map.clear();
			map.put("userOnlyId", userOnlyId);
			if(!Check.isBlank(channelCode) && getCheckImg(channelCode,"1")){//判断不需要的
				log.info("queryStoreInfo 特殊渠道自主注册的用户不需要合影 userOnlyId:"+userOnlyId+";channelCode："+channelCode);
				appxuqi="appxuqi";
				map.clear();
				map.put("userOnlyId", userOnlyId);
				map.put("userName", userName);
				map.put("certNo", certNo);
				Map<String, Object> whiteMap=customerWhiteService.queryCustomerWhite(map); 
				if(whiteMap!=null){
					map.put("channelCode", channelCode);
					map.put("CustomerWhite", whiteMap.get("CustomerWhite"));
				}
			}
//			if(orgCode!=null && orgCode.indexOf("pc")!=0 && orgCode.indexOf("mobile")!=0 && OrgProvinceName.indexOf("河北")<0){
//				log.info("queryStoreInfo 不是自主注册的用户 userOnlyId:"+userOnlyId+";orgCode："+orgCode+";OrgProvinceName:"+OrgProvinceName);
//				appxuqi="appxuqi";
//			}
			map.put("MainBusiness1", MainBusiness1);
			log.info("queryStoreInfo map:"+map);
			Map<String, Object> resultMap=customerStoreService.queryCustomerStore(map);
			if(resultMap==null){
				resultMap=new HashMap<String, Object>();
			}
			if(!Check.isBlank(channelCode) && !UhjConstant.channelCode.ZGD_CHANNEL.equals(channelCode)
					&& !UhjConstant.channelCode.ZGD_HEBEI_CHANNEL.equals(channelCode)
					&& "true".equals(Convert.toStr(resultMap.get("businessFlag")))){
				Map<String,Object> ruleMap = new HashMap<String, Object>();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.business_license);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
			}
			String bzgMobile="";
			String bzgName="";
			if(appxuqi!=null && "appxuqi".equals(appxuqi)){//额度续期的不做图片校验
				resultMap.put("outsideFlag", "true");
				resultMap.put("innerFlag", "true");
			}else{
				Map<String, Object> vpsBzg=creditApplyService.queryBZGCreditPostMember(userOnlyId);
				if(vpsBzg!=null){
					bzgMobile=Convert.toStr(vpsBzg.get("bzgMobile"));
					bzgName=Convert.toStr(vpsBzg.get("bzgName"));
				}
			} 
			String businessName=userName;
			String businessNameCertNo=certNo;
			if(UhjConstant.customerType.contact.equals(Convert.toStr(resultMap.get("owner"), UhjConstant.customerType.loanor))){
				businessName=contactName;
				businessNameCertNo=contactCertNo;
			}
			List<Map<String, Object>> listMap=getBusinessLicenseList(userOnlyId,businessName, businessNameCertNo);
			resultMap.put("listMap", listMap);
			resultMap.put("bzgMobile", bzgMobile);
			resultMap.put("bzgName", bzgName);
			resultMap.put("appxuqi", appxuqi);
			resultMap.put("maritalStatus", maritalStatus);
			resultMap.put("userName", userName);
			resultMap.put("contactName", contactName);
			resultMap.put("code", "0000");
			log.info("queryStoreInfo userOnlyId:"+userOnlyId+";resultMap："+resultMap);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(resultMap));
		} catch (Exception e) {
			log.error("queryStoreInfo error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}

	/**查询用户名下营业执照数量列出来给用户选择
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryBusinessLicenseList")
	@ResponseBody
	public JSONPObject queryBusinessLicenseList(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("queryBusinessLicenseList begin.");
		try {
			Map<String, Object> resultMap=new HashMap<String, Object>();
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			String owner=Convert.toStr(request.getParameter("owner"));
			log.info("queryBusinessLicenseList userOnlyId="+userOnlyId+";owner:"+owner);
			Map<String, Object> map =new HashMap<String, Object>();
			String businessName="";
			String businessNameCertNo="";
			if(!UhjConstant.customerType.loanor.equals(owner)){
				map.clear();
				map.put("userOnlyId", userOnlyId);
				map.put("contactType", UhjConstant.contactType.spouse);
				map=customerInfoService.queryCustomerSpouseInfo(map);
				businessName=Convert.toStr(map.get("name"));
				businessNameCertNo=Convert.toStr(map.get("certNo"));
			}else{
				map.put("userOnlyId", userOnlyId);
				map.put("certType", UhjConstant.certType.idcard);
				map=customerInfoService.queryCustomerInfo(map);
				businessName=Convert.toStr(map.get("name"));
				businessNameCertNo=Convert.toStr(map.get("certNo"));
			}
			List<Map<String, Object>> listMap=getBusinessLicenseList(userOnlyId,businessName, businessNameCertNo);
			resultMap.put("listMap", listMap);
			resultMap.put("code", "0000");
			log.info("queryBusinessLicenseList userOnlyId:"+userOnlyId+";resultMap："+resultMap);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(resultMap));
		} catch (Exception e) {
			log.error("queryBusinessLicenseList error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**查询用户名下营业执照数量
	 * @return
	 */
	private List<Map<String, Object>> getBusinessLicenseList(String userOnlyId,String userName,String certNo){
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try{
			String result =null;
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			
			Map<String, Object> interfaceMap =new HashMap<String, Object>();
			interfaceMap.put("userOnlyId", userOnlyId);
			interfaceMap.put("tranzCode", UhjConstant.transCode.QIAN_HAI_ENTMGRINC);
			List<InterfaceAccessInfoWithBLOBs> interfaceList= interfaceAccessInfoService.queryInterfaceAccessInfoWithBLOBs(interfaceMap);
			if(interfaceList!=null && interfaceList.size()>0){
				for(InterfaceAccessInfoWithBLOBs blob:interfaceList){
					String requestInfo=new String(blob.getRequestInfo());
					log.info("queryBusinessLicenseList userOnlyId:"+userOnlyId+"; 数据库中前海接口日志 requestInfo:"+requestInfo);
					JSONObject js =JSONObject.fromObject(requestInfo);
					String name=Convert.toStr(js.get("name"));
					log.info("queryBusinessLicenseList userOnlyId:"+userOnlyId+"; 数据库中前海接口日志   name:"+name+";userName:"+userName);
					if(userName.equals(name)){
						result=new String(blob.getResponseInfo());
						log.info("queryBusinessLicenseList userOnlyId:"+userOnlyId+"; 数据库中前海接口日志    result:"+result);
						break;
					}
				}
			}
			
			
			if(Check.isBlank(result)){
				Map<String, String> param =new HashMap<String, String>();
				param.put("appId", userOnlyId);
				param.put("idCard", certNo);
				param.put("name", userName);
				param.put("userOnlyId", userOnlyId);
				param.put("tranzCode", UhjConstant.transCode.QIAN_HAI_ENTMGRINC);
				log.info("queryBusinessLicenseList param:"+param.toString());
				result = HttpClientUtil.sendPost(appkey_url, param);
				log.info("queryBusinessLicenseList  调用前海接口日志  userOnlyId:"+userOnlyId+"; result:"+result);
			}
			JSONObject js=JSONObject.fromObject(result);
			JSONObject entMgrInc=JSONObject.fromObject(js.get("entMgrInc"));
			JSONArray records=JSONArray.fromObject(entMgrInc.get("records"));
			Object[] strs = records.toArray(); //json转为数组 
		    for(Object object :strs){
		    	JSONObject objs=JSONObject.fromObject(object);
		    	String erCode=Convert.toStr(objs.get("erCode"));
				if(!"E000000".equals(erCode)){
					continue;
				}	
				JSONArray extInvstAndPosition=JSONArray.fromObject(objs.get("extInvstAndPosition"));
				Object[] arry = extInvstAndPosition.toArray(); //json转为数组 
			    for(Object obj :arry){
			    	Map<String, Object> map=new HashMap<String, Object>();
			    	JSONObject jsobj=JSONObject.fromObject(obj);
			    	String entStatus=Convert.toStr(jsobj.get("entStatus"));
			    	String establishDate=Convert.toStr(jsobj.get("establishDate"));
			    	String registerNo=Convert.toStr(jsobj.get("registerNo"));
			    	String entName=Convert.toStr(jsobj.get("entName"));
			    	String content=Convert.toStr(jsobj.get("content"));
			    	if(content!=null && (content.indexOf("法人")>=0 || content.indexOf("董事长")>=0)
			    			&& (Check.isBlank(entStatus) || entStatus.indexOf("注销")<0)){
			    		map.put("entName", entName);
				    	map.put("establishDate", establishDate);
				    	map.put("registerNo", registerNo);
//				    	if(registerNo!=null && registerNo.length()>4){
//				    		map.put("endRegisterNo", registerNo.substring(registerNo.length()-4, registerNo.length()));
//				    	}
				    	list.add(map);
			    	}
			    }
		    }
		}catch(Exception e){
			log.error("queryBusinessLicenseList  userOnlyId:"+userOnlyId+"; error ",e);
		}
		log.info("queryBusinessLicenseList  userOnlyId:"+userOnlyId+"; list:"+list);
		return list;
	}
	
	/**
	 * 用户选择或者输入营业执照编号后查询企业信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryBusinessLicense")
	@ResponseBody
	public JSONPObject queryBusinessLicense(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("queryBusinessLicense begin.");
		try {
			String result=null;
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("queryBusinessLicense userOnlyId="+userOnlyId);
			String businessLicense=request.getParameter("businessLicense");
			String owner=request.getParameter("owner");
			log.info("queryBusinessLicense userOnlyId:"+userOnlyId+"; businessLicense="+businessLicense+";owner:"+owner);
			if(Check.isBlank(businessLicense)){
				result=JsonResult.getInstance().addError("营业执照编号为空").toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			if(Check.isBlank(owner)){
				result=JsonResult.getInstance().addError("店铺所有人为空").toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if("1".equals(owner)){
				map.put("userOnlyId", userOnlyId);
				map.put("certType", UhjConstant.certType.idcard);
				map=customerInfoService.queryCustomerInfo(map);
			}else{
				map.put("userOnlyId", userOnlyId);
				map.put("contactType", UhjConstant.contactType.spouse);
				map=customerInfoService.queryCustomerSpouseInfo(map);
			}
			String name=Convert.toStr(map.get("name"));
			String certNo=Convert.toStr(map.get("certNo"));
			String phone=Convert.toStr(map.get("phone"));
			log.info("queryBusinessLicense userOnlyId:"+userOnlyId+"; name:"+name+";certNo:"+certNo+";phone:"+phone);
			//先去查企查查的模糊查询信息，再查精确查询  --2017-12  不去模糊查询了，直接返回  0000
			String retCode=checkBusinessLicense(name,certNo,phone,businessLicense,userOnlyId);
			if("0000".equals(retCode)){
				map.clear();
				map=getEnterpriseInformation(businessLicense,userOnlyId,name);
				String OperName=Convert.toStr(map.get("OperName"));
				if(OperName!=null && OperName.replaceAll(" ", "").indexOf(name)<0){
					result=JsonResult.getInstance().addError("请添加本人店铺").add("flag", "0").toString();
				}else{
					String storeName=Convert.toStr(map.get("storeName"),"");
					String storeAddress=Convert.toStr(map.get("storeAddress"),"");
					if(!Check.isBlank(storeName) && !Check.isBlank(storeAddress)){
						storeAddress=storeAddress.replaceAll("#", "号");
						//flag=2 营业执照编号已经查询出信息，店铺所有人，营业执照编号、名称地址都不可再修改
						log.info("queryBusinessLicense userOnlyId:"+userOnlyId+"; businessLicense="+businessLicense+";owner:"+owner);
						result=JsonResult.getInstance().addOk().add("storeName", storeName).add("storeAddress", storeAddress).add("flag", "2")
								.toString();
						Map<String,Object> ruleMap = new HashMap<String, Object>();
						ruleMap.put("userOnlyId", userOnlyId);
						ruleMap.put("ruleRefId", UhjConstant.ruleRefId.business_license);
						ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
						creditRuleService.saveCreditRuleService(ruleMap);
					}else{
						result=JsonResult.getInstance().addOk().add("storeName", storeName).add("storeAddress", storeAddress)
								.toString();
					}
				}
			}else if("2000".equals(retCode)){
				//返回前台错误的时候flag=1 可以接着往下做，flag=0不可以再往下做了
				result=JsonResult.getInstance().addError("您的营业执照已失效，请核对后重新录入！").add("flag", "0").toString();
			}else if("3000".equals(retCode)){
				//返回前台错误的时候flag=1 可以接着往下做，flag=0不可以再往下做了
				result=JsonResult.getInstance().addError("您的营业执照未满一年，请满一年后再次申请！").add("flag", "0").toString();
			}else if("4000".equals(retCode)){
				//返回前台错误的时候flag=1 可以接着往下做，flag=0不可以再往下做了
				result=JsonResult.getInstance().addError("营业执照所有人与营业执照号码不匹配！").add("flag", "0").toString();
			}else{
				result=JsonResult.getInstance().addOk().add("storeName", "").add("storeAddress", "").toString();
			}
			log.info("queryBusinessLicense userOnlyId:"+userOnlyId+";result："+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryBusinessLicense error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	/*@RequestMapping("/queryBusinessLicense")
	@ResponseBody
	public JSONPObject queryBusinessLicense(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("queryBusinessLicense begin.");
		try {
			String result=null;
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("queryBusinessLicense userOnlyId="+userOnlyId);
			String businessLicense=request.getParameter("businessLicense");
			String owner=request.getParameter("owner");
			log.info("queryBusinessLicense userOnlyId:"+userOnlyId+"; businessLicense="+businessLicense+";owner:"+owner);
			if(Check.isBlank(businessLicense)){
				result=JsonResult.getInstance().addError("营业执照编号为空").toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			if(Check.isBlank(owner)){
				result=JsonResult.getInstance().addError("店铺所有人为空").toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if("1".equals(owner)){
				map.put("userOnlyId", userOnlyId);
				map.put("certType", UhjConstant.certType.idcard);
				map=customerInfoService.queryCustomerInfo(map);
			}else{
				map.put("userOnlyId", userOnlyId);
				map.put("contactType", UhjConstant.contactType.spouse);
				map=customerInfoService.queryCustomerSpouseInfo(map);
			}
			String name=Convert.toStr(map.get("name"));
			String certNo=Convert.toStr(map.get("certNo"));
			String phone=Convert.toStr(map.get("phone"));
			log.info("queryBusinessLicense userOnlyId:"+userOnlyId+"; name:"+name+";certNo:"+certNo+";phone:"+phone);
			//先去查企查查的模糊查询信息，再查精确查询
			String retCode=checkQianhaiBusinessLicense(name,certNo,businessLicense,userOnlyId);
			if("0000".equals(retCode)){
				map.clear();
				map=getEnterpriseInformation(businessLicense,userOnlyId);
				String storeName=Convert.toStr(map.get("storeName"));
				String storeAddress=Convert.toStr(map.get("storeAddress"));
				if(!Check.isBlank(storeName) && !Check.isBlank(storeAddress)){
					//flag=2 营业执照编号已经查询出信息，店铺所有人，营业执照编号、名称地址都不可再修改
					log.info("queryBusinessLicense userOnlyId:"+userOnlyId+"; businessLicense="+businessLicense+";owner:"+owner);
					result=JsonResult.getInstance().addOk().add("storeName", storeName).add("storeAddress", storeAddress).add("flag", "2")
							.toString();
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.business_license);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					result=JsonResult.getInstance().addOk().add("storeName", storeName).add("storeAddress", storeAddress)
							.toString();
				}
			}else if("2000".equals(retCode)){
				//返回前台错误的时候flag=1 可以接着往下做，flag=0不可以再往下做了
				result=JsonResult.getInstance().addError("您的营业执照已失效，请核对后重新录入！").add("flag", "0").toString();
			}else if("3000".equals(retCode)){
				//返回前台错误的时候flag=1 可以接着往下做，flag=0不可以再往下做了
				result=JsonResult.getInstance().addError("您的营业执照未满一年，请满一年后再次申请！").add("flag", "0").toString();
			}else if("4000".equals(retCode)){
				//返回前台错误的时候flag=1 可以接着往下做，flag=0不可以再往下做了
				result=JsonResult.getInstance().addError("营业执照所有人与营业执照号码不匹配！").add("flag", "0").toString();
			}else{
				result=JsonResult.getInstance().addOk().add("storeName", "").add("storeAddress", "").toString();
			}
			log.info("queryBusinessLicense userOnlyId:"+userOnlyId+";result："+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryBusinessLicense error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}*/
	/*private String  checkQianhaiBusinessLicense(String name, String certNo,String businessLicense,String userOnlyId) {
		try{
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			params.put("tranzCode", "4108");
			params.put("idNo", certNo);
			params.put("name", name);
			params.put("userOnlyId", userOnlyId);
			String	ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			log.info("checkQianhaiBusinessLicense userOnlyId:"+userOnlyId+";ret"+ret);
			JSONObject js= JSONObject.fromObject(ret);
			JSONObject header= JSONObject.fromObject(js.get("header"));
			String rtCode=Convert.toStr(header.get("rtCode"));
			if(!"E000000".equals(rtCode)){
				return "1000";
			}
			JSONObject busiData= JSONObject.fromObject(js.get("busiData"));
			JSONArray records=JSONArray.fromObject(busiData.get("records"));
			Object[] strs = records.toArray(); //json转为数组 
		    for(Object object :strs){
		    	JSONObject objs=JSONObject.fromObject(object);
		    	String erCode=Convert.toStr(objs.get("erCode"));
				if(!"E000000".equals(erCode)){
					continue;
				}	
				JSONArray extInvstAndPosition=JSONArray.fromObject(objs.get("extInvstAndPosition"));
				Object[] arry = extInvstAndPosition.toArray(); //json转为数组 
			    for(Object obj :arry){
			    	JSONObject jsobj=JSONObject.fromObject(obj);
			    	String entStatus=Convert.toStr(jsobj.get("entStatus"));
			    	String establishDate=Convert.toStr(jsobj.get("establishDate"));
			    	String registerNo=Convert.toStr(jsobj.get("registerNo"));
			    	if(!businessLicense.equals(registerNo)){
			    		continue;
			    	}
					if(Check.isBlank(entStatus) || 
						(entStatus.indexOf("存续")<0 && entStatus.indexOf("在业")<0 
							&& entStatus.indexOf("在营")<0
							&& entStatus.indexOf("开业")<0
							&& entStatus.indexOf("正常")<0
							&& entStatus.indexOf("在册")<0)){
						return "2000";
					}
					if(!Check.isBlank(establishDate) && establishDate.length()>10){
						establishDate=establishDate.substring(0, 10);
						if(DateUtil.diffDays(DateUtil.currDateStr(), establishDate, DateUtil.YMD)<365){
							return "3000"; 
						}
					}
					return "0000";
			    }
		    }
		}catch(Exception e){
			log.info("checkBusinessLicense  error",e);
		}
		return "1000";

	}*/
	/**
	 * 企查查模糊查询
	 * @return
	 */
	private String checkBusinessLicense(String name, String certNo,String phone,String businessLicense,String userOnlyId) {
		return "0000";
		/*try{
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
			    	OperName=OperName.replaceAll(" ", "");
			    	String Status=Convert.toStr(objs.get("Status"));
			    	String StartDate=Convert.toStr(objs.get("StartDate"));
					if(name.equals(OperName)){
						//不包含存续  在业 在营、开业、在册 的提示用户
						if(Check.isBlank(Status) || 
							(Status.indexOf("存续")<0 && Status.indexOf("在业")<0
								&& Status.indexOf("在营")<0
								&& Status.indexOf("开业")<0
								&& Status.indexOf("正常")<0
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
//					else if(!Check.isBlank(OperName)){
//						return "4000";
//					}
			    }
			}
		}catch(Exception e){
			log.info("checkBusinessLicense  error",e);
		}
		return "1000";*/

	}
	
	/**
	 * 企查查精确查询
	 * @return
	 */
	private Map<String, Object> getEnterpriseInformation(String businessLicense,String userOnlyId,String userName){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String	ret = null;
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			params.put("tranzCode", "4103");
			params.put("keyWord", businessLicense);
			params.put("userOnlyId", userOnlyId);
			
			
			Map<String, Object> interfaceMap =new HashMap<String, Object>();
			interfaceMap.put("userOnlyId", userOnlyId);
			interfaceMap.put("tranzCode", "4103");
			List<InterfaceAccessInfoWithBLOBs> interfaceList= interfaceAccessInfoService.queryInterfaceAccessInfoWithBLOBs(interfaceMap);
			if(interfaceList!=null && interfaceList.size()>0){
				for(InterfaceAccessInfoWithBLOBs blob:interfaceList){
					String requestInfo=new String(blob.getRequestInfo());
					log.info("getEnterpriseInformation userOnlyId:"+userOnlyId+"; 数据库中企查查接口日志 requestInfo:"+requestInfo);
					JSONObject js =JSONObject.fromObject(requestInfo);
					String keyWord=Convert.toStr(js.get("keyWord"));
					log.info("getEnterpriseInformation userOnlyId:"+userOnlyId+"; 数据库中企查查接口日志   keyWord:"+keyWord+";businessLicense:"+businessLicense);
					if(businessLicense.equals(keyWord)){
						ret=new String(blob.getResponseInfo());
						log.info("getEnterpriseInformation userOnlyId:"+userOnlyId+"; 数据库中企查查接口日志    result:"+ret);
						break;
					}
				}
			}
			
			if(Check.isBlank(ret)){
				ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
				log.info("getEnterpriseInformation 调用企查查接口日志 userOnlyId:"+userOnlyId+";ret"+ret);
			}
			
			JSONObject js= JSONObject.fromObject(ret);
			JSONObject result = JSONObject.fromObject(js.get("Result"));
			if(result!=null && !result.isEmpty()){
				String OperName=Convert.toStr(result.get("OperName"));
				map.put("OperName", OperName);
				if(OperName!=null && (OperName.replaceAll(" ", "").indexOf(userName)>=0 || OperName.replaceAll("　", "").indexOf(userName)>=0)){
					map.put("storeName", result.get("Name"));
					map.put("storeAddress", result.get("Address"));
				}
			}
		} catch (Exception e) {
			log.error("getEnterpriseInformation error",e);
		}
		return map;
		
	}
	
	/**
	 * 根据营业执照号获取百融数据
	 * @param businessLicense
	 * @param userOnlyId
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public Map<String,Object> queryBRInfo(String businessLicense,String userOnlyId){
//		log.info("queryBRInfo businessLicense:"+businessLicense+" userOnlyId:"+userOnlyId);
//		Map<String,Object> requestMap = new HashMap<String, Object>();
//		requestMap.put("bizRegnum", businessLicense);
//		SldService sldService = new DefaultSldService();
//		 Map<String,Object>  responseMap =new HashMap<String, Object>();
//		 Request request = sldService.getRequest(DefaultSldService.REQUEST_TYPE_BR, BRTransCodeEnum.BR_COMP_QUERY.getTransCode(),requestMap);
//	     SldOperateLog sldOperateLog = new SldOperateLog("掌柜贷app", "掌柜贷app用户名", "BR", "", "掌柜贷app店铺查询企业工商信息百融征信产品数据。", "");
//	     request.setOpeartor(sldOperateLog);
//	     Response response = sldService.doService(request);
//	     Map<String,Object> resultMap=new HashMap<String, Object>();
//	     try{
//			if("000000".equals(response.getResponseCode())){  //000000,处理成功！ "000000".equals(response.getResponseCode())
//		 	     responseMap = response.getResponseMap();//我是处理后的数据,Map格式
//		 	    log.info("queryBRInfo responseMap==========" + responseMap);
//		 	     if(responseMap!=null && responseMap.size()>=1){
//		 	    	 Map<String,Object> productMap = (Map<String,Object>)responseMap.get("product");
//					 if(productMap!=null && productMap.size()>=1 && productMap.containsKey("data")){
//						 resultMap = (Map<String,Object>)productMap.get("data");
//				 	     log.info("queryBRInfo resultMap==========" + resultMap);
//				 	    return resultMap;
//					 }
//		 	     }
//		    }
//	     }catch(Exception e){
//	    	 log.error("queryBRInfo error:"+e.getMessage());
//	     }
//	     return null;
//	}
	
	/**
	 * 校验店铺图片
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/checkStoreImage")
	@ResponseBody
	public JSONPObject checkStoreImage(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("checkStoreImage begin.");
		try {
//			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			String result=null;
			String url=request.getParameter("url");
			String type=request.getParameter("type");
			String appxuqi=request.getParameter("appxuqi");
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("checkStoreImage userOnlyId="+userOnlyId+";type:"+type+";url:"+url);
			Map<String, Object> map =new HashMap<String, Object>();
//			map.put("userOnlyId", userOnlyId);
//			map=customerInfoService.queryCustomerInfo(map);
//			String appxuqi=Convert.toStr(map.get("subbranchName"));//判断是否是额度续期的用户
//			String orgCode=Convert.toStr(map.get("orgCode"));//取机构号判断是否是自助注册
			log.info("checkStoreImage userOnlyId="+userOnlyId+";appxuqi:"+appxuqi);
			if(appxuqi!=null && "appxuqi".equals(appxuqi)){
				if(UhjConstant.imageType.app_storeInner.equals(type)){
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else if(UhjConstant.imageType.app_storeOutside.equals(type)){
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				result=JsonResult.getInstance().addOk().toJsonStr();
				log.info("checkStoreImage 额度续期的用户 appxuqi userOnlyId:"+userOnlyId+";result："+result);
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			map.clear();
			map.put("userOnlyId", userOnlyId);
			Map<String,Object> returnMap=creditReturnService.queryReviewReturn(map);
			if(returnMap!=null && !returnMap.isEmpty()){
				CreditReturn creditReturn=(CreditReturn) returnMap.get("reason");
				String reason=creditReturn.getReason();
				log.info("checkStoreImage queryReviewReturn  userOnlyId:"+userOnlyId+";reason："+reason+";StorePhotoError:"+ReturnReasonEnum.StorePhotoError.getReasonCode());
				if(reason!=null && reason.indexOf(ReturnReasonEnum.StorePhotoError.getReasonCode())>0){
					result=JsonResult.getInstance().addOk().toJsonStr();
					log.info("checkStoreImage queryReviewReturn RT11 userOnlyId:"+userOnlyId+";result："+result);
					return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
				}
			}
			if(Check.isBlank(url)){
				result=JsonResult.getInstance().addError("店铺地址为空").toJsonStr();
				log.info("checkStoreImage userOnlyId:"+userOnlyId+";result："+result);
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			if(Check.isBlank(type) || (!type.equals(UhjConstant.imageType.app_storeOutside) && !type.equals(UhjConstant.imageType.app_storeInner))){
				result=JsonResult.getInstance().addError("店铺类型不正确，或者为空").toJsonStr();
				log.info("checkStoreImage userOnlyId:"+userOnlyId+";result："+result);
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			log.info("checkStoreImage userOnlyId:"+userOnlyId+";url="+url+";type:"+type);
//			String value=customerInfoService.getIsSuperKeyWord("store_yzs_province");
//			log.info(" checkStoreImage  店铺邮助手人脸省份设置  "+value);
//			boolean flag=false;
//			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
//			if(vps==null){
//				result=JsonResult.getInstance().addError("店铺照片不符合规则，请重新拍照。").toJsonStr();
//				log.info("checkStoreImage vps is null userOnlyId:"+userOnlyId+";result："+result);
//				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
//			}
//			String orgProvinceName=Convert.toStr(vps.get(VpsInfoService.OrgProvinceName));
//			if(!Check.isBlank(value)){
//				String values[]=value.split("@");
//				for(String key:values){
//					//判断是否是配置表省份的用户
//					if((orgProvinceName!=null && orgProvinceName.indexOf(key)>=0) || "1006135739".equals(userOnlyId)){
//						flag=true;
//					}
//				}
//			}
//			if(flag){
				result=checkStoreImageFaceNumber4(userOnlyId,url,type);//全国都一样
//			}else{
//				result=checkStoreImageFaceNumber3(userOnlyId,url,type);
//			}
			log.info("checkStoreImage userOnlyId:"+userOnlyId+";result："+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("checkStoreImage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 店铺内外部照片 百度人脸库校验
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/checkStoreImage1")
	@ResponseBody
	public JSONPObject checkStoreImage1(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("checkStoreImage begin.");
		try {
			String result=null;
			String url=request.getParameter("url");
			String type=request.getParameter("type");
			//String userOnlyId=CommonHelper.getUserOnlyId(request);
			
			Map<String, Object> resultMap = baiDuFaceService.faceMultiIdentify(url);
			
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(resultMap));
		} catch (Exception e) {
			log.error("checkStoreImage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
//	private boolean checkStoreImageFaceNumber (String ret,String userOnlyId,String url) throws Exception{
//		log.info(" .....statr.....  ");
//		String appkey_url = PropertiesHelper.getDfs("app_interface_url");
//		Map<String, String> headers = new HashMap<String, String>();
//		//抠图
//		log.info(" .....抠图开始..... ");
//		CutOutService cos = new CutOutService();
//		List<BufferedImage> bIs = cos.cutout(url, ret);
//		log.info(" .....抠图完成..... ");
//		
//		//Map<String, String> uploadParams = new HashMap<String, String>();//上传服务器参数
//		Map<String, Object> applyParams = new HashMap<String, Object>();//取人脸照片参数
//		Map<String, String> coparParams = new HashMap<String, String>();//人脸比对参数
//		String imgetype=url.substring(url.lastIndexOf(".") + 1).trim().toLowerCase();
//		String verifyImg_content="";
//		Double  score = new Double(0);
//		for(BufferedImage image:bIs){
//            ByteArrayOutputStream out = new ByteArrayOutputStream();  
//            ImageIO.write(image, imgetype, out);  
//            byte[] bytes = out.toByteArray(); 
//            verifyImg_content = StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false));
//            
//			//本人刷脸照
//			log.info(" .....查询本人刷脸照..... ");
//			applyParams.clear();
//			applyParams.put("userOnlyId", userOnlyId);
//			applyParams.put("imageType", UhjConstant.imageType.app_selfFace);
//			String selffaceImgUrl=applyImageService.queryApplyImageService(applyParams);
//			
//			//人脸比对
//			log.info(" .....人脸比对 上传照片与人脸照片.....  ");
//			coparParams.clear();
//			coparParams.put("tranzCode", "3106");
//			coparParams.put("verifyImg_content", verifyImg_content);
//			coparParams.put("appSelffaceImgUrl", selffaceImgUrl);
//			String	comparjson = com.ule.uhj.util.http.HttpClientUtil.sendPost(
//					appkey_url, headers, coparParams, UhjConstant.time_out);
//			
//			log.info(" .....人脸比对结果:"+comparjson+".....  ,userOnlyId:"+userOnlyId);
//			
//			JSONObject ob=JSONObject.fromObject(comparjson);
//			if("OK".equals(ob.get("message").toString())){
//				Double similarity =(Double)ob.get("pair_verify_similarity");//相识度
//				
//				if(score<similarity){
//					score=similarity;
//				}
//			}else{
//				log.info(ob.get("message").toString());							
//			}
//			
//		}
//		log.info(" .....人脸比对结果 end.....  score:"+score+";userOnlyId:"+userOnlyId);
//		if(score>=75){
//			return true;
//		}
//		log.info(" .....人脸比对结果 end.....  score:"+score+";userOnlyId:"+userOnlyId);
//		return false;
//	}
	
	/**
	 * 店铺内外部照片 
	 * 百度人脸库校验地推和掌柜
	 * 依图校验地推和掌柜
	 * @param appStoreImgUrl  店铺图片
	 * @param type     店铺类型
	 * @return
	 */
	private String checkStoreImageFaceNumber4(String userOnlyId,String appStoreImgUrl,String type) throws Exception{
		Map<String,Object> ruleMap = new HashMap<String, Object>();
		String result=storeImageBaidu(userOnlyId, appStoreImgUrl, type);
		log.info(" checkStoreImageFaceNumber4 百度识别结果 storeImageBaidu userOnlyId"+userOnlyId+" result  "+result);
		JSONObject js=JSONObject.fromObject(result);
		String yzsFlag=Convert.toStr(js.get("yzs"));
		String userFlag=Convert.toStr(js.get("user"));
		if("true".equals(yzsFlag) && "true".equals(userFlag)){
			if(type.equals(UhjConstant.imageType.app_storeOutside)){
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
				ruleMap.put("ruleOutput", "图片校验成功");
				creditRuleService.saveCreditRuleService(ruleMap);
			}else{
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
				ruleMap.put("ruleOutput", "图片校验成功");
				creditRuleService.saveCreditRuleService(ruleMap);
			}
			result=JsonResult.getInstance().addOk().toJsonStr();
			return result;
		}
		result=storeImageYiTu(userOnlyId, appStoreImgUrl, type,yzsFlag,userFlag);
		log.info(" checkStoreImageFaceNumber4 依图识别结果 storeImageYiTu userOnlyId"+userOnlyId+" result  "+result);
		JSONObject object=JSONObject.fromObject(result);
		yzsFlag=Convert.toStr(object.get("yzs"));
		userFlag=Convert.toStr(object.get("user"));
		if("true".equals(yzsFlag) && "true".equals(userFlag)){
			if(type.equals(UhjConstant.imageType.app_storeOutside)){
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
				ruleMap.put("ruleOutput", "图片校验成功");
				creditRuleService.saveCreditRuleService(ruleMap);
			}else{
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
				ruleMap.put("ruleOutput", "图片校验成功");
				creditRuleService.saveCreditRuleService(ruleMap);
			}
			result=JsonResult.getInstance().addOk().toJsonStr();
			return result;
		}else{
			if(!"true".equals(yzsFlag) && !"true".equals(userFlag)){
				if(type.equals(UhjConstant.imageType.app_storeOutside)){
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
					ruleMap.put("ruleOutput", "合影照未检测到已认证的地推人员和贷款申请人，请重新拍照。");
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
					ruleMap.put("ruleOutput", "合影照未检测到已认证的地推人员和贷款申请人，请重新拍照。");
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				result=JsonResult.getInstance().addError("合影照未检测到已认证的地推人员和贷款申请人，请重新拍照。").toJsonStr();
				return result;
			}
			if(!"true".equals(yzsFlag)){
				if(type.equals(UhjConstant.imageType.app_storeOutside)){
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
					ruleMap.put("ruleOutput", "合影照未检测到已认证的地推人员，请重新拍照。");
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
					ruleMap.put("ruleOutput", "合影照未检测到已认证的地推人员，请重新拍照。");
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				result=JsonResult.getInstance().addError("合影照未检测到已认证的地推人员，请重新拍照。").toJsonStr();
				return result;
			}
			
			if(!"true".equals(userFlag)){
				if(type.equals(UhjConstant.imageType.app_storeOutside)){
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
					ruleMap.put("ruleOutput", "合影照未检测到贷款申请人，请重新拍照。");
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					ruleMap.clear();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
					ruleMap.put("ruleOutput", "合影照未检测到贷款申请人，请重新拍照。");
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				result=JsonResult.getInstance().addError("合影照未检测到贷款申请人，请重新拍照。").toJsonStr();
				return result;
			}
		}
		return result;
	}
	/**
	 * 百度校验店铺内外部照片地推和掌柜
	 * @param appStoreImgUrl  店铺图片
	 * @param type     店铺类型
	 * @return
	 */
	private String storeImageBaidu(String userOnlyId,String appStoreImgUrl,String type)throws Exception{
		String yzsFlag="false";
		String userFlag="false";
		String result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
		try{
			String value=customerInfoService.getIsSuperKeyWord("store_yzs_score");
			log.info(" storeImageBaidu  店铺邮助手人脸 对比分值  "+value);
			Double peizhiScore=Convert.toDouble(value, new Double(75));
			Map<String, Object> resultMap = baiDuFaceService.faceMultiIdentify(appStoreImgUrl);
			String data=Convert.toStr(resultMap.get("data"));
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			log.info("storeImageBaidu 百度人脸对比 userOnlyId:"+userOnlyId+";data:"+data);
			if(Check.isBlank(data)){//百度接口没有返回数据
				result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
				return result;
			}
			JSONObject js=JSONObject.fromObject(data);
			if(js.get("error_code")!=null){//店铺图片有问题
				result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
				return result;
			}
			String  uid="";
			Double  baiduScores = new Double(0);
			Double  userScores = new Double(0);//用来记录用户最大的分值
			Double  yzsScores = new Double(0);//用来记录地推最大的分值
			String  yzsUid="";//用来记录对应的uid
			JSONArray imgDate=js.getJSONArray("result");
			if(imgDate!=null && imgDate.size()>0){
				int userIndex=100;
				Object[] strs = imgDate.toArray();
				//为了防止地推和掌柜是同一个人的情况
				//前一半下标是一个人脸，后一半下标是一个人脸，先遍历一遍取出掌柜对应的分值下标
				//再排除掌柜对应人脸的那一半下标，去判断地推的人脸分值
				for (int i = 0; i < strs.length; i++) {
					JSONObject objs=JSONObject.fromObject(strs[i]);
					JSONArray scoresArray=objs.getJSONArray("scores");
					uid=Convert.toStr(objs.get("uid"));
					Object[] arrays = scoresArray.toArray();
					for(Object array :arrays){//目前返回的只有一个分值，只遍历一次
						baiduScores=Convert.toDouble(array);
						if(uid.equals(userOnlyId) && userScores<baiduScores){
							userScores=baiduScores;
							userIndex=i;
						}
					}
				}
				for (int i = 0; i < strs.length; i++) {
					if(userIndex<strs.length/2){
						if(i<strs.length/2){
							continue;
						}
					}
					if(userIndex>=strs.length/2){
						if(i>=strs.length/2){
							continue;
						}
					}
					JSONObject objs=JSONObject.fromObject(strs[i]);
					JSONArray scoresArray=objs.getJSONArray("scores");
					uid=Convert.toStr(objs.get("uid"));
					String group_id=Convert.toStr(objs.get("group_id"));
					Object[] arrays = scoresArray.toArray();
					for(Object array :arrays){//目前返回的只有一个分值，只遍历一次
						baiduScores=Convert.toDouble(array);
						if("bangZGface".equals(group_id) && yzsScores<baiduScores){
							if(baiduScores>=peizhiScore){
								//判断百度返回的地推的省市和掌柜的是否是同一个省市
								//不是的就continue;
								if(!userInfoService.booleanUserYzsProvinceCity(userOnlyId, uid)){
									continue;
								}
							}
							yzsUid=uid;
							yzsScores=baiduScores;	
						}
					}
				}
//				for(Object object :strs){
//					JSONObject objs=JSONObject.fromObject(object);
//					JSONArray scoresArray=objs.getJSONArray("scores");
//					uid=Convert.toStr(objs.get("uid"));
//					String group_id=Convert.toStr(objs.get("group_id"));
//					Object[] arrays = scoresArray.toArray();
//					for(Object array :arrays){//目前只有一个分值，只遍历一次
//						baiduScores=Convert.toDouble(array);
//						if("bangZGface".equals(group_id) && yzsScores<baiduScores){
//							yzsUid=uid;
//							yzsScores=baiduScores;	
//						}
//						if(uid.equals(userOnlyId) && userScores<baiduScores){
//							userScores=baiduScores;
//						}
//					}
//				}
			}
			if(type.equals(UhjConstant.imageType.app_storeOutside)){
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.baidu_outyzs_similarity);
				ruleMap.put("ruleOutput",yzsUid+"|"+yzsScores);
				creditRuleService.saveCreditRuleService(ruleMap);
				
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.baidu_outuser_similarity);
				ruleMap.put("ruleOutput",userScores);
				creditRuleService.saveCreditRuleService(ruleMap);
			}else{
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.baidu_inyzs_similarity);
				ruleMap.put("ruleOutput",yzsUid+"|"+yzsScores);
				creditRuleService.saveCreditRuleService(ruleMap);
				
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.baidu_inuser_similarity);
				ruleMap.put("ruleOutput",userScores);
				creditRuleService.saveCreditRuleService(ruleMap);
			}
			if(yzsScores>=peizhiScore){
				yzsFlag="true";
			}
			if(userScores>=peizhiScore){
				userFlag="true";
			}
			result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
			return result;
		}catch(Exception e){
			log.info("storeImageBaidu   邮助手照片校验报错  userOnlyId:"+userOnlyId,e);
			result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
			return result;
		}
	}
	/**
	 * 依图校验店铺内外部照片地推和掌柜
	 * @param appStoreImgUrl  店铺图片
	 * @param type     店铺类型
	 * @return
	 */
	private String storeImageYiTu (String userOnlyId,String appStoreImgUrl,String type,String yzsFlag,String userFlag) throws Exception{
		log.info(" storeImageYiTu.....statr.....  ");
		String result=JsonResult.getInstance().addOk().toJsonStr();
		String appkey_url = PropertiesHelper.getDfs("app_interface_url");
		Map<String, String> headers = new HashMap<String, String>();
		//刷脸照
		Map<String, Object> applyParams = new HashMap<String, Object>();//取人脸照片参数
		//人脸比对
		Map<String, String> coparParams = new HashMap<String, String>();//人脸比对参数
		
		if("false".equals(yzsFlag)){
			log.info("storeImageYiTu 依图人脸对比 userOnlyId:"+userOnlyId+";yzsFlag:"+yzsFlag);
			Double  yzsScore = new Double(0);
			String staffId="";
			Map<String, Object> vpsBzg=creditApplyService.queryBZGCreditPostMember(userOnlyId);
			if(vpsBzg!=null && !Check.isBlank(vpsBzg.get("staffId"))){
				log.info("storeImageYiTu 地推信息 userOnlyId:"+userOnlyId+";vpsBzg:"+vpsBzg);
				staffId=Convert.toStr(vpsBzg.get("staffId"));
				String status =yzsPostmemberService.queryYzsPostmemberStatus(staffId);
				log.info("storeImageYiTu 地推实名状态 staffId "+staffId+"; status:"+status);
				if("1".equals(status)){
					applyParams.clear();
					applyParams.put("userOnlyId", staffId);
					applyParams.put("imageType", UhjConstant.imageType.yzs_postmember_face);
					String yzsImgUrl=applyImageService.queryApplyImageService(applyParams);
					if(!Check.isBlank(yzsImgUrl)){
						log.info("storeImageYiTu 依图人脸对比没有地推人脸图片  userOnlyId:"+userOnlyId+";yzsFlag:"+yzsFlag);
						coparParams.clear();
						coparParams.put("tranzCode", "3107");
						coparParams.put("appStoreinnerImgUrl", appStoreImgUrl);
						coparParams.put("appSelffaceImgUrl", yzsImgUrl);
						coparParams.put("userOnlyId", userOnlyId);
						log.info(" storeImageYiTu.....地推人脸比对 上传照片与人脸照片参数.....  ;coparParams:"+coparParams);
						String	ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
								appkey_url, headers, coparParams, UhjConstant.time_out);
						log.info(" storeImageYiTu.....地推人脸比对 返回结果.....  ;ret:"+ret);
						JSONObject object=JSONObject.fromObject(ret);
						if("OK".equals(object.get("message").toString())){
							yzsScore=(Double)object.get("pair_verify_similarity");//相识度
						}
						if(type.equals(UhjConstant.imageType.app_storeOutside)){
							Map<String,Object> ruleMap = new HashMap<String, Object>();
							ruleMap.put("userOnlyId", userOnlyId);
							ruleMap.put("ruleRefId", UhjConstant.ruleRefId.yitu_outyzs_similarity);
							ruleMap.put("ruleOutput", yzsScore);
							creditRuleService.saveCreditRuleService(ruleMap);
						}else{
							Map<String,Object> ruleMap = new HashMap<String, Object>();
							ruleMap.put("userOnlyId", userOnlyId);
							ruleMap.put("ruleRefId", UhjConstant.ruleRefId.yitu_inyzs_similarity);
							ruleMap.put("ruleOutput", yzsScore);
							creditRuleService.saveCreditRuleService(ruleMap);
						}
						
						if(yzsScore>=75){
							yzsFlag="true";
						}else{
							result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
							return result;
						}
					}
				}
			}else{
				log.info("storeImageYiTu 没有查询到绑定的地推信息 userOnlyId:"+userOnlyId);
			}
		}
		
		if("false".equals(userFlag)){
			log.info("storeImageYiTu 掌柜百度人脸对比 userOnlyId:"+userOnlyId+";userFlag:"+userFlag);
			Double  score = new Double(0);
			applyParams.clear();
			applyParams.put("userOnlyId", userOnlyId);
			applyParams.put("imageType", UhjConstant.imageType.app_selfFace);
			String selffaceImgUrl=applyImageService.queryApplyImageService(applyParams);
			
			coparParams.clear();
			coparParams.put("tranzCode", "3107");
			coparParams.put("appStoreinnerImgUrl", appStoreImgUrl);
			coparParams.put("appSelffaceImgUrl", selffaceImgUrl);
			coparParams.put("userOnlyId", userOnlyId);
			log.info(" storeImageYiTu.....掌柜人脸比对 上传照片与人脸照片参数.....  ;coparParams:"+coparParams);
			String	comparjson = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, coparParams, UhjConstant.time_out);
			log.info(" storeImageYiTu.....掌柜人脸比对 返回结果.....  ;comparjson:"+comparjson);
			JSONObject ob=JSONObject.fromObject(comparjson);
			if("OK".equals(ob.get("message").toString())){
				score=(Double)ob.get("pair_verify_similarity");//相识度
			}
			if(type.equals(UhjConstant.imageType.app_storeOutside)){
				Map<String,Object> ruleMap = new HashMap<String, Object>();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_similarity);
				ruleMap.put("ruleOutput", score);
				creditRuleService.saveCreditRuleService(ruleMap);
			}else{
				Map<String,Object> ruleMap = new HashMap<String, Object>();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_similarity);
				ruleMap.put("ruleOutput", score);
				creditRuleService.saveCreditRuleService(ruleMap);
			}
			log.info(" .....人脸比对结果 end.....  score:"+score+";userOnlyId:"+userOnlyId+";result:"+result);
			if(score>=75){
				userFlag="true";
			}else{
				result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
				return result;
			}
		}
		result=JsonResult.getInstance().addOk().add("yzs", yzsFlag).add("user", userFlag).toJsonStr();
		return result;
	}
	
	
	/**
	 * 店铺内外部照片 
	 * 依图校验店铺掌柜
	 * @param appStoreImgUrl  店铺图片
	 * @param type     店铺类型
	 * @return
	 */
	/*private String checkStoreImageFaceNumber3 (String userOnlyId,String appStoreImgUrl,String type) throws Exception{
		log.info(" .....statr.....  ");
		String result=JsonResult.getInstance().addOk().toJsonStr();
		
		String appkey_url = PropertiesHelper.getDfs("app_interface_url");
		Map<String, String> headers = new HashMap<String, String>();
		Double  score = new Double(0);
		//本人刷脸照
		Map<String, Object> applyParams = new HashMap<String, Object>();//取人脸照片参数
		log.info(" .....查询本人刷脸照..... ;userOnlyId:"+userOnlyId);
		applyParams.clear();
		applyParams.put("userOnlyId", userOnlyId);
		applyParams.put("imageType", UhjConstant.imageType.app_selfFace);
		String selffaceImgUrl=applyImageService.queryApplyImageService(applyParams);
		
		
		//人脸比对
		Map<String, String> coparParams = new HashMap<String, String>();//人脸比对参数
		coparParams.put("tranzCode", "3107");
		coparParams.put("appStoreinnerImgUrl", appStoreImgUrl);
		coparParams.put("appSelffaceImgUrl", selffaceImgUrl);
		coparParams.put("userOnlyId", userOnlyId);
		log.info(" .....人脸比对 上传照片与人脸照片参数.....  ;coparParams:"+coparParams);
		String	comparjson = com.ule.uhj.util.http.HttpClientUtil.sendPost(
				appkey_url, headers, coparParams, UhjConstant.time_out);
		
		log.info(" .....人脸比对结果:"+comparjson+".....  ,userOnlyId:"+userOnlyId);
		
		
		JSONObject ob=JSONObject.fromObject(comparjson);
		if("OK".equals(ob.get("message").toString())){
			
			//判断人脸数大于2
			JSONObject verify_detail=JSONObject.fromObject(ob.get("verify_detail"));
			JSONArray scoreList = JSONArray.fromObject(verify_detail.get("score_list"));  

	        int size = scoreList.size();  //相识度
			if(size>1){
				score=(Double)ob.get("pair_verify_similarity");//相识度
			}else{
				log.info(" .....人脸个数必须大于等于2 ;size"+size+";userOnlyId:"+userOnlyId);
				result=JsonResult.getInstance().addError("检测到的人脸数量不符合规则，请重新拍照。").toJsonStr();
				return result;
			}
		}
		
		if(type.equals(UhjConstant.imageType.app_storeOutside)){
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_similarity);
			ruleMap.put("ruleOutput", score);
			creditRuleService.saveCreditRuleService(ruleMap);
		}else{
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_similarity);
			ruleMap.put("ruleOutput", score);
			creditRuleService.saveCreditRuleService(ruleMap);
		}
		
		if(score<75){//调用百度的人脸对比
			score = new Double(0);
			log.info("storeImageBaidu 百度人脸对比掌柜 userOnlyId:"+userOnlyId);
			Map<String, Object> resultMap = baiDuFaceService.faceMultiIdentify(appStoreImgUrl);
			String data=Convert.toStr(resultMap.get("data"));
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			log.info("storeImageBaidu 百度人脸对比 userOnlyId:"+userOnlyId+";data:"+data);
			if(!Check.isBlank(data)){//百度接口没有返回数据
				JSONObject js=JSONObject.fromObject(data);
				if(js.get("error_code")==null){//店铺图片木有问题
					JSONArray imgDate=js.getJSONArray("result");
					if(imgDate!=null && imgDate.size()>0){
						Object[] strs = imgDate.toArray();
						for(Object object :strs){
							JSONObject objs=JSONObject.fromObject(object);
							JSONArray scoresArray=objs.getJSONArray("scores");
							String uid=Convert.toStr(objs.get("uid"));
							Object[] arrays = scoresArray.toArray();
							for(Object array :arrays){
								Double baiduScores=Convert.toDouble(array);
								if(uid.equals(userOnlyId) && score<baiduScores){
									score=baiduScores;
								}
							}
						}
					}
					if(type.equals(UhjConstant.imageType.app_storeOutside)){
						ruleMap.clear();
						ruleMap.put("userOnlyId", userOnlyId);
						ruleMap.put("ruleRefId", UhjConstant.ruleRefId.baidu_outuser_similarity);
						ruleMap.put("ruleOutput",score);
						creditRuleService.saveCreditRuleService(ruleMap);
					}else{
						ruleMap.clear();
						ruleMap.put("userOnlyId", userOnlyId);
						ruleMap.put("ruleRefId", UhjConstant.ruleRefId.baidu_inuser_similarity);
						ruleMap.put("ruleOutput",score);
						creditRuleService.saveCreditRuleService(ruleMap);
					}
				}
			}
		}
		if(type.equals(UhjConstant.imageType.app_storeOutside)){
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			if(score>=75){
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
				ruleMap.put("ruleOutput", "图片校验成功");
				creditRuleService.saveCreditRuleService(ruleMap);
				result=JsonResult.getInstance().addOk().toJsonStr();
			}else{
				//保存店铺内部过程结束
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_outside_reason);
				ruleMap.put("ruleOutput", "合影照未检测到贷款申请人，请重新拍照。");
				creditRuleService.saveCreditRuleService(ruleMap);
				log.info("上传的店铺外部照片不符合规则,userOnlyId:"+userOnlyId);
				result=JsonResult.getInstance().addError("合影照未检测到贷款申请人，请重新拍照。").toJsonStr();
			}
		}else{
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			if(score>=75){
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
				ruleMap.put("ruleOutput", "图片校验成功");
				result=JsonResult.getInstance().addOk().toJsonStr();
			}else{
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_photos);
				ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				creditRuleService.saveCreditRuleService(ruleMap);
				ruleMap.clear();
				ruleMap.put("userOnlyId", userOnlyId);
				ruleMap.put("ruleRefId", UhjConstant.ruleRefId.store_inside_reason);
				ruleMap.put("ruleOutput", "合影照未检测到贷款申请人，请重新拍照。");
				creditRuleService.saveCreditRuleService(ruleMap);
				result=JsonResult.getInstance().addError("合影照未检测到贷款申请人，请重新拍照。").toJsonStr();
			}
		}
		log.info(" .....人脸比对结果 end.....  score:"+score+";userOnlyId:"+userOnlyId+";result:"+result);
		return result;
	}*/
	/**
	 * 只有人脸的个数大于等于2才可以通过
	 * @param ret
	 * @param userOnlyId
	 * @return
	 */
	/*private boolean checkStoreImageForFaceDetect(String ret,String userOnlyId){
		log.info("checkStoreImageForFaceDetect userOnlyId:"+userOnlyId+";ret:"+ret);
		try {
			if(Check.isBlank(ret) || ret.indexOf("error")>0){
				log.info("checkStoreImageForFaceDetect false userOnlyId:"+userOnlyId+"; ret:"+ ret);
				return false;
			}
			JSONArray  ja=JSONArray.fromObject(ret);
			if(ja==null){
				log.info("checkStoreImageForFaceDetect false userOnlyId:"+userOnlyId+"; ja:"+ ja);
				return false;
			}
			if(ja.size()<=1){
				log.info("checkStoreImageForFaceDetect false userOnlyId:"+userOnlyId+"; ja.size():"+ ja.size());
				return false;
			}
			if(ja.size()>=2){
				log.info("checkStoreImageForFaceDetect true userOnlyId:"+userOnlyId+"; ja.size():"+ ja.size());
				return true;
			}
		} catch (Exception e) {
			log.info("checkStoreImageForFaceDetect error userOnlyId:"+userOnlyId,e);
			return false;
		}
		return false;
		
	}*/
	/**
	 * 1、描述小于0.6
	 * 2、描述为空或包含case、counter
	 * 3、类别1<0.6
	 * 4、类别1包括abstract、text（空除外）
	 * 5、标签1为空或<0.9
	 * 6、标签1不在以下值范围（indoor、store、shop、marketplace、scene、shelf）
	 * 7、标签2为空或<0.8
	 * @param ret
	 * @return
	 */
	/*private boolean checkStoreInnerAnalysis(String ret){
		log.info("checkStoreInnerAnalysis  ret:"+ret);
		try {
			List<String> list=analyzeJsonStr(ret);
			String text=list.get(0);//描述为空或包含case、counter
			String confidence=list.get(1);//描述小于0.6
			String categories1=list.get(2);//类别1包括abstract、text（空除外）
			String categoriesScore1=list.get(3);//类别1<0.6
			String tags1=list.get(6);//标签1不在以下值范围（indoor、store、shop、marketplace、scene、shelf）
			String tagsScore1=list.get(7);//标签1为空或<0.9
			String tagsScore2=list.get(9);//标签2为空或<0.8
			log.info("checkStoreInnerAnalysis text:"+text);
			log.info("checkStoreInnerAnalysis confidence:"+confidence);
			log.info("checkStoreInnerAnalysis categories1:"+categories1);
			log.info("checkStoreInnerAnalysis categoriesScore1:"+categoriesScore1);
			log.info("checkStoreInnerAnalysis tags1:"+tags1);
			log.info("checkStoreInnerAnalysis tagsScore1:"+tagsScore1);
			log.info("checkStoreInnerAnalysis tagsScore2:"+tagsScore2);
			if(Check.isBlank(text) || text.indexOf("case")>=0 || text.indexOf("counter")>=0){
				log.info("checkStoreInnerAnalysis rule1 text:"+text);
				return false;
			}else if(Convert.toBigDecimal(confidence).compareTo(new BigDecimal("0.6"))<0){
				log.info("checkStoreInnerAnalysis rule2 confidence:"+confidence);
				return false;
			}else if(!Check.isBlank(categories1) && (categories1.indexOf("abstract")>=0 || categories1.indexOf("text")>=0)){
				log.info("checkStoreInnerAnalysis rule3 categories1:"+categories1);
				return false;
			}else if(Convert.toBigDecimal(categoriesScore1).compareTo(new BigDecimal("0.6"))<0){
				log.info("checkStoreInnerAnalysis rule4 categoriesScore1:"+categoriesScore1);
				return false;
			}else if ("indoor、store、shop、marketplace、scene、shelf".indexOf(tags1)<0){
				log.info("checkStoreInnerAnalysis rule5 tags1:"+tags1);
				return false;
			}else if (Check.isBlank(tagsScore1) || Convert.toBigDecimal(tagsScore1).compareTo(new BigDecimal("0.9"))<0){
				log.info("checkStoreInnerAnalysis rule6 tagsScore1:"+tagsScore1);
				return false;
			}else if (Check.isBlank(tagsScore2) || Convert.toBigDecimal(tagsScore2).compareTo(new BigDecimal("0.8"))<0){
				log.info("checkStoreInnerAnalysis rule7 tagsScore2:"+tagsScore2);
				return false;
			}
		} catch (Exception e) {
			log.info("checkStoreInnerAnalysis error ",e);
			return false;
		}
		return true;
		
	}
	private boolean checkStoreOutsideAnalysis(String ret,String storeName){
		log.info("checkStoreOutsideAnalysis ret:"+ret);
		log.info("checkStoreOutsideAnalysis storeName:"+storeName);
		try {
			Map map = new ObjectMapper().readValue(ret, Map.class);
    		String dataValueStr = null;
    		if(!Check.isBlank(map.get("outputs"))   
    				&& !Check.isBlank(((List)map.get("outputs")).get(0))
    				&& !Check.isBlank( ((Map)((List)map.get("outputs")).get(0)).get("outputValue")  )
    				&& !Check.isBlank( dataValueStr = (String)((Map)((Map)((List)map.get("outputs")).get(0)).get("outputValue")).get("dataValue") )
    				){
    			if(!Check.isBlank(dataValueStr)){
    				Map readValue = new ObjectMapper().readValue(dataValueStr, Map.class);
    				if("true".equals(String.valueOf(readValue.get("success")))){
	    				String  shop_sign=String.valueOf(readValue.get("shop_sign"));
	    				char[] ch=shop_sign.toCharArray();
	    				int count=0;
	    				for(char c:ch){
	    					if(storeName.indexOf(c)>=0){
	    						count++;
	    					}
	    				}
	    				if(count>=5){
	    					return true;
	    				}
	    			}
    			}
    		}
		} catch (Exception e) {
			log.info("checkStoreOutsideAnalysis error ",e);
			return false;
		}
		
		return false;
		
	}*/
	/** headers.add("标题");
		headers.add("可能性");
		headers.add("类别名称1");
		headers.add("分数1");
		headers.add("类别名称2");
		headers.add("分数2");
		headers.add("标签1");
		headers.add("可能性1");
		headers.add("标签2");
		headers.add("可能性2");
		headers.add("标签3");
		headers.add("可能性3");
		headers.add("标签4");
		headers.add("可能性4");
		headers.add("标签5");
		headers.add("可能性5");
	 * @param result
	 * @return
	 */
	/*private  List<String>  analyzeJsonStr(String result){
		List<String> list =new ArrayList<String>();
     	JSONObject js=JSONObject.fromObject(result);
     	
     	try {
			JSONObject description = js.getJSONObject("description");
			if (null != description) {

				JSONArray captions = description.getJSONArray("captions");
				if (null != captions) {

					Iterator<Object> it2 = captions.iterator();
					while (it2.hasNext()) {
						JSONObject ob = (JSONObject) it2.next();
						String text = Convert.toStr(ob.getString("text"), " ");
						String confidence = Convert.toStr(
								ob.getString("confidence"), " ");
						list.add(text);
						list.add(confidence);
//						log.info(String.format(
//								"captions text:%s,confidence:%s", text,
//								confidence));
						break;
					}
				} else {
					list.add(" ");
					list.add(" ");
				}
			} else {
				list.add(" ");
				list.add(" ");
			}
		} catch (Exception e) {
			log.error("description"+e.getMessage());
		}
		int m=0;
     	try {
			 
			JSONArray categories = js.getJSONArray("categories");
			if (null != categories) {
				Iterator<Object> it = categories.iterator();
				while (it.hasNext()) {
					++m;
					JSONObject ob = (JSONObject) it.next();
					String name = Convert.toStr(ob.getString("name"), " ");
					String score = Convert.toStr(ob.getString("score"), " ");
//					log.info(String.format(	"categories name:%s,score:%s,times:%s", name,score, m + ""));
					if (m < 3) {
						list.add(name);
						list.add(score);
					}
				}

			}
			if (m< 2) {
				log.info("categories 数据不足，补空值"+m);
				for (int j = 0; j < 2 - m; j++) {
					list.add(" ");
					list.add(" ");
				}
			}
		} catch (Exception e) {
			log.error("categories"+e.getMessage());
		}
		try {
			JSONArray tags = js.getJSONArray("tags");
			if (null != tags) {

				Iterator<Object> it1 = tags.iterator();
				m = 0;
				while (it1.hasNext()) {
					++m;
					JSONObject ob = (JSONObject) it1.next();
					String name = Convert.toStr(ob.getString("name"), " ");
					String confidence = Convert.toStr(
							ob.getString("confidence"), " ");
//					log.info(String.format("tags name:%s,confidence:%s,times:%s", name,	confidence, m + ""));
					if (m < 6) {
						list.add(name);
						list.add(confidence);
					}

				}
			}
			if (m < 5) {
				log.info("tags 数据不足，补空值"+m);
				for (int j = 0; j < 5 - m; j++) {
					list.add(" ");
					list.add(" ");
				}
			}
		} catch (Exception e) {
			log.error("tags"+e.getMessage());
		}
		log.info("list:"+list.toString());
		return list;
     }*/
	/**
	 * 
	 * 保存部分店铺信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/savePartStoreInfo")
	@ResponseBody
	public JSONPObject savePartStoreInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("savePartStoreInfo begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("savePartStoreInfo userOnlyId="+userOnlyId);
			String businessLicense=request.getParameter("businessLicense");//营业执照   证件表
			
			String owner=request.getParameter("owner");//owner   店铺所有人(地址的用户类型)   1 是本人  2是配偶       地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断是本人的
			String addressType=UhjConstant.addressType.store_address;//地址类型
			String propertyType=request.getParameter("propertyType");//店铺房产类型  0是自有 1：租赁    地址表
			String storeAddress=request.getParameter("storeAddress");//店铺地址     地址表
			
			String storeInner=request.getParameter("storeInner");//店铺内部照片    已经保存早图片表了不用保存
			String storeOutside=request.getParameter("storeOutside");//店铺外部照片   已经保存早图片表了不用保存
			
//			String incomeType=request.getParameter("incomeType");//收入类型     收入表
//			String incomeSubType=request.getParameter("incomeSubType");//收入子类型    收入表
//			String description=request.getParameter("description");//备注        收入表
//			String incomeAmount=request.getParameter("incomeAmount");//金额      收入表
			
			String storeInnerGPS=request.getParameter("storeInnerGPS");//店铺内部GPS定位信息    店铺表
			String storeOutsideGPS=request.getParameter("storeOutsideGPS");//店铺外部GPS定位信息     店铺表
			String storeName=request.getParameter("storeName");//店铺名称     店铺表     要和地址表关联地址ID
//			String storeArea=request.getParameter("storeArea");//店铺面积          店铺表
//			String storeTurnover=request.getParameter("storeTurnover");//店铺营业额（每日）   店铺表
			String rent=request.getParameter("rent");//租赁金额      店铺表
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			log.info("savePartStoreInfo userOnlyId:"+userOnlyId+"; map:"+map);
			map.put("userOnlyId", userOnlyId);
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("userOnlyId", userOnlyId);
			addressMap.put("addressType", UhjConstant.addressType.store_address);
			addressMap.put("address", storeAddress);
			addressMap.put("customerType", owner);
			addressMap.put("propertyType", propertyType);
			addressMap.put("storeInnerGPS", storeInnerGPS);
			String addressId=customerAddressService.saveCustomerAddress(addressMap);
			map.put("addressId", addressId);
			log.info("savePartStoreInfo userOnlyId:"+userOnlyId+";map:"+map);
			customerStoreService.saveCustomerStore(map);
			String result =JsonResult.getInstance().addOk().toString();
			log.info("savePartStoreInfo userOnlyId:"+userOnlyId+";result："+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("savePartStoreInfo error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 
	 * 保存店铺信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/saveStoreInfo")
	@ResponseBody
	public JSONPObject saveStoreInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("saveStoreInfo begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("saveStoreInfo userOnlyId="+userOnlyId);
			String businessLicense=request.getParameter("businessLicense");//营业执照    店铺表
			
			String owner=request.getParameter("owner");//owner   店铺所有人(地址的用户类型)   1 是本人  2是配偶       地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断是本人的
			String addressType=UhjConstant.addressType.store_address;//地址类型
			String propertyType=request.getParameter("propertyType");//店铺房产类型  0是自有 1：租赁    地址表
			String storeAddress=request.getParameter("storeAddress");//店铺地址     地址表
			
			String storeInner=request.getParameter("storeInner");//店铺内部照片    已经保存早图片表了不用保存
			String storeOutside=request.getParameter("storeOutside");//店铺外部照片   已经保存早图片表了不用保存
			
			String incomeType=request.getParameter("incomeType");//收入类型     收入表
			String incomeSubType=request.getParameter("incomeSubType");//收入子类型    收入表
			String description=request.getParameter("description");//备注        收入表
			String incomeAmount=request.getParameter("incomeAmount");//金额      收入表
			
			String storeInnerGPS=request.getParameter("storeInnerGPS");//店铺内部GPS定位信息    店铺表
			String storeOutsideGPS=request.getParameter("storeOutsideGPS");//店铺外部GPS定位信息     店铺表
			String storeName=request.getParameter("storeName");//店铺名称     店铺表     要和地址表关联地址ID
			String storeArea=request.getParameter("storeArea");//店铺面积          店铺表
			String storeTurnover=request.getParameter("storeTurnover");//店铺营业额（每日）   店铺表
			String rent=request.getParameter("rent");//租赁金额      店铺表
			String storeType = request.getParameter("storeType");//店铺类型
			String mainBusiness = request.getParameter("mainBusiness");//从商家组获取的掌柜店铺营业类型转化,没有的让掌柜自己选择（参照StoreBusinessType.java）
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			map.put("userOnlyId", userOnlyId);
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("userOnlyId", userOnlyId);
			addressMap.put("addressType", UhjConstant.addressType.store_address);
			addressMap.put("address", storeAddress);
			addressMap.put("customerType", owner);
			addressMap.put("propertyType", propertyType);
			addressMap.put("storeInnerGPS", storeInnerGPS);
			String addressId=customerAddressService.saveCustomerAddress(addressMap);
			map.put("addressId", addressId);
			log.info("saveStoreInfo  userOnlyId:"+userOnlyId+";map:"+map);
			customerStoreService.saveCustomerStore(map);
			
			forecastResultService.saveChooseResult(userOnlyId, mainBusiness);
			//保存店铺信息过程结束
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.shops_fill);
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
			creditRuleService.saveCreditRuleService(ruleMap);
			String result =JsonResult.getInstance().addOk().toString();
			log.info("saveStoreInfo userOnlyId:"+userOnlyId+";result："+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("saveStoreInfo error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 
	 * 查询是否商超
	 * http://money.beta.ule.com:8080/lendvps/store/queryIsSuper.do?jsoncallback=jsonp_1505196829886_48655
	 * 
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryIsSuper")
	@ResponseBody
	public JSONPObject queryIsSuper(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String jsoncallback) {
		log.info("queryIsSuper begin.");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			log.info("queryIsSuper userOnlyId=" + userOnlyId);

			int isSuper = 1;

			// isSuper业务重新判断是否为商超 start
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);// 10000026255、10000026256测试账号
			if (null != vps && !vps.isEmpty()) {

				log.info("isSuper业务重新判断 vps接口返回信息：" + vps.toString());
				String mainBusiness1 = Convert.toStr(vps.get("MainBusiness1"));// 业务类型
				String leagueChannel = Convert.toStr(vps.get("LeagueChannel"));// 渠道
																				// 0:邮乐渠道,1:邮政渠道,3:web自主注册,4:pc端自主注册,5:app自主注册,6:H5自主注册
				log.info("isSuper业务重新判断 leagueChannel=" + leagueChannel + ",mainBusiness1=" + mainBusiness1);
				if (Check.isBlank(leagueChannel) || "0".equals(leagueChannel) || "1".equals(leagueChannel)) {
					isSuper = 1;
				} else {
					isSuper = 0;
					// ("2","便利店超市","1"),("14","粮油干货店","4"),("16","烟酒店","4")
					if (Check.isBlank(mainBusiness1) || "2".equals(mainBusiness1) || "14".equals(mainBusiness1)
							|| "16".equals(mainBusiness1)) {
						isSuper = 1;
					}
				}
				log.info("isSuper业务重新判断 isSuper=" + isSuper);

			}
			// isSuper业务重新判断是否为商超 end
			String result = JsonResult.getInstance().addOk().add("isSuper", isSuper).toString();
			log.info("queryIsSuper userOnlyId:" + userOnlyId + ";result：" + result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryIsSuper error!", e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	public boolean getCheckImg(String channelCode, String useType) {
		boolean rs=false;
			ProductInfoN productInfo = getProductInfoN(channelCode,useType);
			log.info("查询产品表结果 :"+JSON.toJSONString(productInfo));
			if(productInfo!=null && StringUtil.isNotEmpty(productInfo.getPhotoCheck())){
				if("0".equals(productInfo.getPhotoCheck())){
					rs=true;
				}else if("1".equals(productInfo.getPhotoCheck())){
					rs=false;
				}
						;
			}else{
				if(!UhjConstant.channelCode.ZGD_CHANNEL.equals(channelCode) && !UhjConstant.channelCode.ZGD_HEBEI_CHANNEL.equals(channelCode)){
					rs=true;
				}else{
					rs=false;
				}
			}
		return rs;
	}
	public ProductInfoN getProductInfoN(String channelCode,String useType){
		ProductInfoN rs =null;
		
		ProductInfoN info = new ProductInfoN();
		info.setChannelCode(channelCode);
		info.setUseType(useType);
		
		ZgdQueryClient zgdQueryClient;
		try {
			zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			List<ProductInfoN> list = zgdQueryClient.queryProductInfo(info);
			if(!CollectionUtils.isEmpty(list)){
				rs=list.get(0);
			}
		} catch (Exception e) {
			log.error("getProductInfoN error:", e);
		}
		return rs;
	}
}
