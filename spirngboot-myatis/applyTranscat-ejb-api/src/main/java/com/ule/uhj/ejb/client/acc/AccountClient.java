package com.ule.uhj.ejb.client.acc;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.acc.CreditAccountDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("AccountBean")
public interface AccountClient {

	/**
	 * 查询闪电提额上下限
	 * @param userOnlyId
	 * @return
	 */
	public String fastAmountLowAndTop(String userOnlyId);
	/**
	 * 开通掌柜贷授信账户
	 * 未使用 不要使用  使用zgdCsBean.openZgdAccinfo 
	 * @throws Exception
	 */
	@Deprecated
	public String openCreditAccount(CreditAccountDto dto) throws Exception;
	
	/**
	 * 验证指定账户使用闪电提额的资质
	 * @param userOnlyId
	 * @return
	 */
	public boolean checkFastAmount(String userOnlyId);
	
	/**
	 * 闪电提额
	 * @param paramers accNo:掌柜账户号 amount:新额度 transType:交易类型
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> fastAmount(Map<String,Object> paramers) throws Exception;
	
	/**
	 * 查询掌柜贷产品掌柜状态
	 * @return  返回json字符串  code 4位 ; status：1位
	 * code:0000(成功), 非0000均为失败
	 * msg:success or 其他具体原因
	 * status:
	 * 0(未开通)，
	 * 1(已申请)， 
	 * 2(申请通过)， 
	 * 3(已开通)， 
	 * 4(还款中)， 
	 * 5(逾期xx天)
	 * overDays: 如果 status=5  该字段有值
	 */
	public String queryZgdUserStatus(String userOnlyId);
	
	
	/**
	 * 邮掌柜组使用
	 * 查询用户是否有白名单的资质
	 * @param userOnlyId 用户id
	 * @return json
	 * code : 0000 成功;非0000失败
	 * msg : 返回成功或失败的消息
	 * result : 0 无资质,1 有资质
	 */
	public String queryWhiteQualifyByUserOnlyId(String userOnlyId);
	
	/**
	 * 批量提额用
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> fastAmountToYouChuLimit()
			throws Exception;
	
	/**
	 * 更新周期内到期提醒次数
	 * @param userOnlyId
	 * @param expireRemind
	 * @return
	 */
	public String updExpireRemindCount(String userOnlyId, String expireRemind);
}
