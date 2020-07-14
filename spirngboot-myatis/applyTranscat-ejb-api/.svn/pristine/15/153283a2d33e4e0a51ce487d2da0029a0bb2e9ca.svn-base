package com.ule.uhj.ejb.client.zgd;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

/**
 * @author panxing
 *
 */
@Remote
@BeanName("ZgdCacheBean")
public interface ZgdCacheClient {

	void setWithExpire(String key, String value, int seconds) throws Exception;

	String get(String key);

	void remove(String key);

	<T> T getObjFromJsonStr(String key, Class<T> tc);

	void setExpireCurrDate(String key, String value) throws Exception;

	void set(String key, String value);

	void setJsonStr(String key, Object value);
	

}
