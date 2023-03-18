package com.shipogle.app.repository;

import com.shipogle.app.model.Notification;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository  extends JpaRepository<Notification, Long> {


    List<Notification> findByUserOrderByCreatedAt(@Param("user") User user);

}
