package com.ule.uhj.ejb.client.pixiao;

import java.math.BigDecimal;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("PiXiaoPayBean")
public interface PiXiaoPayClient {
	public Map<String,Object> queryRequetLine(Map<String,Object> map);
	public String saveRequetLine(Map<String,Object> map);
	public String queryPhone(String UserOnlyId);
	/**
	 * 用户余额账户信息查询：根据用户和支付金额, 
	 * 返回给支付平台用户可用额度以及是否可贷标志(掌柜贷提供接口,支付组调用)
	 * @param  userOnlyId 用户id  payAmount 支付金额
	 * @return  code:0000   成功    availableBalance:可用额度   loanFlag:是否可贷:Y为可贷(List<Map<String, String>>)
	 * 			code:1000  失败     msg:失败信息
	 * @author yubingbing
	 */
	public String queryBalanceInfo(Map<String, Object> map);
	
	/**
	 *查询用户邮赊购是否可用，可用余额 用于支付组接口
	 * @param  userOnlyId 用户id  merchantId 商户Id
	 * @return  code:0000   成功    availableBalance:可用额度   supportStatus:0 不可用，1 邮赊购可用  2 批销可用  3 批销、赊购可用
	 * 			code:1000  失败     msg:失败信息
	 * @author zhangshuai
	 */
	public String queryYsgBalanceInfo(Map<String, Object> map);

	/**
	 * 用户账户信息查询：根据用户和批销订单金额, 
	 * 返回给支付平台用户可用额度以及还款计划(掌柜贷提供接口,支付组调用)
	 * @return  code:0000   成功  msg 接口失败，返回失败原因  limit:可用额度   list:还款计划(List<Map<String, String>>)  availableBalance:账户可用金额，没有可用金额返回0
	 *          loanFlag:是否可贷   reApplyMsg:不可贷原因：不可贷款时用于页面展示   loanAmount:贷款金额   period:期限   interRate:年利率
	 * 			code:1000  失败     msg:失败信息
	 */
	public String queryAccountInformation(Map<String,Object> map);
	/**
	 * 邮赊购使用
	 * 用户账户信息查询：根据用户和邮赊购订单金额, 
	 * 返回给支付平台用户可用额度以及还款计划(掌柜贷提供接口,支付组调用)
	 * @return  code:0000   成功  msg 接口失败，返回失败原因  
	 * 			code:1000  失败     msg:失败信息
	 */
	public String queryYsgAccountInformation(Map<String,Object> map);
	
	public String queryAccountInfo(Map<String,Object> map);
	
	/**
	 * 支付通知接口：支付成功后支付平台通知掌柜贷系统,借款成功,生成还款计划(掌柜贷提供接口,支付组调用) 
	 * @param   userOnlyId 用户id  loanAmount 借款金额   payId 支付流水号
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String payment(final Map<String,Object> map);
	
	/**
	 * 支付接口：根据支付组给定信息产生初始订单(掌柜贷邮赊购提供接口,支付组调用) 
	 * @param   userOnlyId 用户id  loanAmount 借款金额   payId 支付流水号
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String ysgPayment(final Map<String,Object> map);
	/**
	 * 支付通知接口：支付成功后支付平台通知掌柜贷系统,借款成功,生成还款计划(掌柜贷提供接口,支付组调用) 
	 * @param   userOnlyId 用户id  payId 支付流水号    payDate  支付时间
	 * @return  code:0000   成功   
	 * 			code:1000  失败     msg:失败信息
	 */
	public String paymentResultsQuery(Map<String,Object> map);
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
	/**
	 * 查询机具订单已还清、申请、签收状态下的记录数目
	 * @param UserOnlyId
	 * @return
	 */
	public int queryPiXiaoJiJu(String userOnlyId);
	/**
	 * 支付组接口  查询对账信息
	 * @param map
	 * @return
	 */
	public String querySettlementInfo(Map<String,Object> map);
	
	
	public String pxMoneyBackSuccess(Map<String,Object> map);
    /**
     * 循环批销退款记录表， 累计掌柜贷支付后退款金额/累计掌柜贷支付金额>=50% 批销不可用
     */
	public void changeAccountInfoPxFlag();
	/**
	 * 批量发送用户借款信息到搜索组（2020-0101-0417时间段）
	 */
	public void sendUserLoanAmountAndVpsInfo();
}