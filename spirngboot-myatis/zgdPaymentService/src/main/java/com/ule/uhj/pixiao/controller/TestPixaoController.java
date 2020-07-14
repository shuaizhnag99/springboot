package com.ule.uhj.pixiao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ule.uhj.app.zgd.util.MapSign;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

@Controller
@RequestMapping("zgd/test")
public class TestPixaoController {

	public static void main(String[] args) throws Exception {
    //查询账户余额
//		getBanalce();
		 //查询支用预览信息
//		orderInfo();
		//支付
//		smsSendRandomCode();
		topay("822048");
//		queryPayRuslt();
 
//		refund();
		
//		refundQuery();
		
//		refundSuccess();
		
//		refreshData();
 
	}
 
 
	
	
		/**
		 * 邮赊购测试：获取掌柜状态余额 
		 * @throws Exception
		 */
		public static void  getBanalce() throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			
            String url="";
			map.put("userOnlyId", "10000040268");//10000024271
			map.put("channel", "PC");
			map.put("merchantId","100000002");
//			map.put("payableAmount","124432");
//			String productDetail="[{\"items\":[{\"id\":\"800000068801\",\"name\":\"非转基因大豆玉米花生油\",\"num\":2800,\"price\":44.44}],\"orderAmount\":124432,\"orderNo\":\"8181211100975661\"}]";
//			map.put("productDetail", Base64.encode(productDetail.getBytes()));  
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("parmMap"+parmMap.toString());
			System.out.println(parmMap.toString());
//			url="http://localhost:8080/zgdPaymentService/interface/ysg/queryBalance.do";
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/queryBalance.do";
			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
		/**
		 * 邮赊购测试：支付预览 
		 * @throws Exception
		 */
		public static void orderInfo() throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			String url="";
		     
            map.put("merchantId","100000002");
			map.put("payableAmount", "2000");
			map.put("userOnlyId", "10000024271");
			String productDetail="[{\"items\":[{\"id\":\"800000068801\",\"name\":\"非转基因大豆玉米花生油\",\"num\":190,\"price\":10}],\"orderAmount\":190,\"orderNo\":\"8181211100975661\"}]";
			map.put("productDetail", Base64.encodeBase64String(productDetail.getBytes())); 
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("type=6 "+JsonUtil.getJsonStringFromMap(parmMap));
//			url="http://localhost:8080/zgdPaymentService/interface/ysg/orderPayInfo.do";
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/orderPayInfo.do";
			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
		
		public static void smsSendRandomCode()  throws Exception{
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			String url="";
		 
			map.put("merchantId", "100000002");
			 
			map.put("userOnlyId","10000040268");
		    
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("type=2 "+JsonUtil.getJsonStringFromMap(parmMap));
		 
//			url="http://localhost:8080/zgdPaymentService/interface/ysg/ysgPay.do";
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/smsSendRandomCode.do";

			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
		/**
		 * 支付
		 * @throws Exception
		 */
		public static void topay(String validatecode) throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			String url="";
			String productDetail="[{\"items\":[{\"id\":\"800000068801\",\"name\":\"非转基因大豆玉米花生油\",\"num\":100,\"price\":100}],\"orderAmount\":10000,\"orderNo\":\"8181211100975662\"}]";
			productDetail=Base64.encodeBase64String(productDetail.getBytes());
			map.put("merchantId",  "100000002");
			map.put("channel","APP") ;
			map.put("reqNo", "20200061613362201589673");
			map.put("payableAmount","10000");
			map.put("userOnlyId","10000040268");
			map.put("productDetail", productDetail);
		    map.put("validatecode", validatecode);
		    map.put("notifyUrl", "http://www.baidu.com");
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("type=2 "+JsonUtil.getJsonStringFromMap(parmMap));
		 
//			url="http://localhost:8080/zgdPaymentService/interface/ysg/ysgPay.do";
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/zgdPay.do";
//			url="http://localhost:8080/zgdPaymentService/interface/2020/payAndRe/zgdPay.do";

			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
		/**
		 * 邮赊购测试：支付查询
		 * @throws Exception
		 */
		public static void queryPayRuslt() throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			String url="";
			map.put("merchantId", "100000002");
			map.put("reqNo","20200060413362201589673");
			 
			map.put("userOnlyId","10000040268" );
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("type=3 "+JsonUtil.getJsonStringFromMap(parmMap));
		 
		 
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/queryPayResult.do";
			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
		/**
		 * 邮赊购测试：退款
		 * @throws Exception
		 */
		public static void refund() throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			String url="";
			map.put("merchantId", "100000002");
			map.put("refundReqNo","1000002427120181112096666");
			map.put("refundAmount","444.4000");
			map.put("payReqNo","20200060413362201589673");
			map.put("userOnlyId","10000040268" );
			map.put("notifyUrl","http://www.baidu.com");
			map.put("flag","2");
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("type=4 "+JsonUtil.getJsonStringFromMap(parmMap));
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/refund.do";
//			url="http://localhost:8080/zgdPaymentService/interface/2020/payAndRe/refund.do";
			String re = HttpClientUtil.sendPost(url, parmMap);
			
	 
			
			 
			
			System.out.println(re);
		}
		/**
		 * 退款查询
		 * @throws Exception
		 */
		public static void refundQuery() throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			Map<String,String> map=new HashMap<String, String>();
			String url="";
			map.put("merchantId","100000002");
			map.put("refundReqNo","1000002427120181112096666");
			map.put("userOnlyId", "10000040268") ;
			map.put("payId", "21812111336110198666");
			Map<String,String> signMap = MapSign.sign(map, UhjSecurityProperty.get("100000002"));
			String sign=signMap.get("sign");
			map.put("sign", sign);
			parmMap.put("message", JsonUtil.getJsonStringFromMap(map));
			System.out.println("type=5 "+JsonUtil.getJsonStringFromMap(parmMap));
			url="http://uhj-zgdPaymentService.http.beta.uledns.com/zgdPaymentService/interface/2020/payAndRe/refundQuery.do";
			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
	
			public static void refreshData() throws Exception {
			Map<String,String> parmMap=new HashMap<String, String>();
			 
			String url="";
			
			url="http://uhj-zgdDataService.http.beta.uledns.com/zgdDataService/ule/operateTotalDate.do?chinaPostId=1&jsonpCallback=jsonpCallback";
			String re = HttpClientUtil.sendPost(url, parmMap);
			System.out.println(re);
		}
		
		public static void refundSuccess() throws Exception {
			Map<String,Object> parmMap=new HashMap<String, Object>();
			parmMap.put("refundId", "10000971");
			parmMap.put("sucessRefundTime", "2020-03-26");
			parmMap.put("refundTransferNumber", "2020032610000001");
			parmMap.put("actRefundAmount", "450.40");
			String re=WildflyBeanFactory.getPiXiaoPayClient().pxMoneyBackSuccess(parmMap);
			System.out.println(re);
		}
	
      
      public static String[] getParamsFromRequest(Map<String,Object> map) {
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
  	public static boolean  piXiaoPayVerfiy(Map<String,Object> reMap, String channel, String[] verfiyFieldNames) throws Exception{
  		Map<String,String> map = new HashMap<String, String>();
  		if(verfiyFieldNames != null && verfiyFieldNames.length != 0){
  			for(String str : verfiyFieldNames){
  				map.put(str, Convert.toStr(reMap.get(str)));
  			}
  		}
  		map.put("sign", Convert.toStr(reMap.get("sign")));
  		if(StringUtils.isBlank(UhjSecurityProperty.get(channel))){
  			return false;
  		}
  		if(!MapSign.verify(map, UhjSecurityProperty.get(channel))){
  			return false;
  		}
  		return true;
  	}
  	
  	public static void queryInterestStrategy()  throws Exception{
  		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userOnlyId", "10000024271");
		String result = WildflyBeanFactory.getZgdAppClient().queryInterestStrategy(map);
		System.out.println(result);
	}
  	
  	
  	public static void testinsertjycfaile() throws Exception {
  		String url="http://localhost:8080/loanRiskControlAudit/framework/approve/testInsert.do";
		String re ="";
		re= HttpClientUtil.sendPost(url, new HashMap<String, String>());
		System.out.println(re);
  	}
}
