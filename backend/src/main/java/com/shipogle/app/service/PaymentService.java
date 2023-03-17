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

    @Autowired
    private TransactionService transactionService;

    public PaymentResponse chargeCreditCard(PaymentGatewayRequest paymentRequest) throws PaymentGatewayException {
        try {
            if(!validatePaymentRequest(paymentRequest))
                return null;

            PaymentResponse paymentGatewayResponse = paymentGatewayClient.chargeCreditCard(paymentRequest);
            transactionService.saveTransaction();
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

