package com.ule.uhj.app.zgd.service;

import java.util.Map;

public interface CustomerStoreService {

	public String saveCustomerStore(Map<String, Object> map)  throws Exception ;
	
	public Map<String, Object> queryCustomerStore(Map<String, Object> map)  throws Exception ;
	
}
