package com.ule.uhj.ejb.client.pixiao;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("PiXiaoYcLoanBean")
public interface PiXiaoYcLoanClient {
	
	/**
	 * 批销邮储放款及生成还款计划和借据
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String   PxLoanApply();
	public String PxLoanApplyUp();
	public void PxLoanApplyQuery();
	public Map<String, Object> queryLoanFail(Map<String, Object> map);
	public String   PxManualOperation(String dueId,String  flag,String lineId,String  userId);
	/**
	 * 批销机具类型订单邮储放款及生成还款计划和借据
	 * @return
	 */
	public String   PxJiJuLoanApply();
}
