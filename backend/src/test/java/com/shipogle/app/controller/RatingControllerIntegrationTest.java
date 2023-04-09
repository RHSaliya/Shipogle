package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shipogle.app.Application;
import com.shipogle.app.model.ChatMessageRequest;
import com.shipogle.app.model.DriverRoute;
import com.shipogle.app.model.Rating;
import com.shipogle.app.service.RatingService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//@AutoConfigureMockMvc

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

//    @Before
//    public void setUp() {
//        objectMapper = new ObjectMapper();
//        String token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InF1cGlmdWdpdG9AZ290Z2VsLm9yZyIsInN1YiI6IlNoaXZhbSIsImlhdCI6MTY4MDkzNjQ2MCwiZXhwIjoxNjgxMDIyODYwfQ.dQ5bC5Nb88ZyRIdoARdksAweYoA82sciLFiCKI4Rfk29k2XVcpYZPXogNDEYTdpH";
//    }
//
//    @Test
//    public void postRatingTest() throws Exception {
//        // Given
//        Long driverRouteId = 1L;
//        Float star = 4.5F;
//        String review = "Great service!";
//        Map<String,String> req = new HashMap<>();
//        String req_content = "{\"driver_route_id\": 25,\"star\": 4.5,\"review\": \"Test review of driver\"}";
//
//        // When
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(req_content))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        // Then
//        assertThat(response).isNotBlank();
//    }
//
//    @Test
//    public void getDelivererRatingTest() throws Exception {
//        // Given
//        Rating rating = new Rating();
//        rating.setDriverRoute(new DriverRoute());
//        rating.setStar(4.5F);
//        rating.setReview("Great service!");
//
//        ratingService.storeRating(rating.getDriverRoute().getDriverRouteId(), rating.getStar(), rating.getReview());
//
//        // When
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rating/deliverer/getall")
//                .header("Authorization", "Bearer " + token + "\""))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        // Then
//        assertThat(response).contains(rating.getReview());
//    }
//
//    @Test
//    public void getSenderPostedRatingTest() throws Exception {
//        // Given
//        Rating rating = new Rating();
//        rating.setDriverRoute(new DriverRoute());
//        rating.setStar(4.5F);
//        rating.setReview("Great service!");
//
//        ratingService.storeRating(rating.getDriverRoute().getDriverRouteId(), rating.getStar(), rating.getReview());
//        // When
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rating/posted/getall")
//                .header("Authorization", "Bearer " + token + "\""))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        // Then
//        assertThat(response).contains(rating.getReview());
//    }
//
//    @Test
//    public void deleteRatingTest() throws Exception {
//        // Given
//        Rating rating = new Rating();
//        rating.setDriverRoute(new DriverRoute());
//        rating.setStar(4.5F);
//        rating.setReview("Great service!");
//
//        ratingService.storeRating(rating.getDriverRoute().getDriverRouteId(), rating.getStar(), rating.getReview());
//        // When
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/rating/delete")
//                        .header("Authorization", "Bearer " + token + "\"")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(Collections.singletonMap("rating_id", "1"))))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        // Then
//        assertThat(response).contains("Rating deleted successfully");
//        // Check that the rating was actually deleted
//        assertThat(ratingService.getDelivererRating()).isEmpty();
//    }
}
