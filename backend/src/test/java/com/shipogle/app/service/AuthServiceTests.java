package com.shipogle.app.service;

import com.shipogle.app.model.User;
import com.shipogle.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTests {
    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepo;

    @Test
    public void isAlreadyExistTestPositive(){
        User user = new User();
        user.setEmail("kadivarnand007@gmail.com");
        when(userRepo.findUserByEmail(user.getEmail())).thenReturn(new User());

        assertTrue(authService.isAlreadyExist(user));
    }

    @Test
    public void isAlreadyExistTestNagative(){
        User user = new User();
        user.setEmail("kadivarnand@gmail.com");
        when(userRepo.findUserByEmail(user.getEmail())).thenReturn(null);

        assertFalse(authService.isAlreadyExist(user));
    }

    @Test
    public void resetPasswordTest(){
        String email = "nandkumarkadivar2001@gmail.com";
        String password = "abc123";

        when(userRepo.getUserByEmail(email)).thenReturn(new User());

        assertEquals("Password changed successfully",authService.resetPassword(email,password));
    }

    @Test
    public void resetPasswordTestPasswordChange(){
        String email = "nandkumarkadivar2001@gmail.com";
        String password = "abc123";
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        when(userRepo.getUserByEmail(email)).thenReturn(user);

        authService.resetPassword(email,password);
        assertTrue(encoder.matches(password,user.getPassword()));
    }
}
