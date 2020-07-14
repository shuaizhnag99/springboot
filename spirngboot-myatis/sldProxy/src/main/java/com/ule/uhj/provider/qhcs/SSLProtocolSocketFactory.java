/**
 * 
 */
package com.ule.uhj.provider.qhcs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

/**
 * SSL连接
 * 
 * @author 唐应泉(0755-22625539)
 * 
 */
public class SSLProtocolSocketFactory implements ProtocolSocketFactory
{
    private boolean isChkCert;

    public SSLProtocolSocketFactory(boolean chkCert)
    {
        this.isChkCert = chkCert;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
            HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException
    {
        if (params == null)
        {
            throw new IllegalArgumentException("Parameters may not be null!");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketFactory = SSLContextFactory.getInstance(this.isChkCert).getSocketFactory();
        if (timeout == 0)
        {
            return createSocket(host, port, localAddress, localPort);
        }
        return packSocket(socketFactory.createSocket(), localAddress, localPort, host, port, timeout);
    }

    private Socket packSocket(Socket socket, InetAddress localAddress, int localPort, String host, int port, int timeout) throws IOException {
        SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
        SocketAddress remoteaddr = new InetSocketAddress(host, port);
        socket.bind(localaddr);
        socket.connect(remoteaddr, timeout);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort) throws IOException,
            UnknownHostException
    {
        return SSLContextFactory.getInstance(this.isChkCert).getSocketFactory().createSocket(host, port, localAddress,
                localPort);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException
    {
        return SSLContextFactory.getInstance(this.isChkCert).getSocketFactory().createSocket(host, port);
    }
}
