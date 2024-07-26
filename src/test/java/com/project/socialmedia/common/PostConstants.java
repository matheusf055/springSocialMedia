package com.project.socialmedia.common;

import com.project.socialmedia.dto.*;
import com.project.socialmedia.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

public class PostConstants {

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

    public static final Post POST = new Post(
            1L,
            USER.getId(),
            "Test Post",
            0,
            0,
            0,
            LocalDateTime.now(),
            LocalDateTime.now(),
            USER,
            Collections.emptySet(),
            Collections.emptySet()
    );

    public static final PostRequestDTO POST_REQUEST_DTO = new PostRequestDTO(
            "Test Post",
            USER.getId()
    );

    public static final PostResponseDTO POST_RESPONSE_DTO = new PostResponseDTO(
            POST.getId(),
            POST.getUserId(),
            POST.getText(),
            POST.getLikes(),
            POST.getReposts(),
            POST.getNumberComments(),
            POST.getCreatedAt(),
            POST.getUpdatedAt(),
            Collections.emptyList()
    );

    public static final RetweetRequestDTO RETWEET_REQUEST_DTO = new RetweetRequestDTO(
            POST.getId(),
            USER.getId()
    );

    public static final Comment COMMENT = new Comment(
            1L,
            "Test Comment",
            0,
            0,
            LocalDateTime.now(),
            POST,
            USER
    );

    public static final CommentDTO COMMENT_DTO = new CommentDTO(
            COMMENT.getId(),
            COMMENT.getPost().getId(),
            COMMENT.getAuthor().getId(),
            COMMENT.getAuthor().getUsername(),
            COMMENT.getText(),
            COMMENT.getLikes(),
            COMMENT.getReposts(),
            0
    );
}
