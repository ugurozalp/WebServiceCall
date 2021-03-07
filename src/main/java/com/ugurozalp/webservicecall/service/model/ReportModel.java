package com.ugurozalp.webservicecall.service.model;

import com.ugurozalp.webservicecall.builder.WebServiceCall;

public class ReportModel {
    public static ReportConfig error(WebServiceCall webServiceCall) {
        return getReportBuilder(webServiceCall)
                .build();
    }

    public static ReportConfig allInfo(WebServiceCall webServiceCall) {
        return getReportBuilder(webServiceCall)
                .requestBody(webServiceCall.getInputParamsOfService().getRequestBody())
                .responseBody(webServiceCall.getOutputParams().getResponseBody())
                .build();
    }

    private static ReportConfig.ReportBuilder getReportBuilder(WebServiceCall webServiceCall) {
        return new ReportConfig.ReportBuilder()
                .sessionId(webServiceCall.getSessionId())
                .url(webServiceCall.getInputParamsOfService().getURL())
                .methodName(webServiceCall.getMethodName())
                .requestMethod(webServiceCall.getInputParamsOfService().getRequestMethod())
                .responseCode(String.valueOf(webServiceCall.getOutputParams().getResponseCode()))
                .responseMsg(webServiceCall.getOutputParams().getResponseMsg())
                .elapsedTime(String.valueOf(webServiceCall.getOutputParams().getResponseTime()));
    }

    public static ReportConfig connections(WebServiceCall webServiceCall) {
        return new ReportConfig.ReportBuilder()
                .sessionId(webServiceCall.getSessionId())
                .url(webServiceCall.getInputParamsOfService().getURL())
                .methodName(webServiceCall.getMethodName())
                .responseBody(webServiceCall.getOutputParams().getResponseBody().replaceAll("\"",""))
                .responseCode(String.valueOf(webServiceCall.getOutputParams().getResponseCode()))
                .responseMsg(webServiceCall.getOutputParams().getResponseMsg())
                .elapsedTime(String.valueOf(webServiceCall.getOutputParams().getResponseTime()))
                .build();
    }

    public static ReportConfig info(WebServiceCall webServiceCall) {
        return new ReportConfig.ReportBuilder()
                .methodName(webServiceCall.getMethodName())
                .responseCode(String.valueOf(webServiceCall.getOutputParams().getResponseCode()))
                .responseMsg(webServiceCall.getOutputParams().getResponseMsg())
                .elapsedTime(String.valueOf(webServiceCall.getOutputParams().getResponseTime()))
                .build();
    }

}
