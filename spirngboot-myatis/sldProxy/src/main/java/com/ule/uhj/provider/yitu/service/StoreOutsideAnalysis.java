package com.ule.uhj.provider.yitu.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.ule.uhj.provider.yitu.util.HttpUtils;
import com.ule.uhj.provider.yitu.util.ImgUrlUtils;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

public class StoreOutsideAnalysis {
	
	private static Log log = LogFactory.getLog(StoreInnerAnalysis.class);
	
	private static String appcode = "d253424f092b48169ecc505a531b942c";
	
	
	public static String aliOcrSignatureByUrl(Map<String, Object> paras)
			throws Exception {
		String imgUrl = (String) paras.get("url");
		imgUrl = ImgUrlUtils.toInnerImgUrl(imgUrl);
		log.info("aliOcrSignatureByUrl begin imgUrl:"+imgUrl);
		String base64String = "";

		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(imgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();
			outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = is.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			base64String = Base64.encodeBase64String(outStream.toByteArray());
		} catch (Exception e) {
			log.error("Exception error.", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Exception error.", e);
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					log.error("Exception error.", e);
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}
		String host = PropertiesHelper.getDfs("ocr_host");
		String path = "/rest/160601/ocr/ocr_shop_sign.json";
		String method = "POST";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, Object> querys = new HashMap<String, Object>();
		String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\""
				+ base64String + "\"}}]}";
		HttpResponse response = HttpUtils.doPost(host, path, method, headers,
				querys, bodys);
		HttpEntity entity = response.getEntity();
		String entityStr = EntityUtils.toString(entity);
		log.info("aliOcrSignatureByUrl entityStr:" + entityStr);
		return entityStr;
	}
	
	/*public  static String aliOcrSignature(Map<String, Object> paras) throws Exception {
		log.info("aliOcrSignature begin");
		String base64String=(String) paras.get("base64String");
		
		String host = "https://dm-54.data.aliyun.com";
	    String path = "/rest/160601/ocr/ocr_shop_sign.json";
	    String method = "POST";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, Object> querys = new HashMap<String, Object>();
	    String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\"" + base64String + "\"}}]}";
    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
    	HttpEntity entity = response.getEntity();
    	String entityStr = EntityUtils.toString(entity);
    	log.info("aliOcrSignature entityStr:"+entityStr);
    	return entityStr;
	}*/
	
//	public static void main(String[] args) throws Exception {
//		Map<String, Object> paras = new HashMap<String, Object>();
////		String imgUrl="http://pic.ule.com/pic/user_5153985/product/prd20160121/store1453359399776.jpg";
//		String imgUrl="http://pic.ule.com/pic/user_5154245/product/prd20160129/store1454071890671.jpg";
//		
//		paras.put("url", imgUrl);
//		aliOcrSignatureByUrl(paras);
//	}

	
}
