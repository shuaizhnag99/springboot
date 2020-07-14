package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.sldProxy.util.PropertiesHelper;
import com.ule.uhj.util.AesUtils;

public class CustomerAssetService {
	protected static Log log = LogFactory.getLog(CustomerAssetService.class);
	
	public static final String hunan_url=PropertiesHelper.getDfs("hunan_zichang_url");
	public static final String jiangxi_url=PropertiesHelper.getDfs("jiangxi_zichang_url");
	public static final String app_key=PropertiesHelper.getDfs("hunan_app_key");
	public static final String app_security=PropertiesHelper.getDfs("hunan_app_security");
	
	/**
	 * 湖南商贸客户在邮储银行的资产数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static String queryHuNanAssetData(Map<String,Object> param)throws Exception {
		Map<String,Object>  params = new LinkedHashMap<String, Object>();
		params.put("idcard", param.get("idCard"));
		params.put("name", param.get("name"));
		String bodyStr = JSONObject.toJSONString(params);
		String sign = DigestUtils.md5DigestAsHex((app_security+"app_key=" + app_key + bodyStr + app_security).getBytes()).toUpperCase();
		HttpPost httpPost = new HttpPost(hunan_url+"?app_key="+app_key+"&sign="+sign);
		
		try (DefaultHttpClient client = new DefaultHttpClient()) {
			StringEntity paramerEntity = new StringEntity(AesUtils.encrypt(bodyStr, app_security),"utf-8");
	        paramerEntity.setContentEncoding("utf-8");
	        paramerEntity.setContentType("text/plain");
	        httpPost.setEntity(paramerEntity);
	        HttpEntity responseEntity  = client.execute(httpPost).getEntity();
	        log.info("HttpClientUtil类httpPost方法 : 请求完毕！"+httpPost.getRequestLine());
	        String responseStr = EntityUtils.toString(responseEntity,"utf-8");
	        Map<String,Object> retMap = JSONObject.parseObject(responseStr, Map.class);
	        if("true".equals(Convert.toStr(retMap.get("success")))){
	        	String data = Convert.toStr(retMap.get("data"));
	        	String decData = AesUtils.decrypt(data, app_security);
	        	Map<String,Object> dataMap = JSONObject.parseObject(decData, Map.class);
	        	long timelon =(Long) dataMap.get("SUMM_DATE");
	        	String time = DateUtil.longTimeToDate(timelon);
	        	dataMap.put("SUMM_DATE", time);
	        	retMap.put("data", dataMap);
	        	responseStr = JSONObject.toJSONString(retMap);
	        	log.info("HuNanService queryAssetData="+decData);
	        	return responseStr;
	        }
		}
		return null; 
	}
	
	/**
	 * 江西商贸客户在邮储银行的资产数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static String queryJiangXiJdzAssetData(Map<String,Object> param)throws Exception {
		HttpPost httpPost = new HttpPost(jiangxi_url+"/"+DigestUtils
				.md5DigestAsHex(param.get("idCard").toString().getBytes())+"/"+param.get("name"));
		String responseStr="";
		try (DefaultHttpClient client = new DefaultHttpClient()) {
	        HttpEntity responseEntity  = client.execute(httpPost).getEntity();
	        log.info("HttpClientUtil类httpPost方法 : 请求完毕！"+httpPost.getRequestLine());
	        responseStr= EntityUtils.toString(responseEntity,"utf-8");
	        JSONObject retMap = JSONObject.parseObject(responseStr);
	        if("0".equals(Convert.toStr(retMap.get("code")))){
	        	Map<String,Object> data = (Map)retMap.get("data");
	        	String summDate =Convert.toStr(data.get("SUMM_DATE"));
	        	summDate = summDate.substring(0,4)+"-"+summDate.substring(4, 6)+"-"+summDate.substring(6, 8);
	        	data.put("SUMM_DATE", summDate);
	        	responseStr = JSONObject.toJSONString(retMap);
	        	log.info("HuNanService queryAssetData="+responseStr);
	        }
		}
		return responseStr; 
	}
	
//	public static void main(String[] args) throws Exception {
//		try{
//			Map<String,Object> map = new HashMap<String, Object>();
////			map.put("idCard", "360202195707293010");
////			map.put("name", "刘秋水");
//			map.put("idCard", "433024197412185423");
//			map.put("name", "舒美林");
//			queryHuNanAssetData(map);
//		}catch(Exception ex){
//		}
//	}
	
}
