package com.ule.uhj.app.zgd.service;

import java.util.Map;

/**
 * 聚信力查询服务密码
 * @author Administrator
 *
 */
public interface JuXinLiService{
    /**
     * 获取支持的数据源列表
     * @return
     * @throws Exception
     */
	public String datasources(Map<String,Object> param)throws Exception;
    
    /**
     * 提交申请表单获取回执信息
     * @return
     * @throws Exception
     */
	public String getReceipt(Map<String,Object> dataMap)throws Exception;
    
    /**
     * 查询数据采集接口
     * @param paras
     * @return
     * @throws Exception
     */
	public String queryCollectMsg(Map<String, String> paras)throws Exception;
	
	/**
	 * 提交跳过当前数据源接口
	 * @return
	 * @throws Exception
	 */
	public String skipRequest(Map<String,Object> param)throws Exception;

	/**
	 * 重置密码
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public String resetPwd(Map<String, Object> paras)throws Exception;
	
	public String accessReportToken(Map<String,Object> param)throws Exception;
	
	/**
	 * 根据用户基本信息返回用户报告数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String accessReportData(Map<String,Object> param)throws Exception;
	
	/**
	 * 根据用户基本信息返回运营商原始数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String accessRawData(Map<String,Object> param)throws Exception;
	
	/**
	 * 根据用户基本信息返回电商原始数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String accessBinessRawData(Map<String,Object> param)throws Exception;
	
	/**
	 * 根据用户基本信息获取报告状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String accessJobStatus(Map<String,String> param)throws Exception;
}
