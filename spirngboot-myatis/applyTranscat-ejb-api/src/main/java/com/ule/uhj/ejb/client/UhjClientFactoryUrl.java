package com.ule.uhj.ejb.client;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class UhjClientFactoryUrl {
	
	private static Logger log = Logger.getLogger(UhjClientFactoryUrl.class);
	private static String NAMING_FACTORY_INITIAL="org.jnp.interfaces.NamingContextFactory";
	private static String NAMING_URL_PKG_PREFIXES="org.jboss.naming:org.jnp.interfaces";
	private Properties configProperties = null;
	private Context context;
	private static UhjClientFactoryUrl instance ;
	
	
	private UhjClientFactoryUrl(String remoteUrl){
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,NAMING_FACTORY_INITIAL);
		properties.put(Context.URL_PKG_PREFIXES, NAMING_URL_PKG_PREFIXES);
		properties.put(Context.PROVIDER_URL, remoteUrl);
		try {
			context = new InitialContext(properties);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public static UhjClientFactoryUrl getInstance(String remoteUrl){
		if (instance == null) {
			instance = new UhjClientFactoryUrl(remoteUrl);
		}
		return instance;
	}
	
	private Object getEjbClient(String jndiParam) {
		Object client = null;
		try {
			client = context.lookup(jndiParam);
		} catch (Exception ex) {
			log.error("getEjbClient error! " + ex.getMessage() , ex);
		}
		return client;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getHJClient(Class<T> clazz){
		return (T) getEjbClient(clazz.getSimpleName() + "Jndi");
	}
}
