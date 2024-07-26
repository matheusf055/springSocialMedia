package com.project.socialmedia.dto.mapper;

import com.project.socialmedia.dto.PostRequestDTO;
import com.project.socialmedia.dto.PostResponseDTO;
import com.project.socialmedia.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMapperService {

    private final ModelMapper modelMapper;

    public Post toEntity(PostRequestDTO postRequestDTO) {
        Post post = modelMapper.map(postRequestDTO, Post.class);
        post.setUserId(postRequestDTO.getUserId());
        return post;
    }

    public PostResponseDTO toResponseDTO(Post post) {
        return modelMapper.map(post, PostResponseDTO.class);
    }
}
