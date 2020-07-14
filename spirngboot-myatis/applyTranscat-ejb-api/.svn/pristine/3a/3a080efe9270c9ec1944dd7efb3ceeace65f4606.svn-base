package com.ule.uhj.ejb.client.ycZgd;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("YCZgdRepayDetailBean")
public interface YCZgdRepayDetailClient {

	/**
	 * 根据邮储返回的还款明细入库，并同步还款计划
	 * @param 
	 *
	 */
    public void   synchroRepaymentDetail() throws Exception;
	
    public void   synchroBadOrder() throws Exception;
    public void   synchrepayInfo() throws Exception;
    
    public void shareProfitDetail() throws Exception;
    
    /**
            *  多种业务状态的佣金总金额：已记收、资金在途（特殊）、已发放。
     * @param staffId
     * @return
     */
    public Map<String ,Object> queryCommissionByStaffId(String staffId) throws Exception;
    
    /**
            * 相应状态，展示掌柜列表：产生佣金的掌柜对应状态的佣金总金额。
     * @param staffId
     * @param status
     * @return
     */
    public List<Map<String ,Object>> queryCommByIdAndStatus(String staffId,String status) throws Exception;
    /**
             * 掌柜对应状态的佣金明细：某一状态、某个掌柜的佣金明细，展示，佣金金额、描述字段
     * @param staffId
     * @param status
     * @param userId
     * @return
     */
    public List<Map<String ,Object>> queryCommByIdAndStatAndUserId(String staffId,String status,String userId) throws Exception;

    
}
