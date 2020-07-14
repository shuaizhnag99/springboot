package com.ule.uhj.sldProxy.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SldConfig {
	protected static Log log = LogFactory.getLog(SldConfig.class);
	private SldConfig(){}
	
	private static Properties p = new Properties();
	
	static {
		init();
	}
	
	private static void init(){
		InputStream inputStream = SldConfig.class.getResourceAsStream("/sldConfig.properties");
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			log.error("Exception error.", e1);
		}
	}
	
	public static String getProperty(String key){
		return p.getProperty(key);
	}
	
	
}