package com.ule.uhj.provider.pengyuan.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.provider.pengyuan.service.PengyuanQueryService;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 鹏元征信 SSLContext 帮助类
 */
public class PySSLContextUtil {
	protected static Log log = LogFactory.getLog(PySSLContextUtil.class);

    /**
     * 使用该SSLContext，证书如下
     * keystore  ： javax.net.ssl.keyStore 指定的证书
     * truststore  ： javax.net.ssl.trustStore 指定的证书
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SSLContext createDefaultSSLContext() throws NoSuchAlgorithmException {
        return SSLContext.getDefault();
    }

    /**
     * 使用该SSLContext, 证书可自定义
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SSLContext createCustomerSSLContext() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException, UnrecoverableKeyException {
    	String StrTLS="TLS";
        SSLContext context = SSLContext.getInstance(StrTLS);
        String keyStoreFile =  Thread.currentThread().getContextClassLoader().getResource("/").getPath()+PropertiesHelper.getDfs("pengyuan_keystore_file");
//        log.info("createCustomerSSLContext keyStoreFile= "+keyStoreFile);
        KeyStore keyStore = getKeyStore("JKS", new FileInputStream(keyStoreFile), PropertiesHelper.getDfs("pengyuan_keystore_password"));
//        log.info("createCustomerSSLContext keyStore= "+keyStore);
        KeyManager[] kms = createKeyManager(keyStore, PropertiesHelper.getDfs("pengyuan_keystore_password"));
//        log.info("createCustomerSSLContext kms= "+kms);
//        KeyStore trustStore = getKeyStore("JKS", new FileInputStream("D:/file/ylwl.jks"), PropertiesHelper.getDfs("pengyuan_truststore_password"));
//        TrustManager[] tms = createTrustManager(trustStore);
		//需要添加信任证书（需要公钥）
//        context.init(kms, tms, null);
		//不要信任证书
		TrustManager tm= new X509TrustManager(){
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain,String authType) throws CertificateException
		{
			try {
				String currDateStr = DateUtil.currDateStr();
				log.info("currDateStr:"+currDateStr);
			} catch (Exception e) {
				throw new CertificateException("CertificateException error");
			}
		}
		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain,String authType) throws CertificateException
		{
			try {
				String currDateStr = DateUtil.currDateStr();
				log.info("currDateStr:"+currDateStr);
			} catch (Exception e) {
				throw new CertificateException("CertificateException error");
			}
		}
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers()
		{
		   return null;
		}
		};
		context.init(kms,new TrustManager[]{tm},null);
		log.info("createCustomerSSLContext context init is ok...");
        return context;
    }

    private static KeyManager[] createKeyManager(KeyStore keyStore, String password) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, password.toCharArray());
        return factory.getKeyManagers();
    }

    private static TrustManager[] createTrustManager(KeyStore trustStore) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(trustStore);
        return factory.getTrustManagers();
    }


    public static KeyStore getKeyStore(String keyStoreType, InputStream stream, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(stream, password.toCharArray());
        return keyStore;
    }
}