package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


import com.example.demo.models.CommentEntity;
import com.example.demo.models.UserEntity;

public interface CommentRepo extends JpaRepository <CommentEntity, Long> {
    List<CommentEntity> findByUser(UserEntity user);
}
