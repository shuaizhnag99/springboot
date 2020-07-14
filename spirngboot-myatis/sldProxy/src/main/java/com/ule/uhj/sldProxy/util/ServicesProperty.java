package com.ule.uhj.sldProxy.util;

import java.util.Properties;

import org.apache.log4j.Logger;

public class ServicesProperty {

	private static Logger logger = Logger.getLogger(ServicesProperty.class);

	private static String propertiesPath = "/conf";
	private static String propertiesFile = "/system.properties";
	
	public static final int CACHE_DAYS = 30;
	public static final int DAY_S = 86400;
	public static final int DAY_MS = 86400000;
	public static final Integer DEFAULT_MONTH = 2592000;

	/** 
	* @Fields EWEURL : 亿微接口URL
	*/ 
	protected static String EWEURL = null;
	private static String EWE_URL_KEY = "eweUrl";

	private static final String VERSION_NO = "VERSION_NO";
	protected static String versionNo = null;
	
	private static final String USERNAME_KEY = "username";
	protected static String USERNAME = null;
	
	private static final String PASS_WORD_KEY = "password";
	protected static String PASSWORD = null;
	
	private static final String REDIS_KEY = "redisappid";
	protected static String REDIS_APPID = null;
	
	protected static String INTERFACE_PREFIX = null;
	
	public static final String BR_USERNAME = PropertiesHelper.getDfs("br_username");// 百融商户用户名
	public static final String BR_PASSWORD = PropertiesHelper.getDfs("br_pwd");// 百融商户密码
	public static final String BR_API_CODE = PropertiesHelper.getDfs("br_api_code");// 百融唯一标示
	
	public static final String ALI_APP_CODE = PropertiesHelper.getDfs("ali_app_code");// 阿里appcode
	
	/**
	 * 同盾
	 */
	protected static String PARTNER_CODE = null;// 合作方标识
	protected static String PARTNER_KEY = null;// 合作方密钥
	protected static String PARTNER_APP = null;// 应用名
	protected static String SECRET_KEY = null;// 应用密钥
	protected static String EVENT_ID = null;// 事件标识
	protected static String SUBMIT_URL = null;// 提交申请url
	protected static String QUERY_URL = null;// 查询申请url
	protected static String MONITOR_URL = null;// 贷后添加监控url
	protected static String MONITOR_COLOSE_URL = null;// 贷后中止监控url
	protected static String MONITOR_EXTEND_URL = null; // 贷后延长监控
	protected static String MONITOR_LOCATION_URL = null; // 活动地检测查询
	protected static String MONITOR_RISK_LIST_URL = null; // 风险列表
	protected static String RISK_FRAUD_URL = null; // 风险决策
	protected static String RISK_DETAIL_URL = null; // 命中规则详情查询(解析版)
	protected static String RULE_DETAIL_URL = null; // 命中规则详情查询(展示)
	protected static String RULE_RESULT_URL = null; // 事件详情查询
	protected static String RISK_BASE_DETAIL_URL = null; // 基础数据详情查询
	protected static String RISK_DISCREDIT = null; // P2P失信数据反馈
	protected static String RISK_LIST_DATA = null; // 列表数据导入接口
	protected static String RISK_EVIDENCE = null; // 证据反馈
	
	public static final String ALIB_BANK_FOUR_URL = PropertiesHelper.getDfs("ali_bank_four_url"); // 阿里云银行卡四要素
	public static final String ALI_IDCARD_AUTH = PropertiesHelper.getDfs("ali_idCard_auth");
	
	static {
		Properties appProperties = new Properties();
		try {
			if (ServicesProperty.class.getResourceAsStream(propertiesFile) != null) {
				appProperties.load(ServicesProperty.class.getResourceAsStream(propertiesFile));
			} else if (ServicesProperty.class.getResourceAsStream(propertiesPath + propertiesFile) != null) {
				appProperties.load(ServicesProperty.class.getResourceAsStream(propertiesPath + propertiesFile));
			}
			versionNo = appProperties.getProperty(VERSION_NO);
			EWEURL = appProperties.getProperty(EWE_URL_KEY);
			USERNAME = appProperties.getProperty(USERNAME_KEY);
			PASSWORD = appProperties.getProperty(PASS_WORD_KEY);
			REDIS_APPID = appProperties.getProperty(REDIS_KEY);
			INTERFACE_PREFIX = appProperties.getProperty("interfacePrefix");
			/**
			 * 百融
			 */
			//BR_USERNAME = appProperties.getProperty("br_username");
			//BR_PASSWORD = appProperties.getProperty("br_pwd");
			//BR_API_CODE = appProperties.getProperty("br_api_code");
			
			//ALI_APP_CODE = appProperties.getProperty("ali_app_code");
			/**
			 * 同盾
			 */
			PARTNER_CODE = appProperties.getProperty("partner_code");
			PARTNER_KEY = appProperties.getProperty("partner_key");
			PARTNER_APP = appProperties.getProperty("partner_app");
			SECRET_KEY = appProperties.getProperty("secret_key");
			EVENT_ID = appProperties.getProperty("event_id");
			SUBMIT_URL = appProperties.getProperty("submit_url");
			QUERY_URL = appProperties.getProperty("query_url");
			MONITOR_URL = appProperties.getProperty("monitor_url");
			MONITOR_COLOSE_URL = appProperties.getProperty("monitor_colose_url");
			MONITOR_EXTEND_URL = appProperties.getProperty("monitor_extend_url");
			MONITOR_LOCATION_URL = appProperties.getProperty("monitor_location_url");
			MONITOR_RISK_LIST_URL = appProperties.getProperty("monitor_risk_list_url");
			RISK_FRAUD_URL = appProperties.getProperty("risk_fraud_url");
			RISK_DETAIL_URL = appProperties.getProperty("risk_detail_url");
			RULE_DETAIL_URL = appProperties.getProperty("rule_detail_url");
			RULE_RESULT_URL = appProperties.getProperty("risk_result_url");
			RISK_BASE_DETAIL_URL = appProperties.getProperty("risk_base_detail_url");
			RISK_DISCREDIT = appProperties.getProperty("risk_discredit");
			RISK_LIST_DATA = appProperties.getProperty("risk_list_data");
			RISK_EVIDENCE = appProperties.getProperty("risk_evidence");
			//ALIB_BANK_FOUR_URL = appProperties.getProperty("ali_bank_four_url");
			//ALI_IDCARD_AUTH = appProperties.getProperty("ali_idCard_auth");
		} catch (Exception e) {
			System.out.println("错误：解析配置文件异常，message=" + e.getMessage());
			logger.error("错误：解析配置文件异常，message=" + e.getMessage(), e);
		}
	}
	
}
