package com.ugurozalp.webservicecall.builder;

import com.ugurozalp.webservicecall.builder.config.InputParams;
import com.ugurozalp.webservicecall.builder.config.MaskParams;
import com.ugurozalp.webservicecall.builder.config.OutputParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author "ugur.ozalp"
 * @version 1.0.0
 * @see <a href="http://www.ugurozalp.com">ugurozalp.com</a>
 */
public abstract class WebServiceCall {

    private String sessionId;
    private String methodName;
    private String requestData;

    private InputParams inputParams;
    private OutputParams outputParams;
    private MaskParams maskParams;

    protected WebServiceCall(InputParams inputParam, MaskParams maskParams) {
        super();
        setInitConst(inputParam, maskParams);

    }

    private void setInitConst(InputParams inputParam, MaskParams maskParams) {
        setSessionId(inputParam.getCallServiceParams().getWasSessionId());
        setMethodName(inputParam.getCallServiceParams().getMethodName());
        setRequestData(createRequestData(inputParam.getRequestBody(), inputParam.getCallServiceParams().getReqParamList()));
        setInputParamsOfService(inputParam);
        setMaskParams(maskParams);
    }

    protected WebServiceCall(InputParams inputParam) {
        super();
        setInitConst(inputParam, null);
    }

    private void setSessionId(String sessionId) {
        if (sessionId == null || sessionId.equalsIgnoreCase(""))
            throw new NullPointerException("sessionId can not be null or empty!!");
        else
            this.sessionId = sessionId;
    }

    private void setInputParamsOfService(InputParams inputParams) {
        this.inputParams = inputParams;
    }

    private void setRequestData(String requestData) {
        if (requestData == null || requestData.equalsIgnoreCase(""))
            throw new NullPointerException("requestData can not be null or empty!!");
        else
            this.requestData = requestData;
    }

    private void setMethodName(String methodName) {
        if (methodName == null || methodName.equalsIgnoreCase(""))
            throw new NullPointerException("methodName can not be null or empty!!");
        else
            this.methodName = methodName;

    }

    public OutputParams getOutputParams() {
        return outputParams;
    }

    protected void setOutputParams(OutputParams outputParams) {
        this.outputParams = outputParams;
    }

    public MaskParams getMaskParams() {
        return maskParams;
    }

    private void setMaskParams(MaskParams maskParams) {
        this.maskParams = maskParams;
    }

    public String getSessionId() {
        return sessionId;
    }

    protected String getRequestData() {
        return requestData;
    }

    public String getMethodName() {
        return methodName;
    }

    public InputParams getInputParamsOfService() {
        return inputParams;
    }

    private static String createRequestData(String requestBody, String[] params) {
        String regex = "\\?\\?\\?";
        int count = countSpecificWords(requestBody, regex);
        if (count > params.length) {
            throw new ArrayIndexOutOfBoundsException("Input values(length:" + params.length
                    + ") can not be less than query values(length:" + count + ")");
        } else if (count < params.length) {
            throw new ArrayIndexOutOfBoundsException("Input values(length:" + params.length
                    + ") can not be greater than query values(length:" + count + ")");

        } else if (count == 1 && count == params.length) {
            return requestBody.replaceAll(regex, params[0]);
        } else {
            try {
                StringBuilder result = new StringBuilder();
                int i;
                String[] skeleton = requestBody.split(regex);
                for (i = 0; i < params.length; i++) {
                    result.append(skeleton[i]).append(params[i]);
                }
                if (skeleton.length > params.length) {
                    result.append(skeleton[i]);
                }
                return result.toString();
            } catch (Exception e) {
                throw new NullPointerException("Input values and query values different from each other");
            }
        }
    }

    private static int countSpecificWords(String body, String word) {
        int i = 0;
        Pattern p = Pattern.compile(word);
        Matcher m = p.matcher(body);
        while (m.find()) {
            i++;
        }
        return i;
    }
}
