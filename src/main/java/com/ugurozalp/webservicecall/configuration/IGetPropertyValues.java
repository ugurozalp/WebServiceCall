package com.ugurozalp.webservicecall.configuration;

interface IGetPropertyValues {
    String getPropValues(String property);

    String getPropValues( String propertiesFileName, String property);
}
