package com.project.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Data
public class ProfileDTO {

    private Long id;
    private String fullName;
    private String username;
    private String summary;
    private List<Long> follows;
    private List<Long> followers;
    private List<PostDTO> posts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
