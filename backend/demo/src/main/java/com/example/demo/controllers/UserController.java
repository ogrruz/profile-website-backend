package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.models.UserEntity;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // @PostMapping("/register")
    // public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {

    //     /// remove after testing complete -----------------------------
    //     String temp = user.getEmail();
    //     System.out.println("This user's email is: " + temp);

    //     String pswdTemp = user.getPassword();
    //     System.out.println("This user's password is: " + pswdTemp);
    //     ///--------------------------------------------------

    //     if (userService.findByUsername(user.getUsername()) == null){
    //         userService.register(user);
    //         return ResponseEntity.ok("User registered sucessfully.");
    //     }
    //     else {return ResponseEntity.status(400).body("User not registered: this username already exists");}

        
    // }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        
        return ResponseEntity.ok(userService.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        
        return ResponseEntity.ok(userService.authenticate(request));

    }


    // public SomeEnityData postMethodName(@RequestBody SomeEnityData entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }
    

    // Add a GET endpoint to retrieve user information by username
    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> getUserByName(@PathVariable String username) {
        UserEntity user = userService.findByEmail(username);
        
        if (user != null){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    // Other endpoints for login, etc.
    
}
