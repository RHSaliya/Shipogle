package com.shipogle.app.service;

import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentGatewayClient);
    }

    @Test
    void testChargeCreditCard_Success() throws PaymentGatewayException {
        // Arrange
        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
        paymentRequest.setCardHolderName("Stuart Clark");
        paymentRequest.setCardNumber("4111111111221111");
        paymentRequest.setCardCvv("457");
        paymentRequest.setCurrency("100.0");
        paymentRequest.setAmount(new BigDecimal(1223));
        paymentRequest.setCardExpiryMonth(10);
        paymentRequest.setCardExpiryYear(2028);
        PaymentResponse paymentGatewayResponse = new PaymentResponse();
        paymentGatewayResponse.setStatus("success");
        paymentGatewayResponse.setMessage("Payment processed successfully");
        paymentGatewayResponse.setTransactionId("123456789");

        when(paymentGatewayClient.chargeCreditCard(paymentRequest)).thenReturn(paymentGatewayResponse);

        // Act
        PaymentResponse paymentResponse = paymentService.chargeCreditCard(paymentRequest);

        // Assert
        assertNotNull(paymentResponse);
        assertEquals("success", paymentResponse.getStatus());
        assertEquals("Payment processed successfully", paymentResponse.getMessage());
        assertEquals("123456789", paymentResponse.getTransactionId());
    }

    @Test
    void testChargeCreditCard_NullRequest() throws PaymentGatewayException {
        // Arrange
        PaymentGatewayRequest paymentRequest = null;

        // Act & Assert
        assertThrows(PaymentGatewayException.class, () -> paymentService.chargeCreditCard(paymentRequest));
    }

    @Test
    void testChargeCreditCard_EmptyRequestFields() throws PaymentGatewayException {
        // Arrange
        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();

        // Act & Assert
        assertThrows(PaymentGatewayException.class, () -> paymentService.chargeCreditCard(paymentRequest));
    }

    @Test
    void testChargeCreditCard_FailedPaymentWithInvalidCardDetails() throws PaymentGatewayException {
        // Arrange
        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
        paymentRequest.setCardHolderName("John Doe");
        paymentRequest.setCardNumber("4111111111111111");
        paymentRequest.setCardCvv("123");
        paymentRequest.setAmount(new BigDecimal("10.0"));
        PaymentResponse paymentGatewayResponse = new PaymentResponse();
        paymentGatewayResponse.setStatus("failure");
        paymentGatewayResponse.setMessage("Payment failed");

        when(paymentGatewayClient.chargeCreditCard(paymentRequest)).thenReturn(paymentGatewayResponse);

        // Act & Assert
        assertThrows(PaymentGatewayException.class, () -> paymentService.chargeCreditCard(paymentRequest));
    }

    @Test
    void testChargeCreditCard_ExceptionThrownByPaymentGatewayClient() throws PaymentGatewayException {
        // Arrange
        PaymentGatewayRequest paymentRequest = new PaymentGatewayRequest();
        paymentRequest.setCardHolderName("Stuart Clark");
        paymentRequest.setCardNumber("4111111111221111");
        paymentRequest.setCardCvv("457");
        paymentRequest.setCurrency("100.0");
        paymentRequest.setAmount(new BigDecimal(1223));
        paymentRequest.setCardExpiryMonth(10);
        paymentRequest.setCardExpiryYear(2028);

        when(paymentGatewayClient.chargeCreditCard(paymentRequest)).thenThrow(new PaymentGatewayException("Something went wrong"));

        // Act & Assert
        assertThrows(PaymentGatewayException.class, () -> {
            PaymentResponse paymentResponse = paymentService.chargeCreditCard(paymentRequest);
        });
    }
}