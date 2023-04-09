package com.shipogle.app.socket_handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChatSocketHandlerTests {

    private ChatSocketHandler chatSocketHandler;
    private WebSocketSession session;
    private final int userOneId = 1234;
    private final int userTwoId = 5678;

    @Before
    public void setUp() {
        chatSocketHandler = ChatSocketHandler.getInstance();
        session = Mockito.mock(WebSocketSession.class);
        String requestURI = "/chatSocket/" + mockUniqueId();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(requestURI);
        request.setServerPort(8080);
        Mockito.when(session.getUri()).thenReturn(URI.create(requestURI));
    }

    @Test
    public void testHandleTextMessage() throws Exception {
        WebSocketSession chatSession = Mockito.mock(WebSocketSession.class);

        Mockito.when(chatSession.getUri()).thenReturn(URI.create("ws://localhost:8080/chat/"+ userTwoId + ChatSocketHandler.ID_SPLITTER + userOneId));

        String messagePayload = "Hello, world!";
        TextMessage message = new TextMessage(messagePayload);

        chatSocketHandler.afterConnectionEstablished(session);
        chatSocketHandler.afterConnectionEstablished(chatSession);

        chatSocketHandler.handleTextMessage(chatSession, message);

        chatSocketHandler.afterConnectionClosed(chatSession, new CloseStatus(1000, "closed"));
        chatSocketHandler.afterConnectionClosed(session, new CloseStatus(1000, "closed"));

        Mockito.verify(chatSession).sendMessage(message);
    }
    
    @Test
    public void testAfterConnectionClosed() throws Exception {
        WebSocketSession sessionToRemove = Mockito.mock(WebSocketSession.class);

        Mockito.when(sessionToRemove.getUri()).thenReturn(URI.create("/chatSocket/" + mockUniqueId()));

        CloseStatus closeStatus = new CloseStatus(1000, "closed");
        chatSocketHandler.afterConnectionEstablished(sessionToRemove);
        chatSocketHandler.afterConnectionClosed(sessionToRemove, closeStatus);
        Mockito.verify(sessionToRemove).close();
        assertNull(chatSocketHandler.getSessions().get(mockUniqueId()));
    }

    @Test
    public void testGetSendingUniqueID() {
        String expected = userTwoId + ChatSocketHandler.ID_SPLITTER + userOneId;
        String actual = chatSocketHandler.getSendingUniqueID(session);
        assertEquals(expected, actual);
    }
    
    private String mockUniqueId() {
        return userOneId + ChatSocketHandler.ID_SPLITTER + userTwoId;
    }
}
