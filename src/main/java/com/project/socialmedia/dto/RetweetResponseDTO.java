package com.project.socialmedia.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Data
public class RetweetResponseDTO {

    private Long id;
    private Long postId;
    private Long authorId;
    private Long retweeterId;
    private String createdAt;
}
