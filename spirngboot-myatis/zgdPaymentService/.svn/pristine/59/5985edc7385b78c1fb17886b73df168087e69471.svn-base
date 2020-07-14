package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/sign")
public class SignContractController {
	private static Logger log = LoggerFactory.getLogger(SignContractController.class);
	
	/**
	 * 借款合同
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/loanContract")
	@ResponseBody
	public JSONPObject loanContract(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("loanContract begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("loanContract userOnlyId="+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			String result = WildflyBeanFactory.getZgdAppClient().loanContract(map);
			log.info("loanContract userOnlyId:"+userOnlyId+"; result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("loanContract error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**借款支用单
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/contractBill")
	@ResponseBody
	public JSONPObject loanContractBill(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("loanContractBill begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("loanContractBill userOnlyId="+userOnlyId);
			String applyAmount=Convert.toStr(request.getParameter("applyAmount"));
			String periods=Convert.toStr(request.getParameter("periods"));
			String loanRemark=Convert.toStr(request.getParameter("loanRemark"));
			String repayType=Convert.toStr(request.getParameter("repayType"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("applyAmount", applyAmount);
			map.put("periods", periods);
			map.put("loanRemark", loanRemark);
			map.put("repayType", repayType);
			log.info("loanContractBill map:"+map);
			String result = WildflyBeanFactory.getZgdAppClient().loanContractBill(map);
			log.info("loanContractBill userOnlyId:"+userOnlyId+"; result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("loanContractBill error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}

}
