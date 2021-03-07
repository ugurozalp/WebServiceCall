package com.ugurozalp.webservicecall.service;

import com.ugurozalp.webservicecall.builder.WebServiceCall;
import com.ugurozalp.webservicecall.builder.WebServiceCallBuilder;
import com.ugurozalp.webservicecall.builder.config.CallServiceParams;
import com.ugurozalp.webservicecall.builder.config.InputParams;
import com.ugurozalp.webservicecall.configuration.ServicePropertiesConfiguration;
import com.ugurozalp.webservicecall.managers.ConnectionManager;
import com.ugurozalp.webservicecall.service.model.InitInputParams;
import com.ugurozalp.webservicecall.service.model.ReportConfig;
import com.ugurozalp.webservicecall.service.model.ReportModel;
import com.ugurozalp.webservicecall.types.InputTypes;
import com.ugurozalp.webservicecall.utilities.Serialization;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * @author "ugur.ozalp"
 * @version 1.1.3
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public class WebServiceCallFactory extends WebServiceCallBuilder {

    private ArrayList<WebServiceCall> webServiceCall;
    private ServicePropertiesConfiguration servicePropConfig;

    private static final Logger webServiceCallLogger = LogManager.getLogger(InputTypes.logger.WebServiceCallLogger.name());
    private static final Logger elkLogger = LogManager.getLogger(InputTypes.logger.ELKLogger.name());

    public WebServiceCallFactory(CallServiceParams callServiceParams) {
        super(callServiceParams);
    }

    @Override
    public void callService(CallServiceParams callServiceParams) {
        servicePropConfig = ServicePropertiesConfiguration.getInstance(callServiceParams.getWasSessionId());
        try {
            callServiceInfo(callServiceParams);
            callReportingService(callServiceParams.getWasSessionId());
        } catch (Exception e) {
            webServiceCallLogger.error(callServiceParams.getWasSessionId() + " - callService - " + e.getMessage(), e);
        } finally {
            insertELK();
        }
    }

    private void callServiceInfo(CallServiceParams callServiceParams) {
        setInitParams(callServiceParams);
        webServiceCall = getWebServiceCallList();
    }

    private void setInitParams(CallServiceParams callServiceParams) {
        InputParams inputParams = InitInputParams.setInputParams(servicePropConfig, callServiceParams);
        getWebServiceCallList().add(new ConnectionManager(inputParams));
    }

    private void callReportingService(String sessionId) {
        String callMethodName = "report";
        CallServiceParams params = new CallServiceParams.Builder(sessionId, callMethodName, Serialization.json(ReportModel.connections(webServiceCall.get(0)))).build();
        setInitParams(params);
    }

    private void insertELK() {
        try {
            WebServiceCall webServiceCall = this.webServiceCall.get(0);
            ReportConfig report = ReportModel.error(webServiceCall);
            elkLogger.info(Serialization.json(report));

        } catch (NullPointerException e) {
            webServiceCallLogger.error("No service has been called.", e);
        }
    }
}
