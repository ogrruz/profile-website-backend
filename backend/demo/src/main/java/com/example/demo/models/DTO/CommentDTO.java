package com.example.demo.models.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    
    private String commentText;

    private String userDisplayName;

    private LocalDateTime creationDate;

    private LocalDateTime lastModifed;

    private Long commentId;

}
