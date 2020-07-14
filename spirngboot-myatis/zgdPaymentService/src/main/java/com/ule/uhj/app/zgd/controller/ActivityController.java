package com.ule.uhj.app.zgd.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.yzs.util.ResultUtil;
import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.model.ActivityInfo;
import com.ule.uhj.app.zgd.service.ActivityInfoService;
import com.ule.uhj.app.zgd.service.OrderInfoService;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.UhjWebJsonUtil;

/**
 * 浙江新活动
 * @author weisihua
 * @date 2018年2月1日
 */
@Controller
@RequestMapping("activity")
public class ActivityController {
	
	protected static Log log = LogFactory.getLog(ActivityController.class);
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private ActivityInfoService activityInfoService;
	
	/**
	 * 查询是否展示活动
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryActivityInfo")
	public JSONPObject queryActivityInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法queryActivityInfo");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			//String userOnlyId = "1004046007";
			log.info("userOnlyId是："+userOnlyId);
			Map<String, Object> data = orderInfoService.queryActivityOrderInfo(userOnlyId);
			map.put("data", data);
			map.put("userOnlyId", userOnlyId);
			log.info("退出方法queryActivityInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("queryActivityInfo 执行失败！",e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 保存活动
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveActivityInfo")
	public JSONPObject saveActivityInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法saveActivityInfo");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			//String userOnlyId = "1004046007";
			String amount = request.getParameter("amount");
			log.info("userOnlyId是："+userOnlyId+"传入金额是："+amount);
			ActivityInfo activity = new ActivityInfo();
			if(!StringUtils.isEmpty(amount)) {
				activity.setAmount(new BigDecimal(amount));
			}
			activity.setUserOnlyId(userOnlyId);
			activityInfoService.saveActivityInfo(activity);
			log.info("退出方法saveActivityInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("saveActivityInfo 执行失败！",e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询是否展示活动(浙江)
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkZJActivity")
	public JSONPObject checkZJActivity(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法queryActivityInfo");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			//			String userOnlyId = "1004043738";
			log.info("userOnlyId是："+userOnlyId);
			Map<String, Object> data = orderInfoService.checkZJActivity(userOnlyId);
			map.put("data", data);
			map.put("userOnlyId", userOnlyId);
			log.info("退出方法queryActivityInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("queryActivityInfo 执行失败！",e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 保存活动(浙江)
	 * @param request
	 * @param jsoncallback
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/saveZJActivityInfo")
	public JSONPObject saveZJActivityInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法saveActivityInfo");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);
			//String userOnlyId = "1004043738";
			String amount =Convert.toStr(request.getParameter("amount"),"10") ;
			log.info("userOnlyId是："+userOnlyId+"传入金额是："+amount);
			ActivityInfo activity = new ActivityInfo();
			if(!StringUtils.isEmpty(amount)) {
				activity.setAmount(new BigDecimal(amount));
			}
			activity.setActivityCode("ZJ_20180625");
			activity.setUserOnlyId(userOnlyId);
			activityInfoService.saveActivityInfo(activity);
			log.info("退出方法saveActivityInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("saveActivityInfo 执行失败！",e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
}
