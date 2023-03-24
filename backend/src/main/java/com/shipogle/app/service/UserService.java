package com.shipogle.app.service;

import com.shipogle.app.model.User;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public String updateUserLocation(String latitude, String longitude){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getPrincipal().toString();

        User user = userRepo.getUserByEmail(user_email);

        user.setLatitude(latitude);
        user.setLongitude(longitude);

        userRepo.save(user);

        return "Location updated";
    }

    public Map<String,String> getUserLocation(Integer id){
        User user = userRepo.getUserById(id);
        if(!user.equals(null)){
            String latitude = user.getLatitude();
            String longitude = user.getLongitude();

            Map<String,String> coordinates = new HashMap<>();
            coordinates.put("latitude",latitude);
            coordinates.put("longitude",longitude);

            return coordinates;
        }
        return null;
    }
}
