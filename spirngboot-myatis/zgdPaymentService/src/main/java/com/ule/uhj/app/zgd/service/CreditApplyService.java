package com.ule.uhj.app.zgd.service;

import java.util.List;
import java.util.Map;

import com.ule.uhj.app.zgd.model.CreditApply;

public interface CreditApplyService {

	public String saveCertApplyInformation(Map<String, Object> map)  throws Exception ;
	
	public String saveCertNoPositive(Map<String, Object> map)  throws Exception ;
	
	public String saveCertNoNegative(Map<String, Object> map)  throws Exception ;
	
	public String queryApplyStatus(Map<String, Object> map) throws Exception;
	public String querySuspendStatus(Map<String, Object> map) throws Exception;
	
	public String booleanCustomInfo(Map<String, Object> map) throws Exception;
	
	public boolean queryPostMember(String userOnlyId) throws Exception;
	
	public boolean queryHasBindPostMember(String userOnlyId) throws Exception;
	
	public Map<String, Object> queryBZGCreditPostMember(String userOnlyId) throws Exception;
	
	/**
	 * 根据userOnlyId查询信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public List<CreditApply> queryInfoByUserOnlyId(String userOnlyId) throws Exception;

	Map<String, Object> getCustomerCouponInfo(Map<String, Object> couponParamters) throws Exception;
	
	public String toXuqiStatuPage(Map<String, Object> map) throws Exception;
	
	public String toXuqiPage(Map<String, Object> map) throws Exception;
	
	public boolean queryIsOldUser(String userOnlyId) throws Exception;
}
