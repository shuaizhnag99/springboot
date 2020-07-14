/**
 * 
 */
package com.ule.uhj.provider.qhcs;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.sld.util.DateUtil;

/**
 * 我们信任任何SERVER 证书
 * 
 * @author 唐应泉(0755-22625539)
 * 
 */
public class TrustAnyTrustManager implements X509TrustManager
{
	protected static Log log = LogFactory.getLog(TrustAnyTrustManager.class);

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
		try {
			String currDateStr = DateUtil.currDateStr();
			log.info("currDateStr:"+currDateStr);
		} catch (Exception e) {
			throw new CertificateException("CertificateException error");
		}
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
		try {
			String currDateStr = DateUtil.currDateStr();
			log.info("currDateStr:"+currDateStr);
		} catch (Exception e) {
			throw new CertificateException("CertificateException error");
		}
    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return new X509Certificate[] {};
    }

}
