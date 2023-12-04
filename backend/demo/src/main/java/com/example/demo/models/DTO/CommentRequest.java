package com.example.demo.models.DTO;

public class CommentRequest {

    private String text;

    public CommentRequest() {

    }

    //getters and setters

    public CommentRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
}
