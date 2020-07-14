package com.ule.uhj.app.zgd.service;

import java.util.Map;

import com.ule.uhj.app.zgd.model.CreditRule;

public interface CreditRuleService {

	public String saveCreditRuleService(Map<String, Object> map)  throws Exception ;
	public String queryCreditRuleService(Map<String, Object> map) throws Exception;
	public String queryCreditRuleToPage(Map<String, Object> map) throws Exception;
	public void saveCreditRuleOfBindCard(Map<String, Object> map) throws Exception;
	public String queryCreditRuleRuleOutput(Map<String, Object> map)throws Exception;
	public CreditRule queryCreditRuleType(Map<String, Object> map)throws Exception;
	public void updateCreditRuleService(String userOnlyId) throws Exception;
}
