package com.shipogle.app.controller;

import com.shipogle.app.config.WebSocketConfig;
import com.shipogle.app.model.Notification;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.NotificationRepository;
import com.shipogle.app.repository.UserRepository;
import com.shipogle.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    @Autowired
    private WebSocketConfig webSocketConfig;

    @PostMapping
    public ResponseEntity<?> sendNotification(@RequestBody Map<String, String> json) {
        System.out.println("json = " + json);
        try {
            String userId = json.get("userId");
            String title = json.getOrDefault("title", "Notification");
            String message = json.getOrDefault("message", "");
            String payload = json.getOrDefault("payload", "{}");
            String type = json.getOrDefault("type", "default");

            Optional<User> userOptional = userRepository.findById(Integer.parseInt(userId));
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid sender or receiver ID");
            }

            User user = userOptional.get();

            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setPayload(payload);
            notification.setType(type);
            notification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(notification);
            System.out.println("notification = " + notification);
            webSocketConfig.notificationHandler().sendNotification(notification);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
    }

    @PostMapping("/get")
    public List<Notification> getNotificationsByToken(@RequestHeader("Authorization") String token) {
        User user = new AuthService().getUserInfo(token);
        if (user == null) {
            throw new RuntimeException("Invalid user ID");
        }

        return new ArrayList<>(notificationRepository.findByUserOrderByCreatedAt(user));
    }
    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid user ID");
        }

        User user = userOptional.get();

        return new ArrayList<>(notificationRepository.findByUserOrderByCreatedAt(user));
    }


    @DeleteMapping("/all/{userId}")
    public ResponseEntity<?> deleteNotifications(@PathVariable int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid user ID");
        }

        User user = userOptional.get();

        notificationRepository.deleteNotificationsByUser(user);

        return ResponseEntity.ok("Deleted notifications for user with id: " + userId);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isEmpty()) {
            throw new RuntimeException("Invalid notification ID");
        }

        Notification notification = notificationOptional.get();

        notificationRepository.delete(notification);

        return ResponseEntity.ok("Deleted notification with id: " + notificationId);
    }

}
