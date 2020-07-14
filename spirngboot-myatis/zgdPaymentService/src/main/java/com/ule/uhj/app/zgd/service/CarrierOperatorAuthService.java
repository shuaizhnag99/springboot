package com.ule.uhj.app.zgd.service;

import java.util.List;
import java.util.Map;

import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;

public interface CarrierOperatorAuthService {
	
	public Map<String,Object> queryCarrierMobile(String userOnlyId)throws Exception;
	
	public Map<String,Object> saveRequestData(String userOnlyId,String flag,Map<String,Object> requestMap)throws Exception;
	
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestTokenData()throws Exception;
	
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestData(String userOnlyId)throws Exception;
	
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestSuanHuaData()throws Exception;
	
	public Map<String,Object> queryJuXinLiData(String userOnlyId)throws Exception;
	
	public Map<String,Object> querySuanhuaData(String requestStr)throws Exception;
	
	public Map<String,Object> queryCreditApplyInfo(String userOnlyId)throws Exception;
	
	public void updateReportData(String userOnlyId)throws Exception;
	
	public void updateApplyStatus(String userOnlyId,String authorizationOrg)throws Exception;
	
	public boolean queryFaildRequest(String userOnlyId)throws Exception;
	
	public void updateReportDataFaild(String userOnlyId)throws Exception;
	
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestJobStatus(String userOnlyId)throws Exception;

	public boolean querySuanHuaInfo(InterfaceAccessInfoWithBLOBs blobs);
	
	public boolean queryTongdunInfo(InterfaceAccessInfoWithBLOBs blobs);
	
	
	/**
	 * 查询和解析前海接口
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public boolean queryAndParseQHIntegerfaceAccessInfo(String userOnlyId) throws Exception;
	
	/**
	 * 新增调用前海插入credit_rule 逻辑
	 * @param userOnlyId
	 * @param authorizationOrg
	 * @throws Exception
	 */
	public void updateApplyStatusNew(String userOnlyId,String type)throws Exception;

	public boolean queryAndParseQHIntegerfaceAccessInfo1(String userOnlyId)throws Exception;

	/**
	 * 查询和解析鹏元在网时长接口
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	boolean queryAndParsePengyuanIntegerfaceAccessInfo(String userOnlyId)
			throws Exception;
}
