package com.ugurozalp.model.connector.configuration;

import com.ugurozalp.webservicecall.configuration.ConnectorGetPropertyValues;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author "ugur.ozalp"
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
class ReadConfigurationTest {

    private static ConnectorGetPropertyValues properties;

    @BeforeAll
    static void setUpBeforeClass() {
        properties = new ConnectorGetPropertyValues();
    }

    @Test
    void getConnectorPropertiesStatus() {
        assertNotEquals(null, properties.getPropValues("connector.properties.status"));
    }

}