package com.ule.uhj.sldProxy.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;









import net.sf.json.JSONObject;

//import org.apache.axis.utils.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
    
    private HttpClientUtil(){};

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);
    private static int connectionTimeout = 3000000;
    private static int soTimeout = 1000000;
    
    public static HttpClientUtil getInstance(){
    	return new HttpClientUtil();
    }
    
    public static String httpGet(String url){  
        logger.info("httpGet:"+url);  
        HttpClient client = new HttpClient();  
        GetMethod method = new GetMethod(url);    
        String result = null;  
        try {  
            client.executeMethod(method);  
            int status = method.getStatusCode();  
            if (status == HttpStatus.SC_OK) {  
                result = method.getResponseBodyAsString();  
            } else {  
                logger.error("Method failed: " + method.getStatusLine());  
            }  
        } catch (HttpException e) {  
            // 发生致命的异常，可能是协议不对或者返回的内容有问题  
            logger.error("Please check your provided http address!");  
            logger.error(e,e);
        } catch (IOException e) {  
            // 发生网络异常  
            logger.error("network error！");  
            logger.error(e,e);  
        } finally{  
            // 释放连接  
            if(method!=null)method.releaseConnection();  
            method = null;  
            client = null;  
        }  
        return result;  
    }  
     
    /**
     * http post form
     * @param url
     * @param paramMap
     * @param code
     * @return
     */
    public static String httpPost(String url, Map<String, ?> paramMap, String code) {  
        logger.info("httpPost:"+url);  
        StringBuffer content = new StringBuffer();  
        logger.info("send httpPost[" + url + "]" + ",param" + paramMap);
        HttpClient httpClient = new HttpClient();  
        PostMethod method = new PostMethod(url);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        if(paramMap != null && !paramMap.isEmpty()){
            for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
                method.addParameter(new NameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }
        try {  
            httpClient.executeMethod(method);  
            logger.info(method.getStatusLine());  
            InputStream inputStream = method.getResponseBodyAsStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,code));
            String buffer = new String();
            while((buffer=in.readLine())!=null){
            	content.append(buffer);
            }
        } catch (Exception e) {  
            logger.error("send httpPost[" + url + "]" + ",param" + (null!=paramMap?paramMap.toString():null), e);  
        } finally {  
            method.releaseConnection();  
        }
        logger.info("http access ok[" + url + "] ，result[" + content + "]");
        return content.toString();  
    }  
    
  
    public static String httpPost(String url, Map<String, ?> paramMap) {  
        return HttpClientUtil.httpPost(url, paramMap, "UTF-8");  
    }  
    
    /**
     * http post json
     * @param url
     * @param paramMap
     * @param code
     * @return
     */
    public static String httpPostJson(String url, Map<String, ?> paramMap, String code) {  
        StringBuffer content = new StringBuffer();  
        HttpClient httpClient = new HttpClient();  
        PostMethod method = new PostMethod(url);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        String requestStr = null;
        try {
        	//外部参数转为Json字符串
        	RequestEntity requestEntity;
        	requestStr = getJsonStringFromMap(paramMap);
			requestEntity = new StringRequestEntity(requestStr,"application/json", code);
			method.setRequestEntity(requestEntity);
			logger.info("send httpPost[" + url + "]" + ",param" + requestStr );
            httpClient.executeMethod(method);  
            logger.info("response statusLine:" + method.getStatusLine());  
            InputStream inputStream = method.getResponseBodyAsStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,code));
            String buffer = new String();
            while((buffer=in.readLine())!=null){
            	content.append(buffer);
            }
            logger.info("http access response content:" + content.toString());
        } catch (Exception e) {  
            logger.error("execute error!", e);  
        } finally {  
            method.releaseConnection();  
        }
        return content.toString();  
    }  
    
//    public static void main(String[] args) {
//		//http://igateway.wolaidai.com:18080/underwriting/api/youle/income_loan/
//    	httpPostJson("http://igateway.wolaidai.com:18080/underwriting/api/youle/income_loan/", null, "UTF-8");
//	}
    
    /**
     * http post file
     * @param url
     * @param paramMap
     * @param code
     * @param file
     * @return
     */
    public static String httpPostFile(String url, Map<String, ?> paramMap, String code,File file) {  
        logger.info("httpPost:"+url);  
        String content = new String();  
        logger.info("send httpPost[" + url + "]" + ",param" + paramMap.toString() );
        try (DefaultHttpClient httpClient = new DefaultHttpClient()){
        	 HttpPost postMethod = new HttpPost(url);
             httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, code);
        	MultipartEntity requestEntity = new MultipartEntity();
        	FileBody fileBody = new FileBody(file);
        	StringBody stringBody = new StringBody(getJsonStringFromMap(paramMap));
        	logger.info("json : "+getJsonStringFromMap(paramMap));
        	
        	requestEntity.addPart("message", stringBody);
        	requestEntity.addPart("file", fileBody);
        	
        	postMethod.setEntity(requestEntity);
        	HttpResponse response = httpClient.execute(postMethod);
            logger.info(response.getStatusLine().getStatusCode());  
            content = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {  
            logger.error("send httpPost[" + url + "]" + ",param =" + paramMap.toString()+",File ="+file.getName(), e);  
        }
        logger.info("http access ok[" + url + "] ，result[" + content + "]");
        return content;  
    }  
    
    public static String httpPostJson(String url, Map<String, ?> paramMap) {  
        return HttpClientUtil.httpPostJson(url, paramMap, "UTF-8");  
    } 
    
    public static String httpPostFile(String url,Map<String,?> paramMap,File file){
    	return HttpClientUtil.httpPostFile(url, paramMap,"UTF-8",file);  
    }
    
    /**
	 * map 对象转换为json 字符串
	 * @param map
	 * @return String json
	 */
	public static String getJsonStringFromMap(Map map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
}