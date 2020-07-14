package com.ule.uhj.ysg.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.model.UserControl;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.MapSign;
import com.ule.uhj.dic.PaymentDic;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.pixiao.LoanInterfaceClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoPayClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.pixiao.controller.UhjSecurityProperty;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.ysg.service.SyncPayResultService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 邮赊购业务接口，测试完毕，业务更改，不再使用。哭哭哭哭哭哭哭哭哭哭哭哭哭哭哭哭哭哭
 * @author zhangshuai
 *
 */
@Controller
@RequestMapping("/interface/ysg")
public class YsgPayController {
//	private static Log log = LogFactory.getLog(YsgPayController.class);
//	@Autowired
//	private UserInfoService userInfoService;
//	@Autowired
//	private SyncPayResultService syncPayResultService;
//	
//	/**
//	 * 支付接口
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="/ysgPay", produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String  ysgPay(HttpServletRequest request) {
//		    log.info("ysgPay begin =========== request="+request);
//			Object  message=request.getParameter("message");
//			String json=JsonUtil.getJsonStringFromObject(message);
//		    log.info("ysgPay begin =========== message="+json);
//			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
//			try {
//				String userOnlyId=map.get("userOnlyId").toString();
//				String  merchantId=(String) map.get("merchantId");
//				String  channel=(String) map.get("channel");
//				String productDetail=Convert.toStr(map.get("productDetail"));
//				String proCode=Convert.toStr(map.get("proCode"));
//				
//				log.info("ysgPay userOnlyId "+userOnlyId+"merchantId"+merchantId+"channel"+channel);
//				String[] str=getParamsFromRequest(map);
//				if(!piXiaoPayVerfiy(map,merchantId,str)){
//					log.info("ysgPay userOnlyId "+userOnlyId+" 验签失败了");
//					map.put("code", "2000");
//					map.put("msg", "验签失败");
//					return  JsonUtil.getJsonStringFromMap(map);
//				}
//				
//			if(StringUtils.isBlank(productDetail)){
//				  return JsonResult.getInstance().addError().add("msg","参数缺失productDetail。").toJsonStr(); 
//			}	
////			  productDetail=SpecialBASE64.decode(productDetail);
//			  productDetail=new String (Base64.decodeBase64(productDetail.getBytes()));
//			  log.info(String.format("ysgPay json productdetail useronlyid %s json %s productDetail %s ", userOnlyId,json,productDetail));
//			  //验证支付总金额是否等于订单金额总计
////			  JSONArray projson=JSONArray.fromObject(productDetail);
//			  com.alibaba.fastjson.JSONArray projson=com.alibaba.fastjson.JSONArray.parseArray(productDetail);
//			  BigDecimal orderAmount=new BigDecimal(0);
//			  for(Object pro:projson){
//				  JSONObject prj= JSONObject.fromObject(pro);
//				  orderAmount =orderAmount.add(Convert.toBigDecimal(prj.get("orderAmount"),new BigDecimal(0)));
//				  
//			  }
//			  BigDecimal payableAmount=Convert.toBigDecimal(Convert.toStr(map.get("payableAmount")));
//			  log.info(String.format("ysgPay 支付总金额/订单金额总计 useronlyid %s orderamount %s payamount  %s", userOnlyId,orderAmount,payableAmount));
//			  if(payableAmount.compareTo(orderAmount)!=0){
//				  log.info(String.format("ysgPay payableAmount not equal orderAmount useronlyid %s payableAmount %s productDetail %s ", userOnlyId,payableAmount,productDetail));
//				  return JsonResult.getInstance().addError().add("msg","支付总金额不等于订单金额总计").toJsonStr();
//			  }
//			  
//			Map<String,Object> map1=new HashMap<String, Object>();
//			String orderType=Convert.toStr(map.get("orderType"),"01");
//			
//			
//			//邮赊购一期暂时不支持机具产品
//			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
////               Map<String,Boolean> jumap=this.judgeGoodsId(productDetail);
////			 
////			 if(jumap.get("all")&&jumap.get("part")){
////				 log.info("orderType..."+orderType);
////				 orderType="02";
////				 int count=client.queryPiXiaoJiJu(userOnlyId);
////		    		if(count>0){
////		    		 return JsonResult.getInstance().addError().add("msg","一个邮掌柜账号只能使用掌柜贷额度购买一次机具。").toJsonStr(); 
////		    		}
////		    	}else if(jumap.get("part")&&!jumap.get("all")){
////	    			 return JsonResult.getInstance().addError().add("msg","请使用掌柜贷额度单独购买机具商品。").toJsonStr(); 
////	    		}
//			//邮赊购一期暂时不支持机具产品
//			 
//			 if(checkYsg(proCode)) {
//				 orderType="03";
//			 }
//			 log.info("ysgPay  orderType"+orderType);
//		    map1.put("merchantId", merchantId);
//		    map1.put("orderType", orderType);//订单类型 01批销订单，02 机具订单
//			map1.put("productDetail", productDetail);
//			map1.put("validatecode", map.get("validatecode"));
////			log.info("validatecode=="+map.get("validatecode"));
//			map1.put("userOnlyId", map.get("userOnlyId"));
//			map1.put("reqNo", map.get("reqNo"));
//			map1.put("payableAmount", map.get("payableAmount"));
//			map1.put("channel", channel);
//			map1.put("fixDate", map.get("fixDate"));
//			map1.put("proCode", map.get("proCode"));
//			log.info("map1 ====================>"+map1);
//			Map<String,Object> reqMap=new HashMap<String, Object>();
//			reqMap.put("reqNo", map.get("reqNo"));
//			
//			String re= client.ysgPayment(map1);
//			JSONObject  jasonObject = JSONObject.fromObject(re);
//			 Map returnMap = (Map)jasonObject;
//			 if("0000".equals(Convert.toStr(returnMap.get("code")))){
//				 returnMap.put("payStatus", "SUCCESS");
//				 returnMap.put("merchantId", merchantId);
//				 
//				 Map<String,String> signMap = MapSign.sign(returnMap, UhjSecurityProperty.get(merchantId));
//				 String sign=signMap.get("sign");
//				 returnMap.put("sign", sign);
//				log.info("ysgPay result"+ JsonUtil.getJsonStringFromMap(returnMap));
//				return JsonUtil.getJsonStringFromMap(returnMap);
//			 }else{
//				log.info("ysgPay result"+ JsonUtil.getJsonStringFromMap(returnMap));
//				return JsonUtil.getJsonStringFromMap(returnMap);
//				 
//			 }
//			 
//			
//		} catch (Exception e) {
//			log.error("ysgPay error",e);
//			 
//			return  JsonResult.getInstance().addError().add("msg","系统异常，请联系相关人员").toJsonStr(); 
//		}
//	}
//	
//	private boolean checkYsg(String proCode) {
//		boolean fl=false;
//		if(StringUtils.isNotBlank(proCode)) {
//			PaymentDic pd=PaymentDic.getByCode(proCode);
//			if(null!=pd) {
//				String category=pd.getCategory();
//				if(PaymentDic.ysg01.getCategory().equals(category)) {
//					fl=true;
//				}
//			}
//			
//		}
//		return fl;
//	}
//	
//	  /**
//     * 判断结算商品是否包含特殊商品
//     * @param productDetail
//     * @return
//     *  all  true 全部是特殊商品  false  商品中不全部是特殊商品
//     *  part  true 部分是特殊商品 false 商品中没有特殊商品
//     */
//    private Map<String,Boolean> judgeGoodsId(String productDetail){
//    	Map<String,Boolean> juMap=new HashMap<String,Boolean>();
//    	log.info("judgeGoodsId ..."+productDetail);
//    	boolean all=true;
//    	boolean part=false;
//    	try{
//    	if(StringUtils.isNotBlank(productDetail)){
//    		JSONArray json1=JSONArray.fromObject(productDetail);
//    		if(null!=json1&&json1.size()>0){
//    			for(Object js:json1){
//    				JSONObject jb=	JSONObject.fromObject(js);
//    				JSONArray items=jb.getJSONArray("items");
//    				if(null!=items&&jb.size()>0){
//    					for(Object jt:items){
//    						JSONObject je=	JSONObject.fromObject(jt);
//    						boolean check=checkId(Convert.toStr(je.get("id"),""));
//    						all=all&&check;
//    						part=part||check;
//    					}
//    				}
//    				
//    				
//    			}
//    			
//    		}else{
//    			all=false;
//    		}
//    		
//    	}else{
//    		all=false;
//    	}
//    	}catch(Exception e){
//    		log.error("judgeGoodsId error", e);
//    		all=false;
//    		part=false;
//    	}
//    	juMap.put("all", all);
//    	juMap.put("part", part);
//    	log.info("judgeGoodsId ..end."+juMap.toString());
//    	return juMap;
//    }
//    private boolean checkId(String id){
//    	boolean resu=false;
//    	String ids=getConstantValue("pixiaoSpecialGoods");
//    	log.info("checkId getConstantValue.."+ids);
//    	if(StringUtils.isNotBlank(ids)){
//    		String idar[]=ids.split(",");
//    		for(String st:idar){
//    			if(id.equals(st)){
//    				resu= true;
//    			}
//    			
//    		}
//    	}
//    	log.info("checkId end.."+resu);
//    	return resu;
//    }
//    
//    private String getConstantValue(String key){
//		String re="";
//		
//		try {
//			Map<String, Class<?>> constantMap = new HashMap<String, Class<?>>();
//			constantMap.put(key, String.class);
//			ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
//			Map<String, Object> conMap = zgdQueryClient.queryZgdConstantValue2(constantMap);
//			List<String> provinceCodeList = (List<String>) conMap.get(key);//邮掌柜总数统计
//			if (null != provinceCodeList && provinceCodeList.size() > 0) {
//				re = Convert.toStr(provinceCodeList.get(0));
//			}
//		} catch (Exception e) {
//			re="";
//			log.error("PiXiaoPcPayController getConstantValue error", e);
//		}
//		log.info(key+"..PiXiaoPcPayController trad key-value.."+re);
//		return re;
//	}
//	/**
//	 * 支付结果查询
//	 * @param request pc
//	 * @return
//	 */
//	@RequestMapping(value="/queryPayResult", produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String queryPayResult(HttpServletRequest request) {
//		try {
//			log.info("PiXiaoPayController queryPayResult begin =========== ");
//			Object  message=request.getParameter("message");
//			log.info("PiXiaoPayController queryPayResult  =========== "+null!=message?message.toString():"");
//			String json=JsonUtil.getJsonStringFromObject(message);
//			log.info("PiXiaoPayController queryPayResult json =========== "+json);
//			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
//			String  merchantId=(String) map.get("merchantId");
//			String[] str=getParamsFromRequest(map);
//			if(!piXiaoPayVerfiy(map,merchantId,str)){
//				return JsonResult.getInstance().addError("验签失败").toString();
//			}
//			log.info("PiXiaoPayController queryPayResult 验签成功 =========== "+json);
//			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
//			String re= client.paymentResultsQuery(map);
//			log.info("PiXiaoPayController queryPayResult ejb result =========== "+re);
////			JSONObject  jasonObject = JSONObject.fromObject(re);
//			 Map returnMap =JsonUtil.getMapFromJsonString(re) ;
//			 log.info("PiXiaoPayController queryPayResult ejb result 加签=========== ");
//			 String code=Convert.toStr(returnMap.get("code"));
//			 String json1="";
//			 if("0000".equals(code)) 
//			 {
//				 returnMap.put("payedAmount", Convert.toStr(returnMap.get("payedAmount")));
//				 returnMap.put("merchantId",merchantId);
//				 
//				 returnMap.put("payStatus", Convert.toStr(returnMap.get("payStatus")));
//				 returnMap.put("paymentVoucher", Convert.toStr(returnMap.get("paymentVoucher")));
//				 returnMap.put("reqNo", Convert.toStr(returnMap.get("reqNo")));
//				 
//				 Map<String,String> signMap = MapSign.sign(returnMap, UhjSecurityProperty.get(merchantId));
//				 String sign=signMap.get("sign");
//				 returnMap.put("sign", sign);
//				 json1=JsonUtil.getJsonStringFromObject(returnMap);
//			 }else {
//				 json1=re;
//			 }
//				log.info("PiXiaoPayController queryPayResult result json =========== "+json1);
//			 return json1;
//		} catch (Exception e) {
//			log.error("queryPayResult error",e);
//			return JsonResult.getInstance().addError("系统异常").toString();
//		}
//	}
// 
//	
//	/**
//	 * 余额账户查询接口
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//    @RequestMapping(value="/queryBalance", produces = "application/json; charset=utf-8")
//	public String queryAvailableBalance(HttpServletRequest request) {
//		try {
//			Object  message=request.getParameter("message");
//			String json=JsonUtil.getJsonStringFromObject(message);
//			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
//			String  userOnlyId=Convert.toStr(map.get("userOnlyId"));
//			String  merchantId=Convert.toStr(map.get("merchantId"));
//			log.info("queryAvailableBalance userOnlyId "+userOnlyId+"merchantId"+merchantId+"sign="+map.get("sign"));
//			
//			String checkResult=blackListCheck(userOnlyId);
//			log.info("queryAvailableBalance blackListCheck  result "+checkResult);
//			if(!"0000".equals(checkResult)){
//				String result= JsonResult.getInstance().addError().addError("你的账号存在异常信息，暂时不可以支用！").add("code","1004").toJsonStr();
//				log.info("queryAvailableBalance blackListCheck userOnlyId "+userOnlyId+" result "+result);
//				return result;
//			}
//			
//			String[] str=getParamsFromRequest(map);
//			if(!piXiaoPayVerfiy(map,merchantId,str)){
//				log.info("queryAvailableBalance  error 验签失败");
//				return JsonResult.getInstance().addError("验签失败").toString();
//			};
//			log.info("queryAvailableBalance 验签通过了");
//			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
//			String result=client.queryYsgBalanceInfo(map);
//		 
//			log.info("queryAvailableBalance 余额账户查询 userOnlyId "+userOnlyId+" result "+result);
//			return result;
//		} catch (Exception e) {
//			log.error("queryBalance error",e);
//			return JsonResult.getInstance().addError("系统异常").toString();
//		}
//	}
//	private String[] getParamsFromRequest(Map<String,Object> map) {
//		List<String> paramList = new ArrayList<String>();
//		 for(Entry<String, Object> entry : map.entrySet()){
//			 if(!"sign".equals(entry.getKey())){
//					paramList.add(entry.getKey());
//				}
//		 }
//		String[] str = (String[]) paramList.toArray(new String[paramList.size()]);
//		return str;
//	}
//	/**
//	 * 调用支付接口验签
//	 * @param request
//	 * @throws Exception 
//	 */
//	private boolean  piXiaoPayVerfiy(Map<String,Object> reMap, String channel, String[] verfiyFieldNames) throws Exception{
//		Map<String,String> map = new HashMap<String, String>();
//		if(verfiyFieldNames != null && verfiyFieldNames.length != 0){
//			for(String str : verfiyFieldNames){
//				map.put(str, Convert.toStr(reMap.get(str)));
//			}
//		}
//		map.put("sign", Convert.toStr(reMap.get("sign")));
//		log.info("sign==> "+reMap.get("sign"));
//		if(StringUtils.isBlank(UhjSecurityProperty.get(channel))){
//			log.error("merchantId error key is null 获取key失败了********************* ");
//			return false;
//		}
//		if(!MapSign.verify(map, UhjSecurityProperty.get(channel))){
//			log.info("piXiaoPayVerfiy sign error ============"+map);
//			return false;
//		}
//		return true;
//	}
//	//黑名单验证
//	public String blackListCheck(String userOnlyId){
//		String result= "0000";
//		try {
//			UserControl userControl=userInfoService.queryUserControl(userOnlyId);
//			if(userControl!=null){
//				log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";reason:您有未结清的贷款，请结清后再试");
//				result="WJQ001";
//			}
//		} catch (Exception e) {
//			log.error("blackListCheck error "+userOnlyId, e);
//			result="1000";
//		}
//		return result;
//	}
//	
//	/**
//	 * 退款
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/refund", produces = "application/json; charset=utf-8",method=RequestMethod.POST)
//	@ResponseBody
//	public String moneyBack(HttpServletRequest request) {
//		try {
//			log.info("PiXiaoPayController moneyBack begin =========== ");
//			Object  message=request.getParameter("message");
//			String json=JsonUtil.getJsonStringFromObject(message);
//			log.info("PiXiaoPayController moneyBack message =========== "+message);
//			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
//			String  merchantId=(String) map.get("merchantId");
//			String[] str=getParamsFromRequest(map);
//			if(!piXiaoPayVerfiy(map,merchantId,str)){
//				return JsonResult.getInstance().addError("验签失败").toString();
//			};
//			Map<String,Object> reMap=new HashMap<String, Object>();
//			reMap.put("returnReqNo", map.get("refundReqNo"));
//			reMap.put("returnAmount", map.get("refundAmount"));
//			reMap.put("channel", Convert.toStr(map.get("channel"),""));
//			reMap.put("payId", map.get("payReqNo"));
//			reMap.put("merchantId",map.get("merchantId"));
//			reMap.put("flag", "1");
//			LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
//			return client.moneyBack(reMap);
//		} catch (Exception e) {
//			log.error("moneyBack Error", e);
//			return JsonResult.getInstance().addError("系统异常").toString();
//		}
//	}
//	/**
//	 * 退款查询
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/refundQuery", produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String refundQuery(HttpServletRequest request) {
//		try {
//			log.info("PiXiaoPayController refundQuery begin =========== ");
//			Object  message=request.getParameter("message");
//			String json=JsonUtil.getJsonStringFromObject(message);
//			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
//			String  merchantId=(String) map.get("merchantId");
//			String[] str=getParamsFromRequest(map);
//			if(!piXiaoPayVerfiy(map,merchantId,str)){
//				return JsonResult.getInstance().addError("验签失败").toString();
//			};
//			Map<String,Object> reMap=new HashMap<String, Object>();
//			reMap.put("returnReqNo", map.get("refundReqNo"));
//			reMap.put("merchantId", merchantId);
//			reMap.put("userOnlyId", Convert.toStr(map.get("userOnlyId")));
//			reMap.put("flag", "1");
//			LoanInterfaceClient client = WildflyBeanFactory.getLoanInterfaceClient();
//			return client.moneyBackResultQuery(reMap);
////			JSONObject  jasonObject = JSONObject.fromObject(re);
////			 Map returnMap = (Map)jasonObject;
////			 String sign=MapSign.sign(returnMap, UhjSecurityProperty.get("100000001")).get("sign");
////			 returnMap.put("sign", sign);
////			 String json1=JsonUtil.getJsonStringFromObject(returnMap);
////			 return JsonResult.getInstance().addOk().add("message",json1).toJsonStr();
//		} catch (Exception e) {
//			log.error("moneyBack Error", e);
//			return JsonResult.getInstance().addError("系统异常").toString();
//		}
//	}
//	
//	 
//    @RequestMapping(value="/orderPayInfo")
//    @ResponseBody
//	public String orderPayInfo(HttpServletRequest request) {
//		try {
//			Object  message=request.getParameter("message");
//			log.info("orderPayInfo 确认订单时的信息 message ==="+message);
//			String json=JsonUtil.getJsonStringFromObject(message);
//			log.info("orderPayInfo json "+json);
//			if(StringUtils.isBlank(json)){
//				 return JsonResult.getInstance().addError("参数为空").toString();
//			}
//			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
//			
//			String userOnlyId=(String) map.get("userOnlyId");
//			String  merchantId=(String) map.get("merchantId");
//			String  channel=(String) map.get("channel");
//			String productDetail=Convert.toStr(map.get("productDetail"));
//			log.info("productDetail before decode"+productDetail);
//			if(StringUtils.isNotBlank(productDetail)){
//				productDetail =new String (Base64.decodeBase64(productDetail.getBytes()));
//				log.info("productDetail decode"+productDetail);
//			}
//			
//			
//			log.info("orderPayInfo userOnlyId "+userOnlyId+"merchantId"+merchantId+"channel"+channel+"productDetail"+productDetail);
//			String[] str=getParamsFromRequest(map);
//			if(!piXiaoPayVerfiy(map,merchantId,str)){
//				log.info("orderPayInfo userOnlyId "+userOnlyId+" 验签失败了");
//			 
//				 return JsonResult.getInstance().add("code", "1001").add("msg", " 验签失败了").toString();
//			}
//			String productBody= Convert.toStr(map.get("productBody"));
//			productBody=StringUtils.isBlank(productBody)?"zhifu":productBody;
//			map.put("productBody", productBody);
//			map.put("productDetail", productDetail);
//			log.info("orderPayInfo userOnlyId "+userOnlyId+" 验签通过了");
//			
//			PiXiaoPayClient client = WildflyBeanFactory.getPiXiaoPayClient();
//			String re=client.queryYsgAccountInformation(map);
//			Map<String,String>	returnMap=new HashMap<String, String>();
//			log.info("orderPayInfo ejb result"+re);
//			JSONObject js=JSONObject.fromObject(re);
//			String code=js.getString("code");
//			String pb="";
//			if("0000".equals(code)){
//				returnMap.put("code", "0000");
//				returnMap.put("msg", "sucess");
//				returnMap.put("limit", Convert.toStr(js.get("limit")));				 
//				returnMap.put("fixedRepayDate", Convert.toStr(js.get("fixedRepayDate")));
//				returnMap.put("userOnlyId", Convert.toStr(js.get("userOnlyId")));
//				returnMap.put("merchantId", merchantId);
//				returnMap.put("mobile", Convert.toStr(js.get("mobile")));
//				returnMap.put("isFirstLoan", Convert.toStr(js.get("isFirstLoan")));//是否首次支付	
//				pb=new String (Base64.encodeBase64(js.get("periodsDetail").toString().getBytes()));
//				returnMap.put("periodsDetail",pb);
//				
//			}else{
//				return re;
//			}
//		 
////	    	Map<String,Boolean> jumap=this.judgeGoodsId(productDetail);
////	    	if(jumap.get("all")&&jumap.get("part")){
////	    		returnMap.put("repaymentMethod", "等额本息");
////	    		returnMap.put("periods", "24个月");
////	    	}else{
////	    		returnMap.put("repaymentMethod", "一次性还本付息");
////	    		returnMap.put("periods", Convert.toStr(js.get("loanAmount"))+"*1期");
////	    	}
//	   	 Map<String,String> signMap = MapSign.sign(returnMap, UhjSecurityProperty.get(merchantId));
//		 String sign=signMap.get("sign");
//		 returnMap.put("sign", sign);
//		 log.info("orderPayInfo result end returnMap "+JsonUtil.getJsonStringFromMap(returnMap));
//		
//	    	return JsonUtil.getJsonStringFromMap(returnMap);
//			
//		} catch (Exception e) {
//		     log.error("orderPayInfo error", e);
//			 return JsonResult.getInstance().addError("系统异常").toString();
//		}
//	}
//    
//    /**
//     * 支付组通知支付结果调用地址
//     * @param request
//     * @return
//     */
//    @RequestMapping(value="/syncPayResult")
//    @ResponseBody
//	public String syncPayResult(HttpServletRequest request) {
//    	 log.info("syncPayResult begin...");
//    	syncPayResultService.syncPayResult(request);
//    	 log.info("syncPayResult end...");
//    	return "SUCCESS";
//    }
//    
//    /**
//     * 支付组通知退款结果调用地址
//     * @param request
//     * @return
//     */
//    @RequestMapping(value="/syncRefundResult")
//    @ResponseBody
//	public String syncRefundResult(HttpServletRequest request) {
//    	 log.info("syncRefundResult begin...");
//    	syncPayResultService.syncRefundResult(request);
//    	 log.info("syncRefundResult end...");
//    	return "SUCCESS";
//    }
//    
////    
////	/**
////	 * 向掌柜手机发送短信
////	 * @param phone
////	 * @return none
////	 * response json
////	 * returnCode 0000(成功)
////	 * randomCode 校验码
////	 */
////	@RequestMapping("/smsSendRandomCode")
////	@ResponseBody
////	public String smsSendRandomCode(HttpServletRequest request) {
////		String result= "";
////		try {
////			Object  message=request.getParameter("message");
////			log.info("orderPayInfo 确认订单时的信息 message ==="+message);
////			String json=JsonUtil.getJsonStringFromObject(message);
////			log.info("orderPayInfo json "+json);
////			if(StringUtils.isBlank(json)){
////				 return JsonResult.getInstance().addError("参数为空").toString();
////			}
////			Map<String,Object> map=JsonUtil.getMapFromJsonString(json);
////			
////			String userOnlyId=(String) map.get("userOnlyId");
////			String  merchantId=(String) map.get("merchantId");
////			
////			String[] str=getParamsFromRequest(map);
////			if(!piXiaoPayVerfiy(map,merchantId,str)){
////				log.info("orderPayInfo userOnlyId "+userOnlyId+" 验签失败了");
////			 
////				 return JsonResult.getInstance().add("code", "1001").add("msg", " 验签失败了").toString();
////			}
////			
////			log.info("smsSendRandomCode -->" + userOnlyId);
////			String phone=WildflyBeanFactory.getPiXiaoPayClient().queryPhone(userOnlyId);
////			if(StringUtils.isBlank(phone)){
////				 JsonResult.getInstance().addError("未查询到该掌柜手机号码").toJsonStr();
////			}
////			String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
////			log.info("smsSendRandomCode ret:"+ret);
////			JSONObject js=JSONObject.fromObject(ret);
////			result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
////			log.info("smsSendRandomCode --> result" + result);
////			return JsonUtil.getJsonStringFromObject(result);
////		} catch (Exception e) {
////			log.error("smsSendRandomCode error", e);
////			return   JsonResult.getInstance().addError("获取验证码异常").toJsonStr();
////		}
////	}
////	
//	public static void main(String args[]) throws UnsupportedEncodingException{
//		
//		String pr="[{\"availBalance\":80991,\"nextBillDay\":\"订单签收后下个月1号\",\"nextInterestfreeday \":\"订单签收后下个月9号\",\"category\":\"ysg\",\"productList\":[{\"interRate\":15,\"repaymentMethod\":\"0200\",\"proCode\":\"ysg01\",\"periods\":3},{\"interRate\":15,\"repaymentMethod\":\"0200\",\"proCode\":\"ysg02\",\"periods\":6}],\"lastLoanDate\":\"2019-07-11\"}]";
//		com.alibaba.fastjson.JSONArray js=com.alibaba.fastjson.JSONArray.parseArray(pr);
//		System.out.println("pro..."+js);
//		
//	} 
}