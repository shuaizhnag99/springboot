package com.ule.uhj.ejb.client.sendMessage;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
/**
 * 短信统一发送接口
 * @author zhangshuai
 *
 */
@Remote
@BeanName("SendMessageTemplateBean")
public interface SendMessageTemplateClient {
	/**
	 *    文字短信发送接口 需要短信模板
	 * @param phone 手机号码
	 * @param mesageCode 短信code
	 * @param map 短信动态参数
	 * @return
	 */
	public String sendMeaage(String phone,String mesageCode,Map<String,String> map);
	/**
	 * 发送随机短信 不需要短信模板的短信
	 * @param phone
	 * @param Content
	 * @param smsType
	 * @return
	 */
	public String sendRandomMeaage(String phone,String content, String sendChannel);

}
