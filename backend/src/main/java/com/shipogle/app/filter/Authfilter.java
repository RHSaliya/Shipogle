package com.shipogle.app.filter;

import com.shipogle.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    UserRepository userRepo;

    UserDetailsService userDetailsService = new UserDetailsService() {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username);
    }
};


    private String secretKey = "2A462D4A614E645267556B58703273357638792F423F4528472B4B6250655368";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getHeader("Authorization") == null){
           filterChain.doFilter(servletRequest,servletResponse);

           return;
        }else {
            String auth_details[] = request.getHeader("Authorization").split(" ");
            String token_type = auth_details[0];
            String jwt_token = auth_details[1];

            if(token_type.equals("Bearer")){

                try{
                    Claims claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt_token).getBody();
                    String email = (String) claim.get("email");

                    if(jwtTokenService.isJwtActive(jwt_token)){
                        UserDetails user = userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(email,null,user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth_token);
                        filterChain.doFilter(request,response);
                    }else {
                        response.sendError(401,"Unauthorized");
                        return;
                    }

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
