package com.shipogle.app.utility;

import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

public class MyUtils {
    public static String getUniqueIdForSession(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
