package com.ugurozalp.webservicecall.builder.config;

public class MaskParams {

    private final boolean isRequestMask;
    private final String requestMaskList;
    private final boolean isResponseMask;
    private final String responseMaskList;

    private MaskParams(Builder builder) {
        isRequestMask = builder.isRequestMask;
        requestMaskList = builder.requestMaskList;
        isResponseMask = builder.isResponseMask;
        responseMaskList = builder.responseMaskList;
    }

    public static class Builder {
        // Required parameters
        private final boolean isRequestMask;
        private final boolean isResponseMask;
        // Optional parameters
        private String requestMaskList;
        private String responseMaskList;

        public Builder(boolean isRequestMask, boolean isResponseMask) {
            this.isRequestMask = isRequestMask;
            this.isResponseMask = isResponseMask;
        }

        public Builder requestMaskList(String val) {
            requestMaskList = val;
            return this;
        }

        public Builder responseMaskList(String val) {
            responseMaskList = val;
            return this;
        }

        public MaskParams build() {
            return new MaskParams(this);
        }
    }

    public boolean isRequestMask() {
        return isRequestMask;
    }

    public String getRequestMaskList() {
        return requestMaskList;
    }

    public boolean isResponseMask() {
        return isResponseMask;
    }

    public String getResponseMaskList() {
        return responseMaskList;
    }

    @Override
    public String toString() {
        return "MaskParams [isRequestMask=" + isRequestMask + ", requestMaskList=" + requestMaskList
                + ", isResponseMask=" + isResponseMask + ", responseMaskList=" + responseMaskList + "]";
    }


}
