package com.shipogle.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shipogle.app.model.*;
import com.shipogle.app.repository.*;
import com.shipogle.app.service.DriverRouteFilter;

@RestController
@RequestMapping("/driver-routes")
public class DriverRouteController {
    @Autowired
    private DriverRouteRepository driverRouteRepository;

    @PostMapping
    public ResponseEntity<DriverRoute> createDriverRoute(@RequestBody DriverRoute driverRoute) {
        DriverRoute savedDriverRoute = driverRouteRepository.save(driverRoute);
        return new ResponseEntity<>(savedDriverRoute, HttpStatus.CREATED);
    }
    
    @GetMapping
    public List<DriverRoute> getDriverRoutes(
            @RequestParam(name = "sourceCity") String sourceCity,
            @RequestParam(name = "destinationCity") String destinationCity,
            @RequestParam(name = "pickupDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate,
            @RequestParam(name = "category") String category
    ) {
        List<DriverRoute> driverRoutes = driverRouteRepository.getDriversOnRoute(
                sourceCity,
                destinationCity,
                pickupDate,
                category
        );
        
        driverRoutes = new DriverRouteFilter().filter(driverRoutes);
        
        return driverRoutes;
    }
}