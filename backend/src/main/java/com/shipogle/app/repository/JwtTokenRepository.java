package com.shipogle.app.repository;

import com.shipogle.app.model.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {
    
}
