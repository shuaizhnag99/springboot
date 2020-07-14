package com.ule.uhj.pixiao.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ule.uhj.util.CommonHelper;

@Controller
@RequestMapping("/pixiao")
public class PiXiaoTuiKuanController{
	private static Logger log = LoggerFactory.getLogger(PiXiaoTuiKuanController.class);
	

	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		return usronlyId;
	}
//
//	/**
//	 * 查询退款结果
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("")
//	@ResponseBody
//	public String queryMoneyBack(HttpServletRequest request) {
//		try {
//			
//			String result = WildflyBeanFactory.getPiXiaoBusinessClient().queryPiXiaoPlan(getUserOnlyId(request), request.getParameter("dueId"));
//			return null;
//		} catch (Exception e) {
//			log.error("queryMoneyBack Error", e);
//			return null;
//		}
//	}

//	/**
//	 * 退款
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("moneyBack")
//	@ResponseBody
//	public String moneyBack(HttpServletRequest request) {
//		try {
////			String userOnlyId=request.getParameter("userOnlyId");
////			String  payId=request.getParameter("payId");
////			String  payDate=request.getParameter("payDate");
////			String  channel=request.getParameter("channel");
////			String[] str=getParamsFromRequest(request);
////			LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
//////			piXiaoPayVerfiy(request,channel,str);
////			return client.paymentResultsQuery(userOnlyId, payId, payDate);
//			return  null;
//		} catch (Exception e) {
//			log.error("queryPlans Error", e);
//			return null;
//		}
//	}
	private String[] getParamsFromRequest(HttpServletRequest request) {
		List<String> paramList = new ArrayList<String>();
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String param = paramNames.nextElement().toString();
			if(!"sign".equals(param)){
				paramList.add(param);
			}
		}
		String[] str = (String[]) paramList.toArray(new String[paramList.size()]);
		return str;
	}
}
