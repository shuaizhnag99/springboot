package com.ule.uhj.provider.yitu.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import com.ule.uhj.sldProxy.util.Check;
import com.ule.uhj.sldProxy.util.Convert;

public class HttpUtils {
	
	/**
	 * get
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        System.out.println("send httpPost[" + host + "]" + ",param" + querys );
        return httpClient.execute(request);
    }
	
	public static HttpResponse doGet(String host,
			Map<String, Object> querys)
            throws Exception {  
    	HttpClient httpClient = wrapClient(host);
    	HttpResponse response = null;
    	try{
    		HttpGet request = new HttpGet(buildUrl(host, null, querys));
    		System.out.println("send httpPost[" + host + "]" + ",param" + querys );
    		response = httpClient.execute(request);
		}catch (Exception e) {
			System.out.println("doPost error!"+e.getMessage());
		} 
//    	finally{
//			if(httpClient!=null){
//				httpClient.getConnectionManager().shutdown();
//			}
//		}
		return response;
    }
	
	/**
	 * post form
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys, 
			Map<String, String> bodys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }	
	
	
	/**
	 * post form
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse sendPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys, 
			Map<String, Object> bodys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key,Convert.toStr(bodys.get(key))));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }	
	
	/**
	 * Post String
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        System.out.println("send httpPost[" + host + "]" + ",param" + querys );
        return httpClient.execute(request);
    }
	
	
	public static HttpResponse doPost(String host, String body)
            throws Exception {    
		HttpClient httpClient = wrapClient(host);
		HttpResponse response = null;
		try{
	    	HttpPost request = new HttpPost(buildUrl(host, null, null));
	        if (isNotBlank(body)) {
	        	request.setEntity(new StringEntity(body,"application/json", "UTF-8"));
	        }
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 120000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 120000);
	        System.out.println("send httpPost[" + host + "]" + ",param" + body );
	        response = httpClient.execute(request);
		}catch (Exception e) {
			System.out.println("doPost error!"+e.getMessage());
		} finally{
			if(httpClient!=null){
				httpClient.getConnectionManager().shutdown();
			}
		}
		return response;
    }
	
	public static HttpResponse doPost(String host, Map<String, Object> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, null, querys));
//        for (Map.Entry<String, Object> e : headers.entrySet()) {
//        	request.addHeader(e.getKey(), e.getValue());
//        }

//        if (isNotBlank(body)) {
//        	request.setEntity(new StringEntity(body, "utf-8"));
//        }
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        System.out.println("send httpPost[" + host + "]" + ",param" + querys );
        return httpClient.execute(request);
    }
	
	/**
	 * Post stream
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put String
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put stream
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Delete
	 *  
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doDelete(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, Object> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	private static String buildUrl(String host, String path, Map<String, Object> querys) throws UnsupportedEncodingException, MalformedURLException {
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append(host);
    	if (!isBlank(path)) {
    		sbUrl.append(path);
        }
    	if (null != querys) {
    		StringBuilder sbQuery = new StringBuilder();
        	for (Map.Entry<String, Object> query : querys.entrySet()) {
        		if (0 < sbQuery.length()) {
        			sbQuery.append("&");
        		}
        		if (isBlank(query.getKey()) && !Check.isBlank(query.getValue())) {
        			sbQuery.append(query.getValue());
                }
        		if (!isBlank(query.getKey())) {
        			sbQuery.append(query.getKey());
        			if (!Check.isBlank(query.getValue())) {
        				sbQuery.append("=");
        				sbQuery.append(URLEncoder.encode(query.getValue().toString(), "utf-8"));
        			}        			
                }
        	}
        	if (0 < sbQuery.length()) {
        		sbUrl.append("?").append(sbQuery);
        	}
        }
    	URL url = new URL(sbUrl.toString());
    	System.out.println(url);
    	System.out.println("#####url:"+sbUrl.toString());
    	return url.toString();
    }
	
	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}
		
		return httpClient;
	}
	
	private static void sslClient(HttpClient httpClient) {
        try {
        	String strTLS = "TLS";
            SSLContext ctx = SSLContext.getInstance(strTLS);
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] xcs, String str) throws CertificateException {
        			try {
        				String currDateStr = DateUtil.currDateStr();
        				System.out.println("currDateStr");
        			} catch (Exception e) {
        				System.out.println("checkClientTrusted error");
        				throw new CertificateException("checkServerTrusted error");
        			}
                }
                public void checkServerTrusted(X509Certificate[] xcs, String str)  throws CertificateException {
        			try {
        				String currDateStr = DateUtil.currDateStr();
        				System.out.println("currDateStr");
        			} catch (Exception e) {
        				System.out.println("checkClientTrusted error");
        				throw new CertificateException("checkServerTrusted error");
        			}
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
        	throw new RuntimeException(ex);
        }
    }
	
	private static boolean isNotBlank(String str){
		return (str != null && str.trim().length() > 0) ? true : false;
	}
	
	private static boolean isBlank(String str){
		return (str == null || str.trim().length() == 0) ? true : false;
	}
}
