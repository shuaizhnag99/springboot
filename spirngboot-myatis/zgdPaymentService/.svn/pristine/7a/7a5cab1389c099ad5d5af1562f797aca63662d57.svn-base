package com.ule.uhj.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.lettuce.utils.CacheCloudUtils;
import com.ule.lettuce.utils.CompressUtils;
import com.ule.lettuce.utils.SerializeHelper;
import com.ule.lettuce.utils.UleLettuce;

public class RedisUtils {

	private final static Logger ERROR = LoggerFactory.getLogger(RedisUtils.class);
	
	private static int defultExpireTime = 30 * 24 * 3600;
	private static UleLettuce lettuctUtils = CacheCloudUtils.build();

	public static String get(String key) {
		String result = null;
		try {
			result = lettuctUtils.forString().get(key);
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}
		return result;
	}

	public static String get(String key, String proname) {
		String result = null;
		try {
			UleLettuce proNamelettuctUtils = CacheCloudUtils.build(proname);
			result = proNamelettuctUtils.forString().get(key);
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}
		return result;
	}
	/**
	 *
	 * @param key
	 * @param value
	 */
	public static String set(String key, String value) {
		String result = null;
		try {
			result = lettuctUtils.forString().set(key, value);
		} catch (Exception e) {
			ERROR.error("redis set error:", e);
		}
		return result;
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @param seconds 过期时间（单位：秒）
	 */
	public static String setex(String key, String value, int seconds) {
		String result = null;
		try {
			result = lettuctUtils.forString().setEx(key, seconds, value);
		} catch (Exception e) {
			ERROR.error("redis setex error:", e);
		}
		return result;
	}

	public static void set(String key, Object value, int seconds, String proname) {
		try {
			UleLettuce proNamelettuctUtils = CacheCloudUtils.build(proname);
			byte[] e;
			byte[] compressBytes;
			if (seconds <= 0) {
				e = SerializeHelper.serialize(value);
				compressBytes = CompressUtils.fastCompress(e);
				if (null != e) {
					proNamelettuctUtils.forGeneralOpt().set(key, compressBytes, defultExpireTime);
				}
			} else {
				e = SerializeHelper.serialize(value);
				compressBytes = CompressUtils.fastCompress(e);
				if (null != e) {
					proNamelettuctUtils.forGeneralOpt().set(key, compressBytes, seconds);
				}
			}
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}

	}

	public static String hget(String key, String field) {
		try {
			return lettuctUtils.forHash().hget(key, field);
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}
		return null;
	}

	public static Map<String, String> hgetAll(String key) {
		try {
			return lettuctUtils.forHash().hgetAll(key);
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}
		return null;
	}

	public static Object getHighObject(String key) {
		Object bean = null;
		try {
			byte[] bytes = lettuctUtils.forGeneralOpt().getObjectBytes(key);
			byte[] e = CompressUtils.deCompress(bytes);
			if (null != bytes) {
				bean = SerializeHelper.unserialize(e);
			}
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}

		return bean;
	}

	public static Object getHighObject(String key, String proname) {
		byte[] bytes;
		Object bean = null;
		try {
			UleLettuce proNamelettuctUtils = CacheCloudUtils.build(proname);
			bytes = proNamelettuctUtils.forGeneralOpt().getObjectBytes(key);
			byte[] recoverBytes = CompressUtils.deCompress(bytes);
			if (null != bytes) {
				bean = SerializeHelper.unserialize(recoverBytes);
			}
		} catch (Exception e) {
			ERROR.error(e.getMessage(), e);
		}
		return bean;
	}
	
	
	
}
