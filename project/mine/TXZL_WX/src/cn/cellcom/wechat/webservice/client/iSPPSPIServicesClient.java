
package cn.cellcom.wechat.webservice.client;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class iSPPSPIServicesClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public iSPPSPIServicesClient() {
        create0();
        Endpoint SPISynLocalEndpointEP = service0 .addEndpoint(new QName("http://ispp.com.cn/ispp_spi/", "SPISynLocalEndpoint"), new QName("http://ispp.com.cn/ispp_spi/", "SPISynLocalBinding"), "xfire.local://iSPPSPIServices");
        endpoints.put(new QName("http://ispp.com.cn/ispp_spi/", "SPISynLocalEndpoint"), SPISynLocalEndpointEP);
        Endpoint SPIServicesPortEP = service0 .addEndpoint(new QName("http://ispp.com.cn/ispp_spi/", "SPIServicesPort"), new QName("http://ispp.com.cn/ispp_spi/", "SPIServicesPort"), "http://132.96.63.61:8080/axis2/services/iSPPSPIServices");
        endpoints.put(new QName("http://ispp.com.cn/ispp_spi/", "SPIServicesPort"), SPIServicesPortEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((cn.cellcom.wechat.webservice.client.SPISyn.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://ispp.com.cn/ispp_spi/", "SPISynLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://ispp.com.cn/ispp_spi/", "SPIServicesPort"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public SPISyn getSPISynLocalEndpoint() {
        return ((SPISyn)(this).getEndpoint(new QName("http://ispp.com.cn/ispp_spi/", "SPISynLocalEndpoint")));
    }

    public SPISyn getSPISynLocalEndpoint(String url) {
        SPISyn var = getSPISynLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public SPISyn getSPIServicesPort() {
        return ((SPISyn)(this).getEndpoint(new QName("http://ispp.com.cn/ispp_spi/", "SPIServicesPort")));
    }

    public SPISyn getSPIServicesPort(String url) {
        SPISyn var = getSPIServicesPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        iSPPSPIServicesClient client = new iSPPSPIServicesClient();
        
		//create a default service endpoint
        SPISyn service = client.getSPIServicesPort();
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed");
        		System.exit(0);
    }

}
