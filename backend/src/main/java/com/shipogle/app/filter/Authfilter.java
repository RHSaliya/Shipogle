package com.shipogle.app.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.shipogle.app.service.JwtTokenService;


@Component
public class Authfilter implements Filter {

    @Autowired
    JwtTokenService jwtTokenService;
    UserDetailsService userDetails;
    private String secretKey = "2A462D4A614E645267556B58703273357638792F423F4528472B4B6250655368";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getHeader("Authorization") == null){
//            System.out.println("Flag ......");
           filterChain.doFilter(servletRequest,servletResponse);
           return;
        }else {
            String auth_details[] = request.getHeader("Authorization").split(" ");
            String token_type = auth_details[0];
            String jwt_token = auth_details[1];
//        System.out.println(jwt_token);

            if(token_type.equals("Bearer")){
//            if(!jwtTokenService.isJwtExpired(jwt_token)){
//                Claims claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt_token).getBody();
//                System.out.println(claim.get("email"));
//            }else {
//                response.sendError(505,"Token Expired");
//                return;
//            }
                try{
                    Claims claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt_token).getBody();
                    System.out.println(claim.get("email"));
                }catch (ExpiredJwtException e){
                    response.getWriter().print(e.getMessage());
                }
            }
            else {
                return;
            }
        }

    }

}
