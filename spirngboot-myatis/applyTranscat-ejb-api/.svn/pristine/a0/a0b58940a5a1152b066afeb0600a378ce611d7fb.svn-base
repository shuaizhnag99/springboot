package com.ule.uhj.ejb.client.ycZgd;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.UserInfoDto;
import com.ule.uhj.dto.zgd.UserShopPhotoDto;
import com.ule.wildfly.annotation.BeanName;


/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("YCZgdQueryBean")
public interface YCZgdQueryClient {
	
	/***
	 * 查询是否存在已逾期借据
	 * @param paras userOnlyId : 用户id
	 * @return true已逾期 false未逾期
	 */
	public boolean queryOverDues(Map<String, Object> paras);
	/**
	 * 账户概览页面,
	 * 第一期 简单页面 只显示用户 总额度 使用额度  和 借款订单简单信息
	 * @param dto
	 * @return
	 */
	public String queryAccOverview(UserInfoDto dto);
	
	/**
	 * 查询用户借据信息
	 * @param paras.userOnlyId 用户id paras.currPage 当前页  paras.pageSize 每页条数
	 * @return  String json
	 * total 总条数
	 * list [{dueId 借据id, loanAmount  借钱  借款金额 , restPrincipal  剩余本金, drawTime  时间 放款时间, status 状态}]  //0未还款,  1已还款  ,2已逾期  ,3已取消  ,4待放款
	 */
	public String queryDues(Map<String, Object> paras);
	
	/**
	 * 查询用户的还款计划(暂时不需要分页)
	 * @param dueId
	 * @param userOnlyId
	 * @return
	 * code:0000
	 * msg:success
		list : [
		{index,  分期数
		currInter,  当期利息
		planRepayDate, 计划还款时间
		currPrincipal} 当期本金
		]
	 */
	public String queryPlans(String dueId,String userOnlyId);
	
	/**
	 * 查询用户贷款页相关信息
	 * @param userOnlyId
	 * @return
	 * code 0000(成功)  1000(失败)
	 * LoanOrderDto(成功): LoanOrderDto
	 * availBalance 可用金额
	 * fixedRepayDate 固定还款日
	 * receiveAcc  收款账户(用户已绑卡)
	 * receiveAccs 收款账户列表(用户未绑卡)与receiveAcc 只会有一个返回; receiveAccs : [{cardNo:卡号1},{cardNo:卡号2}]
	 * interRate 利率
	 * userOnlyId 用户id
	 * applyAmount 借款金额 
	 * minApplyAmount 最低借款金额
	 * paidAll 1:不欠款  0:欠款
	 */
	public Map<String, Object> queryLendInfo(String userOnlyId);
	
	/**
	 * 判断掌柜贷用户使用状态
	 * @param userOnlyId
	 * @return json 
	 * code 0000(成功) 1000(失败)
	 * msg  success(成功) 失败原因(失败)
	 * status String
	 * 用户无掌柜贷资质 0
	 * 用户已冻结 10
	 * 用户有掌柜贷资质 11
	 * 用户已绑卡 21 
	 */
	public String queryZgdUserUseStatus(String userOnlyId);
	
	/**
	 * 根据用户还款日和预测放款日 显示可借款期限范围
	 * (还款日可能是用户自己设定1~28 修改后借款期限范围应该随之更新)
	 * @param fixedRepayDate 固定还款日 1~28
	 * @param calendar 申请时间
	 * @return json 
	 * code 0000成功  1000失败
	 * msg 成功success  失败失败原因
	 * list 集合 每个对象包含    index 序列  days 天数  endDate 到期日
	 */
	public String choosePeriodsScope(String fixedRepayDate,String userOnlyId);
	
	/**
	 *根据用户选择的最后还款日,预测放款日和借款金额 生成初始还款计划  粗略估计计划
	 * @param lastRepayDate 最后还款日  yyyy-mm-dd
	 * @param lendAmount 借款金额
	 * @return json 集合 每个对象包含
	 * code 0000成功  1000失败
	 * msg 成功success  失败失败原因
	 * list 集合 每个对象包含    index 序列 , endDate 每期到期日 , inter 利息 ,  princ 本金, startDate 每期开始时间
	 */
	public String initBiappPlans(Map<String, Object> map);
	
	public String queryViewContract(Map<String, Object> map);
	
	/**
	 * 用户还款成功页面信息查询
	 * @param map {userOnlyId, orderId}
	 * @return String json
	 * {code:0000,
	 * msg:success,
	 * loanAmount:, //借款金额
	 * receiveAcc: //收款账户
	 * }
	 */
	public String queryLendSuccess(Map<String, Object> map) ;

	/**
	 * 查询用户当前应还款计划及信息
	 * @param dueId
	 * @param userOnlyId
	 * @return
	 * code:0000
	 * msg:success
		list : [
		{index,  分期数
		currInter,  当期利息
		planRepayDate, 计划还款时间
		currPrincipal, 当期本金
		status } ,状态
		]
		sumRepayAmt  总还款金额
		sumPrincipal 总还款本金
		sumInter  总还款利息
		sumForfeit 总还款罚息
	 */
	public String queryShouldPlans(String dueId,String userOnlyId) ;
	
	public String queryEarlyPlans(String dueId,String userOnlyId);
	/**
	 * 支付组支付之前查询校验接口
	 * @param bigOrderNo  大订单号（对应还款的账单号）
	 * @param userOnlyId 用户Id
	 * @param tranzAmount 还款金额
	 * @return
	 * 成功
		 * code:0000
		 * msg:success
	 * 失败
		 * code:1000
		 * msg:失败信息
	 */
	public String queryZgdBill(String bigOrderNo,String userOnlyId,String tranzAmount);
	/**
	 * 显示用户的还款记录
	 * @return
	 */
	public String queryPaymentHistory(String userOnlyId);
	/**
	 * 查询用户的基本信息
	 * @return
	 */
	public String queryUserInfo(String userOnlyId);
	
	/**
	 * 查询用户的可授信额度
	 * @return
	 */
	public String queryZgdWhiteLimit(String userOnlyId);
	
	/**
	 * 查询VPS系统用户的基本信息
	 * @return
	 */
	public String queryVPSUserInfo(String userOnlyId);
	/**1.	判断掌柜年龄,18-55之间,Y, 进入后续页面,N,将掌柜移出白名单,提示用户年龄超过限制 0001
	 * 2.	判断掌柜是否绑定过银行卡,Y, 进入后续页面（申请页面0002）,N,进入绑卡页面0003
	 * 3.	如已绑定银行卡,系统发送短信验证掌柜个人身份0004
	 * @return
	 */
	public String checkUserInfo(String userOnlyId,String certNo);
	/**
	 * 根据用的借款本金显示用户的还款计划，以及预估费用等
	 * @param userOnlyId 用户Id
	 * @param lendAmount 借款金额
	 * @param lendAmount 固定还款日
	 * @return
		还款计划  list
		费用预估  forecast
		服务费  serviceCharge
		借款到期日 lastRepayDate
	 */
	public String queryRepayPlanAndFree(Map<String, Object> map);
	
	public String selectCardNo(String userOnlyId);
	/**
	 *  调用VPS接口获取用户图片
	 * @param orgCod 机构号
	 * @param userOnlyId  用户ID
	 * @param status 图片标记  0：店铺内；1：店铺外
	 * @return
	 * @throws Exception
	 */
	public List<UserShopPhotoDto> findUserShopPhotoByCode(String orgCod,String userOnlyId,String status) throws Exception;
	
	public String viewConsultService(Map<String, Object> map);
	
	public String viewPettyLoan(Map<String, Object> map);
	
	public String viewPettyLoanBill(Map<String, Object> map);
	public String checkLendInfo(String userOnlyId);
	/**
	 * 查询白名单相关信息
	 * @param userOnlyId
	 * @return
	 */
	public Map<String,Object> findZgdWhiteInfo(String userOnlyId);
	
	public String queryUserProvince(String userOnlyId);
	
	public String queryUserMessage(String userOnlyId);
	
	public List<String> getCardNos(String userOnlyId);
	
	public List<String> getCardNos(String userOnlyId,String userName,String certNo);
	
	public List<Map<String,Object>> getCardNosAndMobileNo(String userOnlyId,String userName,String certNo);
	
	public String queryLoanRecordDetail(Map<String,Object> param);
	
	/**
	 * 查询用户机具借据信息
	 * @param paras.userOnlyId 用户id paras.currPage 当前页  paras.pageSize 每页条数
	 * @return  String json
	 * total 总条数
	 * list [{dueId 借据id, loanAmount  借钱  借款金额 , restPrincipal  剩余本金, drawTime  时间 放款时间,repayAmt 已还本息金额  status 状态}]  //0未还款,  1已还款  ,2已逾期  ,3已取消  ,4待放款
	 */
	public String queryJiJuDues(Map<String, Object> paras);
}
