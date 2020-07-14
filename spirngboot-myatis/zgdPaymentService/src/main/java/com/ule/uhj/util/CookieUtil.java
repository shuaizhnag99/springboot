package com.ule.uhj.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class CookieUtil {

	protected static Logger log = Logger.getLogger(CookieUtil.class);
	private static String appkey = null;
	private static String version_no = null;
	private static String appkey_url = null;
	static {
		InputStream resourceAsStream = CookieUtil.class
				.getResourceAsStream("/cookie_conf.properties");
		Properties p = new Properties();
		try {
			p.load(resourceAsStream);
		} catch (IOException e) {
			log.error("init bangzg cookie error!", e);
		}
		appkey = p.getProperty("API_COOKIE_DEC_APPKEY");
		version_no = p.getProperty("API_COOKIE_DEC_VERSION_NO");
		appkey_url = p.getProperty("API_COOKIE_DEC_URL");

		System.out.println(appkey);
		System.out.println(version_no);
		System.out.println(appkey_url);
	}

	/**
	 * 获取解密cookie
	 * 
	 * @param name
	 * @param clientType
	 * @return
	 */
	public static String cookieDec(String name, String clientType,
			String mobileCookie) {
		String data = "";
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("appkey", appkey);
			headers.put("version_no", version_no);
			Map<String, String> params = new HashMap<String, String>();
			params.put("name", name);
			params.put("clientType", clientType);
			params.put("mobileCookie", mobileCookie);
			log.info("headers:" + JSONObject.fromObject(headers).toString()
					+ ", params:" + JSONObject.fromObject(params).toString());
			String result = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, 3000);
			log.info("cookieDec:result=" + result);
			if (isSucJson(result)) {
				data = getString(JSONObject.fromObject(result), "data");
			}
		} catch (Exception e) {
			log.error("cookieDec error:" + e);
		}
		return data;
	}

	public static boolean isSucJson(String s) {
		if (!isJson(s)) {
			return false;
		}
		JSONObject json = JSONObject.fromObject(s);
		if (json.getString("returnCode").equals("9999")) { // api接口系统异常
			return false;
		}
		return true;
	}

	// 判断是否是json
	public static boolean isJson(String s) {
		if (s != null && s.startsWith("{") && s.endsWith("}")) {
			return true;
		}
		return false;
	}

	public static String getString(JSONObject o, String name) {
		if (null == o || null == name) {
			return null;
		}
		if (o.containsKey(name)) {
			return o.getString(name);
		}
		return null;
	}

	// cookie中获取不到，就到request获取attribute
	public static String getCookie(HttpServletRequest request, String name) {
		String c = getCookieInfo(request, name);
		if (null == c || "".equals(c)) {
			c = String.valueOf(request.getAttribute(name));
		}
		if (null == c || "null".equals(c)) {
			c = "";
		}
		return c;
	}

	public static String getCookieInfo(HttpServletRequest request, String name) {
		Cookie[] cookies = null;
		if (request != null) {
			cookies = request.getCookies();
		}
		String cookieInfo = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null) {
					if (cookie.getName().equals(name)) {
						cookieInfo = cookie.getValue();
					}
				}
			}
		}
		return cookieInfo;
	}

	public static void main(String[] args) {
		new CookieUtil();
	}
}
