/**
 * 
 */
package com.ule.uhj.provider.yitu.util.qianhai;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.X509TrustManager;

import org.apache.http.client.utils.DateUtils;

/**
 * 我们信任任何SERVER 证书
 * 
 * @author 唐应泉(0755-22625539)
 * 
 */
public class TrustAnyTrustManager implements X509TrustManager
{

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
    	try {
    		String currDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
    		System.out.println("currDateStr");
    	} catch (Exception e) {
    		System.out.println("checkClientTrusted error");
    		throw new CertificateException("checkClientTrusted error");
    	}
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
    	try {
    		String currDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
    		System.out.println("currDateStr");
    	} catch (Exception e) {
    		System.out.println("checkClientTrusted error");
    		throw new CertificateException("checkClientTrusted error");
    	}
    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return new X509Certificate[] {};
    }

}
