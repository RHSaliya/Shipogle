package com.shipogle.app.config;

import com.shipogle.app.socket_handlers.ChatSocketHandler;
import com.shipogle.app.socket_handlers.NotificationSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/*
 * Reference: https://spring.io/guides/gs/messaging-stomp-websocket/
 * Reference: https://www.baeldung.com/spring-websockets-send-message-to-user
 * Reference: https://blog.logrocket.com/websocket-tutorial-real-time-node-react/
 */

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
