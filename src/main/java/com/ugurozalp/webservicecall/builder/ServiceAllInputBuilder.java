package com.ugurozalp.webservicecall.builder;

import com.ugurozalp.webservicecall.builder.config.InputParams;

import java.util.ArrayList;

/**
 * @author "ugur.ozalp"
 * @version 1.1.2
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public abstract class ServiceAllInputBuilder {

    private ArrayList<WebServiceCall> webServiceCallList = new ArrayList<>();

    protected abstract void callService(String sessionId, String methodName, InputParams inputParams, String[] inputParamList);

    protected ServiceAllInputBuilder(String sessionId, String methodName, InputParams inputParams, String[] inputParamList) {
        this.callService(sessionId, methodName, inputParams, inputParamList);
    }

    protected ArrayList<WebServiceCall> getWebServiceCallList() {
        return webServiceCallList;
    }

    public void setWebServiceCallList(ArrayList<WebServiceCall> webServiceCallList) {
        this.webServiceCallList = webServiceCallList;
    }

    public WebServiceCall getIVRService() {
        if (getWebServiceCallList().size() == 0) {
            throw new NullPointerException("No service has been called.");
        } else if (getWebServiceCallList().size() >= 2) {
            return getWebServiceCallList().get(1);
        } else {
            return getWebServiceCallList().get(0);
        }
    }

}
