package com.shipogle.app.service;

import com.shipogle.app.model.ForgotPasswordToken;
import com.shipogle.app.model.JwtToken;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.ForgotPasswordTokenRepository;
import com.shipogle.app.repository.JwtTokenRepository;
import com.shipogle.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    UserRepository userRepo;

    @Mock
    User user;

    @Mock
    ForgotPasswordToken forgotPasswordToken;

    @Mock
    MailServiceImpl mailService;

    @Mock
    ForgotPasswordTokenService forgotPasswordTokenService;

    @Mock
    ForgotPasswordTokenRepository forgotPasswordTokenRepo;

    @Mock
    Claims claims;

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    AuthenticationManager authManager;

    @Mock
    UsernamePasswordAuthenticationToken authToken;

    @Mock
    JwtTokenService jwtTokenService;

    @Mock
    JwtTokenRepository jwtTokenRepo;

    @Mock
    JwtToken token;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void isAlreadyExistTestPositive() {
        // User user = Mockito.mock(User.class);
        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");

        Mockito.when(userRepo.findUserByEmail(user.getEmail())).thenReturn(user);

        assertTrue(authService.isAlreadyExist(user));
    }

    @Test
    public void isAlreadyExistTestNegative() {
        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.lenient().when(userRepo.findUserByEmail(user.getEmail())).thenReturn(null);

        assertFalse(authService.isAlreadyExist(user));
    }

    @Test
    public void verifyEmailTestUserAlreadyVerified() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String code = encoder.encode("kadivarnand007@gmail.com");

        Mockito.lenient().when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.lenient().when(user.getIs_verified()).thenReturn(true);
        Mockito.lenient().when(user.getUser_id()).thenReturn(40);
        Mockito.lenient().when(userRepo.getById(user.getUser_id())).thenReturn(user);

        assertThrows(ResponseStatusException.class,() -> authService.verifyEmail(code, user.getUser_id()));
    }

    @Test
    public void verifyEmailTestVerifyUser() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String code = encoder.encode("kadivarnand007@gmail.com");

        Mockito.lenient().when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.lenient().when(user.getIs_verified()).thenReturn(false);
        Mockito.lenient().when(user.getUser_id()).thenReturn(40);
        Mockito.lenient().when(userRepo.getById(user.getUser_id())).thenReturn(user);

        assertEquals("Email Verified",authService.verifyEmail(code, user.getUser_id()));
    }

    @Test
    public void verifyEmailTestVerifyNotValidUser() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String code = encoder.encode("kadivarnand007@gmail.com");

        Mockito.lenient().when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.lenient().when(user.getIs_verified()).thenReturn(false);
        Mockito.lenient().when(user.getUser_id()).thenReturn(40);
        Mockito.lenient().when(userRepo.getById(user.getUser_id())).thenReturn(null);

        assertThrows(ResponseStatusException.class,() -> authService.verifyEmail(code, user.getUser_id()));
    }

    @Test
    public void forgotPasswordTestEmailSend() {

        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.when(userRepo.getUserByEmail("kadivarnand007@gmail.com")).thenReturn(user);

        Mockito.when(forgotPasswordTokenService.createForgotPasswordToken(user)).thenReturn(forgotPasswordToken);
        Mockito.when(forgotPasswordToken.getForgot_password_token()).thenReturn("token");

        assertEquals("Password reset link sent", authService.forgotPassword("kadivarnand007@gmail.com"));
    }

    @Test
    public void forgotPasswordTestEmail() {

        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.when(userRepo.getUserByEmail("kadivarnand007@gmail.com")).thenReturn(null);

        Mockito.when(forgotPasswordTokenService.createForgotPasswordToken(user)).thenReturn(forgotPasswordToken);
        Mockito.when(forgotPasswordToken.getForgot_password_token()).thenReturn("token");

        assertThrows(ResponseStatusException.class,() -> authService.forgotPassword("kadivarnand007@gmail.com"));
    }

    @Test
    public void registerTestUserAlreadyExist() {
        authService = Mockito.spy(new AuthServiceImpl());
        Mockito.doReturn(true).when(authService).isAlreadyExist(user);

        assertThrows(ResponseStatusException.class,() -> authService.register(user));
    }

    @Test
    public void registerTestUserRegistration() {
        User user = new User();
        user.setEmail("kadivarnand007@gmail.com");
        user.setPassword("abc123");
        user.setIs_verified(false);
        Mockito.when(userRepo.findUserByEmail(user.getEmail())).thenReturn(null);
        assertEquals("Verification email sent", authService.register(user));
    }

    @Test
    public void loginTestNotVerifiedUser(){
        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
        Mockito.when(user.getIs_verified()).thenReturn(false);
        Mockito.when(userRepo.getUserByEmail(user.getEmail())).thenReturn(user);
        assertThrows(ResponseStatusException.class,()->authService.login("kadivarnand007@gmail.com","abc123"));
    }

//    @Test
//    public void loginTestVerifiedUser(){
//        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
//        Mockito.when(user.getIs_verified()).thenReturn(true);
//        Mockito.when(userRepo.getUserByEmail(user.getEmail())).thenReturn(user);
//        Mockito.when(token.getToken()).thenReturn("jwt token");
//
//        String result = authService.login("kadivarnand007@gmail.com","abc123");
//
//        verify(jwtTokenService,times(1)).createJwtToken(user);
//    }
}
