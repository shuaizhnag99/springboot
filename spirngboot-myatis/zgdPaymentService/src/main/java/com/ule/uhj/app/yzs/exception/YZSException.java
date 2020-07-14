package com.ule.uhj.app.yzs.exception;

/**
 * 自定义邮助手异常信息
 * @author weisihua
 * @date 2017年11月1日
 */
public class YZSException extends Exception {

	private String msg = "";
	
	public YZSException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
