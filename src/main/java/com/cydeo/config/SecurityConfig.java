package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    // this is the second part of what is needed to implement multiple users with varying authorizations
  /*  @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        // UserDetailsService is an interface that carries out security related services
        // includes the CRUD operations
        // UserDetailsService uses a User object to call a UI view
        // the User class is inherent to Spring

        // loadByUserName(User user) is the method we are the most interested in

        // this is a list in order to carry more than one user
        List<UserDetails> userList = new ArrayList<>();

        userList.add(new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        userList.add(new User("ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));

        return new InMemoryUserDetailsManager(userList);
    }

   */

    // use this part to have the login page recognized as a page that does not require security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .antMatchers("/user/**").hasRole("ADMIN") // those with Admin role can access any method is the user controller
                .antMatchers("/project/**").hasRole("MANAGER") // those with Manager role can access any method in the project controller
                .antMatchers("/task/employee/**").hasRole("EMPLOYEE")
                .antMatchers("/task/**").hasRole("EMPLOYEE")
                //.antMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
                //.antMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")
                .antMatchers( // these are endpoints that can be visualized without authentication
                        "/", "/login", "/fragments/**", "/assets/**", "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
//                .httpBasic()
                .formLogin()
                .loginPage("/login")
//                    .defaultSuccessUrl("/welcome")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .tokenValiditySeconds(120)
                .key("cydeo")
                .userDetailsService(securityService)
                .and().build();
    }

}

