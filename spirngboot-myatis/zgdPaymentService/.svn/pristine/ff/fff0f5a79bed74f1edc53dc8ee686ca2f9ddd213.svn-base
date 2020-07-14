package com.ule.uhj.pixiao.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.UserControl;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.MapSign;
import com.ule.uhj.app.zgd.util.SpecialBASE64;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.pixiao.LoanInterfaceClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoPayClient;
import com.ule.uhj.ejb.client.ycZgd.SendMessageClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

@Controller
@RequestMapping("zgd/pixiao")
public class PiXiaoPcPayController {
	private static Logger log = LoggerFactory.getLogger(PiXiaoPcPayController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	String ERROR = "common/error";
	//-----------------------以下是订单组支付时调用的接口   http://wiki.uletm.com/pages/viewpage.action?pageId=30330266---------------------------------------
		
	/*@RequestMapping(value="/zgdPxPay")
	public String orderPay(HttpServletRequest request,String  message) {
    	message=request.getParameter("message");
    	log.info("orderPay 确认订单时的信息  message "+message);
    	String json=JsonUtil.getJsonStringFromObject(message);
		request.getSession().setAttribute("json", json);
		return "redirect:orderPayRedirect.do"; 
	}
	
	 @RequestMapping(value="/orderPayRedirect")
	public String orderPayRedirect(HttpServletRequest request){
		try {
			String json=(String) request.getSession().getAttribute("json");
			log.info("orderPayRedirect json "+json);
			if(StringUtils.isBlank(json)){
				request.setAttribute("result",JsonResult.getInstance().addError().add("error"));
				 return "yc/yc_zgdPay";
			}
			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
			
			String userOnlyId=getUserOnlyId(request);
			String  merchantId=(String) map.get("merchantId");
			String  channel=(String) map.get("channel");
			
			log.info("orderPay userOnlyId "+userOnlyId+"merchantId"+merchantId+"channel"+channel);
			String[] str=getParamsFromRequest(map);
			if(!piXiaoPayVerfiy(map,merchantId,str)){
				log.info("zgdPxPay 验签失败了");
				return JsonResult.getInstance().addError().add("验签失败").toString();
			};
			log.info("zgdPxPay 验签通过了");
			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
			String productBody=SpecialBASE64.decode(Convert.toStr(map.get("productBody")));
			map.put("productBody", productBody);
		
			String re=client.queryAccountInformation(map);
			log.info("zgdPxPay userOnlyId "+userOnlyId+" re:"+re);
			JSONObject js=JSONObject.fromObject(re);
	    	request.setAttribute("result",js);
	    	 return "yc/yc_zgdPay";
			
		} catch (Exception e) {
			log.error("orderPay error",e);
			Map<String,Object>	returnMap=new HashMap<String, Object>();
			returnMap.put("message", JsonResult.getInstance().addError().add("error"));
			request.setAttribute("result",JsonResult.getInstance().addError().add("error"));
			 return "yc/yc_zgdPay";
		}
	}*/
	/**
	 * 
	 * @param request   pc 提供给订单组用
	 * @return
	 */
	
    @RequestMapping(value="/zgdPxPay")
//    @ResponseBody
	public String orderPay(HttpServletRequest request,String  message1) {
    	ModelAndView model = new ModelAndView();
		try {
			String message=request.getParameter("message");
			log.info("zgdPxPay 确认订单时的信息 message ==="+message);
			String json=JsonUtil.getJsonStringFromObject(message);
			log.info("zgdPxPay json "+json);
			if(StringUtils.isBlank(json)){
				model.addObject("code","1000");
				model.addObject("msg","参数为空");
//				return model;
				JSONObject js=JSONObject.fromObject(model);
		    	request.setAttribute("result",js);
				 return "yc/yc_zgdPay";
			}
//			Collections.synchronizedList()
			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
			
			String userOnlyId=getUserOnlyId(request);
			String  merchantId=(String) map.get("merchantId");
			String  channel=(String) map.get("channel");
			
			log.info("zgdPxPay userOnlyId "+userOnlyId+"merchantId"+merchantId+"channel"+channel);
			String[] str=getParamsFromRequest(map);
			if(!piXiaoPayVerfiy(map,merchantId,str)){
				log.info("zgdPxPay userOnlyId "+userOnlyId+" 验签失败了");
				model.addObject("code","1000");
				model.addObject("msg","验签失败");
//				return model;
				JSONObject js=JSONObject.fromObject(model);
		    	request.setAttribute("result",js);
				 return "yc/yc_zgdPay";
			};
			log.info("zgdPxPay userOnlyId "+userOnlyId+" 验签通过了");
			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
//			String productDetail=SpecialBASE64.decode(Convert.toStr(map.get("productDetail")));
//			map.put("productDetail", productDetail);
			String productBody=SpecialBASE64.decode(Convert.toStr(map.get("productBody")));
			map.put("productBody", productBody);
			String productDetail=SpecialBASE64.decode(Convert.toStr(map.get("productDetail")));
//			JSONArray json1=JSONArray.fromObject(productDetail);
			String re=client.queryAccountInformation(map);
			Map<String,Object>	returnMap=new HashMap<String, Object>();
			returnMap.put("message", re);
//			 JSONObject  jasonObject = JSONObject.fromObject(re);
//			 Map returnMap = (Map)jasonObject;
//			 return "yc/yc_zgdPay";
////			 Map returnMap = new HashMap<String, Object>();
			
		 
			log.info("message ===="+re);
	    	JSONObject js=JSONObject.fromObject(re);
//	    	request.setAttribute("result",js);
	    	Map<String,Boolean> jumap=this.judgeGoodsId(productDetail);
	    	if(jumap.get("all")&&jumap.get("part")){
	    		js.put("orderType", "02");
	    	}else{
	    		js.put("orderType", "01");
	    	}
	    	log.info("zgdPxPay result "+js.toString());
	    	request.setAttribute("result",js);
	    	return "yc/yc_zgdPay";
//			log.info("zgdPxPay 查询账户信息 userOnlyId "+userOnlyId+" re:"+re);
//			 return new ModelAndView(new RedirectView("/lendvps/zgd/pixiao/toPage"), returnMap);
			
			 
			 
		} catch (Exception e) {
			log.error("orderPay error",e);
//			Map<String,Object>	returnMap=new HashMap<String, Object>();
//			returnMap.put("message", JsonResult.getInstance().addError().add("error"));
			
			request.setAttribute("result",JsonResult.getInstance().addError().add("error"));
			 return "yc/yc_zgdPay";
//			 return new ModelAndView(new RedirectView("/lendvps/zgd/pixiao/toPage"), returnMap);
		}
	}
    /**
     * 判断结算商品是否包含特殊商品
     * @param productDetail
     * @return
     *  all  true 全部是特殊商品  false  商品中不全部是特殊商品
     *  part  true 部分是特殊商品 false 商品中没有特殊商品
     */
    private Map<String,Boolean> judgeGoodsId(String productDetail){
    	Map<String,Boolean> juMap=new HashMap<String,Boolean>();
    	log.info("judgeGoodsId ..."+productDetail);
    	boolean all=true;
    	boolean part=false;
    	try{
    	if(StringUtils.isNotBlank(productDetail)){
    		JSONArray json1=JSONArray.fromObject(productDetail);
    		if(null!=json1&&json1.size()>0){
    			for(Object js:json1){
    				JSONObject jb=	JSONObject.fromObject(js);
    				JSONArray items=jb.getJSONArray("items");
    				if(null!=items&&jb.size()>0){
    					for(Object jt:items){
    						JSONObject je=	JSONObject.fromObject(jt);
    						boolean check=checkId(Convert.toStr(je.get("id"),""));
    						all=all&&check;
    						part=part||check;
    					}
    				}
    				
    				
    			}
    			
    		}else{
    			all=false;
    		}
    		
    	}else{
    		all=false;
    	}
    	}catch(Exception e){
    		log.error("judgeGoodsId error", e);
    		all=false;
    		part=false;
    	}
    	juMap.put("all", all);
    	juMap.put("part", part);
    	log.info("judgeGoodsId ..end."+juMap.toString());
    	return juMap;
    }
    private boolean checkId(String id){
    	boolean resu=false;
    	String ids=getConstantValue("pixiaoSpecialGoods");
    	log.info("checkId getConstantValue.."+ids);
    	if(StringUtils.isNotBlank(ids)){
    		String idar[]=ids.split(",");
    		for(String st:idar){
    			if(id.equals(st)){
    				resu= true;
    			}
    			
    		}
    	}
    	log.info("checkId end.."+resu);
    	return resu;
    }
    
    private String getConstantValue(String key){
		String re="";
		
		try {
			Map<String, Class<?>> constantMap = new HashMap<String, Class<?>>();
			constantMap.put(key, String.class);
			ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			Map<String, Object> conMap = zgdQueryClient.queryZgdConstantValue2(constantMap);
			List<String> provinceCodeList = (List<String>) conMap.get(key);//邮掌柜总数统计
			if (null != provinceCodeList && provinceCodeList.size() > 0) {
				re = Convert.toStr(provinceCodeList.get(0));
			}
		} catch (Exception e) {
			re="";
			log.error("PiXiaoPcPayController getConstantValue error", e);
		}
		log.info(key+"..PiXiaoPcPayController trad key-value.."+re);
		return re;
	}
    
    @RequestMapping(value="/toPage")
//  @ResponseBody
	public String toPage(HttpServletRequest request){
    	String re=request.getParameter("message");
    	
    	log.info("message ===="+re);
    	JSONObject js=JSONObject.fromObject(re);
    	request.setAttribute("result",js);
    	 return "yc/yc_zgdPay";
    }
	/**
	 * 支付接口
	 * @param request  pc 
	 * @return
	 */
	@RequestMapping(value="/toPay", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String  zgdPay(HttpServletRequest request) {
		log.info("toPay begin =========== request="+request);
		
		log.info("PiXiaoPayController toPay begin =========== ");
		Object  message=request.getParameter("message");
		log.info("message==="+message);
		
		String json=JsonUtil.getJsonStringFromObject(message);
		log.info("toPay json "+json);
		Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
		
		 try {
			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
			Map<String,Object> queryMap=new HashMap<String, Object>();
			queryMap.put("reqNo", map.get("reqNo"));
			Map<String,Object> urlmap=client.queryRequetLine(map);
			String notifyUrl="";
			String returnUrl="";
			
			if(urlmap!=null){
				returnUrl=Convert.toStr(urlmap.get("returnUrl"));
				notifyUrl=Convert.toStr(urlmap.get("notifyUrl"));
			}
			log.info("returnUrl===="+returnUrl+"notifyUrl===="+notifyUrl);
			String productDetail=SpecialBASE64.decode(Convert.toStr(map.get("productDetail")));

			 map.put("productDetail", productDetail);
			 map.put("channel", "pc");
			 String orderType=Convert.toStr( map.get("orderType"),"01");
			 log.info("zgdPay orderType===="+orderType);
			 String userOnlyId=getUserOnlyId(request);
			 Map<String,Boolean> jumap=this.judgeGoodsId(productDetail);
			 
			 if(jumap.get("all")&&jumap.get("part")){
				 log.info("orderType..."+orderType);
				 orderType="02";
				 int count=client.queryPiXiaoJiJu(userOnlyId);
		    		if(count>0){
		    		 return JsonResult.getInstance().addError().add("msg","一个邮掌柜账号只能使用掌柜贷额度购买一次机具。").toJsonStr(); 
		    		}
		    	}else if(jumap.get("part")&&!jumap.get("all")){
	    			 return JsonResult.getInstance().addError().add("msg","请使用掌柜贷额度单独购买机具商品。").toJsonStr(); 
	    		}
			 
		    map.put("orderType", orderType);//订单类型 01批销订单，02 机具订单
			String re = client.payment(map);
			JSONObject  jasonObject = JSONObject.fromObject(re);
			 Map returnMap = (Map)jasonObject;
			 if("0000".equals(Convert.toStr(returnMap.get("code")))){
				 Map<String,String> signMap = MapSign.sign(returnMap, UhjSecurityProperty.get("100000001"));
				 String sign=signMap.get("sign");
				 returnMap.put("sign", sign);
				 String result=JsonResult.getInstance().toJsonStr(returnMap);
				
				 final Map<String,String> urlMap=new HashMap<String, String>();
				 urlMap.put("message", result);
				 log.info("result=============>"+result+"urlMap====>"+urlMap);
				 
				 final String url=notifyUrl;
				 new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							log.info("zheshi xinacheng begin =======");
							String result=HttpClientUtil.sendPost(url, urlMap);
						
							if(!"success".equals(result)){
								Thread.sleep(300000);
								result=HttpClientUtil.sendPost(url, urlMap);
								log.info("pctongzhile haojicile num===");
								}
							log.info("zheshi xinacheng end =======");
						} catch (Exception e) {
							log.error("pc zgdPay error ",e);
						}
						
					}
				}){}.start();
				log.info("zheshi returnUrl =======");
				 return JsonResult.getInstance().addOk().add("message", result).add("returnUrl", returnUrl).toJsonStr();
			 }else{
				 return JsonResult.getInstance().addError().add("msg",returnMap.get("msg")).toJsonStr(); 
			 }
			
		} catch (Exception e) {
			return JsonResult.getInstance().addError().add("msg","系统异常").toJsonStr();
			
		}
	}
	
	/**
		 * 余额账户查询接口
		 * @param request
		 * @return
		 */
		@ResponseBody
	    @RequestMapping(value="/queryBalance", produces = "application/json; charset=utf-8")
		public String queryAvailableBalance(HttpServletRequest request) {
//			log.info(arg0);
			try {
//				Map<String,Object> map=new HashMap<String, Object>();
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  userOnlyId=Convert.toStr(map.get("userOnlyId"));
				String  merchantId=Convert.toStr(map.get("merchantId"));
				log.info("queryAvailableBalance userOnlyId "+userOnlyId+"merchantId"+merchantId+"sign="+map.get("sign"));
				
				String checkResult=blackListCheck(userOnlyId);
				log.info("queryAvailableBalance blackListCheck  result "+checkResult);
				if(!"0000".equals(checkResult)){
					String result= JsonResult.getInstance().addError().add("loanFlag", "N").addError("你的账号存在异常信息，暂时不可以支用！").add("code","1004").toJsonStr();
					log.info("queryAvailableBalance blackListCheck userOnlyId "+userOnlyId+" result "+result);
					return result;
				}
				
				String[] str=getParamsFromRequest(map);
				if(!piXiaoPayVerfiy(map,merchantId,str)){
					log.info("queryAvailableBalance  error 验签失败");
					return JsonResult.getInstance().addError("验签失败").toString();
				};
				log.info("queryAvailableBalance 验签通过了");
				PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
				String result=client.queryBalanceInfo(map);
				log.info("queryAvailableBalance 余额账户查询 userOnlyId "+userOnlyId+" result "+result);
				return result;
			} catch (Exception e) {
				log.error("queryBalance error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		//黑名单验证
		public String blackListCheck(String userOnlyId){
			String result= "0000";
			try {
				UserControl userControl=userInfoService.queryUserControl(userOnlyId);
				if(userControl!=null){
//					List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
//					AccountInfoExample accountInfoExample = new AccountInfoExample();
//					accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
//					accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
//					if(accountInfos!=null && accountInfos.size()>0){
//						AccountInfo info=accountInfos.get(0);
//						if(info.getBanlance().compareTo(info.getCreditLimit())==0){
////							result=JsonResult.getInstance().addError("error：javax.ejb.EJBTransactionRolledbackException: org.hibernate.exception.GenericJDBCException: could not execute query——"+info.getAccName()).toJsonStr();
//							log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";reason:javax.ejb.EJBTransactionRolledbackException");
//							return "BLACK001";
//						}
//					}
					//result=JsonResult.getInstance().addError("您有未结清的贷款，请结清后再试！").toJsonStr();
					log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";reason:您有未结清的贷款，请结清后再试");
					result="WJQ001";
				}
			} catch (Exception e) {
				log.error("blackListCheck error "+userOnlyId, e);
				result="1000";
			}
			return result;
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
		 * 调用支付接口验签
		 * @param request
		 * @throws Exception 
		 */
		private boolean  piXiaoPayVerfiy(Map<String,Object> reMap, String channel, String[] verfiyFieldNames) throws Exception{
			Map<String,String> map = new HashMap<String, String>();
			if(verfiyFieldNames != null && verfiyFieldNames.length != 0){
				for(String str : verfiyFieldNames){
					map.put(str, Convert.toStr(reMap.get(str)));
				}
			}
			map.put("sign", Convert.toStr(reMap.get("sign")));
			log.info("sign==> "+reMap.get("sign"));
			if(StringUtils.isBlank(UhjSecurityProperty.get(channel))){
				log.error("merchantId error key is null 获取key失败了********************* ");
				return false;
			}
			if(!MapSign.verify(map, UhjSecurityProperty.get(channel))){
				log.info("piXiaoPayVerfiy sign error ============"+map);
				return false;
			}
			return true;
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
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				if(!piXiaoPayVerfiy(map,merchantId,str)){
					return JsonResult.getInstance().addError("验签失败").toString();
				};
				PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
				String re= client.paymentResultsQuery(map);
				JSONObject  jasonObject = JSONObject.fromObject(re);
				 Map returnMap = (Map)jasonObject;
				 Map<String,String> signMap = MapSign.sign(returnMap, UhjSecurityProperty.get(merchantId));
				 String sign=signMap.get("sign");
				 returnMap.put("sign", sign);
				 String json1=JsonUtil.getJsonStringFromObject(returnMap);
				 return json1;
			} catch (Exception e) {
				log.error("queryPayResult error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
			}
		}
		/**
		 * 退款
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/refund", produces = "application/json; charset=utf-8",method=RequestMethod.POST)
		@ResponseBody
		public String moneyBack(HttpServletRequest request) {
			try {
				log.info("PiXiaoPayController moneyBack begin =========== ");
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				if(!piXiaoPayVerfiy(map,merchantId,str)){
					return JsonResult.getInstance().addError("验签失败").toString();
				};
				Map<String,Object> reMap=new HashMap<String, Object>();
				reMap.put("returnReqNo", map.get("refundReqNo"));
				reMap.put("returnAmount", map.get("refundAmount"));
				reMap.put("channel", map.get("channel"));
				reMap.put("payId", map.get("payReqNo"));
				reMap.put("merchantId", "100000001");
				reMap.put("flag", "1");
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				return client.moneyBack(reMap);
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
		@RequestMapping(value="/refundQuery", produces = "application/json; charset=utf-8")
		@ResponseBody
		public String refundQuery(HttpServletRequest request) {
			try {
				log.info("PiXiaoPayController refundQuery begin =========== ");
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  merchantId=(String) map.get("merchantId");
				String[] str=getParamsFromRequest(map);
				if(!piXiaoPayVerfiy(map,merchantId,str)){
					return JsonResult.getInstance().addError("验签失败").toString();
				};
				Map<String,Object> reMap=new HashMap<String, Object>();
				reMap.put("returnReqNo", map.get("refundReqNo"));
				reMap.put("merchantId", "100000001");
				reMap.put("flag", "1");
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				return client.moneyBackResultQuery(reMap);
//				JSONObject  jasonObject = JSONObject.fromObject(re);
//				 Map returnMap = (Map)jasonObject;
//				 String sign=MapSign.sign(returnMap, UhjSecurityProperty.get("100000001")).get("sign");
//				 returnMap.put("sign", sign);
//				 String json1=JsonUtil.getJsonStringFromObject(returnMap);
//				 return JsonResult.getInstance().addOk().add("message",json1).toJsonStr();
			} catch (Exception e) {
				log.error("moneyBack Error", e);
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
					if(!"0000".equals(Convert.toStr(js.get("returnCode")))){
						return JsonResult.getInstance().addError().add("msg", js.get("returnMessage")).toJsonStr();
					}
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
		@RequestMapping("yczgd_viewPxContract")
		@ResponseBody
		public JSONPObject viewPxContract(HttpServletRequest request,@RequestParam String jsoncallback) {
			try {
				BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
				String usronlyId =CommonHelper.getUserOnlyId(request);
				String result = WildflyBeanFactory.getLoanInterfaceClient().queryContractInfo(applyAmount, usronlyId);
				JSONObject  jasonObject = JSONObject.fromObject(result);
				 Map returnMap = (Map)jasonObject;
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(returnMap));
			} catch (Exception e) {
				Map emap = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(emap));
			}
		}
		@RequestMapping("yczgd_viewPettyLoanBill")
		@ResponseBody
		public JSONPObject viewPettyLoanBill(HttpServletRequest request,@RequestParam String jsoncallback) {
			try {
				String userOnlyId = CommonHelper.getUserOnlyId(request);
				String applyAmount = request.getParameter("applyAmount");
				String lastRepayDate = request.getParameter("lastRepayDate");
				String flag = request.getParameter("flag");
				log.info("viewPettyLoanBill  userOnlyId="+userOnlyId);
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
		 		map.put("applyAmount", applyAmount);
		 		map.put("lastRepayDate", lastRepayDate);
		 		map.put("flag", flag);
	 			String result = WildflyBeanFactory.getYCZgdQueryClient().viewPettyLoanBill(map);
				log.info("viewPettyLoanBill result:"+result);
				JSONObject jasonObject=JSONObject.fromObject(result);
				 Map returnMap = (Map)jasonObject;
				 return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(returnMap));
			} catch (Exception e) {
				log.error("viewPettyLoanBill Error", e);
				Map emap = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(emap));
			}
		}
//		public static void main(String[] args) {
//			System.out.println(JsonResult.getInstance().addError().add("手机号为空").toJsonStr());
//			System.out.println(JsonResult.getInstance().addOk().add("phone","请输入"+"157****1476"+"收到的验证码").add("msg", "成功").toJsonStr()); 
//		}
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
		 * 保存固定还款日
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/saveFixDate", produces = "application/json; charset=utf-8")
		@ResponseBody
		public JSONPObject saveFixDate(HttpServletRequest request,@RequestParam String jsoncallback) {
			try {
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				String  fixDate=(String) map.get("fixDate");
				String  userOnlyId=(String) map.get("userOnlyId");
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				String result =client.saveFixDate(fixDate, userOnlyId);
				JSONObject jasonObject=JSONObject.fromObject(result);
				 Map returnMap = (Map)jasonObject;
				 return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(returnMap));
			} catch (Exception e) {
				log.error("saveFixDate Error", e);
				Map emap = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(emap));
			}
		}
		private String getUserOnlyId(HttpServletRequest request) throws Exception {
			String usronlyId =CommonHelper.getUserOnlyId(request);
			return usronlyId;
		}
		
		
}
