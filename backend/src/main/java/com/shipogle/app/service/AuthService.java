package com.shipogle.app.service;

import com.shipogle.app.model.User;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.repository.JwtTokenRepository;
import com.shipogle.app.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public boolean isAlreadyExist(User user) {
        User db_user = userReop.findUserByEmail(user.getEmail());
        if (db_user == null) {
            return false;
        }
        return true;
    }

    public String resetPassword(String email, String password) {
        try {
            User user = userReop.getUserByEmail(email);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String new_password = encoder.encode(password);
            user.setPassword(new_password);
            userReop.save(user);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Password changed successfully";
    }

    // public String logout(String token){
    // System.out.println("Flag logout service");
    // String email = Jwts.parser().parseClaimsJws(token).getBody().getAudience();
    // System.out.println(email);
    // return "";
    // }

    public String verifyEmail(String code, int id) {

        try {
            User user = userReop.getById(id);

            if (!user.getIs_verified()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (encoder.matches(user.getEmail(), code)) {
                    user.setIs_verified(true);
                    userReop.save(user);
                    return "Email Verified";
                } else {
                    return "Not valid user";
                }
            } else {
                return "Already Verified";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String register(User new_user) {
        if (!isAlreadyExist(new_user)) {
            String user_password = new_user.getPassword();
            System.out.println(new_user.getPassword());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            new_user.setPassword(encoder.encode(user_password));
            new_user.setIs_verified(false);
            userReop.save(new_user);

            String encoded_email = encoder.encode(new_user.getEmail());
            mailService.sendMail(new_user.getEmail(),
                    "http://localhost:8080/verification?code=" + encoded_email + "&id=" + new_user.getUser_id());

            return "Verification email sent";

        } else {
            return "User Already exist with this email";
        }
    }

    public String login(String email, String password) {

        UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(auth_token);
        try {
            authManager.authenticate(auth_token);
        } catch (Exception e) {
            return e.getMessage();
        }

        User storedUser = userReop.getUserByEmail(email);

        if (storedUser.getIs_verified()) {
            JwtToken token = jwtTokenService.createJwtToken(storedUser);
            jwtTokenService.deactiveUserTokens(storedUser);
            jwtTokenRepo.save(token);
            return token.getToken();
        } else {
            return "User is not verified";
        }
    }

    public User getUser(int id) {
        return userReop.getReferenceById(id);
    }

    public User getUserInfo(String token) {
        token = token.replace("Bearer", "").trim();
        Claims claim = Jwts.parser().setSigningKey(JwtTokenService.secretKey).parseClaimsJws(token).getBody();
        String email = (String) claim.get("email");
        return userReop.getUserByEmail(email);
    }
}
