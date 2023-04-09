package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shipogle.app.Application;
import com.shipogle.app.model.ChatMessageRequest;
import com.shipogle.app.model.DriverRoute;
import com.shipogle.app.model.Rating;
import com.shipogle.app.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class RatingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingService ratingService;

    private ObjectMapper objectMapper;
    String token = "";

    @Test
    public void postRatingTest() throws Exception {
        String endpoint = "/rating/post";
        String req = "{\"driver_route_id\": 25,\"star\": 4.5,\"review\": \"Test review of driver\"}";


        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P")
                        .content(req))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDelivererRatingTest() throws Exception {
        String endpoint = "/rating/deliverer/getall";


        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getSenderPostedRatingTest() throws Exception {
        String endpoint = "/rating/posted/getall";


        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDelivererRatingByIDTest() throws Exception {
        String endpoint = "/rating/deliverer?driver_id=40";


        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
