
package cn.cellcom.wechat.webservice.client;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;

import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import cn.cellcom.wechat.common.Env;



public class InternalServiceClient {
	
	private static Log log = LogFactory.getLog(InternalServiceClient.class);

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;


    public InternalServiceClient() {
        create0();
        Endpoint InternalServicePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServicePortTypeLocalEndpoint"), new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServicePortTypeLocalBinding"), "xfire.local://InternalService");
        endpoints.put(new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServicePortTypeLocalEndpoint"), InternalServicePortTypeLocalEndpointEP);
        Endpoint InternalServiceHttpPortEP = service0 .addEndpoint(new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServiceHttpPort"), new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServiceHttpBinding"), Env.INTERNAL_ADDRESS);
        endpoints.put(new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServiceHttpPort"), InternalServiceHttpPortEP);
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
      
        service0 = asf.create((cn.cellcom.wechat.webservice.client.InternalServicePortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServiceHttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServicePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
        
        HttpClientParams params = new HttpClientParams();
		params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
		params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, 100l); 
        service0.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
        
    }

    public InternalServicePortType getInternalServicePortTypeLocalEndpoint() {
        return ((InternalServicePortType)(this).getEndpoint(new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServicePortTypeLocalEndpoint")));
    }

    public InternalServicePortType getInternalServicePortTypeLocalEndpoint(String url) {
        InternalServicePortType var = getInternalServicePortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public InternalServicePortType getInternalServiceHttpPort() {
        return ((InternalServicePortType)(this).getEndpoint(new QName("http://services.ws.callforward.assist.cellcom.cn", "InternalServiceHttpPort")));
    }

    public InternalServicePortType getInternalServiceHttpPort(String url) {
        InternalServicePortType var = getInternalServiceHttpPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        InternalServiceClient client = new InternalServiceClient();
        
		//create a default service endpoint
        InternalServicePortType service = client.getInternalServiceHttpPort();
       
        String ss = service.internalCallforwardAPI("1","1", "1", "1");
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed"+ss);
        		System.exit(0);
    }

}
