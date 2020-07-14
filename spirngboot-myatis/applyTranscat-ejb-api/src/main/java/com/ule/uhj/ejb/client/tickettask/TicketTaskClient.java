package com.ule.uhj.ejb.client.tickettask;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("TicketTaskBean")
public interface TicketTaskClient {
    /**
     * 券发放/转让成功通知
     */
    void sendTransTicketTask();

    /**
     * 券即将过期通知
     */
    void expireTicketTask();

    /**
     * @param num
     * @return
     */
    String backup(String type, Integer num);
}
