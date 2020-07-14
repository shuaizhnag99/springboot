package com.ule.uhj.sldProxy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端手机信息抓取类型
 * @author LIJIAN
 *
 */
public enum GatherTypeEnum {
	SMS(1, "短信"),
	CONTACTS(2, "通讯录"),
	CALL_LOG(3, "通话记录"),
	GEOLOCATION(4, "定位");
	
	private GatherTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	private Integer code;
	private String name;
	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	private static List<Integer> getCods(){
		List<Integer> codes=new ArrayList<Integer>();
		for (GatherTypeEnum gte : GatherTypeEnum.values()) {
			codes.add(gte.getCode());
		}
		return codes;
	}
	
	public static boolean containsKey(Integer code){
		return getCods().contains(code);
	}
}
