package com.ule.uhj.ejb.client.gp;

import com.ule.wildfly.annotation.BeanName;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
@BeanName("GPBean")
public interface GPClient {
    /**
     * 收集往GP推送的进件审核数据
     * @param map
     */
    void collectApplyExamine(Map<String, String> map);

    /**
     * 收集往GP推送的助手成功认证数据
     * @param map
     */
    void collectAuth(Map<String, String> map);

    /**
     * 往GP推送数据（从redis中取数据）
     */
    void sendToGP();

    /**
     * 往GP推送数据（补数据用）
     * @param type
     * @param dataList
     */
    void sendToGP(String type, Object data);

    /**
     * 推送掌柜贷已到期未还清还款计划
     */
    void sendZgdUpaidPlan();
}
