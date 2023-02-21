package com.shipogle.app.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.model.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenService {
    private static String secretKey = "2A462D4A614E645267556B58703273357638792F423F4528472B4B6250655368";
    public JwtToken createJwtToken(User user){
        JwtToken token = new JwtToken();

        String jwt_token = Jwts.builder()
                .claim("email",user.getEmail())
                .setSubject(user.getFirst_name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(60, ChronoUnit.MINUTES)))
                .signWith(generateKey())
                .compact();

        System.out.println("JWT Token is "+jwt_token);
        token.setToken(jwt_token);
        token.setIs_active(true);
        token.setUser(user);
        return token;
    }

    public Key generateKey(){
        return new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.getJcaName());
    }
}
