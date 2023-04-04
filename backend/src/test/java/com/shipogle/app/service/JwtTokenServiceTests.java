package com.shipogle.app.service;

import com.shipogle.app.model.JwtToken;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.JwtTokenRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTests {
    @InjectMocks
    JwtTokenServiceImpl jwtTokenService;
    @Mock
    JwtTokenRepository jwtTokenRepo;
    @Mock
    JwtToken token;

    @Mock
    JwtBuilder jwtBuilder;
    @Mock
    User user;

//    @Test
//    public void createJwtTokenTest(){
//        Jwts jwts1 = Mockito.mock(Jwts.class,Mockito.RETURNS_DEEP_STUBS);
//        Mockito.when(user.getEmail()).thenReturn("kadivarnand007@gmail.com");
//        Mockito.when(user.getFirst_name()).thenReturn("Nand");
//        Mockito.when(jwts1.builder().claim("email",user.getEmail()).compact()).thenReturn("token");
//
//        assertEquals("token",jwtTokenService.createJwtToken(user).getToken());
//    }

    @Test
    public void deactiveUserTestSuccess(){
        List<JwtToken> tokens = new ArrayList<>();
        tokens.add(token);
        Mockito.when(jwtTokenRepo.getAllByUser(user)).thenReturn(tokens);
        jwtTokenService.deactiveUserTokens(user);
        Mockito.verify(token,Mockito.times(1)).setIs_active(false);
    }

    @Test
    public void deactiveUserTestStoreTokens(){
        List<JwtToken> tokens = new ArrayList<>();
        tokens.add(token);
        Mockito.when(jwtTokenRepo.getAllByUser(user)).thenReturn(tokens);
        jwtTokenService.deactiveUserTokens(user);
        Mockito.verify(jwtTokenRepo,Mockito.times(1)).saveAll(tokens);
    }

    @Test
    public void isJwtActiveTestPositive(){
        Mockito.when(jwtTokenRepo.getJwtTokensByToken("jwt token")).thenReturn(token);
        Mockito.when(token.getIs_active()).thenReturn(true);
        assertTrue(jwtTokenService.isJwtActive("jwt token"));
    }

    @Test
    public void isJwtActiveTestNegative(){
        Mockito.when(jwtTokenRepo.getJwtTokensByToken("jwt token")).thenReturn(token);
        Mockito.when(token.getIs_active()).thenReturn(false);
        assertFalse(jwtTokenService.isJwtActive("jwt token"));
    }
}
