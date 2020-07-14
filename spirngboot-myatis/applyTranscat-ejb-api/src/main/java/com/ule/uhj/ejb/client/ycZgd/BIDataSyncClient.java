package com.ule.uhj.ejb.client.ycZgd;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("BIDataSyncBean")
public interface BIDataSyncClient {
	/**
	 * 从同步bi的掌柜月度销售数据
	 * @param saleMonth
	 * @return 
	 * @throws Exception
	 */
	public String syncYzgSaleMonthFromBI(String saleMonth) throws Exception;
	
	
	/**
	 * 更新最新预估额度
	 * @return
	 */
	public String syncYuGuLimit();
	public String syncYzgSaleDailyFromBI() throws Exception;
	public String getDaiGouNumFromBI() throws Exception;
}
