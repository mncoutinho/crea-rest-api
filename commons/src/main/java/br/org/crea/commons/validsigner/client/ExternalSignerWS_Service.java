package br.org.crea.commons.validsigner.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ExternalSignerWS", targetNamespace = "http://ws.web.pad.cd.valid.com.br/", wsdlLocation = "http://10.103.1.42:8080/pad-ws/ExternalSignerWS?wsdl")
public class ExternalSignerWS_Service
    extends Service
{

    private final static URL EXTERNALSIGNERWS_WSDL_LOCATION;
    private final static WebServiceException EXTERNALSIGNERWS_EXCEPTION;
    private final static QName EXTERNALSIGNERWS_QNAME = new QName("http://ws.web.pad.cd.valid.com.br/", "ExternalSignerWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://10.103.1.42:8080/pad-ws/ExternalSignerWS?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        EXTERNALSIGNERWS_WSDL_LOCATION = url;
        EXTERNALSIGNERWS_EXCEPTION = e;
    }

    public ExternalSignerWS_Service() {
        super(__getWsdlLocation(), EXTERNALSIGNERWS_QNAME);
    }

    public ExternalSignerWS_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), EXTERNALSIGNERWS_QNAME, features);
    }

    public ExternalSignerWS_Service(URL wsdlLocation) {
        super(wsdlLocation, EXTERNALSIGNERWS_QNAME);
    }

    public ExternalSignerWS_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, EXTERNALSIGNERWS_QNAME, features);
    }

    public ExternalSignerWS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ExternalSignerWS_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ExternalSignerWS
     */
    @WebEndpoint(name = "ExternalSignerWSPort")
    public ExternalSignerWS getExternalSignerWSPort() {
        return super.getPort(new QName("http://ws.web.pad.cd.valid.com.br/", "ExternalSignerWSPort"), ExternalSignerWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ExternalSignerWS
     */
    @WebEndpoint(name = "ExternalSignerWSPort")
    public ExternalSignerWS getExternalSignerWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.web.pad.cd.valid.com.br/", "ExternalSignerWSPort"), ExternalSignerWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (EXTERNALSIGNERWS_EXCEPTION!= null) {
            throw EXTERNALSIGNERWS_EXCEPTION;
        }
        return EXTERNALSIGNERWS_WSDL_LOCATION;
    }

}
