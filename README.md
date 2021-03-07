# WebServiceCall

## Overview
The WebServiceCall is a useful java library that will help you consume web service (SOA, REST) with its simplified usage and common logging structure.
##### Specifications;
+ Provides easy service call.
+ Together with ELK integration, it helps you report the service response.
+ Allows you to change service information at run time.

## Guidelines

As an example, let's call the "**jsonplaceholder-todos**" service. 

1. The first thing we need to do is to add the service information to our properties file.
```yaml
# jsonplaceholder - GET REST
# description: Free fake API for testing and prototyping.
# website : https://jsonplaceholder.typicode.com/
jsonplaceholder-todos.url=https://jsonplaceholder.typicode.com
jsonplaceholder-todos.requestMethod=GET
jsonplaceholder-todos.contentType=application/json
jsonplaceholder-todos.query=/???
```

The name "**jsonplaceholder-todos**" is now a unique field for us.
The "**.query**" field has the request data of the service. We can use one or more input parameters with "**???**". In this example we are using one input parameter.

2. We can write the codes that we will call the web service.

```java
String todos = "todos/1";
try {
    CallServiceParams params = new CallServiceParams.Builder("1234", "jsonplaceholder-todos",todos).build();
    WebServiceCallBuilder wsBasicBuilder = new WebServiceCallFactoryBasic(params);
    WebServiceCall wsCall = wsBasicBuilder.getService();
} catch (Exception e) {
    System.err.println(e.getMessage());
}
```
#### WebServiceCall Log
``` logs
2021-03-07 16:08:57.307  INFO - [      main]                WebServiceCallLogger : 1234 - jsonplaceholder-todos << Request      : /todos/1
2021-03-07 16:08:57.308  INFO - [      main]                WebServiceCallLogger : 1234 - jsonplaceholder-todos >> ElapsedTime  : 816
2021-03-07 16:08:57.308  INFO - [      main]                WebServiceCallLogger : 1234 - jsonplaceholder-todos >> ResponseCode : 200
2021-03-07 16:08:57.309  INFO - [      main]                WebServiceCallLogger : 1234 - jsonplaceholder-todos >> ResponseMsg  : OK
2021-03-07 16:08:57.309  INFO - [      main]                WebServiceCallLogger : 1234 - jsonplaceholder-todos >> Response     : {
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
```
#### ELK Log
``` logs
2021-03-07 16:08:57.378  INFO - [      main]                           ELKLogger : {"application_session_id":"1234","elapsed_time":"816","end_point_url":"https://jsonplaceholder.typicode.com","method_name":"jsonplaceholder-todos","response_code":"200","response_msg":"OK","requestMethod":"GET"}```
```
# Usage Details:
Webservice consuming with single input parameters;
``` java
String inputParam= "foo";
```
``` yaml
methodName.query={ "title": "???", "body": "bar", "userId": 1}
{ "title": "???", "body": "???", "userId": ???}
```
Webservice consuming with multiple input parameters;
``` java
String[] inputParams= {"foo","bar","1"};
```
``` yaml
methodName.query={ "title": "???", "body": "???", "userId": ???}
```
or
``` java
String inputParam = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
```
``` yaml
methodName.query=???
```
Webservice consuming with token authentication;
``` java
CallServiceParams methodNameParams = new CallServiceParams.Builder("1234", "methodName",
		    inputParams).authorization("Bearer " + token).build();
```
Webservice consuming with integration and reporting;
``` java
WebServiceCallBuilder wsBuilder = new WebServiceCallFactory(methodNameParams);
```
Webservice consuming without integration and reporting;
``` java
WebServiceCallBuilder wsBasicBuilder = new WebServiceCallBasicFactory(methodNameParams);
```

Webservice parameters;
``` java
wsCall.getMethodName()
wsCall.getSessionId())
// input parameters
wsCall.getInputParamsOfService().getCallServiceParams();
wsCall.getInputParamsOfService().getSoapAction();
wsCall.getInputParamsOfService().getAuthorization();
wsCall.getInputParamsOfService().getRequestMethod();
wsCall.getInputParamsOfService().getConnTimeout();
wsCall.getInputParamsOfService().getContentType();
wsCall.getInputParamsOfService().getURL();
wsCall.getInputParamsOfService().getHost();
wsCall.getInputParamsOfService().getPort();
wsCall.getInputParamsOfService().getProtocol();
wsCall.getInputParamsOfService().getRequestBody();
wsCall.getInputParamsOfService().getAuthPass();
wsCall.getInputParamsOfService().getAuthUser();
wsCall.getInputParamsOfService().getProxyURL();
wsCall.getInputParamsOfService().getProxyPort();
//output parameters
wsCall.getOutputParams().getResponseCode();
wsCall.getOutputParams().getResponseMsg();
wsCall.getOutputParams().getResponseTime();
wsCall.getOutputParams().getResponseBody();
```
## Configuration Properties

Code block where feature files are read in the system

```java
sysProp = new SystemProperty();
File file = new File(sysProp.getOSPath() + "/AppConfig/connector/" + propertiesFileName + ".properties");
```

```java
public class SystemProperty {
    public String getOSPath() {
        String configFileUrl = "";
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Linux")) {
                Properties p = new Properties();
                p.put("path", "${DOMAIN_HOME}");
                configFileUrl = SystemEnvironment.resolveValueWithEnvVars(p.getProperty("path"));
            } else
                configFileUrl = Objects.requireNonNull(getClass().getClassLoader().getResource("")).getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return configFileUrl;
    }
}
```
### config.properties

"config.properties" file, prod/preprod/test information is setting.

```yaml
#value prod/preprod/test
connector.properties.status=test
```

if set "test" —> wsconnector_test.properties

if set "prod" —> wsconnector_prod.properties

In this way, we can enter different web service information in different environments.

### wsconnector_[prod/preprod/test].properties

Is the property file where the service information to be called is entered. You can find the required and optional fields below

``` yaml
# Root Settings
##########################
connector.connectionTimeout=10000
##########################
# Webservice
##########################
# required parameters
methodName.url=http://127.0.0.1:8080/API
methodName.requestMethod=GET
methodName.contentType=application/json

# If you want to append the service url to the header tag, you must start with "/". Otherwise it is set to the body tag.
methodName.query=/DATA/getDatalimit/???

# optional parameters
# service timeout value(miliseconds), default value:10000 if the connectionTimeout parameter is not use.
methodName.connectionTimeout=10000
methodName.soapAction=abc
# default value: false
methodName.needAuth=false
# authUser and authPass are disabled if the needAuth parameter is false
methodName.authUser=user
methodName.authPass=passwd
# default value: false
methodName.needProxy=false
#proxyUrl and proxyPort are disabled if the proxyUrl parameter is false
methodName.proxyUrl=test.intra
methodName.proxyPort=8080
```
