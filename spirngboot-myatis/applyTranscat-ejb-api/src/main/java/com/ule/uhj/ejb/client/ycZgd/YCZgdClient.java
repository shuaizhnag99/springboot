package com.ule.uhj.ejb.client.ycZgd;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.ApplyDetailDto;
import com.ule.uhj.dto.zgd.CustomerVisitDto;
import com.ule.uhj.dto.zgd.LoanOrderDto;
import com.ule.uhj.dto.zgd.ZgdRepayDto;
import com.ule.wildfly.annotation.BeanName;



/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("YCZgdBean")
public interface YCZgdClient {

	/***
	 * 行业预测
	 * @param words 经营范围
	 * @return
	 */
	public List<Map<String,BigDecimal>> businessForecast(String userOnlyId,String words);
	/***
	 * 额度管理
	 * @param accNo 账户id
	 * @param amount 变动后金额
	 * @param transType 交易类型
	 * @return
	 */
	public String limitManager(String accNo,BigDecimal amount,String transType);
	
	/**
	 * 轮询程序 
	 * 轮询已放款(订单状态等于3)但未生成借据的订单
	 * @return
	 */
	public String createDueAfterLoan();
	/**
	 * 确认借款申请
	 * @return
	 */
	public String confirmLoanApply(LoanOrderDto loanDto);
	
	/**
	 * 每天更新逾期费用  (没有设置 setPreDateStr(yyyy-MM-dd) 则以系统昨天为准)
	 * @return
	 */
	public String biappUpdateDaily();
	
	/**
	 * 更新指定的借据费用  (没有设置 setPreDateStr(yyyy-MM-dd) 则以系统昨天为准)
	 * @param dueId 借据id
	 * @return
	 */
	public String biappUpdateCost(String dueId);
	
	/**
	 * 指定更新日期
	 * @param preDateStr
	 */
	public void setPreDateStr(String preDateStr);
	
	/**创建用户还款账单
	 * 先判断账单是否存在，如果存在，根据返回的remark判断是否相同类型的还款
	 * 如果是同类型的跳转还款页面，否则提示用户用已经创建账单的类型去还款
	 * @param MAP 创建账单要素
	 * userOnlyId 用户ID
	 * sumAmt 还款总金额
	 * repayPrincipal 还款本金
	 * remark 备注 判断还款类型是否一致
	 * 		  type1--立即还款
	 * 		  type2--提前还款提前还款
	 * planId 还款计划ID
	 * @return
	 */
	public String initZGDBill(Map<String, Object> map);
	
	/**
	 * 将所有的账单置为 过期取消     
	 * 定时任务 
	 * @return
	 */
	public String cancelRepayBillTimer();
	
	/**
	 * 还款处理
	 * @param ZgdRepayDto 支付组传入的参数
	 * @return
	 */
	public String repayment(ZgdRepayDto dto);
	
	/**
	 * 掌柜贷消息定时通知
	 * @return json
	 * code     0000    1000
	 * msg   X条success {error}
	 */
	public String zgdSMSFixTimeNotice();
	
	/**
	 * 同步用户信息（信息来源：VPS）
	 * @param userOnlyId 用户ID
	 * @return
	 */
	public String synUserInfo(String userOnlyId);
	
	
	public String bindCardNo(String userOnlyId,String cardNo,String certNo);
	
	public String saveYzgUser(Map<String,Object> map);
	
	/**
	 * 保存上传图片
	 * @param map
	 * @return
	 */
	public Map<String,Object> saveUploadImage(Map<String,Object> map);
	
	/**
	 * 每天更新逾期费用  (已经逾期十天，所有权归邮乐的)
	 * @return
	 */
	public String UpdateOverRepayPlanTimer();
	/**
	 * 每天定时代扣逾期费用  (已经逾期十天，所有权归邮乐的)
	 * @return
	 */
	public String withHoldOverRepayPlanTimer();
	
	/**
	 * 保证金扣款查询  (已经逾期十天，所有权归邮乐的)
	 * @return
	 */
	public String bondRepaymentQueryByLoanId(String loanId);
	
	/**
	 * 保证金扣款查询  (已经逾期十天，所有权归邮乐的)
	 * @return
	 */
	public String bondRepaymentQueryTimer();
	/**
	 * 用户提前还款
	 * @return
	 */
	public String confirmEarlyPlans(Map<String, Object> map);
	/**
	 * 用户提前还款
	 * @return
	 */
	public String returnApply(String userOnlyId);
	/**
	 * 用户通话记录
	 * @return
	 */
	public String createCustomVisit(CustomerVisitDto dto);
	
	/**
	 * 保存掌柜申请居住、店铺、户籍地址等相关信息
	 * @param dto
	 * @return
	 */
	public String  saveApplyAddress(ApplyDetailDto dto);
	
	/**
	 * 每天定时查询邮储邮E贷订单数据
	 * @return
	 */
	public String queryAndStoreUeloanOrders(String date);
	
	/**
	 * 移动版掌柜贷同步新表数据到老表
	 */
	public Map<String,Object> synNewData(Map<String, Object> map);

	/***
	 * 冻结指定账户
	 * @param userOnlyId
	 * @param dayNum
	 */
	public void freeAccount(String userOnlyId,int dayNum,String operator);
	
	/***
	 * 查询用户授信时区划信息
	 * @param userOnlyId
	 * @param dayNum
	 */
	public  List<Map<String, Object>> queryYcCreditQuhuaInfo(String userOnlyId);
}
