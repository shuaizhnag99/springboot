package com.ule.uhj.ysg.service;


import javax.servlet.http.HttpServletRequest;

public interface SyncPayResultService {
	 public String syncPayResult(HttpServletRequest request);
	 
	 public String syncRefundResult(HttpServletRequest request);
}
