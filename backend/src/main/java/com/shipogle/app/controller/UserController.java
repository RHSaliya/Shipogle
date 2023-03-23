package com.shipogle.app.controller;

import com.shipogle.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("user/location/put")
    public String updateUserLocation(@RequestBody Map<String,String> req){
        return userService.updateUserLocation(req.get("latitude"),req.get("longitude"));
    }

    @GetMapping("user/location/get")
    public Map<String,String> getUserLocation(@RequestBody Map<String,String> req){
        return userService.getUserLocation(Integer.valueOf(req.get("user_id")));
    }
}
