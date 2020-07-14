package com.ule.uhj.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.web.util.Constants;
import com.ule.web.util.Tools;

public class CommonHelper {

	private static Log log = LogFactory.getLog(CommonHelper.class);
	
	public static  String getUserOnlyId(HttpServletRequest request) throws Exception {
		return "10000029150";//"10000021963"可借款;//"10000020957";//10000022142
		/*String usronlyid = Tools.getCookieValueByName(request, Constants.GLOBAL_COOKIE_NAME_USRONLYID);
		log.info("getUserOnlyId usronlyid:"+usronlyid);
		if (usronlyid != null && !"".equals(usronlyid)) {
			return Long.valueOf(usronlyid).toString();
		} else {
			throw new Exception("用户ID无法获取，请重新登录！");
		}*/
		
	}
	
	public static  String getBangZGId(HttpServletRequest request) throws Exception {
//		return "222";
		String mobileCookie = CookieUtil.getCookie(request, "mobileCookie");
		String bangZGId = CookieUtil.cookieDec("mobileCookie", "yzs", mobileCookie);
		log.info("getBangZGId bangZGId:"+bangZGId);
		if (!Check.isBlank(bangZGId)) {
			return bangZGId;
		} else {
			throw new Exception("邮助手地推ID无法获取，请重新登录！");
		}
		
	}
}
