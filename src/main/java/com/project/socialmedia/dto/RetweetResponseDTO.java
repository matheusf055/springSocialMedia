package com.project.socialmedia.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Data
public class RetweetResponseDTO {

    private Long id;
    private Long postId;
    private Long authorId;
    private Long retweeterId;
    private LocalDateTime createdAt;
}
