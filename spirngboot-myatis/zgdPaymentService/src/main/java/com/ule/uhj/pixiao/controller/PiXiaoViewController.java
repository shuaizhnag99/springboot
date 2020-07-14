package com.ule.uhj.pixiao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.pixiao.PiXiaoBusinessClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.YzsResponse;


@Controller
@RequestMapping("/pixiao")
public class PiXiaoViewController{
	private static Logger log = LoggerFactory.getLogger(PiXiaoViewController.class);
	private static final String JSONP_CALL_BACK = "jsoncallback";
	
	/*====================已出账单 begin =====================================*/
	/**
	 * 概览页面已出账单  账单明细查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/px_signBillDetail", produces = "application/json; charset=utf-8")
	public JSONPObject signBillDetail(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("PiXiaoViewController signBillDetail begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000021960";//
			log.info("userOnlyId====>"+usronlyId);
			String dueIds=request.getParameter("dueIds");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("dueIds", dueIds);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.signBillDetail(map);
			log.info("PiXiaoViewController signBillDetail result  -- >"+result);
			return new JSONPObject(callback,result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	/**
	 * 概览页面 已出账单 提前还款
	 * @return
	 */
	@RequestMapping("/px_confirmSignEarlyRepay")
	@ResponseBody
	public JSONPObject confirmSignEarlyRepay(HttpServletRequest request, HttpServletResponse response) {
		log.info("PiXiaoViewController confirmSignEarlyRepay begin  ====");
		String jsoncallback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			String dueIds=request.getParameter("dueIds");
			String loanType=request.getParameter("loanType");
			String preRepayAmount=request.getParameter("preRepayAmount");//归还本息金额（元）
			String preRepayAmount1=request.getParameter("preRepayAmount1");//提前归还本金金额（元）
			String presentCapitalBalance=request.getParameter("presentCapitalBalance");//当前期本金
			String overdueCapitalBalance=request.getParameter("overdueCapitalBalance");//贷款拖欠本金
			String normalInterest=request.getParameter("normalInterest");//正常利息
			String overdueInterest=request.getParameter("overdueInterest");//拖欠利息
			String defaultInterest=request.getParameter("defaultInterest");//罚息
			String repayInter=request.getParameter("repayInter");//利息
			String preRepayKind="2";//提前还款类型 2--全部结清
			log.info("PiXiaoViewController confirmPxEarlyPlans  userOnlyId=" +userOnlyId+";dueIds="+dueIds);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueIds", dueIds);
			map.put("preRepayAmount", preRepayAmount);
			map.put("preRepayAmount1", preRepayAmount1);
			map.put("preRepayKind", preRepayKind);
			map.put("presentCapitalBalance", presentCapitalBalance);
			map.put("overdueCapitalBalance", overdueCapitalBalance);
			map.put("normalInterest", normalInterest);
			map.put("overdueInterest", overdueInterest);
			map.put("defaultInterest", defaultInterest);
			map.put("loanType", loanType);
			map.put("repayInter", repayInter);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String  result=client.signEarlyRepay(map);
			log.info("PiXiaoViewController confirmSignEarlyRepay result  ===="+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("PiXiaoViewController confirmSignEarlyRepay Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 概览页面 已出账单 提前还款查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/px_querySignPreRepay", produces = "application/json; charset=utf-8")
	public JSONPObject querySignPreRepay(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("PiXiaoViewController querySignPreRepay begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000021960";//
			String dueIds=request.getParameter("dueIds");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", usronlyId);
			map.put("dueIds", dueIds);
			
			log.info("userOnlyId==="+usronlyId+" dueIds=="+dueIds);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.querySignEarlyRepay(map);
			log.info("PiXiaoViewController querySignPreRepay result  -- >"+result);
		
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	
	/*====================已出账单 end  =====================================*/
	
/**====================未出账单部分   begin=============================*/	
	
	/**
	 * 概览页面未出账单  账单明细查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/px_unOutBillDetail", produces = "application/json; charset=utf-8")
	public JSONPObject unOutBillDetail(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("PiXiaoViewController unOutBillDetail begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000021960";//
			log.info("userOnlyId====>"+usronlyId);
			String orderId=request.getParameter("dueId");
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.unOutBillDetail(orderId);
			log.info("PiXiaoViewController unOutBillDetail result  -- >"+result);
			return new JSONPObject(callback,result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}


	/**
	 * 概览页面 未出账单 提前还款查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/px_preRepay", produces = "application/json; charset=utf-8")
	public JSONPObject queryPreRepay(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("PiXiaoViewController preRepay begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000021960";//
			log.info("userOnlyId====>"+usronlyId);
			String loanType=request.getParameter("loanType");
			String orderId=request.getParameter("dueId");
			log.info("loanType==="+loanType+"dueId=="+orderId);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.preReapy(usronlyId, loanType, orderId);
			log.info("PiXiaoViewController preRepay result  -- >"+result);
		
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	/**
	 * 概览页面 未出账单 提前还款
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/px_sendPreRepay", produces = "application/json; charset=utf-8")
	public JSONPObject sendPreRepay(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("PiXiaoViewController sendPreRepay begin  -- >"+request);
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			String dueId=request.getParameter("dueId");
			String loanType=request.getParameter("loanType");
			String preRepayAmount=request.getParameter("preRepayAmount");//归还本息金额（元）
			String preRepayAmount1=request.getParameter("preRepayAmount1");//提前归还本金金额（元）
			String presentCapitalBalance=request.getParameter("presentCapitalBalance");//当前期本金
			String overdueCapitalBalance=request.getParameter("overdueCapitalBalance");//贷款拖欠本金
			String normalInterest=request.getParameter("normalInterest");//正常利息
			String overdueInterest=request.getParameter("overdueInterest");//拖欠利息
			String defaultInterest=request.getParameter("defaultInterest");//罚息
			String raPrincOver=request.getParameter("raPrincOver");//罚息
			String raInterOver=request.getParameter("raInterOver");//罚息
			String repayInter=request.getParameter("repayInter");//利息
			String username=request.getParameter("userName");//罚息
			
			String preRepayKind="2";//提前还款类型 2--全部结清
			log.info("confirmEarlyPlans  userOnlyId=" +userOnlyId+";dueId="+dueId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			map.put("preRepayAmount", preRepayAmount);
			map.put("preRepayAmount1", preRepayAmount1);
			map.put("preRepayKind", preRepayKind);
			map.put("presentCapitalBalance", presentCapitalBalance);
			map.put("overdueCapitalBalance", overdueCapitalBalance);
			map.put("normalInterest", normalInterest);
			map.put("overdueInterest", overdueInterest);
			map.put("defaultInterest", defaultInterest);
			map.put("loanType", loanType);
			map.put("raPrincOver", raPrincOver);
			map.put("raInterOver", raInterOver);
			map.put("userName", username);
			map.put("repayInter", repayInter);
			log.info("PiXiaoViewController sendPreRepay map  -- >"+map);
			String result = WildflyBeanFactory.getPiXiaoBusinessClient().sendPreReapy(map);
			log.info("PiXiaoViewController sendPreRepay result  -- >"+result);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	/**
	 * 概览页面未出账单查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryPxUnOutBill", produces = "application/json; charset=utf-8")
	public JSONPObject queryPxUnOutBill(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("queryPxUnOutBill begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000021960";//
			log.info("userOnlyId====>"+usronlyId);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.queryPxUnOutBill(usronlyId);
			log.info("queryPxUnOutBill result  -- >"+result);
			return new JSONPObject(callback,result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	/**==================未出账单 finish===================================*/
	/**
	 * 概览页面已出账单查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryPxOrder", produces = "application/json; charset=utf-8")
	public JSONPObject queryPxOrder(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("queryPxOrder begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000000391";//
			log.info("userOnlyId====>"+usronlyId);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.queryPxOrder(usronlyId);
			log.info("queryPxOrder result  -- >"+result);
			return new JSONPObject(callback,result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
	
	/**
	 * 概览页面还款查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryPxRepayInfo", produces = "application/json; charset=utf-8")
	public JSONPObject queryPxRepayInfo(HttpServletRequest request, HttpServletResponse response){
		String callback = request.getParameter(PiXiaoViewController.JSONP_CALL_BACK);
		log.info("queryPxOrder begin  -- >");
		try {
			String usronlyId =CommonHelper.getUserOnlyId(request);//"10000000391";//
			String orderId=request.getParameter("orderId");
			log.info("userOnlyId====>"+usronlyId+" orderId=="+orderId+" request.getParameterMap()=="+request.getParameterMap().values()+request.getParameterMap().keySet());
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			String result=client.unOutBillDetail(orderId);
			return new JSONPObject(callback,result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(YzsResponse.responeError()));
		}
	}
}
