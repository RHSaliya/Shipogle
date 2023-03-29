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

    @MockBean
    private User user;

    @MockBean
    private BCryptPasswordEncoder encoder;

//    @Test
//    public void isAlreadyExistTestPositive(){
//
//        user.setEmail("kadivarnand007@gmail.com");
//        when(userRepo.findUserByEmail(user.getEmail())).thenReturn(user);
//
//        assertTrue(authService.isAlreadyExist(user));
//    }

//    @Test
//    public void isAlreadyExistTestNagative(){
//        User user = new User();
//        user.setEmail("kadivarnand@gmail.com");
//        when(userRepo.findUserByEmail(user.getEmail())).thenReturn(null);
//
//        assertFalse(authService.isAlreadyExist(user));
//    }

    @Test
    public void verifyEmailTest(){
        String encypted_code = "";
        int id=36;

//        when(userRepo.getById(id)).thenReturn()
    }
}
