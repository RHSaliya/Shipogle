package com.shipogle.app.controller;

import com.shipogle.app.model.ChatMessageRequest;
import com.shipogle.app.model.Message;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.MessageRepository;
import com.shipogle.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatController chatController;

    @Test
    public void sendMessageTest() {
        User sender = new User();
        sender.setId(1);

        User receiver = new User();
        receiver.setId(2);

        ChatMessageRequest request = new ChatMessageRequest();
        request.setSenderId(1);
        request.setReceiverId(2);
        request.setMessage("Hello");

        when(userRepository.findById(request.getSenderId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(request.getReceiverId())).thenReturn(Optional.of(receiver));

        ResponseEntity<?> responseEntity = chatController.sendMessage(request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(messageRepository, times(1)).save(any(Message.class));
    }
    @Test
    public void getChatUsersTest() {
        User user = new User();
        user.setId(1);

        List<Integer> userIds = Arrays.asList(2, 3, 4);

        when(userRepository.findById(user.getUser_id())).thenReturn(Optional.of(user));
        when(messageRepository.findDistinctSenderAndReceiverIdsByUserId(user.getUser_id())).thenReturn(userIds);
        when(userRepository.getUserByIds(userIds)).thenReturn(Arrays.asList(new User(), new User(), new User()));

        List<User> chatUsers = chatController.getChatUsers(user.getUser_id());

        assertEquals(3, chatUsers.size());
        verify(messageRepository, times(1)).findDistinctSenderAndReceiverIdsByUserId(user.getUser_id());
        verify(userRepository, times(1)).getUserByIds(userIds);
    }

    @Test
    public void removeMessageTest() {
        long messageId = 1L;

        chatController.removeMessage(messageId);

        verify(messageRepository, times(1)).deleteById(messageId);
    }


}
