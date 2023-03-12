package com.shipogle.app.controller;

import com.shipogle.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

//    @PostMapping("/logout")
//    public String logout(@RequestBody String token){
//        return authService.logout(token);
//    }

    @PostMapping("/changepassword")
    public String changePassword(@RequestBody Map<String, String> json){
        return authService.resetPassword(json.get("email"),json.get("password"));
    }

    @GetMapping("/verification")
    public String emailVerification(@RequestParam("code") String code,@RequestParam("id") int id){
        return authService.verifyEmail(code,id);
    }

    @GetMapping("/test")
    public String test(){
        return "Test page ...";
    }

    @GetMapping("/user")
    public String getUser(@RequestParam("id") int id)
    {
        System.out.println("id = " + id);
        return authService.getUser(id).toString();
    }
}