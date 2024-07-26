package com.project.socialmedia.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Data
public class CommentResponseDTO {

    private Long id;
    private Long userId;
    private Long authorId;
    private String author;
    private String text;
    private int likes;
    private int reposts;
    private int numberComments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
