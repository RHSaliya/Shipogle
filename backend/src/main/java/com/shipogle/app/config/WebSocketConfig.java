package com.shipogle.app.config;

import com.shipogle.app.model.Notification;
import com.shipogle.app.socket_handlers.ChatSocketHandler;
import com.shipogle.app.socket_handlers.NotificationSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ChatSocketHandler.getInstance(), "/chatSocket/{userId}")
                .addHandler(NotificationSocketHandler.getInstance(), "/notificationSocket/{userId}")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }

    @Bean
    public ChatSocketHandler chatHandler() {
        return ChatSocketHandler.getInstance();
    }

    @Bean
    public NotificationSocketHandler notificationHandler() {
        return NotificationSocketHandler.getInstance();
    }

}
