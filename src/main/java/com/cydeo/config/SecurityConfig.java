package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    // this is the second part of what is needed to implement multiple users with varying authorizations
    @Bean
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

}

