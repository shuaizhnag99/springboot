package com.ule.uhj.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.web.util.Constants;
import com.ule.web.util.Tools;

public class InitializeListener implements ServletContextListener {
	private static Log log = LogFactory.getLog(InitializeListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// 登录信息相关配置初始化
		log.info("****** system init, loadPlatformData start.....******");
		Tools.loadPlatformData();
		log.info("Constants.SIGN_MODULE_PATH:" + Constants.getSignModulePath());
		log.info("ULE.COOKIE.NAME  Constants.ULE_COOKIE_NAME:" + Constants.getUleCookieName());
		log.info("GLOBAL.COOKIE.NAME.USRONLYID  Constants.GLOBAL_COOKIE_NAME_USRONLYID:" + Constants.getGlobalCookieNameUsronlyid());
		log.info("GLOBAL.COOKIE.NAME.USRLOGINID Constants.GLOBAL_COOKIE_NAME_USRLOGINID:" + Constants.getGlobalCookieNameUsrloginid());
		log.info("****** system init, loadPlatformData end.....******");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
