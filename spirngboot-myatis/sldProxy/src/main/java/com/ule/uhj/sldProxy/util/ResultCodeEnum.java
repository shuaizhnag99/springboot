package com.ule.uhj.sldProxy.util;

/**
 * Web Service接口调用返回值枚举
 * @author LIJIAN
 *
 */
public enum ResultCodeEnum {
	SUCCESS("0000", "操作成功"),
	ERROR("9999", "操作失败");
	
	private ResultCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	private String code;
	private String name;
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
