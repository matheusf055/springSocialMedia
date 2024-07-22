package com.project.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private Long userId;
    private String text;
    private int likes;
    private int reposts;
    private int numberComments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
