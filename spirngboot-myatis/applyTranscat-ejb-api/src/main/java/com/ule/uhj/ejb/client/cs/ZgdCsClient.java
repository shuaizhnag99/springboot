package com.ule.uhj.ejb.client.cs;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.acc.CreditAccountDto;
import com.ule.uhj.dto.zgd.ApplyAuditDto;
import com.ule.uhj.dto.zgd.ApplyDetailDto;
import com.ule.uhj.dto.zgd.OrderDto;
import com.ule.uhj.dto.zgd.ZgdQueryDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("ZgdCsBean")
public interface ZgdCsClient {

	/**
	 * 查询待放款记录 order表 status = 0
	 * @param paras
	 * export:1导出,0查询
	 * currPage: 当前页
	 * pageSize: 每页条数
	 * @return Map<String, Object>
	 * total : 总数
	 * list[OrderDto] 借据id,申请人姓名,申请人身份证号,申请人待放款卡的银行名称,申请人待放款卡的银行卡号,申请金额,申请时间
	 */
	public Map<String, Object> queryWillLoanRecords(Map<String, Object> paras) throws Exception;
	
	/***
	 * 查询授信日志
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryLimitApplyLog(ZgdQueryDto dto);
	
	/***
	 * 按照账户号查询对应的用户信息
	 * @param AccountNo 账户号
	 * @return Map.userName:用户名 Map.phoneNumber:用户手机号
	 */
	public Map<String,Object> queryUserInfoByAccountNo(String AccountNo);
	
	/***
	 * 按照账户号查询对应的借据信息
	 * @param paramers paramers.currentPage:当前页 paramers.pageSize:每页条数 paramers.accountNo:账户号
	 * @return String json code 0000 成功 msg success 成功 total 总条数 list [{dueId 借据id, loanAmount 借钱 借款金额 , restPrincipal 剩余本金, drawTime 时间 放款时间, status 状态}] //0未还款, 1已还款 ,2已逾期 ,3已取消 ,4待放款
	 */
	public String queryDuesByAccountNo(Map<String,Object> paramers);
	/**
	 * 财务放款后更新订单状态,产生借据
	 * @return Map<String, Object>
	 * total : 总数
	 * list[OrderDto] 借据id,申请人姓名,申请人身份证号,申请人待放款卡的银行名称,申请人待放款卡的银行卡号,申请金额,申请时间,财务放款金额,财务放款时间
	 */
	public Map<String, Object> updateAfterLend(List<OrderDto> dtos) throws Exception;
	
	/***
	 * 根据条件查询指定掌柜用户名下的所有账户信息
	 * @param zgdQueryDto 需拼装掌柜名、掌柜身份证号、机构代码、省代码至该类
	 * @return Map<String,String>
	 * result:状态，000为成功，其他为失败
	 * total:记录数量
	 * List<ZgdQueryDto> 返回所有符合条件的账户信息
	 * @throws Exception
	 */
	public Map<String,Object> queryZgdAccountByUserInfo(ZgdQueryDto zgdQueryDto) throws Exception;
	
	/***
	 * 掌柜贷开户 创建 如果没有userInfo 创建
	 * @param dto 传输模型内需传入用户id与限额
	 * @return
	 * @throws Exception
	 * code 0000
	 * msg success
	 */
	public String openZgdAccinfo(CreditAccountDto dto) throws Exception;
	
	/**
	 * 掌柜贷待放款审核记录查询
	 * @param map
	 * @return
	 * total 记录总数
	 * list<OrderDto> 待放款记录
	 * @throws Exception
	 */
	public Map<String ,Object> loanReview(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询余额
	 * @param map
	 * @return
	 * total 记录总数
	 * list<OrderDto> 查询余额记录
	 * @throws Exception
	 */
	public Map<String ,Object> queryBalance(Map<String,Object> map)throws Exception;
	
	/**
	 * 掌柜贷通报信息查询
	 * @param map
	 * @return
	 * Map<String ,Object> 掌柜贷通报信息记录
	 * @throws Exception
	 */
	public Map<String ,Object> queryBulletinInfo(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 应收表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> queryShouldCharged(Map<String,Object> map)throws Exception;
	
	
	
	/**
	 *  借款 or 逾期统计 查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> queryLoanOrOverdue(Map<String,Object> map)throws Exception;
	
	
	
	/**
	 * 应收表SUM合计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> queryRaSum(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 掌柜贷放款确认记录查询
	 * @param map
	 * export:1导出,0查询
	 * @return
	 * total 记录总数
	 * list<OrderDto> 放款待确认记录
	 * @throws Exception
	 */
	public Map<String ,Object> loanConfirmation(Map<String,Object> map)throws Exception;
	/**
	 * 根据订单Id 修改订单状态
	 * @param map
	 * String []ids 订单id数组
	 * status 修改后的订单状态
	 * @return
	 * @throws Exception
	 */
	public void updateOrderStatusById(Map<String,Object>map)throws Exception;
	
	/**
	 * 放款明细查询
	 * @param map
	 * orderTime 放款时间
	 * province  省份
	 * @return
	 * total 记录总数
	 * list<OrderDto> 放款待确认记录
	 * @throws Exception
	 */
	public Map<String,Object> queryOrderDetail(Map<String,Object> map) throws Exception;
	
	/**
	 * 还款明细查询
	 * @param map
	 * orderTime 放款时间
	 * province  省份
	 * @return
	 * total 记录总数
	 * list<OrderDto> 放款待确认记录
	 * @throws Exception
	 */
	public Map<String,Object> queryTranzInfoDetail(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 根据账单号修改账单状态为取消
	 * @param BillId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateBillById(String BillId)throws Exception;
	
	/**
	 * 通过给定的邮乐县区的uleCode 返回对应的邮储区划
	 * @param uleLevelCode
	 * @return
	 * @throws Exception 
	 */
	public String getPostQuHua(String uleLevelCode) throws Exception;
	
	/**
	 * 借款查询
	 * @param map
	 * orderTime 放款时间
	 * province  省份
	 * @return
	 * total 记录总数
	 * list<OrderDto> 放款待确认记录
	 * @throws Exception
	 */
	public Map<String,Object> queryLoan(Map<String,Object> map) throws Exception;
	
	/**
	 * 预审岗 详细信息查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryNewDetail(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 补充资料 查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryExtendPics(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 保存预审信息
	 * @param dto
	 * @throws Exception
	 */
	public void saveOrUpdateYuShenAuditApplyDetail(ApplyDetailDto dto) throws Exception;
	
	/**
	 * 删除预审信息
	 * @param userOnlyId
	 * @throws Exception
	 */
	public void removeYuShenDetailInfo(String userOnlyId) throws Exception;
	
	
	/**
	 * 补充文件保存
	 * @param map
	 * @throws Exception
	 * @return id
	 */
	public String saveOrUpdateExtendPics(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询邮储授信的总额度
	 * @return
	 * @throws Exception
	 */
	public String queryAllYouChuLimit() throws Exception;
	/**
	 * 补充资料 删除
	 * @param picId
	 * @return
	 * @throws Exception
	 */
	public String updateStatus(String picId)throws Exception;
	
	
	/**
	 * 逾期后还款但未恢复额度的记录信息查询
	 * @param map
	 * @throws Exception
	 */
	public Map<String, Object> queryRepayDetailList (Map<String, Object> paras) throws Exception;
	
	/**
	 * 根据邮储的记录ID	后台恢复额度等信息
	 * @param map
	 * @throws Exception
	 */
	public String dealRepayDetailList (String recordId) throws Exception;
	
	/**
	 * 预审岗 状态保存
	 * @param dto
	 * @throws Exception
	 */
	public void saveOrUpdateApplyAudit(ApplyAuditDto dto) throws Exception;
	
	/**
	 * 查询支用记录 order表 
	 * @param paras
	 * export:1导出,0查询
	 * currPage: 当前页
	 * pageSize: 每页条数
	 * @return Map<String, Object>
	 * total : 总数
	 * list[Map<String,Object>] 机构号orgCode、所属地区address、掌柜名称userName、支用时间orederTime、支用金额orderAmount、支用期限periods、利率interRate、还款方式repayType、固定还款日、状态status、备注ycMessage；
	 */
	public Map<String, Object> queryDisburse(Map<String, Object> paras) throws Exception;
	
	/**
	 * 邮储原始还款流水
	 * @param paras
	 * currPage: 当前页
	 * pageSize: 每页条数
	 * @return Map<String, Object>
	 * total : 总数
	 * list[Map<String,Object>]
	 */
	public Map<String, Object> queryPsRepaymentWater(Map<String, Object> paras) throws Exception;
	
	/**
	 * 贷中监控查询 
	 * @param paras
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryLoanMonitorInfo(
			Map<String, Object> paras);
	
	/**
	 * 贷中监控锁事件处理 
	 * @param paras
	 * @return String 默认"success"
	 */
	public String dealMonitorEvent(Map<String, Object> paras) throws Exception;
	
	/**
	 * 查询支用提额/降额日志
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryDealApply(Map<String,Object> paras)throws Exception;
	
	/**
	 * 查询代办事项
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryToDoList(Map<String,Object> paras)throws Exception;

	
	/**
	 * opc 权限配置
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryUserProvincesList(Map<String,Object> paras)throws Exception;
	
	/**
	 * opc 删除
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public void delUserProvinces(Map<String,Object> paras)throws Exception;
	
	/**
	 * opc 保存
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public String saveUserProvinces(Map<String,Object> paras)throws Exception;
	
	/**
	 * opc 保存
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public void updateUserProvinces(Map<String,Object> paras)throws Exception;
	
	/**
	 * 查询账户变更日志
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryAccountCreditLog(Map<String,Object> paras)throws Exception;
	
	/**
	 * 查询掌柜支用记录
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryApplyRecord(Map<String,Object> paras) throws Exception;
	
	/**
	 * 查询掌柜逾期记录
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryOverDueRecord(Map<String,Object> paras) throws Exception;
	
	/**
	 * 查询风险短信数据
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> querySmsRiskInfo(Map<String,Object> paras) throws Exception;
	
	/**
	 * 查询掌柜贷white表信息
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryZgdWhiteInfo(Map<String,Object> paras) throws Exception;
	
	/**
	 * 修改营业执照
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateBusLiecnceNo(Map<String,Object> map) throws Exception;
	
	/**
	 * 保存电话审核信息
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> saveCustomVisitInfo(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据条件查询电话审核信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryCustomVisitInfo(Map<String,Object> map) throws Exception;
	/**
	 * 查询账户变更日志
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryApplyDdetail(String userOnlyId)throws Exception;
	
	/**
	 * 查询所有已授信用户的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryZgdUserInfo(Map<String,Object> map) throws Exception;
	
	/**
	 * 修改掌柜手机号/银行卡号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> changeZgdUserInfo(Map<String,Object> map) throws Exception;
	 
	 /**
	 * 查询掌柜手机号/银行卡号日志记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryChangeLog(Map<String,Object> map) throws Exception;

	/**
	 * 保存平效测算结果到申请审核表
	 * @param regressResult
	 * @param regressResult 
	 * @throws Exception
	 */
	public void saveRevernueRegressResult(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询坏账列表
	 * @param regressResult
	 * @param regressResult 
	 * @throws Exception
	 */
	public Map<String,Object> queryBadOrderList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询地推人员信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryPostMember(Map<String, Object> map) throws Exception;
	
	/**
	 * 还款分润列表
	 * @param regressResult
	 * @param regressResult 
	 * @throws Exception
	 */
	public Map<String,Object> queryRepaymentShareList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询地推人员绑定的掌柜信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryBindZgdInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据手机号查询邮助手接口返回的掌柜信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryCreditPostMemberInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 保存移交的地推人员信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> saveCreditPostMemberInfo(Map<String, Object> map) throws Exception;

}
