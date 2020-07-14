package com.ule.uhj.sldProxy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
   
	private static Logger log = Logger.getLogger(PropertiesUtil.class);
	
	private static Properties p;
	
	private static synchronized Properties getInstance(){
		if(PropertiesUtil.p == null){
			Properties p = new Properties();
			InputStream in = null;
			try {
				in = PropertiesUtil.class.getResourceAsStream("/uhjSecurity.properties");
				p.load(in);
				PropertiesUtil.p = p;
			}catch (Exception e){
				log.error("读取配置文件uhjSecurity.properties时发生异常。", e);
			}finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						log.error("Exception error.", e);
					}
					in = null;
				}
			}
		} 
		return PropertiesUtil.p;
	}
	
	/**
	 * 对外提供唯一查询方法
	 * @param name
	 * @return
	 */
	public static String getPropertyValue(String name){
		String propertyValue = PropertiesUtil.getInstance().getProperty(name);
		return propertyValue;
	}
	
}
