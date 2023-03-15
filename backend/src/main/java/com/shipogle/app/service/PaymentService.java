package com.shipogle.app.service;

import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentGatewayClient paymentGatewayClient;

    public PaymentResponse chargeCreditCard(PaymentGatewayRequest paymentRequest) throws PaymentGatewayException {
        try {
            // Validate the payment request
            validatePaymentRequest(paymentRequest);

            // Call the payment gateway API to charge the credit card
            PaymentResponse paymentGatewayResponse = paymentGatewayClient.chargeCreditCard(paymentRequest);

            // Map the payment gateway response to a PaymentResponse object
            PaymentResponse paymentResponse = mapPaymentGatewayResponse(paymentGatewayResponse);

            // Return the payment response
            return paymentResponse;
        } catch (PaymentGatewayException e) {
            throw new PaymentGatewayException("Error processing payment: " + e.getMessage());
        }
    }

    private void validatePaymentRequest(PaymentGatewayRequest paymentRequest) throws PaymentGatewayException {
        // Implement validation logic for the payment request, such as checking the amount and currency
        // Throw a PaymentException if the validation fails
    }

    private PaymentResponse mapPaymentGatewayResponse(PaymentResponse paymentGatewayResponse) {
        // Map the payment gateway response to a PaymentResponse object
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(paymentGatewayResponse.getStatus());
        paymentResponse.setMessage(paymentGatewayResponse.getMessage());
        // Set any other relevant information from the payment gateway response

        return paymentResponse;
    }
}

