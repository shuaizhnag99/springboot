//package com.ule.uhj.sldProxy.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.ule.memcached.source.MemCachedClient;
//import com.ule.memcached.source.SockIOPool;
//
//public class CacheUtils {
//	private static MemCachedClient ttCacheClientNew;
//	private static MemCachedClient memCacheClientNew;
//	static {
//		// 新的TT
//		String  cachename = "sldProxyTT";
//		SockIOPool pool = SockIOPool.getInstance(cachename);
//		pool.setServers(SldConfig.getProperty("GLOBAL_TT_SERVER").split(","));
//		pool.setInitConn(2);
//		pool.setMinConn(2);
//		pool.setMaxConn(10);
//		pool.setNagle(false);
//		pool.initialize();
//		ttCacheClientNew = new MemCachedClient(cachename);
//		ttCacheClientNew.setCompressEnable(false);
//		
//		//新的mercache
//		cachename = "sldProxyMem";
//		pool = SockIOPool.getInstance(cachename);
//		pool.setServers(SldConfig.getProperty("GLOBAL_MEMCACHE_SERVER").split(","));
//		pool.setInitConn(2);
//		pool.setMinConn(2);
//		pool.setMaxConn(10);
//		pool.setNagle(false);
//		pool.initialize();
//		memCacheClientNew = new MemCachedClient(cachename);
//		memCacheClientNew.setCompressEnable(false);
//	}
//	
//	public  static MemCachedClient getTTClient(){
//		return ttCacheClientNew;
//	}
//	
//	public  static MemCachedClient getMemCachClient(){
//		return memCacheClientNew;
//	}
//	
//}
