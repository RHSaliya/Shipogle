//package com.shipogle.app.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shipogle.app.model.PaymentGatewayRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.math.BigDecimal;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class PaymentControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private String token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InF1cGlmdWdpdG9AZ290Z2VsLm9yZyIsInN1YiI6IlNoaXZhbSIsImlhdCI6MTY4MDkzNzY0OCwiZXhwIjoxNjgxMDI0MDQ4fQ.gHGmWb-3bFScWgq5Z_zizfCPd-FwrvBI6qTvDSQsQ1CmwNjJd1AbnMh_ZAP-0Nbl";
//
//    @Test
//    public void testChargeCreditCard() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        PaymentGatewayRequest paymentGatewayRequest = new PaymentGatewayRequest();
//        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
//        paymentRequest.setAmount(new BigDecimal("189.00"));
//        paymentRequest.setCurrency("USD");
//        paymentRequest.setCardNumber("4112323111111111");
//        paymentRequest.setCardExpiryMonth(12);
//        paymentRequest.setCardExpiryYear(2025);
//        paymentRequest.setCardCvv("567");
//        paymentRequest.setCardHolderName("Stuart Clark");
//
//        String jsonString = objectMapper.writeValueAsString(paymentGatewayRequest);
//
//        MvcResult result = mockMvc.perform(post("/payment/charge")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization",
//                                "Bearer " + token + "\"")
//                        .content(jsonString))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//}
//
