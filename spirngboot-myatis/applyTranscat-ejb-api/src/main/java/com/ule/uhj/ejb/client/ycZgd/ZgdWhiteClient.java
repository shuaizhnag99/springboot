package com.ule.uhj.ejb.client.ycZgd;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("ZgdWhiteBean")
public interface ZgdWhiteClient {

	/**
	 * 同步所有的未同步的白名单信息
	 */
	public String synchroVpsToZgdWhite();
	/**
	 * 根据userOnlyId同步白名单信息
	 * @param userOnlyId
	 */
	public String synchroVpsToZgdWhiteByUserOnlyId(String userOnlyId);
	
	/**
	 * 根据orgCode同步白名单信息
	 * @param orgCode
	 */
	public String synchroVpsToZgdWhiteByOrgCode(String orgCode);
	
	/**
	 * 批量同步白名单信息
	 * @param orgCode
	 */
	
	public String  synZgdUserInfoBatch() throws Exception ;
	/**
	 * 批量同步ApplyDetail
	 * @param 
	 */
	public String  synApplyDetailBatch() throws Exception;
	
	/***
	 * 白名单激活时间校验
	 * @return
	 * @throws Exception
	 */
	public String userActiveTimeCheck(String userOnlyId) throws Exception;
	/**
	 * 查询个人的白名单和进销存信息
	 * @param userOnlyId
	 * @return
	 */
	public Map<String,Object> findWhiteAndSaleInfo(String userOnlyId);
	
	/**
	 * 根据省和激活时间范围查询到相应的机构号
	 * @param provinceName
	 * @param minActiveTime
	 * @param maxActiveTime
	 */
	public void  syncZgdWhiteFromBIData(String provinceName, Date minActiveTime, Date maxActiveTime);
	/**
	 * CMR 根据机构号查询预估额度
	 * @param orgCodes
	 * @return
	 */
	public Map<String, Object> queryYuGuLimit(List<String> orgCodes);
	
	
	/**
	 * 查询系统中的当前销售数据月份
	 * @return 
	 * @throws Exception 
	 */
	public String querySysCurrSaleMoth();
}
