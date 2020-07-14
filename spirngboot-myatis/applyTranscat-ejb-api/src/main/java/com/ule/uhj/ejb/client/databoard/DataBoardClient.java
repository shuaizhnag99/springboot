package com.ule.uhj.ejb.client.databoard;

import com.ule.wildfly.annotation.BeanName;

import javax.ejb.Remote;
import java.util.Map;

@Remote
@BeanName("DataBoardBean")
public interface DataBoardClient {

    /**
     * OPC渠道治理数据看板
     * @param userOnlyId
     */
    void opcChannelGovern(Map<String, String> map);
}
