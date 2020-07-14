package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;




/**
 * @author zhaojie
 *
 */
@Remote
@BeanName("PXZgdBean")
public interface PXZgdClient {
	
	public String checkPXLend(String userOnlyId,String applyAmount);
	
	public String queryPXLendInfo(Map<String, Object> map);
	
	public String queryPXRepayPlan(Map<String, Object> map);
	
	public String confirmPXLoanApply(Map<String, Object> map);
}
