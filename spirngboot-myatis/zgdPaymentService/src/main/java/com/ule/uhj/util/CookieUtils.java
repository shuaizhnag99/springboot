package com.ule.uhj.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import  com.ule.uhj.util.MD5Util;

public class CookieUtils {
	
	private final static Logger ERROR = LoggerFactory.getLogger(CookieUtils.class);
	
	public static void addCookie(HttpServletResponse res, String name,
			String value) throws UnsupportedEncodingException {
		Cookie cookie = null;

		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			cookie = new Cookie(name, AesUtils.encrypt(value));
			cookie.setPath("/");
			cookie.setDomain(".ule.com");
			//cookie 保存30天
			cookie.setMaxAge(86400*30);
			res.addCookie(cookie);
		} catch (Exception arg4) {
			// arg4.printStackTrace();
		}

	}
	
	
	public static String createCookieInfo(String serialId, String token, int seconds, String proname) {
		String cookieInfo = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(serialId).append("_").append(token);
			cookieInfo = MD5Util.string2MD5(sb.toString());
			RedisUtils.set(serialId, cookieInfo, seconds, proname);
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}

		return cookieInfo;
	}

	public static String getCookieValue(HttpServletRequest req, String name)
			throws UnsupportedEncodingException {
		Cookie c = null;
		String value = "";

		try {
			c = getCookie(req, name);
			if (c != null) {
				return c.getValue();
			}
		} catch (Exception arg4) {
			// arg4.printStackTrace();
		}

		return c == null ? "" : URLDecoder.decode(value, "UTF-8");
	}
	
	public static String getDecryptCookieValue(HttpServletRequest req, String name)
			throws UnsupportedEncodingException {
		Cookie c = null;
		String value = "";

		try {
			c = getCookie(req, name);
			if (c != null) {
				value = AesUtils.decrypt(c.getValue());
			}
		} catch (Exception arg4) {
			ERROR.error(arg4.getMessage(), arg4);
		}

		return c == null ? "" : URLDecoder.decode(value, "UTF-8");
	}
	
	public static Boolean verifyCookieInfo(String serialId, String cookieInfo) {
		Boolean flag = Boolean.valueOf(false);
		try {
			if (null != serialId && null != cookieInfo) {
				String str = (String) RedisUtils.getHighObject(serialId);
				if (null != str && cookieInfo.equals(str)) {
					flag = Boolean.valueOf(true);
				}
			}
		} catch (Exception arg4) {
			ERROR.error(arg4.getMessage(), arg4);
		}
		return flag;
	}
	
	public static Boolean verifyCookieInfo(String serialId, String cookieInfo, String proname) {
		Boolean flag = Boolean.valueOf(false);
		try {
			if (null != serialId && null != cookieInfo) {
				String str = (String) RedisUtils.getHighObject(serialId, proname);
				if (null != str && cookieInfo.equals( str)) {
					flag = Boolean.valueOf(true);
				}
			}
		} catch (Exception arg4) {
			ERROR.error(arg4.getMessage(), arg4);
		}
		return flag;
	}
	
	

	public static Cookie getCookie(HttpServletRequest req, String name)
			throws UnsupportedEncodingException {
		name = URLEncoder.encode(name, "UTF-8");
		Cookie[] cs = req.getCookies();
		if (cs != null) {
			Cookie[] arr$ = cs;
			int len$ = cs.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				Cookie c = arr$[i$];
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}

		return null;
	}

	public static void removeCookies(HttpServletResponse res, String name) {
		Cookie cookie = null;

		try {
			name = URLEncoder.encode(name, "UTF-8");
			cookie = new Cookie(name, (String) null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			cookie.setDomain(".ule.com");
			res.addCookie(cookie);
		} catch (Exception arg3) {
			// arg3.printStackTrace();
		}

	}

	public static void addCookieByAge(HttpServletResponse res, String name,
			String value, Integer maxAge) throws UnsupportedEncodingException {
		Cookie cookie = null;

		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			cookie = new Cookie(name, AesUtils.encrypt(value));
			cookie.setPath("/");
			cookie.setMaxAge(maxAge.intValue());
			res.addCookie(cookie);
		} catch (Exception arg5) {
			// arg5.printStackTrace();
		}

	}
}
