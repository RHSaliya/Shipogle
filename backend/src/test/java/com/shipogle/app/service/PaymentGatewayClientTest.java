package com.shipogle.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentGatewayClientTest {

    private PaymentGatewayClient paymentGatewayClient;
    @MockBean
    private RestTemplate restTemplate;
    //@Value("${payment.gateway.url}")
    private String paymentGatewayUrl = "http://mockUrl";

    private HttpEntity<String> requestEntity;
    private PaymentGatewayRequest paymentRequest;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        paymentGatewayClient = new PaymentGatewayClient();
        paymentGatewayClient.setRestTemplate(restTemplate);
        paymentGatewayClient.setPaymentGatewayUrl(paymentGatewayUrl);

        paymentRequest = createPaymentRequest();
        HttpHeaders expectedHeaders = createExpectedHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(paymentRequest);
        requestEntity = new HttpEntity<>(jsonString, expectedHeaders);
    }

//    @Test
//    public void testChargeCreditCard_SuccessfulPayment() throws PaymentGatewayException, JsonProcessingException {
//        // Arrange
//        PaymentGatewayRequest paymentRequest = createPaymentRequest();
//        PaymentResponse expectedResponse = createExpectedResponse();
//
//        // serialize the expectedResponse object to JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
//
//        ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedJson, HttpStatus.OK);
//
//        RestTemplate restTemplateMock = mock(RestTemplate.class);
//        Mockito.when(restTemplateMock.exchange(
//               anyString(),
//               eq(HttpMethod.GET),
//               any(),
//               eq(String.class)
//        )).thenReturn(ResponseEntity.status(HttpStatus.OK).body(expectedJson));
//
//        //when(restTemplate.exchange(eq(paymentGatewayUrl), Mockito.HttpMethod.POST, Mockito.anyString(), eq(String.class))).thenReturn(responseEntity);
//        //when(restTemplate.exchange(Mockito.any(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(String.class))).thenReturn(responseEntity);
//        //when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenReturn(responseEntity);
//
//
//        // Act
//        PaymentResponse actualResponse = paymentGatewayClient.chargeCreditCard(paymentRequest);
//
//        // Assert
//        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
//        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
//    }

    @Test
    public void testChargeCreditCard_FailedPayment() {
        // Arrange
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.exchange(eq(paymentGatewayUrl), eq(HttpMethod.POST), eq(requestEntity), eq(String.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(PaymentGatewayException.class, () -> paymentGatewayClient.chargeCreditCard(paymentRequest));
    }

    @Test
    public void testChargeCreditCard_NetworkError() {
        // Arrange
        when(restTemplate.exchange(eq(paymentGatewayUrl), eq(HttpMethod.POST), eq(requestEntity), eq(String.class))).thenThrow(new RuntimeException("Network Error"));

        // Act & Assert
        assertThrows(PaymentGatewayException.class, () -> paymentGatewayClient.chargeCreditCard(paymentRequest));
    }

    private PaymentGatewayRequest createPaymentRequest() {
        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
        paymentRequest.setCardHolderName("Stuart Clark");
        paymentRequest.setCardNumber("4111111111111111");
        paymentRequest.setCardCvv("123");
        paymentRequest.setAmount(new BigDecimal("10.0"));
        paymentRequest.setCardExpiryMonth(12);
        paymentRequest.setCardExpiryYear(2022);
        paymentRequest.setCurrency("USD");
        return paymentRequest;
    }

    private HttpHeaders createExpectedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private PaymentResponse createExpectedResponse() {
        PaymentResponse response = new PaymentResponse();
        response.setStatus("Completed");
        response.setMessage("Amount Received");
        return response;
    }
}
