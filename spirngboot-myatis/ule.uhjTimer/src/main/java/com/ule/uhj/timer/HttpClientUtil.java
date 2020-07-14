package com.ule.uhj.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.ule.uhj.sld.util.SldSign;

public class HttpClientUtil {
	private static Log log = LogFactory.getLog(HttpClientUtil.class);

	public static String httpGet(String url, Map<String, String> paras) throws Exception{
	 
		CloseableHttpResponse response1=null;
		HttpEntity entity1 = null;
		try (CloseableHttpClient httpclient= HttpClients.createDefault();){
		log.info(String.format("httpGet --> url:%s, paras:%s", url, paras));
		
	 
		RequestConfig requestConfig = RequestConfig.custom()
//		        .setSocketTimeout(1000)
//		        .setConnectTimeout(1000)
		        .build();
		HttpGet httpget1 = new HttpGet(url);
		httpget1.setConfig(requestConfig);
		AtomicInteger count = new AtomicInteger(1);
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAttribute("count", count);
		response1 = httpclient.execute(httpget1, localContext);
		entity1 = response1.getEntity();
		} finally {
			if(null!=response1) {
				
				response1.close();
			}
			 
		}
		return entity1 == null ? "null" : EntityUtils.toString(entity1);
	}
	
	public static String httpPost(String url,  boolean signAble, String secret ,Map<String, String> paras) throws Exception{
		log.info(String.format("httpGet --> url:%s, paras:%s", url, paras));
    	CloseableHttpClient httpclient = getDefaultHttpClient();
    	CloseableHttpResponse response = null;
    	HttpEntity entity1 = null;
    	try {
    		HttpPost post = new HttpPost(url);
        	List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        	if(paras != null && paras.size() > 0){
        		for (String key : paras.keySet()) {
        			formparams.add(new BasicNameValuePair(key, paras.get(key)));
				}
        	}
        	if(signAble){
        		paras = SldSign.sign(paras, secret);
            	formparams.add(new BasicNameValuePair("uuid", paras.get("uuid"))); 
            	formparams.add(new BasicNameValuePair("sign", paras.get("sign")));
        	}
        	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        	post.setEntity(entity);
        	response = httpclient.execute(post);
		    entity1 = response.getEntity();
		} finally {
			if(null!=response) {
				
				response.close();
			}
		}
		return entity1 == null ? "null" : EntityUtils.toString(entity1);
	}
    	
    private static CloseableHttpClient getDefaultHttpClient(){
    	return HttpClients.createDefault();
    }

}
