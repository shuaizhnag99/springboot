package com.ule.uhj.provider.yitu.util.baiduface;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.utils.DateUtils;
 
public class SslUtils {
 
    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        String SSLStr = "SSL";
        SSLContext sc = SSLContext.getInstance(SSLStr);
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
 
    static class miTM implements TrustManager,X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
			try {
				String currDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				System.out.println("currDateStr");
			} catch (Exception e) {
				System.out.println("checkClientTrusted error");
				throw new CertificateException("checkServerTrusted error");
			}
        }
 
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
			try {
				String currDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				System.out.println("currDateStr");
			} catch (Exception e) {
				System.out.println("checkClientTrusted error");
				throw new CertificateException("checkServerTrusted error");
			}
        }
    }
     
    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     * @throws Exception
     */
    public static void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
            	if("X".equals(urlHostName)) {
            		return false;
            	}
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}
