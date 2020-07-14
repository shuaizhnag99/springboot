package com.ule.uhj.app.zgd.service;

import java.util.Map;

/**
 * 订单信息service
 * @author weisihua
 * @date 2018年2月1日
 */
public interface OrderInfoService {

	/**
	 * 查询浙江活动信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryActivityOrderInfo(String userOnlyId) throws Exception;
	
	Map<String, Object> checkZJActivity(String userOnlyId) throws Exception;
}
