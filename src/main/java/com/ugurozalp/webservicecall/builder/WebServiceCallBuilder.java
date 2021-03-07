package com.ugurozalp.webservicecall.builder;

import com.ugurozalp.webservicecall.builder.config.CallServiceParams;

import java.util.ArrayList;

/**
 * @author "ugur.ozalp"
 * @version 1.0.0
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public abstract class WebServiceCallBuilder {

    private ArrayList<WebServiceCall> webServiceCallList = new ArrayList<>();

    protected abstract void callService(CallServiceParams CallServiceParams);

    protected WebServiceCallBuilder(CallServiceParams CallServiceParams) {
        this.callService(CallServiceParams);
    }

    public ArrayList<WebServiceCall> getWebServiceCallList() {
        return webServiceCallList;
    }

    public void setWebServiceCallList(ArrayList<WebServiceCall> webServiceCallList) {
        this.webServiceCallList = webServiceCallList;
    }

    public WebServiceCall getService() {
        if (getWebServiceCallList().size() == 0)
            throw new NullPointerException("No service has been called.");
        else
            return getWebServiceCallList().get(0);

    }

}
