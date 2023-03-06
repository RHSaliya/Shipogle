package com.shipogle.app.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shipogle.app.model.*;

public interface DriverRouteRepository extends JpaRepository<DriverRoute, Long> {

	List<DriverRoute> getDriversOnRoute(String sourceCity,
			String destinationCity, LocalDate pickupDate, String allowedCategory);

}
