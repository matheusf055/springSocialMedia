package com.project.socialmedia.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Data
public class CommentDTO {

    private Long id;
    private Long userId;
    private Long authorId;
    private String author;
    private String text;
    private int likes;
    private int reposts;
    private int numberComments;
}
