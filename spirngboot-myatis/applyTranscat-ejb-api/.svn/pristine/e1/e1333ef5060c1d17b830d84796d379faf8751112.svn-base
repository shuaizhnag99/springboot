package com.ule.uhj.ejb.client.ycZgd;

import com.ule.uhj.dto.zgd.ZgdSmsRecordDto;
import com.ule.wildfly.annotation.BeanName;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;


/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("SendMessageBean")
public interface SendMessageClient {

	/***
	 * 发送短信
	 * @param code
	 * @param phone
	 * @param paramerMap
	 */
	public void SendMessage (String code,String phone,String messageType,Map paramerMap);
	
	/**
	 * 随机码短信类型 
	 */
	public static String SMT_VALIDCODE = "280102";
	
	/**
	 * 不需要模板的短信，随机短信类型
	 */
	public static String SMT_NOT_TEMPLATE="19062801";
	
	/**
	 * 向掌柜手机发送短信
	 * @param phone
	 * @return none
	 * response json
	 * returnCode 0000(成功)
	 * randomCode 校验码
	 */
	public String smsSendRandomCode(String phone);

	/**
	 * 验证手机校验码是否正确
	 * @param phone   手机号
	 * @param validCode 验证码
	 * @return none
	 * response json
	 * returnCode  0000 成功
	 * returnMessage 
	 * verifyResult  true or false
	 *  {"returnCode":"0000","returnMessage":"操作成功","verifyResult":"true"}
	 */
	public String verifyRandomCode(String phone, String validCode) ;
	
	/**
	 * 批量发送短信
	 * @param List<ZgdSmsRecordDto> smss
	 * @return
	 */
	public String batchSend(List<ZgdSmsRecordDto> smss);
	
	/**
	 * 查询短信发送记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> querySendSmsRecords(Map<String, Object> map)	throws Exception;
	
	/**
	 * 保存短信发送记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String saveSendSmsRecords(Map<String, Object> map)	throws Exception;

}
