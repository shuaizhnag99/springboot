package com.ule.uhj.sldProxy.util;

import java.util.List;
import java.util.Map;

public class ProxyResponse {
	//错误代码(非空)
	private String code;
	//错误描述(非空)
	private String message;
	//数据集合-map类型(可选)
	private Map<String,Object> dataMap;
	//数据集合-list类型(可选)
	private List<Object> dataList;
	
	public ProxyResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public ProxyResponse(String code, String message, List<Object> dataList) {
		super();
		this.code = code;
		this.message = message;
		this.dataList = dataList;
	}

	public ProxyResponse(String code, String message, Map<String, Object> dataMap) {
		super();
		this.code = code;
		this.message = message;
		this.dataMap = dataMap;
	}
	public ProxyResponse(String code, String message,
			Map<String, Object> dataMap, List<Object> dataList) {
		super();
		this.code = code;
		this.message = message;
		this.dataMap = dataMap;
		this.dataList = dataList;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public List<Object> getDataList() {
		return dataList;
	}
	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}
	
	public static ProxyResponse responeSuccess(){
		return new ProxyResponse("0000","成功");
	}
	
	public static ProxyResponse responeError(){
		return new ProxyResponse("9999","失败");
	}
}