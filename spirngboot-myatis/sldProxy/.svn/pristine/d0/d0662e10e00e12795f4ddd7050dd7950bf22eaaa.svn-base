package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.ule.tools.creditService.bean.ECISearchBean;
import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.util.HttpUtils;
import com.ule.uhj.sld.util.MD5;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 企查查相关接口
 * 
 * @author zhaojie
 * 
 */
public class EnterpriseInformation {
	private static Log log = LogFactory.getLog(EnterpriseInformation.class);
	
	protected static String ECI_KEY= PropertiesHelper.getDfs("ECI_KEY");//公司key值
	
	protected static String ECI_SECRETKEY= PropertiesHelper.getDfs("ECI_SECRETKEY");//公司密钥
	
	protected static final String ECI_URL = PropertiesHelper.getDfs("ECI_URL");
	
	protected static final String OPE_URL = PropertiesHelper.getDfs("OPE_URL");
	
	protected static final String REPORT_URL = PropertiesHelper.getDfs("REPORT_URL");
	
	protected static final String ECISimple_URL = PropertiesHelper.getDfs("ECISimple_URL");
	
//	public static void main(String[] args) throws Exception {
//		EnterpriseInformation e=new EnterpriseInformation();
//		Map<String, Object> paras=new HashMap<String, Object>();
//		paras.put("keyWord", "123456");
//		System.out.println(e.getSimpleDetailsByName(paras));
//	}
	
	private  Map<String, String> getToken(){
		String timestamp = String.valueOf((System.currentTimeMillis()/1000));//精确到秒的Unix时间戳【当前最新时间】timespan有效期是五分钟
		System.out.println("getToken:"+ECI_KEY + timestamp + ECI_SECRETKEY);
		String token = MD5.md5Code(ECI_KEY + timestamp + ECI_SECRETKEY).toUpperCase();    //验证加密值
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Timespan", timestamp);
		resultMap.put("Token", token);
		System.out.println("getToken:"+resultMap);
		return resultMap;
	}
	
	 public String  getSimpleDetailsByName(Map<String, Object> paras)
				throws Exception {
		String keyWord=(String) paras.get("keyWord");
		log.info("getSimpleDetailsByName begin keyWord:"+keyWord);
		/*Map<String, String> headers=getToken();
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("key", ECI_KEY);
		paramMap.put("keyWord", keyWord);
		paramMap.put("dtype", "json");
    	HttpResponse response = HttpUtils.doGet(ECISimple_URL, null, null, headers, paramMap);
    	HttpEntity entity = response.getEntity();*/
    	
    	ECISearchBean  eCISearchBean=new ECISearchBean();
		eCISearchBean.setKeyword(keyWord);
		Object result =CreditServiceTools.eciSearch(eCISearchBean);
		log.info("getSimpleDetailsByName 基础服务部返回 entityStr"+result.toString());
		JSONObject jsonObject = JSONObject.fromObject(result);
		if((jsonObject.get("code").equals("200") ||jsonObject.get("code").equals("201")) && !jsonObject.get("data").equals("")){
			String entityStr = jsonObject.get("data").toString();
	    	log.info("getSimpleDetailsByName entityStr"+entityStr);
			return entityStr;
		}else{
			throw new Exception("关键字模糊查询失败");
		}
	}
	
	public String  getDetailsByName(Map<String, Object> paras)
			throws Exception {
		String keyWord=(String) paras.get("keyWord");
		log.info("getDetailsByName begin keyWord:"+keyWord);
		/*Map<String, String> headers=getToken();
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("key", ECI_KEY);
		paramMap.put("keyWord", keyWord);
		paramMap.put("dtype", "json");
    	HttpResponse response = HttpUtils.doGet(ECI_URL, null, null, headers, paramMap);
    	HttpEntity entity = response.getEntity();
    	String entityStr = EntityUtils.toString(entity);
    	log.info("getDetailsByName entityStr"+entityStr);
		return entityStr;*/
		
		ECISearchBean  eCISearchBean=new ECISearchBean();
		eCISearchBean.setKeyword(keyWord);
		Object result =CreditServiceTools.eciGetDetailsByName(keyWord);
		log.info("getDetailsByName 基础服务部返回 entityStr"+result.toString());
		JSONObject jsonObject = JSONObject.fromObject(result);
		if((jsonObject.get("code").equals("200") ||jsonObject.get("code").equals("201")) && !jsonObject.get("data").equals("")){
			String entityStr = jsonObject.get("data").toString();
	    	log.info("getDetailsByName entityStr"+entityStr);
			return entityStr;
		}else{
			throw new Exception("关键字精确查询失败");
		}
	}
	
	public String getOpException(Map<String, Object> paras)
			throws Exception{
		log.info("getOpException begin");
		String keyWord=(String) paras.get("keyWord");
		Map<String, String> headers=getToken();
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("key", ECI_KEY);
		paramMap.put("keyWord", keyWord);
		paramMap.put("dtype", "json");
    	HttpResponse response = HttpUtils.doGet(OPE_URL, null, null, headers, paramMap);
    	HttpEntity entity = response.getEntity();
    	String entityStr = EntityUtils.toString(entity);
    	log.info("getOpException entityStr"+entityStr);
		return entityStr;
	}
	
	public String getYearReport(Map<String, Object> paras)
			throws Exception{
		log.info("getYearReport begin");
		String keyWord=(String) paras.get("keyWord");
		Map<String, String> headers=getToken();
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("key", ECI_KEY);
		paramMap.put("keyNo", keyWord);
		paramMap.put("dtype", "json");
    	HttpResponse response = HttpUtils.doGet(REPORT_URL, null, null, headers, paramMap);
    	HttpEntity entity = response.getEntity();
    	String entityStr = EntityUtils.toString(entity);
    	log.info("getYearReport entityStr"+entityStr);
		return entityStr;
	}
}