package com.ule.uhj.app.zgd.service;

import java.util.Map;

public interface ApplyImageService {

	public String saveApplyImage(Map<String, Object> map)  throws Exception ;
	
	public String queryApplyImageService(Map<String, Object> map)
			throws Exception;
	
	public String saveAppPdf(Map<String, Object> map)  throws Exception ;
}
