package com.ule.uhj.ejb.client.zgd;

import com.ule.uhj.dto.zgd.PreServiceRequest;
import com.ule.wildfly.annotation.BeanName;

import javax.ejb.Remote;

/***
 * 前置服务请求统一入口
 * By.ZhengXin
 * 2019-03-26
 */
@Remote
@BeanName("PreServiceRequestBean")
public interface PreServiceRequestClient {

    /***
     * 处理OPC支局长业务的入口
     * @param request
     */
    Object DoOpcGeneralBussiness(PreServiceRequest request);

    /***
     * 处理App支局长业务的入口
     * @param request
     */
    Object DoAppGeneralBussiness(PreServiceRequest request);
}
