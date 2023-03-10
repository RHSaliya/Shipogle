package com.shipogle.app.service;

import com.shipogle.app.model.Order;
import com.shipogle.app.repository.DriverRouteRepository;
import com.shipogle.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersCreatedAfter(LocalDate createDate) {
        //return orderRepository.findAllByOrderDateAfter(createDate);
        return null;
    }
}

