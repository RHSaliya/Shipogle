package com.shipogle.app.socket_handlers;


import com.shipogle.app.model.Notification;
import com.shipogle.app.utility.MyUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

public class NotificationSocketHandler extends TextWebSocketHandler {

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

        WebSocketSession receiverSession = sessions.get(MyUtils.getUniqueIdForSession(session));
        if (receiverSession != null)
            receiverSession.sendMessage(new TextMessage(payload));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(MyUtils.getUniqueIdForSession(session), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession sessionOld = sessions.remove(MyUtils.getUniqueIdForSession(session));
        if (sessionOld != null)
            sessionOld.close();
        super.afterConnectionClosed(session, status);
    }
}
