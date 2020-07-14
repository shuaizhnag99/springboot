package com.ule.uhj.zgd.controller;

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
import com.ule.uhj.ejb.client.ycZgd.PXZgdClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.web.util.Tools;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pixiao")
public class PiXiaoController{/*
	private static Logger log = LoggerFactory.getLogger(PiXiaoController.class);
	
	String ERROR = "common/error";
	String pxlendPage = "yc/px_zgdLend";
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		return usronlyId;
	}
	
	*//**
	 * 给批销订单组用的接口
	 * 判断掌柜是否可以使用掌柜贷  jsonp
	 * @return
	 *//*
	@RequestMapping(value="/px_checkPXLend", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkPXLend(HttpServletRequest request) {
		try {
			String applyAmount = request.getParameter("applyAmount");
			String userOnlyId = request.getParameter("userOnlyId");
			log.info("checkPXLend userOnlyId=" + userOnlyId);
//			String userOnlyId=getUserOnlyId(request);
			String result=WildflyBeanFactory.getPXZgdClient().checkPXLend(userOnlyId,applyAmount);
			log.info("checkPXLend " + result);
			return result;
		} catch (Exception e) {
			log.error("checkPXLend error",e);
			return JsonResult.getInstance().addError("系统异常").toString();
		}
	}
	
	*//**
	 * 给批销订单组调用
	 * 跳转批销借款页面
	 * @return
	 *//*
	@RequestMapping("px_toPXLendPage")
	public String toPXLendPage(HttpServletRequest request) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("toPXLendPage userOnlyId=" + userOnlyId);
			String pxNo = request.getParameter("pxNo");
			String applyAmount = request.getParameter("applyAmount");
			log.info("toPXLendPage pxNo=" + pxNo+";applyAmount="+applyAmount);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("pxNo", pxNo);
			map.put("applyAmount", applyAmount);
			String result=WildflyBeanFactory.getPXZgdClient().queryPXLendInfo(map);
			log.info("toPXLendPage " + result);
			JSONObject js=JSONObject.fromObject(result);
			request.setAttribute("result", js);
			return pxlendPage;
		} catch (Exception e) {
			log.error("toPXLendPage error",e);
			return ERROR;
		}
	}
	*//**
	 * 批销借款页还款计划测算  jsonp
	 * @return
	 *//*
	@RequestMapping("px_queryPXRepayPlan")
	@ResponseBody
	public JSONPObject queryPXRepayPlan(HttpServletRequest request,@RequestParam String jsoncallback) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryPXRepayPlan userOnlyId=" + userOnlyId);
			String fixedRepayDate = request.getParameter("fixedRepayDate");
			String applyAmount = request.getParameter("applyAmount");
			String repayType = request.getParameter("repayType");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("applyAmount", applyAmount);
			map.put("fixedRepayDate", fixedRepayDate);
			map.put("repayType", repayType);
			String result=WildflyBeanFactory.getPXZgdClient().queryPXRepayPlan(map);
			log.info("queryPXRepayPlan " + result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPXRepayPlan error",e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	*//**
	 * 批销借款确认
	 * @return
	 *//*
	@RequestMapping("px_confirmPXLoanApply")
	@ResponseBody
	public JSONPObject confirmPXLoanApply(HttpServletRequest request,@RequestParam String jsoncallback) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("confirmPXLoanApply  userOnlyId=" + userOnlyId);
			String applyId=Tools.getIpAddr(request);
			String contractVer="30001";
			String pxNo = request.getParameter("pxNo");
			String applyAmount = request.getParameter("applyAmount");
			String fixedRepayDate = request.getParameter("fixedRepayDate");
			String repayType = request.getParameter("repayType");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("contractVer", contractVer);
			map.put("applyId", applyId);
			map.put("pxNo", pxNo);
			map.put("applyAmount", applyAmount);
			map.put("fixedRepayDate", fixedRepayDate);
			map.put("repayType", repayType);
			String result = WildflyBeanFactory.getPXZgdClient().confirmPXLoanApply(map);
			log.info("confirmPXLoanApply result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confirmPXLoanApply error",e);
			return new JSONPObject(jsoncallback,e);
		}
	}
*/}
