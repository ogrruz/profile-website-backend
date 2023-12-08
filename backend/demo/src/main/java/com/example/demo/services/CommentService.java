package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.models.CommentEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.CommentRepo;
@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public void saveComment(CommentEntity comment) {
        //Add comment filters - Censoring?
        commentRepo.save(comment);
    }

    // methods for fetching, editing, etc 

    public CommentEntity findCommentById(Long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    public List<CommentEntity> findCommentsByUser(UserEntity user){
        return commentRepo.findMultipleByUser(user);
    }

    public void deleteComment(CommentEntity comment) {
        commentRepo.delete(comment);
    }

    public void UpdateOrLeaveNewComment(UserEntity user, String newText){

        CommentEntity currentComment = commentRepo.findByUser(user);

        if (currentComment == null){
            //User not left a comment before
            CommentEntity newComment = new CommentEntity();
            newComment.setComment(newText);
            saveComment(newComment);
        }
        else {
            // User has already left a comment before - making a new one edits their previous one
            currentComment.setComment(newText);
            saveComment(currentComment);
        }
        
    }

    



    
}
