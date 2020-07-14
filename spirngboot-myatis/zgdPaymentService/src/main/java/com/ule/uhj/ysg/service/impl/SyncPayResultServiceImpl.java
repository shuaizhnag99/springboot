package com.ule.uhj.ysg.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.pixiao.controller.UhjSecurityProperty;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.SecureUtil;
import com.ule.uhj.ysg.service.SyncPayResultService;
@Service
public class SyncPayResultServiceImpl implements SyncPayResultService {
	private static Log log = LogFactory.getLog(SyncPayResultServiceImpl.class);
	@Async
	@Override
	public String syncPayResult(HttpServletRequest request) {
		log.info("syncPayResult begin...");
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			 
			String returnCode=Convert.toStr(request.getParameter("returnCode"));
			String returnMsg=Convert.toStr(request.getParameter("returnMsg"));
			String merchantId=Convert.toStr(request.getParameter("merchantId"));
			String sign=Convert.toStr(request.getParameter("sign"));
			String reqNo=Convert.toStr(request.getParameter("reqNo"));
			String payedAmount=Convert.toStr(request.getParameter("payedAmount"));
			String payStatus=Convert.toStr(request.getParameter("payStatus"));
			String payResult=Convert.toStr(request.getParameter("payResult"));
			String payDetail=Convert.toStr(request.getParameter("payDetail"));			
			String attach=Convert.toStr(request.getParameter("attach"));
			String errorCode=Convert.toStr(request.getParameter("errorCode"));
			String errorCodeDesc=Convert.toStr(request.getParameter("errorCodeDesc"));
			if(StringUtils.isNotBlank(merchantId)) {
				map.put("merchantId", merchantId);
			}
			if(StringUtils.isNotBlank(returnCode)) {
				map.put("returnCode", returnCode);
			}
			if(StringUtils.isNotBlank(returnMsg)) {
				map.put("returnMsg", returnMsg);
			}
			if(StringUtils.isBlank(sign)) {
				log.info("syncPayResult sign is null cannot sync "+reqNo);
				return JsonResult.getInstance().addError("syncPayResult sign is null ").toJsonStr();
			}
			if(StringUtils.isNotBlank(reqNo)) {
				map.put("paymentFlowId", reqNo);
				int st=WildflyBeanFactory.getYsgPayAndBillClient().checkPayStatus(map);
				if(2!=st) {
					log.info("syncPayResult status cannot sync "+reqNo);
					return JsonResult.getInstance().addError("syncPayResult status cannot sync status is "+st).toJsonStr();
				}
				
			}else {
				log.info("syncPayResult error reqNo is null");
				return JsonResult.getInstance().addError("syncPayResult error reqNo is null ").toJsonStr();
			}
			if(StringUtils.isNotBlank(payedAmount)) {
				map.put("payedAmount", payedAmount);
			}
			if(StringUtils.isNotBlank(payStatus)) {
				map.put("payStatus", payStatus);
			}	
			if(StringUtils.isNotBlank(payResult)) {
				map.put("payResult", payResult);
			}
			if(StringUtils.isNotBlank(payDetail)) {
				map.put("payDetail", payDetail);
			}
			if(StringUtils.isNotBlank(attach)) {
				map.put("attach", attach);
			}
			if(StringUtils.isNotBlank(errorCode)) {
				map.put("errorCode", errorCode);
			}
			if(StringUtils.isNotBlank(errorCodeDesc)) {
				map.put("errorCodeDesc", errorCodeDesc);
			}
			log.info(String.format("syncPayResult map %s", map.toString()));
		 
			String scret=UhjSecurityProperty.get(merchantId);
			
			if(StringUtils.isBlank(scret)||!SecureUtil.verify(map, scret, sign)){
				log.info("syncPayResult  error 验签失败");
				return JsonResult.getInstance().addError("验签失败").toString();
			};
			if(StringUtils.isNotBlank(returnCode)&&"SUCCESS".equals(returnCode.toUpperCase())) {
				log.info("syncPayResult ejb begin...");
				String re=WildflyBeanFactory.getYsgPayAndBillClient().syncPayResult(map);
				log.info("syncPayResult ejb result"+re);
			}
		} catch (Exception e) {
			 log.error("syncPayResult error", e);
//			e.printStackTrace();
		}
		log.info("queryAvailableBalance  end...");
		return JsonResult.getInstance().addOk().toJsonStr();
	}
	
	
	@Async
	@Override
	public String syncRefundResult(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		 
		try {
			String returnCode = Convert.toStr(request.getParameter("returnCode"));
			String returnMsg = Convert.toStr(request.getParameter("returnMsg"));
			String merchantId = Convert.toStr(request.getParameter("merchantId"));
			String sign = Convert.toStr(request.getParameter("sign"));
			String refundReqNo = Convert.toStr(request.getParameter("refundReqNo"));
			String refundAmount = Convert.toStr(request.getParameter("refundAmount"));
			String refundStatus = Convert.toStr(request.getParameter("refundStatus"));
			String refundResult = Convert.toStr(request.getParameter("refundResult"));
			String errorCode = Convert.toStr(request.getParameter("errorCode"));
			String errorCodeDesc = Convert.toStr(request.getParameter("errorCodeDesc"));
			map.put("returnCode", returnCode);
			map.put("returnMsg", returnMsg);
			map.put("merchantId", merchantId);
//			map.put("sign", sign);
			map.put("refundReqNo", refundReqNo);
			map.put("refundAmount", refundAmount);
			map.put("refundStatus", refundStatus);
			map.put("refundResult", refundResult);
			map.put("errorCode", errorCode);
			map.put("errorCodeDesc", errorCodeDesc);
			
			int st=WildflyBeanFactory.getYsgPayAndBillClient().checkRefundStatus(map);
			if(2!=st) {
				log.info("syncRefundResult status cannot sync "+refundReqNo);
				return JsonResult.getInstance().addError("syncRefundResult status cannot sync status is "+st).toJsonStr();
			}
			
			//检验状态是否可以退款更新
			log.info(String.format("syncRefundResult map %s", map.toString()));
             String scret=UhjSecurityProperty.get(merchantId);
			
			if(StringUtils.isBlank(scret)||!SecureUtil.verify(map, scret, sign)){
				log.info("syncRefundResult  error 验签失败");
				return JsonResult.getInstance().addError("验签失败").toString();
			}
			if (StringUtils.isNotBlank(returnCode) && "SUCCESS".equals(returnCode.toUpperCase())) {
				log.info("syncRefundResult ejb begin...");
				String re = WildflyBeanFactory.getYsgPayAndBillClient().YsgRefunNotice(map);
				log.info("syncRefundResult ejb result" + re);
			} 
		} catch (Exception e) {
			log.error("syncRefundResult error",e);
			JsonResult.getInstance().addError("系统异常").toJsonStr();
		}
		return JsonResult.getInstance().addOk().toJsonStr();
	}

}