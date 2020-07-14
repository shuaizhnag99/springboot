package com.ule.uhj.app.zgd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.AnnouncementInfoService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.UhjWebJsonUtil;

/**
 * 移动app掌柜贷公告
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/announce")
public class AnnouncementInfoController {
	private static Logger log = LoggerFactory.getLogger(AnnouncementInfoController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private AnnouncementInfoService announcementInfoService;
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId="10000025652";
		return usronlyId;
	}
	
	/**
	 * 查询轮播公告内容
	 */
	@RequestMapping("/queryAnnounceShow")
	@ResponseBody
	public JSONPObject queryAnnounceShow(HttpServletRequest request,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			String userOnlyId = getUserOnlyId(request);
			log.info("queryAnnounceShow userOnlyId:"+userOnlyId);
			if(!Check.isBlank(userOnlyId)){
				List<String> proCodes = new ArrayList<String>();
				proCodes.add("300");
				if(!isGreeSh(userOnlyId)){
					proCodes.add("500");
				}
				log.info("queryAnnounceShow proCodes:"+proCodes.toString());
				resultMap = announcementInfoService.queryAnnounceShow(userOnlyId,proCodes);
				log.info("queryAnnounceShow resultMap:"+resultMap.toString());
				if(resultMap!=null&&resultMap.size()>0){
					resultMap.put("code", "0000");
					resultMap.put("msg", "查询成功");
				}
			}
		}catch(Exception e){
			log.error("queryAnnounceShow error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 查询所有公告内容
	 */
	@RequestMapping("/queryAnnounceList")
	@ResponseBody
	public JSONPObject queryAnnounceList(HttpServletRequest request,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			String userOnlyId = getUserOnlyId(request);
			log.info("queryAnnounceList userOnlyId:"+userOnlyId);
			Map<String,Object> param = new HashMap<String, Object>();
			if(!Check.isBlank(userOnlyId)){
				param.put("userOnlyId", userOnlyId);
				List<String> proCodes = new ArrayList<String>();
				proCodes.add("300");
				if(!isGreeSh(userOnlyId)){
					proCodes.add("500");
				}
				log.info("queryAnnounceList proCodes:"+proCodes.toString());
				resultMap = announcementInfoService.queryAnnounceList(userOnlyId,proCodes);
				log.info("queryAnnounceList resultMap:"+resultMap.toString());
				if(resultMap!=null&&resultMap.size()>0){
					resultMap.put("code", "0000");
					resultMap.put("msg", "查询成功");
				}
			}
		}catch(Exception e){
			log.error("queryAnnounceList error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 保存已读公告记录
	 */
	@RequestMapping("/saveAnnounceRecord")
	@ResponseBody
	public JSONPObject saveAnnounceRecord(HttpServletRequest request,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			String userOnlyId = getUserOnlyId(request);
			String noticeId = Convert.toStr(request.getParameter("noticeId"));
			log.info("saveAnnounceRecord userOnlyId:"+userOnlyId+" noticeId:"+noticeId);
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("userOnlyId", userOnlyId);
			param.put("noticeId", noticeId);
			resultMap = announcementInfoService.saveAnnounceRecord(param);
			log.info("saveAnnounceRecord resultMap:"+resultMap.toString());
		}catch(Exception e){
			log.error("saveAnnounceRecord error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	public boolean isGreeSh(String userOnlyId) throws Exception{
		boolean isGreeSh = false;
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("userOnlyId", userOnlyId);
		param.put("certType", UhjConstant.certType.idcard);
		param=customerInfoService.queryCustomerInfo(param);
		String channelCode=Convert.toStr(param.get("channelCode"));
		if(!Check.isBlank(channelCode) && !UhjConstant.channelCode.ZGD_CHANNEL.equals(channelCode)&& !UhjConstant.channelCode.ZGD_HEBEI_CHANNEL.equals(channelCode)){
			isGreeSh = true;
		}
		return isGreeSh;
	}

}
