package com.shipogle.app.config;

import com.shipogle.app.model.Notification;
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


    private static String getUniqueID(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static class NotificationSocketHandler extends TextWebSocketHandler {

        private static NotificationSocketHandler instance;
        public static NotificationSocketHandler getInstance() {
            if (instance == null)
                instance = new NotificationSocketHandler();
            return instance;
        }

        private static final HashMap<String, WebSocketSession> sessions = new HashMap<>();

        public void sendNotification(Notification notification){
            WebSocketSession receiverSession = sessions.get(String.valueOf(notification.getUserId()));
            if (receiverSession != null)
                try {
                    receiverSession.sendMessage(new TextMessage(notification.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
            String payload = message.getPayload();

            WebSocketSession receiverSession = sessions.get(getUniqueID(session));
            if (receiverSession != null)
                receiverSession.sendMessage(new TextMessage(payload));
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            sessions.put(getUniqueID(session), session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            WebSocketSession sessionOld = sessions.remove(getUniqueID(session));
            if (sessionOld != null)
                sessionOld.close();
            super.afterConnectionClosed(session, status);
        }
    }

    public static class ChatSocketHandler extends TextWebSocketHandler {
        private static ChatSocketHandler instance;
        public static ChatSocketHandler getInstance() {
            if (instance == null)
                instance = new ChatSocketHandler();
            return instance;
        }

        private static final HashMap<String, WebSocketSession> sessions = new HashMap<>();
        private static final String ID_SPLITTER = "!";

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
            String payload = message.getPayload();
            System.out.println("Message received: " + payload);

            session.sendMessage(new TextMessage(payload));

            WebSocketSession receiverSession = sessions.get(getSendingUniqueID(session));
            if (receiverSession != null)
                receiverSession.sendMessage(new TextMessage(payload));
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            sessions.put(getUniqueID(session), session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            WebSocketSession sessionOld = sessions.remove(getUniqueID(session));
            if (sessionOld != null)
                sessionOld.close();
            super.afterConnectionClosed(session, status);
        }

        private String getSendingUniqueID(WebSocketSession session) {
            String url = Objects.requireNonNull(session.getUri()).toString();
            String userId = url.substring(url.lastIndexOf("/") + 1);
            String[] ids = userId.split(ID_SPLITTER);
            return ids[1] + ID_SPLITTER + ids[0];
        }
    }

}
