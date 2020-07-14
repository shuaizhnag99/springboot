package com.ule.uhj.pixiao.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 秘钥管理类
 * get方法 
 * 入参key为接入方的channel
 * 出参为对应的秘钥
 */
public class UhjSecurityProperty {

	private static Properties p = new Properties();
	
	static {
		init();
	}

	/**
	 * 载入所有配置文件
	 */
	public static void init() {
		InputStream inputStream = UhjSecurityProperty.class.getResourceAsStream("/UhjSecurity.properties");
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			//  e1.printStackTrace();
		}
	}

	/**
	 * 读取配置文件中内容
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return p.getProperty(key);
	}

}
