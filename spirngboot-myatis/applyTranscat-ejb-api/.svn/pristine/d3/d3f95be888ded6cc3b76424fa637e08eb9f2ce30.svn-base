package com.ule.uhj.ejb.client.zgd;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.ApplyAuditDto;
import com.ule.uhj.dto.zgd.ApplyDetailDto;
import com.ule.uhj.dto.zgd.ApplyImageDto;
import com.ule.uhj.dto.zgd.LoanOrderDto;
import com.ule.uhj.dto.zgd.ZgdRepayDto;
import com.ule.wildfly.annotation.BeanName;



/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("ZgdBean")
public interface ZgdClient {
	
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
	 * 每天更新逾期费用  (没有设置 setPreDateStr(yyyy-MM-dd) 则以系统昨天为准) 重新激活掌柜
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
	/**
	 * 保存用户资料信息
	 * @param dto
	 * @throws Exception
	 */
	public Map<String,Object>  saveOrUpdateApplyDetail(ApplyDetailDto dto) throws Exception;
	/**
	 * 保存用户基本资料信息
	 * @param dto
	 * @throws Exception
	 */
	public Map<String,Object> saveOrUpdateApplyDetailInfo(ApplyDetailDto dto)throws Exception;
	/**
	 * 保存用户图像信息
	 * @param dto
	 * @throws Exception
	 */
	public Map<String,Object> UpdateApplyDetailImage(ApplyDetailDto dto) throws Exception;
	  
	
	/**
	 * 保存用户审核信息
	 * @param dto
	 * @throws Exception
	 */
	public Map<String,Object> saveOrUpdateApplyAudit(ApplyAuditDto dto) throws Exception;
	
	/**
	 * 后台将用户打回实名认证之前
	 * @param dto
	 * @throws Exception
	 */
	public String returnRealName(String  userOnlyId) ;
	
	/**
	 * 保存预审审核信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> PreAuditSave(ApplyAuditDto dto)throws Exception;
	
	/**
	 * 除了浙江省意外地其他省份审核信息保存
	 * @param map(注意：applyaudit 实体类的部分属性和一个applydetail 的一个属性)
	 * @return
	 */
	public String  modifyApplydetailAndAudit(Map map);
	
	/**
	 * 删除预审 状态信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public void removeApplyAudit(String userOnlyId) throws Exception;
	
	
	  /**
	    * 批量新增或者修改图片信息
	    * @param dto
	    * @return
	    */
	public String saveOrUpdateList(List<ApplyImageDto> list);
	   /**
	    * 根据userOnlyID 查询图片信息
	    * @param userOnlyId
	    * @return
	    */
	public List<ApplyImageDto> queryByUserOnlyId(String userOnlyId);
	
	
	/**
	 * 保存用户基本资料信息（校验未通过，保存申请信息状态为实名之前）
	 * @param dto
	 * @throws Exception
	 */
	public Map<String,Object> saOrUpDetailInfoBeRealName(ApplyDetailDto dto)throws Exception;
	
	/**
	 * 规则组数据运行公共bean
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> zgdRuleSetRun(Map map) throws Exception;
	
	/**
	 * userOnlyId 重新激活根据
	 * @param map
	 * @return 0000 表示成功  1000 表示失败
	 * @throws Exception 
	 */
	public String reactivated(Map<String, Object> map);
}
