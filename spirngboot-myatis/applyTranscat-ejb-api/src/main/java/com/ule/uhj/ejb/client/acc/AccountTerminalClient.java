package com.ule.uhj.ejb.client.acc;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("AccountTerminalBean")
public interface AccountTerminalClient {

	/**
	 * 添加统计操作(保存部分)
	 * 
	 * @throws Exception
	 */
	public void saveAccountTerminalByAccountPeriod();

	/**
	 * 添加统计操作(更新部分)
	 * 
	 * @throws Exception
	 */
	public void updateAccountTerminalByAccountPeriod();
	
	/**
	 * 更新账期
	 * 
	 * @throws Exception
	 */
	public void updatePeriodTime() ;
}
