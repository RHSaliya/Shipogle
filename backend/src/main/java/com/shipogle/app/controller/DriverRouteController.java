package com.shipogle.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public ResponseEntity<String> createDriverRoute(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DriverRoute driverRoute = objectMapper.readValue(jsonString, DriverRoute.class);

        DriverRoute savedDriverRoute = driverRouteRepository.save(driverRoute);
        return new ResponseEntity<>("Driver Details saved", HttpStatus.CREATED);
    }



    @GetMapping("/driverRoutes")
    public List<DriverRoute> getDriverRoutesByFilters(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DashboardFilter dashboardFilters = objectMapper.readValue(jsonString, DashboardFilter.class);

        return driverRouteFilter.getDriverRoutesByFilters(dashboardFilters);
    }
}