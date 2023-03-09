package com.shipogle.app.repository;

import com.shipogle.app.model.JwtToken;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {
    List<JwtToken> getAllByUser(User user);

    JwtToken getJwtTokensByToken(String token);
}
