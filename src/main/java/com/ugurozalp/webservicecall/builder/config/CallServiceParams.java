package com.ugurozalp.webservicecall.builder.config;

import java.util.Arrays;

public class CallServiceParams {
    private final String applicationSessionId;
    private final String methodName;
    private final String[] reqParamList;
    private final String authorization;

    CallServiceParams(Builder builder) {
        applicationSessionId = builder.applicationSessionId;
        methodName = builder.methodName;
        reqParamList = builder.reqParamList;
        authorization = builder.authorization;
    }

    public static class Builder {
        private final String applicationSessionId;
        private final String methodName;
        private final String[] reqParamList;
        private String authorization;

        public Builder(String applicationSessionId, String methodName) {
            this.applicationSessionId = applicationSessionId;
            this.methodName = methodName;
            this.reqParamList = new String[]{};
        }

        public Builder(String applicationSessionId, String methodName, String reqParam) {
            this.applicationSessionId = applicationSessionId;
            this.methodName = methodName;
            this.reqParamList = new String[]{reqParam};
        }

        public Builder(String applicationSessionId, String methodName, String[] reqParamList) {
            this.applicationSessionId = applicationSessionId;
            this.methodName = methodName;
            this.reqParamList = reqParamList;
        }

        public Builder authorization(String authorization) {
            this.authorization = authorization;
            return this;
        }

        public CallServiceParams build() {
            return new CallServiceParams(this);
        }
    }

    public String getWasSessionId() {
        return applicationSessionId;
    }

    public String getMethodName() {
        return methodName;
    }


    public String[] getReqParamList() {
        return reqParamList;
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "CallServiceParams{" +
                "applicationSessionId='" + applicationSessionId + '\'' +
                ", methodName='" + methodName + '\'' +
                ", reqParamList=" + Arrays.toString(reqParamList) +
                ", authorization='" + authorization + '\'' +
                '}';
    }
}
