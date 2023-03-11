package com.shipogle.app.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "driver_routes")
public class DriverRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_city")
    private String sourceCity;

    @Column(name = "destinations")
    //private List<String> destinations;
    private String destinations;

    @Column(name = "max_packages")
    private int maxPackages;

    @Column(name = "pickup_date")
    private Date pickupDate;

    @Column(name = "days_to_deliver")
    private int daysToDeliver;

    @ElementCollection
    @CollectionTable(name = "location_coords")
    private List<Double> locationCoords;

    @Column(name = "allowed_category")
    private String allowedCategory;

    @Column(name = "radius")
    private Integer radius;

    @Column(name = "price")
    private Integer price;

    public DriverRoute() {
        // default constructor for JPA
    }

    public DriverRoute(String sourceCity, String destinations, int maxPackages, Date pickupDate
                , int daysToDeliver, List<Double> locationCoords, String allowedCategory, Integer radius, Integer price) {
        this.sourceCity = sourceCity;
        this.destinations = destinations;
        this.maxPackages = maxPackages;
        this.pickupDate = pickupDate;
        this.daysToDeliver = daysToDeliver;
        this.locationCoords = locationCoords;
        this.allowedCategory = allowedCategory;
        this.radius = radius;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestinations() {
        return destinations;
    }

    //public void setDestinations(List<String> destinations) {
        //this.destinations = destinations;
    //}

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    public int getMaxPackages() {
        return maxPackages;
    }

    public void setMaxPackages(int maxPackages) {
        this.maxPackages = maxPackages;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getDaysToDeliver() {
        return daysToDeliver;
    }

    public void setDaysToDeliver(int daysToDeliver) {
        this.daysToDeliver = daysToDeliver;
    }

    public List<Double> getLocationCoords() {
        return locationCoords;
    }

    public void setLocationCoords(List<Double> locationCoords) {
        this.locationCoords = locationCoords;
    }

    public String getAllowedCategory() {
        return allowedCategory;
    }

    public void setAllowedCategory(String allowedCategory) {
        this.allowedCategory = allowedCategory;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
