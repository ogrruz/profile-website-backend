package com.example.demo.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.CommentEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.models.DTO.CommentRequest;
import com.example.demo.models.DTO.CommentRequestJWT;
import com.example.demo.services.CommentService;
import com.example.demo.services.UserService;
import com.example.demo.security.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private JwtService jwtService;
    
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

    // Post comment through including jwt in the request
    @PostMapping("/post")
    public ResponseEntity<String> postComment(@RequestBody CommentRequestJWT commentRequest, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String jwt = authorizationHeader.substring("Bearer ".length());
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userService.findByUsername(username);

        CommentEntity comment = new CommentEntity();
        comment.setComment(commentRequest.getCommentText());
        comment.setUser(user);
        commentService.saveComment(comment);
        return ResponseEntity.ok("Comment added successfully to user");
    }
    
    
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

    @GetMapping("/retrieve")
    public ResponseEntity<List<CommentEntity>> retrieveComments() {

        List<CommentEntity> comments = commentService.findAll();

        if (comments != null) {
            
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    

}
