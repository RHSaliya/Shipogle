package com.shipogle.app.model;

import java.math.BigDecimal;

public class PaymentGatewayRequest {

    private BigDecimal amount;
    private String currency;
    private String cardNumber;
    private int cardExpiryMonth;
    private int cardExpiryYear;
    private String cardCvv;
    private String cardHolderName;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(int cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public int getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(int cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public String getCardHolderName(){return cardHolderName; };

    public void setCardHolderName(String cardHolderName){
        this.cardHolderName = cardHolderName;
    }
}
