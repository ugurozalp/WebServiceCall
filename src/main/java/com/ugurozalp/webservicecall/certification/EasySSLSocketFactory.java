package com.ugurozalp.webservicecall.certification;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class EasySSLSocketFactory {

    private SSLContext sslcontext;

    public EasySSLSocketFactory() {
        super();
    }

    private static SSLContext createEasySSLContext() {
        SSLContext context = null;
        try {
            context = SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy) (arg0, arg1) -> true).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return context;
    }

    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createEasySSLContext();
        }
        return this.sslcontext;
    }

    public SSLConnectionSocketFactory getSSLConnectionSocketFactory() {
        return new SSLConnectionSocketFactory(
                getSSLContext(),
                SSLConnectionSocketFactory.getDefaultHostnameVerifier()
        );
    }
}
