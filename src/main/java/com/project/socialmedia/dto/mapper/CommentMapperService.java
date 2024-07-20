package com.project.socialmedia.dto.mapper;

import com.project.socialmedia.dto.CommentRequestDTO;
import com.project.socialmedia.dto.CommentResponseDTO;
import com.project.socialmedia.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentMapperService {

    private final ModelMapper modelMapper;

    public Comment toEntity(CommentRequestDTO commentRequestDTO){
        return modelMapper.map(commentRequestDTO, Comment.class);
    }

    public CommentResponseDTO toResponseDTO(Comment comment){
        return modelMapper.map(comment, CommentResponseDTO.class);
    }
}
