package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.ule.uhj.provider.yitu.util.HttpUtils;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 企业关键字模糊查询-精简版
 * 
 * @author zhaojie
 * 
 */
public class EnterpriseSimpleInfo {
	private static Log log = LogFactory.getLog(EnterpriseSimpleInfo.class);
	
	protected static String ECI_KEY= PropertiesHelper.getDfs("ECI_KEY");
	
	protected static String ECI_URL = PropertiesHelper.getDfs("ECISimple_URL");
	
	public String  getSimpleDetailsByName(Map<String, Object> paras)
			throws Exception {
		String keyWord=(String) paras.get("keyWord");
		log.info("getSimpleDetailsByName begin keyWord:"+keyWord);
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("key", ECI_KEY);
		paramMap.put("keyWord", keyWord);
		paramMap.put("dtype", "json");
    	HttpResponse response = HttpUtils.doGet(ECI_URL,paramMap);
    	HttpEntity entity = response.getEntity();
    	String entityStr = EntityUtils.toString(entity);
    	log.info("getSimpleDetailsByName entityStr"+entityStr);
		return entityStr;
	}
}