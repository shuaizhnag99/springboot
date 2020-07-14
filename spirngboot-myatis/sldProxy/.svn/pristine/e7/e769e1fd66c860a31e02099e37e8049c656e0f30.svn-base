package com.ule.uhj.provider.qhcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.tools.creditService.bean.ManagerCredit;
import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 前海征信接口
 * 
 * @author 伍海涛（改）
 * @support by Tel:0755-22625539
 * @company 深圳前海征信中心股份有限公司
 * 
 */
public class QianHaiService {
	
	protected static Log log = LogFactory.getLog(QianHaiService.class);
	
	protected static String URL= PropertiesHelper.getDfs("qianhai_url");
	
	protected static String sign= PropertiesHelper.getDfs("qianhai_sign");
	protected static String userPassword= PropertiesHelper.getDfs("qianhai_userPassword");
	protected static String userName=PropertiesHelper.getDfs("qianhai_userName");
	protected static String orgCode=PropertiesHelper.getDfs("qianhai_orgCode");
	protected static String chnlId=PropertiesHelper.getDfs("qianhai_chnlId");
	protected static String authCode=PropertiesHelper.getDfs("qianhai_authCode");
	protected static String authDate=PropertiesHelper.getDfs("qianhai_authDate");
	protected static String credooUrl=PropertiesHelper.getDfs("qianhai_credooUrl");
	protected static String storePassword=PropertiesHelper.getDfs("qianhai_storePassword");
	protected static String storeAlias=PropertiesHelper.getDfs("qianhai_storeAlias");
		
	
	
	protected static String MSC8133 = "/verify/commNameNoMob/v1/MSC8133";	// 手机三要素验证
	protected static String MSC8134 = "/query/commMobileStat/v1/MSC8134";	// 手机在网时长验证
	protected static String MSC8184 = "/query/entMgrInc/v1/MSC8184";		// 好信高管通(基础版)
	
	
	
//	public static void main(String[] args) throws Exception {
//
//		// 发送HTTP请求
//		// postHttpRequest();
//		// 发送HTTPS请求
////		postHttpsRequest(MSC8133);
//		
//		Map<String, Object> param =new HashMap<String, Object>();
//		
//
//		
//		param.put("appId", "A00046");
//		param.put("idCard", "330902196107281339");
//		param.put("name", "甘来");
//		param.put("phone", "18580172139");
//		
//		QianHaiService s = new QianHaiService();
//		String rs =s.verifyPhone(param);
//		
//		System.out.print(rs);
//		
//	}
	
	
	/**
	 * 手机验证三要素、在网时长
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String verifyPhone(Map<String,Object> param)throws Exception {
		Map<String, Object> result =new HashMap<String, Object>();
//		
//		log.info(" QianHaiService [method]verifyPhone [param]:"+param.toString());
//		
//		String appId		=	Convert.toStr(param.get("appId"));
//		String phone		=	Convert.toStr(param.get("phone"));
//		String idCard		=	Convert.toStr(param.get("idCard"));
//		String name			=	Convert.toStr(param.get("name"));
//				
//				
//		String checkResult =postHttpsRequest(appId,phone,idCard,name,MSC8133);
//		String inUseTime =postHttpsRequest(appId,phone,idCard,name,MSC8134);
//		
//		result.put("checkResult", JSONObject.fromObject(checkResult));
//		result.put("inUseTime", JSONObject.fromObject(inUseTime));
//		
//		log.info(" QianHaiService [method]verifyPhone [result]:"+result);
//		
//		return JSONObject.fromObject(result).toString();
		
		//接入基础服务组
		ManagerCredit managerCredit = new ManagerCredit();
		managerCredit.setMobileNo(Convert.toStr(param.get("phone")));
		// managerCredit.setProvince("bj");//北京
		managerCredit.setIdNo(Convert.toStr(param.get("idCard")));
		managerCredit.setIdType("0");
		managerCredit.setBusiDesc("互金-在网时长");
		managerCredit.setName(Convert.toStr(param.get("name")));
		managerCredit.setReasonCode("01");
		managerCredit.setSeqNo("ule" + System.currentTimeMillis());
		List<ManagerCredit> list = new ArrayList<>();
		list.add(managerCredit);
		String ret = CreditServiceTools.mobileCredit(list);
		JSONObject obj = JSONObject.fromObject(ret);
		if(!Check.isBlank(obj.get("data"))) {
			JSONObject data = obj.getJSONObject("data");
			if(!Check.isBlank(data.get("checkResult"))) {
				result.put("checkResult", data.getJSONObject("checkResult").get("busiData"));
			}
			if(!Check.isBlank(data.get("inUseTime"))) {
				result.put("inUseTime", data.getJSONObject("inUseTime").get("busiData"));
			}
		}
		return JSONObject.fromObject(result).toString();
	}
	
	/**
	 * 好信高管通(基础版)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String entMgrInc(Map<String,Object> param)throws Exception {
		Map<String, Object> result =new HashMap<String, Object>();
		
//		log.info(" QianHaiService [method]entMgrInc [param]:"+param.toString());
//		
//		String appId		=	Convert.toStr(param.get("appId"));
//		String idCard		=	Convert.toStr(param.get("idCard"));
//		String name			=	Convert.toStr(param.get("name"));
//				
//				
//		String entMgrInc =postHttpsRequestMSC8184(appId,idCard,name,MSC8184);
//		
//		result.put("entMgrInc", JSONObject.fromObject(entMgrInc));
//		
//		log.info(" QianHaiService [method]entMgrInc [result]:"+result);
//		
//		return JSONObject.fromObject(result).toString();
		
		//接入基础服务组
		ManagerCredit managerCredit = new ManagerCredit();
		managerCredit.setIdNo(Convert.toStr(param.get("idCard")));
		managerCredit.setIdType("0");
		managerCredit.setBusiDesc("互金-对外投资");
		managerCredit.setName(Convert.toStr(param.get("name")));
		managerCredit.setReasonCode("01");
		managerCredit.setSeqNo("ule" + System.currentTimeMillis());
		List<ManagerCredit> list = new ArrayList<>();
		list.add(managerCredit);
		String ret = CreditServiceTools.managerCredit(list);
		JSONObject obj = JSONObject.fromObject(ret);
		
		if(!Check.isBlank(obj.get("data"))) {
			JSONObject data = obj.getJSONObject("data");
			result.put("entMgrInc", data.get("busiData"));
		}
		return JSONObject.fromObject(result).toString();
	}
	
	
	/**
	 * 发送HTTPs请求,注意这里我们信任了任何服务器证书
	 * 
	 * @throws Exception
	 */
	private static String postHttpsRequestMSC8184(String appId,String idCard,String name,String APIType) throws Exception {
		String sfUrl = URL + APIType;
		String header = "\"header\":" + MessageUtil.getMHeader(appId,orgCode,chnlId,authCode,authDate);
		String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiDataMSC8184(appId,idCard,name).getBytes(), sign);
		String busiData = "\"busiData\":\"" + encBusiData + "\"";
		String sigValue = DataSecurityUtil.signData(encBusiData,credooUrl,storePassword,storeAlias);
		String pwd = DataSecurityUtil.digest(userPassword.getBytes());
		String securityInfo = "\"securityInfo\":"+ MessageUtil.getSecurityInfo(sigValue,userName, pwd);
		String message = "{" + header + "," + busiData + "," + securityInfo
				+ "}";
		System.out.println("请求：" + message);
		log.info(" QianHaiService [method]postHttpsRequest [请求]:"+message);

		String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

		System.out.println("响应Message：" + res);
		log.info(" QianHaiService [method]postHttpsRequest [响应Message]:"+res);
		JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
		System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));
		log.info(" QianHaiService [method]postHttpsRequest [响应BusiData密文]:"+msgJSON.getString("busiData"));
		// 一定要验证签名的合法性！！！！！！！！
		DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON
				.getJSONObject("securityInfo").getString("signatureValue"),credooUrl);
		
		
		String result =DataSecurityUtil.decrypt(msgJSON.getString("busiData"),sign);
		
		System.out.println("响应BusiData明文："	+ result);
		log.info(" QianHaiService [method]postHttpsRequest [响应BusiData明文]:"+result);
		
		return result;
	}
	
	
	
	/**
	 * 发送HTTPs请求,注意这里我们信任了任何服务器证书
	 * 
	 * @throws Exception
	 */
	private static String postHttpsRequest(String appId,String phone,String idCard,String name,String APIType) throws Exception {
		String sfUrl = URL + APIType;
		String header = "\"header\":" + MessageUtil.getMHeader(appId,orgCode,chnlId,authCode,authDate);
		String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData(appId,phone,idCard,name).getBytes(), sign);
		String busiData = "\"busiData\":\"" + encBusiData + "\"";
		String sigValue = DataSecurityUtil.signData(encBusiData,credooUrl,storePassword,storeAlias);
		String pwd = DataSecurityUtil.digest(userPassword.getBytes());
		String securityInfo = "\"securityInfo\":"+ MessageUtil.getSecurityInfo(sigValue,userName, pwd);
		String message = "{" + header + "," + busiData + "," + securityInfo
				+ "}";
		System.out.println("请求：" + message);
		log.info(" QianHaiService [method]postHttpsRequest [请求]:"+message);

		String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

		System.out.println("响应Message：" + res);
		log.info(" QianHaiService [method]postHttpsRequest [响应Message]:"+res);
		JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
		System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));
		log.info(" QianHaiService [method]postHttpsRequest [响应BusiData密文]:"+msgJSON.getString("busiData"));
		// 一定要验证签名的合法性！！！！！！！！
		DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON
				.getJSONObject("securityInfo").getString("signatureValue"),credooUrl);
		
		
		String result =DataSecurityUtil.decrypt(msgJSON.getString("busiData"),sign);
		
		System.out.println("响应BusiData明文："	+ result);
		log.info(" QianHaiService [method]postHttpsRequest [响应BusiData明文]:"+result);
		
		return result;
	}
	
	
	
	
//	/**
//	 * 发送HTTP请求
//	 * 
//	 * @throws Exception
//	 */
//	private static void postHttpRequest() throws Exception {
//		String sfUrl = "http://localhost:7012/dcs-dmz/do/dmz/query/rskdoo/v1/MSC8036";
//		// String header = "\"header\":" + MessageUtil.getMHeader_DMZ();
//		// String encBusiData =
//		// DataSecurityUtil.encrypt(MessageUtil.getBusiData_MSC8004().getBytes(),
//		// "SK803@!QLF-D25WEDA5E52DA");
//
//		String header = "\"header\":" + MessageUtil.getMHeader();
//		String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData()
//				.getBytes(), "SK803@!QLF-D25WEDA5E52DA");
//		String busiData = "\"busiData\":\"" + encBusiData + "\"";
//		String sigValue = DataSecurityUtil.signData(encBusiData);
//		String pwd = DataSecurityUtil.digest("weblogic1".getBytes());
//		String securityInfo = "\"securityInfo\":"
//				+ MessageUtil.getSecurityInfo(sigValue, pwd);
//		String message = "{" + header + "," + busiData + "," + securityInfo
//				+ "}";
//		System.out.println("请求：" + message);
//
//		String res = HttpRequestUtil.sendJsonWithHttp(sfUrl, message);
//
//		System.out.println("响应Message：" + res);
//		JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
//		System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));
//
//		// 一定要验证签名的合法性！！！！！！！！
//		DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON
//				.getJSONObject("securityInfo").getString("signatureValue"));
//
//		System.out.println("响应BusiData明文："
//				+ DataSecurityUtil.decrypt(msgJSON.getString("busiData"),
//						"SK803@!QLF-D25WEDA5E52DA"));
//	}
//
//	/**
//	 * 发送HTTPs请求,注意这里我们信任了任何服务器证书
//	 * 
//	 * @throws Exception
//	 */
//	private static String postHttpsRequest(String APIType) throws Exception {
//		String sfUrl = "https://test-qhzx.pingan.com.cn:5443/do/dmz" + APIType;
//		// String header = "\"header\":" + MessageUtil.getMHeader_DMZ();
//		// String encBusiData =
//		// DataSecurityUtil.encrypt(MessageUtil.getBusiData_MSC8004().getBytes(),
//		// "SK803@!QLF-D25WEDA5E52DA");
//		String header = "\"header\":" + MessageUtil.getMHeader();
//		String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData()
//				.getBytes(), "SK803@!QLF-D25WEDA5E52DA");
//		String busiData = "\"busiData\":\"" + encBusiData + "\"";
//		String sigValue = DataSecurityUtil.signData(encBusiData);
//		String pwd = DataSecurityUtil.digest("weblogic1".getBytes());
//		String securityInfo = "\"securityInfo\":"
//				+ MessageUtil.getSecurityInfo(sigValue, pwd);
//		String message = "{" + header + "," + busiData + "," + securityInfo
//				+ "}";
//		System.out.println("请求：" + message);
//
//		String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);
//
//		System.out.println("响应Message：" + res);
//		JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
//		System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));
//
//		// 一定要验证签名的合法性！！！！！！！！
//		DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON
//				.getJSONObject("securityInfo").getString("signatureValue"));
//		
//		
//		String result =DataSecurityUtil.decrypt(msgJSON.getString("busiData"),"SK803@!QLF-D25WEDA5E52DA");
//		
//		System.out.println("响应BusiData明文："	+ result);
//		
//		return result;
//	}
}
