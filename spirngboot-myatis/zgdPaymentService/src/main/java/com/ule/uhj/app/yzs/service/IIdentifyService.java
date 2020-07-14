package com.ule.uhj.app.yzs.service;

import java.util.Map;

import com.ule.uhj.app.zgd.model.CreditPostmember;

/**
 * 身份验证service
 * @author weisihua
 * @date 2017年11月1日
 */
public interface IIdentifyService {

	/**
	 * 验证地推人员是否已经通过验证。0：否，1：是
	 * @param userOnlyId
	 * @return
	 */
	int checkUserIdentify(String bzgId) throws Exception;
	
	/**
	 * 根据bzgId查询地推人员信息
	 * @param bzgId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryUserInfoByBzgId(String bzgId) throws Exception;
	
	/**
	 * 保存地推人员信息
	 * @param member
	 * @throws Exception
	 */
	void saveCreditPostMember(CreditPostmember member) throws Exception;
	
	/**
	 * 更改掌柜信息
	 * @param param
	 * @throws Exception
	 */
	void updateZgInfo(Map<String, Object> param) throws Exception;
}
