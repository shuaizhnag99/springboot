package com.ule.uhj.app.yzs.controller;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.yzs.service.YzsPostmemberService;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/yzs")
public class YzsPostmemberController {
	private static Logger log = LoggerFactory.getLogger(YzsPostmemberController.class);
	@Autowired
	private YzsPostmemberService yzsPostmemberService;
	
	/**
	 * 邮助手跳转页面控制
	 * 0 身份验证页面
	 * 1 邮助手的掌柜贷首页
	 * 其他跳转身份验证页面
	 */
	@RequestMapping("/toYzsPage")
	@ResponseBody
	public JSONPObject toYzsPage(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("toYzsPage begin.");
		try {
			String bangZGId = CommonHelper.getBangZGId(request);
			log.info("toYzsPage bangZGId "+bangZGId);
			String status =yzsPostmemberService.queryYzsPostmemberStatus(bangZGId);
			String result=JsonResult.getInstance().addOk().add("status",status).toString();
			log.info("toYzsPage bangZGId:"+bangZGId+";result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("toYzsPage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 邮助手跳转页面控制
	 * 0 身份验证页面
	 * 1 邮助手的掌柜贷首页
	 * 其他跳转身份验证页面
	 */
	@RequestMapping("/yzsPostmemberPage")
	@ResponseBody
	public JSONPObject yzsPostmemberPage(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("yzsPostmemberPage begin.");
		try {
			String bangZGId = CommonHelper.getBangZGId(request);
			log.info("yzsPostmemberPage bangZGId "+bangZGId);
			String result =yzsPostmemberService.queryYzsPostmemberInfo(bangZGId);
			log.info("yzsPostmemberPage bangZGId:"+bangZGId+";result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("yzsPostmemberPage error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
}
