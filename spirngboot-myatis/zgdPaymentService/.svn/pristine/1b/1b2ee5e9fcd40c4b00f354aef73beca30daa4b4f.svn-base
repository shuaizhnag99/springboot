package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/loanRecord")
public class LoanRecordController {
	private static Logger log = LoggerFactory.getLogger(LoanRecordController.class);
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId= "10000025522";
		return usronlyId;
	}

	/**
	 * 查询现金借款记录详情
	 * @return
	 */
	@RequestMapping("/queryLoanRecordDetail")
	@ResponseBody
	public JSONPObject queryLoanRecordDetail(HttpServletRequest request,@RequestParam String jsoncallback){
		String result= "";
		try{
			Map<String,Object> paras = new HashMap<String, Object>();
			paras.put("userOnlyId", getUserOnlyId(request));
			paras.put("type", "cash");
			result = WildflyBeanFactory.getYCZgdQueryClient().queryLoanRecordDetail(paras);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryLoanRecordDetail Error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询现金借款记录详情
	 * @return
	 */
	@RequestMapping("/queryPiXiaoLoanRecord")
	@ResponseBody
	public JSONPObject queryPiXiaoLoanRecord(HttpServletRequest request,@RequestParam String jsoncallback){
		String result= "";
		try{
			Map<String,Object> paras = new HashMap<String, Object>();
			paras.put("userOnlyId", getUserOnlyId(request));
			paras.put("type", "pixiao");
			result = WildflyBeanFactory.getYCZgdQueryClient().queryLoanRecordDetail(paras);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryLoanRecordDetail Error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
}
