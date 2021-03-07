package com.ugurozalp.webservicecall.configuration;

import com.ugurozalp.webservicecall.types.InputTypes;
import com.ugurozalp.webservicecall.utilities.SystemProperty;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConnectorGetPropertyValues implements IGetPropertyValues {
    private static final Logger webServiceCallLogger = LogManager.getLogger(InputTypes.logger.WebServiceCallLogger.name());

    /**
     * Tomcat configuration: /apache-tomcat-x.x.x/conf/catalina.properties
     * common.loader="${catalina.base}/lib","${catalina.base}/lib/*.jar","${catalina.home}/lib","${catalina.home}/lib/*.jar"
     */
    private final SystemProperty sysProp;

    public ConnectorGetPropertyValues() {
        sysProp = new SystemProperty();
    }

    @Override
    public String getPropValues(String propert) {
        String result = "";
        Properties prop = new Properties();

        File file = new File(sysProp.getOSPath() + "/AppConfig/connector/config.properties");
        try (InputStream inputStream = new FileInputStream(file)) {

            prop.load(inputStream);

            result = prop.getProperty(propert);
        } catch (Exception e) {
            webServiceCallLogger.error("getPropValues Ex: " + e.getLocalizedMessage(), e);
        }
        return result;
    }

    public String getPropValues(String propertiesFileName,String property) {
        String result;
        Properties prop = new Properties();
        File file = new File(sysProp.getOSPath() + "/AppConfig/connector/" + propertiesFileName);
        try (InputStream inputStream = new FileInputStream(file)) {
            prop.load(inputStream);

            result = prop.getProperty(property);
        } catch (IOException e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }
        return result;
    }

    public String[] getListPropValues(String propertiesFileName, String property) {
        String result = "";
        Properties prop = new Properties();
        File file = new File(sysProp.getOSPath() + "/AppConfig/connector/" + propertiesFileName + ".properties");
        try (InputStream inputStream = new FileInputStream(file)) {
            prop.load(inputStream);

            result = prop.getProperty(property);
        } catch (Exception e) {
            webServiceCallLogger.error("getListPropValues Ex: " + e.getLocalizedMessage(), e);
        }
        return getListValue(result);
    }

    private String[] getListValue(String result) {
        String[] returnList = {};
        if (result != null) {
            returnList = result.split(",");
        }
        return returnList;
    }
}
