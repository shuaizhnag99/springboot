package com.ule.uhj.zgd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.AnnouncementClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.YzsResponse;

/**
 * 掌柜贷公告
 * 公告 {
 * type:公告类型,1:逾期处罚,2:通知
 * title:标题
 * content:内容
 * nounceDate:公告日期
 * isShowFp:是否在首页展示 1:展示,0:不展示
 * status: 1:有效,0:无效
 * }
 * @author zhangyaou
 *
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	private static Logger log = LoggerFactory.getLogger(AnnouncementController.class);
	//jsonpcallback标识符
		private static final String JSONP_CALL_BACK = "jsoncallback";
	/**
	 * 首页加载逾期和通知的title
	 * yiqititle 逾期处罚
	 * tongzhititle 通知标题
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fpnounce", produces = "application/json; charset=utf-8")
	public JSONPObject loadFirstPageAnnounces(HttpServletRequest request, HttpServletResponse response){
		
		String callback = request.getParameter(AnnouncementController.JSONP_CALL_BACK);
		YzsResponse responseDto = YzsResponse.responeSuccess();
		
		log.info("fpnounce -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);
			if(StringUtils.isBlank(usronlyId)){
				responseDto.setMessage("登陆超时，请重新登录");
				responseDto.setCode("10000");
				return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
			}
			AnnouncementClient client = WildflyBeanFactory.getAnnouncementClient();
			Map<String, Object> map=client.queryAnnouncementShow(usronlyId);
			responseDto.setDataMap(map);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}

	/**
	 * 公告查询
	 * @param type
	 * 1:逾期处罚,2:通知
	 * @return
	 * 
	 */
	@RequestMapping(value="/nounces", produces = "application/json; charset=utf-8")
	@ResponseBody
	public JSONPObject loadAnnouncesList(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(AnnouncementController.JSONP_CALL_BACK);
		YzsResponse responseDto = YzsResponse.responeSuccess();
		String usronlyId="";
		try {
			usronlyId =CommonHelper.getUserOnlyId(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseDto.setMessage("登陆超时，请重新登录");
			responseDto.setCode("10000");
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
		}
		List<Object> r = new ArrayList<Object>();
		
		String type= request.getParameter("type");
		try {
			AnnouncementClient client = WildflyBeanFactory.getAnnouncementClient();
			List<Map<String,Object>> list=client.queryAnnouncement(usronlyId,type);
			for(Map<String, Object> line :list){
				r.add(line);
			}
			responseDto.setDataList(r);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	/**
	 * 查询通知详情
	 * @param id 公告id
	 * @return
	 * title
	 * content
	 * picurl
	 */
	@ResponseBody
	@RequestMapping(value="/detail", produces = "application/json; charset=utf-8")
	public JSONPObject nounceDetail(HttpServletRequest request, HttpServletResponse response){
		YzsResponse responseDto = YzsResponse.responeSuccess();
		String callback = request.getParameter(AnnouncementController.JSONP_CALL_BACK);
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseDto.setMessage("登陆超时，请重新登录");
			responseDto.setCode("10000");
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
		}
		String  id=request.getParameter("id");
		//通过id查询通知内容  
//		Map<String, Object> r = testDatas.get(id);
		try {
			AnnouncementClient client = WildflyBeanFactory.getAnnouncementClient();
			Map<String, Object> reMap=client.queryAnnouncementById(id);
			responseDto.setDataMap(reMap);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(responseDto));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	/***
	 * jsonp回调包装
	 * @param pre
	 * @param content
	 * @return
	 */
	private String jsonpCallBack(String pre,String content){
		if(StringUtils.isBlank(pre)){
			return content;
		}else{
			return pre+"("+content+")";
		}
	}
}
