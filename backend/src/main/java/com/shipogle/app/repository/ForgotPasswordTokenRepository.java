package com.shipogle.app.repository;

import com.shipogle.app.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Integer> {
    ForgotPasswordToken findByForgetPasswordToken(String Forgot_password_token);
}
