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
        if(filter == null)
            return null;

        return driverRouteRepository.getDriverRoutesByFilters(filter.getSourceCity(), filter.getDestination(), filter.getPickupDataTime(), filter.getMaxPackages()
                ,filter.getRadius(), filter.getPrice(), filter.getAllowedCategory(),  filter.getCategory());
    }

    public List<DriverRoute> getDriverRouteById(String driverId) {
        return driverRouteRepository.getDriverRoutes(driverId);
    }

    public DriverRoute save(DriverRoute driverRoute) {
        return driverRouteRepository.save(driverRoute);
    }
}
