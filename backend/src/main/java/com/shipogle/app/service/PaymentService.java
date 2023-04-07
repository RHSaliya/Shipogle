package com.shipogle.app.service;

import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGatewayClient paymentGatewayClient;

    @Autowired
    public PaymentService(PaymentGatewayClient paymentGatewayClient) {
        this.paymentGatewayClient = paymentGatewayClient;
    }

    public PaymentResponse chargeCreditCard(PaymentGatewayRequest paymentRequest) throws PaymentGatewayException {
        try {
            if(paymentRequest == null || !validatePaymentRequest(paymentRequest))
                throw new PaymentGatewayException("Unable to fetch card details.");

            PaymentResponse paymentGatewayResponse = paymentGatewayClient.chargeCreditCard(paymentRequest);

            return mapPaymentGatewayResponse(paymentGatewayResponse);
        } catch (PaymentGatewayException e) {
            throw new PaymentGatewayException("Error processing payment: " + e.getMessage());
        }
    }

    private Boolean validatePaymentRequest(PaymentGatewayRequest paymentRequest) {
        return !paymentRequest.hasEmptyFields();
    }

    private PaymentResponse mapPaymentGatewayResponse(PaymentResponse paymentGatewayResponse) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(paymentGatewayResponse.getStatus());
        paymentResponse.setMessage(paymentGatewayResponse.getMessage());
        paymentResponse.setTransactionId(paymentGatewayResponse.getTransactionId());

        return paymentResponse;
    }
}

