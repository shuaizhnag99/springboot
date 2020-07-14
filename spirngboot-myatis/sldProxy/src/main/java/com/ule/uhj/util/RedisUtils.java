package com.ule.uhj.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.lettuce.utils.CacheCloudUtils;
import com.ule.lettuce.utils.UleLettuce;

public class RedisUtils {
    private static final Log log = LogFactory.getLog(RedisUtils.class);
    
    private static UleLettuce lettuctUtils= CacheCloudUtils.build();

    public static String get(String key) {
		String result = null;
		try {
			result = lettuctUtils.forString().get(key);
		} catch (Exception e) {
			log.error("redis get error:", e);
		} finally {
		}
		return result;
	}

    public static void set(String key, String value) {
		try {
			lettuctUtils.forString().set(key, value);
		} catch (Exception e) {
			log.error("redis set error:", e);
		}
	}

    /**
     *
     * @param key
     * @param value
     * @param seconds 过期时间
     */
    public static void set(String key, String value, int seconds) {
        try {
        	lettuctUtils.forGeneralOpt().set(key, value, seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
