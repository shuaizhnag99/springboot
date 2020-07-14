package com.ule.uhj.ejb.client.pixiao;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("RepayInterfaceBean")
public interface RepayInterfaceClient {
	/**
	 * 
	 * @param queryInfos
	 * @return
	 */
	String pxUleLoanRepayTimer() throws Exception;
	public String pxYcLoanRepayTimer();
	public String queryPayStatus();
	public String withholdApplyNotice(String dataStr);
	public String caculateDayInter(String planId);
	public String pxYcWitholdFail();
}
