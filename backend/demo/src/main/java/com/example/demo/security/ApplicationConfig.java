package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.repositories.UserRepo;

@Configuration
public class ApplicationConfig {

    private UserRepo userRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        
        return username -> userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    }
    
}
