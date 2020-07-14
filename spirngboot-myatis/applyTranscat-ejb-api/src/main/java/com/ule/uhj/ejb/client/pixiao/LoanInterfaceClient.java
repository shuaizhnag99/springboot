package com.ule.uhj.ejb.client.pixiao;

import java.math.BigDecimal;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("LoanInterfaceBean")
public interface LoanInterfaceClient {
	public String queryPhone(String UserOnlyId);
	/**
	 * 用户余额账户信息查询：根据用户和支付金额, 
	 * 返回给支付平台用户可用额度以及是否可贷标志(掌柜贷提供接口,支付组调用)
	 * @param  userOnlyId 用户id  payAmount 支付金额
	 * @return  code:0000   成功    availableBalance:可用额度   loanFlag:是否可贷:Y为可贷(List<Map<String, String>>)
	 * 			code:1000  失败     msg:失败信息
	 * @author yubingbing
	 */
	public String queryBalanceInfo(String userOnlyId);

	/**
	 * 用户账户信息查询：根据用户和批销订单金额, 
	 * 返回给支付平台用户可用额度以及还款计划(掌柜贷提供接口,支付组调用)
	 * @return  code:0000   成功  msg 接口失败，返回失败原因  limit:可用额度   list:还款计划(List<Map<String, String>>)  availableBalance:账户可用金额，没有可用金额返回0
	 *          loanFlag:是否可贷   reApplyMsg:不可贷原因：不可贷款时用于页面展示   loanAmount:贷款金额   period:期限   interRate:年利率
	 * 			code:1000  失败     msg:失败信息
	 */
	public String queryAccountInformation(String userOnlyId,String loanAmount);
	
	/**
	 * 支付通知接口：支付成功后支付平台通知掌柜贷系统,借款成功,生成还款计划(掌柜贷提供接口,支付组调用) 
	 * @param   userOnlyId 用户id  loanAmount 借款金额   payId 支付流水号
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String paymentNotification(final String userOnlyId,final String loanAmount,final String payId,final String pxNo,final String validatecode);
	/**
	 * 支付通知接口：支付成功后支付平台通知掌柜贷系统,借款成功,生成还款计划(掌柜贷提供接口,支付组调用) 
	 * @param   userOnlyId 用户id  payId 支付流水号    payDate  支付时间
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String paymentResultsQuery(String userOnlyId, String payId,String payDate);
	/**
	 * 退款接口
	 * @param   userOnlyId 用户id  payId 支付流水号    payDate  支付时间
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String moneyBack(Map<String,Object> map);
	/**
	 * 退款查询接口
	 * @param   userOnlyId 用户id  payId 支付流水号    payDate  支付时间
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String moneyBackResultQuery(Map<String,Object> map);
	/**
	 * 退款查询接口
	 * @param   userOnlyId 用户id  payId 支付流水号    payDate  支付时间
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String saveFixDate(String fixDate,String UserOnlyId);
	public String queryContractInfo(BigDecimal applyAmount,String UserOnlyId);
	
}
