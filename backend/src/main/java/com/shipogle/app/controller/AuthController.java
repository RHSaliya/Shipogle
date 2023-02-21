package com.shipogle.app.controller;

import com.shipogle.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.shipogle.app.model.User;
import com.shipogle.app.model.JwtToken;
@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/register")
    public JwtToken registerNewUser(@RequestBody User user){

        return authService.register(user);
    }
}
