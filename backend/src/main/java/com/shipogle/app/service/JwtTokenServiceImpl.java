package com.shipogle.app.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.JwtTokenRepository;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.shipogle.app.utility.Const.SECRETKEY;
import static com.shipogle.app.utility.Const.TOKEN_EXPIRATION_TIME;

@Service
public class JwtTokenServiceImpl implements JwtTokenService{

    @Autowired
    JwtTokenRepository jwtTokenRepo;

    public JwtToken createJwtToken(User user) {
        JwtToken token = new JwtToken();

        JwtBuilder jwtBuilder = Jwts.builder().claim("email", user.getEmail());
        jwtBuilder.setSubject(user.getFirst_name());
        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtBuilder.setExpiration(Date.from(Instant.now().plus(TOKEN_EXPIRATION_TIME, ChronoUnit.MINUTES)));
        jwtBuilder.signWith(generateKey());
        String jwt_token = jwtBuilder.compact();

        // System.out.println("JWT Token is "+jwt_token);
        token.setToken(jwt_token);
        token.setIs_active(true);
        token.setUser(user);
        return token;
    }

    public Key generateKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(SECRETKEY), SignatureAlgorithm.HS256.getJcaName());
    }

    public void deactiveUserTokens(User user) {
        List<JwtToken> activeTokens = jwtTokenRepo.getAllByUser(user);
        for (JwtToken t : activeTokens) {
            t.setIs_active(false);
        }
        jwtTokenRepo.saveAll(activeTokens);
    }

    public boolean isJwtActive(String token) {
        JwtToken jwt_token = jwtTokenRepo.getJwtTokensByToken(token);

        return jwt_token.getIs_active();
    }
}
