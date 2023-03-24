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
    @Autowired
    private DriverRouteRepository driverRouteRepository;
    @Autowired
    private DriverRouteFilter driverRouteFilter;

    @PostMapping("/driverRoutes")
    public ResponseEntity<?> createDriverRoute(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DriverRoute driverRoute = objectMapper.readValue(jsonString, DriverRoute.class);

        DriverRoute savedDriverRoute = driverRouteRepository.save(driverRoute);
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
        dashboardFilters.sourceCity = sourceCity;
        dashboardFilters.destination = destination;
        dashboardFilters.pickupDataTime = pickupDataTime;
        dashboardFilters.maxPackages = maxPackages;
        dashboardFilters.allowedCategory = allowedCategory;
        dashboardFilters.radius = radius;
        dashboardFilters.price = price;
        dashboardFilters.category = category;

        return driverRouteFilter.getDriverRoutesByFilters(dashboardFilters);
    }
}