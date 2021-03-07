package com.ugurozalp.webservicecall.types;

/**
 * ,
 *
 * @author "ugur.ozalp"
 * @version 1.0.0
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public class InputTypes {

    public enum errorTypes {
        timeout, business_error, exception
    }

    public enum serviceTypes {
        ws, db
    }

    public enum errorImpactTypes {
        business, teknik, handled
    }
    public enum logger{
        WebServiceCallLogger,ELKLogger
    }
}
