package com.ule.uhj.zgd.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.CookieUtil;
import com.ule.uhj.util.CookieUtils;
import com.ule.uhj.util.DesUtils;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.StringUtil;
import com.ule.uhj.util.VpsHttpClientUtil;

/**
 * 该拦截器只是拦截opc运营中心的调用，校验调用合法性
 * 
 *  /ule/operateDeatil.action
 * @author zhangshuai
 *
 */
public class UleOperateContInter extends HandlerInterceptorAdapter {
	private static Logger log = Logger.getLogger(UleOperateContInter.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle........begin......");
		boolean flag=true;
        boolean checkCookie=checkCookie(request);//原来 的校验
		String serialId =getUserId(request.getParameter("key")); 
		String cookieInfo = CookieUtils.getDecryptCookieValue(request, "opc_mobile_cookie"); 
		flag=validataUser(request, response, request.getParameter("token"), serialId,cookieInfo);//新的校验
		if(flag&&!checkCookie){
			sendInfoToKaFka(serialId, request.getParameter("chinaPostId"), "");
		}
		log.info("preHandle result........end......"+flag+"...checkCookie..."+checkCookie);
		return flag||checkCookie;
	}
	
	private boolean checkCookie(HttpServletRequest request){
		boolean flag=true;
		String appKey=PropertiesHelper.getDfs("appKey");
		String appSecret=PropertiesHelper.getDfs("appSecret");
		String appUrl=PropertiesHelper.getDfs("app_data_verify_url");
		String serialId =CookieUtil.getCookieInfo(request, "serialId");
		String cookieInfo =CookieUtil.getCookieInfo(request, "opc_mobile_cookie");
		log.info(String.format("VerifyCookie serialId %s,cookieInfo %s,appUrl %s", serialId,cookieInfo,appUrl));
		if(!StringUtil.checkParam(appKey,appSecret,serialId,cookieInfo,appUrl)){
			log.info("VerifyCookie paramter is empty");
			return false;
		}
		Map<String, String> parmMap=new HashMap<String, String>();
		parmMap.put("appKey", appKey);
		parmMap.put("appSecret", appSecret);
		parmMap.put("serialId", serialId);
		parmMap.put("cookieInfo", cookieInfo);
		String result="";
		try {
			result=VpsHttpClientUtil.sendPost(appUrl, parmMap);
		} catch (Exception e) {
			log.error("VerifyCookie http error", e);
			result="";
			flag=false;
		}
		log.info(String.format("VerifyCookie HTTP RESULT %s", result));
		JSONObject js=null;
		try {
			if (StringUtils.isNotBlank(result)) {
				js = JSONObject.parseObject(result);
				String returnCode = Convert.toStr(js.getString("returnCode"),"");
				if (!"10009".equals(returnCode)) {
					log.info("verifyCookie IS NOT PASS.."+serialId);
					return false;
				}
			}
			if (null != js) {
				
				JSONObject returnData = js.getJSONObject("returnData");
				if (null != returnData) {
					String orgunitId = returnData.getString("orgunitId");
					String orgunitIdName = returnData.getString("orgunitId_name");
					String userId = returnData.getString("serialId");
					sendInfoToKaFka(userId, orgunitId, orgunitIdName);
				}
			}
		} catch (Exception e) {
			log.error("verifyCookie http result json error", e);
			flag=false;
		}
		return flag;
	}

	/**
	 * 从 redis 总查询信息时发送信息到kafka
	 * @param userOnlyId
	 * @param orgId
	 * @param orgName
	 */
	private  void sendInfoToKaFka(String userOnlyId,String orgId,String orgName){
		log.info(String.format("sendInfoToKaFka begin userOnlyId %s orgId %s orgName %s", userOnlyId,orgId,orgName));
		
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("USER_ONLYID", userOnlyId);
			data.put("OPERATION_TYPE", 1);//1为查看，2更新,
			data.put("OPERATION_CONTENT", 4);//1主页2批销3进销存4掌柜贷5零售
			data.put("USER_ORGUNIT_ID", orgId);
			data.put("USER_ORGUNIT_NAME", orgName);
			data.put("OPERATION_TIME", DateUtil.currDateStr());
			WildflyBeanFactory.getUleOperateClient().sendDataToKafka(data);
		} catch (Exception e) {
			log.error("sendInfoToKaFka....", e);
		}
		log.info("sendInfoToKaFka end....");
	}
	private String getUserId(String key) {
		Map<String,String> paramsMap =new HashMap<String, String>();
		try {
			DesUtils des = new DesUtils("sendEmail");
			String params =des.decrypt(key);
			String[] paramStr = params.split("&");
			for(String str : paramStr){
				String[] s = str.split("=");
				paramsMap.put(s[0],s[1]);
			}
			// 用户id
			return paramsMap.get("serialId");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	private Boolean validataUser(HttpServletRequest request, HttpServletResponse response, String token, String serialId,String cookieInfo) {
		try{
			//cookie验证
			log.info(String.format("validataUser serialId %s,cookieInfo %s,token %s", serialId,cookieInfo,token));
//			// CacheConsts.OPC_COOKIE_INFO_KEY + 
			if(CookieUtils.verifyCookieInfo(serialId, cookieInfo, "jedisclientSell.properties")) {// redis校验cookie
				log.info("validata user cookieInfo true"+serialId);
				return true;
			}
//			// 查询token是否有效
			Map<String,String > params=new HashMap<String, String>();
			params.put("token", token);
			String result = VpsHttpClientUtil.sendGet(PropertiesHelper.getDfs("app_token_verify_url"), params, 1000 * 60);//get("ule.opc.mobileCenterVerifyTonkenUrl=http://mail2.tom.com/webmail/oa/verify.action"+"?token=" + token);
			if(result == null) {
				log.info("validata user token false"+serialId);
				return false;
			}
			JSONObject resultJson = JSONObject.parseObject(result);
			log.info("validata token result"+resultJson);
			String code = (String) resultJson.get("code");
			if("001".equals(code) ){// 返回001验证token成功
				// 生成cookie 并写入浏览器 CacheConsts.OPC_COOKIE_INFO_KEY + 
				String createCookie = CookieUtils.createCookieInfo(serialId, token, 
						60*60*24*30, "lettuceclient.properties");
				CookieUtils.addCookie(response, "opc_mobile_cookie", createCookie);
				CookieUtils.addCookie(response, "serialId", serialId);
				log.info("validata user token true"+serialId);
				return true;
			}
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("validata user  false"+serialId);
		return false;
	}
}
