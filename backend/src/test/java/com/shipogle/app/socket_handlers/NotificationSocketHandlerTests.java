package com.shipogle.app.socket_handlers;

import com.shipogle.app.model.Notification;
import com.shipogle.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class NotificationSocketHandlerTests {

    private NotificationSocketHandler notificationSocketHandler;
    private WebSocketSession session;

    private int userId = 60;
    private final int CLOSE_STATUS = 1000;
    private final int EXPECTED_INVOCATION = 2;

    @BeforeEach
    public void setUp() {
        notificationSocketHandler = NotificationSocketHandler.getInstance();
        session = mock(WebSocketSession.class);
        Mockito.when(session.getUri()).thenReturn(URI.create("ws://localhost:8080/notification/"+userId));
        notificationSocketHandler.afterConnectionEstablished(session);
    }


    @Test
    public void testSendNotification() throws Exception {
        Notification notification = getMockNotification();

        String expectedPayload = notification.toString();

        NotificationSocketHandler handler = NotificationSocketHandler.getInstance();
        handler.afterConnectionEstablished(session);
        handler.sendNotification(notification.getUserId(), notification);

        handler.handleTextMessage(session, new TextMessage(expectedPayload));

        Mockito.verify(session, Mockito.times(EXPECTED_INVOCATION)).sendMessage(new TextMessage(expectedPayload));
    }


    @Test
    void testAfterConnectionClosed() throws Exception {
        WebSocketSession sessionToRemove = Mockito.mock(WebSocketSession.class);

        Mockito.when(sessionToRemove.getUri()).thenReturn(URI.create("/notificationSocket/"+userId));

        CloseStatus closeStatus = new CloseStatus(CLOSE_STATUS, "closed");
        notificationSocketHandler.afterConnectionEstablished(sessionToRemove);
        notificationSocketHandler.afterConnectionClosed(sessionToRemove, closeStatus);

        Mockito.verify(sessionToRemove).close();

        Assertions.assertNull(notificationSocketHandler.getSessions().get(String.valueOf(userId)));
    }

    @Test
    void testSendNotificationWithException(){
        Notification notification = getMockNotification();
        notification.setUser(new User());
        assertThrows(RuntimeException.class, () -> notificationSocketHandler.sendNotification(notification.getUserId(), null));
    }

    public Notification getMockNotification(){
        Notification notification = new Notification();
        notification.setMessage("Hello, world!");
        notification.setTitle("Test");
        notification.setPayload("{}");
        notification.setType("test");
        notification.setCreatedAt(LocalDateTime.now());

        User user = new User();
        user.setUser_Id(userId);
        notification.setUser(user);

        return notification;
    }
}
