package com.shipogle.app.socket_handlers;


import com.shipogle.app.utility.MyUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ChatSocketHandler extends TextWebSocketHandler {
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
        sessions.put(MyUtils.getUniqueIdForSession(session), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession sessionOld = sessions.remove(MyUtils.getUniqueIdForSession(session));
        if (sessionOld != null)
            sessionOld.close();
        super.afterConnectionClosed(session, status);
    }

    String getSendingUniqueID(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        String userId = url.substring(url.lastIndexOf("/") + 1);
        String[] ids = userId.split(ID_SPLITTER);
        return ids[1] + ID_SPLITTER + ids[0];
    }
}
