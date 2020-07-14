package com.ule.uhj.provider.yitu.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 这个类主要是微软认知服务中的计算机视觉
 * 
 * @author zhaojie
 * 
 */
public class StoreInnerAnalysis {
	private static Log log = LogFactory.getLog(StoreInnerAnalysis.class);
	
	public static final String KEY = "73c4f8dbf157452083241c2955ff1067";
	public static final String KEY2 = "dbb93757e638447db51c9b9c449452cf";

	public static final String ANALYZE_IMAGE =PropertiesHelper.getDfs("ANALYZE_IMAGE"); 

	protected static  HttpClient httpClient =new HttpClient();
	
	
	
	 /**
     * 解析图片，URL为参数解析，图片必须是网络图片不能是本地图片，结果结果不全。
     * @param url
     * @param paramMap
	 * @throws Exception 
     */
	public static String  getStoreInnerAnalysis(Map<String, Object> paras) throws Exception{
		String url=(String) paras.get("url");
		log.info("getStoreInnerAnalysis url:"+url);
		URIBuilder builder = new URIBuilder(ANALYZE_IMAGE);
		builder.setParameter("visualFeatures", "Categories,Tags,Description");
		URI uri = builder.build();
		StringEntity reqEntity = new StringEntity("{\"Url\":\"" + url
				+ "\"}");
		String content="";
		try (DefaultHttpClient httpClient = new DefaultHttpClient()) {
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader("Content-Type", "application/json");
			postMethod.addHeader("Ocp-Apim-Subscription-Key", KEY);
			postMethod.setEntity(reqEntity);
			
			HttpResponse response = httpClient.execute(postMethod);
			content = EntityUtils.toString(response.getEntity());
		}
		log.info("getStoreInnerAnalysis content:"+content);
		return content;
	}

//	public static void main(String[] args) throws Exception {
//		Map<String, Object> paras = new HashMap<String, Object>();
////		String url="http://file.ule.com/file/app_uhj/uppic5141682/store14633306708088853.jpg";
//		String url="http://pic.ule.com/pic/user_5153985/product/prd20160121/store1453359399776.jpg";
////		String url="http://pic.beta.ule.com/pic/user_10000000391/product/prd20160116/store1452933193511.jpg";
//		paras.put("url", url);
//		getStoreInnerAnalysis(paras);
//	}
}