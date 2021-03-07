package com.ugurozalp.webservicecall.configuration;

import com.ugurozalp.webservicecall.types.InputTypes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author "ugur.ozalp"
 * @version 1.0.0
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public class ServicePropertiesConfiguration {
    private static final Logger webServiceCallLogger = LogManager.getLogger(InputTypes.logger.WebServiceCallLogger.name());
    private final ConnectorGetPropertyValues properties = new ConnectorGetPropertyValues();
    private final String confPath;
    private static volatile ServicePropertiesConfiguration instance = null;
    private static String sessionId;

    private ServicePropertiesConfiguration() {
        String confStatus = properties.getPropValues("connector.properties.status");
        if (confStatus == null || confStatus.isEmpty()) {
            confStatus = "test";
            webServiceCallLogger.warn(sessionId +
                    " - connector.properties.status is null or empty. Please check config.properites. Default set 'test'");
        }
        webServiceCallLogger.debug(sessionId +
                " - connector.properties.status is " + confStatus);
        confPath = "wsconnector_" + confStatus + ".properties";
    }

    /**
     * @param sessionId to
     * @return instance object of the PropertiesConfiguration
     */
    public static ServicePropertiesConfiguration getInstance(String sessionId) {
        ServicePropertiesConfiguration.sessionId = sessionId;
        if (instance == null) {
            // Double check locking
            synchronized (ServicePropertiesConfiguration.class) {
                if (instance == null) {
                    instance = new ServicePropertiesConfiguration();
                }
            }
        }
        return instance;
    }


    /**
     * @param key to search for in the property file
     * @return property value
     */
    private String getProperty(String key) {
        String value = properties.getPropValues(confPath, key);

        if (value == null && key.endsWith("url")
                && key.endsWith("requestMethod")
                && key.endsWith("query")
                && key.endsWith("contentType")) {
            webServiceCallLogger.error(sessionId +
                    " -  Key: '" + key + "' field not found. Please check the configuration file: "
                    + confPath);
            throw new NullPointerException(
                    "Key: '" + key + "' field not found. Please check the configuration file: " + confPath);
        } else {
            return properties.getPropValues(confPath, key);
        }

    }

    private String getStringProperty(String prefix) {
        String stringValue = getProperty(prefix);
        if (stringValue == null) {
            webServiceCallLogger.debug(sessionId +
                    " - getStringProperty: " + prefix + " not found");
            return "";
        } else {
            return stringValue;
        }
    }

    private int getIntegerProperty(String prefix) {
        String stringValue = getProperty(prefix);
        if (stringValue == null) {
            webServiceCallLogger.debug(sessionId +
                    " - getIntegerProperty: " + prefix + " not found");
            return 0;
        }
        int integerValue;
        try {
            integerValue = Integer.parseInt(stringValue);
        } catch (Exception e) {
            webServiceCallLogger.error(sessionId +
                    " - getIntegerProperty: " + prefix + " (" + stringValue
                    + ") cannot be converted to integer");
            return 0;
        }
        return integerValue;
    }

    private boolean getBooleanProperty(String prefix) {
        String stringValue = getProperty(prefix);
        if (stringValue == null) {
            webServiceCallLogger.debug(sessionId +
                    " - getBooleanProperty: " + prefix + " not found");
            return false;
        }
        boolean booleanValue;
        try {
            booleanValue = Boolean.parseBoolean(stringValue);
        } catch (Exception e) {
            webServiceCallLogger.error(sessionId +
                    " - getBooleanProperty: " + prefix + " (" + stringValue
                    + ") cannot be converted to boolean");
            return false;
        }
        return booleanValue;
    }

    public int getConnectionTimeout(String prefix) {
        int timeout = getIntegerProperty(prefix + ".connectionTimeout");
        if (timeout == 0)
            return getIntegerProperty("connector.connectionTimeout");
        else
            return getIntegerProperty(prefix + ".connectionTimeout");

    }

    public int getMaxConnections() {
        return getIntegerProperty("connector.maxConnections");
    }

    public String getSoapAction(String prefix) {
        return getStringProperty(prefix + ".soapAction");
    }

    public String getRequestMethod(String prefix) {
        return getProperty(prefix + ".requestMethod");
    }

    public String getWsContentType(String prefix) {
        return getStringProperty(prefix + ".contentType");
    }

    public String getURL(String prefix) {
        return getStringProperty(prefix + ".url");
    }

    public boolean getWsNeedAuth(String prefix) {
        return getBooleanProperty(prefix + ".needAuth");
    }

    public String getAuthUser(String prefix) {
        return getStringProperty(prefix + ".authUser");
    }

    public String getAuthPass(String prefix) {
        return getStringProperty(prefix + ".authPass");
    }

    public boolean getWsNeedProxy(String prefix) {
        return getBooleanProperty(prefix + ".needProxy");
    }

    public String getProxyUrl(String prefix) {
        return getStringProperty(prefix + ".proxyUrl");
    }

    public int getProxyPort(String prefix) {
        return getIntegerProperty(prefix + ".proxyPort");
    }

    public String getQueryMessage(String prefix) {
        return getStringProperty(prefix + ".query");
    }

    public String getRequestmasklist(String prefix) {
        return getStringProperty(prefix + ".requestmasklist");
    }

    public String getResponsemasklist(String prefix) {
        return getStringProperty(prefix + ".responsemasklist");
    }

    public boolean isRequestmask(String prefix) {
        return getBooleanProperty(prefix + ".isRequestMask");
    }

    public boolean isResponsemask(String prefix) {
        return getBooleanProperty(prefix + ".isResponseMask");
    }

}
