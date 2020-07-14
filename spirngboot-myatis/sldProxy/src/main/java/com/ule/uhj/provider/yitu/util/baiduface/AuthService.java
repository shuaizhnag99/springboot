package com.ule.uhj.provider.yitu.util.baiduface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.sld.util.Check;
import com.ule.uhj.util.RedisUtils;

import net.sf.json.JSONObject;

public class AuthService {
	protected static Log log = LogFactory.getLog(AuthService.class);
	
	/**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
    	
    	//519179739@qq.com[  API Key=QQQhtjmnr72u1GafGzc3GbMb    Secret Key=Wek0aEVLxqGPugTAks2AoAVSplQ9WMN0]
    	//yg823207378[  API Key=    Secret Key=]
    	
    	
        // 官网获取的 API Key 更新为你注册的
        String clientId = "QQQhtjmnr72u1GafGzc3GbMb";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "Wek0aEVLxqGPugTAks2AoAVSplQ9WMN0";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
    	String access_token = RedisUtils.get("authToken"+ak+"_"+sk);
    	if(!Check.isBlank(access_token)) {//先查询缓存中是否存在
    		log.info("getAuth getCacheInfo success access_token="+access_token);
    		return access_token;
    	}
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        
        log.info("getAccessTokenUrl="+getAccessTokenUrl);
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            
            if("https".equalsIgnoreCase(realUrl.getProtocol())){
        	   SslUtils.ignoreSsl();
        	}
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            log.info("getAccessTokenUrl result:" + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            access_token = jsonObject.getString("access_token");
            //放入缓存，token三十天有效
            RedisUtils.set("authToken"+ak+"_"+sk, access_token,28*24*3600);
            log.info("return access_token="+access_token);
            return access_token;
        } catch (Exception e) {
            log.error("获取token失败！", e);
        }
        log.info("return access_token=now return");
        return null;
    }
    
    
//    public static void main(String[] args) {
//    	getAuth();
//	}
}