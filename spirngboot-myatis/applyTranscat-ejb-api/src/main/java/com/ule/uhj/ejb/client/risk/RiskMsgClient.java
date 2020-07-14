package com.ule.uhj.ejb.client.risk;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.SmsHitRiskDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("RiskSMS")
public interface RiskMsgClient {

	/**
	 * 风险同步:
	 * 根据用户在数据库中max(collection_time)和当前时间差值 与 间隔常量diffTime(默认半个小时)比较大小
	 * 如果小于diffTime则从搜索中检索短信
	 * 对检索到的短信进行风险匹配
	 * 然后将匹配到的风险短信入库
	 * @param userOnlyId
	 * @return
	 */
	public List<SmsHitRiskDto> syncRiskSmsFromCse(String userOnlyId);
	
	/**
	 * 保存
	 * @param paras
	 */
	public void saveKeyWord(Map<String, Object> paras);
	
	/**
	 * 删除
	 * @param paras
	 */
	public void deleteKeyWord(Map<String, Object> paras);
	
	/**
	 * 修改
	 * @param paras
	 */
	public void updateKeyWord(Map<String, Object> paras);
	
	/**
	 * 查询
	 * @param paras
	 */
	public String listKeyWord(Map<String, Object> paras);
	
	
	/**
	 * 批量新增关键词
	 * @param words
	 */
	public void batchSaveKeyWord(List<String> words);
	
}
