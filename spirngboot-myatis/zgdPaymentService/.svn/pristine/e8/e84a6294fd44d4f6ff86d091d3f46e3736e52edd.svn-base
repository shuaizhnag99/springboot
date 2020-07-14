package com.ule.uhj.pixiao.controller;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.util.MapSign;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.pixiao.LoanInterfaceClient;
import com.ule.uhj.ejb.client.ycZgd.SendMessageClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.UhjWebSign;
import com.ule.uhj.util.repayPlans.pixiao.PiXiaoDengEBenXi;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pixiao")
public class PiXiaoPayController {
	private static Logger log = LoggerFactory.getLogger(PiXiaoPayController.class);
	String ERROR = "common/error";
//	
//	/**
//	 * 掌柜贷还款接口  wiki地址  http://wiki.uletm.com/pages/viewpage.action?pageId=22303433
//	 * @return
//	 * @author cuitiantian
//	 */
//	@RequestMapping("/repaymentRequest")
//	@ResponseBody
//	public JSONPObject repaymentRequest(HttpServletRequest request,@RequestParam String jsoncallback){
//		try{
//			String userOnlyId="9637";
//			String repayDate=request.getParameter("repayDate");
//			String loanId=request.getParameter("dueId");
//			String planId="0";//request.getParameter("planId");
//			String repayType="1";//request.getParameter("repayType");
//			String ip=request.getRemoteHost();
//			RepayInterfaceClient client = WildflyBeanFactory.getRepayInterfaceClient();
//			String result=client.repayMentRequest(loanId, ip, userOnlyId,planId,repayType);
//			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
//		}catch(Exception e){
//			log.error("repaymentRequest error",e);
//			return new JSONPObject(jsoncallback,e);
//		}
//	}
//	/**
//	 * 掌柜贷还款通知地址   支付组调用 
//	 * @return
//	 * @author cuitiantian
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/repayNotify")
//	public String repayNotify(HttpServletRequest request){
//		try{
//			    String userOnlyId="9637";//getUserOnlyId(request);
//				String param =request.getParameter("message");
//				String paramJson = URLDecoder.decode(param);
//				Map<String,Object> paramMap =JsonUtil.getMapFromJsonString(paramJson);
//				log.info("repayNotify  paramMap ***********"+paramMap);
//				RepayInterfaceClient client = WildflyBeanFactory.getRepayInterfaceClient();
//				paramMap.put("userOnlyId", userOnlyId);
//				client.updateLoanInfo(paramJson,userOnlyId);
//				log.info("request:"+request);
//			    return "redirect:/uhj/zgd_toZgdPage.do";
//		}catch(Exception e){
//			log.error("repaymentRequest error",e);
//			return JsonResult.error("repayNotify error!").toString();
//		}
//	}
	//************************以上是还款时的接口*********************
	/**
	 * @param loanAmount
	 * @return
	 */
	@RequestMapping("/queryPlans.do")
	@ResponseBody
	public String plans(String loanAmount) {
		try {
			Map<String, Object> r = PiXiaoDengEBenXi.createPlansJsonResult(
					new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "12", 3, new BigDecimal(loanAmount),
					BigDecimal.valueOf(0.09));
			return new ObjectMapper().writeValueAsString(r);
		} catch (Exception e) {
			return "{\"code\":\"9999\",\"msg\":\"" + e.getMessage()	+ "\"}";
		}
	}
	
	/**
	 * 调用支付接口验签
	 * @param request
	 * @throws Exception 
	 */
	private void piXiaoPayVerfiy(Map<String,Object> reMap, String channel, String[] verfiyFieldNames) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		if(verfiyFieldNames != null && verfiyFieldNames.length != 0){
			for(String str : verfiyFieldNames){
				map.put(str, Convert.toStr(reMap.get(str)));
			}
		}
		map.put("sign", Convert.toStr(reMap.get("sign")));
		log.info("sign==> "+reMap.get("sign"));
		if(!MapSign.verify(map, UhjSecurityProperty.get(channel))){
			log.info("piXiaoPayVerfiy sign error ============");
			throw new Exception("uhj lendvps sign error!" + map);
		}
	}
	
	
	//-----------------------以下是支付组支付时调用的接口   wiki wiki.uletm.com/pages/viewpage.action?pageId=27564308---------------------------------------
		/**
		 * 余额账户查询接口
		 * @param request
		 * @return
		 */
	    @RequestMapping(value="/queryAvailableBalance", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String queryAvailableBalance(HttpServletRequest request) {
			try {
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("queryAvailableBalance json "+json);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String userOnlyId=(String) map.get("userOnlyId");
				String  merchantId=(String) map.get("merchantId");
				log.info("queryAvailableBalance userOnlyId "+userOnlyId+"merchantId"+merchantId);
				String[] str=getParamsFromRequest(map);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				
				piXiaoPayVerfiy(map,merchantId,str);
				return client.queryBalanceInfo(userOnlyId);
			
			} catch (Exception e) {
				log.error("queryAvailableBalance error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
	
	
	    /**
		 * 还款计划及账户余额查询
		 * @param request
		 * @return
		 */
	    @RequestMapping(value="/queryAccountInformation", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String queryAccountInformation(HttpServletRequest request) {
			try {
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("queryAccountInformation json "+json);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String userOnlyId=(String) map.get("userOnlyId");
				String payAmount=(String) map.get("payAmount");
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				log.info("queryAccountInformation  str " + Arrays.toString(str));
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				piXiaoPayVerfiy(map,merchantId,str);
				return client.queryAccountInformation(userOnlyId, payAmount);
			
			} catch (Exception e) {
				log.error("queryAccountInformation error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		/**
		 * 支付接口
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/zgdPay", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String zgdPay(HttpServletRequest request) {
			log.info("zgdPay begin =========== request="+request);
			try {
				log.info("PiXiaoPayController zgdPay begin =========== ");
				Object  message=request.getParameter("message");
//				log.info("message==="+message);
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("zgdPay json "+json);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  pxNo=(String) map.get("pxNo");
				String  payId=(String) map.get("payId") ;
				String  validatecode=(String) map.get("validateCode") ;
				String userOnlyId=(String) map.get("userOnlyId");
				if(Check.isBlank(pxNo)){
					log.info("zgdPay payId=="+payId+"pxNo=="+pxNo+"userOnlyId=="+userOnlyId);
					return JsonResult.getInstance().addError("不能使用掌柜贷支付！").toString();
				}
				
				String payAmount=(String) map.get("payAmount");
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				piXiaoPayVerfiy(map,merchantId,str);
				return client.paymentNotification(userOnlyId, payAmount, payId,pxNo,validatecode);
			
			} catch (Exception e) {
				log.error("zgdPay error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		/**
		 * 支付接口
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/sendMsg", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String sendMsg(HttpServletRequest request) {
			log.info("sendMsg begin =========== request="+request);
			try {
				log.info("PiXiaoPayController sendMsg begin =========== ");
				String userOnlyId=(String) request.getParameter("userOnlyId");
				log.info("PiXiaoPayController sendMsg begin userOnlyId=========== "+userOnlyId);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				String phone= client.queryPhone(userOnlyId);
				log.info("sendMsg phone ="+phone);
				if(phone!=null){
					log.info("sendMsg -->" + phone);
					String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
					log.info("sendMsg ret:"+ret);
					JSONObject js=JSONObject.fromObject(ret);
					String  mobile=replaceMiddleData(phone, "*");
					return JsonResult.getInstance().addOk().add("phone","请输入"+mobile+"收到的验证码").add("msg", js.get("returnMessage")).toJsonStr();
					
				}else{
					log.info("sendMsg -->手机号是空");
					return JsonResult.getInstance().addError().add("msg","手机号为空").toJsonStr();
				}
				
			} catch (Exception e) {
				log.error("sendMsg error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		public static void main(String[] args) {
			System.out.println(JsonResult.getInstance().addError().add("手机号为空").toJsonStr());
			System.out.println(JsonResult.getInstance().addOk().add("phone","请输入"+"157****1476"+"收到的验证码").add("msg", "成功").toJsonStr()); 
		}
		/**
		 * 替换中间3/1数据
		 * @param dataSuorce 源数据字符串
		 * @param singleReplaceChar  单个替换符（此值为空，默认用*代替）
		 * @return  替换后的字符串
		 */
		public static String replaceMiddleData(String dataSuorce,String singleReplaceChar){
			if(StringUtils.isEmpty(dataSuorce) 
					||dataSuorce.length()<3){
				return dataSuorce;
			}
			if(StringUtils.isEmpty(singleReplaceChar)){
				singleReplaceChar ="*";
			}
			
			StringBuffer replaceChar = new StringBuffer();
			int totalChar = dataSuorce.length()/3;
			for(int i = 0 ;i< totalChar;i++){
				replaceChar.append(singleReplaceChar);
				
			}
			int remainder = dataSuorce.length()%3;
			int startPosition = totalChar;
			if(remainder > 1 && totalChar >0){
				startPosition++;
			}
			return new StringBuffer(dataSuorce).replace(startPosition, startPosition+totalChar, replaceChar.toString()).toString();
		}
		/**
		 * 支付结果查询
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/queryPayResult", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String queryPayResult(HttpServletRequest request) {
			try {
				log.info("PiXiaoPayController queryPayResult begin =========== ");
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String userOnlyId=(String) map.get("userOnlyId");
				String  payId=(String) map.get("payId");
				String  payDate=(String) map.get("payDate");
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				piXiaoPayVerfiy(map,merchantId,str);
				return client.paymentResultsQuery(userOnlyId, payId, payDate);
			
			} catch (Exception e) {
				log.error("queryPayResult error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		
		private String[] getParamsFromRequest(Map<String,Object> map) {
			List<String> paramList = new ArrayList<String>();
			 for(Entry<String, Object> entry : map.entrySet()){
				 if(!"sign".equals(entry.getKey())){
						paramList.add(entry.getKey());
					}
			 }
			String[] str = (String[]) paramList.toArray(new String[paramList.size()]);
			return str;
		}
		/**
		 * 退款
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/moneyBack", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String moneyBack(HttpServletRequest request) {
			try {
				log.info("PiXiaoPayController moneyBack begin =========== ");
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				piXiaoPayVerfiy(map,merchantId,str);
				return client.moneyBack(map);
			} catch (Exception e) {
				log.error("moneyBack Error", e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		/**
		 * 退款
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/queryMoneyBack", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String queryMoneyBack(HttpServletRequest request) {
			try {
				log.info("PiXiaoPayController queryMoneyBack begin =========== ");
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				piXiaoPayVerfiy(map,merchantId,str);
				return client.moneyBackResultQuery(map);
			} catch (Exception e) {
				log.error("queryMoneyBack Error", e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		/**
		 * 保存固定还款日
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/saveFixDate", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String saveFixDate(HttpServletRequest request) {
			try {
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  merchantId=(String) map.get("merchantId");
				String  fixDate=(String) map.get("fixDate");
				String  userOnlyId=(String) map.get("userOnlyId");
				String[] str=getParamsFromRequest(map);
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				piXiaoPayVerfiy(map,merchantId,str);
				return client.saveFixDate(fixDate, userOnlyId);
			} catch (Exception e) {
				log.error("saveFixDate Error", e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		
		
		@RequestMapping("yczgd_viewPxContract")
		@ResponseBody
		public String viewPxContract(HttpServletRequest request) {
			try {
				BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
				String usronlyId =CommonHelper.getUserOnlyId(request);
				String result = WildflyBeanFactory.getLoanInterfaceClient().queryContractInfo(applyAmount, usronlyId);
				log.info("viewPxContract result:"+result);
				return result;
			} catch (Exception e) {
				log.error("viewPxContract Error", e);
				return ERROR;
			}
		}
		@RequestMapping("yczgd_viewPettyLoanBill")
		public String viewPettyLoanBill(HttpServletRequest request) {
			try {
				String userOnlyId = CommonHelper.getUserOnlyId(request);
				String applyAmount = request.getParameter("applyAmount");
				String lastRepayDate = request.getParameter("lastRepayDate");
				String flag = request.getParameter("flag");
				log.info("viewPettyLoanBill  userOnlyId="+userOnlyId+"lastRepayDate"+lastRepayDate);
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
		 		map.put("applyAmount", applyAmount);
		 		map.put("lastRepayDate", lastRepayDate);
		 		map.put("flag", flag);
	 			String result = WildflyBeanFactory.getYCZgdQueryClient().viewPettyLoanBill(map);
				log.info("viewPettyLoanBill result:"+result);
				JSONObject js=JSONObject.fromObject(result);
				request.setAttribute("js", js);
				return "yc/viewPettyLoanBill";
			} catch (Exception e) {
				log.error("viewPettyLoanBill Error", e);
				return ERROR;
			}
		}
}
