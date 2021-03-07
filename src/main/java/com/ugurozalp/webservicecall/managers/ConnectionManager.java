package com.ugurozalp.webservicecall.managers;

import com.ugurozalp.webservicecall.builder.WebServiceCall;
import com.ugurozalp.webservicecall.builder.config.InputParams;
import com.ugurozalp.webservicecall.builder.config.OutputParams;
import com.ugurozalp.webservicecall.certification.EasySSLSocketFactory;
import com.ugurozalp.webservicecall.types.InputTypes;
import com.ugurozalp.webservicecall.utilities.URL;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.System.currentTimeMillis;

/**
 * @author "ugur.ozalp"
 * @version 1.0.0
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public class ConnectionManager extends WebServiceCall {

    private final HttpHost target;
    private RequestConfig requestConfig;
    private final HttpClientContext localContext;

    private static final Logger webServiceCallLogger = LogManager.getLogger(InputTypes.logger.WebServiceCallLogger.name());
    private final InputParams connectorParams;
    private static OutputParams outputParams;
    private int responseCode = -1;
    private String responseMsg = "";
    private String responseBody = "";

    public ConnectionManager(InputParams inputParams) {
        super(inputParams);
        this.connectorParams = URL.parseURL(inputParams);

        target = new HttpHost(connectorParams.getHost(), connectorParams.getPort(),
                getScheme(connectorParams.getURL()));
        requestConfig = RequestConfig.custom()
//                .setCookieSpec(CookieSpecs.DEFAULT)
//                .setExpectContinueEnabled(false)
//                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
//                .setProxyPreferredAuthSchemes(singletonList(AuthSchemes.BASIC))
                .build();
        localContext = HttpClientContext.create();

        callHttpClient();
    }

    private String getScheme(String URL) {
        if (URL.toLowerCase().contains("https")) {
            return "https";
        } else {
            return "http";
        }
    }

    private void setRequestTimeout() {
        requestConfig = RequestConfig.copy(requestConfig).setSocketTimeout(connectorParams.getConnTimeout())
                .setConnectTimeout(connectorParams.getConnTimeout())
                .setConnectionRequestTimeout(connectorParams.getConnTimeout()).build();
    }

    private void setProxy() {
        if (connectorParams.isNeedProxy()) {
            HttpHost proxy = new HttpHost(connectorParams.getProxyURL(), connectorParams.getProxyPort(),
                    getScheme(connectorParams.getProxyURL()));
            requestConfig = RequestConfig.copy(requestConfig).setProxy(proxy).build();
        }
    }

    private void setAuth() {
        if (connectorParams.isNeedAuth()) {
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);
            // Add AuthCache to the execution context
            localContext.setAuthCache(authCache);
        }
    }

    private void setOutputParams(long responseTime, int responseCode, String responseMsg, String responseBody) {
        outputParams = new OutputParams.Builder(responseCode, responseTime).responseBody(responseBody)
                .responseMsg(responseMsg).build();
        setOutputParams(outputParams);
    }

    private CredentialsProvider getCredentialsProvider() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        if (connectorParams.isNeedAuth()) {
            credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()),
                    new UsernamePasswordCredentials(connectorParams.getAuthUser(), connectorParams.getAuthPass()));
        }
        return credsProvider;
    }

    private static ByteArrayEntity getRequestEntityData(String xmlInput) {
        return new ByteArrayEntity(xmlInput.getBytes(StandardCharsets.UTF_8));
    }

    private void callHttpClient() {

        long responseTime = connectorParams.getConnTimeout();
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(getCredentialsProvider())
              //  .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext))
                .setSSLSocketFactory(new EasySSLSocketFactory().getSSLConnectionSocketFactory())
                .build()) {

            setProxy();
            setAuth();
            setRequestTimeout();

            HttpRequest request = getHttpRequest();

            AtomicLong startTime = new AtomicLong(currentTimeMillis());
            try (CloseableHttpResponse response = httpclient.execute(target, request, localContext)) {
                responseCode = response.getStatusLine().getStatusCode();
                responseMsg = response.getStatusLine().getReasonPhrase();
                responseBody = EntityUtils.toString(response.getEntity());
            } catch (SocketTimeoutException e) {
                getExModel(e, 2, " - SocketTimeoutException: ");
            } catch (Exception e) {
                getExModel(e, 3, " - Exception: ");
            }
            responseTime = currentTimeMillis() - startTime.get();
        } catch (IOException e) {
            getExModel(e, 1, " - ClientProtocolException: ");
        } finally {
            setOutputParams(responseTime, responseCode, responseMsg, responseBody);

            webServiceCallLogger.info(connectorParams.getCallServiceParams().getWasSessionId() + " - " + getMethodName()
                    + " << Request      : " + getRequestData());
            webServiceCallLogger.info(connectorParams.getCallServiceParams().getWasSessionId() + " - " + getMethodName()
                    + " >> ElapsedTime  : " + responseTime);
            webServiceCallLogger.info(connectorParams.getCallServiceParams().getWasSessionId() + " - " + getMethodName()
                    + " >> ResponseCode : " + outputParams.getResponseCode());
            webServiceCallLogger.info(connectorParams.getCallServiceParams().getWasSessionId() + " - " + getMethodName()
                    + " >> ResponseMsg  : " + outputParams.getResponseMsg());
            webServiceCallLogger.info(connectorParams.getCallServiceParams().getWasSessionId() + " - " + getMethodName()
                    + " >> Response     : " + outputParams.getResponseBody());
        }
    }

    private void getExModel(Exception e, int errorCode, String exName) {
        responseCode = -errorCode;
        responseMsg = e.getLocalizedMessage();
        webServiceCallLogger.error(connectorParams.getCallServiceParams().getWasSessionId() + exName + e.getMessage());
        e.printStackTrace();
    }

    private HttpRequest getHttpRequest() {
        HttpRequest request;
        switch (connectorParams.getRequestMethod().toUpperCase()) {
            case "GET":
                request = getHttpGet();
                break;
            case "POST":
                request = getHttpPost();
                break;
            case "PUT":
                request = getHttpPut();
                break;
            default:
                String errorMsg = " - requestMethod: \"" + connectorParams.getRequestMethod()
                        + "\" field is not available. The only fields you can use are \"GET\", \"POST\" methods.";
                webServiceCallLogger.error(connectorParams.getCallServiceParams().getWasSessionId() + " - " + errorMsg);
                throw new UnsupportedOperationException(errorMsg);
        }
        return request;
    }

    private HttpGet getHttpGet() {
        HttpGet httpget;
        if (getRequestData().startsWith("/")) {
            httpget = new HttpGet(connectorParams.getURL() + getRequestData());
        } else
            httpget = new HttpGet(connectorParams.getURL());
        httpget.setHeader(HttpHeaders.CONTENT_TYPE, connectorParams.getContentType());
        if (connectorParams.getSoapAction() != null) {
            httpget.setHeader("SOAPAction", connectorParams.getSoapAction());
        }
        httpget.setConfig(requestConfig);
        return httpget;
    }

    private HttpPost getHttpPost() {
        HttpPost httppost = new HttpPost(connectorParams.getURL());
        httppost.setHeader(HttpHeaders.CONTENT_TYPE, connectorParams.getContentType());

        if (connectorParams.getAuthorization() != null) {
            httppost.setHeader("Authorization", connectorParams.getAuthorization());
        }
        if (connectorParams.getSoapAction() != null) {
            httppost.setHeader("SOAPAction", connectorParams.getSoapAction());
        }
        httppost.setConfig(requestConfig);
        httppost.setEntity(getRequestEntityData(getRequestData()));
        return httppost;
    }

    private HttpPut getHttpPut() {
        HttpPut httpput = new HttpPut(connectorParams.getURL());
        httpput.setHeader(HttpHeaders.CONTENT_TYPE, connectorParams.getContentType());
        if (connectorParams.getAuthorization() != null) {
            httpput.setHeader("Authorization", connectorParams.getAuthorization());
        }
        if (connectorParams.getSoapAction() != null) {
            httpput.setHeader("SOAPAction", connectorParams.getSoapAction());
        }
        httpput.setConfig(requestConfig);
        httpput.setEntity(getRequestEntityData(getRequestData()));
        return httpput;
    }

}
