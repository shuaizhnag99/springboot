package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("UleHelperFinancerBean")
public interface UleHelperFinancerClient {
	/***
	 * 查询指定掌柜信息
	 * @param userOnlyId
	 * @return
	 */
	public Map<String,Object> queryUserInfoByUserOnlyId(String userOnlyId);
	
	/***
	 * 查询指定掌柜实名认证状态
	 * @param userOnlyId
	 * @return
	 */
	public Map<String,Object> queryUserRealNameStatus(String userOnlyId);
	
	/***
	 * 查询指定掌柜机构号
	 * @param userOnlyId
	 * @return
	 */
	public String getOrgCodeByUserOnlyId(String userOnlyId);
	
	/***
	 * 保存用户信息
	 * @param parmaer
	 * @return
	 */
	public boolean saveUserInfo(Map<String,Object> paramer);
	
	/**
	 * 验证递推人员和掌柜的隶属关系
	 * @param userOnlyId 
	 * @param bangZGId 
	 * @return true 
	 */
	public boolean checkUserOnlyIdByBzgId(String bangZGId, String userOnlyId);
	
	
}
