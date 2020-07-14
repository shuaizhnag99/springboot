package com.ule.uhj.ejb.client.zgd;


import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("YcNoticeUleBean")
public interface YcNoticeUleClient {

	/***
	 * 2019-08-29
	 * 增加对邮储数据查询接口
	 * By.ZhengXin
	 */
	public String noticeUserQuery(String parameterStr);

	/***
	 * 还款帐号变更
	 * @param userOnlyId
	 * @param newCardNo
	 * @return
	 */
	public Map<String,Object> changeBankCard(String userOnlyId,String newCardNo);
//
	/***
	 * 受托支付结果通知
	 * @param dataStr
	 * @return
	 */
	public String EntrustLoanCallback(String dataStr);
	/***
	 * 申请邮储放款
	 * @orderId 订单Id
	 * @param orderId
	 * @return
	 */
	public Map LoanApply(String orderId);
	/***
	 *服务协议查看接口
	 * @param save 是否保存
	 * @param userOnlyId 用户id
	 * @param bussinessID 业务id
	 * @param bussinessType 业务id类型，line-lineId，loan-loanId
	 * @param orderId 订单id
	 * @param extension 拓展數據
	 *
	 * @return
	 */
	public Map<String,Object> entranceView(boolean save,String userOnlyId,String bussinessID,String bussinessType,String orderId,Map extension);
	/**
	 * 邮储通知邮乐批复的掌柜额度
	 * 额度ID	lineId
	 * 额度当前进度	lineProgress
	 * 额度合同编号	lineContractNo 
	 * 审批额度金额	finalLineAmount
	 * 电子合同URL	netContractURL 
	 */
	public String noticeUleZgLimit(String dataStr);
	
	/**
	 * 贷款申请结果通知
	 * 贷款ID	loanId
	 * 贷款当前进度	loanProgress 
	 * 贷款合同编号	loanContractNo 
	 * 贷款借据编号	duebillNo 
	 * 放款金额	lendAmount 
	 * 放款日期	lendDate 
	 */
	public String noticeUleZgLend(String loanId);
	
	/***
	 * 额度申请定时查询
	 */
	public void limitApplyQuery();
	
	/***
	 * 贷款申请定时查询
	 */
	public void loanApplyQuery();
	
	/***
	 * 还款计划明细查询
	 */
	public void repaymentQuery();
	
	/***
	 * 联调测试
	 * @param i
	 * @return
	 */
	public String noticeTest(String i);
	
	/***
	 * 联调测试
	 * @param i
	 * @return
	 */
	public Map<String,Object> reviewRefuse(String orderId);
	
	/***
	 * 信贷经理变更通知接口
	 * @param dataStr
	 * @return
	 */
	public String loanOfficerChangeNotice(String dataStr);
	
	/**
	 * 商乐贷服务协议
	 * @param b
	 * @param userOnlyId
	 * @param businessId
	 * @param businessType
	 * @param orderId
	 * @param map 
	 * @return
	 */
	public Map<String, Object> sldEntranceView(boolean b, String userOnlyId,
			String businessId, String businessType, String orderId, Map<String, Object> map);
	
}
