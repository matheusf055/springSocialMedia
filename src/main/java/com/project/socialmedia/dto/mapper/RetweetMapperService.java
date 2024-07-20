package com.project.socialmedia.dto.mapper;

import com.project.socialmedia.dto.RetweetRequestDTO;
import com.project.socialmedia.dto.RetweetResponseDTO;
import com.project.socialmedia.entity.Retweet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetMapperService {

    private final ModelMapper modelMapper;

    public Retweet toEntity(RetweetRequestDTO requestDTO){
        return modelMapper.map(requestDTO, Retweet.class);
    }

    public RetweetResponseDTO toResponseDTO(Retweet retweet){
        return modelMapper.map(retweet, RetweetResponseDTO.class);
    }
}
