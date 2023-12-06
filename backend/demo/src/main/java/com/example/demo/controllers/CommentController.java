package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.CommentEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.models.DTO.CommentRequest;
import com.example.demo.services.CommentService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    // Endpoints

    //Adding new comment
    // @PostMapping("/add")
    // public ResponseEntity<String> addComment(@RequestBody CommentRequest commentRequest) {
        
    //     // You might want to perform authentication and get the current user
    //     // This is a simplified example assuming you have a CommentRequest class
    //     // that represents the structure of the incoming JSON request

    //     CommentEntity comment = new CommentEntity();
    //     comment.setComment(commentRequest.getText());

    //     // Set the user making the comment (you may get this from authentication)
    //     // For simplicity, let's assume a user with ID 1

    //     UserEntity user = new UserEntity();
    //     user.setId(1L);
    //     comment.setUser(user);

    //     commentService.saveComment(comment);

    //     return ResponseEntity.ok("Comment added successfully");
        

    // }

    @PostMapping("/add")
    public ResponseEntity<String> addCommentToUser(@RequestBody CommentRequest commentRequest) {

        /// remove after testing complete -----------------------------
        String temp = commentRequest.getText();
        System.out.println("The comment text is: " + temp);

        Long IDtemp = commentRequest.getId();
        System.out.println("The comment user id is: " + IDtemp);
        ///--------------------------------------------------

        CommentEntity comment = new CommentEntity();
        comment.setComment(commentRequest.getText());

        UserEntity user = userService.findById(commentRequest.getId());

        comment.setUser(user);
        commentService.saveComment(comment);

        return ResponseEntity.ok("Comment added successfully to user");
        

    }

}
