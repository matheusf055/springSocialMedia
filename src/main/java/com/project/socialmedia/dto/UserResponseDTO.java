package com.project.socialmedia.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String username;
    private String summary;
    private LocalDate birthDate;
    private String email;
    private String createdAt;
    private String updatedAt;
}
