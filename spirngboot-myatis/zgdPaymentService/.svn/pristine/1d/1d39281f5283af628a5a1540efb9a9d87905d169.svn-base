package com.ule.uhj.app.zgd.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ule.uhj.app.zgd.service.ManualCreditService;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.http.HttpClientUtil;

@Service
public class ManualCreditServiceImpl implements ManualCreditService {

	private static final Logger log = LoggerFactory.getLogger(ManualCreditServiceImpl.class);
	
	@Override
	public String manualCredit(String userOnlyId, String uleScore) throws Exception {
		log.info("进入方法 manualCredit 参数为：[userOnlyId : "+userOnlyId+" , uleScore : "+uleScore+"]");
		String returnCode = "000000";
		String URL_PREFIX = PropertiesHelper.getDfs("mgt_prefix");
		//String URL_PREFIX = "http://localhost/ifadmin";
		if(StringUtils.isEmpty(userOnlyId)) {
			throw new Exception("userOnlyId不能为空!");
		}
		if(StringUtils.isEmpty(uleScore)) {
			throw new Exception("[uleScore]邮乐评分不能为空!");
		}
		
		// 组装参数
		Map<String, String> param = new HashMap<String, String>();
		param.put("userOnlyId", userOnlyId);
		param.put("uleScore", uleScore);
		// 先同步数据
		String syncUrl = URL_PREFIX + "/framework/approve/manualSynData4App.do";
		String syncResult = HttpClientUtil.sendPost(syncUrl, param);
		Map<String, Object> syncMap = new HashMap<String, Object>();
		try {
			syncMap = JsonUtil.getMapFromJsonString(syncResult);
		} catch (Exception e) {
			throw new Exception("userOnlyId : "+userOnlyId+" ,同步数据接口没有返回正确的json格式，接口异常");
		}
		if(!CollectionUtils.isEmpty(syncMap)) {
			if(!"000000".equals(syncMap.get("code"))) {
				log.info("同步数据失败，userOnlyId : "+userOnlyId+" ,原因是："+syncMap.get("message"));
				returnCode = "100000";
				return returnCode;
			}
		}
		log.info("数据同步成功！");
		
		// 授信额度建模
		String creditUrl = URL_PREFIX + "/framework/approve/manualLimitCredit4App.do";
		String creditResult = HttpClientUtil.sendPost(creditUrl, param);
		Map<String, Object> creditMap = new HashMap<String, Object>();
		try {
			creditMap = JsonUtil.getMapFromJsonString(creditResult);
		} catch (Exception e) {
			throw new Exception("userOnlyId : "+userOnlyId+" ,授信额度建模接口没有返回正确的json格式，接口异常");
		}
		if(!CollectionUtils.isEmpty(creditMap)) {
			if(!"000000".equals(creditMap.get("code"))) {
				log.info("userOnlyId : "+userOnlyId+" ,授信额度建模失败，原因是："+creditMap.get("message"));
				returnCode = "200000";
				return returnCode;
			}
		}
		log.info("授信额度建模成功！");
		
		// 额度建立
		String applyUrl = URL_PREFIX + "/framework/approve/manualLimitApply4App.do";
		String applyResult = HttpClientUtil.sendPost(applyUrl, param);
		Map<String, Object> applyMap = new HashMap<String, Object>();
		try {
			applyMap = JsonUtil.getMapFromJsonString(applyResult);
		} catch (Exception e) {
			throw new Exception("userOnlyId : "+userOnlyId+" ,额度建立接口没有返回正确的json格式，接口异常");
		}
		if(!CollectionUtils.isEmpty(applyMap)) {
			if(!"000000".equals(applyMap.get("code"))) {
				log.info("userOnlyId : "+userOnlyId+" ,额度建立失败，原因是："+applyMap.get("message"));
				returnCode = "300000";
				return returnCode;
			}
		}
		log.info("额度建立成功！");
		
		return returnCode;
	}

}
