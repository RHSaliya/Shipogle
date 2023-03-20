package com.shipogle.app.service;

import com.shipogle.app.model.ForgotPasswordToken;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.ForgotPasswordTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
@Service
public class ForgotPasswordTokenService {

    @Autowired
    ForgotPasswordTokenRepository forgotPasswordTokenRepo;

    private String secretKey = "2A462D4A614E645267556B58703273357638792F423F4528472B4B6250655368";

    public ForgotPasswordToken createForgotPasswordToken(User user){
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        String forgot_password_token = Jwts.builder()
                .claim("email",user.getEmail())
                .setSubject(user.getFirst_name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(60*24, ChronoUnit.MINUTES)))
                .signWith(generateKey())
                .compact();

        forgotPasswordToken.setForgot_password_token(forgot_password_token);
        forgotPasswordToken.setIs_active(true);
        forgotPasswordToken.setUser(user);
        forgotPasswordTokenRepo.save(forgotPasswordToken);

        return forgotPasswordToken;
    }

    public Key generateKey(){
        return new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.getJcaName());
    }
}
