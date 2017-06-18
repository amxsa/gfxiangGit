package com.gf.ims.web.websocket;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * 握手类
 * @author Administrator
 *
 */
public class WebSocketHandshakeInterceptor  implements HandshakeInterceptor {

	private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		// TODO Auto-generated method stub
		URI uri = request.getURI();
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		logger.info("afterHandshake---------"+"uri----"+uri+"---------remoteAddress"+remoteAddress);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof  ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest=(ServletServerHttpRequest)request;
			HttpSession session = servletRequest.getServletRequest().getSession(true);
			attributes.put("sessionId", session.getId());
		}
		return true;
	}

}
