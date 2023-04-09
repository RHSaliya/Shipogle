package com.shipogle.app.config;

import com.shipogle.app.service.LogoutHandlerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.shipogle.app.filter.Authfilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.shipogle.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.shipogle.app.utility.Const.AUTH_EXCEPT_PATHS;

/*
* Reference: https://www.marcobehler.com/guides/spring-security
* Reference: https://spring.io/guides/gs/securing-web/
* Reference: https://www.toptal.com/spring/spring-security-tutorial
*/

@EnableWebSecurity
@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {
     @Autowired
     Authfilter authFilter;

     @Autowired
     UserRepository userRepository;

     @Autowired
     LogoutHandlerServiceImpl logoutService;

     /**
      * @author Nandkumar Kadivar
      * Configurate the http security
      * @param http HttpConfig.
      */
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         CorsConfigurer<HttpSecurity> buildConfig  = http.cors();
         HttpSecurity config = buildConfig.and().csrf().disable();
         config.httpBasic().disable();
         config.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         config.authorizeRequests().antMatchers(AUTH_EXCEPT_PATHS).permitAll().anyRequest().authenticated();
         config.logout().logoutUrl("/logout").addLogoutHandler(logoutService).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
         config.authenticationProvider(authProvider()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
     }

     /**
      * @author Rahul Saliya
      * Configurate the web security
      * @param web WebSecurity.
      */
     @Override
     public void configure(WebSecurity web) throws Exception {
          web.ignoring().antMatchers("/chatSocket/**");
          web.ignoring().antMatchers("/notificationSocket/**");
          super.configure(web);
     }

     /**
      * @author Nandkumar Kadivar
      * Configure Authentication manager
      * @param configuration auth configuration.
      */
     @Bean
     public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
     }

     /**
      * @author Nandkumar Kadivar
      * User detail service
      */
     @Bean
     public UserDetailsService userDetails() {
          return new UserDetailsService() {
               @Override
               public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return userRepository.findByEmail(username);
               }
          };
     }

     /**
      * @author Nandkumar Kadivar
      * Authentication provider
      */
     @Bean
     public AuthenticationProvider authProvider() {
          DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
          provider.setUserDetailsService(userDetails());
          provider.setPasswordEncoder(new BCryptPasswordEncoder());
          System.out.println(provider.getClass());
          return provider;
     }

}
