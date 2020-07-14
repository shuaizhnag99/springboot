package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

/***
 * 掌柜贷额度续期相关EJB接口集
 * 思路上将ejb层分为三个阶段：评测阶段（evaluating），数据采集阶段（dataCollection），第三方验证阶段（thirdPartyVerification）
 * 如无特别说明，Map集内返回code为999999时，均为系统异常，异常信息会放在message内提供阅读。
 * @author yfzx-sh-zhengxin
 *
 */
@Remote
@BeanName("YcLimitRenewBean")
public interface YcLimitRenewClient {
	/***
	 * 阶段1：检验掌柜是否具备借款资格
	 * 在该阶段主要验证掌柜当前的借款资格，来评价该掌柜是否需要进行额度续期（评测阶段）
	 * @param userOnlyId，用户唯一标识
	 * @return Map
	 * code:000000-资格具备 000001-账户已冻结 000002-账户超期，欠款未还 000003-需重新申请资格
	 * message:对code意义的描述
	 */
	Map<String,Object> evaluating(String userOnlyId);
	/***
	 * 阶段2：数据采集阶段
	 * 该阶段主要验证掌柜是否存在需要补录的资料，若有则需要掌柜重新提交审核资料（数据采集阶段）
	 * @param userOnlyId，用户唯一标识
	 * @return Map
	 * code:000000-阶段2测评无误，已转入阶段3流程 000001-用户身份证超期，已打回提交资料阶段
	 * mssage:对code意义的描述
	 */
	Map<String,Object> dataCollection(String userOnlyId);
	/***
	 * 阶段3：第三方验证阶段
	 * 该阶段调用第三方接口，即易微、百融，验证掌柜数据真实性（第三方验证阶段）
	 * @param userOnlyId，用户唯一标识
	 * @return Map
	 * code:000000-流程全部正常结束，资格申请已重新提交邮储 000001-经营者非本人 000002-一年内有行政处罚
	 * 000003-有外部黑名单或负债
	 * mssage:对code意义的描述
	 */
	Map<String,Object> thirdPartyVerification(String userOnlyId);
	
	Map<String,Object> appEvaluate(String userOnlyId);
}
