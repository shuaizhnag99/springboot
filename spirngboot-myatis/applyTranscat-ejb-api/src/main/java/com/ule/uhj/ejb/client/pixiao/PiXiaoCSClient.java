package com.ule.uhj.ejb.client.pixiao;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("PiXiaoCSBean")
public interface PiXiaoCSClient {
	public  Map<String, Object> queryWithHoldDetail( Map<String, Object> map);
	public Map<String, Object> queryOrderInfo(String dueId);
	public Map<String, Object> queryPayInfo(Map<String, Object> map);
	public Map<String, Object> queryRefundInfo(String dueId);
	public Map<String, Object> queryWitholdInfo(Map<String, Object> map);
	public String queryWitholdResult(String loanId);
	public Map<String, Object> exportWithholdInfo(Map<String, Object> paras);
}
