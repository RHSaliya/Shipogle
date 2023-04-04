package com.shipogle.app.service;

import com.shipogle.app.model.ForgotPasswordToken;
import com.shipogle.app.model.User;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.repository.ForgotPasswordTokenRepository;
import com.shipogle.app.repository.JwtTokenRepository;
import com.shipogle.app.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.shipogle.app.utility.Const.*;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    JwtTokenRepository jwtTokenRepo;
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    MailService mailService;
    @Autowired
    ForgotPasswordTokenRepository forgotPasswordTokenRepo;
    @Autowired
    ForgotPasswordTokenService forgotPasswordTokenService;

    public boolean isAlreadyExist(User user) {
        User db_user = userRepo.findUserByEmail(user.getEmail());
        if (db_user == null) {
            return false;
        }
        return true;
    }

    public String resetPassword(String token, String password) {
        try {
            ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepo.findByForgetPasswordToken(token);
            if (forgotPasswordToken.getIs_active()) {
                Claims claim = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
                String email = (String) claim.get("email");

                User user = userRepo.getUserByEmail(email);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String new_password = encoder.encode(password);
                user.setPassword(new_password);
                userRepo.save(user);
                forgotPasswordToken.setIs_active(false);
                forgotPasswordTokenRepo.save(forgotPasswordToken);

                return "Password changed successfully";
            } else {
//                return "Link is not active";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Link is not active");
            }
        } catch (Exception e) {
//            return e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String forgotPassword(String email) {
        try {
            User user = userRepo.getUserByEmail(email);

            // String forgot_password_token =
            // forgotPasswordTokenService.createForgotPasswordToken(user).getForgot_password_token();
            ForgotPasswordToken token = forgotPasswordTokenService.createForgotPasswordToken(user);
            String forgot_password_token = token.getForgot_password_token();

            String user_email = user.getEmail();
            String subject = "Reset Password";
            String body = "Password rest link(Expires in 24 hours): ";
            String reset_link =  URL_FRONTEND+"/forgotpwd/reset/"+forgot_password_token;
            mailService.sendMail(user_email, subject, body, reset_link);

        } catch (Exception e) {
//            return e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Password reset link sent";
    }

    public String verifyEmail(String code, int id) {

        try {
            User user = userRepo.getById(id);

            if (!user.getIs_verified()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (encoder.matches(user.getEmail(), code)) {
                    user.setIs_verified(true);
                    userRepo.save(user);
                    return "Email Verified";
                } else {
//                    return "Not valid user";
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not valid user");
                }
            } else {
//                return "Already Verified";
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not valid user");
            }
        } catch (Exception e) {
//            return e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String register(User new_user) {
        if (!isAlreadyExist(new_user)) {
            String user_password = new_user.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            new_user.setPassword(encoder.encode(user_password));
            new_user.setIs_verified(false);
            userRepo.save(new_user);

            String encoded_email = encoder.encode(new_user.getEmail());

            String user_email = new_user.getEmail();
            String subject = "Email Verification";
            String body = "Please verify your email:";
            String link =  URL_BACKEND+"/verification?code="+encoded_email+"&id="+new_user.getUser_id();

            mailService.sendMail(user_email,subject, body, link);

            return "Verification email sent";

        } else {
//            return "User Already exist with this email";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already exist with this email");
        }
    }

    public String login(String email, String password) {

        UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(auth_token);
        try {
            authManager.authenticate(auth_token);
        } catch (Exception e) {
//            return e.getMessage();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        User storedUser = userRepo.getUserByEmail(email);

        if (storedUser.getIs_verified()) {
            JwtToken token = jwtTokenService.createJwtToken(storedUser);
            jwtTokenService.deactiveUserTokens(storedUser);
            jwtTokenRepo.save(token);
            return token.getToken();
        } else {
//            return "User is not verified";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not verified");
        }
    }

    public User getUser(int id) {
        return userRepo.getReferenceById(id);
    }

    public User getUserInfo(String token) {
        token = token.replace("Bearer", "").trim();
        Claims claim = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        String email = (String) claim.get("email");
        return userRepo.getUserByEmail(email);
    }

    public String updateUser(String token, User user) {
        token = token.replace("Bearer", "").trim();
        Claims claim = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        String email = (String) claim.get("email");
        User db_user = userRepo.getUserByEmail(email);
        db_user.update(user);
        userRepo.save(db_user);
        return "User updated";
    }
}
