package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.models.UserEntity;
import com.example.demo.services.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

// @Configuration
// public class SecurityConfiguration {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests((authz) -> authz
//                 .anyRequest().authenticated()
//             )
//             .httpBasic(withDefaults());
//         return http.build();
//     }
    

// }


// @Configuration
// public class SecurityConfiguration {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests((authz) -> authz
//                 .anyRequest().authenticated()
//             )
//             .httpBasic(withDefaults());
//         return http.build();
//     }

    

// }

