package com.ule.uhj.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 安全工具类
 *
 * 搬运自崔甜甜原EJB内工具
 *
 * 2017/05/12——加入了对map类型的支持
 *
 * @author caijian
 *
 */
public class SecureUtil {
	
	private static final Logger logger = Logger.getLogger(SecureUtil.class);
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String sign(Object obj, String key) {
		StringBuffer param = new StringBuffer();
		List msgList=objToList(obj);
		Collections.sort(msgList);
		for (int i = 0; i < msgList.size(); i++) {
			String msg = (String) msgList.get(i);
			if (i > 0) {
				param.append("&");
			}
			param.append(msg);
		}
		param.append("&key=" + key);
//		logger.info("====sign>>>>>> [" + param.toString() +"]");
		String signStr = signStr(param.toString());
		return signStr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean verify(Object obj, String key, String sign) {
		List msgList=objToList(obj);
		Collections.sort(msgList);
		StringBuffer param = new StringBuffer();
		for (int i = 0; i < msgList.size(); i++) {
			String msg = (String) msgList.get(i);
			if (i > 0) {
				param.append("&");
			}
			param.append(msg);
		}
		param.append("&key=" + key);
//		logger.info("====verify>>>>>> [" + param.toString() +"]");
		String signStr = signStr(param.toString());
		return signStr.equals(sign);
	}
	
	private static String signStr(String needSignStr) {
		String resultString = null;
		try {
			resultString = needSignStr;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.info("signStr Exception:", e);
		}
		return resultString;
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * 转换byte到16进制
	 * 
	 * @param b
	 *            要转换的byte
	 * @return 16进制格式
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List objToList(Object obj){
		List msgList = new ArrayList();

		if(obj instanceof Map){
			for(Object mapKey : ((Map)obj).keySet()){
				Object mapValue = ((Map)obj).get(mapKey);
				if(StringUtils.isBlank(Convert.toStr(mapValue,""))){
					continue;
				}else{
					msgList.add(mapKey.toString()+"="+mapValue.toString()+"");
				}
			}
			return msgList;
		}

		Field[] superField=obj.getClass().getSuperclass().getDeclaredFields();
		Field[] childField = obj.getClass().getDeclaredFields();
		Field[] sourceField = new Field[superField.length+childField.length];
		
		System.arraycopy(superField, 0, sourceField, 0, superField.length);
		System.arraycopy(childField, 0, sourceField, superField.length, childField.length);
		
		for (Field field : sourceField) {
			field.setAccessible(true);
			String fieldName = field.getName();
			if ("sign".equalsIgnoreCase(fieldName) || "serialVersionUID".equalsIgnoreCase(fieldName)) {
				continue;
			}
			String getMethodName = "get" + toFirstLetterUpperCase(field.getName());
			try {
				Object fieldVal = obj.getClass().getMethod(getMethodName).invoke(obj);
				if (null != fieldVal && !"".equals(fieldVal+"")) {
					msgList.add(fieldName + "=" + (fieldVal + ""));
				}
			} catch (Exception e) {
				logger.info("objToList Exception:", e);
			}
		}
		return msgList;
	}

	private static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
	}
}
