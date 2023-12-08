package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.UserEntity;
import java.util.Optional;


public interface UserRepo extends JpaRepository <UserEntity, Long> {
    UserEntity findByUsername (String username);

    Optional<UserEntity> findById(Long id);
}
