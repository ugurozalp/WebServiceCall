package com.ugurozalp.webservicecall.builder.config;


/**
 * @author "ugur.ozalp"
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public class InputParams {

    private final String URL;
    private final int port;
    private final String host;
    private final String protocol;
    private final boolean isNeedAuth;
    private final String authUser;
    private final String authPass;
    private final String authorization;
    private final boolean isNeedProxy;
    private final String proxyURL;
    private final int proxyPort;
    private final String contentType;
    private final String soapAction;
    private final int connTimeout;
    private final String requestBody;
    private final String requestMethod;
    private final String methodName;
    private final CallServiceParams callServiceParams;

    private InputParams(Builder builder) {
        callServiceParams = builder.callServiceParams;
        URL = builder.URL;
        port = builder.port;
        host = builder.host;
        protocol = builder.protocol;
        isNeedAuth = builder.isNeedAuth;
        authUser = builder.authUser;
        authPass = builder.authPass;
        authorization = builder.authorization;
        isNeedProxy = builder.isNeedProxy;
        proxyURL = builder.proxyURL;
        proxyPort = builder.proxyPort;
        contentType = builder.contentType;
        soapAction = builder.soapAction;
        connTimeout = builder.connTimeout;
        requestBody = builder.requestBody;
        requestMethod = builder.requestMethod;
        methodName = builder.methodName;
    }

    public static InputParams.Builder custom() {
        return new Builder();
    }

    public static InputParams.Builder copy(final InputParams config) {
        return new Builder(config.URL, config.isNeedAuth, config.isNeedProxy, config.contentType, config.requestBody, config.requestMethod, config.methodName)
                .host(config.getHost())
                .port(config.port)
                .protocol(config.protocol)
                .authUser(config.authUser)
                .authPass(config.authPass)
                .authorization(config.authorization)
                .proxyURL(config.proxyURL)
                .proxyPort(config.proxyPort)
                .soapAction(config.soapAction)
                .connTimeout(config.connTimeout)
                .callServiceParams(config.callServiceParams);
    }

    public static class Builder {
        // Required parameters
        private CallServiceParams callServiceParams;
        private String URL;
        private int port;
        private String host;
        private String protocol;
        private boolean isNeedAuth;
        private boolean isNeedProxy;
        private String contentType;
        private String requestMethod;
        private String requestBody;
        // Optional parameters - initialized to default values
        private String authUser;
        private String authPass;
        private String authorization;
        private String proxyURL;
        private int proxyPort;
        private String soapAction;
        private int connTimeout;
        private String methodName;


        public Builder(String URL, boolean isNeedAuth, boolean isNeedProxy, String contentType,
                       String requestBody, String requestMethod, String methodName) {
            String methodLog = "'" + methodName + "': ";

            if (URL == null || URL.equalsIgnoreCase(""))
                throw new NullPointerException(methodLog + "URL parameters cannot be null or empty!!!");
            else
                this.URL = URL;
            if (contentType == null || contentType.equalsIgnoreCase(""))
                throw new NullPointerException(methodLog + "contentType parameters cannot be null or empty!!!");
            else
                this.contentType = contentType;
            if (requestMethod == null || requestMethod.equalsIgnoreCase(""))
                throw new NullPointerException(methodLog + "requestMethod parameters cannot be null or empty!!!");
            else
                this.requestMethod = requestMethod;
            if (requestBody == null || requestBody.equalsIgnoreCase(""))
                throw new NullPointerException(methodLog + "requestBody parameters cannot be null or empty!!!");
            else
                this.requestBody = requestBody;

            this.isNeedAuth = isNeedAuth;
            this.isNeedProxy = isNeedProxy;

        }

        Builder() {
            super();
            this.protocol = "http";
            this.port = 80;
            this.connTimeout = 10000;
        }

        public Builder URL(String val) {
            if (val == null || val.equalsIgnoreCase(""))
                throw new NullPointerException("URL parameters cannot be null or empty!!!");
            else
                URL = val;
            return this;
        }

        public Builder callServiceParams(CallServiceParams params) {
            callServiceParams = params;
            return this;
        }

        public Builder isNeedAuth(boolean val) {
            isNeedAuth = val;
            return this;
        }

        public Builder isNeedProxy(boolean val) {
            isNeedProxy = val;
            return this;
        }

        public Builder host(String val) {
            host = val;
            return this;
        }

        public Builder authorization(String authorization) {
            this.authorization = authorization;
            return this;
        }

        public Builder contentType(String val) {
            if (val == null || val.equalsIgnoreCase(""))
                throw new NullPointerException("contentType parameters cannot be null or empty!!!");
            else
                contentType = val;
            return this;
        }

        public Builder requestMethod(String val) {
            requestMethod = val;
            return this;
        }

        public Builder requestBody(String val) {
            requestBody = val;
            return this;
        }

        public Builder port(int val) {
            port = val;
            return this;
        }

        public Builder protocol(String val) {
            protocol = val;
            return this;
        }

        public Builder authUser(String val) {
            authUser = val;
            return this;
        }

        public Builder authPass(String val) {
            authPass = val;
            return this;
        }

        public Builder proxyURL(String val) {
            proxyURL = val;
            return this;
        }

        public Builder proxyPort(int val) {
            proxyPort = val;
            return this;
        }

        public Builder soapAction(String val) {
            soapAction = val;
            return this;
        }

        public Builder connTimeout(int val) {
            connTimeout = val;
            return this;
        }

        public InputParams build() {
            return new InputParams(this);
        }
    }

    public String getURL() {
        return URL;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getProtocol() {
        return protocol;
    }

    public boolean isNeedAuth() {
        return isNeedAuth;
    }

    public String getAuthUser() {
        return authUser;
    }

    public String getAuthPass() {
        return authPass;
    }

    public boolean isNeedProxy() {
        return isNeedProxy;
    }

    public String getProxyURL() {
        return proxyURL;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public String getContentType() {
        return contentType;
    }

    public String getSoapAction() {
        return soapAction;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getAuthorization() {
        return authorization;
    }

    public CallServiceParams getCallServiceParams() {
        return callServiceParams;
    }

    @Override
    public String toString() {
        return "InputParams{" +
                "URL='" + URL + '\'' +
                ", port=" + port +
                ", host='" + host + '\'' +
                ", protocol='" + protocol + '\'' +
                ", isNeedAuth=" + isNeedAuth +
                ", authUser='" + authUser + '\'' +
                ", authPass='" + authPass + '\'' +
                ", authorization='" + authorization + '\'' +
                ", isNeedProxy=" + isNeedProxy +
                ", proxyURL='" + proxyURL + '\'' +
                ", proxyPort=" + proxyPort +
                ", contentType='" + contentType + '\'' +
                ", soapAction='" + soapAction + '\'' +
                ", connTimeout=" + connTimeout +
                ", requestBody='" + requestBody + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", callServiceParams=" + callServiceParams +
                '}';
    }
}
