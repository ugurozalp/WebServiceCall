package com.ugurozalp.webservicecall.utilities;

import java.util.Objects;
import java.util.Properties;

public class SystemProperty {
    public String getOSPath() {
        String configFileUrl = "";
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Linux")) {
                Properties p = new Properties();
                p.put("path", "${DOMAIN_HOME}");
                configFileUrl = SystemEnvironment.resolveValueWithEnvVars(p.getProperty("path"));
            } else
                configFileUrl = Objects.requireNonNull(getClass().getClassLoader().getResource("")).getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return configFileUrl;
    }
}
