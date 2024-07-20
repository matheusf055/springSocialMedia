package com.project.socialmedia.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data
public class PostResponseDTO {

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
    private List<CommentDTO> comments;
}
