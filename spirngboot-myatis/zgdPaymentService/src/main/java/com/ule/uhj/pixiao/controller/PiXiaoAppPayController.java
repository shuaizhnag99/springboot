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

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.yzs.util.ResultUtil;
import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.util.MapSign;
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
@RequestMapping("/pixiao/app")
public class PiXiaoAppPayController {
	private static Logger log = LoggerFactory.getLogger(PiXiaoAppPayController.class);
	String ERROR = "common/error";
	//-----------------------以下是支付组支付时调用的接口   wiki wiki.uletm.com/pages/viewpage.action?pageId=27564308---------------------------------------
		/**
		 * 余额账户查询接口
		 * @param request
		 * @return
		 */
		@ResponseBody
	    @RequestMapping(value="/queryAvailableBalance", produces = "application/json; charset=utf-8")
		public JSONPObject queryAvailableBalance(HttpServletRequest request,@RequestParam String jsoncallback) {
			try {
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("queryAvailableBalance json "+json);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
			
				String payReqNo=Convert.toStr(map.get("payReqNo"));
				String reqNo=Convert.toStr(map.get("reqNo"));
				log.info("queryAccountInformation  begin userOnlyId==="+payReqNo);
				
//				String  merchantId=(String) map.get("merchantId");
//				String  channel=(String) map.get("channel");
//				log.info("queryAvailableBalance userOnlyId "+userOnlyId+"merchantId"+merchantId+"channel"+channel);
				PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
				String re=client.queryBalanceInfo(map);
				log.info("queryAccountInformation  begin userOnlyId==="+payReqNo+";re:"+re);
				JSONObject  jasonObject = JSONObject.fromObject(re);
				 Map returnMap = (Map)jasonObject;
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(returnMap));
			} catch (Exception e) {
				log.error("queryAvailableBalance error",e);
				Map map = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
			}
		}

	    @RequestMapping(value="/zgdPxPay")
	    @ResponseBody
		public String  orderPay(HttpServletRequest request,String  message) {
			try {
				log.info("message ==="+message);
//				message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("appzgdPxPay json "+json);
				if(StringUtils.isBlank(json)){
					return JsonResult.getInstance().addError("参数为空").toString();
				}
//				Collections.synchronizedList()
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				
				String userOnlyId=(String) map.get("userOnlyId");
				String  merchantId=(String) map.get("merchantId");
				String  channel=(String) map.get("channel");
				
				log.info("orderPay userOnlyId "+userOnlyId+"merchantId"+merchantId+"channel"+channel);
				String[] str=getParamsFromRequest(map);
				if(!piXiaoPayVerfiy(map,merchantId,str)){
					log.info("zgdPxPay 验签失败了");
					return JsonResult.getInstance().addError("验签失败").toString();
				};
				log.info("zgdPxPay 验签通过了");
				PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
//				String productBody=SpecialBASE64.decode(Convert.toStr(map.get("productBody")));
//				map.put("productBody", productBody);
				
				String re1=client.queryBalanceInfo(map);
				JSONObject  jasonObject = JSONObject.fromObject(re1);
				 Map returnMap = (Map)jasonObject;
				if("0000".equals(Convert.toStr(returnMap.get("code")))){
					String re=client.saveRequetLine(map);
					String  productDetail=map.get("productDetail").toString();
					Map<String,Boolean> jumap=this.judgeGoodsId(productDetail);
//					String pagemessage="";	
					if(jumap.get("all")&&jumap.get("part")){
//						int count=client.queryPiXiaoJiJu(userOnlyId);
//						if(count>0){
//							pagemessage="一个邮掌柜账号只能使用掌柜贷额度购买一次机具。";
//						}
						log.info("retrun result:"+JsonResult.getInstance().addOk().add("payReqNo",re).add("merchantId","100000001").
								add("reqNo",map.get("reqNo")).add("orderType", "02").toJsonStr());
						return JsonResult.getInstance().addOk().add("payReqNo",re).add("merchantId","100000001").
								add("reqNo",map.get("reqNo")).add("orderType", "02").toJsonStr();//跳到新的页面 02机具页面
					}else {
//						if(!jumap.get("all")&&jumap.get("part")){
//							pagemessage="请使用掌柜贷额度单独购买机具商品。";
//							return JsonResult.getInstance().addOk().add("payReqNo",re).add("merchantId","100000001").
//									add("reqNo",map.get("reqNo")).add("orderType", "01").add("message", pagemessage).toJsonStr();
//						}else{
//							
//						}
						log.info("return result: "+JsonResult.getInstance().addOk().add("payReqNo",re).add("merchantId","100000001").
									add("reqNo",map.get("reqNo")).add("orderType", "01").toJsonStr());
							return JsonResult.getInstance().addOk().add("payReqNo",re).add("merchantId","100000001").
									add("reqNo",map.get("reqNo")).add("orderType", "01").toJsonStr();//orderType 0 1原来的页面
					}
					
				}else {
					return re1;
				}
				
				
			} catch (Exception e) {
				log.error("orderPay error",e);
				return JsonResult.getInstance().addError("系统异常").toString();
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
	    
	    /**
		 * 还款计划及账户余额查询
		 * @param request
		 * @return
		 */
		@ResponseBody
	    @RequestMapping(value="/queryAccountInformation", produces = "application/json; charset=utf-8")
		public JSONPObject queryAccountInformation(HttpServletRequest request,@RequestParam String jsoncallback) {
			try {
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("queryAccountInformation json "+json);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
				String re= client.queryAccountInfo(map);
				JSONObject  jasonObject = JSONObject.fromObject(re);
				 Map returnMap = (Map)jasonObject;
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(returnMap));
			} catch (Exception e) {
				log.error("queryAccountInformation error",e);
				Map map = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
			}
		}
		/**
		 * 支付接口
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/zgdPay", produces = "application/json; charset=utf-8")
		@ResponseBody
		public JSONPObject  zgdPay(HttpServletRequest request,@RequestParam String jsoncallback) {
			    log.info("zgdPay begin =========== request="+request);
				Object  message=request.getParameter("message");
				String json=JsonUtil.getJsonStringFromObject(message);
				log.info("zgdPay json "+json);
				Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
				 String url=Convert.toStr(map.get("notifyUrl"));
				try {
				String  productDetail=map.get("productDetail").toString();
				Map<String,Object> map1=new HashMap<String, Object>();
				String orderType=Convert.toStr(map.get("orderType"),"01");
				log.info("zgdPay  orderType"+orderType);
			
//				JSONArray json1=JSONArray.fromObject(productDetail);
//				log.info("json1 ====================>"+json1);
//				String detail ="";
//				if(json1.size()>0){
//					  for(int i=0;i<json1.size();i++){
//					    JSONObject job = json1.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//					    if(StringUtils.isBlank(detail)){
//					    	 detail=detail+job.get("orderNo")+"|"+job.get("orderAmount");
//					    }else{
//					    	 detail=detail+","+job.get("orderNo")+"|"+job.get("orderAmount");
//					    }
//					   
//					  }
//					}
//				log.info("detail ====================>"+detail);
			    PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
			    
			 Map<String,Boolean> jumap=this.judgeGoodsId(productDetail);
			 if(jumap.get("all")&&jumap.get("part")){
				 String userOnlyId=Convert.toStr(map.get("userOnlyId")) ;
				 int count=client.queryPiXiaoJiJu(userOnlyId);
				 orderType="02";
		    		if(count>0){
		    			 Map<String,String> uMap=new HashMap<String, String>();
						 uMap.put("code", "1000");
						 uMap.put("msg", "一个邮掌柜账号只能使用掌柜贷额度购买一次机具。");
						return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(uMap));
		    		}
		    	}else if(jumap.get("part")&&!jumap.get("all")){
		    		 Map<String,String> uMap=new HashMap<String, String>();
					 uMap.put("code", "1000");
					 uMap.put("msg", "请使用掌柜贷额度单独购买机具商品。");
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(uMap));
	    		}
			    map1.put("orderType", orderType);//订单类型 01批销订单，02 机具订单
				map1.put("productDetail", productDetail);
				map1.put("validatecode", map.get("validatecode"));
				log.info("validatecode=="+map.get("validatecode"));
				map1.put("userOnlyId", map.get("userOnlyId"));
				map1.put("reqNo", map.get("reqNo"));
				map1.put("payableAmount", map.get("payableAmount"));
				map1.put("channel", map.get("app"));
				map1.put("fixDate", map.get("fixDate"));
				log.info("map1 ====================>"+map1);
				Map<String,Object> reqMap=new HashMap<String, Object>();
				reqMap.put("reqNo", map.get("reqNo"));
				Map<String,Object> urlMap=client.queryRequetLine(reqMap);
			     
			    final String notifyUrl=Convert.toStr(urlMap.get("notifyUrl"));
				
				String re= client.payment(map1);
				JSONObject  jasonObject = JSONObject.fromObject(re);
				 Map returnMap = (Map)jasonObject;
				 if("0000".equals(Convert.toStr(returnMap.get("code")))){
					 Map<String,String> signMap = MapSign.sign(returnMap, UhjSecurityProperty.get("100000001"));
					 String sign=signMap.get("sign");
					 returnMap.put("sign", sign);
					 String result=JsonResult.getInstance().toJsonStr(returnMap);
					 Map<String,String> notifyMap=new HashMap<String, String>();
					 notifyMap.put("message", result);
					 final Map<String,String> notMap=new HashMap<String, String>(notifyMap);
					 new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									log.info("zheshi xinacheng begin =======");
									String result=HttpClientUtil.sendPost(notifyUrl, notMap);
									if(!"success".equals(result)){
										Thread.sleep(300000);
										result=HttpClientUtil.sendPost(notifyUrl, notMap);
										log.info("app tongzhile haojicile num===");
										}
									log.info("zheshi appxinacheng end =======");
								} catch (Exception e) {
									log.error("pc zgdPay error ",e);
								}
								
							}
						}){}.start();
					
					 Map<String,String> uMap=new HashMap<String, String>();
					 URLCodec uc=new URLCodec();
					 String returnUrl=UhjSecurityProperty.get("PX_RETURN_URL");
					StringBuffer brf=new StringBuffer(returnUrl);
					brf.append("?payedAmount=" +returnMap.get("payedAmount"))
					.append("&reqNo="+ returnMap.get("reqNo")).append("&paymentVoucher=" + returnMap.get("paymentVoucher"))
					.append("&payedTime=" + returnMap.get("payTime")).append("&merchantId=" + returnMap.get("merchantId"))
					.append("&payStatus=SUCCESS").append("&payTypeName="+uc.encode("掌柜贷支付",CharEncoding.UTF_8));
					log.info("returnUrl ========>"+brf.toString());
					 uMap.put("returnUrl", brf.toString());
					 uMap.put("code", "0000");
					 uMap.put("msg", "success");
					
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(uMap));
				 }else{
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(returnMap));
					 
				 }
				 
				
			} catch (Exception e) {
				log.error("zgdPay error",e);
				Map map1 = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map1));
			}
		}
		/**
		 * 支付接口
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/sendMsg", produces = "application/json; charset=utf-8")
		@ResponseBody
		public JSONPObject sendMsg(HttpServletRequest request,@RequestParam String jsoncallback) {
			log.info("sendMsg begin =========== request="+request);
			Map<String, Object> map = ResultUtil.successMap();
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
					if(!"0000".equals(Convert.toStr(js.get("returnCode")))){
						map.put("code","1000");
						map.put("msg",js.get("returnMessage"));
						return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
					}
					String  mobile=replaceMiddleData(phone, "*");
					map.put("phone","请输入"+mobile+"收到的验证码");
					map.put("msg",js.get("returnMessage"));
					
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
					
				}else{
					log.info("sendMsg -->手机号是空");
					map.put("msg","手机号是空");
					return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
				}
				
			} catch (Exception e) {
				log.error("sendMsg error",e);
				Map emap = YZSExceptionUtil.handleException(e);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(emap));
			}
		}
		@RequestMapping("yczgd_viewPxContract")
		@ResponseBody
		public JSONPObject viewPxContract(HttpServletRequest request,@RequestParam String jsoncallback) {
			try {
				log.info("apppixaio  viewPxContract begin ");
				BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
				String usronlyId =CommonHelper.getUserOnlyId(request);
				log.info("apppixaio  viewPxContract begin applyAmount=="+applyAmount+"usronlyId=="+usronlyId);
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
		public static void main(String[] args) {
//			System.out.println(JsonResult.getInstance().addError().add("手机号为空").toJsonStr());
//			System.out.println(JsonResult.getInstance().addOk().add("phone","请输入"+"157****1476"+"收到的验证码").add("msg", "成功").toJsonStr()); 
			boolean all=true;
			for(int i=0;i<10;i++){
				System.out.println(i);
				if(i%2==0){
					System.out.println(all||false);
				}else{
					System.out.println(all&&true);
				}
			}
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
				LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
				return client.paymentResultsQuery(userOnlyId, payId, payDate);
			
			} catch (Exception e) {
				log.error("queryPayResult error",e);
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
}
