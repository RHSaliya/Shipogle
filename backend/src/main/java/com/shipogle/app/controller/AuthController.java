package com.shipogle.app.controller;

import com.shipogle.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import com.shipogle.app.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public String registerNewUser(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> json) {
        return authService.login(json.get("email"), json.get("password"));
    }

    @PostMapping("/changepassword")
    public String changePassword(@RequestBody Map<String, String> json) {
        return authService.resetPassword(json.get("token"), json.get("password"));
    }

    @PostMapping("/forgotpassword")
    public String forgotPassword(HttpServletRequest request, @RequestBody Map<String, String> json) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        return authService.forgotPassword(origin, json.get("email"));
    }

    @GetMapping("/verification")
    public String emailVerification(@RequestParam("code") String code, @RequestParam("id") int id) {
        return authService.verifyEmail(code, id);
    }

    @GetMapping("/test")
    public String test() {
        return "Test page ...";
    }

    @GetMapping("/user")
    public String getUser(@RequestParam("id") int id) {
        System.out.println("id = " + id);
        return authService.getUser(id).toString();
    }

    @GetMapping("/user_info")
    public User getUserInfo(@RequestHeader("Authorization") String token) {
        return authService.getUserInfo(token);
    }
}
