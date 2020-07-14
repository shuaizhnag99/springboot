package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("BangZgBean")
public interface BangZGClient {

	/**
	 * 帮掌柜批量查询 给定掌柜id 的状态
	 * @param queryInfos
	 * key -- userOnlyIdCollection : List<String> 待查询的用户id的集合  最大1000条
	 * key -- resultFormat : String 1:json, 默认Map(该值不设置)
	 * @return Map<String, Object>
	 * key -- statusCollection : Map<String, String> (用户id:状态文本)
	 * key -- code: String "0000"(成功),其他失败
	 * key -- msg: String "success", 或者其它(处理结果)
	 */
	Map<String, Object> queryStatusLabel(Map<String, Object> queryInfos);
	
	/**
	 * 查询某个掌柜的申请进度值
	 * @param queryInfos
	 * key -- userOnlyId : 待查询进度的掌柜userOnlyId
	 * key -- bangZGId : 帮掌柜id
	 * key -- resultFormat : String 1:json, 默认Map(该值不设置)
	 * @return Map<String, Object>
	 * key -- storeName : String 店铺名称
	 * key -- userName : String 掌柜名称
	 * key -- process : List<String> 进度节点名称;节点时间点
	 * key -- code: String "0000"(成功),其他失败
	 * key -- msg: String "success", 或者其它(处理结果)
	 */
	Map<String, Object> queryApplyProcess(Map<String, Object> queryInfos);
	public Map<String, Object>  zgdPopularize(String userOnlyId,String taskType) throws Exception;
	public Map<String,Object>  queryZgdPopularize(String merchentId) throws Exception;
	/**
	 * 查询管理掌柜页面信息
	 * @param queryInfos
	 * key -- userOnlyId : 查询地推人员userOnlyId
	 * @return queryInfos
	 * key -- code：
	 * key -- msg：
	 */
	Map<String, Object> queryZgManageInfo(Map<String, Object> queryInfos);
	
	public void searchAndStroeSkdaily() throws Exception;
	
	public void dealLoanRule() throws Exception;
	/**
	 * 根据掌柜useronlyid获取地推人员信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryBzgByUserOnlyId(String userOnlyId)throws Exception;
	
	/**
	 * 根据掌柜bzgId获取地推人员信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 * @author weisihua
	 */
	Map<String, Object> queryBzgInfoByBzgId(String bzgId)throws Exception;
	
	/**
	 * 查询地推人员所属机构信息
	 * @param bzgId
	 * @return
	 * @throws Exception
	 * @author weisihua
	 */
	Map<String, Object> queryBzgOrgInfoByBzgId(String bzgId)throws Exception;
	
	/**
	 * 查询地推人员所属机构信息
	 * @param bzgId
	 * @return
	 * @throws Exception
	 * @author weisihua
	 */
	Map<String, Object> queryUserListByBzgId(String bzgId)throws Exception;

	/**
	 * 根据手机号获取对应的地推人员信息
	 * @param mobileNo
	 * @return
	 * @throws Exception
	 */
	Map<String, String> queryBzgInfoByMobileNo(String mobileNo) throws Exception;
}