
package cn.cellcom.wechat.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import cn.com.ispp.ispp_spi.ConfirmMsg;
import cn.com.ispp.ispp_spi.ResultMsg;

@WebService(name = "SPISyn", targetNamespace = "http://ispp.com.cn/ispp_spi/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SPISyn {

	@WebMethod(operationName = "SYNSPIAPI", action = "http://ispp.com.cn/ispp_spi/SYNSPIAPI")
	@WebResult(name = "ResultMsg", targetNamespace = "http://ispp.com.cn/ispp_spi/")
	public ResultMsg sYNSPIAPI(
			@WebParam(name = "WorkOrderMsgSYN", targetNamespace = "http://ispp.com.cn/ispp_spi/")
			cn.com.ispp.ispp_spi.WorkOrderMsgSYN WorkOrderMsgSYN,
			@WebParam(name = "Username", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String Username,
			@WebParam(name = "Password", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String Password,
			@WebParam(name = "MessageID", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String MessageID,
			@WebParam(name = "Address", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String Address);

	@WebMethod(operationName = "ASYSPIAPI", action = "http://ispp.com.cn/ispp_spi/ASYSPIAPI")
	@WebResult(name = "ConfirmMsg", targetNamespace = "http://ispp.com.cn/ispp_spi/")
	public ConfirmMsg aSYSPIAPI(
			@WebParam(name = "WorkOrderMsgASY", targetNamespace = "http://ispp.com.cn/ispp_spi/")
			cn.com.ispp.ispp_spi.WorkOrderMsgASY WorkOrderMsgASY,
			@WebParam(name = "Username", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String Username,
			@WebParam(name = "Password", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String Password,
			@WebParam(name = "MessageID", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String MessageID,
			@WebParam(name = "Address", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String Address,
			@WebParam(name = "RelateTo", targetNamespace = "http://ispp.com.cn/ispp_spi/", header = true)
			String RelateTo);

}
