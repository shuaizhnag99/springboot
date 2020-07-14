package com.ule.uhj.provider.yitu.service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.AliHttpUtils;
import com.ule.uhj.sldProxy.util.ServicesProperty;

public class AliQueryService {
	protected static Log log = LogFactory.getLog(AliQueryService.class);

	public String queryAliBankFourInfo(Map<String, Object> param) throws Exception {
		log.info("queryAliBankFourInfo param:" + param);
		String cid = Convert.toStr(param.get("id"));
		String cell = Convert.toStr(param.get("cell"));
		String name = Convert.toStr(param.get("name"));
		String bankId = Convert.toStr(param.get("bankId"));

		String host = ServicesProperty.ALIB_BANK_FOUR_URL;
	    String path = "/bank";
	    String method = "GET";
	    String appcode = ServicesProperty.ALI_APP_CODE;
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("Mobile", cell);
	    querys.put("bankcard", bankId);
	    querys.put("cardNo", cid);
	    querys.put("realName", name);
		HttpResponse aliResponse = AliHttpUtils.doGet(host, path, method, headers, querys);
		String responseStr = EntityUtils.toString(aliResponse.getEntity(),Charset.forName("UTF-8"));
		return responseStr;
	}
	
	public String queryAliIdCardAuth(Map<String, Object> param) throws Exception {
		log.info("queryAliIdCardAuth param:" + param);
		String name = Convert.toStr(param.get("name"));
		String certNo = Convert.toStr(param.get("citizen_id"));

		String host = ServicesProperty.ALI_IDCARD_AUTH;
	    String path = "/idcard";
	    String method = "GET";
	    String appcode = ServicesProperty.ALI_APP_CODE;
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("idCard", certNo);
	    querys.put("name", name);
		HttpResponse aliResponse = AliHttpUtils.doGet(host, path, method, headers, querys);
		String responseStr = EntityUtils.toString(aliResponse.getEntity(),Charset.forName("UTF-8"));
		return responseStr;
	}

}
