package com.shipogle.app.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shipogle.app.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRouteRepository extends JpaRepository<DriverRoute, Long> {
    @Query ("select d from DriverRoute d where (?1 is null or d.sourceCity = ?1)  and (?2 is null OR d.destinationCity = ?2) and (?3 is null OR d.pickupDate = ?3)and (?4 is null OR d.maxPackages > ?4)and (?5 is null OR d.radius > ?5)and (?6 is null OR d.price > ?6)")
    List<DriverRoute> getDriverRoutesByFilters(String sourceCity, String destination, String pickupDataTime, String maxPackages, String allowedCategory, String radius, String price, String category);
}