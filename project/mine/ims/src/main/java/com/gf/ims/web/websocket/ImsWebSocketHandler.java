package com.gf.ims.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class ImsWebSocketHandler implements WebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ImsWebSocketHandler.class);
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		logger.info("indexconfig websocket connection closed......");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		// TODO Auto-generated method stub
		logger.info("indexconfig connect to the websocket success......");
	}

	/**
	 * 处理前端发起的信息
	 */
	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		 if (webSocketSession.isOpen()) {
	            webSocketSession.close();
	        }
	        logger.info("indexconfig websocket connection closed......");
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return true;
	}

}
