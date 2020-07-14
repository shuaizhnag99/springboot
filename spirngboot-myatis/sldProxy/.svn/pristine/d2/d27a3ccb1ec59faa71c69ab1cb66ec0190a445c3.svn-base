package com.ule.uhj.sldProxy.util;


/** 
* @Description: 枚举类型 
* @author 💎 IVAN
* @date 2016年5月26日 下午5:26:46 
*/
public enum CodeMsg {

	//系统状态码
	SUCCESS("success", "0000"), 
	ERROR("error", "0001"),
	SYSTEM("系统异常！", "9999"),
	BUSY("请求频率过快，请稍后再试！","9000"),
	APPKEY_ERROR("appkey校验失败","9001"),
	IP_ERROR("IP白名单校验失败","9002"),
	AUTH_ERROR("权限校验失败","9003"),
	PARAM_ERROR("参数不正确", "9004"),
	FILE_TOO_MAX("图片大小不能超过100K","9005"),
	RETURN_NULL("无数据","9006"),
	OUT_OF_TIME("接口请求超时","9007"),
	PARAM_NULL("请求参数为空","8001"),
	REQUIRED_PARAM_NULL("必填参数为空","8002")
	;
	
	// 成员变量
	private String code;
	private String msg;

	// 构造方法
	private CodeMsg(String msg, String code) {
		this.code = code;
		this.msg = msg;
	}

	// 普通方法
	public static String getMsg(String code) {
		for (CodeMsg c : CodeMsg.values()) {
			if (code.equals(c.getCode())) {
				return c.msg;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
