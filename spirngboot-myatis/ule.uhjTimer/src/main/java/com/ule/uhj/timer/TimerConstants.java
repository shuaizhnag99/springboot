package com.ule.uhj.timer;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TimerConstants {
   
	private static Logger log = Logger.getLogger(TimerConstants.class);
	
	public static final String ifadminHost ;
	
	public static final String lendMerchantHost ;
	
	public static final String chinaPostHost;
	
	public static final String lendvpsHost;
	
    private static Hashtable<String, String> map = new Hashtable<String, String>();
	
	/*初始化APPKEY的键值集合*/
    static{
    	Properties appProperties = new Properties();
    	try {
//    		log.info("Loading TimerConstants configuration ...... ");
    		appProperties.load(TimerConstants.class.getResourceAsStream("/SldUrl.properties"));
    		appProperties.load(TimerConstants.class.getResourceAsStream("/UrlHost.properties"));
    		appProperties.load(TimerConstants.class.getResourceAsStream("/UhjSecurity.properties"));
    		for(Entry<?, ?> entry:appProperties.entrySet()){
    			map.put((String)entry.getKey(), (String)entry.getValue());
//    			log.info(entry.getKey() + " : " + entry.getValue());
			}
//    		log.info("Loaded TimerConstants size : " + map.size());
		} catch (Exception e) {
			log.error("error", e);
		}
    	ifadminHost = map.get("ifadmin_base");
    	lendMerchantHost = map.get("lendMerchant_base");
    	chinaPostHost = map.get("chinaPost_base");
    	lendvpsHost = map.get("lendvps_base");
    }
	
	/**
	 * 对外提供唯一查询方法
	 * @param name
	 * @return
	 */
	public static String get(String name){
		log.info(String.format("key:%s, value:%s",  name, map.get(name)));
		return map.get(name);
	}
	
	public static void main(String[] args) {
		System.out.println(get("batchSmsTemplateUrl"));
		System.out.println(TimerConstants.ifadminHost);
	}
}
