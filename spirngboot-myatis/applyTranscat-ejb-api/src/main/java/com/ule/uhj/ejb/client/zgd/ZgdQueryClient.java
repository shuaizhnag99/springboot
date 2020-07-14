package com.ule.uhj.ejb.client.zgd;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.ApplyAuditDto;
import com.ule.uhj.dto.zgd.ApplyDetailDto;
import com.ule.uhj.dto.zgd.ProductInfoN;
import com.ule.uhj.dto.zgd.UserInfoDto;
import com.ule.wildfly.annotation.BeanName;


/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("ZgdQueryBean")
public interface ZgdQueryClient {
	
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
	 * 查询用户账户类型（邮乐100、邮储101、无资质0）
	 * @return 
	 */
	public String querySysSource(String userOnlyId);
	
	/**
	 * 根据userOnlyId查询用户资料信息
	 * @param dto
	 * @throws Exception
	 */
	public List<ApplyDetailDto> findApplyDetail(ApplyDetailDto dto) throws Exception;
	
	
	/**
	 * 查询用户资料信息
	 * @param dto
	 * @throws Exception
	 */
	public Map<String,Object> findApplyDetail(Map<String,Object> dto) throws Exception;
	
	/**
	 * 查询用户审核情况
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<ApplyAuditDto> findApplyAuditDto(ApplyAuditDto dto) throws Exception;
	/**
	 * 根据客户userOnlyId查询客户相关信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String findUserOrgCode(String userOnlyId)throws Exception;
	/**
	 * 查询风险短信
	 * @param parm
	 * start 分页开始条数（查询时不包括本条）
	 * length 每次查询条数
	 */
	public Map<String,Object> queryList(Map<String,Object> parm);
	
	/**
	 * 查询用户姓名
	 * @param userOnlyId
	 * @return
	 */
	public String queryUserInfoByuserOnlyId(String userOnlyId);
	
	/**
	 * 查询风险短信不分页
	 */
	Map<String,Object> queryNotPageList(Map<String,Object> parm);
	
	/**
	 * 从搜索中获取用户短信
	 * @param userOnlyId 用户id
	 * @param lastTime 上次短信采集时间
	 * @return
	 */
	Collection<Map> querySMSFromCSE(String userOnlyId, long lastTime,int start);
	/**
	 * 从app端服务获取用户短信
	 * @param userOnlyId 用户id
	 * @param lastTime 上次短信采集时间
	 * @return
	 */
	Collection<Map> querySMSFromCSE(String userOnlyId, long lastTime,long endTime);
	
	/**
	 * 采集用户相关信息，默认SMS信息
	 * @param userOnlyId  map:  String userOnlyId, long lastTime,int start,String type,String orderBy,String fileds int forEach
	 * @param lastTime 上次短信采集时间
	 * @param start 采集开始条数
	 * @param type 采集类型 默认 sms
	 * @param orderBy 排序
	 * @param fileds 要返回的字段 默认是短信相关信息
	 * @param forEach 循环次数 默认 3次
	 * @return
	 */
	Collection<Map> queryHwFromCSE(Map<String,Object> map);
	
	/**
	 * 查询掌柜及其联系人相关的重复信息
	 * @param selectUserOnlyId
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> queryZgdRelInfoByRepeatRule(String selectUserOnlyId) throws Exception;
	
	/**
	 * 查询掌柜的店铺省市县区划信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryQueHuaOfZgInfo(String userOnlyId) throws Exception;
	
	/**
	 * 查询掌柜贷常量表T_J_CONSTANT里面key对应的value值
	 * @param <T>
	 * @param map1
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> queryZgdConstantValue(Map<String, Class<?>> map) throws Exception;
	
	/**
	 * 根据类型从 RISK_MESSAGE 索引中分页查询信息
	 * @param type  c:通话记录,l:位置信息,pb:通信录,sms:短信记录,hw:硬件记录,sw:软件信息
	 * @param pageSize
	 * @return
	 */
	public Collection<Map> querySMSFromCSEByType(String type, int pageSize);
	
	/**
	 * 根据用户id从 RISK_MESSAGE 索引中分页查询信息
	 * @param type  c:通话记录,l:位置信息,pb:通信录,sms:短信记录,hw:硬件记录,sw:软件信息
	 * @param pageSize
	 * @return
	 */
	public Collection<Map> queryFullSmsFromCseByUserOnlyId(String userOnlyId, int pageSize);
	

	/**
	 * 从搜索中获取用户安装APP信息
	 * @param userOnlyId 用户id
	 * @param lastTime 上次短信采集时间  
	 * type 默认 sw  c:通话记录,l:位置信息,pb:通信录,sms:短信记录,hw:硬件记录,sw:软件信息
	 * @return
	 */
	public Collection<Map> querySWFromCSE(String userOnlyId,long lastTime);
	
	/**
	 * 获取掌柜通讯录信息
	 * @param userOnlyId
	 * @return
	 */
	 public Collection<Map> queryMailListFromCSE(String userOnlyId, long lastTime);
	 
	 /**
	  * 查询产品配置信息
	  * @param info
	  * @return
	  */
	 public List<ProductInfoN> queryProductInfo(ProductInfoN info);
	
	 /**
	 * 查询掌柜贷常量表T_J_CONSTANT里面key对应的value值
	 * @param <T>
	 * @param map1
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> queryZgdConstantValue2(Map<String, Class<?>> map) throws Exception;
	/**
	 * 查询最新的设备信息
	 * @param userOnlyId
	 * @return
	 */
	Map<String, Object> queryNewHwFromCSE(String userOnlyId);
	/**
	 * 查询拒绝字典信息
	 * @param userOnlyId
	 * @return
	 */
	String queryRefuseDicInfo(String refuseCode,String type,Map<String,Object> descReplace);
	
	/**
	 * 查询或初始化信用分数据录入，等待定时查询
	 * @param userOnlyId
	 * @return 返回creditScore实体Bean Json
	 */
	String initOrQueryCreditScoreData(String userOnlyId,boolean ifInit) throws Exception;
	/**
	 * 保存信用分额度
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	BigDecimal saveCreditScoreLimit(String userOnlyId) throws Exception;
	/**
	 * 掌柜贷打折额度
	 * @param userOnlyId
	 * @param creditLimit
	 * @param channelCode
	 * @param approveType 
	 * @return 打折利率，最新的邮乐授信金额
	 * @throws Exception
	 */
	Map<String, Object> getZgdDiscountCreditLimit(String userOnlyId, BigDecimal creditLimit, String channelCode, String approveType)
			throws Exception;
	
	/**
	 * 初始化客户利率
	 * @param userOnlyId 用户编号
	 * @param ChannelCode 渠道号
	 * @param reInit 是否重新初始化
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> initCustomerInterest(String userOnlyId, String ChannelCode, boolean reInit) throws Exception;
}