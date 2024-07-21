package com.project.socialmedia.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Data
public class PostRequestDTO {

    private String text;
    private Long userId;
}