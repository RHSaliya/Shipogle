package com.shipogle.app.model;

import java.util.Date;

public class Transaction {
    private Date date;
    private String trackingNumber;
    private String sender;
    private String recipient;
    private String deliveryAddress;
    private String courierService;
    private String serviceType;
    private Date deliveryDate;
    private Date deliveryTime;
    private String packageDescription;
    private double deliveryFee;

    public Transaction(Date date, String trackingNumber, String sender, String recipient, String deliveryAddress,
                       String courierService, String serviceType, Date deliveryDate, Date deliveryTime,
                       String packageDescription, double deliveryFee) {
        this.date = date;
        this.trackingNumber = trackingNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.deliveryAddress = deliveryAddress;
        this.courierService = courierService;
        this.serviceType = serviceType;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.packageDescription = packageDescription;
        this.deliveryFee = deliveryFee;
    }

    // Getter and setter methods

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCourierService() {
        return courierService;
    }

    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}

