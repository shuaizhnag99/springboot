package com.ule.uhj.app.zgd.service;

import java.util.Map;

import com.ule.uhj.app.zgd.model.CustomerAddress;

public interface CustomerAddressService {

	public String saveCustomerAddress(Map<String, Object> map)  throws Exception ;
	
	public void saveCustomerCertAddress(Map<String, Object> map)  throws Exception ;
	
	public CustomerAddress queryCustomerAddress(Map<String, Object> map)throws Exception;
}
