package com.project.socialmedia.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Data
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String summary;
    private LocalDate birthDate;
    private String username;
    private String email;
    private String password;
}
