package com.shipogle.app.service;

import com.shipogle.app.model.ForgotPasswordToken;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.ForgotPasswordTokenRepository;
import com.shipogle.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepo;

    @Mock
    User user;

    @Mock
    ForgotPasswordToken forgotPasswordToken;

    @Mock
    MailService mailService;

    @Mock
    ForgotPasswordTokenService forgotPasswordTokenService;

    @Mock
    ForgotPasswordTokenRepository forgotPasswordTokenRepo;

    @Mock
    Claims claims;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void isAlreadyExistTestPositive() {
        // User user = Mockito.mock(User.class);
        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");

        Mockito.when(userRepo.findUserByEmail(user.getEmail())).thenReturn(user);

        assertTrue(authService.isAlreadyExist(user));
    }

    @Test
    public void isAlreadyExistTestNagative() {
        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.lenient().when(userRepo.findUserByEmail(user.getEmail())).thenReturn(null);

        assertFalse(authService.isAlreadyExist(user));
    }

//    @Test
//    public void verifyEmailTestUserAlreadyVerified() {
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        String code = encoder.encode("kadivarnand007@gmail.com");
//
//        Mockito.lenient().when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
//        Mockito.lenient().when(user.getIs_verified()).thenReturn(true);
//        Mockito.lenient().when(user.getUser_id()).thenReturn(40);
//        Mockito.lenient().when(userRepo.getById(user.getUser_id())).thenReturn(user);
//
//        assertEquals("Already Verified", authService.verifyEmail(code, user.getUser_id()));
//    }

    @Test
    public void forgotPasswordTest() {

        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.when(userRepo.getUserByEmail("kadivarnand007@gmail.com")).thenReturn(user);

        Mockito.when(forgotPasswordTokenService.createForgotPasswordToken(user)).thenReturn(forgotPasswordToken);
        Mockito.when(forgotPasswordToken.getForgot_password_token()).thenReturn("token");

        assertEquals("Password reset link sent", authService.forgotPassword("kadivarnand007@gmail.com"));
    }

//    @Test
//    public void registerTestUserAlreadyExist() {
//        authService = Mockito.spy(new AuthService());
//        Mockito.doReturn(true).when(authService).isAlreadyExist(user);
//
//        assertEquals("User Already exist with this email", authService.register(user));
//    }

}
