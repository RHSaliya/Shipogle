package com.shipogle.app.service;

import com.shipogle.app.model.User;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.repository.JwtTokenRepository;
import com.shipogle.app.repository.UserRepository;
import com.shipogle.app.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userReop;
    @Autowired
    JwtTokenRepository jwtTokenRepo;
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    MailService mailService;

    public boolean isAlreadyExist(User user){
        User db_user = userReop.findUserByEmail(user.getEmail());
        if(db_user == null){
            return false;
        }
        return true;
    }

    public String register(User new_user){
        if(!isAlreadyExist(new_user)){
            String user_password = new_user.getPassword();
            System.out.println(new_user.getPassword());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            new_user.setPassword(encoder.encode(user_password));
            System.out.println(new_user.getPassword());
            System.out.println(encoder.encode("abc"));
            userReop.save(new_user);
            JwtToken token = jwtTokenService.createJwtToken(new_user);
            jwtTokenRepo.save(token);
            return token.getToken();
        }else {
            return "User Already exist with this email";
        }
    }

    public String login(String email, String password){

        UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(email,password);
        System.out.println(auth_token);
        try{
            authManager.authenticate(auth_token);
        }catch (Exception e){
            return e.getMessage();
        }

        User storedUser = userReop.getUserByEmail(email);

        JwtToken token = jwtTokenService.createJwtToken(storedUser);
        jwtTokenService.deactiveUserTokens(storedUser);
        jwtTokenRepo.save(token);
        return token.getToken();
    }
}
