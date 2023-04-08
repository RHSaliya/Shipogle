package com.shipogle.app.config;

import com.shipogle.app.model.Notification;
import com.shipogle.app.model.User;
import com.shipogle.app.socket_handlers.ChatSocketHandler;
import com.shipogle.app.socket_handlers.NotificationSocketHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class WebSocketConfigTest {

    @Test
    public void testNotificationSocketHandler() throws Exception {
        WebSocketSession session = Mockito.mock(WebSocketSession.class);
        Mockito.when(session.getUri()).thenReturn(new URI("ws://localhost:8080/notification/60"));

        Notification notification = new Notification();
        notification.setMessage("Hello, world!");
        notification.setTitle("Test");
        notification.setPayload("{}");
        notification.setType("test");
        notification.setCreatedAt(LocalDateTime.now());
        User user = new User();
        user.setUser_Id(60);
        notification.setUser(user);

        String expectedPayload = notification.toString();

        NotificationSocketHandler handler = NotificationSocketHandler.getInstance();
        handler.afterConnectionEstablished(session);
        handler.sendNotification(notification);

        Mockito.verify(session, Mockito.times(1)).sendMessage(new TextMessage(expectedPayload));
    }

    @Test
    public void testWebSocketOrigins() {
        WebSocketConfigurer configurer = new WebSocketConfig();

        WebSocketHandlerRegistry registry = Mockito.mock(WebSocketHandlerRegistry.class);
        WebSocketHandlerRegistration registration = Mockito.mock(WebSocketHandlerRegistration.class);

        Mockito.when(registry.addHandler(Mockito.any(), Mockito.any())).thenReturn(registration);
        Mockito.when(registration.addHandler(Mockito.any(), Mockito.any())).thenReturn(registration);
        Mockito.when(registration.setAllowedOrigins(Mockito.any())).thenReturn(registration);
        Mockito.when(registration.addInterceptors(Mockito.any())).thenReturn(registration);
        Mockito.when(registration.setHandshakeHandler(Mockito.any())).thenReturn(registration);

        configurer.registerWebSocketHandlers(registry);

        Mockito.verify(registration, Mockito.times(1)).setAllowedOrigins("*");
    }

    @Test
    public void testWebSocketInterceptors() {
        WebSocketConfigurer configurer = new WebSocketConfig();

        WebSocketHandlerRegistry registry = Mockito.mock(WebSocketHandlerRegistry.class);
        WebSocketHandlerRegistration registration = Mockito.mock(WebSocketHandlerRegistration.class);

        Mockito.when(registry.addHandler(Mockito.any(), Mockito.any())).thenReturn(registration);
        Mockito.when(registration.addHandler(Mockito.any(), Mockito.any())).thenReturn(registration);
        Mockito.when(registration.setAllowedOrigins(Mockito.any())).thenReturn(registration);
        Mockito.when(registration.addInterceptors(Mockito.any())).thenReturn(registration);
        Mockito.when(registration.setHandshakeHandler(Mockito.any())).thenReturn(registration);

        configurer.registerWebSocketHandlers(registry);

        Mockito.verify(registration, Mockito.times(1)).addInterceptors(Mockito.any(HttpSessionHandshakeInterceptor.class));
    }

    @Test
    public void testWebSocketHandshakeHandler() {
        WebSocketConfigurer configurer = new WebSocketConfig();

        WebSocketHandlerRegistry registry = Mockito.mock(WebSocketHandlerRegistry.class);
        WebSocketHandlerRegistration registration = Mockito.mock(WebSocketHandlerRegistration.class);

        Mockito.when(registry.addHandler(Mockito.any(), Mockito.any())).thenReturn(registration);
        Mockito.when(registration.addHandler(Mockito.any(), Mockito.any())).thenReturn(registration);
        Mockito.when(registration.setAllowedOrigins(Mockito.any())).thenReturn(registration);
        Mockito.when(registration.addInterceptors(Mockito.any())).thenReturn(registration);
        Mockito.when(registration.setHandshakeHandler(Mockito.any())).thenReturn(registration);

        configurer.registerWebSocketHandlers(registry);

        Mockito.verify(registration, Mockito.times(1)).setHandshakeHandler(Mockito.any(DefaultHandshakeHandler.class));
    }


    @Test
    public void testChatHandlerBean() {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        ChatSocketHandler chatHandler = webSocketConfig.chatHandler();

        // Assert that the chatHandler bean is not null
        assertNotNull(chatHandler);
    }

    @Test
    public void testNotificationHandlerBean() {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        NotificationSocketHandler notificationHandler = webSocketConfig.notificationHandler();

        // Assert that the notificationHandler bean is not null
        assertNotNull(notificationHandler);
    }
}

