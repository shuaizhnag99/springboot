package com.ule.uhj.ejb.client.pixiao;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("PiXiaoBusinessBean")
public interface PiXiaoBusinessClient {

	public Map<String,Object> checkBill(Map<String,Object> map);
	public String queryPxOrder(String userOnlyId);
	public String queryPxUnOutBill(String userOnlyId);
	public Map<String, Object> queryPaymentOrder(Map<String, Object> map);
	public Map<String, Object> querySignInfo(Map<String, Object> map);
	public String preReapy(String userOnlyId,String loanType,String orderId);
	public String sendPreReapy(Map<String, Object> map);
	public String unOutBillDetail(String orderId);
	public String signBillDetail(Map<String, Object> map);
	public String querySignEarlyRepay(Map<String, Object> map);
	public String signEarlyRepay(Map<String, Object> map);
	//--------------------------- 分割线 分割线上面甜甜写，下面赵杰写 ----------------------------------------------//
	
	public String synchroFafkaPxOrder(String key,String value);
	
	public String queryPiXiaoPlan(String userOnlyId,String orderId);
	
	public String queryPiXiaoEarlyPlans(String userOnlyId,String orderId);
	
	public Map<String, Object> queryWithholdInfo(Map<String, Object> paras);
	
}
