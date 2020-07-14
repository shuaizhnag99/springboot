package com.ule.uhj.app.zgd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.app.zgd.service.ForecastService;
import com.ule.uhj.util.CommonHelper;

@Controller
@RequestMapping("/forecast")
public class ForecastBusScopeController {
	private static Logger log = LoggerFactory.getLogger(ForecastBusScopeController.class);
	
	@Autowired
	private ForecastService forecastService;
	
	@RequestMapping("/queryVps")
	@ResponseBody
	public JSONPObject queryVps(HttpServletRequest request,@RequestParam String jsonpCallback){
		List<Map<String,String>> re=new ArrayList<Map<String,String>>();
		JSONObject json=new JSONObject();
		try {
			String userOnlyId =CommonHelper.getUserOnlyId(request);//request.getParameter("userOnlyId") ;//
			if(StringUtils.isNotBlank(userOnlyId)){
			 re=	forecastService.getForecastKyeWord(userOnlyId);
			 json.put("data", re);	
			 json.put("code", "0000");
			 json.put("msg", "sucess");
			}else{
				 json.put("data", new ArrayList<Map<String,String>>());	
				 json.put("code", "1001");
				 json.put("msg", "please login");
				
			}
			
			
		} catch (Exception e) {
			log.error("queryVps error", e);
			 json.put("data", new ArrayList<Map<String,String>>());	
			 json.put("code", "1000");
			 json.put("msg", "please try agin");
			return new JSONPObject(jsonpCallback, json);
		}
		 	return new JSONPObject(jsonpCallback,json);
	}
	
}