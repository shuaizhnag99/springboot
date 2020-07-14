package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;


/**
 * @author zhaojie
 *
 */
@Remote
@BeanName("VoiceMessageBean")
public interface VoiceMessageClient {
	
	/**
	 * 到期日前一天向掌柜手机发送语音短信
	 */
	public String SendVoiceMessage();

	/**
	 * 获取语音短信
	 */
	public String AcquireVoiceMessage() ;
	
	public Map<String, Object> queryVoiceMessage(Map<String, Object> map)
			throws Exception;
	
	public String summaryDueDetail() ;
	public Map<String, Object> queryDueDetailList(Map<String, Object> map)
			throws Exception;
	public String chinapostMemberPromotionBenefit();
	
	public Map<String, Object> queryPromotionRuleList(Map<String, Object> map)
			throws Exception;
	/**
	 * 发送语音短信给配偶
	 * @param phone
	 * @param content
	 * @param smt
	 * @return
	 * @throws Exception
	 */
	public String sendVoiceMessageToSpouse(String userOnlyId) throws Exception;


}
