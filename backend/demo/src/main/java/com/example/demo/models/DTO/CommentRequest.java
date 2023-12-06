package com.example.demo.models.DTO;

public class CommentRequest {

    private String text;

    private Long id;

    public CommentRequest() {

    }

    //getters and setters

    public CommentRequest(String text, Long id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId(){
        return this.id;
    }

    public void setID(Long id){
        this.id = id;
    }

    
}
