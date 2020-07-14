package com.ule.uhj.ejb.client.cs;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.acc.OrderDto;
import com.ule.uhj.dto.zgd.ApplyDetailDto;
import com.ule.uhj.dto.zgd.OperateLogDto;
import com.ule.uhj.dto.zgd.PageDto;
import com.ule.uhj.dto.zgd.SmsHitRiskDto;
import com.ule.wildfly.annotation.BeanName;


/**
 * @author zhangyaou
 *
 */
@Remote
@BeanName("ZgdCSWebBean")
public interface ZgdCSWebClient {
	
	
	public PageDto<OrderDto> queryOrderDtoList(PageDto<OrderDto> queryDto)
			throws Exception ;
	
	public Map<String,Object> queryUserOrg(String userOnlyId)throws Exception;
	
	public Map<String,Object> ApplyDetailSend(Map<String,Object> map)throws Exception;
	
	public Map<String, Object> ExportZgdInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> queryOperateLog(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> exportOperateLog(Map<String, Object> map)throws Exception;
	
	public List<OperateLogDto> queryOperateLog(String userOnlyId) throws Exception;
	
	/**
	 * 
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public ApplyDetailDto findApplyDetailDtoByUserOnlyId(String userOnlyId)throws Exception;
	
	public List<SmsHitRiskDto> findSmsHitRiskByUserOnlyId(String userOnlyId)throws Exception;
}
