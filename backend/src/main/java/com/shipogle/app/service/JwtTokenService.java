package com.shipogle.app.service;

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

@Service
public class JwtTokenService {

    @Autowired
    JwtTokenRepository jwtTokenRepo;

    // @Value("${jwt.secret.key}")
    public static String secretKey = "2A462D4A614E645267556B58703273357638792F423F4528472B4B6250655368";

    public JwtToken createJwtToken(User user) {
        JwtToken token = new JwtToken();

        String jwt_token = Jwts.builder()
                .claim("email", user.getEmail())
                .setSubject(user.getFirst_name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(60 * 24, ChronoUnit.MINUTES)))
                .signWith(generateKey())
                .compact();

        // System.out.println("JWT Token is "+jwt_token);
        token.setToken(jwt_token);
        token.setIs_active(true);
        token.setUser(user);
        return token;
    }

    public Key generateKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.getJcaName());
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

    // public boolean isJwtExpired(String token){
    //// Date expiration =
    // Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    //// if(expiration.before(new Date())){
    //// return true;
    //// }
    //// return false;
    // System.out.println(Jwts.claims().getExpiration());
    // return true;
    // }
}
