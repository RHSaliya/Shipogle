package com.shipogle.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/chatSocket")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }


    @Bean
    public WebSocketHandler myHandler() {
        return new SocketHandler();
    }

    public static class SocketHandler extends TextWebSocketHandler {

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
            System.out.println("Message received: " + message.getPayload());

            // TODO: From here send message to other user
            session.sendMessage(new TextMessage(message.getPayload()));
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws IOException {
            System.out.println("Connection established");
        }
    }
}
