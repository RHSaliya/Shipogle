package com.shipogle.app.service;

import java.util.*;

import com.shipogle.app.model.*;
import com.shipogle.app.repository.DriverRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverRouteFilter {//implements DriverRouteRepository {
    @Autowired
    private final DriverRouteRepository driverRouteRepository;

    public DriverRouteFilter(DriverRouteRepository driverRouteRepository){
        this.driverRouteRepository = driverRouteRepository;
    }

    public List<DriverRoute> filter(List<DriverRoute> driverRoutes) {
        return driverRoutes;
    }

    public List<DriverRoute> getDriverRoutesByFilters(DashboardFilter filter) {
         return driverRouteRepository.getDriverRoutesByFilters(filter.sourceCity, filter.allowedCategory, filter.maxPackages);
    }

}
