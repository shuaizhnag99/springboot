package com.ule.uhj.ejb.client.app;

import com.ule.uhj.dto.zgd.BussinessCardDto;
import com.ule.uhj.dto.zgd.MarketingDto;
import com.ule.wildfly.annotation.BeanName;

import java.util.Map;

import javax.ejb.Remote;


/**
 * @author zhaojie
 *
 */
@Remote
@BeanName("ZgdAppBean")
public interface ZgdAppClient {

	public MarketingDto QueryMarketingData(MarketingDto dto);
	
	/**
	 * 新增掌柜大转盘抽检次数
	 */
	public String addDzpLuckyDrawActivity(String activityCode, String userOnlyId);

	public MarketingDto JoinMarketingAct(MarketingDto dto);

	public MarketingDto CheckMarketingQualification(MarketingDto dto);

	/***
	 * 查询营业执照信息（待更换）
	 * @param bussinessCardDto
	 * @return
	 */
	public BussinessCardDto QueryBussinessCardInfoByChange(BussinessCardDto bussinessCardDto);

	/***
	 * 保存营业执照信息（待更换）
	 * @param bussinessCardDto
	 * @return
	 */
	public boolean SaveBussinessCardInfoByChange(BussinessCardDto bussinessCardDto);

	/**
	 * 账号概览
	 * @param map
	 * @return
	 */
	public String queryAppAccOverview(Map<String, Object> map);
	
	/**
	 * 还款计划测算
	 * @param map
	 * @return
	 */
	public String queryAppRepayPlan(Map<String, Object> map);
	
	/**
	 * 确认借款
	 * @param map
	 * @return
	 */
	public String confirmAppLoan(Map<String, Object> map);
	
	
	/**
	 * 带确认订单向邮储发起建额
	 * @param map
	 * @return
	 */
	public String loanApply(Map<String, Object> map);
	
	/**
	 * 应还款页面的查询接口
	 * @param map
	 * @return
	 */
	public String queryAppNeedRepay(Map<String, Object> map);
	
	/**
	 * 现金的还款计划查询接口
	 * @param map
	 * @return
	 */
	public String queryCashRepayPlan(Map<String, Object> map);
	
	/**
	 * 现金的提前还款查询接口
	 * @param map
	 * @return
	 */
	public String queryCashEarlyPay(Map<String, Object> map);
	
	/**
	 * 现金的提前还款确认接口
	 * @param map
	 * @return
	 */
	public String confimCashEarlyPay(Map<String, Object> map);
	
	/**
	 * 批销的还款计划查询接口
	 * @param map
	 * @return
	 */
	public String queryPXRepayPlan(Map<String, Object> map);
	
	/**
	 * 批销的还款订单信息
	 * @param map
	 * @return
	 */
	public String queryPXPayOrder(Map<String, Object> map);
	
	public String queryZgUserInfo(Map<String, Object> map);
	
	public String loanContract(Map<String, Object> map);
	
	public String loanContractBill(Map<String, Object> map);
	
//	public String queryPXEarlyPayPlan(Map<String, Object> map);
	
	public String queryInterestStrategy(Map<String, Object> map);

	//查询地推人员信息
	public Map<String,Object> handleChinaPostMember(Map<String,Object> param) throws Exception;
	
	/**
	 * 保存掌柜贷白名单信息
	 * @author weisihua
	 * @param param
	 * @return
	 * @throws Exception
	 */
	void saveZgdWhiteInfo(Map<String,Object> param) throws Exception;
	
	/***
	 * 
	 * @param   interest    利率
	 * 			userOnlyId  用户ID
	 * 			channelCode 渠道
	 * 			isSuper    是否商超
	 * 
	 * 利率15    渠道是C0001和C0002  非商超的用户   发一张券
	 * @return
	 */
	void createSaleTicket(Map<String,Object> param) throws Exception;
	
	void sendAllUserToVps(Map<String, Object> param) throws Exception;
	
	/**
	 * 更加身份证照片扫描 带确认人脸照片
	 * @param param
	 */
	void scanFaceImagebyIdCardPositive(Map<String, Object> param);
	
	public String getChinapostMemberByZgUserOnlyIdJson(String usrOnlyId);
}