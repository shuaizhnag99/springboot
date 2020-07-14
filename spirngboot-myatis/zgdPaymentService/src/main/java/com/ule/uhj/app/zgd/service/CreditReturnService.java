package com.ule.uhj.app.zgd.service;

import java.util.Map;

public interface CreditReturnService {

	public Map<String,Object> queryReviewReturn(Map<String, Object> map)  throws Exception ;
	
	public String saveReviewReturn(Map<String, Object> map)  throws Exception ;
	
}
