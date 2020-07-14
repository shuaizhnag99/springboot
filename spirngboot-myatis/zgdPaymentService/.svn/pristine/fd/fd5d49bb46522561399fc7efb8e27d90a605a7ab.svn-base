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
public class PropertiesHelper {
	static Logger log = Logger.getLogger(PropertiesHelper.class);
    
    /*APPKEY的键值集合*/
    private static Map<String, String> dfsMap = new HashMap<String, String>();
    
    /*APPKEY的键值集合*/
    private static Map<String, String> securityMap = new HashMap<String, String>();
    
    /*初始化APPKEY的键值集合*/
    static{
    	Properties appProperties = new Properties();
    	Properties securityProperties = new Properties();
    	try {
    		log.info("Loading dfs configuration ...... ");
    		appProperties.load(PropertiesHelper.class.getResourceAsStream("/dfs_server.properties"));
    		securityProperties.load(PropertiesHelper.class.getResourceAsStream("/UhjSecurity.properties"));
    		for(Entry entry : appProperties.entrySet()){
    			dfsMap.put((String)entry.getKey(), (String)entry.getValue());
    			log.info(entry.getKey() + " : " + entry.getValue());
			}
    		for(Entry entry : securityProperties.entrySet()){
    			securityMap.put((String)entry.getKey(), (String)entry.getValue());
//    			log.info(entry.getKey() + " : " + entry.getValue());
			}
    		log.info("Loaded securityKey size : " + securityMap.size());
    		log.info("Loaded dfs size : " + dfsMap.size());
		} catch (FileNotFoundException e) {
			log.error("error", e);
		} catch (IOException e) {
			log.error("error", e);
		}
    }
    
    public static String getDfs(String key){
    	return dfsMap.get(key);
    }
    
    public static String getSecurityKey(String key){
    	return securityMap.get(key);
    }
}
