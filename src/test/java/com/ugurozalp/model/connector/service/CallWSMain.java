package com.ugurozalp.model.connector.service;

import com.ugurozalp.webservicecall.builder.WebServiceCall;
import com.ugurozalp.webservicecall.builder.WebServiceCallBuilder;
import com.ugurozalp.webservicecall.builder.config.CallServiceParams;
import com.ugurozalp.webservicecall.configuration.ConnectorGetPropertyValues;
import com.ugurozalp.webservicecall.service.WebServiceCallFactory;

public class CallWSMain {

    public static void main(String[] args) {

	ConnectorGetPropertyValues getProp = new ConnectorGetPropertyValues();

	try {
	    System.setProperty("DOMAIN_HOME", "/u01/app/product/weblogic/user_projects/domains/ivrprep_domain");
	    CallServiceParams params = new CallServiceParams.Builder("ivr_ws_connector_test", args[1],
		    getProp.getListPropValues(args[0], args[1])).build();

	    WebServiceCallBuilder ws = new WebServiceCallFactory(params);

	    WebServiceCall webServiceCall = ws.getService();
	    System.out.println(webServiceCall.toString());

	} catch (Exception e) {
	    System.err.println(e.getMessage());
	}

    }
}
