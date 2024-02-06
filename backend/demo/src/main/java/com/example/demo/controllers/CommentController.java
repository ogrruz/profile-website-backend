package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryInformation;
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
import com.example.demo.models.DTO.CommentDTO;
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
    
    //old testing request - do not use
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
    public ResponseEntity<List<CommentDTO>> retrieveComments() {

        List<CommentEntity> comments = commentService.findAll();
        List<CommentDTO> commentsDTOs = new ArrayList<>();

        if (comments != null) {

            for(CommentEntity comment : comments) {
                CommentDTO dto = new CommentDTO();
                dto.setCommentText(comment.getCommentText());
                dto.setCreationDate(comment.getDate());
                dto.setLastModifed(comment.getLastModifiedDate());
                dto.setUserDisplayName(comment.getUser().getDisplayName());
                dto.setCommentId(comment.getId());
                commentsDTOs.add(dto);
            }
            
            return ResponseEntity.ok(commentsDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @PostMapping("/checkOwner")
    public ResponseEntity<Boolean> belongsToUser(@RequestBody CommentDTO commentDTO, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        String jwt = authorizationHeader.substring("Bearer ".length());
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userService.findByUsername(username);
        
        CommentEntity matchedComment = commentService.findCommentById(commentDTO.getCommentId());

        if (matchedComment != null && user.getId() == matchedComment.getUser().getId()){
            return ResponseEntity.ok(true);
        } else if ( matchedComment != null &&  user.getId() != matchedComment.getUser().getId()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
        
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestBody CommentDTO commentDTO, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        String jwt = authorizationHeader.substring("Bearer ".length());
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userService.findByUsername(username);
        
        CommentEntity matchedComment = commentService.findCommentById(commentDTO.getCommentId());

        //ensure the commentId given matches the comment text of the  comment
        Boolean verifyComment = matchedComment.getCommentText().equals(commentDTO.getCommentText());

        if (verifyComment == true && user.getId() == matchedComment.getUser().getId()){
            
            commentService.deleteComment(matchedComment);
            return ResponseEntity.ok("Comment Successfully removed");
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editComent(@RequestBody CommentDTO newComment, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        
        String jwt = authorizationHeader.substring("Bearer ".length());
        String username = jwtService.extractUsername(jwt);
        UserEntity user = userService.findByUsername(username);
        
        CommentEntity matchedComment = commentService.findCommentById(newComment.getCommentId());

        //verify user's authorship of the comment
        List<CommentEntity> commentsByUser = commentService.findCommentsByUser(user);
        Boolean containsComment = false;
        for (CommentEntity comment : commentsByUser) {
            if (comment.getId() == matchedComment.getId()) {
                containsComment = true;
                break;
            }
        }

        if(containsComment) {
            commentService.editComment(matchedComment, newComment.getCommentText());
            return ResponseEntity.ok("Comment successfully updated");
        } else {
            return ResponseEntity.notFound().build();
        }
        
        
    }

}
