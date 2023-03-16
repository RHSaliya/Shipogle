package com.shipogle.app.config;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/chatSocket/{userId}")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }


    @Bean
    public WebSocketHandler myHandler() {
        return new SocketHandler();
    }

    public static class SocketHandler extends TextWebSocketHandler {

        HashMap<String, WebSocketSession> sessions = new HashMap<>();

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
            String payload = message.getPayload();
            System.out.println("Message received: " + payload);
            // TODO: From here send message to other user
            session.sendMessage(new TextMessage(payload));

            // TODO: Send message to other user
            JsonParser jsonParser = JsonParserFactory.getJsonParser();
            Map<String, Object> jsonMap = jsonParser.parseMap(payload);
            String receiverId = jsonMap.get("receiverId").toString();
            WebSocketSession receiverSession = sessions.get(receiverId);
            if (receiverSession != null) {
                receiverSession.sendMessage(new TextMessage(payload));
            }
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            String url = Objects.requireNonNull(session.getUri()).toString();
            String userId = url.substring(url.lastIndexOf("/") + 1);
            sessions.put(userId, session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            String url = Objects.requireNonNull(session.getUri()).toString();
            String userId = url.substring(url.lastIndexOf("/") + 1);
            WebSocketSession sessionOld = sessions.remove(userId);
            sessionOld.close();
            super.afterConnectionClosed(session, status);
        }
    }
}
