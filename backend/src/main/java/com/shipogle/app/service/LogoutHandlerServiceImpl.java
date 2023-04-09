package com.shipogle.app.service;

import com.shipogle.app.model.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shipogle.app.repository.JwtTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class LogoutHandlerServiceImpl implements LogoutHandler {

    @Autowired
    JwtTokenRepository jwtTokenRepo;

    /**
     * @author Nandkumar Kadivar
     * Deactivate jwt toke and logout user
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(request.getHeader("Authorization") == null){
            return;
        }else {
            String auth_details[] = request.getHeader("Authorization").split(" ");
            String token_type = auth_details[0];
            String jwt_token = auth_details[1];

            if(token_type.equals("Bearer")){
                try{
                    JwtToken token = jwtTokenRepo.getJwtTokensByToken(jwt_token);
                    token.setIs_active(false);
                    jwtTokenRepo.save(token);
                }catch (ExpiredJwtException e){
                    return;
                }
            }
            else {
                return;
            }
        }
    }
}
