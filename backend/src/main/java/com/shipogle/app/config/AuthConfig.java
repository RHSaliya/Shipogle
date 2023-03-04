package com.shipogle.app.config;

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
                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .authorizeRequests().antMatchers("/register").permitAll().antMatchers("/login").permitAll().antMatchers("/verification").permitAll().anyRequest().authenticated()
                  .and()
                  .logout().permitAll()
                  .and()
                  .authenticationProvider(authProvider())
          ;
     }


     @Bean
     public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
     }

     @Bean
     public UserDetailsService userDetails(){
          System.out.println("Flag from useDetails Method");
          return new UserDetailsService() {
//               System.out.println("Flag from useDetails Method");
               @Override
               public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    //System.out.println("Flag from loadUserByUsername Method");
                    return (UserDetails) userRepository.getUserByEmail(username);
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
