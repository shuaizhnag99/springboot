package com.ule.uhj.dto.YC;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;

public class Request implements Serializable {
	/***
	 * 邮乐至邮储
	 */
	public static final String REQUEST_TYPE_ULE2YC = "00";
	
	/***
	 * 邮储至邮乐
	 */
	public static final String REQUEST_TYPE_YC2ULE = "01";
	
	/**
	 * Form Post方式发送
	 */
	public static final String REQUEST_METHOD_POST ="Post";
	
	/**
	 * Json Post方式发送
	 */
	public static final String REQUEST_METHOD_POSTJSON ="PostJson";
	
	/**
	 * File Post方式发送
	 */
	public static final String REQUEST_METHOD_POSTFILE ="PostFile";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1380588914568089769L;
	
	//交易代码
	private String tranCode;
	
	//交易日期
	private String senderDate;
	
	//交易时间
	private String senderTime;
	
	//请求描述
	private String desc;
	
	//交易流水号
	private String flowno;
	
	//指定该Request提交地址
	private String URL;
	
	//指定发送方式
	private String requestMethod;

	//是否期望获得json格式的回复
	private boolean wishJSONReplay = true;
	
	//请求数据
	private Map<String,Object> dataMap;

	private String moudleName;

	private String appName;

	private String ejbUrl;

	private String ejbVersion;

	public Request(String tranCode) {
		this.tranCode = tranCode;
		Date today = new Date();
		this.senderDate = new SimpleDateFormat("yyyyMMdd").format(today);
		this.senderTime = new SimpleDateFormat("HHmmss").format(today);
	}
	
	public Map<String,Object> getRequestMap(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> headMap = new HashMap<String, Object>();
		
		headMap.put("tranCode", this.tranCode);
		headMap.put("senderDate", this.senderDate);
		headMap.put("senderTime", this.senderTime);
		headMap.put("flowno", this.flowno);
		
		if(this.tranCode.startsWith("WEM")){ //我来贷接口参数封装
			resultMap.put("head", headMap);
			resultMap.put("data", this.dataMap);
		}else{
			String jsonHeadMap = JsonUtil.getJsonStringFromMap(headMap);
			resultMap.put("head", jsonHeadMap);
			String jsonDataMap = JsonUtil.getJsonStringFromMap(this.dataMap);
			resultMap.put("data", jsonDataMap);
		}
		
		return resultMap;
		
	}

	public boolean isWishJSONReplay() {
		return wishJSONReplay;
	}

	public void setWishJSONReplay(boolean wishJSONReplay) {
		this.wishJSONReplay = wishJSONReplay;
	}

	public String toString() {
		return getRequestMap().toString();
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getSenderDate() {
		return senderDate;
	}

	public void setSenderDate(String senderDate) {
		this.senderDate = senderDate;
	}

	public String getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}

	public String getFlowno() {
		return flowno;
	}

	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}

	public Map<String, ?> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEjbUrl() {
		return ejbUrl;
	}

	public void setEjbUrl(String ejbUrl) {
		this.ejbUrl = ejbUrl;
	}

	public String getEjbVersion() {
		return ejbVersion;
	}

	public void setEjbVersion(String ejbVersion) {
		this.ejbVersion = ejbVersion;
	}

	public String getMoudleName() {
		return moudleName;
	}

	public void setMoudleName(String moudleName) {
		this.moudleName = moudleName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
