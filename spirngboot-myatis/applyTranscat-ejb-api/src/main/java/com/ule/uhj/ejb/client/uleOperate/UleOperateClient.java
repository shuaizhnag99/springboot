package com.ule.uhj.ejb.client.uleOperate;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("UleOperateBean")
public interface UleOperateClient {
	/**
	 * 根据机构组织ID返还该机构组织下的掌柜贷统计数据
	 * @param id
	 * @return
	 */
	public Map<String,Object> operateTotalDate(String id);
	/**
	 * 根据机构组织ID返还该机构组织下的掌柜贷详情数据
	 * @param id
	 * @return
	 */
	public Map<String,Object>  operateDeatil(String id);
	
	/**
	 * 获取redis 指定区域内邮掌柜总数
	 * @param key
	 * @return
	 */
	public String getUserCount(String key);
	
	public String setUserCount(String key,String value);
	/**
	 * 发送数据到kafka
	 * @param data
	 * @return
	 */
	public String sendDataToKafka(Map<String,Object> data);
	/**
	 * 获取缓存数据
	 * @param key
	 * @return
	 */
    public String getCacheCloudData(String key);
	/**
	 * 设置缓存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public String setCacheCloudData(String key,String value);
	
	
}
