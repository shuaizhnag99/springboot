package com.ule.uhj.yzs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.framework.util.DateUtil;
import com.ule.uhj.dto.zgd.PromoterFeedbackDto;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.cs.PromoterFeedbackClient;
import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
import com.ule.uhj.util.CookieUtil;
import com.ule.uhj.util.DesUtil;
import com.ule.uhj.util.StringUtil;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.YzsResponse;
import com.ule.uhj.yzs.enums.TaskTypeEnum;
import com.ule.uhj.yzs.enums.YzsResponeCodeEnum;

@Controller
@RequestMapping("/promoter")
public class DailyTaskFeedbackController {
	private static Logger log = LoggerFactory.getLogger(DailyTaskFeedbackController.class);
	
	private static final Map<String, String> viewName=new HashMap<String, String>();
	static{
		viewName.put("0","isWish");
		viewName.put("88","useTime" );
		viewName.put("12","isAnewApply" );
		viewName.put("777","overdue" );
		viewName.put("888","incomeReduce");
		viewName.put("999","isWish");
	}
	@RequestMapping("/toTask")
	@ResponseBody
	public JSONPObject choosePage(HttpServletRequest request, @RequestParam String jsoncallback) {
		Map<String,Object> dataMap = new HashMap<String, Object>();	
		YzsResponse responseDto = YzsResponse.responeSuccess();
		try {
			Map<String, Object> reqMap = StringUtil.getRequestMap(request);
			String userOnlyId=(String) reqMap.get("userOnlyId");//掌柜ID[已加密]
			String tempType=(String) reqMap.get("taskType");//任务类型[小崔专属参数，非数据表中定义的任务类型]
			String taskNo=(String) reqMap.get("taskNo");//任务编号
			//查询该任务是否已反馈
			PromoterFeedbackClient pfClient=WildflyBeanFactory.getPromoterFeedbackClient();
			if (!pfClient.feedbackIsExists(taskNo)) {
				responseDto.setCode(YzsResponeCodeEnum.FEEDBACK_REPAET_ERROR.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.FEEDBACK_REPAET_ERROR.getMessage());
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(responseDto));
			}

			//跳转至想要页面
			BangZGClient client=WildflyBeanFactory.getBangZGClient();
			Map<String, Object> resultMap=client.zgdPopularize(DesUtil.decrypt(userOnlyId),tempType);
			String code=(String) resultMap.get("code");
			if ("00".equals(code)) {
				responseDto.setCode(YzsResponeCodeEnum.OVERAMOUNT_REPAYED.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.OVERAMOUNT_REPAYED.getMessage());
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(responseDto));
			}else if("9999".equals(code)){
				throw new Exception("服务调用异常");
			}
			dataMap.put("taskNo", taskNo);
			dataMap.put("userOnlyId", userOnlyId);
			dataMap.put("month", resultMap.get("month"));
			dataMap.put("address", resultMap.get("address"));
			dataMap.put("storeName", resultMap.get("storeName"));
			dataMap.put("shouldRepayAmt", resultMap.get("shouldRepayAmt"));
			dataMap.put("status", resultMap.get("status"));
			String status=(String) resultMap.get("status");
			dataMap.put("view", viewName.get(status));
			responseDto.setDataMap(dataMap);
			log.info("DailyTaskFeedbackController choosePage,dataMap:" + dataMap.toString());
			return  new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(responseDto));
		} catch (Exception e) {
			responseDto.setCode(YzsResponeCodeEnum.EXCEPTION.getCode());
			responseDto.setMessage(YzsResponeCodeEnum.EXCEPTION.getMessage());
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(responseDto));
		}
	}
	
	@RequestMapping("/feedback")
	@ResponseBody
	public JSONPObject feedback(HttpServletRequest request, @RequestParam String jsoncallback){
		try{
			String context = request.getParameter("context");
			log.info("DailyTaskFeedbackController feedback,context:" + context);
			YzsResponse responseDto = YzsResponse.responeSuccess();
			//解析请求参数
			if (StringUtils.isBlank(context)) {
				log.error("DailyTaskFeedbackController feedback request context isBlank");
				responseDto.setCode(YzsResponeCodeEnum.JSON_DATA_NULL.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.JSON_DATA_NULL.getMessage());
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(responseDto));
			}
			context=StringUtil.decodeStr(context);
			Map<String, Object> reqMap = (Map<String, Object>)UhjWebJsonUtil.parseObject(context);
			
			//判断是否已入库
			String taskNo=(String) reqMap.get("taskNo");//任务编号
				
			// 查询该任务是否已反馈
			PromoterFeedbackClient pfClient = WildflyBeanFactory.getPromoterFeedbackClient();
			if (!pfClient.feedbackIsExists(taskNo)) {
				log.error("DailyTaskFeedbackController feedbackIsExists error");
				responseDto.setCode(YzsResponeCodeEnum.FEEDBACK_REPAET_ERROR.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.FEEDBACK_REPAET_ERROR.getMessage());
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(responseDto));
			}

			// 入库反馈信息
			Integer taskType = Integer.parseInt(reqMap.get("taskType")
					.toString());// 业务类型
			if (!TaskTypeEnum.containsCode(taskType)) {
				log.error("DailyTaskFeedbackController feedback invalid taskType");
				responseDto.setCode(YzsResponeCodeEnum.CLIENT_NOT_FOUND.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.CLIENT_NOT_FOUND.getMessage());
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(responseDto));
			}

			PromoterFeedbackDto feedbackDto=UhjWebJsonUtil.getForm(context,PromoterFeedbackDto.class);
			feedbackDto.setUserOnlyId(DesUtil.decrypt(feedbackDto.getUserOnlyId()));
			String mobileCookie = CookieUtil.getCookie(request, "mobileCookie");
			String promoterId=CookieUtil.cookieDec("mobileCookie", "yzs", mobileCookie);//地推人员ID
			feedbackDto.setPromoterId(promoterId);
			
			if (TaskTypeEnum.OVERDUE_WARN.getCode().equals(taskType)) {
				responseDto =overdueWarn(request, feedbackDto);
			}else if(TaskTypeEnum.INCOME_REDUCE.getCode().equals(taskType)){
				responseDto=incomeReduce(request, feedbackDto);
			}else{
				responseDto=generalize(request, feedbackDto);
			}
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(responseDto));
		}catch(Exception e){
			log.error("DailyTaskFeedbackController feedback error", e);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}	
	}
	
	/**
	 * 逾期提醒
	 * @param request
	 * @param content
	 * @return
	 */
	private YzsResponse overdueWarn(HttpServletRequest request,PromoterFeedbackDto feedbackDto){
		YzsResponse responseDto = YzsResponse.responeSuccess();
		try {
			if (null==feedbackDto || StringUtil.isEmpty(feedbackDto.getFeedbackContent())
					|| feedbackDto.getFeedbackContent().length()<5) {
				log.error("PromoterFeedbackController overdueWarn invalid FeedbackContent,userOnlyId:"
					+(feedbackDto!=null?feedbackDto.getUserOnlyId():"")
					+",promoterId:"+(feedbackDto!=null?feedbackDto.getPromoterId():"")
					+",FeedbackContent:"+(feedbackDto!=null?feedbackDto.getFeedbackContent():""));
				responseDto.setCode(YzsResponeCodeEnum.FEEDBACK_INFO_ERROR.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.FEEDBACK_INFO_ERROR.getMessage());
				return responseDto;
			}
			
			feedbackDto.setCreateTime(DateUtil.currentDateString("yyyy-MM-dd hh:mm:ss"));
			
			PromoterFeedbackClient client=WildflyBeanFactory.getPromoterFeedbackClient();
			client.savePromoterFeedback(feedbackDto);
			return responseDto;
		} catch (Exception e) {
			log.error("DailyTaskFeedbackController overdueWarn save error！", e);
			return YzsResponse.responeError();
		}
	}

	
	/**
	 * 经营业绩下降
	 * @param request
	 * @param content
	 * @return
	 */
	private YzsResponse incomeReduce(HttpServletRequest request,PromoterFeedbackDto feedbackDto){
		YzsResponse responseDto = YzsResponse.responeSuccess();
		try {
			if (null==feedbackDto || StringUtil.isEmpty(feedbackDto.getFeedbackContent())
					|| feedbackDto.getFeedbackContent().length()<5) {
				log.error("PromoterFeedbackController incomeReduce invalid FeedbackContent,userOnlyId:"
					+(feedbackDto!=null?feedbackDto.getUserOnlyId():"")
					+",promoterId:"+(feedbackDto!=null?feedbackDto.getPromoterId():"")
					+",FeedbackContent:"+(feedbackDto!=null?feedbackDto.getFeedbackContent():""));
				responseDto.setCode(YzsResponeCodeEnum.FEEDBACK_INFO_ERROR.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.FEEDBACK_INFO_ERROR.getMessage());
				return responseDto;
			}
			
			feedbackDto.setCreateTime(DateUtil.currentDateString("yyyy-MM-dd hh:mm:ss"));
			
			PromoterFeedbackClient client=WildflyBeanFactory.getPromoterFeedbackClient();
			client.savePromoterFeedback(feedbackDto);
			return responseDto;
		} catch (Exception e) {
			log.error("DailyTaskFeedbackController incomeReduce save error！", e);
			return YzsResponse.responeError();
		}
	}
	
	
	/**
	 * 推广反馈
	 * @param request
	 * @param content
	 * @return
	 */
	private YzsResponse generalize(HttpServletRequest request,PromoterFeedbackDto feedbackDto){
		YzsResponse responseDto = YzsResponse.responeSuccess();
		try {
			if (null==feedbackDto || StringUtil.isEmpty(feedbackDto.getLoanApplyStatus())) {
				log.error("PromoterFeedbackController generalize invalid LoanApplyStatus,userOnlyId:"
					+(feedbackDto!=null?feedbackDto.getUserOnlyId():"")
					+",promoterId:"+(feedbackDto!=null?feedbackDto.getPromoterId():"")
					+",LoanApplyStatus:"+(feedbackDto!=null?feedbackDto.getLoanApplyStatus():""));
				responseDto.setCode(YzsResponeCodeEnum.FEEDBACK_INFO_ERROR.getCode());
				responseDto.setMessage(YzsResponeCodeEnum.FEEDBACK_INFO_ERROR.getMessage());
				return responseDto;
			}
			
			feedbackDto.setCreateTime(DateUtil.currentDateString("yyyy-MM-dd hh:mm:ss"));
			PromoterFeedbackClient client=WildflyBeanFactory.getPromoterFeedbackClient();
			client.savePromoterFeedback(feedbackDto);
			return responseDto;
		} catch (Exception e) {
			log.error("DailyTaskFeedbackController generalize save error！", e);
			return YzsResponse.responeError();
		}
	}

}
