package com.ule.uhj.ejb.client.ycWelab;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.CustomerVisitDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("ZgdWelabBean")
public interface ZgdWelabClient {
	/**
	 * welab变态通知
	 * @param params
	 * @return
	 */
   public String reciveChangeStatusNotice(String params);

   /**
    * welab终态通知
    * @return
    */
   public String reciveFinalStatusNotice(String params);
   /**
  	 * 初审通过
  	 * @return
  	 */
   public Map<String,Object>firstTrialPass(Map<String, Object> map);
   
   /**
 	 * 审核退回实名认证之后
 	 * @return
 	 */
   public Map<String,Object>backWhiteList(Map<String, Object> map);
   
   
   /**
	 * 审核退回到welab
	 * @return
	 */
  public Map<String,Object>firstTrialBackWelab(Map<String, Object> map);
  
  /**
	 * 审核拒绝
	 * @return
	 */
   public Map<String,Object>auditRefuse(Map<String, Object> map);
  
   
   
   /**
  	 * 复审通过
  	 * @return
  	 */
    public Map<String,Object>reTrialPass(Map<String, Object> map);
    
    
    /**
   	 * 退回到实名认证之前
   	 * @return
   	 */
    public Map<String,Object> backBeforeRealName(Map<String, Object> map);
    
    /**
     * 掌柜贷二期预审通过
     * @param map
     * @return
     */
    public Map<String,Object>  preAuditPass(Map<String, Object> map);
    
    /**
     * 向WELAB 发送数据 定时器使用
     * @return
     */
    public Map<String,Object>  sendDataToWelab();
    
    
    
    public List<CustomerVisitDto>  findCustomerCall(CustomerVisitDto dto);
    
    
    /**
     * 掌柜贷二期重新接收WELAB 审核结果
     * @param map
     * @return
     */
    public Map<String,Object>  retrieve(Map<String, Object> map);
    
    /**
     * 掌柜贷二期重退回到预审前
     * @param map
     * @return
     */
    public Map<String,Object>  returnPreAudit(Map<String, Object> map);
    
    /**
     * 重新激活已经拒绝的掌柜
     * @param map
     * @return
     */
    public String reactivated(Map<String, Object> map);
    /**
     * 查询已经拒绝的掌柜
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String,Object>  queryRefuseList(Map<String, Object> map);

    /**
     * 商乐贷预审批通知接口
     */
	public Map<String, Object> queryYcLimitCreditNotify(
			Map<String, Object> requestMap);
      
}
