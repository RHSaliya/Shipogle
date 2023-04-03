package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.model.ChatMessageRequest;
import com.shipogle.app.model.Message;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.MessageRepository;
import com.shipogle.app.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ChatController.class)
public class ChatControllerTest {
/*
    private User user1;
    private User user2;
    private Message message1;
    private Message message2;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setUser_Id(1);
        user1.setFirst_name("User");
        user1.setLast_name("One");

        user2 = new User();
        user2.setUser_Id(2);
        user2.setFirst_name("User");
        user2.setLast_name("Two");

        message1 = new Message();
        message1.setId(1L);
        message1.setSender(user1);
        message1.setReceiver(user2);
        message1.setMessage("Hello!");
        message1.setCreatedAt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        message2 = new Message();
        message2.setId(2L);
        message2.setSender(user2);
        message2.setReceiver(user1);
        message2.setMessage("Hi there!");
        message2.setCreatedAt(LocalDateTime.of(2022, 1, 1, 12, 1, 0));
    }

    @Test
    public void testSendMessage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageRepository messageRepository = Mockito.mock(MessageRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);


        ChatMessageRequest request = new ChatMessageRequest();
        request.setSenderId(1);
        request.setReceiverId(2);
        request.setMessage("Hello!");

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ChatController())
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/chat")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(argumentCaptor.capture());
        Message savedMessage = argumentCaptor.getValue();

        assert savedMessage.getSenderId().equals(user1.getUser_id());
        assert savedMessage.getReceiverId().equals(user2.getUser_id());
        assert savedMessage.getMessage().equals("Hello!");
    }*/
}