package com.ugurozalp.webservicecall.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SystemEnvironment {
    private SystemEnvironment() {
    }

    static String resolveValueWithEnvVars(String value) {
        if (null == value) {
            return "";
        }

        Pattern p = Pattern.compile("\\$\\{(\\w+)}|\\$(\\w+)");
        Matcher m = p.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
            String envVarValue = System.getenv(envVarName);
            m.appendReplacement(sb, null == envVarValue ? "" : Matcher.quoteReplacement(envVarValue));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}