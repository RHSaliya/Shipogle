package com.shipogle.app.model;

public class DashboardFilter {
    public String sourceCity;
    public String destination;
    public String pickupDataTime;
    public String maxPackages;
    public String allowedCategory;
    public String radius;
    public String price;
    public String category;
    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPickupDataTime() {
        return pickupDataTime;
    }

    public void setPickupDataTime(String pickupDataTime) {
        this.pickupDataTime = pickupDataTime;
    }

    public String getMaxPackages() {
        return maxPackages;
    }

    public void setMaxPackages(String maxPackages) {
        this.maxPackages = maxPackages;
    }

    public String getAllowedCategory() {
        return allowedCategory;
    }

    public void setAllowedCategory(String allowedCategory) {
        this.allowedCategory = allowedCategory;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
