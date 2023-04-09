package com.shipogle.app.repository;

import com.shipogle.app.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {
    Rating getRatingById(Integer rating_id);
    List<Rating> getAllByDriverRoute_DriverId(String driver_id);

    List<Rating> getAllByUser_Id(Integer sender_id);
}
