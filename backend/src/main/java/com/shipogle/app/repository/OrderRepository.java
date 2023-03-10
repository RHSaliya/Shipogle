package com.shipogle.app.repository;

import com.shipogle.app.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Integer> {
//    @Override
//    List<Order> findAll();
}

