package com.shipogle.app.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "packageId")
    private String packageId;
    @Column(name = "senderId")
    private String senderId;
    @Column(name = "driverId")
    private String driverId;
    @Column(name = "pickupCode")
    private String pickupCode;
    @Column(name = "createDate")
    private LocalDate createDate;

    // Default constructor
    public Order() {}

    // Parameterized constructor
    public Order(String packageId, String senderId, String driverId, String pickupCode, LocalDate createDate) {
        this.packageId = packageId;
        this.senderId = senderId;
        this.driverId = driverId;
        this.pickupCode = pickupCode;
        this.createDate = createDate;
    }

    // Setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}


