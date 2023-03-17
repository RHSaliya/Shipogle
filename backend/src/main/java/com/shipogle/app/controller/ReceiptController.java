package com.shipogle.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.exception.PaymentGatewayException;
import com.shipogle.app.model.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transaction;

@RestController
public class ReceiptController{
    @GetMapping("/getReceipt")
    public ResponseEntity<?> chargeCreditCard(@RequestBody String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Transaction transactionDetails = objectMapper.readValue(jsonString, Transaction.class);


            return ResponseEntity.ok(transactionDetails);
        } catch (PaymentGatewayException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
