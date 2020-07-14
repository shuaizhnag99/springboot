package com.ule.uhj.yzs.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ule.dfs.client.util.UploadFile;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
import com.ule.uhj.ejb.client.ycZgd.UleHelperFinancerClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdClient;
import com.ule.uhj.ejb.client.ycZgd.ZgdWhiteClient;
import com.ule.uhj.ejb.client.zgd.ZgdClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.CookieUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.RequestJsonUtil;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.YzsResponse;
import com.ule.uhj.yzs.enums.YzsResponeCodeEnum;
import com.ule.web.util.Tools;

@Controller
@RequestMapping("/yzs")
public class ZgdPageController {
	private static Logger log = LoggerFactory.getLogger(ZgdPageController.class);
	//jsonpcallback标识符
	private static final String JSONP_CALL_BACK = "jsoncallback";
	// "身份证号与姓名一致性"-征信查询通过标识符
	private static final String CERT_NO_SAME = "1";
	// 图片类型
	private static final String[] PIC_TYPE = new String[] { "certPos",
			"certNeg", "certHold", "busLience","store","storeInner" };
	// 允许的文件最大bytes
	private static final long FILE_MAX_SIZE = (long) 10485760;

	
	
	@RequestMapping("/createDueAfterLoan")
	@ResponseBody
	public String createDueAfterLoan(HttpServletRequest httpRequest) {
		try {
			WildflyBeanFactory.getZgdClient().createDueAfterLoan();
		} catch (Exception ex) {
			log.error("createDueAfterLoan error",ex);
			return "error";
		}
		return "success";
	}
	
	
	/**
	 * 地推人员掌柜信息查询
	 * @param request
	 * @param sortRule
	 * @param jsoncallback
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/zgdManage", produces = "application/json; charset=utf-8")
	public JSONPObject zgdManage(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(ZgdPageController.JSONP_CALL_BACK);
		try {
			//json回调前缀
			YzsResponse responseDto = YzsResponse.responeSuccess();
			log.info("zgdManage begin*********");
			String mobileCookie = CookieUtil.getCookie(request, "mobileCookie");
			String bangZGId = CookieUtil.cookieDec("mobileCookie", "yzs", mobileCookie);
//			String bangZGId ="20160";
			log.info("parameter bangZGId="+bangZGId);
			if(Check.isBlank(bangZGId)){
				throw new Exception("地推人员ID获取失败");
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("bangZGId", bangZGId);
			
			Map<String,Object> jsonMap = RequestJsonUtil.getRequestMap(request);
			param.put("sortRule", jsonMap.get("sortRule")==null?"0":jsonMap.get("sortRule"));
			BangZGClient client = WildflyBeanFactory.getBangZGClient();
			Map<String,Object> resultMap = client.queryZgManageInfo(param);

			if("0000".equals(resultMap.get("code"))){
				Map<String,Object> result = (Map<String, Object>) resultMap.get("data");
				responseDto.setDataMap(result);
				log.info("zgdManage result:" + result);
				return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
			}else{
				throw new Exception(resultMap.get("msg").toString());
			}
		} catch (Exception e) {
			log.error("zgdManage error",e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	/***
	 * 掌柜实名状态查询
	 * (已实名认证,未实名认证)
	 * @param requestMap
	 * @return
	 */
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping(value="/accountRealNameCheckQuery",produces = "application/json; charset=utf-8")
	public String accountRealNameCheckQuery(HttpServletRequest request,HttpServletResponse response){
		UleHelperFinancerClient uleHelperFinancerClient;
		//json回调前缀
		String callback = request.getParameter(ZgdPageController.JSONP_CALL_BACK);
		try{
			uleHelperFinancerClient = WildflyBeanFactory.getUleHelperFinancerClient();
		}catch(Exception e){
			log.error("ZgdPageController类accountRealNameCheckQuery方法:获取uleHelperFinancerClient失败!",e);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.CLIENT_NOT_FOUND.getCode(),YzsResponeCodeEnum.CLIENT_NOT_FOUND.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountRealNameCheckQuery方法开始:掌柜实名状态查询.");
		
		//前端数据
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		try{
			//接收并解析前端发送的数据
			jsonMap = RequestJsonUtil.getRequestMap(request);
			if(jsonMap == null || jsonMap.size()<=0){
				throw new Exception("json数据未接收!");
			}
		}catch(Exception e){
			log.error("ZgdPageController类accountRealNameCheckQuery方法:json数据获取失败,请重新发起请求!");
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.JSON_DATA_NULL.getCode(),YzsResponeCodeEnum.JSON_DATA_NULL.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountRealNameCheckQuery方法:jsonMap = "+jsonMap.toString());
		String userOnlyId = Convert.toStr(jsonMap.get("userOnlyId"),"");
		log.info("ZgdPageController类accountRealNameCheckQuery方法:userOnlyId = "+userOnlyId);
		//useronlyid不可为空
		if(StringUtils.isBlank(userOnlyId)){
			log.info("ZgdPageController类accountRealNameCheckQuery方法:userOnlyId为null!");
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.USER_ONLY_ID_NULL.getCode(),YzsResponeCodeEnum.USER_ONLY_ID_NULL.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}else{
			//地推人员操作合法性校验
			boolean flag = this.checkUserOnlyIdByBzgId(uleHelperFinancerClient, request, userOnlyId);
			if(!flag){
				log.info("ZgdPageController类accountRealNameCheckQuery方法:userOnlyId不属于该地推人员!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.OPERATION_CHECK_ERROR.getCode(),YzsResponeCodeEnum.OPERATION_CHECK_ERROR.getMessage());
				return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
			}
		}
		//通过ejb服务器获取数据
		Map<String,Object> dataMap;
		String orgCode;
		try{
			dataMap = uleHelperFinancerClient.queryUserRealNameStatus(userOnlyId);
			orgCode = uleHelperFinancerClient.getOrgCodeByUserOnlyId(userOnlyId);
			if(dataMap==null || dataMap.size()<=0 || StringUtils.isBlank(orgCode)){
				log.info("ZgdPageController类toInfoVer方法:数据查询为空!orgCode="+orgCode+",dataMap="+dataMap);
				throw new Exception("数据查询为空!");
			}
		}catch(Exception e){
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.DATA_NOT_FOUND.getCode(),YzsResponeCodeEnum.DATA_NOT_FOUND.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountRealNameCheckQuery方法:callback="+callback);
		//拼装传输层Dto
		YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
				.SUCCESS.getCode(),YzsResponeCodeEnum.SUCCESS.getMessage());
		//根据结果的不同，告知手机客户端所需跳转的信息
		String url = PropertiesHelper.getDfs("UHJ_H5_HTTP");
		if(dataMap.get("status")!=null && (Boolean)dataMap.get("status")){
			dataMap.put("nextStep", "imageUpload");
			dataMap.put("url", url+"/yzs/2016/1206/uploadImg.html");
			dataMap.put("userOnlyId", userOnlyId);
		}else{
			dataMap.put("nextStep", "infoConfirm");
			dataMap.put("url", url+"/yzs/2016/1206/info.html");
			dataMap.put("userOnlyId", userOnlyId);
			dataMap.put("orgCode", orgCode);
		}
		responseDto.setDataMap(dataMap);
		String result = UhjWebJsonUtil.toJSONString(responseDto);
		log.info("ZgdPageController类accountRealNameCheckQuery方法:result="+result);
		return jsonpCallBack(callback,result);
	}
	
	/***
	 * 掌柜信息查询
	 * (信息内容包括:掌柜姓名、身份证、营业执照注册号、店铺名称、店铺所有人姓名)
	 * @param requestMap
	 * @return
	 */
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping(value="/accountInfoQuery",produces = "application/json; charset=utf-8")
	public String accountInfoQuery(HttpServletRequest request,HttpServletResponse response){
		UleHelperFinancerClient uleHelperFinancerClient;
		ZgdWhiteClient zgdWhiteClient;
		//json回调前缀
		String callback = request.getParameter(ZgdPageController.JSONP_CALL_BACK);
		try{
			uleHelperFinancerClient = WildflyBeanFactory.getUleHelperFinancerClient();
			zgdWhiteClient = WildflyBeanFactory.getZgdWhiteClient();
		}catch(Exception e){
			log.error("ZgdPageController类accountInfoQuery方法:获取uleHelperFinancerClient失败!",e);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.CLIENT_NOT_FOUND.getCode(),YzsResponeCodeEnum.CLIENT_NOT_FOUND.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountInfoQuery方法开始:掌柜信息查询.");
		//前端数据
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		try{
			//接收并解析前端发送的数据
			jsonMap = RequestJsonUtil.getRequestMap(request);
			if(jsonMap == null || jsonMap.size()<=0){
				throw new Exception("json数据未接收!");
			}
		}catch(Exception e){
			log.error("ZgdPageController类accountInfoQuery方法:json数据获取失败,请重新发起请求!");
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.JSON_DATA_NULL.getCode(),YzsResponeCodeEnum.JSON_DATA_NULL.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountInfoQuery方法:jsonMap = "+jsonMap);
		String userOnlyId = Convert.toStr(jsonMap.get("userOnlyId"),"");
		log.info("ZgdPageController类accountInfoQuery方法:userOnlyId = "+userOnlyId);
		//useronlyid不可为空
		if(StringUtils.isBlank(userOnlyId)){
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.USER_ONLY_ID_NULL.getCode(),YzsResponeCodeEnum.USER_ONLY_ID_NULL.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}else{
			//地推人员操作合法性校验
			boolean flag = this.checkUserOnlyIdByBzgId(uleHelperFinancerClient, request, userOnlyId);
			if(!flag){
				log.info("ZgdPageController类accountInfoQuery方法:userOnlyId不属于该地推人员!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.OPERATION_CHECK_ERROR.getCode(),YzsResponeCodeEnum.OPERATION_CHECK_ERROR.getMessage());
				return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
			}
		}
		//通过ejb服务器获取数据
		Map<String,Object> dataMap;
		try{
			String orgCode = uleHelperFinancerClient.getOrgCodeByUserOnlyId(userOnlyId);
			if(StringUtils.isBlank(orgCode)){
				log.info("ZgdPageController类accountInfoQuery方法:机构号查询为空!userOnlyId="+userOnlyId);
				throw new Exception("机构号查询为空!");
			}
			//调用ejb同步白名单数据
			zgdWhiteClient.synchroVpsToZgdWhiteByOrgCode(orgCode);
			//白名单数据同步之后再进行查询
			dataMap = uleHelperFinancerClient.queryUserInfoByUserOnlyId(userOnlyId);
			if(dataMap==null || dataMap.size()<=0){
				log.info("ZgdPageController类accountInfoQuery方法:数据查询为空!userOnlyId="+userOnlyId);
				throw new Exception("数据查询为空!");
			}
		}catch(Exception e){
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.DATA_NOT_FOUND.getCode(),YzsResponeCodeEnum.DATA_NOT_FOUND.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		//拼装传输层Dto
		YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
				.SUCCESS.getCode(),YzsResponeCodeEnum.SUCCESS.getMessage());
		responseDto.setDataMap(dataMap);
		String result = UhjWebJsonUtil.toJSONString(responseDto);
		log.info("ZgdPageController类accountInfoQuery方法:result="+result);
		return jsonpCallBack(callback,result);
	}
	/***
	 * 掌柜信息审核
	 * (调用亿微征信接口，查询并比对掌柜填写信息是否一致)
	 * @param requestMap
	 * @return
	 */
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping(value = "/accountInfoCheck",produces = "application/json; charset=utf-8")
	public String accountInfoCheck(HttpServletRequest request,HttpServletResponse response){
		log.info("ZgdPageController类accountInfoCheck方法开始:掌柜信息审核.");
		UleHelperFinancerClient uleHelperFinancerClient;
		ZgdWhiteClient zgdWhiteClient;
		//json回调前缀
		String callback = request.getParameter(ZgdPageController.JSONP_CALL_BACK);
		try{
			uleHelperFinancerClient = WildflyBeanFactory.getUleHelperFinancerClient();
			zgdWhiteClient = WildflyBeanFactory.getZgdWhiteClient();
		}catch(Exception e){
			log.error("ZgdPageController类accountInfoCheck方法:获取uleHelperFinancerClient失败!",e);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.CLIENT_NOT_FOUND.getCode(),YzsResponeCodeEnum.CLIENT_NOT_FOUND.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		//前端数据
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		try{
			//接收并解析前端发送的数据
			jsonMap = RequestJsonUtil.getRequestMap(request);
			if(jsonMap == null || jsonMap.size()<=0){
				throw new Exception("json数据未接收!");
			}
		}catch(Exception e){
			log.error("ZgdPageController类accountInfoCheck方法:json数据获取失败,请重新发起请求!");
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.JSON_DATA_NULL.getCode(),YzsResponeCodeEnum.JSON_DATA_NULL.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountInfoCheck方法:jsonMap = "+jsonMap);
		String userOnlyId = Convert.toStr(jsonMap.get("userOnlyId"),"");
		log.info("ZgdPageController类accountInfoCheck方法:userOnlyId = "+userOnlyId);
		//useronlyid不可为空
		if(StringUtils.isBlank(userOnlyId)){
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.USER_ONLY_ID_NULL.getCode(),YzsResponeCodeEnum.USER_ONLY_ID_NULL.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}else{
			//地推人员操作合法性校验
			boolean flag = this.checkUserOnlyIdByBzgId(uleHelperFinancerClient, request, userOnlyId);
			if(!flag){
				log.info("ZgdPageController类accountInfoCheck方法:userOnlyId不属于该地推人员!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.OPERATION_CHECK_ERROR.getCode(),YzsResponeCodeEnum.OPERATION_CHECK_ERROR.getMessage());
				return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
			}
		}
		//通过ejb服务器获取数据
		Map<String,Object> dataMap;
		try{
			String orgCode = uleHelperFinancerClient.getOrgCodeByUserOnlyId(userOnlyId);
			if(StringUtils.isBlank(orgCode)){
				log.info("ZgdPageController类accountInfoQuery方法:机构号查询为空!userOnlyId="+userOnlyId);
				throw new Exception("机构号查询为空!");
			}
			//调用ejb同步白名单数据
			zgdWhiteClient.synchroVpsToZgdWhiteByOrgCode(orgCode);
			//白名单数据同步之后再进行查询
			dataMap = uleHelperFinancerClient.queryUserInfoByUserOnlyId(userOnlyId);
			if(dataMap==null || dataMap.size()<=0){
				log.info("ZgdPageController类accountInfoQuery方法:数据查询为空!userOnlyId="+userOnlyId);
				throw new Exception("数据查询为空!");
			}
		}catch(Exception e){
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.DATA_NOT_FOUND.getCode(),YzsResponeCodeEnum.DATA_NOT_FOUND.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		//掌柜姓名
		String personName = Convert.toStr(dataMap.get("personName"),"");
		//掌柜身份证号
		String certNo = Convert.toStr(dataMap.get("personCode"),"");
		//营业执照注册号(来源为页面)
		String regCode = Convert.toStr(jsonMap.get("regCode"),"");
		//掌柜店铺名
		String storeName = Convert.toStr(dataMap.get("shopName"),"");
		//店铺所有人姓名(来源为页面)
		String storeOwner = Convert.toStr(jsonMap.get("shopOwnName"),"");
		
		//验证掌柜信息是否录全
		if(StringUtils.isBlank(personName) || StringUtils.isBlank(certNo) || StringUtils.isBlank(regCode) || StringUtils.isBlank(storeName)|| StringUtils.isBlank(storeOwner)){
			log.info("ZgdPageController类accountInfoCheck方法:用户信息不全!掌柜姓名:"+personName+",掌柜身份证号:"+certNo+",营业执照号:"+regCode+",店铺名:"+storeName+",店铺所有人:"+storeOwner);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.ACCOUNT_INFO_MISSED.getCode(),YzsResponeCodeEnum.ACCOUNT_INFO_MISSED.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		
		/*************************亿微接口全部关掉，也不做接口替换，需求来源【尹刚】*********************************/
		//身份证号一致性验证参数
//		List<Object> codeParamer = new ArrayList<Object>();
//		codeParamer.add(personName);
//		codeParamer.add(certNo);
//		
//		//个人企业工商注册信息查询请求
//		List<Object> personParamer = new ArrayList<Object>();
//		personParamer.add(certNo);
//		
//		SldService sldService = new DefaultSldService();
//		SldOperateLog sldOperateLog = new SldOperateLog("邮助手", "system", "", "", "邮助手查询征信审核。", "");
//		//身份证号一致性验证请求
//		Request codeRequest = sldService.getRequest(
//				DefaultSldService.REQUEST_TYPE_ZX, 
//				ZXTransCodeEnum.identityCheck.getTransCode(), 
//				codeParamer);
//		codeRequest.setOpeartor(sldOperateLog);
//		//个人企业工商注册信息查询请求
//		Request personRequest = sldService.getRequest(
//				DefaultSldService.REQUEST_TYPE_ZX, 
//				ZXTransCodeEnum.ysPersonalCircles.getTransCode(), 
//				personParamer);
//		personRequest.setOpeartor(sldOperateLog);
//		//身份证号一致性通过标识,默认为否
//		boolean codeFlag = false;
//		//个人企业工商注册信息查询-营业执照号一致性通过标识,默认为否
//		boolean personFlag = false;
//		//身份证号一致性验证的响应
//		Response zxResponse = null;
//		try{
//			zxResponse = sldService.doService(codeRequest);
//			if(zxResponse == null){
//				throw new Exception("调用易微征信查询身份证一致性失败!");
//			}
//		}catch(Exception e){
//			log.error("ZgdPageController类accountInfoCheck方法:调用易微征信查询身份证一致性失败!"+e.getMessage(),e);
//			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//					.CALL_IDC_FAILED.getCode(),YzsResponeCodeEnum.CALL_IDC_FAILED.getMessage());
//			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//		}
//		
//		try{
//			//判断身份证号一致性请求调用是否成功
//			if(zxResponse!=null && zxResponse.getResponseCode()!=null && zxResponse.getResponseCode().equals(YzsResponeCodeEnum.SUCCESS.getCode())){
//				Map<String,Object> zxResponseMap = zxResponse.getResponseMap();
//				log.info("ZgdPageController类accountInfoCheck方法:调用易微征信查询身份证一致性成功!返回数据:"+zxResponseMap);
//				String zxResponseJsonData = Convert.toStr(zxResponseMap.get("data"),"");
//				//数据做非空校验
//				if(StringUtils.isBlank(zxResponseJsonData)){
//					throw new Exception("身份证一致性征信返回数据为null,本次查询无效!");
//				}else{
//					zxResponseMap = UhjWebJsonUtil.getForm(zxResponseJsonData, Map.class);
//					String status = Convert.toStr(zxResponseMap.get("status"),"");
//					log.info("ZgdPageController类accountInfoCheck方法:身份证与姓名-征信比对结果:"+status);
//					codeFlag = status.equals(ZgdPageController.CERT_NO_SAME);
//				}
//			}else{
//				throw new Exception("身份证一致性响应码不正确,code = "+zxResponse.getResponseCode()+",message = "+zxResponse.getMessage());
//			}
//			
//			//未通过身份证与姓名一致性校验
//			if(!codeFlag){
//				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//						.CODE_NAME_CHECK_NOT_PASS.getCode(),YzsResponeCodeEnum.CODE_NAME_CHECK_NOT_PASS.getMessage());
//				return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//			}else{
//				/**********************亿微接口关闭,邮助手调用此处,app会弹框提示错误,此段校验被要求注释掉********************************/
//				/*try{
//					//个人企业工商注册信息查询请求-响应
//					zxResponse = null;
//					zxResponse = sldService.doService(personRequest);
//					if(zxResponse==null){
//						throw new Exception("调用易微征信查询个人企业工商注册信息失败!zxResponse返回null");
//					}
//				}catch(Exception e){
//					log.error("ZgdPageController类accountInfoCheck方法:调用易微征信查询个人企业工商注册信息失败!"+e.getMessage(),e);
//					YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//							.CALL_REG_FAILED.getCode(),YzsResponeCodeEnum.CALL_REG_FAILED.getMessage());
//					return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//				}
//				if(zxResponse!=null && zxResponse.getResponseCode()!=null && zxResponse.getResponseCode().equals(YzsResponeCodeEnum.SUCCESS.getCode())){
//					Map<String,Object> zxResponseMap = zxResponse.getResponseMap();
//					log.info("ZgdPageController类accountInfoCheck方法:调用易微征信查询个人企业工商注册信息成功!返回数据:"+zxResponseMap);
//					String zxResponseJsonData = Convert.toStr(zxResponseMap.get("data"),"");
//					//数据做非空校验
//					if(StringUtils.isBlank(zxResponseJsonData)){
//						throw new Exception("个人企业工商注册信息征信返回数据为null,本次查询无效!");
//					}else{
//						zxResponseMap = UhjWebJsonUtil.getForm(zxResponseJsonData, Map.class);
//						if(zxResponseMap.get("basic")==null){
//							throw new Exception("个人企业工商注册信息征信返回数据basic为null,本次查询无效!");
//						}
//						@SuppressWarnings("unchecked")
//						List<Map<String,Object>> basicInfoList = (List<Map<String,Object>>)zxResponseMap.get("basic");
//						
//						log.info("ZgdPageController类accountInfoCheck方法:调用易微征信查询个人企业工商注册信息条目数"+basicInfoList.size());
//						if(basicInfoList.size()<=0){
//							throw new Exception("个人企业工商注册信息征信返回数据basicInfoList <= 0为null,本次查询无效!");
//						}
//						for(Map<String,Object> basicMap : basicInfoList){
//							String zxRegCode = Convert.toStr(basicMap.get("rEGNO"));
//							if(StringUtils.isBlank(zxRegCode) || !zxRegCode.equals(regCode)){
//								continue;
//							}else{
//								String endDate = Convert.toStr(basicMap.get("oPTO"));
//								String zxRegName = Convert.toStr(basicMap.get("fRNAME"));
//								String currentDate = DateUtil.currDateSimpleStr();
//								//查询到的营业执照号与掌柜填写的相符,但是注册人姓名不相符
//								if(!zxRegName.equals(personName)){
//									YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//											.REG_CODE_NOT_SAME_PERSON.getCode(),YzsResponeCodeEnum.REG_CODE_NOT_SAME_PERSON.getMessage());
//									return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//								}else if(!compareYear(currentDate, endDate)){
//									//判断营业执照有效期是否大于1年
//									YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//											.REG_CODE_END_DATE_LESS.getCode(),YzsResponeCodeEnum.REG_CODE_END_DATE_LESS.getMessage());
//									return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//								}else{
//									personFlag = true;
//									break;
//								}
//							}
//						}
//						//未通过营业执照校验
//						if(!personFlag){
//							YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//									.REG_CODE_NOT_FOUND.getCode(),YzsResponeCodeEnum.REG_CODE_NOT_FOUND.getMessage());
//							return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//						}
//					}
//				}else{
//					throw new Exception("个人企业工商注册信息响应码不正确,code = "+zxResponse.getResponseCode()+",message = "+zxResponse.getMessage());
//				}*/
//			}
//		}catch(Exception e){
//			log.error(
//					"ZgdPageController类accountInfoCheck方法:调用易微征信查询身份证一致性失败!营业执照号:"
//							+ regCode + ",身份证号:" + certNo + ",用户名:"
//							+ personName + ",errorMsg:" + e.getMessage(), e);
//			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
//					.YIWEI_REC_IDC_ERROR.getCode(),YzsResponeCodeEnum.YIWEI_REC_IDC_ERROR.getMessage());
//			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
//		}

		//通过所有校验，保存该用户信息入库
		Map<String,Object> saveApplyDetailInfoParamer = new HashMap<String, Object>();
		saveApplyDetailInfoParamer.put("userOnlyId", userOnlyId);
		saveApplyDetailInfoParamer.put("shopName", storeName);
		saveApplyDetailInfoParamer.put("shopOwnName", storeOwner);
		saveApplyDetailInfoParamer.put("personName", personName);
		saveApplyDetailInfoParamer.put("personCode", certNo);
		saveApplyDetailInfoParamer.put("regCode", regCode);
		try{
			//调用EJB接口保存用户信息，流程最后一步
			log.info("ZgdPageController类accountInfoCheck方法:调用EJB服务接口保存用户信息,paramer="+saveApplyDetailInfoParamer.toString());
			boolean saveResult = uleHelperFinancerClient.saveUserInfo(saveApplyDetailInfoParamer);
			if(!saveResult){
				throw new Exception("ZgdPageController类accountInfoCheck方法:EJB服务器保存用户信息失败!");
			}
		}catch(Exception e){
			log.error("ZgdPageController类accountInfoCheck方法:调用EJB服务保存用户信息失败！",e);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.SAVE_USER_INFO_ERROR.getCode(),YzsResponeCodeEnum.SAVE_USER_INFO_ERROR.getMessage());
			return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
		}
		log.info("ZgdPageController类accountInfoCheck方法:审核掌柜信息通过!掌柜信息已保存!paramer="+saveApplyDetailInfoParamer.toString());
		YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
				.SUCCESS.getCode(),YzsResponeCodeEnum.SUCCESS.getMessage());
		return jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto));
	}
	
	/***
	 * 图片上传
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImg",produces = "application/json; charset=utf-8")
	public void uploadImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("ZgdPageController类uploadImg方法开始:开始上传图片.");
		//jsonp上传图片，返回response需配置头字段
		String origin =request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.addHeader("Access-Control-Allow-Origin", StringUtils.isBlank(origin)? "*" : origin); 
        String requestHeaders = request.getHeader("Access-Control-Request-Headers");  
        response.setHeader("Access-Control-Allow-Headers", StringUtils.isBlank(requestHeaders) ? "*" : requestHeaders); 
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setContentType("application/json;charset=utf-8");
        
		String userOnlyId = Convert.toStr(request.getParameter("userOnlyId"));
		YCZgdClient yczgdClient = null;
		String callback = request.getParameter(ZgdPageController.JSONP_CALL_BACK);
		try{
			yczgdClient = WildflyBeanFactory.getYCZgdClient();
		}catch(Exception e){
			log.error("ZgdPageController类uploadImg方法:获取yczgdClient失败!",e);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.CLIENT_NOT_FOUND.getCode(),YzsResponeCodeEnum.CLIENT_NOT_FOUND.getMessage());
			response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
			return;
		}
		//userOnlyId不可为空
		if(StringUtils.isBlank(userOnlyId)){
			log.info("ZgdPageController类uploadImg方法开始:userOnlyId为空!");
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.USER_ONLY_ID_NULL.getCode(),YzsResponeCodeEnum.USER_ONLY_ID_NULL.getMessage());
			response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
			return;
		}else{
			UleHelperFinancerClient uleHelperFinancerClient = WildflyBeanFactory.getUleHelperFinancerClient();
			//地推人员操作合法性校验
			boolean flag = this.checkUserOnlyIdByBzgId(uleHelperFinancerClient, request, userOnlyId);
			if(!flag){
				log.info("ZgdPageController类uploadImg方法:userOnlyId不属于该地推人员!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.OPERATION_CHECK_ERROR.getCode(),YzsResponeCodeEnum.OPERATION_CHECK_ERROR.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
		}
		
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		//不受理无文件上传的请求
		if(!commonsMultipartResolver.isMultipart(request)){
			log.info("ZgdPageController类uploadImg方法开始:无上传文件!");
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.FILE_MISSED.getCode(),YzsResponeCodeEnum.FILE_MISSED.getMessage());
			response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
			return;
		}else{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			MultipartFile certPos = multipartRequest.getFile("certPos");
			//检查四种证件是否都上传
			if(certPos == null){
				log.info("ZgdPageController类uploadImg方法开始:身份证正面照缺失!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.CERT_POS_MISSED.getCode(),YzsResponeCodeEnum.CERT_POS_MISSED.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
			MultipartFile certNeg = multipartRequest.getFile("certNeg");
			if(certNeg == null){
				log.info("ZgdPageController类uploadImg方法开始:身份证反面照缺失!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.CERT_NEG_MISSED.getCode(),YzsResponeCodeEnum.CERT_NEG_MISSED.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
			MultipartFile certHold = multipartRequest.getFile("certHold");
			if(certHold == null){
				log.info("ZgdPageController类uploadImg方法开始:手持身份证照缺失!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.CERT_HOLD_MISSED.getCode(),YzsResponeCodeEnum.CERT_HOLD_MISSED.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
			MultipartFile busLience = multipartRequest.getFile("busLience");
			if(busLience == null){
				log.info("ZgdPageController类uploadImg方法开始:营业执照缺失!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.BUS_MISSED.getCode(),YzsResponeCodeEnum.BUS_MISSED.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
			MultipartFile store = multipartRequest.getFile("store");
			if(store == null){
				log.info("ZgdPageController类uploadImg方法开始:店铺外照片缺失!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.OUTSHOP_MISSED.getCode(),YzsResponeCodeEnum.OUTSHOP_MISSED.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
			MultipartFile storeInner = multipartRequest.getFile("storeInner");
			if(storeInner == null){
				log.info("ZgdPageController类uploadImg方法开始:店铺内照片缺失!");
				YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
						.INNERSHOP_MISSED.getCode(),YzsResponeCodeEnum.INNERSHOP_MISSED.getMessage());
				response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
				return;
			}
			//将文件放入数组方便迭代
			MultipartFile[] imgFiles = new MultipartFile[]{certPos,certNeg,certHold,busLience,store,storeInner};
			for(int i = 0;i<imgFiles.length;i++){
				MultipartFile imgFile = imgFiles[i];
				String picType = PIC_TYPE[i];
				String fileName = imgFile.getOriginalFilename();
				Long fileSize = imgFile.getSize();
				
				if(StringUtils.isBlank(fileName)){
					log.info("ZgdPageController类uploadImg方法开始:图片名不可以为空!picType="+picType+",userOnlyId"+userOnlyId);
					YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
							.FILE_MISSED.getCode(),YzsResponeCodeEnum.FILE_MISSED.getMessage());
					response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
					return;
				}
				//限制文件尺寸
				if(fileSize>FILE_MAX_SIZE){
					log.info("ZgdPageController类uploadImg方法开始:图片过大!picType="+picType+",userOnlyId"+userOnlyId);
					YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
							.IMG_SIZE_LARGE.getCode(),YzsResponeCodeEnum.IMG_SIZE_LARGE.getMessage());
					response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
					return;
				}
				//图片上传所需参数，均在配置文件内，读取即可
				String uploadURL = PropertiesHelper.getDfs("uploadURL");
				String bussinessUnit = PropertiesHelper
						.getDfs("bussinessUnit");
				String fullName = "/app_uhj/uppic" + userOnlyId + "/"
						+ picType + new Date().getTime()
						+ (int) (Math.random() * 10000) + "."
						+ fileName.substring(fileName.lastIndexOf(".") + 1);
				String process = PropertiesHelper.getDfs("process");
				try{
					//调用图片上传接口
					String uploadRp = UploadFile.upload(uploadURL, bussinessUnit,fullName, imgFile.getInputStream(), new String[]{process});
					Map<String,Object> uploadResultMap = UhjWebJsonUtil.getForm(uploadRp, Map.class);
					if(uploadResultMap!=null && "success".equals(Convert.toStr(uploadResultMap.get("status"),""))){
						String url = Convert.toStr(uploadResultMap.get("url"));
						log.info("ZgdPageController类uploadImg方法开始:图片上传成功!picType="+picType+",url="+url);
						Map<String,Object> param = new HashMap<String, Object>();
						param.put("userOnlyId",
								Convert.toStr(userOnlyId, ""));
						param.put("picType", Convert.toStr(picType, ""));
						param.put("imageUrl", Convert.toStr(url, ""));
						param.put("ip", Tools.getIpAddr(request));
						yczgdClient.saveUploadImage(param);
						log.info("ZgdPageController类uploadImg方法开始:图片入库保存成功!picType="+picType+",url="+url);
					}else{
						log.info("ZgdPageController类uploadImg方法开始:服务器响应不正确，picType="+picType);
						YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
								.IMG_SERVER_ERROR.getCode(),YzsResponeCodeEnum.IMG_SERVER_ERROR.getMessage());
						response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
						return;
					}
				}catch(Exception e){
					log.error("ZgdPageController类uploadImg方法开始:调用图片上传接口异常!picType="+picType+",userOnlyId"+userOnlyId,e);
					YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
							.EXCEPTION.getCode(),YzsResponeCodeEnum.EXCEPTION.getMessage());
					response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
					return;
				}
			}
			log.info("ZgdPageController类uploadImg方法开始:调用图片上传结束!全部图片上传成功,userOnlyId="+userOnlyId);
			YzsResponse responseDto = new YzsResponse(YzsResponeCodeEnum
					.SUCCESS.getCode(),YzsResponeCodeEnum.SUCCESS.getMessage());
			response.getWriter().write(jsonpCallBack(callback,UhjWebJsonUtil.toJSONString(responseDto)));
			return;
		}
	}
	
	/***
	 * jsonp回调包装
	 * @param pre
	 * @param content
	 * @return
	 */
	private String jsonpCallBack(String pre,String content){
		if(StringUtils.isBlank(pre)){
			return content;
		}else{
			return pre+"("+content+")";
		}
	}
	
	/***
	 * 比对营业执照有效期是否大于1年
	 * @param currentDate
	 * @param endDate
	 * @return
	 */
	private boolean compareYear(String currentDate,String endDate){
		log.info("ZgdPageController类compareYear方法:比对开始,currentDate="+currentDate+",endDate="+endDate);
		if(StringUtils.isBlank(currentDate) || StringUtils.isBlank(endDate)){
			return false;
		}
		try{
			currentDate = currentDate.replace("-", "");
			endDate = endDate.replace("-", "");
			int currentYear = Integer.parseInt(currentDate.substring(0, 4));
			int endYear = Integer.parseInt(endDate.substring(0, 4));
			int currentMonth =  Integer.parseInt(currentDate.substring(4, 6));
			int endMonth =  Integer.parseInt(endDate.substring(4, 6));
			//结束年比当前年小或一样,说明不满一年
			if(endYear<=currentYear){
				return false;
			}
			//结束年比当前年大两年以上,说明满一年
			if(endYear-currentYear>=2){
				return true;
			}
			//结束年比当前年大一年但不满两年,若结束月大于等于当前月，说明满一年
			if(endMonth>=currentMonth){
				return true;
			}
		}catch(Exception e){
			log.error("ZgdPageController类compareYear方法:发生未知异常",e);
		}
		//除此以外,统统不满一年
		return false;
	}

	private boolean checkUserOnlyIdByBzgId(UleHelperFinancerClient client, HttpServletRequest request,String userOnlyId){
		boolean retFlag = false;
		log.info("checkUserOnlyIdByBzgId begin*********");
		String mobileCookie = CookieUtil.getCookie(request, "mobileCookie");
		if(StringUtils.isBlank(mobileCookie)){
			mobileCookie = request.getParameter("mobileCookie");
			log.info("checkUserOnlyIdByBzgId param mobileCookie="+mobileCookie);
		}
		String bangZGId = CookieUtil.cookieDec("mobileCookie", "yzs", mobileCookie);
		if(bangZGId!=null&&client!=null){
			retFlag = client.checkUserOnlyIdByBzgId(bangZGId,userOnlyId);
			log.info("checkUserOnlyIdByBzgId result:"+(retFlag?"校验合法":"检验不合法"));
		}else{
			log.error("checkUserOnlyIdByBzgId error, bangZGId:"+bangZGId);
		}
		return retFlag;
	}
	
}