package com.ugurozalp.model.connector.service;

import com.ugurozalp.webservicecall.builder.WebServiceCall;
import com.ugurozalp.webservicecall.builder.WebServiceCallBuilder;
import com.ugurozalp.webservicecall.builder.config.CallServiceParams;
import com.ugurozalp.webservicecall.service.WebServiceCallFactoryBasic;

public class RESTServiceGETMethodCalls {
    public static void main(String[] args) {

        String todos = "todos/1";
        try {
            CallServiceParams params = new CallServiceParams.Builder("1234", "jsonplaceholder-todos",todos).build();
            WebServiceCallBuilder ws = new WebServiceCallFactoryBasic(params);

            WebServiceCall wsCall = ws.getService();
            System.out.println(wsCall.getMethodName());
            System.out.println(wsCall.getSessionId());


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
