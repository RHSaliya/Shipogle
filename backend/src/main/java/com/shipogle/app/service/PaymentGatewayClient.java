package com.shipogle.app.service;

import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentGatewayClient {

    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;

    @Value("${payment.gateway.apikey}")
    private String paymentGatewayApiKey;

    public PaymentResponse chargeCreditCard(PaymentGatewayRequest paymentRequest) throws PaymentGatewayException {
        try {
            // Create the payment gateway request object from the payment request
            PaymentGatewayRequest paymentGatewayRequest = createPaymentGatewayRequest(paymentRequest);

            // Call the payment gateway API to charge the credit card
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + paymentGatewayApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentGatewayRequest> requestEntity = new HttpEntity<>(paymentGatewayRequest, headers);
            ResponseEntity<PaymentResponse> responseEntity = restTemplate.postForEntity(paymentGatewayUrl, requestEntity, PaymentResponse.class);

            // Return the payment gateway response
            PaymentResponse paymentGatewayResponse = responseEntity.getBody();
            return paymentGatewayResponse;
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
        // Set any other relevant information for the payment gateway request

        return paymentGatewayRequest;
    }
}


