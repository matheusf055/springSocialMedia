package com.project.socialmedia.service;

import com.project.socialmedia.dto.UserRequestDTO;
import com.project.socialmedia.dto.UserResponseDTO;
import com.project.socialmedia.dto.mapper.UserMapperService;
import com.project.socialmedia.entity.User;
import com.project.socialmedia.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    public UserResponseDTO register(UserRequestDTO userRequestDTO){
        User user = userMapperService.toEntity(userRequestDTO);
        User createdUser = userRepository.save(user);
        return userMapperService.toResponseDTO(createdUser);
    }

    public Optional<UserResponseDTO> findById(Long id){
        return userRepository.findById(id).map(userMapperService::toResponseDTO);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User not found"))
        );
    }

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll().stream()
                .map(userMapperService::toResponseDTO).toList();
    }

    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO){
        if (userRepository.existsById(id)){
            User user = userMapperService.toEntity(userRequestDTO);
            user.setId(id);
            User updatedUser = userRepository.save(user);
            return userMapperService.toResponseDTO(updatedUser);
        }
        return null;
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public void follow(Long userId, Long followId){
        User user = userRepository.findById(userId).orElseThrow();
        User follow = userRepository.findById(followId).orElseThrow();
        user.getFollowing().add(follow);
        follow.getFollowers().add(user);
        userRepository.save(user);
        userRepository.save(follow);
    }

    public void unfollow(Long userId, Long unfollowId) {
        User user = userRepository.findById(userId).orElseThrow();
        User unfollow = userRepository.findById(unfollowId).orElseThrow();
        user.getFollowing().remove(unfollow);
        unfollow.getFollowers().remove(user);
        userRepository.save(user);
        userRepository.save(unfollow);
    }
}
