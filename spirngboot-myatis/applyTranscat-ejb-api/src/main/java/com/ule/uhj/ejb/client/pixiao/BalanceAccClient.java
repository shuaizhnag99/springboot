package com.ule.uhj.ejb.client.pixiao;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("BalanceAccBean")
public interface BalanceAccClient {
	
	/**
	 * 对账接口
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String uploadBalanceAccFile(String runDate);
	
}
