package com.shipogle.app.service;

import com.shipogle.app.model.User;
import com.shipogle.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

}
