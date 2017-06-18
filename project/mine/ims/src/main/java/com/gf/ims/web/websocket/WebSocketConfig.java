package com.gf.ims.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 *webSocket注册类 配置
 *
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		logger.info("-------------registerWebSocketHandlers-------------");
		 registry.addHandler(systemWebSocketHandler(),"/testSocket")
		 .addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
	}


	@Bean
	public WebSocketHandler systemWebSocketHandler(){
		logger.info("-------------systemWebSocketHandler-------------");
		return new ImsWebSocketHandler();
	}
	
	/*@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//添加这个Endpoint，这样在网页中就可以通过websocket连接上服务了  
		//注册消息连接点 coordination
		*//**
		 * SockJs是一个WebSocket的通信js库，Spring对这个js库进行了后台的自动支持，也就是说，我们如果使用SockJs，
		 * 那么我们就不需要对后台进行更多的配置，只需要加上这一句就可以了
		 * 使用SockJs还有一个好处，那就是对浏览器进行兼容，如果是IE11以下等对WebSocket支持不好的浏览器，
		 * SockJs会自动的将WebSocket降级到轮询，之前也说了，Spring对SockJs也进行了支持，也就是说，
		 * 如果加了withSockJs那句代码，那么服务器也会自动的降级为轮询
		 *//*
		registry.addEndpoint("/coordination").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//这里设置的simple broker是指可以订阅的地址，也就是服务器可以发送的地址
		//网页上要发送消息到服务器上的地址是/app/userChat。
		registry.enableSimpleBroker("/userChat");
		registry.setApplicationDestinationPrefixes("app");//设置前缀
	}*/
}
