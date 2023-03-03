package com.shipogle.app.controller;

import com.shipogle.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.shipogle.app.model.User;
import com.shipogle.app.model.JwtToken;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/register")
    public String registerNewUser(@RequestBody User user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> json){
        return authService.login(json.get("email"),json.get("password"));
    }

    @GetMapping("/test")
    public String temp(){
        return "Test page...";
    }
}
