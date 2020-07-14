package com.ule.uhj.provider.yitu.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ule.uhj.sldProxy.util.PropertiesHelper;

public class FaceDetection {
	private static Log log = LogFactory.getLog(StoreInnerAnalysis.class);
	
	public static final String KEY = "caddf75cad75474f87e43163d237b994";
	public static final String KEY2 = "2b2c8d59bff5454fa5810ef885457e2c";

	public static final String Face_Detection_Url =PropertiesHelper.getDfs("Face_Detection_Url"); 
	
	public static String  faceDetectionAnalysis(Map<String, Object> paras) throws Exception{
		log.info("faceDetectionAnalysis paras:"+paras);
		String url=(String) paras.get("url");
		URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect");
//		builder.setParameter("returnFaceId", "true");
//        builder.setParameter("returnFaceLandmarks", "false");
//        builder.setParameter("returnFaceAttributes", "{}");


		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Ocp-Apim-Subscription-Key", KEY);

		// Request body
		StringEntity reqEntity = new StringEntity("{\"url\":\"" + url
				+ "\"}");
		request.setEntity(reqEntity);
		String content="";
		try (DefaultHttpClient httpClient = new DefaultHttpClient()) {
			HttpResponse response = httpClient.execute(request);
			content = EntityUtils.toString(response.getEntity());
			log.info("faceDetectionAnalysis content:"+content);
		}
		return content;
	}

//	public static void main(String[] args) throws Exception {
//		Map<String, Object> paras = new HashMap<String, Object>();
//		String url="http://pic.ule.com/pic/user_1003498804/product/prd20170714/app_storeoutside14999942808295453.jpg";
////		String url="http://pic.ule.com/pic/user_5153985/product/prd20160121/store1453359399776.jpg";
////		String url="http://file.ule.com/file/app_uhj/uppic5141682/store14633306708088853.jpg";
////		String url="http://pic.ule.com/pic/user_1006135739/product/prd20170707/app_idcardpositive14994092105219651.jpg";
//		paras.put("url", url);
//		faceDetectionAnalysis(paras);
//	}
}