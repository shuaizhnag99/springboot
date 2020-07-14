package com.ule.uhj.provider.yitu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.provider.yitu.util.qianhai.DataSecurityUtil;
import com.ule.uhj.provider.yitu.util.qianhai.HttpRequestUtil;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.sldProxy.util.Convert;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 企业关键字精确获取详细信息
 * 
 * @author zhaojie
 * 
 */
public class QianHaiEnterpriseInformation {
	private static Log log = LogFactory.getLog(QianHaiEnterpriseInformation.class);
	
	protected static String sfUrl= PropertiesHelper.getDfs("qianhai_sfUrl");
	protected static String sign= PropertiesHelper.getDfs("qianhai_sign");
	protected static String userPassword= PropertiesHelper.getDfs("qianhai_userPassword");
	protected static String userName=PropertiesHelper.getDfs("qianhai_userName");
	protected static String orgCode=PropertiesHelper.getDfs("qianhai_orgCode");
	protected static String chnlId=PropertiesHelper.getDfs("qianhai_chnlId");
	protected static String authCode=PropertiesHelper.getDfs("qianhai_authCode");
	protected static String authDate=PropertiesHelper.getDfs("qianhai_authDate");
	

	  

	    /**
	     * 发送HTTPs请求,注意这里我们信任了任何服务器证书
	     * 
	     * @throws Exception
	     */
		public static String getQianHaiEnterpriseInfor(Map<String, Object> map) throws Exception
	    {
			String idNo=Convert.toStr(map.get("idNo"));
			String name=Convert.toStr(map.get("name"));
	        String header = "\"header\":" + getMHeader();
	        String encBusiData = DataSecurityUtil.encrypt(getBusiData_MSC8184(idNo,name).getBytes(),sign);
	        String busiData = "\"busiData\":\"" + encBusiData + "\"";
	        String sigValue = DataSecurityUtil.signData(encBusiData);
	        String pwd = DataSecurityUtil.digest(userPassword.getBytes());
	        String securityInfo = "\"securityInfo\":" + getSecurityInfo(sigValue, userName,pwd);
	        String message = "{" + header + "," + busiData + "," + securityInfo + "}";
	        log.info("请求：" + message);

	        String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

	        log.info("响应Message：" + res);
	        JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);

	        // 验证签名的合法性！！！！！！！！
	        DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString(
	                "signatureValue"));
	        String result=DataSecurityUtil.decrypt(msgJSON.getString("busiData"), sign);
//	        log.info("响应BusiData明文：" + result);
	        msgJSON.put("busiData", result);
	        log.info("最终返回前台的报文：" + msgJSON.toString());
	        return msgJSON.toString();
	    }
		
		public static String getMHeader()
	    {
	    	JSONObject js=new JSONObject();
	    	js.put("orgCode", orgCode);
	    	js.put("chnlId", chnlId);
	    	js.put("transNo", Convert.toStr(new Date().getTime())+Convert.toStr((int) (Math.random() * 10000000)));
	    	js.put("transDate", DateUtil.currTimeStr());
	    	js.put("authCode", authCode);
	    	js.put("authDate", authDate);
	    	return js.toString();
	    }
	    public static String getSecurityInfo(String signatureValue,String userName, String pwd)
	    {
	    	JSONObject js=new JSONObject();
	    	js.put("signatureValue", signatureValue);
	    	js.put("userName", userName);
	    	js.put("userPassword", pwd);
	    	return js.toString();
	    }
	    
	    public static String getBusiData_MSC8184(String idNo,String name)
	    {
	    	
	    	JSONObject js=new JSONObject();
	    	js.put("batchNo", "abc123");
	    	
	    	JSONArray recordsArray=new JSONArray();
	    	JSONObject records=new JSONObject();
	    	records.put("idNo", idNo);//个人的证件号码或中数ID号
	    	records.put("idType", "0");//证件类型  0:身份证
	    	records.put("name", name);//主体名称
	    	records.put("reasonCode", "01");//查询原因  01--贷款审批02--贷中管理03—贷后管理04--本人查询05--异议查询99--其他
	    	records.put("entityAuthCode", "a000111");
	    	records.put("entityAuthDate", "2017-8-10");
	    	records.put("seqNo", "r123456789");
	    	recordsArray.add(records);
	    	js.put("records", recordsArray);
	    	return js.toString();
	    }
	    
//	    public static void main(String[] args) throws Exception
//	    {
////	    	330902196107281339
//	    	Map<String, Object> map=new HashMap<String, Object>();
//	    	map.put("idNo", "34032119871001825X");
//	    	map.put("name", "甘来1");
//	        // 发送HTTP请求
////	        postHttpRequest();
//	        // 发送HTTPS请求
//	    	getQianHaiEnterpriseInfor(map);
////	    	getBusiData_MSC8184();
//	    }
	    
	    /**
	     * 发送HTTP请求
	     * 
	     * @throws Exception
	     */
		/*public static String postHttpRequest() throws Exception
	    {
//	    	String sfUrl = "https://test-qhzx.pingan.com.cn:5443/do/dmz/query/blacklist/v1/MSC8004";
	        String header = "\"header\":" + getMHeader();
	        String encBusiData = DataSecurityUtil.encrypt(getBusiData_MSC8004().getBytes(),
	        		sign);
	        String busiData = "\"busiData\":\"" + encBusiData + "\"";
	        String sigValue = DataSecurityUtil.signData(encBusiData);
	        String pwd = DataSecurityUtil.digest(userPassword.getBytes());
	        String securityInfo = "\"securityInfo\":" + getSecurityInfo(sigValue,userName, pwd);
	        String message = "{" + header + "," + busiData + "," + securityInfo + "}";
	        log.info("请求：" + message);

	        String res = HttpRequestUtil.sendJsonWithHttp(sfUrl, message);

	        log.info("响应Message：" + res);
	        JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
	        log.info("响应BusiData密文：" + msgJSON.getString("busiData"));

	        // 一定要验证签名的合法性！！！！！！！！
	        DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString(
	                "signatureValue"));

	        log.info("响应BusiData明文："
	                + DataSecurityUtil.decrypt(msgJSON.getString("busiData"), sign));
	        return DataSecurityUtil.decrypt(msgJSON.getString("busiData"), sign);
	    }
		 public static String getBusiData_MSC8004()
		    {
		        StringBuffer sb = new StringBuffer(
		                "{\"batchNo\":\"abc123\","
		                + "\"records\":[{\"idNo\":\"440102198301114447\","
		                	+ "\"idType\":\"0\","
		                	+ "\"name\":\"米么联调\","
		                	+ "\"reasonCode\":\"01\","
		                	+ "\"entityAuthCode\":\"a000111\","
		                	+ "\"entityAuthDate\":\"2017-8-10\","
		                	+ "\"seqNo\":\"r123456789\"}]}");
		        log.info(sb.toString());
		        return sb.toString();
		    }*/
}