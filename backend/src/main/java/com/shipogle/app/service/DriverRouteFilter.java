package com.shipogle.app.service;

import java.util.*;

import com.shipogle.app.model.*;
import com.shipogle.app.repository.DriverRouteRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DriverRouteFilter {//implements DriverRouteRepository {
    @Autowired
    private final DriverRouteRepository driverRouteRepository;

    @Autowired
    UserRepository userRepository;

    public DriverRouteFilter(DriverRouteRepository driverRouteRepository){
        this.driverRouteRepository = driverRouteRepository;
    }

    public List<DriverRoute> filter(List<DriverRoute> driverRoutes) {
        return driverRoutes;
    }

    public List<DriverRoute> getDriverRoutesByFilters(DashboardFilter filter) {
        return driverRouteRepository.getDriverRoutesByFilters(filter.sourceCity, filter.destination, filter.pickupDataTime, filter.maxPackages
                ,filter.radius, filter.price, filter.allowedCategory,  filter.category);
    }

    public List<DriverRoute> getDriverRouteById(String driverId) {
        return driverRouteRepository.getDriverRoutes(driverId);
    }
}
