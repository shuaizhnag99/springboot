package com.ule.uhj.app.zgd.service;

public interface ManualCreditService {

	/**
	 * 快速建额
	 * 使用httpClient调用mgt的3个接口
	 * @author weisihua
	 * @param userOnlyId
	 * @param uleScore
	 * @return 000000:成功，100000:同步数据失败，200000:预授信失败，300000:额度建立失败
	 * @throws Exception
	 */
	public String manualCredit(String userOnlyId, String uleScore) throws Exception;
}
