package com.ule.uhj.app.act.constant;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.SecureUtil;

public class AccountMoneyUtil {
	private static Logger log = LoggerFactory.getLogger(AccountMoneyUtil.class);
	/**
	 * 调用钱包组的查询账户开户情况
	 * http://wiki.uletm.com/pages/viewpage.action?pageId=27562223
	 * @return
	 * @throws Exception 
	 */
	public static String isOpenAccount(Map<String, Object> paraMap) throws Exception{
		String userOnlyId=Convert.toStr(paraMap.get("userOnlyId"));
		String host = PropertiesHelper.getDfs("isOpenAccount_url");
		String signKey = PropertiesHelper.getDfs("bankCard_signKey");
		String appKey = PropertiesHelper.getDfs("bankCard_appKey");
		JSONObject param = new JSONObject();
		param.put("appKey", appKey);
		param.put("userOnlyId", userOnlyId);
		param.put("accTypeId", "A004");
		String sign=sign(param, signKey);
		param.put("sign", sign);
		Map<String, String> set =new HashMap<String,String>();
		set.put("message", param.toString());
		log.info("isOpenAccount 调用钱包组查询账户开户情况接口begin。。。。"+set.toString());
		String ret=com.ule.uhj.util.http.HttpClientUtil.sendPost(host, set);
		log.info("isOpenAccount 调用钱包组查询账户开户情况接口 ret:"+ret);
		return ret;
	}
	/**
	 * 调用钱包组的账户开户
	 * http://wiki.uletm.com/pages/viewpage.action?pageId=27559452
	 * @return
	 * @throws Exception 
	 */
	public static String openAccount(Map<String, Object> paraMap) throws Exception{
		String userOnlyId=Convert.toStr(paraMap.get("userOnlyId"));
		String userName=Convert.toStr(paraMap.get("userName"));
		String host = PropertiesHelper.getDfs("openAccount_url");
		String signKey = PropertiesHelper.getDfs("bankCard_signKey");
		String appKey = PropertiesHelper.getDfs("bankCard_appKey");
		JSONObject param = new JSONObject();
		param.put("appKey", appKey);
		param.put("userOnlyId", userOnlyId);
		param.put("userName", userName);
		param.put("accTypeId", "A004");
		param.put("walletType", "ZGACC");//PCACC-大网、ZGACC-邮掌柜、YLXDACC-邮乐小店、YZSACC-邮助手
		param.put("sysCode", "uleCreditLoan");
		String sign=sign(param, signKey);
		param.put("sign", sign);
		Map<String, String> set =new HashMap<String,String>();
		set.put("message", param.toString());
		log.info("openAccount 调用钱包组的账户开户接口begin。。。。"+set.toString());
		String ret=com.ule.uhj.util.http.HttpClientUtil.sendPost(host, set);
		log.info("openAccount 调用钱包组的账户开户接口 ret:"+ret);
		return ret;
	}

	/**
	 * 调用钱包组的入账查询接口
	 * http://wiki.uletm.com/pages/viewpage.action?pageId=30338776
	 * @return
	 * @throws Exception 
	 */
	public static String queryAccountMoneyAndSettle(Map<String, Object> paraMap) throws Exception{
		String orderId=Convert.toStr(paraMap.get("orderId"));
		String signKey = PropertiesHelper.getDfs("bankCard_signKey");
		String appKey = PropertiesHelper.getDfs("bankCard_appKey");
		String host = PropertiesHelper.getDfs("query_accountMoney_url");
		JSONObject param = new JSONObject();
		param.put("appKey", appKey);
		param.put("orderId", orderId);
		String sign=sign(param, signKey);
		param.put("sign", sign);
		Map<String, String> set =new HashMap<String,String>();
		set.put("message", param.toString());
		log.info("queryAccountMoneyAndSettle 调用钱包组的入账状态查询接口begin。。。。"+set.toString());
		String ret=com.ule.uhj.util.http.HttpClientUtil.sendPost(host, set);
		log.info("addAccountMoneyAndSettle 调用钱包组的入账状态查询接口 ret:"+ret);
		return ret;
	}
	
	/**
	 * 调用钱包组的入账接口
	 * http://wiki.uletm.com/pages/viewpage.action?pageId=30337366
	 * @return
	 * @throws Exception 
	 */
	public static String addAccountMoneyAndSettle(Map<String, Object> paraMap) throws Exception{
		String userOnlyId=Convert.toStr(paraMap.get("userOnlyId"));
		String orderId=Convert.toStr(paraMap.get("orderId"));
		String orderTime=Convert.toStr(paraMap.get("orderTime"));
		String orderMoney=Convert.toStr(paraMap.get("orderMoney"));
		String refundOrderId=Convert.toStr(paraMap.get("refundOrderId"));
		String userName=Convert.toStr(paraMap.get("userName"));
		String host = PropertiesHelper.getDfs("accountMoney_url");
		String signKey = PropertiesHelper.getDfs("bankCard_signKey");
		String appKey = PropertiesHelper.getDfs("bankCard_appKey");
		String transDesc=orderId+" 邮利券转让后收益 ";
//		JSONArray orderDetail=new JSONArray();
//		JSONObject js=new JSONObject();
//		js.put("transMoney", orderMoney);
//		js.put("transTypeId", "T217");
//		js.put("transFlag", "D");
//		js.put("transDesc", transDesc);
//		orderDetail.add(js);
		JSONObject param = new JSONObject();
		param.put("appKey", appKey);
		param.put("userOnlyId", userOnlyId);
		param.put("orderId", orderId);
		param.put("orderTime", orderTime);
		param.put("orderType", "2001");
		param.put("orderMoney", orderMoney);
		param.put("refundOrderId", refundOrderId);
		param.put("orderStatus", "1");
		param.put("userName", userName);
		param.put("accTypeId", "A004");
		param.put("transTypeId", "T217");
		param.put("transFlag", "D");
		param.put("sysCode", "S13");
		param.put("channel", "APP");
		param.put("ipAddr", "127.0.0.1");
		param.put("transDesc", transDesc);
//		param.put("orderDetail", orderDetail);
		String sign=sign(param, signKey);
		param.put("sign", sign);
		Map<String, String> set =new HashMap<String,String>();
		set.put("message", param.toString());
		log.info("addAccountMoneyAndSettle 调用钱包组的入账接口 begin。。。。"+set.toString());
		String ret=com.ule.uhj.util.http.HttpClientUtil.sendPost(host, set);
		log.info("addAccountMoneyAndSettle 调用钱包组的入账接口 ret:"+ret);
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("orderId", "1966666666666");
		queryAccountMoneyAndSettle(param);
		
		
		param.put("userOnlyId", "9637");
		param.put("orderId", "1966666666666");
		param.put("orderTime", "2018-05-02 14:42:00");
		param.put("orderMoney", "13.1");
		param.put("refundOrderId", "1966666666666");
		param.put("userName", "zhaojie");
		addAccountMoneyAndSettle(param);
	}
	
	/***
	 * 与结算组签证加密
	 * @param paramer
	 * @param sign
	 * @return
	 * @throws Exception 
	 */
	private static String sign(Map<String,Object>paramer,String key) throws Exception{
		log.info("签证加密开始，paramer="+paramer+",key="+key);
		if(Check.isBlank(paramer) || Check.isBlank(key)){
			log.info("签证加密失败，签证或key为空.");
			throw new Exception("签证加密失败，签证或key为空.");
		}
		return SecureUtil.sign(paramer, key);
	}
	
}
