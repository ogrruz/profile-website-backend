package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String username;
    private String password;
    private String email;
    private String permissions;

    // getters and setters

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String newPassword){
        this.password = email;
    }

    public String getPermissions(){
        return this.permissions;
    }
    public void setPermissions(String permissions){
        this.permissions = permissions;
    }

}
