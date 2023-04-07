//package com.shipogle.app.service;
//
//import com.shipogle.app.exception.PaymentGatewayException;
//import com.shipogle.app.model.PaymentGatewayRequest;
//import com.shipogle.app.model.PaymentResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class PaymentGatewayClientTest {
//
//    private PaymentGatewayClient paymentGatewayClient;
//    @Mock
//    private RestTemplate restTemplate;
//    @Value("${payment.gateway.url}")
//    private String paymentGatewayUrl;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        paymentGatewayClient = new PaymentGatewayClient();
//        paymentGatewayClient.setRestTemplate(restTemplate);
//        paymentGatewayClient.setPaymentGatewayUrl(paymentGatewayUrl);
//    }
//
//    @Test
//    public void testChargeCreditCard_SuccessfulPayment() throws PaymentGatewayException {
//        // Arrange
//        PaymentGatewayRequest paymentRequest = createPaymentRequest();
//
//        HttpHeaders expectedHeaders = createExpectedHeaders();
//        PaymentResponse expectedResponse = createExpectedResponse();
//
//        ResponseEntity<PaymentResponse> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
//
//        when(restTemplate.exchange(eq(paymentGatewayUrl), eq(HttpMethod.POST), eq(any()), eq(PaymentResponse.class))).thenReturn(responseEntity);
//
//        // Act
//        PaymentResponse actualResponse = paymentGatewayClient.chargeCreditCard(paymentRequest);
//
//        // Assert
//        assertEquals(expectedResponse, actualResponse);
//    }
//
//    @Test
//    public void testChargeCreditCard_FailedPayment() {
//        // Arrange
//        PaymentGatewayRequest paymentRequest = createPaymentRequest();
//
//        HttpHeaders expectedHeaders = createExpectedHeaders();
//        PaymentResponse expectedResponse = createExpectedResponse();
//
//        ResponseEntity<PaymentResponse> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        when(restTemplate.exchange(eq(paymentGatewayUrl), eq(HttpMethod.POST), eq(any()), eq(PaymentResponse.class))).thenReturn(responseEntity);
//
//        // Act & Assert
//        assertThrows(PaymentGatewayException.class, () -> paymentGatewayClient.chargeCreditCard(paymentRequest));
//    }
//
//    @Test
//    public void testChargeCreditCard_NetworkError() {
//        // Arrange
//        PaymentGatewayRequest paymentRequest = createPaymentRequest();
//
//        HttpHeaders expectedHeaders = createExpectedHeaders();
//
//        when(restTemplate.exchange(eq(paymentGatewayUrl), eq(HttpMethod.POST), eq(any()), eq(PaymentResponse.class))).thenThrow(new RuntimeException("Network Error"));
//
//        // Set the value of paymentGatewayUrl to the mock URL
//        paymentGatewayClient.setPaymentGatewayUrl(paymentGatewayUrl);
//
//        // Act & Assert
//        assertThrows(PaymentGatewayException.class, () -> paymentGatewayClient.chargeCreditCard(paymentRequest));
//    }
//
//    private PaymentGatewayRequest createPaymentRequest() {
//        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
//        paymentRequest.setCardHolderName("Stuart Clark");
//        paymentRequest.setCardNumber("4111111111111111");
//        paymentRequest.setCardCvv("123");
//        paymentRequest.setAmount(new BigDecimal("10.0"));
//        paymentRequest.setCardExpiryMonth(12);
//        paymentRequest.setCardExpiryYear(2022);
//        paymentRequest.setCurrency("USD");
//        return paymentRequest;
//    }
//
//    private HttpHeaders createExpectedHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        return headers;
//    }
//
//    private PaymentResponse createExpectedResponse() {
//        PaymentResponse response = new PaymentResponse();
//        response.setStatus("Success");
//        response.setMessage("Payment Successful");
//        return response;
//    }
//}
