package com.ugurozalp.webservicecall.service.model;

import com.ugurozalp.webservicecall.builder.config.CallServiceParams;
import com.ugurozalp.webservicecall.builder.config.InputParams;
import com.ugurozalp.webservicecall.builder.config.MaskParams;
import com.ugurozalp.webservicecall.configuration.ServicePropertiesConfiguration;

public class InitInputParams {

    public static InputParams setInputParams(ServicePropertiesConfiguration servicePropConfig, CallServiceParams callServiceParams) {
        String methodName = callServiceParams.getMethodName();
        String URL = servicePropConfig.getURL(methodName);
        String contentType = servicePropConfig.getWsContentType(methodName);
        String requestBody = servicePropConfig.getQueryMessage(methodName);
        String requestMethod = servicePropConfig.getRequestMethod(methodName);
        String soapAction = servicePropConfig.getSoapAction(methodName);
        boolean isNeedAuth = servicePropConfig.getWsNeedAuth(methodName);
        String authUser = servicePropConfig.getAuthUser(methodName);
        String authPass = servicePropConfig.getAuthPass(methodName);
        boolean isNeedProxy = servicePropConfig.getWsNeedProxy(methodName);
        String proxyURL = servicePropConfig.getProxyUrl(methodName);
        int proxyPort = servicePropConfig.getProxyPort(methodName);
        int timeout = servicePropConfig.getConnectionTimeout(methodName);

        return new InputParams.Builder(URL, isNeedAuth, isNeedProxy, contentType, requestBody, requestMethod,methodName)
                .soapAction(soapAction)
                .authUser(authUser)
                .authPass(authPass)
                .authorization(callServiceParams.getAuthorization())
                .proxyURL(proxyURL)
                .proxyPort(proxyPort)
                .connTimeout(timeout)
                .callServiceParams(callServiceParams)
                .build();
    }

    public static MaskParams setMaskParams(ServicePropertiesConfiguration servicePropConfig, CallServiceParams callServiceParams) {
        String methodName = callServiceParams.getMethodName();
        boolean isRequestMask = servicePropConfig.isRequestmask(methodName);
        boolean isResponseMask = servicePropConfig.isResponsemask(methodName);
        String reqMaskList = servicePropConfig.getRequestmasklist(methodName);
        String resMaskList = servicePropConfig.getResponsemasklist(methodName);

        return new MaskParams.Builder(isRequestMask, isResponseMask)
                .requestMaskList(reqMaskList)
                .responseMaskList(resMaskList)
                .build();

    }
}
