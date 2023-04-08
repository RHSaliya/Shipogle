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


    @Test
    void testGetChatHistory() {
        // create userOne
        User userOne = new User();
        userOne.setId(1);
        userRepository.save(userOne);
        when(userRepository.findById(userOne.getUser_id())).thenReturn(Optional.of(userOne));

        // create userTwo
        User userTwo = new User();
        userTwo.setId(2);
        userRepository.save(userTwo);
        when(userRepository.findById(userTwo.getUser_id())).thenReturn(Optional.of(userTwo));

        // Prepare message from userOne to userTwo
        Message message1 = new Message();
        message1.setSender(userOne);
        message1.setReceiver(userTwo);
        message1.setMessage("Hello, userTwo!");
        message1.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message1);

        List<Message> messagesOneToTwo = new ArrayList<>();
        messagesOneToTwo.add(message1);

        when(messageRepository.findBySenderAndReceiverOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(messagesOneToTwo);

        // Prepare message from userTwo to userOne
        Message message2 = new Message();
        message2.setSender(userTwo);
        message2.setReceiver(userOne);
        message2.setMessage("Hi, userOne!");
        message2.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message2);

        List<Message> messagesTwoToOne = new ArrayList<>();
        messagesTwoToOne.add(message2);

        when(messageRepository.findByReceiverAndSenderOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(messagesTwoToOne);

        // get messages between userOne and userTwo
        List<Message> messages = chatController.getChatHistory(userOne.getUser_id(), userTwo.getUser_id());

        // check if messages are correct
        assertEquals(2, messages.size());
        assertEquals(message1.getMessage(), messages.get(0).getMessage());
        assertEquals(message2.getMessage(), messages.get(1).getMessage());
    }



}
