package com.ugurozalp.webservicecall.utilities;

import com.ugurozalp.webservicecall.builder.config.InputParams;

import java.net.MalformedURLException;
import java.util.Objects;

public class URL {
    public static InputParams parseURL(InputParams inputParams) {
        java.net.URL aURL = null;
        try {
            aURL = new java.net.URL(inputParams.getURL());
        } catch (MalformedURLException e) {
            if (e.getMessage().startsWith("no protocol") || e.getMessage().startsWith("unknown protocol"))
                try {
                    aURL = new java.net.URL("http://" + inputParams.getURL());
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
        }

        return InputParams.copy(inputParams)
                .protocol(Objects.requireNonNull(aURL).getProtocol())
                .host(aURL.getHost())
                .port(getPort(aURL))
                .build();

    }

    private static int getPort(java.net.URL aURL) {
        int port = 80;
        if (aURL.getPort() == -1) {
            if (aURL.getProtocol().equalsIgnoreCase("https")) {
                port = 443;
            }
        } else
            port = aURL.getPort();
        return port;
    }
}
