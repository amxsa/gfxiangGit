package cn.cellcom.esb.bus;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import cn.cellcom.wechat.common.ConfLoad;

public class ESBClient {

	private String buildSoapRequestXml(String userName, String passWord,
			String xml) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb
				.append("<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		sb
				.append(
						"<soap:Header  xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">")
				.append(
						"	<wsse:Security  soap:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">")
				.append(
						" <wsse:UsernameToken  wsu:Id=\"unt_ocjPpjDSinrEWC8R\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">")
				.append(" 	<wsse:Username>")
				.append(userName)
				.append("</wsse:Username>")
				.append(
						" 	<wsse:Password  Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">")
				.append(passWord).append("</wsse:Password>").append(
						"	</wsse:UsernameToken>").append("	</wsse:Security>")
				.append("</soap:Header>");
		sb.append("<soapenv:Body>");
		sb.append("<mbos:call  xmlns:mbos=\"mboss-esb\">").append(
				"<esb:data  xmlns:esb=\"mboss/esb\">").append(
				"<esb:sendData><![CDATA[").append(xml).append(
				"]]></esb:sendData></esb:data></mbos:call>");
		sb.append("</soapenv:Body></soapenv:Envelope>");
		return sb.toString();
	}

	public String callWebService(String userName, String passWord,
			String soapAction, String xml, int timeout) throws Exception {
		String result = null;
		StringBuffer sb = new StringBuffer();
		PostMethod post = new PostMethod(ConfLoad.getProperty("CRM_URL"));
		String xmlData = buildSoapRequestXml(userName, passWord, xml);
		System.out.println(xmlData);
		RequestEntity entity = new StringRequestEntity(xmlData, "text/xml",
				"UTF-8");
		post.setRequestEntity(entity);
		post.setRequestHeader("SOAPAction", soapAction);
		HttpClient httpclient = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager());// 这样设置是为了在method.releaseConnection();之后connection
		// manager会关闭connection 。
		try {
			HttpConnectionManagerParams managerParams = httpclient
			.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(timeout);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(timeout);
			int stateCode = httpclient.executeMethod(post);
			if (stateCode == HttpStatus.SC_OK) {
				result = post.getResponseBodyAsString();
				
				if (result != null) {
					Document doc = DocumentHelper.parseText(result);
					String prefix = doc.getRootElement().getNamespacePrefix();
					Element elmBody = (Element) doc.getRootElement()
							.selectSingleNode(prefix + ":Body");
					if (elmBody.selectSingleNode(prefix + ":Fault") != null) { // 返回soap错误
						result = elmBody.selectSingleNode(prefix + ":Fault")
								.asXML();
					} else {
						Node node = elmBody.selectSingleNode("*[1]/*[1]");
						return node.getText();
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				post.releaseConnection();
				httpclient.getHttpConnectionManager().closeIdleConnections(0);
				httpclient = null;
			} catch (Exception e) {
			}
		}
		return null;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
