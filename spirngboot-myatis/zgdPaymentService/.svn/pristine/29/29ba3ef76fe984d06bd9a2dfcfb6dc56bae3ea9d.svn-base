package com.ule.uhj.app.zgd.service;

import java.util.List;
import java.util.Map;

import com.ule.uhj.app.zgd.model.AutoRepaySettingInfo;

/**
 * 自动还款service
 * @author weisihua
 * @date 2018年4月12日
 */
public interface IAutoRepayService {

	
	/**
	 * 查询自动还款设置信息
	 * @param userOnlyId 用户编号
	 * @return
	 * @throws Exception
	 */
	public AutoRepaySettingInfo queryAutoSettingInfo(String userOnlyId) throws Exception;
	
	/**
	 * 保存自动还款设置信息，把之前的配置状态设为0，并插入一条新的状态为1即可。
	 * @param amount 设置金额
	 * @param userOnlyId 用户编号
	 * @throws Exception
	 */
	public void saveAutoSettingInfo(String userOnlyId,int amount) throws Exception;
	
	/**
	 * 关闭自动还款设置
	 * @param userOnlyId
	 * @throws Exception
	 */
	public void closeAutoSetting(String userOnlyId) throws Exception;
	
	/**
	 * 查询所有需要发送邮储的自动还款参数信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAllRepayInfo() throws Exception;
	
	/**
	 * 自动还款接口
	 * @throws Exception
	 */
	public void autoRepay() throws Exception;
}
