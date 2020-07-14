package com.ule.uhj.sldProxy.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ule.dfs.client.util.UploadFile;
import com.ule.oracle.dao.InterfaceRecordMapper;
import com.ule.oracle.model.InterfaceRecordWithBLOBs;
import com.ule.uhj.provider.common.CommonTranzCode;
import com.ule.uhj.sld.constant.BDTransCodeEnum;
import com.ule.uhj.sld.constant.BRTransCodeEnum;
import com.ule.uhj.sld.constant.TDTransCodeEnum;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.sldProxy.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.sldProxy.model.InterfaceAccessInfoExample;
import com.ule.uhj.sldProxy.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.sldProxy.service.InterfaceAccessInfoService;
import com.ule.uhj.sldProxy.util.PropertiesHelper;
import com.ule.uhj.util.PdfUtils;

@Service("interfaceAccessInfoService")
public class InterfaceAccessInfoServiceImpl implements InterfaceAccessInfoService{
	
	private static Logger log = LoggerFactory.getLogger(InterfaceAccessInfoServiceImpl.class);
	@Autowired
	private InterfaceAccessInfoMapper interfaceAccessInfoMapper;
	@Autowired
	private InterfaceRecordMapper interfaceRecordMapper;

	@Override
	public List<InterfaceAccessInfoWithBLOBs> queryInterfaceAccessInfoWithBLOBs(Map<String,Object> param)
			throws Exception {
		InterfaceAccessInfoExample interfaceInfoExample = new InterfaceAccessInfoExample();
		interfaceInfoExample.createCriteria().andUserOnlyIdEqualTo(Convert.toStr(param.get("userOnlyId")))
		.andInterfaceTypeEqualTo(Convert.toStr(param.get("tranzCode")));
		return interfaceAccessInfoMapper.selectByExampleWithBLOBs(interfaceInfoExample);
	}

	@Async
	@Override
	public void saveInterfaceRecord(String request,
			String response,String userOnlyId,String transCode,String source){
		String fileUrl = null;
		try {
			fileUrl = uploadInterfacePdfFile(request, response,userOnlyId,transCode,source);
		}catch(Exception ex){
			log.error("saveInterfaceRecord file error",ex);
		}
		try{
			log.info("saveInterfaceRecord save interface info start...");
			Map param = JSONObject.parseObject(request,Map.class);
			String appId =  Convert.toStr(param.get("appId"));
			InterfaceRecordWithBLOBs interfaceRecordWithBLOBs = new InterfaceRecordWithBLOBs();
			interfaceRecordWithBLOBs.setUserOnlyId(userOnlyId);
			interfaceRecordWithBLOBs.setRequestInfo(request.getBytes("utf-8"));
			interfaceRecordWithBLOBs.setResponseInfo(response.getBytes("utf-8"));
			interfaceRecordWithBLOBs.setStatus("1");
			interfaceRecordWithBLOBs.setInterfaceType(transCode);
			interfaceRecordWithBLOBs.setType(Convert.toStr(source,"300"));
			interfaceRecordWithBLOBs.setAppId(appId);
			interfaceRecordWithBLOBs.setRemark(fileUrl);
			interfaceRecordWithBLOBs.setExceptionLog("查询成功");
			interfaceRecordWithBLOBs.setCreateTime(DateUtil.currTimeStr());
			interfaceRecordMapper.insertSelective(interfaceRecordWithBLOBs);
			log.info("saveInterfaceRecord save interface info end...");
		}catch(Exception ex){
			log.error("saveInterfaceRecord error",ex);
		}
	}
	
	
	@Override
	public String uploadInterfacePdfFile(String request,
			String response,String userOnlyId,String transCode,String source) throws Exception{
		List<String> list = new ArrayList<String>();
		list.add("用户ID");
		list.add("来源");
		list.add("接口类型");
		list.add("请求参数");
		list.add("响应结果");
		list.add("时间");
		List<Float> sizelist = new ArrayList<Float>();
		sizelist.add(Float.valueOf("1"));
		sizelist.add(Float.valueOf("9"));
		List<String> body = new ArrayList<String>();
		body.add(userOnlyId);
		body.add("300".equals(source)?"掌柜贷":source);
		String transName = "";
		if(transCode.startsWith("BD")) {
			transName = BDTransCodeEnum.get(transCode).getMessage();
		}else if(transCode.startsWith("BR")) {
			transName = BRTransCodeEnum.get(transCode).getMessage();
		}else if(transCode.startsWith("TD")) {
			transName = TDTransCodeEnum.get(transCode).getMessage();
		}else{
			Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(transCode);
	        if(isNum.matches()){
	        	CommonTranzCode ct = CommonTranzCode.getTranzCode(Integer.valueOf(transCode));
				if(ct!=null) {
					transName = ct.getDescript();
				}
	       }else{
	    	   transName = transCode;
	       }
		}
		body.add(transName);
		body.add(request);
		body.add(response);
		body.add(DateUtil.currTimeStr());
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("header", list);
		map.put("sizeRatio", sizelist);
		map.put("body", body);
		byte[] pdf = PdfUtils.createPdfFileStream(userOnlyId, map);
		InputStream in = new ByteArrayInputStream(pdf);
		String uploadURL = PropertiesHelper.getDfs("uploadURL");
		String bussinessUnit = PropertiesHelper.getDfs("bussinessUnit");
		String date= com.ule.uhj.sld.util.DateUtil.currDateSimpleStr();
		String fullName="/app_"+ userOnlyId+"/file"+date+"/"+new Date().getTime()+ (int) (Math.random() * 10000) +".pdf";
		log.info("uploadInterfacePdfFile begin upload..."+userOnlyId);
		String result = UploadFile.upload(uploadURL, bussinessUnit, fullName, in, null);
		log.info("uploadInterfacePdfFile upload end result is:" + result);
		if (result != null) {
			log.info("upload ok : " + result);
			Map obj=JSONObject.parseObject(result, Map.class);
			if("success".equals(obj.get("status"))){
				return Convert.toStr(obj.get("url"));
			}
		}
		return null;
	}
	
	

}
