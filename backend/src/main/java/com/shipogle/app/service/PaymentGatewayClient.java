package com.shipogle.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentGatewayClient {

    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;

    public PaymentResponse chargeCreditCard(PaymentGatewayRequest paymentRequest) throws PaymentGatewayException {
        try {
            PaymentGatewayRequest paymentGatewayRequest = createPaymentGatewayRequest(paymentRequest);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(paymentRequest);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);

            ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(paymentGatewayUrl, HttpMethod.POST, requestEntity, PaymentResponse.class);

            PaymentResponse responseBody = responseEntity.getBody();
            return responseBody;
        } catch (Exception e) {
            throw new PaymentGatewayException("Error processing payment: " + e.getMessage());
        }
    }

    private PaymentGatewayRequest createPaymentGatewayRequest(PaymentGatewayRequest paymentRequest) {
        PaymentGatewayRequest paymentGatewayRequest = new PaymentGatewayRequest();
        paymentGatewayRequest.setAmount(paymentRequest.getAmount());
        paymentGatewayRequest.setCurrency(paymentRequest.getCurrency());
        paymentGatewayRequest.setCardNumber(paymentRequest.getCardNumber());
        paymentGatewayRequest.setCardExpiryMonth(paymentRequest.getCardExpiryMonth());
        paymentGatewayRequest.setCardExpiryYear(paymentRequest.getCardExpiryYear());
        paymentGatewayRequest.setCardCvv(paymentRequest.getCardCvv());

        return paymentGatewayRequest;
    }
}


