package com.ule.uhj.ejb.client.sms;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
/**
 * 短信采集相关接口
 * @author zhangshuai
 *
 */
@Remote
@BeanName("CreditCustomerBean")
public interface CreditCustomerClient {
	/**
	 * 查询已开户的掌柜信息 用途 ：划出要采集短信的客户范围
	 * @param parmter 上次采集时间 opentTime
	 * @return MAP userOnlyId、orgCode、userName、openTime
	 */
	List<Map<String,Object>> queryCreditCustomer(Map<String,Object> parmter);

	List<Map<String, Object>> queryCreditCustomerByUserOnlyId(Map<String, Object> parmter);
	/**
	 * 发送短信
	 * @param phone
	 * @param content
	 * @param smt
	 * @return
	 * @throws Exception
	 */
	public String sendMobileMessage(String phone, String content, String smt) throws Exception;

}
