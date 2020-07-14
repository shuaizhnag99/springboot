package com.ule.uhj.sldProxy.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.ule.uhj.sld.model.ContactsInfo;
import com.ule.uhj.sld.model.PhoneInfo;
import com.ule.uhj.sld.model.UserCallLog;
import com.ule.uhj.sld.model.UserGeolocation;
import com.ule.uhj.sldProxy.service.ContactsInfoService;
import com.ule.uhj.sldProxy.service.PhoneInfoService;
import com.ule.uhj.sldProxy.service.UserCallLogService;
import com.ule.uhj.sldProxy.service.UserGeolocationService;
import com.ule.uhj.sldProxy.util.GatherTypeEnum;
import com.ule.uhj.sldProxy.util.JsonResult;
import com.ule.uhj.sldProxy.util.RequestContextBean;
import com.ule.uhj.sldProxy.util.ResultCodeEnum;

/**
 * 收集商家法定代表人手机端的短信内容、通讯录、位置信息
 * @author LIJIAN
 */
@Controller
@RequestMapping("/device")
public class AcquireDeviceInfoController {
	private static Logger log = LoggerFactory.getLogger(AcquireDeviceInfoController.class);
	
	@Autowired
	@Qualifier("userGeolocationService")
	UserGeolocationService service;
	
	@Autowired
	@Qualifier("phoneInfoService")
	PhoneInfoService phoneInfoservice;
	
	@Autowired
	@Qualifier("contactsInfoService")
	ContactsInfoService contactsInfoService;
	
	@Autowired
	@Qualifier("userCallLogService")
	UserCallLogService userCallLogService;
	
	/**
	 * 获取商家移动设备信息Web Service入口
	 * @param content
	 * @return
	 */
	@RequestMapping("/gather")
	@ResponseBody
	public String save(@RequestBody String content){
//		log.info("gather ..." + content);
		//定义结果集
		JsonResult result=new JsonResult();
//		try {
//			//解析请求参数
//			if (StringUtils.isBlank(content)) {
//				log.error("device gather request context isBlank");
//				result.addError(ResultCodeEnum.ERROR.getName());
//				return result.toString();
//			}
//			
//			ObjectMapper mapper = new ObjectMapper();
//			RequestContextBean context = mapper.readValue(content, RequestContextBean.class);
//			if (!GatherTypeEnum.containsKey(context.getType())) {
//				log.error("device gather request context gatherType invalid");
//				result.addError(ResultCodeEnum.ERROR.getName());
//				return result.toString();
//			}
//			String jsonData=JSONUtils.toJSONString(context.getData());//request context
//			log.info("jsonData：" + jsonData);
//			List<Object> objs = null;
//			//业务分发-操作类型【1:获取短信信息、2：获取通信录信息、3：获取设备定位信息、4：获取通话记录信息】
//			switch (context.getType()) {
//			case 1:
//				objs = mapper.readValue(jsonData, ArrayList.class);
//				PhoneInfo info=null;
//				for (Object obj : objs) {
//					info=mapper.readValue(JSONUtils.toJSONString(obj), PhoneInfo.class);
//					phoneInfoservice.save(info);
//				}
//				break;
//			case 2:
//				objs = mapper.readValue(jsonData, ArrayList.class);
//				ContactsInfo contactsInfo=null;
//				for (Object obj : objs) {
//					contactsInfo=mapper.readValue(JSONUtils.toJSONString(obj), ContactsInfo.class);
//					contactsInfoService.save(contactsInfo);
//				}
//				break;
//			case 3:
//				UserGeolocation geolocation=mapper.readValue(jsonData, UserGeolocation.class);
//				service.save(geolocation);
//				break;
//			case 4:
//				objs = mapper.readValue(jsonData, ArrayList.class);
//				UserCallLog uc=null;
//				for (Object obj : objs) {
//					uc=mapper.readValue(JSONUtils.toJSONString(obj), UserCallLog.class);
//					userCallLogService.save(uc);
//				}
//				break;
//			}
//			result.addOk(ResultCodeEnum.SUCCESS.getName());
//		} catch (Exception e) {
//			log.error("device gather info save error", e);
//			result.addError(ResultCodeEnum.ERROR.getName());
//		}
		return result.addOk(ResultCodeEnum.SUCCESS.getName()).toString();
//		return result.toString();
	}
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping("/listPage")
	@ResponseBody
	public String listPage(@RequestBody String content){
		log.info("gather ..." + content);
		//定义结果集
		JsonResult result=new JsonResult();
		try {
			//解析请求参数
			if (StringUtils.isBlank(content)) {
				log.error("device gather request context isBlank");
				result.addError(ResultCodeEnum.ERROR.getName());
				return result.toString();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Integer> context = mapper.readValue(content, Map.class);
			
			List<UserGeolocation> geolocations=service.getListPage(context.get("start"), context.get("pageSize"));
			return mapper.writeValueAsString(geolocations);
		} catch (Exception e) {
			log.error("device gather info save error", e);
			result.addError(ResultCodeEnum.ERROR.getName());
		}
		return result.toString();
	}*/
}
