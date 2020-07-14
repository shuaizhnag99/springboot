package com.ule.uhj.sldProxy.service;

import java.util.List;
import java.util.Map;

import com.ule.uhj.sldProxy.model.InterfaceAccessInfoWithBLOBs;

public interface InterfaceAccessInfoService {
	public List<InterfaceAccessInfoWithBLOBs> queryInterfaceAccessInfoWithBLOBs(Map<String,Object> param)throws Exception;
	
	public void saveInterfaceRecord(String request,String response,String userOnlyId,String transCode,String source);

	String uploadInterfacePdfFile(String request, String response, String userOnlyId, String transCode, String source) throws Exception;

}
