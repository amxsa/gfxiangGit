
package cn.cellcom.wechat.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "InternalServicePortType", targetNamespace = "http://services.ws.callforward.assist.cellcom.cn")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface InternalServicePortType {

	@WebMethod(operationName = "InternalCallforwardAPI", action = "")
	@WebResult(name = "out", targetNamespace = "http://services.ws.callforward.assist.cellcom.cn")
	public String internalCallforwardAPI(
			@WebParam(name = "in0", targetNamespace = "http://services.ws.callforward.assist.cellcom.cn")
			String in0,
			@WebParam(name = "in1", targetNamespace = "http://services.ws.callforward.assist.cellcom.cn")
			String in1,
			@WebParam(name = "in2", targetNamespace = "http://services.ws.callforward.assist.cellcom.cn")
			String in2,
			@WebParam(name = "in3", targetNamespace = "http://services.ws.callforward.assist.cellcom.cn")
			String in3);

}
