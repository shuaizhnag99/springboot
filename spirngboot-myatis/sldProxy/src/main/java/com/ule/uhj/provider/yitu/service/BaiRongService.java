package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.bfd.facade.MerchantServer;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.CodeMsg;
import com.ule.uhj.sldProxy.util.Constants;
import com.ule.uhj.sldProxy.util.CreditException;
import com.ule.uhj.sldProxy.util.ServicesProperty;

public class BaiRongService {
	protected static Log log = LogFactory.getLog(BaiRongService.class);
	
	public static final String TELCHECK="TelCheck";				//手机三要素—移动联通电信
	public static final String TELPERIOD="TelPeriod";				//手机在网时长—移动联通电信
	public static final String BADINFO="BadInfo";					//识别自然人信息
	
	

	
	//发送请求
	public String telCheckAndTelPeriod(Map<String,Object> param) throws CreditException,Exception {
		log.info("BaiRongService [method]:telCheckAndTelPeriod  [param]:" + param.toString());
		// 请求第三方登录获取tokenid
		String login_result = login(new MerchantServer());
		log.info("BaiRongService [method]:telCheckAndTelPeriod  [login_result]:" + login_result);
		if(StringUtils.isBlank(login_result)){
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
		JSONObject loginJson = JSONObject.parseObject(login_result);
		if(!((Constants.CODE_0).equals(loginJson.getString("code")))) {// 登录不成功
			throw new CreditException(CodeMsg.SUCCESS.getCode(), login_result);
		}
		String tokenid = loginJson.getString("tokenid");
		if(StringUtils.isBlank(tokenid)) {
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
		
		String idCard		=	Convert.toStr(param.get("idCard"));
		String phone			=	Convert.toStr(param.get("phone"));
		String name			=	Convert.toStr(param.get("name"));
		
		JSONObject reqData = new JSONObject();
    	reqData.put("id", idCard);
		reqData.put("cell", phone);
		reqData.put("name", name);
		reqData.put("meal", TELCHECK); //TelCheck or TelPeriod
		
		JSONObject jso=new JSONObject();
		jso.put("tokenid", tokenid);
		jso.put("apiName", Constants.FRAUD_API_NAME);//HainaApi
		jso.put("reqData", reqData);
		
		MerchantServer ms = new MerchantServer();
		
		// 请求第三方接口(手机三要素—移动联通电信)
		log.info("BaiRongService [method]:"+TELCHECK+"  [param]:" + jso.toString());
		String telCheck_result = ms.getApiData(jso.toString(), ServicesProperty.BR_API_CODE);//调用报告接口，获取结果。
		// 处理返回结果
		if(StringUtils.isBlank(telCheck_result)) {// 为空  返回无数据
			throw new CreditException(CodeMsg.RETURN_NULL.getCode(), CodeMsg.RETURN_NULL.getMsg());
		}
//		JSONObject telCheckJson = JSONObject.parseObject(telCheck_result);
//		String code = telCheckJson.getString("code");
		log.info("BaiRongService [method]:"+TELCHECK+"  [telCheck_result]:" + telCheck_result);
		
		
		reqData.put("meal", TELPERIOD); //TelCheck or TelPeriod
		jso.put("reqData", reqData);
		// 请求第三方接口(手机在网时长—移动联通电信)
		log.info("BaiRongService [method]:"+TELPERIOD+"  [param]:" + jso.toString());
		String telPeriod_result = ms.getApiData(jso.toString(), ServicesProperty.BR_API_CODE);//调用报告接口，获取结果。
		// 处理返回结果
		if(StringUtils.isBlank(telPeriod_result)) {// 为空  返回无数据
			throw new CreditException(CodeMsg.RETURN_NULL.getCode(), CodeMsg.RETURN_NULL.getMsg());
		}
//		JSONObject telPeriodJson = JSONObject.parseObject(telPeriod_result);
//		String code = telPeriodJson.getString("code");
		log.info("BaiRongService [method]:"+TELPERIOD+"  [telPeriod_result]:" + telPeriod_result);
		
		JSONObject result =new JSONObject();
		result.put(TELCHECK, telCheck_result);
		result.put(TELPERIOD, telPeriod_result);
		System.out.println(result.toJSONString());
		return result.toJSONString();
	}
	
	
	
	
	//发送请求 自然人识别
	public String badInfo(Map<String,Object> param) throws CreditException,Exception {
		log.info("BaiRongService [method]:badInfo  [param]:" + param.toString());
		// 请求第三方登录获取tokenid
		String login_result = login(new MerchantServer());
		log.info("BaiRongService [method]:badInfo  [login_result]:" + login_result);
		if(StringUtils.isBlank(login_result)){
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
		JSONObject loginJson = JSONObject.parseObject(login_result);
		if(!((Constants.CODE_0).equals(loginJson.getString("code")))) {// 登录不成功
			throw new CreditException(CodeMsg.SUCCESS.getCode(), login_result);
		}
		String tokenid = loginJson.getString("tokenid");
		if(StringUtils.isBlank(tokenid)) {
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
		
		String idCard		=	Convert.toStr(param.get("idCard"));
		String phone			=	Convert.toStr(param.get("phone"));
		String name			=	Convert.toStr(param.get("name"));
		
		JSONObject reqData = new JSONObject();
    	reqData.put("id", idCard);
		reqData.put("cell", phone);
		reqData.put("name", name);
		reqData.put("meal", BADINFO); 
		
		JSONObject jso=new JSONObject();
		jso.put("tokenid", tokenid);
		jso.put("apiName", Constants.TRINITYFORCE_API_NAME);//TrinityForceAPI
		jso.put("reqData", reqData);
		
		MerchantServer ms = new MerchantServer();
		
		// 请求第三方接口(手机三要素—移动联通电信)
		log.info("BaiRongService [method]:"+BADINFO+"  [param]:" + jso.toString());
		String badInfo_result = ms.getApiData(jso.toString(), ServicesProperty.BR_API_CODE);//调用报告接口，获取结果。
		// 处理返回结果
		if(StringUtils.isBlank(badInfo_result)) {// 为空  返回无数据
			throw new CreditException(CodeMsg.RETURN_NULL.getCode(), CodeMsg.RETURN_NULL.getMsg());
		}
		log.info("BaiRongService [method]:"+BADINFO+"  [badInfo_result]:" + badInfo_result);
		return badInfo_result;
	}
	
	
	/**
	 * 登录获取tokenid
	 * @param ms
	 * @return
	 * @throws CreditException 
	 * @throws Exception
	 */
	private String login(MerchantServer ms) throws CreditException {
		// 登录获取tokenid；
		try {
			return ms.login(ServicesProperty.BR_USERNAME,ServicesProperty.BR_PASSWORD, ServicesProperty.BR_API_CODE);
		} catch (Exception e) {
			log.error("request login,Exception:",e);
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
	}
	
	
//	public static void main(String[] args) throws Exception {
//		String type=null;	
//		
//		String phone	=	"13399533011";
//		String name		=	"潘星";
//		String idCard	=	"340221199009050031";
//		
//		BaiRongService br = new BaiRongService();
//		Map<String, Object> param =new HashMap<String, Object>();
//		param.put("idCard", idCard);
//		param.put("name", name);
//		param.put("phone", phone);
//		String rs ="";
//		rs =br.telCheckAndTelPeriod(param);
////		rs =br.badInfo(param);
//		System.out.println(rs);
//		
//	}
}
