
package com.extl.jade.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.utils.Configuration;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.utils.RuntimeLogger;


/**
 * This is the API for the Extility user service. You need to be a authenticated as a valid user to use this service.
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "UserAPI", targetNamespace = "http://extility.flexiant.net", wsdlLocation = "UserAPI.wsdl")
public class UserAPI
    extends Service
{

    private final static URL USERAPI_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.extl.jade.user.UserAPI.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            
            baseUrl = Configuration.class.getClassLoader().getResource("/config/UserAPI.wsdl");
            RuntimeLogger.logger.info("Get url get url get url "+baseUrl);
            url = new URL(baseUrl, "UserAPI.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'UserAPI.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        USERAPI_WSDL_LOCATION = url;
    }

    public UserAPI(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }
   
    public UserAPI() {
        super(USERAPI_WSDL_LOCATION, new QName("http://extility.flexiant.net", "UserAPI"));
    }

    /**
     * 
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServicePort")
    public UserService getUserServicePort() {
        return super.getPort(new QName("http://extility.flexiant.net", "UserServicePort"), UserService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServicePort")
    public UserService getUserServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://extility.flexiant.net", "UserServicePort"), UserService.class, features);
    }

}
