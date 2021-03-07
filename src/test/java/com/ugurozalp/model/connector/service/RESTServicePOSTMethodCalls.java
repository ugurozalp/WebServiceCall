package com.ugurozalp.model.connector.service;

import com.ugurozalp.webservicecall.builder.WebServiceCall;
import com.ugurozalp.webservicecall.builder.WebServiceCallBuilder;
import com.ugurozalp.webservicecall.builder.config.CallServiceParams;
import com.ugurozalp.webservicecall.service.WebServiceCallFactoryBasic;

public class RESTServicePOSTMethodCalls {
    public static void main(String[] args) {
        
        String posts = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
        String [] listParams = {"foo","bar","1"};
        try {
            CallServiceParams params = new CallServiceParams.Builder("1234", "jsonplaceholder-posts", listParams).build();
            WebServiceCallBuilder ws = new WebServiceCallFactoryBasic(params);

            WebServiceCall wsCall = ws.getService();
            System.out.println(wsCall.toString());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
