package com.ule.uhj.app.zgd.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.app.zgd.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.app.zgd.service.JuXinLiService;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.http.HttpClientUtil;

import net.sf.json.JSONArray;

/**
 * 聚信力查询服务密码
 * @author Administrator
 *
 */
@Service
public class JuXinLiServiceImpl implements  JuXinLiService{
    protected static Log log = LogFactory.getLog(JuXinLiServiceImpl.class);
    
    private static String host=PropertiesHelper.getDfs("JU_XIN_LI_IP");
    
    @Autowired
    private InterfaceAccessInfoMapper interfaceAccessInfoMapper;
    
    /**
     * 获取支持的数据源列表
     * @return
     * @throws Exception
     */
    @Override
	public String datasources(Map<String,Object> param)throws Exception {
    	String url=host+PropertiesHelper.getDfs("DATA_SOURCE");
    	Map<String,String> paramMap = new HashMap<String, String>();
    	/*
		 * 发送 HTTP请求 
		 */
    	log.info("datasources url:----"+url);
    	String response = HttpClientUtil.sendGet(url,paramMap,12000);
    	return JSONObject.toJSONString(response);
    }
    
    /**
     * 提交申请表单获取回执信息
     * @return
     * @throws Exception
     */
    @Override
	public String getReceipt(Map<String,Object> dataMap)throws Exception {
		String url = host+PropertiesHelper.getDfs("RECEIPT");
//	    JSONArray selected_website = JSONArray.fromObject( dataMap.get("selected_website"));
	    
		JSONObject basic_info = new JSONObject();
		basic_info.put("name", Convert.toStr(dataMap.get("name")));
		basic_info.put("id_card_num", Convert.toStr(dataMap.get("id_card_num")));
		basic_info.put("cell_phone_num", Convert.toStr(dataMap.get("cell_phone_num")));
		basic_info.put("home_addr", "");
		basic_info.put("work_tel", "");
		basic_info.put("work_addr", "");
		basic_info.put("home_tel", "");
		basic_info.put("cell_phone_num2", "");
		
		JSONArray contacts = new JSONArray();
	    JSONObject paramContacts = new JSONObject();
	    paramContacts.put("contact_tel", Convert.toStr(dataMap.get("contactMobileNo")));
	    paramContacts.put("contact_name", Convert.toStr(dataMap.get("contactName")));
	    paramContacts.put("contact_type", Convert.toStr(dataMap.get("contactType")));
	    contacts.add(paramContacts);
		
		boolean skip_mobile=false;
		JSONObject param = new JSONObject();
//		param.put("selected_website", selected_website);
		param.put("basic_info", basic_info);
		param.put("skip_mobile", skip_mobile);
//		param.put("contacts", contacts);
		log.info("GetReceipt request param:"+param.toString());
		/*
		 * 发送 HTTP请求 
		 */
	    return sendRequest(url,param.toString(),"");
	}
    
    /**
     * 查询数据采集接口
     * @param paras
     * @return
     * @throws Exception
     */
    @Override
	public String queryCollectMsg(Map<String, String> paras)throws Exception{
		String url = host+PropertiesHelper.getDfs("DATA_COLLECT");
	    Map<String, Object> requestData = new HashMap<String, Object>();
	    requestData.put("token", Convert.toStr(paras.get("token")));
	    requestData.put("password", Convert.toStr(paras.get("password")));
	    requestData.put("account", Convert.toStr(paras.get("account")));
	    requestData.put("type", Convert.toStr(paras.get("type")));
	    requestData.put("captcha", Convert.toStr(paras.get("captcha")));
	    requestData.put("website", Convert.toStr(paras.get("webSite")));
	    log.info("userOnlyId:"+Convert.toStr(paras.get("userOnlyId"))+" queryCollectMsg param:"+requestData.toString());
		/*
		 * 发送 HTTP请求 
		 */
	    return sendRequest(url,requestData.toString(),Convert.toStr(paras.get("userOnlyId")));
	}
	
	/**
	 * 提交跳过当前数据源接口
	 * @return
	 * @throws Exception
	 */
    @Override
	public String skipRequest(Map<String,Object> param)throws Exception {
		String url=host+PropertiesHelper.getDfs("SKIP");
		log.info("skipRequest param:"+param.toString());
		/*
		 * 发送 HTTP请求 
		 */
	    return sendRequest(url,param.toString(),"");
	}

	/**
	 * 重置密码
	 * @param paras
	 * @return
	 * @throws Exception
	 */
    @Override
	public String resetPwd(Map<String, Object> paras)throws Exception{
		String url = host+PropertiesHelper.getDfs("RESET_PWD");
	    Map<String, Object> requestData = new HashMap<String, Object>();
	    requestData.put("token", Convert.toStr(paras.get("token")));
	    requestData.put("password", Convert.toStr(paras.get("newPassword")));
	    requestData.put("account", Convert.toStr(paras.get("account")));
	    requestData.put("website", Convert.toStr(paras.get("website")));
	    requestData.put("type", Convert.toStr(paras.get("type")));
	    requestData.put("captcha", Convert.toStr(paras.get("captcha")));
	    log.info("resetPwd param:"+requestData.toString());

	    /*
		 *  发送 HTTP请求 
		 */
	    return sendRequest(url,requestData.toString(),"");
	}
	
	public String sendRequest(String url,String requestData,String userOnlyId)throws Exception {
		String response = HttpClientUtil.sendPostJson(url, requestData, 120000);
		return response;
	}
	
	@Override
	public String accessReportToken(Map<String,Object> param)throws Exception {
		String url = host+PropertiesHelper.getDfs("REPORT_TOKEN");
		Map<String, String> paramMap =new HashMap<String, String>();
		paramMap.put("org_name", Convert.toStr(param.get("org_name")));
		paramMap.put("client_secret", Convert.toStr(param.get("client_secret")));
		paramMap.put("hours", "1");
		log.info("accessReportToken param:"+paramMap.toString());
	    /*
		 *  发送 HTTP请求 
		 */
		String response = HttpClientUtil.sendGet(url,paramMap,12000);
    	return JSONObject.toJSONString(response);
	}
	
	/**
	 * 根据用户基本信息返回用户报告数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public String accessReportData(Map<String,Object> param)throws Exception {
		String url = host+PropertiesHelper.getDfs("REPORT_DATA");
		Map<String, String> paramMap =new HashMap<String, String>();
		paramMap.put("access_token", Convert.toStr(param.get("access_token")));
		paramMap.put("client_secret", Convert.toStr(param.get("client_secret")));
		paramMap.put("name", Convert.toStr(param.get("name")));
		paramMap.put("idcard", Convert.toStr(param.get("idCard")));
		paramMap.put("phone", Convert.toStr(param.get("phone")));
		paramMap.put("userOnlyId", Convert.toStr(param.get("userOnlyId")));
		paramMap.put("tranzCode", Convert.toStr(param.get("tranzCode")));
		log.info("accessReportData param:"+paramMap.toString());
	    /*
		 *  发送 HTTP请求 
		 */
	    return sendRequestToData(url,paramMap);
	}
	
	/**
	 * 根据用户基本信息返回运营商原始数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public String accessRawData(Map<String,Object> param)throws Exception {
		String url = host+PropertiesHelper.getDfs("RAW_DATA");
		Map<String, String> paramMap =new HashMap<String, String>();
		paramMap.put("access_token", Convert.toStr(param.get("access_token")));
		paramMap.put("client_secret", Convert.toStr(param.get("client_secret")));
		paramMap.put("name", Convert.toStr(param.get("name")));
		paramMap.put("idcard", Convert.toStr(param.get("idCard")));
		paramMap.put("phone", Convert.toStr(param.get("phone")));
		paramMap.put("userOnlyId", Convert.toStr(param.get("userOnlyId")));
		paramMap.put("tranzCode", Convert.toStr(param.get("tranzCode")));
		log.info("accessRawData param:"+paramMap.toString());
	    /*
		 *  发送 HTTP请求 
		 */
	    return sendRequestToData(url,paramMap);
	}
	
	/**
	 * 根据用户基本信息返回电商原始数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public String accessBinessRawData(Map<String,Object> param)throws Exception {
		String url = host+PropertiesHelper.getDfs("BUSSINESS_RAW_DATA");
		Map<String, String> paramMap =new HashMap<String, String>();
		paramMap.put("access_token", Convert.toStr(param.get("access_token")));
		paramMap.put("client_secret", Convert.toStr(param.get("client_secret")));
		paramMap.put("name", Convert.toStr(param.get("name")));
		paramMap.put("idcard", Convert.toStr(param.get("idCard")));
		paramMap.put("phone", Convert.toStr(param.get("phone")));
		paramMap.put("userOnlyId", Convert.toStr(param.get("userOnlyId")));
		paramMap.put("tranzCode", Convert.toStr(param.get("tranzCode")));
		log.info("accessBinessRawData param:"+paramMap.toString());
	    /*
		 *  发送 HTTP请求 
		 */
	    return sendRequestToData(url,paramMap);
	}
	
	/**
	 * 根据用户基本信息获取报告状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public String accessJobStatus(Map<String,String> param)throws Exception {
		String url = host+PropertiesHelper.getDfs("ACCESS_JOB_STATUS");
		log.info("accessJobStatus url:"+url);
		log.info("accessJobStatus param:"+param.toString());
	    /*
		 *  发送 HTTP请求 
		 */
		String response = HttpClientUtil.sendGet(url,param,12000);
    	return JSONObject.toJSONString(response);
	}
	
	public String sendRequestToData(String url,Map<String, String> param) throws Exception{
		log.info("sendRequestToData userOnlyId:"+Convert.toStr(param.get("userOnlyId"))+" tranzCode:"+Convert.toStr(param.get("tranzCode")));
		String response = HttpClientUtil.sendGet(url,param,12000);
    	return JSONObject.toJSONString(response);
	}
	
	/**
	 * 对象转换为json字符串
	 * @param javaObj
	 * @return json String 
	 */
	public static String getJsonStringFromObject(Object javaObj) {
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(javaObj);
		return json.toString();
	}
}
