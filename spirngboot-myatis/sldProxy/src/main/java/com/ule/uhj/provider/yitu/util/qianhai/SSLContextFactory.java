/**
 * 
 */
package com.ule.uhj.provider.yitu.util.qianhai;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SSL连接上下文
 * 
 * @author 唐应泉(0755-22625539)
 * 
 */
public class SSLContextFactory
{	
	protected static Log log = LogFactory.getLog(SSLContextFactory.class);
    private static SSLContext ctx;
    private final static String PROTOCAL_NAME = "SSL";

    public static SSLContext getInstance(boolean chkCert)
    {
        if (ctx == null)
        {
            try
            {
                ctx = SSLContext.getInstance(PROTOCAL_NAME);
                if (chkCert)
                {
                    System.err.println("您要实现证书信任连接，请联系我们的管理员！");
                }
                else
                {
                    ctx.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
                }
            } catch (NoSuchAlgorithmException e)
            {
            	log.error("Exception error.", e);
            } catch (KeyManagementException e)
            {
            	log.error("Exception error.", e);
            }
            return ctx;
        }
        return ctx;
    }
}
