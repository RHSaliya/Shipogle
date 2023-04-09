package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.model.PaymentGatewayRequest;
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

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testChargeCreditCard() throws Exception {
        String endpoint = "/payment/charge";
        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
        paymentRequest.setAmount(new BigDecimal("189.00"));
        paymentRequest.setCurrency("USD");
        paymentRequest.setCardNumber("4112323111111111");
        paymentRequest.setCardExpiryMonth(12);
        paymentRequest.setCardExpiryYear(2025);
        paymentRequest.setCardCvv("567");
        paymentRequest.setCardHolderName("Stuart Clark");
        System.out.println(objectMapper.writeValueAsString(paymentRequest));

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P")
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
