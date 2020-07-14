/**
 * 
 */
package com.ule.uhj.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class AppSecurityHelper {
	static Logger log = Logger.getLogger(AppSecurityHelper.class);
	/*配置文件名称*/
    private static String CONFIG_FILE="/csWebAppKey.properties";
    
    /*APPKEY的键值集合*/
    private static Map<String, String> appSecurityMap = new HashMap<String, String>();
    
    /*初始化APPKEY的键值集合*/
    static{
    	Properties appProperties = new Properties();
    	try {
    		log.info("Loading AppSecurity configuration ...... ");
    		appProperties.load(AppSecurityHelper.class.getResourceAsStream(CONFIG_FILE));
    		for(Entry entry:appProperties.entrySet()){
    			appSecurityMap.put((String)entry.getKey(), (String)entry.getValue());
			}

    		log.info("Loaded AppSecurity size : " + appSecurityMap.size());
		} catch (FileNotFoundException e) {
			log.error("error", e);
		} catch (IOException e) {
			log.error("error", e);
		}
    }
    
    public static String getAppSecret(String appKey){
    	String appSecret = appSecurityMap.get(appKey);
    	return appSecret;
    }
}
