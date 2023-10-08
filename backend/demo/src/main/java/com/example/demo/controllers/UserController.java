package com.example.demo.controllers;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.UserEntity;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {

        if (userService.findByUsername(user.getUsername()) == null){
            userService.registerUser(user);
            return ResponseEntity.ok("User registered sucessfully.");
        }
        else {return ResponseEntity.status(400).body("User not registered: this username already exists");}

        
    }

    // Add a GET endpoint to retrieve user information by username
    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> getUserByName(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);
        
        if (user != null){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    // Other endpoints for login, etc.
    
}
