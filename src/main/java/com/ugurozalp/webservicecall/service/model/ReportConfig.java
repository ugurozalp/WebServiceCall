package com.ugurozalp.webservicecall.service.model;

import com.google.gson.annotations.SerializedName;

public class ReportConfig {
    @SerializedName(value = "application_session_id", alternate = "applicationSessionId")
    private final String applicationSessionId;
    @SerializedName(value = "elapsed_time", alternate = "elapsedTime")
    private final String elapsedTime;
    @SerializedName(value = "end_point_url", alternate = "endPointUrl")
    private final String endPointUrl;
    @SerializedName(value = "method_name", alternate = "methodName")
    private final String methodName;
    @SerializedName(value = "response_body", alternate = "responseBody")
    private final String responseBody;
    @SerializedName(value = "response_code", alternate = "responseCode")
    private final String responseCode;
    @SerializedName(value = "response_msg", alternate = "responseMsg")
    private final String responseMsg;

    private final String requestMethod;
    private final String requestBody;

    private ReportConfig(ReportBuilder reportBuilder) {
        this.applicationSessionId = reportBuilder.sessionId;
        this.endPointUrl = reportBuilder.url;
        this.methodName = reportBuilder.methodName;
        this.requestBody = reportBuilder.requestBody;
        this.requestMethod = reportBuilder.requestMethod;
        this.responseBody = reportBuilder.responseBody;
        this.responseCode = reportBuilder.responseCode;
        this.responseMsg = reportBuilder.responseMsg;
        this.elapsedTime = reportBuilder.elapsedTime;
    }


    public static class ReportBuilder {
        private String sessionId;
        private String url;
        private String methodName;
        private String requestBody;
        private  String requestMethod;
        private String responseBody;
        private String responseCode = "-1";
        private String responseMsg;
        private String elapsedTime = "0";

        public ReportBuilder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }
        public ReportBuilder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }
        public ReportBuilder requestBody(String requestBody) {
            this.requestBody = requestBody;
            return this;
        }
        public ReportBuilder requestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public ReportBuilder responseBody(String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public ReportBuilder url(String url) {
            this.url = url;
            return this;
        }

        public ReportBuilder responseCode(String responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public ReportBuilder responseMsg(String responseMsg) {
            this.responseMsg = responseMsg;
            return this;
        }

        public ReportBuilder elapsedTime(String elapsedTime) {
            this.elapsedTime = elapsedTime;
            return this;
        }

        public ReportConfig build() {
            return new ReportConfig(this);
        }
    }

    public String getWasSessionId() {
        return applicationSessionId;
    }

    public String getEndPointUrl() { return endPointUrl; }

    public String getResponseBody() {
        return responseBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public String getRequestMethod() { return requestMethod; }

    public String getMethodName() { return methodName; }

    @Override
    public String toString() {
        return "Report{" +
                "sessionId='" + applicationSessionId + '\'' +
                ", url='" + endPointUrl + '\'' +
                ", methodName='" + methodName + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", responseCode=" + responseCode +
                ", responseMsg='" + responseMsg + '\'' +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
