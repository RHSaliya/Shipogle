package com.shipogle.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.DriverRoute;
import com.shipogle.app.model.PaymentGatewayRequest;
import com.shipogle.app.model.PaymentResponse;
import com.shipogle.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<?> chargeCreditCard(@RequestBody String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentGatewayRequest paymentRequest = objectMapper.readValue(jsonString, PaymentGatewayRequest.class);

            PaymentResponse paymentResponse = paymentService.chargeCreditCard(paymentRequest);
            return ResponseEntity.ok(paymentResponse);
        } catch (PaymentGatewayException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


