package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserEntity;
import com.example.demo.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Transactional
    public void registerUser(UserEntity user) {
        // perform registration
        userRepo.saveAndFlush(user);
    }

    public UserEntity findByUsername (String username) {
        return userRepo.findByUsername(username);
    }

}
