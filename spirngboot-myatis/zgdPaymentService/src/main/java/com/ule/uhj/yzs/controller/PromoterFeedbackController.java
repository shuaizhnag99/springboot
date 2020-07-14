package com.ule.uhj.yzs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ule.framework.util.DateUtil;
import com.ule.uhj.dto.zgd.PromoterFeedbackDto;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.cs.PromoterFeedbackClient;
import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
import com.ule.uhj.util.CookieUtil;
import com.ule.uhj.util.DesUtil;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.StringUtil;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.yzs.enums.TaskTypeEnum;

/**
 * 邮助手--保存地推人员反馈信息
 * (该类废弃，转到DailyTaskFeedbackController.java,暂时仅做保留--20161220)
 * 
 * @author LIJIANA
 */
@Controller
@RequestMapping("/promoter_feiqi")
public class PromoterFeedbackController {
	private static Logger log = LoggerFactory.getLogger(PromoterFeedbackController.class);
	private static final String ERROR_VIEW="500";
	//0:审核拒绝,88:审核通过,12:已退回,999:未申请,777：逾期,888：经营业绩下滑,500:异常提
	private static final Map<String, String> viewName=new HashMap<String, String>();
	static{
		viewName.put("0","bzg/isWish");
		viewName.put("88","bzg/useTime" );
		viewName.put("12","bzg/isAnewApply" );
		viewName.put("777","bzg/overdue" );
		viewName.put("888","bzg/incomeReduce");
		viewName.put("999","bzg/isWish");
		viewName.put("500","bzg/error");
		
		
	}
	
	//00 逾期
	private static final Map<String, String> codes=new HashMap<String, String>();
	static{
		codes.put("00","该掌柜逾期金额已还清");
		codes.put("500","服务器异常");
	}
	
	@RequestMapping("/toTask")
	public ModelAndView toTaskPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(); 
		try {
			Map<String, Object> reqMap = StringUtil.getRequestMap(request);
			String userOnlyId=(String) reqMap.get("userOnlyId");//掌柜ID[已加密]
			String tempType=(String) reqMap.get("taskType");//任务类型[小崔专属参数，非数据表中定义的任务类型]
			String taskNo=(String) reqMap.get("taskNo");//任务编号
			//查询该任务是否已反馈
			PromoterFeedbackClient pfClient=WildflyBeanFactory.getPromoterFeedbackClient();
			if (!pfClient.feedbackIsExists(taskNo)) {
				mav.addObject("msg", "该任务信息已反馈");
				mav.setViewName(viewName.get(ERROR_VIEW));
				return mav;
			}
			
			
			//跳转至想要页面
			BangZGClient client=WildflyBeanFactory.getBangZGClient();
			Map<String, Object> resultMap=client.zgdPopularize(DesUtil.decrypt(userOnlyId),tempType);
			String code=(String) resultMap.get("code");
			if (codes.containsKey(code)) {
				mav.addObject("msg", codes.get(code));
				mav.setViewName(viewName.get(ERROR_VIEW));
				return mav;
			}
			mav.addObject("taskNo",taskNo);
			mav.addObject("userOnlyId", userOnlyId);
			mav.addObject("month", resultMap.get("month"));
			mav.addObject("address", resultMap.get("address"));
			mav.addObject("storeName", resultMap.get("storeName"));
			mav.addObject("shouldRepayAmt", resultMap.get("shouldRepayAmt"));
			String status=(String) resultMap.get("status");
			mav.addObject("status", resultMap.get("status"));
			mav.setViewName(viewName.get(status));
		} catch (Exception e) {
			log.error("PromoterFeedbackController toTask exception ",e);
			mav.setViewName(viewName.get(ERROR_VIEW));
		}
        return mav;  
	}
	
	/**
	 * 逾期提醒
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/feedback")
	@ResponseBody
	public String feedback(HttpServletRequest request,@RequestBody String content){
		log.info("PromoterFeedbackController feedback,content:" + content);
		//解析请求参数
		if (StringUtils.isBlank(content)) {
			log.error("PromoterFeedbackController feedback request context isBlank");
			return JsonResult.getInstance().addError("请求参数为空").toString();
		}
		JsonResult result=JsonResult.getInstance();//定义结果集
		content=StringUtil.decodeStr(content);
		Map<String, Object> reqMap = (Map<String, Object>)UhjWebJsonUtil.parseObject(content);
		
		//判断是否已入库
		String taskNo=(String) reqMap.get("taskNo");//任务编号
		try {
			//查询该任务是否已反馈
			PromoterFeedbackClient pfClient = WildflyBeanFactory.getPromoterFeedbackClient();
			if (!pfClient.feedbackIsExists(taskNo)) {
				result.addError("该任务信息已反馈");
				return result.toString();
			}
		} catch (Exception e) {
			log.error("PromoterFeedbackController feedbackIsExists error",e);
		}
		
		//入库反馈信息
		Integer taskType=Integer.parseInt(reqMap.get("taskType").toString());//业务类型
		if (!TaskTypeEnum.containsCode(taskType)) {
			log.error("PromoterFeedbackController feedback invalid taskType");
			return JsonResult.getInstance().addError("业务类型无效").toString();
		}
		
		
		PromoterFeedbackDto feedbackDto=UhjWebJsonUtil.getForm(content,PromoterFeedbackDto.class);
		feedbackDto.setUserOnlyId(DesUtil.decrypt(feedbackDto.getUserOnlyId()));
		String mobileCookie = CookieUtil.getCookie(request, "mobileCookie");
		String promoterId=CookieUtil.cookieDec("mobileCookie", "yzs", mobileCookie);//地推人员ID
		feedbackDto.setPromoterId(promoterId);
		
		if (TaskTypeEnum.OVERDUE_WARN.getCode().equals(taskType)) {
			result=overdueWarn(request, feedbackDto);
		}else if(TaskTypeEnum.INCOME_REDUCE.getCode().equals(taskType)){
			result=incomeReduce(request, feedbackDto);
		}else{
			result=generalize(request, feedbackDto);
		}
		return result.toString();
	}
	
	/**
	 * 逾期提醒
	 * @param request
	 * @param content
	 * @return
	 */
	private JsonResult overdueWarn(HttpServletRequest request,PromoterFeedbackDto feedbackDto){
		JsonResult result=JsonResult.getInstance();
		try {
			if (null==feedbackDto || StringUtil.isEmpty(feedbackDto.getFeedbackContent())
					|| feedbackDto.getFeedbackContent().length()<5) {
				log.error("PromoterFeedbackController overdueWarn invalid FeedbackContent,userOnlyId:"
					+(feedbackDto!=null?feedbackDto.getUserOnlyId():"")
					+",promoterId:"+(feedbackDto!=null?feedbackDto.getPromoterId():"")
					+",FeedbackContent:"+(feedbackDto!=null?feedbackDto.getFeedbackContent():""));
				return result.addError("掌柜贷逾期提醒，反馈内容有误");
			}
			
			feedbackDto.setCreateTime(DateUtil.currentDateString("yyyy-MM-dd hh:mm:ss"));
			
			PromoterFeedbackClient client=WildflyBeanFactory.getPromoterFeedbackClient();
			client.savePromoterFeedback(feedbackDto);
			
			result.addOk("保存成功");
		} catch (Exception e) {
			log.error("PromoterFeedbackController overdueWarn save error！", e);
			result.addError("保存失败");
		}
		return result;
	}

	
	/**
	 * 经营业绩下降
	 * @param request
	 * @param content
	 * @return
	 */
	private JsonResult incomeReduce(HttpServletRequest request,PromoterFeedbackDto feedbackDto){
		JsonResult result=JsonResult.getInstance();
		try {
			if (null==feedbackDto || StringUtil.isEmpty(feedbackDto.getFeedbackContent())
					|| feedbackDto.getFeedbackContent().length()<5) {
				log.error("PromoterFeedbackController incomeReduce invalid FeedbackContent,userOnlyId:"
					+(feedbackDto!=null?feedbackDto.getUserOnlyId():"")
					+",promoterId:"+(feedbackDto!=null?feedbackDto.getPromoterId():"")
					+",FeedbackContent:"+(feedbackDto!=null?feedbackDto.getFeedbackContent():""));
				return result.addError("掌柜经营业绩下滑提醒，反馈内容有误");
			}
			
			feedbackDto.setCreateTime(DateUtil.currentDateString("yyyy-MM-dd hh:mm:ss"));
			
			PromoterFeedbackClient client=WildflyBeanFactory.getPromoterFeedbackClient();
			client.savePromoterFeedback(feedbackDto);
			
			result.addOk("保存成功");
		} catch (Exception e) {
			log.error("PromoterFeedbackController incomeReduce save error！", e);
			result.addError("保存失败");
		}
		return result;
	}
	
	
	/**
	 * 推广反馈
	 * @param request
	 * @param content
	 * @return
	 */
	private JsonResult generalize(HttpServletRequest request,PromoterFeedbackDto feedbackDto){
		JsonResult result=JsonResult.getInstance();
		try {
			if (null==feedbackDto || StringUtil.isEmpty(feedbackDto.getLoanApplyStatus())) {
				log.error("PromoterFeedbackController generalize invalid LoanApplyStatus,userOnlyId:"
					+(feedbackDto!=null?feedbackDto.getUserOnlyId():"")
					+",promoterId:"+(feedbackDto!=null?feedbackDto.getPromoterId():"")
					+",LoanApplyStatus:"+(feedbackDto!=null?feedbackDto.getLoanApplyStatus():""));
				return result.addError("掌柜推广反馈，借款状态有误");
			}
			
			feedbackDto.setCreateTime(DateUtil.currentDateString("yyyy-MM-dd hh:mm:ss"));
			PromoterFeedbackClient client=WildflyBeanFactory.getPromoterFeedbackClient();
			client.savePromoterFeedback(feedbackDto);
			
			result.addOk("保存成功");
		} catch (Exception e) {
			log.error("PromoterFeedbackController generalize save error！", e);
			result.addError("保存失败");
		}
		return result;
	}
}
