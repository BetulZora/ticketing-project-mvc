package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TicketingProjectMVC {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectMVC.class, args);
    }

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    // add this bean in order to override baseline security and implement multiple users with varying authorizations
    @Bean
    public PasswordEncoder passwordEncoder(){
        // PasswordEncoder is an interface. It is implemented in BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }
}
