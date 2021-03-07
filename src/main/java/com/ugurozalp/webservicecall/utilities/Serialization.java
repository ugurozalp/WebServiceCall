package com.ugurozalp.webservicecall.utilities;

import com.google.gson.Gson;

public class Serialization {
    public static String json(Object paramClass) {
        Gson gson = new Gson();
        return gson.toJson(paramClass);
    }
}
