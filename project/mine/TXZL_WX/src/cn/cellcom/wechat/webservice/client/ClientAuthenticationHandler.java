package cn.cellcom.wechat.webservice.client;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;

import org.jdom.Element;




public class ClientAuthenticationHandler extends AbstractHandler {

	private Log log = LogFactory.getLog(ClientAuthenticationHandler.class);
	private String operator;

	public ClientAuthenticationHandler() {
		super();
	}
	
	public ClientAuthenticationHandler(String operator) {
		this.operator = operator;
	}


	

	public void invoke(MessageContext cfx) throws Exception {
			
		Element eHeader = new Element("header");
		Element eAuth = new Element("AuthenticationToken");
		Element eOperator = new Element("operator");
		eOperator.addContent(this.operator);
		eAuth.addContent(eOperator);
		eHeader.addContent(eAuth);
		
		cfx.getCurrentMessage().setHeader(eHeader);
		log.info("组装soap header完毕");
	}

	
}
