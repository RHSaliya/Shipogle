package com.shipogle.app.controller;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shipogle.app.model.*;
import com.shipogle.app.repository.*;
import com.shipogle.app.service.DriverRouteFilter;

@RestController
public class DriverRouteController {
    private DriverRouteFilter driverRouteFilter;

    @Autowired
    public void setDriverRouteFilter(DriverRouteFilter driverRouteFilter) {
        this.driverRouteFilter = driverRouteFilter;
    }

    @PostMapping("/driverRoutes")
    public ResponseEntity<?> createDriverRoute(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DriverRoute driverRoute = objectMapper.readValue(jsonString, DriverRoute.class);

        DriverRoute savedDriverRoute = driverRouteFilter.save(driverRoute);
        return new ResponseEntity<>("Driver Details saved : \n" +  savedDriverRoute, HttpStatus.CREATED);
    }

    @GetMapping("/driverRoutes")
    public List<DriverRoute> getDriverRoutesByFilters(
            @RequestParam(required = false) String sourceCity,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String pickupDataTime,
            @RequestParam(required = false) String maxPackages,
            @RequestParam(required = false) String allowedCategory,
            @RequestParam(required = false) String radius,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String category
    ) {
        DashboardFilter dashboardFilters = new DashboardFilter();
        dashboardFilters.setSourceCity(sourceCity);
        dashboardFilters.setDestination(destination);
        dashboardFilters.setPickupDataTime(pickupDataTime);
        dashboardFilters.setMaxPackages(maxPackages);
        dashboardFilters.setAllowedCategory(allowedCategory);
        dashboardFilters.setRadius(radius);
        dashboardFilters.setPrice(price);
        dashboardFilters.setCategory(category);

        return driverRouteFilter.getDriverRoutesByFilters(dashboardFilters);
    }

    @GetMapping("/driverRoutesByDriverId")
    public List<DriverRoute> getDriverRoutes(@RequestParam(required = true) String driverId) {
        return driverRouteFilter.getDriverRouteById(driverId);
    }
}