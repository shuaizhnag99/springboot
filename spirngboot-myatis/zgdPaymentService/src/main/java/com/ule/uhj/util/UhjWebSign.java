package com.ule.uhj.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.tools.sign.Constans;
import com.ule.tools.sign.SignEnc;

/**
 * 项目中使用的验签方法,方法中自动增加signTime(yyyyMMddHHmmss)和uuid与所有的输入参数(升序排序)
 * 验签的时候,拿到signTime 和服务器时间比较误差在60s内
 * @author zhangyaou
 *
 */
public class UhjWebSign {
	private static Logger log = LoggerFactory.getLogger(UhjWebSign.class);
	/**
	 * 加签 第一个参数Map 为加签的参数,第二个参数为秘钥
	 * 在原来的传入参数中增加 uuid  sendTime sign三个参数
	 * @param params
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> sign(Map<String, String> params, String secret) throws Exception{
		if(params == null){
			params = new HashMap<String, String>();
		}
		List<String> list = new ArrayList<String>();
		for(Entry<String, String> et : params.entrySet()){
			if(et.getValue() != null && et.getValue().trim().length() != 0){
				list.add(et.getKey() + "=" + et.getValue());
			}
		}
		Collections.sort(list);
		StringBuffer buffer = new StringBuffer();
		for(String str : list){
			buffer.append(str).append("&");
		}
		log.info("sign  ==>"+buffer);
		buffer.setLength(buffer.length() - 1);
		//增加sign
		params.put("sign", SignEnc.sign(buffer.toString(), secret, Constans.SIGN_TYPE_MD5));
		//返回所有信息
		return params;
	}
	
	/**
	 * 验签 第一个参数Map 中包含了sign 为加签的参数,第二个参数为秘钥
	 * @param params
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(Map<String, String> params, String secret) throws Exception{
		List<String> list = new ArrayList<String>();
		//增加uuid和signTime
		for(Entry<String, String> et : params.entrySet()){
			if(et.getValue() != null && et.getValue().trim().length() != 0 && !"sign".equals(et.getKey())){
				list.add(et.getKey() + "=" + et.getValue());
			}
		}
		Collections.sort(list);
		list.add("sign===============" + params.get("sign"));
		StringBuffer buffer = new StringBuffer();
		for(String str : list){
			buffer.append(str).append("&");
		}
		log.info("buffer str********"+buffer);
		buffer.setLength(buffer.length() - 1);
		//增加sign
		return SignEnc.verify(buffer.toString(), secret, Constans.SIGN_TYPE_MD5);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userOnlyId", "10000023861");
		params.put("payAmount", "500");
//		params.put("payId", "201611161700");
		params.put("merchantId", "88888888");
		params.put("channel", "ulepay");
		
		Map<String, String> p=sign(params,"de734f5b931547778ec54fb4243a69ec");
//		params.put("test", "12312312 ");  //will false
		System.out.println(p);
		params.put("sign",p.get("sign"));
		System.out.println(verify(params, "de734f5b931547778ec54fb4243a69ec"));
	}
	
}
