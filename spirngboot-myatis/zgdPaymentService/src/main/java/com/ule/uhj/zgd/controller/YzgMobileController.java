package com.ule.uhj.zgd.controller;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YzgMobileClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/uhj")
public class YzgMobileController {
	private static Logger log = LoggerFactory.getLogger(YzgMobileController.class);
	
//	String yzgMessage = "yzg/yzg_mobileMessage";
//	String ERROR = "common/error";
	
	/***
	 * 掌柜贷在手机版邮掌柜显示的通知消息获取相应字段信息
	 * 三天后到期还款通知
	 * 审核拒绝通知
	 * @return
	 */
	@RequestMapping("/yzg_mobileMessage")
	@ResponseBody
	public JSONPObject yzg_mobileMessage(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("yzg_mobileMessage begin.");
		try {
			String userOnlyId = CommonHelper.getUserOnlyId(request);//"10000000391";//decryptDES(request.getParameter("userOnlyId"));
			log.info("yzg_mobileMessage userOnlyId "+userOnlyId);
			String type=request.getParameter("type");
			if("2".equals(type)){//审核拒绝通知的相关信息
				String res=WildflyBeanFactory.getYzgMobileClient().querySendYzgMsg(userOnlyId,type);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(res));
			}else if("1".equals(type)){//三天后到期还款通知的相关信息
				String res=WildflyBeanFactory.getYzgMobileClient().querySendYzgMsg(userOnlyId,type);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(res));
			}else{
				return new JSONPObject(jsoncallback, "系统异常");
			}
		} catch (Exception e) {
			log.error("yzg_mobileMessage error!",e);
			return new JSONPObject(jsoncallback, e);
		}
	}
	
	
	
	
	  static final String decryptKey = "6fd4b7f4"; // 密钥
	  static final byte[] iv = { 13, 8, 3, 16, 23, 6, 11, 5 };  //向量
	
	//DES解密
	public static String decryptDES(String encryptString) throws Exception {
		if(encryptString==null || "".equals(encryptString)){
			return encryptString;
		}
		try {
			byte str2[] = Base64.decodeBase64(encryptString.getBytes("UTF-8"));
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte[] str3 = cipher.doFinal(str2);
			String res = encryptString;
			if (str3 != null) {
				res = new String(str3, "UTF-8");
			}
			return res;
		} catch (Exception e) {
			log.error("解密失败：" + encryptString);
			throw  e;
		}
	}

}