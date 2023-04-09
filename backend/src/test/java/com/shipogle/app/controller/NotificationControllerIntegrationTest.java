package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.socket_handlers.NotificationSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSendMessageIntegration() throws Exception {
        String endpoint = "/notifications/";

        Map<String, String> json = new HashMap<>();

        json.put("userId", "1138");
        json.put("title", "Test notification title");
        json.put("message", "Test notification message");

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P")
                        .content(objectMapper.writeValueAsString(json)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
