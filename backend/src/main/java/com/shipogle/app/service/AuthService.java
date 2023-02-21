package com.shipogle.app.service;

import com.shipogle.app.model.User;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.repository.JwtTokenRepository;
import com.shipogle.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userReop;
    @Autowired
    JwtTokenRepository jwtTokenRepo;
    @Autowired
    JwtTokenService jwtTokenService;
    public JwtToken register(User new_user){
        userReop.save(new_user);
        JwtToken token = jwtTokenService.createJwtToken(new_user);
        jwtTokenRepo.save(token);
        return token;
    }

    public JwtToken login(String email, String password){
        User storedUser = userReop.getUserByEmail(email);
        System.out.println(storedUser.getUser_id());
        JwtToken token = jwtTokenService.createJwtToken(storedUser);
        jwtTokenService.deactiveUserTokens(storedUser);
        jwtTokenRepo.save(token);
        return token;
    }
}
