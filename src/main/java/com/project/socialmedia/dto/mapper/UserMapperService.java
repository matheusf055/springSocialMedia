package com.project.socialmedia.dto.mapper;

import com.project.socialmedia.dto.UserRequestDTO;
import com.project.socialmedia.dto.UserResponseDTO;
import com.project.socialmedia.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapperService {

    private final ModelMapper modelMapper;

    public User toEntity(UserRequestDTO userRequestDTO){
        return modelMapper.map(userRequestDTO, User.class);
    }

    public UserResponseDTO toResponseDTO(User user){
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
