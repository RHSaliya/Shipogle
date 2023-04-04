package com.shipogle.app.service;

import com.shipogle.app.model.ForgotPasswordToken;
import com.shipogle.app.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ForgotPasswordTokenServiceTest {
    @InjectMocks
    ForgotPasswordTokenServiceImpl forgotPasswordTokenService;
    @Mock
    ForgotPasswordToken forgotPasswordToken;
    @Mock
    JwtBuilder jwtBuilder;
    @Mock
    User user;
    @Test
    public void createForgotPasswordToken(){
//        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
//        Mockito.when(user.getFirst_name()).thenReturn("Nand");
//        Mockito.when(Jwts.builder()).thenReturn(jwtBuilder);
//        Mockito.when(jwtBuilder.claim("email","kadivarnand007@gmail.com")).thenReturn(jwtBuilder);
//        Mockito.when(jwtBuilder.compact()).thenReturn("token");
//
//        assertEquals("token",forgotPasswordTokenService.createForgotPasswordToken(user).getForgot_password_token());
    }
}
