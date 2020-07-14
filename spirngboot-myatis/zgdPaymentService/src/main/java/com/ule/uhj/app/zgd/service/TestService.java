package com.ule.uhj.app.zgd.service;

import java.util.Map;

public interface TestService {
	public String queryReport(String userOnlyId)throws Exception;
	
	public Map<String,Object> testService(Map<String,Object> param)throws Exception;
}