package com.ugurozalp.webservicecall.builder.config;

public class OutputParams {

    private final int responseCode;
    private final String responseMsg;
    private final long responseTime;
    private final String responseBody;

    private OutputParams(Builder builder) {
        responseCode = builder.responseCode;
        responseMsg = builder.responseMsg;
        responseTime = builder.responseTime;
        responseBody = builder.responseBody;
    }

    public static class Builder {
        // Required parameters
        private final int responseCode;
        private final long responseTime;
        // Optional parameters
        private String responseMsg = "";
        private String responseBody = "";

        public Builder(int responseCode, long responseTime) {
            this.responseCode = responseCode;
            this.responseTime = responseTime;
        }

        public Builder responseMsg(String val) {
            responseMsg = val;
            return this;
        }

        public Builder responseBody(String val) {
            responseBody = val;
            return this;
        }

        public OutputParams build() {
            return new OutputParams(this);
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "OutputParams [responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", responseTime="
                + responseTime + ", responseBody=" + responseBody + "]";
    }

}
