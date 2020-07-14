package com.ule.uhj.sldProxy.util;

import java.io.Serializable;

public class CreditException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;
	
	public CreditException() {
		super();
	}
	
	public CreditException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
