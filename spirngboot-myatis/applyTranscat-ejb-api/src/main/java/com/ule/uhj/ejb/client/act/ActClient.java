package com.ule.uhj.ejb.client.act;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("ActBean")
public interface ActClient {
    /**
     * 发送常规活动营销短信
     * @param actSmsId t_j_act_sms短信配置表ID
     * @param num 短信数量
     * @return
     */
    int sendActSms(Long actSmsId, Integer num);
}
