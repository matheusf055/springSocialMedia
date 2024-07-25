package com.project.socialmedia.common;

import com.project.socialmedia.dto.PostDTO;
import com.project.socialmedia.dto.ProfileDTO;
import com.project.socialmedia.dto.UserRequestDTO;
import com.project.socialmedia.dto.UserResponseDTO;
import com.project.socialmedia.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestsConstans {

    public static final User USER = new User(
            1L,
            "Matheus",
            "Fernandes",
            "Developer",
            LocalDate.of(2004, 6, 3),
            "matheusf5",
            "matheus@gmail.com",
            "password123",
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    public static final User UPDATED_USER = new User(
            1L,
            "Matheus",
            "Fernandes",
            "Senior Developer",
            LocalDate.of(2004, 6, 3),
            "matheusf5",
            "matheus@gmail.com",
            "newEncodedPassword",
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    public static final User USER_TO_FOLLOW = new User(
            2L,
            "João",
            "Silva",
            "Desing",
            LocalDate.of(2005, 4, 3),
            "joaosilva5",
            "joao@gmail.com",
            "password123",
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    public static final UserRequestDTO USER_REQUEST_DTO = new UserRequestDTO(
            USER.getFirstName(),
            USER.getLastName(),
            USER.getSummary(),
            USER.getBirthDate(),
            USER.getUsername(),
            USER.getEmail(),
            USER.getPassword()
    );

    public static final UserResponseDTO USER_RESPONSE_DTO = new UserResponseDTO(
            USER.getId(),
            USER.getFirstName(),
            USER.getLastName(),
            USER.getUsername(),
            USER.getSummary(),
            USER.getBirthDate(),
            USER.getEmail(),
            USER.getPassword(),
            USER.getCreatedAt(),
            USER.getUpdatedAt()
    );

    public static final PostDTO POST_DTO = new PostDTO(
            1L,
            1L,
            "Text",
            10,
            2,
            5,
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    public static final ProfileDTO PROFILE_DTO = new ProfileDTO(
            1L,
            "Matheus Fernandes",
            "matheusf5",
            "Developer",
            List.of(2L),
            List.of(1L),
            List.of(POST_DTO),
            LocalDateTime.now(),
            LocalDateTime.now()
    );
}

