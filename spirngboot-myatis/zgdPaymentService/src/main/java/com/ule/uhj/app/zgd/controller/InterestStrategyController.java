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
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

/**
 * 
 * 手机利息浮动页面
 * @author zhaojie
 *
 */
@Controller
@RequestMapping("/interest")
public class InterestStrategyController{
	private static Logger log = LoggerFactory.getLogger(InterestStrategyController.class);
	
	/**
	 * 借现金首页
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryInterestStrategy")
	@ResponseBody
	public JSONPObject queryInterestStrategy(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("queryInterestStrategy userOnlyId:"+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			result = WildflyBeanFactory.getZgdAppClient().queryInterestStrategy(map);
			log.info("queryInterestStrategy userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryInterestStrategy error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
}
