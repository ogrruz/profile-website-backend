package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.models.Role;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.UserRepo;
import com.example.demo.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

//user and auth service

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepo userRepo;

    //private UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    // @Transactional
    // public void registerUser(UserEntity user) {
    //     // perform registration
    //     userRepo.saveAndFlush(user);
    // }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .permissions(Role.GUEST)
            .build();

            userRepo.save(user);

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        //UserEntity user = userService.findByEmail(request.getEmail());

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();

    }

    @Transactional
    public UserEntity findByUsername (String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Transactional
    public UserEntity findById (Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public UserEntity findByEmail (String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
