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
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    /**
     * @author Nandkumar Kadivar
     * Get logged in user
     * @return user who is logged in
     */
    @Override
    public User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getPrincipal().toString();

        User user = userRepo.getUserByEmail(user_email);

        return user;
    }

    /**
     * @author Nandkumar Kadivar
     * Update the user coordinates in database
     * @return string response
     */
    @Override
    public String updateUserLocation(String latitude, String longitude){
        User user = getLoggedInUser();

        user.setLocation(latitude,longitude);

        userRepo.save(user);

        return "Location updated";
    }

    /**
     * @author Nandkumar Kadivar
     * Get the user coordinates
     * @return map coordinatess
     */
    @Override
    public Map<String,String> getUserLocation(Integer id){
        User user = userRepo.getUserById(id);
        if(user != null){
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
