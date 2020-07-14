//package com.ule.uhj.sldProxy.controller;
//
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.ule.merchant.ctoc.basic.vo.MerchantStaff;
//import com.ule.merchant.merchant.ejb.client.SysPostMerchantClient;
//import com.ule.merchant.merchant.util.MerchantBaseTools;
//import com.ule.uhj.sld.util.Convert;
//import com.ule.uhj.sld.util.SldSign;
//import com.ule.uhj.sldProxy.util.PropertiesUtil;
//import com.ule.uhj.sldProxy.util.ProxyResponse;
//@Controller
//@RequestMapping(value = "/mer")
//public class MerchentAccessController {
//	private static Logger log = LoggerFactory.getLogger(MerchentAccessController.class);
//	
//	private static final String SECRET_KEY = PropertiesUtil.getPropertyValue("UhjProxyAccessKey");
//	
//	private static String DATE_FORMART = "yyyy-MM-dd HH:mm:ss";
//	
//	@RequestMapping("/createMerchantAccount")
//	@ResponseBody
//	public String createMerchantAccount(@RequestBody Map request) throws Exception {
//		try {
//			ProxyResponse response = ProxyResponse.responeSuccess();
//			log.info("createMerchantAccount 调用商家组接口创建法人账号,param="+request);
//			SysPostMerchantClient client =MerchantBaseTools.getInstance().getSysPostMerchantClient();
//			MerchantStaff staff = new MerchantStaff();
//			staff.setMerchantId(Convert.toLong(request.get("merId")));
//			staff.setName(Convert.toStr(request.get("name")));
//			staff.setTrueName(Convert.toStr(request.get("trueName")));
//			staff.setPassword(Convert.toStr(request.get("password")));
//			staff.setPhoneNumber(Convert.toLong(request.get("phoneNumber")));
////			staff.setPhoneNumberStr(Convert.toStr(request.get("phoneNumber")));
//			Map<String,Object> merMap = client.addMerchantStaffLegal(staff);
//			log.info("createMerchantAccount 调用商家组接口创建法人账号,resultMap="+merMap);
//			if("0".equals(Convert.toStr(merMap.get("returnCode"),""))){
//				List<MerchantStaff> msList = client.findStaffsByMerchantId(Convert.toLong(request.get("merId")));
//				if(msList!=null&&msList.size()>0){
//					for(MerchantStaff staffInfo:msList){
//						if(Convert.toStr(request.get("phoneNumber")).equals(Convert.toStr(staffInfo.getPhoneNumber()))){
//							Long staffId = staffInfo.getId();
//							Map<String,Object> map = new HashMap<String, Object>();
//							map.put("staffId",staffId);
//							response.setDataMap(map);
//						}
//					}
//				}else{
//					log.error("createMerchantAccount error... 查询法人账号staffId失败!");
//					response = ProxyResponse.responeError();
//				}
//			}else{
//				response.setCode(Convert.toStr(merMap.get("returnCode")));
//				response.setMessage(Convert.toStr(merMap.get("returnMsg")));
//			}
//			return JSONObject.toJSONString(response);
//		} catch (Exception e) {
//			log.error("createMerchantAccount 调用商家组接口创建法人账号失败!", e);
//		}
//		return JSONObject.toJSONString(ProxyResponse.responeError());
//	}
//	
//	@RequestMapping("/checkPhoneIsNotRegister")
//	@ResponseBody
//	public String checkPhoneIsNotRegister(@RequestBody Map request) throws Exception {
//		ProxyResponse response = ProxyResponse.responeSuccess();
//		try {
//			log.info("checkPhoneIsNotRegister 调用商家组接口校验商家手机号是否被注册,param="+request);
//			Map<String,Object> map = new HashMap<String, Object>();
//			SysPostMerchantClient client =MerchantBaseTools.getInstance().getSysPostMerchantClient();
//			String regPhone = Convert.toStr(request.get("phoneNumber"),"");
//			List<MerchantStaff> msQueryList = client.findStaffsByMerchantId(Convert.toLong(request.get("merId")));
//			for(MerchantStaff merStaff:msQueryList){
//				if(Convert.toStr(merStaff.getPhoneNumber(),"").equals(regPhone)
//						||Convert.toStr(merStaff.getPhoneNumberStr(),"").equals(regPhone)){
//					map.put("accName", merStaff.getName());
//					map.put("staffId", merStaff.getId());
//					response.setCode("0001");
//					response.setMessage("手机号【"+regPhone+"】已被注册!");
//					response.setDataMap(map);
//					return JSONObject.toJSONString(response);
//				}
//			}
//		} catch (Exception e) {
//			log.error("checkPhoneIsNotRegister 调用商家组接口校验商家手机号是否被注册失败!", e);
//			return JSONObject.toJSONString(ProxyResponse.responeError());
//		}
//		return JSONObject.toJSONString(response);
//	}
//	
//	@RequestMapping("/updatePassWordForMerchant")
//	@ResponseBody
//	public String updatePassWordForMerchant(@RequestBody Map request) {
//		try {
//			log.info("updatePassWordForMerchant 调用商家组接口修改法人账号密码,param="+request);
//			long merId = Convert.toLong(request.get("merId"));
//			String newpassword = Convert.toStr(request.get("newpassword"));
//			String id = Convert.toStr(request.get("staffId"));
//			SysPostMerchantClient client = MerchantBaseTools.getInstance().getSysPostMerchantClient();
//			List<MerchantStaff> msList = client.findStaffsByMerchantId(merId);
//			for(MerchantStaff merStaff:msList){
//				if(id!=null&&Convert.toStr(merStaff.getId()).equals(id)){
//					Map<String,Object> merMap = client.updateMerchantStaffPassword(Convert.toLong(id),merId,newpassword);
//					log.info("updatePassWordForMerchant 调用商家组接口修改法人账号密码,resultMap="+merMap);
//					if("0".equals(Convert.toStr(merMap.get("returnCode"),""))){
//						return JSONObject.toJSONString(ProxyResponse.responeSuccess());
//					}else{
//						ProxyResponse response = new ProxyResponse(Convert.toStr(merMap.get("returnCode")), Convert.toStr(merMap.get("returnMsg")));
//						return JSONObject.toJSONString(response);
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.error("updatePassWordForMerchant 调用商家组接口修改法人账号密码失败!", e);
//		}
//		return JSONObject.toJSONString(ProxyResponse.responeError());
//	}
//	
//	
//	@RequestMapping("/findSuperStaffsByMerId")
//	@ResponseBody
//	public String findSuperStaffsByMerchantId(@RequestBody Map request) {
//		//项目迁移 商乐贷暂停
////				return null;
//		try{
////			securityVerfiy(request,SECRET_KEY);
//			Long custId= Convert.toLong(request.get("custId"));
//			SysPostMerchantClient sclient = MerchantBaseTools.getInstance().getSysPostMerchantClient();
//			MerchantStaff ms = sclient.findSuperStaffsByMerchantId(custId);
//			if(ms!=null){
//				ProxyResponse response = ProxyResponse.responeSuccess();
//				Map<String, Object> dataMap = new HashMap<String,Object>();
//				dataMap.put("staffId", ms.getId()==null?"":ms.getId());
//				dataMap.put("staffName", ms.getName()==null?"":ms.getName());
//				dataMap.put("custId", ms.getMerchantId()==null?"":ms.getMerchantId());
//				dataMap.put("status", ms.getStatus()==null?"":ms.getStatus());
//				response.setDataMap(dataMap);
//				return JSONObject.toJSONString(response);
//			} else {
//				throw new Exception("findSuperStaffsByMerchantId 商家组接口未查询到相应数据 custId"+custId);
//			}
//		}catch(Exception e){
//			log.error("findSuperStaffsByMerchantId调用异常 ", e);
//			return JSONObject.toJSONString(ProxyResponse.responeError());
//		}
//	}
//	
//	
//	@RequestMapping("/queryMerInfo")
//	@ResponseBody
//	public String queryMerInfoByCustId(HttpServletRequest request, String custId){
//		//项目迁移 商乐贷暂停
//		return null;
////		try{
////			securityVerfiy(request, SECRET_KEY);
//			
//			
////			MerchantInfo mt = MerchantBaseTools.getInstance().getMerchantBasicInfoClient().getMerchantinfo(Long.valueOf(custId));
////			MerchantSearchService mss = MerDataClient.getInstance().getMerchantSearchService();
////			List<Merchant> list = mss.getMerchantInfoByMerchantId(custId);
////			if(null!=list&&list.size()>0){
////				Merchant mer=list.get(0);
////				ProxyResponse response = ProxyResponse.responeSuccess();
////				Map<String, Object> dataMap = new HashMap<String,Object>();
////				dataMap.put("compName", mer.getMerchantName());//商家名称
////				dataMap.put("legalmanLicenceName", mer.getLegalmanLicenceName());//法人名字
////				dataMap.put("registrationNumber",mer.getBusiLicenseCode());//营业执照号
////				dataMap.put("certNo", mer.getLegalmanLicenceCode());//身份证号码
////				dataMap.put("settleOpenBank", mer.getSettlementBankName()+mer.getSettlementBranchBank());//银行名称+支行名称
////				dataMap.put("settleBankUnitedCode", mer.getSettlementBranchCode());//银行联行号 
////				dataMap.put("settleAccBankCardNo", mer.getSettlementAccountNumber());//结算账号
////				dataMap.put("bankSubBranch", Convert.toStr(mer.getSettlementBankProvinceName(), "")
////						+Convert.toStr(mer.getSettlementBankCityName(), "")+Convert.toStr(mer.getSettlementBranchBank(), ""));//支行地址
////				dataMap.put("compType", mer.getGroupId());//公司类型
////				response.setDataMap(dataMap);
////				return this.toJSONString(response);	
////			}else{
////				throw new Exception("queryMerInfoByCustId 商家组接口未查询到相应数据 custId"+custId);
////			}
////		}catch(Exception e){
////			log.error("queryMerInfoByCustId调用异常 ", e);
////			return JSONObject.toJSONString(ProxyResponse.responeError());
////		}
//	}
//	
//	
//	/**
//	 * 调用接口验签
//	 * @param request
//	 * @throws Exception 
//	 */
//	private void securityVerfiy(HttpServletRequest request, String secret) throws Exception{
//		Map<String,String> map = getRequestMap(request);
//		if(!SldSign.verify(map, secret)){
//			throw new Exception("sld security verfiy error!" + map);
//		}
//	}
//	
//   	public static Map<String, String> getRequestMap(HttpServletRequest request){
//	   Map<String, String> resultMap=new HashMap<String, String>();
//	   Enumeration paramNames = request.getParameterNames();  
//	   while (paramNames.hasMoreElements()) {  
//	       String paramName =paramNames.nextElement().toString().trim();  
//	       String paramValue = request.getParameter(paramName);  
//	       resultMap.put(paramName, paramValue);  
//	   }  
//	   return resultMap;
//    }
//   	
//   	private String toJSONString(Object data) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.setDateFormat(DATE_FORMART);
//		Gson gson = builder.create();
//		return gson.toJson(data);
//	}
//}
