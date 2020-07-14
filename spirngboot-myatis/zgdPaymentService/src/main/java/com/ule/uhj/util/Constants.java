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
public class Constants {
	static Logger log = Logger.getLogger(Constants.class);
    
    /*APPKEY的键值集合*/
    private static Map<String, String> dfsMap = new HashMap<String, String>();
    
    /*初始化APPKEY的键值集合*/
    static{
    	Properties appProperties = new Properties();
    	try {
    		log.info("Loading dfs configuration ...... ");
    		appProperties.load(Constants.class.getResourceAsStream("/UhjBase.properties"));
    		for(Entry entry : appProperties.entrySet()){
    			dfsMap.put((String)entry.getKey(), (String)entry.getValue());
    			log.info(entry.getKey() + " : " + entry.getValue());
			}
    		log.info("Loaded dfs size : " + dfsMap.size());
		} catch (FileNotFoundException e) {
			log.error("error", e);
		} catch (IOException e) {
			log.error("error", e);
		}
    }
    
    public static String get(String key){
    	return dfsMap.get(key);
    }
    
}
