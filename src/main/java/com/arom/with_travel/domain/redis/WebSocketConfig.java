package com.arom.with_travel.domain.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){

        registry.enableSimpleBroker("/sub");

        registry.setApplicationDestinationPrefixes("/pub");
    }


    //STOMP엔드포인트 등록 메서드
    //클라이언트가 웹소켓 연결할 수 있는 엔드 포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();

//        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*");
    }
}
