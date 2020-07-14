package com.ule.uhj.app.zgd.service;

import java.util.Map;

import com.ule.uhj.app.zgd.model.UserControl;

public interface UserInfoService {

	public String updateUserPhone(Map<String, Object> map)  throws Exception ;
	
	public Map<String, Object> queryUserInfo(String userOnlyId)  throws Exception ;
	
	public Map<String, Object> queryAccountInfo(String userOnlyId)  throws Exception ;
	
	public Map<String,Object> queryBindCardInfo(Map<String, Object> dataMap)
			throws Exception;
	public UserControl queryUserControl(String userOnlyId)
			throws Exception;
	public boolean booleanUserYzsProvinceCity(String userOnlyId,String StaffId)
			throws Exception;
	
	public Integer queryOverDueDay(Map<String, Object> map)  throws Exception ;
	
}
