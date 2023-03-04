package com.shipogle.app.config;

import com.shipogle.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.shipogle.app.filter.Authfilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.shipogle.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter{
     @Autowired
     Authfilter authFilter;

     @Autowired
     UserRepository userRepository;


     @Override
     protected void configure(HttpSecurity http) throws Exception {
//          super.configure(auth);
          http.cors()
                  .and()
                  .csrf().disable()
                  .httpBasic().disable()
                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .authorizeRequests()
                    .antMatchers("/register","/verification","/changepassword","/login").permitAll()
                    .anyRequest().authenticated()
                  .and()
                  .authenticationProvider(authProvider()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
          ;

     }


     @Bean
     public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
     }

     @Bean
     public UserDetailsService userDetails(){


          return new UserDetailsService() {
               @Override
               public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return userRepository.findByEmail(username);
               }
          };
     }

     @Bean
     public AuthenticationProvider authProvider(){
          DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
          //System.out.println("Flag from authProvider Method");
          provider.setUserDetailsService(userDetails());
          provider.setPasswordEncoder(new BCryptPasswordEncoder());
          System.out.println(provider.getClass());
          return provider;
     }

}
