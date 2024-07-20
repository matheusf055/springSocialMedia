package com.project.socialmedia.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Data
public class RetweetRequestDTO {

    private Long postId;
    private Long authorId;
}
