package com.ule.uhj.app.zgd.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.yzs.util.ResultUtil;
import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.model.AutoRepaySettingInfo;
import com.ule.uhj.app.zgd.service.IAutoRepayService;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.SecurityVerfiy;
import com.ule.uhj.util.UhjWebJsonUtil;

/**
 * 自动还款
 * @author weisihua
 * @date 2018年4月12日
 */
@Controller
@RequestMapping("autoRepay")
public class AutoRepayController {

	protected static Log log = LogFactory.getLog(AutoRepayController.class);
	
	@Autowired
	private IAutoRepayService autoRepayService;
	
	/**
	 * 是否已设置自动还款
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("existSetting")
	public JSONPObject existSetting(HttpServletRequest request, @RequestParam String jsoncallback){
		
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法 existSetting()");
		try {
			String userOnlyId =CommonHelper.getUserOnlyId(request);
			AutoRepaySettingInfo info = autoRepayService.queryAutoSettingInfo(userOnlyId);
			if(null != info) {
				map.put("data", true);
			}
			else {
				map.put("data", false);
			}
			map.put("userOnlyId", userOnlyId);
			log.info("退出方法 existSetting() 且方法执行成功！");
		} catch (Exception e) {
			log.error("方法 existSetting() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询自动还款设置信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("querySettingInfo")
	public JSONPObject querySettingInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法 querySettingInfo()");
		try {
			String userOnlyId =CommonHelper.getUserOnlyId(request);
			AutoRepaySettingInfo info = autoRepayService.queryAutoSettingInfo(userOnlyId);
			map.put("data", info);
			log.info("退出方法 querySettingInfo() 且方法执行成功！");
		} catch (Exception e) {
			log.error("方法 querySettingInfo() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 设置自动还款
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveSettingInfo")
	public JSONPObject saveSettingInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法 saveSettingInfo()");
		try {
			String userOnlyId =CommonHelper.getUserOnlyId(request);
			String amount = request.getParameter("amount");
			autoRepayService.saveAutoSettingInfo(userOnlyId, Integer.parseInt(amount));
			log.info("退出方法 saveSettingInfo() 且方法执行成功！");
		} catch (Exception e) {
			log.error("方法 saveSettingInfo() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 关闭还款设置
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("closeSetting")
	public JSONPObject closeSetting(HttpServletRequest request, @RequestParam String jsoncallback){
		
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法 closeSetting()");
		try {
			String userOnlyId =CommonHelper.getUserOnlyId(request);
			autoRepayService.closeAutoSetting(userOnlyId);
			log.info("退出方法 closeSetting() 且方法执行成功！");
		} catch (Exception e) {
			log.error("方法 closeSetting() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 启动自动还款
	 * @return
	 */
	@ResponseBody
	@RequestMapping("start")
	public String start(HttpServletRequest request) {
		log.info("进入方式自动还款 start()");
		try {
			//SecurityVerfiy.securityVerfiy(request, PropertiesHelper.getSecurityKey("uhjLendvpsSecurity"));
			autoRepayService.autoRepay();
			log.info("退出自动还款方法start()！");
		} catch (Exception e) {
			log.error("自动还款start()执行失败原因如下：", e);
			return "error";
		}
		return "success";
	}
}
