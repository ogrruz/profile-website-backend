package com.example.demo.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String commentText;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private LocalDateTime creationDate;
    
    private LocalDateTime lastModifed;

    // getters and setters

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.creationDate = now;
        this.lastModifed = now;
    }

    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.lastModifed = now;
    }

    public String getComment() {
        return this.commentText;
    }

    public void setComment(String text) {
        this.commentText = text;
    }

    public LocalDateTime getDate(){
        return creationDate;
    }

    public LocalDateTime getLastModifiedDate(){
        return this.lastModifed;
    }

    public UserEntity getUser(){
        return this.user;
    }

     public void setUser(UserEntity user) {
        this.user = user;
     }

}
