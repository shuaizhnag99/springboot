package com.ule.uhj.sldProxy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangyaou
 * 2016年6月5日
 */
@Controller
@RequestMapping(value = "/apply")
public class MerchentApiController {
	private static Logger log = LoggerFactory.getLogger(MerchentApiController.class);
	
	
	/**
	 * 从商家组获取基本信息
	 * @return
	 */
	@RequestMapping("/getBasicMerInfoFromMer")
	@ResponseBody
	public String getBasicMerInfoFromMer() {
//		MerApplyInfo applyInfo=MerchantBaseTools.getInstance().getMerApplyBeanClient().queryMerApplyInfoByMerchantId(merchantId);
//		MerApplyBeanClient client = MerchantBaseTools.getInstance().getMerApplyBeanClient();
//		MerApplyInfo info = client.queryMerApplyInfoByMerchantId(merchantId);
		
		return "{\"hello\":\"word\"}";
	}
	
	/**
	 * 更新商家信息，
	 * @param content
	 * updateType 2位长度  更新的类型，不同的类型允许更新的字段不同，由页面要素决定，原则上每个页面对应一个
	 * IP 记录
	 * @return
	 */
	@RequestMapping("/updateMerInfo")
	@ResponseBody
	public String updateMerInfo(@RequestBody String content){
		return "{\"hello\":\"word\"}";
	}
	
}
