package com.shipogle.app.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shipogle.app.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRouteRepository extends JpaRepository<DriverRoute, Long> {
    @Query ("select d from DriverRoute d where (?1 is null or d.sourceCity like %?1%)  and (?2 is null OR d.allowedCategory = ?2) and (?3 is null OR d.maxPackages = ?3)")
    List<DriverRoute> getDriverRoutesByFilters(String sourceCity, String allowedCategory, Integer maxPackages);
}
