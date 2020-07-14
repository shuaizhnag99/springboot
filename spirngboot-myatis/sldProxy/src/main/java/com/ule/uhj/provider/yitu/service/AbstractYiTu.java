package com.ule.uhj.provider.yitu.service;

import java.security.PublicKey;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.uhj.provider.yitu.util.EncryptionHelper;
import com.ule.uhj.provider.yitu.util.HttpRequestHelper;
//import org.json.JSONObject;
//import com.yitu.faceSaaS.EncryptionHelper.MD5Helper.Md5EncodingException;
//import com.yitu.faceSaaS.FileHelper;

public class AbstractYiTu {

	private static Logger log = LoggerFactory.getLogger(AbstractYiTu.class);
    
	protected static String pemPath;
	protected static String accessId;
	protected static String accessKey;
	protected static String userDefinedContent;
	protected static String ip;
	
	static{
		try {
			loadConfig();
		} catch (Exception e) {
			log.error(String.format("AbstractYiTu init loadConfig error :%s", new Object[]{e.getMessage()}), e);
		}
	}
	
	protected static String send(String url, String requestBodyString) throws Exception {
		/*
		 * Step 2 生成 signature
		 */
		PublicKey publicKey = EncryptionHelper.RSAHelper.loadPublicKey(AbstractYiTu.class.getClassLoader().getResourceAsStream(pemPath));
		String signature = HttpRequestHelper.generateSignature(publicKey, accessKey, requestBodyString,
				userDefinedContent);
//		System.out.println("生成signature : " + signature);
		/*
		 * Step 3 发送 HTTP请求
		 */
		String result = HttpRequestHelper.sendPost(url, accessId, signature, requestBodyString);
		return result;
	}

	/**
	 * 载入配置文件
	 * 
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	public static void loadConfig() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> config = mapper.readValue(AbstractYiTu.class.getClassLoader().getResourceAsStream("yitu/config.json"), Map.class);

		ip = config.get("yituIp").toString();
		pemPath = config.get("pemPath").toString();
		accessId = config.get("accessId").toString();
		accessKey = config.get("accessKey").toString();
		userDefinedContent = config.get("userDefinedContent").toString();
	}

}