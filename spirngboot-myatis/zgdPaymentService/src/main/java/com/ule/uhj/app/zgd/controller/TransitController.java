package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.CreditReturnService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/transit")
public class TransitController {
	private static Logger log = LoggerFactory.getLogger(TransitController.class);
	
	@Autowired
	private CreditRuleService creditRuleService;
	
	@Autowired
	private CreditApplyService creditApplyService;
	
	@Autowired
	private CreditReturnService creditReturnService;
	
	/**
	 * 手机app进入掌柜贷的时候跳转页面控制
	 * applyStatus   
	 *  0：引导页面
	 * 	2：运营商授权失败页面
	 *  3：等待审核
	 *  4：审核被打回重新申请(显示打回原因) reason
	 *  5 ：审核拒绝  显示重新申请日期   reactivatedDate
	 *  6:发送邮储申请接口阻塞中
	 *  7:邮储建额失败
	 *  100：中断页面
	 *  88：支用提示页面 
	 *  77  支用页面
	 *  9999 邮掌柜用户信息提示页面姓名和身份证号不一致的页面
	 *  
	 *  20  地址完善提示页面
	 *  10  批销额度激活页面
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/toAppPage")
	@ResponseBody
	public JSONPObject toAppPage(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("toAppPage begin.");
		try {
//			String result =JsonResult.getInstance().addOk().toJsonStr();
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("toAppPage userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			String result =creditApplyService.queryApplyStatus(map);
			log.info("toAppPage userOnlyId:"+userOnlyId+";result:"+result);
			// 增加userOnlyId作为开关
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("userOnlyId", userOnlyId);
			returnMap.put("result", result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(returnMap));
		} catch (Exception e) {
			log.error("toAppPage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 中断页面
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/toTransferPage")
	@ResponseBody
	public JSONPObject toTransferPage(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("toTransferPage begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("toTransferPage userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			String result =creditApplyService.querySuspendStatus(map);
			log.info("toTransferPage userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("toTransferPage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 新用户续期人脸以及店铺状态修改和记录是否还在经营
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/toXuqiStatuPage")
	@ResponseBody
	public JSONPObject toXuqiStatuPage(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("toXuqiStatuPage begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			String isStillOpen=request.getParameter("isStillOpen");
			log.info("toXuqiStatuPage userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("isStillOpen", isStillOpen);
			String result =creditApplyService.toXuqiStatuPage(map);
			log.info("toXuqiStatuPage userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("toXuqiStatuPage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**
	 * 新用户续期流程控制
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/toXuqiPage")
	@ResponseBody
	public JSONPObject toXuqiPage(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("toXuqiPage begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("toXuqiPage userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			String result =creditApplyService.toXuqiPage(map);
			log.info("toXuqiPage userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("toXuqiPage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
}
