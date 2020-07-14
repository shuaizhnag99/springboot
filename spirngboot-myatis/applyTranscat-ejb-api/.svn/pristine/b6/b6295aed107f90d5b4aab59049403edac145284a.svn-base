package com.ule.uhj.ejb.client.app;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("SldAppBean")
public interface SldAppClient {

	

	/**
	 * 确认借款
	 * 
	 * @param map
	 * @return
	 */
	public String confirmAppLoan(Map<String, Object> map);

	public String queryAppNeedRepay(Map<String, Object> map);

	public String queryCashEarlyPay(Map<String, Object> map);

	public String confimCashEarlyPay(Map<String, Object> map);

	public String queryCashRepayPlan(Map<String, Object> map);

	public String queryApplyRecordInfo(Map<String, Object> map);

	String queryInterestStrategy(Map<String, Object> map);

	public String queryRepayAccount(Map<String, Object> map);

	public String updateRepayAccount(Map<String, Object> map);

//	public String loanContractBill(Map<String, Object> map);

	

}
