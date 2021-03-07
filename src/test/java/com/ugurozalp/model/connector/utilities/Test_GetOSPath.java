package com.ugurozalp.model.connector.utilities;

import com.ugurozalp.webservicecall.utilities.SystemProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Test_GetOSPath {
    private SystemProperty sysProp;

    @BeforeEach
    void init() {
        sysProp = new SystemProperty();
    }
    @Test
    void checkNullSystemProperty() {
        assertNotNull(sysProp);
    }

    @Test
    void checkEmptyOSPath() {
        assertFalse(sysProp.getOSPath().isEmpty());
        System.out.println(sysProp.getOSPath());
    }
}
