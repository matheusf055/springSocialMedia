package com.project.socialmedia.domain;

import static com.project.socialmedia.common.PostConstants.USER;
import static com.project.socialmedia.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.socialmedia.dto.ProfileDTO;
import com.project.socialmedia.dto.UserRequestDTO;
import com.project.socialmedia.dto.UserResponseDTO;
import com.project.socialmedia.dto.mapper.ProfileMapperService;
import com.project.socialmedia.dto.mapper.UserMapperService;
import com.project.socialmedia.entity.User;
import com.project.socialmedia.repository.UserRepository;
import com.project.socialmedia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapperService userMapperService;

    @MockBean
    private ProfileMapperService profileMapperService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUser_WithValidData_ReturnsResponseDTO() {
        when(userMapperService.toEntity(any(UserRequestDTO.class))).thenReturn(USER);
        when(userRepository.save(any(User.class))).thenReturn(USER);
        when(userMapperService.toResponseDTO(any(User.class))).thenReturn(USER_RESPONSE_DTO);

        UserResponseDTO sut = userService.register(USER_REQUEST_DTO);

        assertThat(sut).isEqualTo(USER_RESPONSE_DTO);
    }

    @Test
    public void findById_WithExistingId_ReturnsResponseDTO(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        when(userMapperService.toResponseDTO(any(User.class))).thenReturn(USER_RESPONSE_DTO);

        Optional<UserResponseDTO> sut = userService.findById(1L);

        assertThat(sut).isPresent().hasValue(USER_RESPONSE_DTO);
    }

    @Test
    public void FindByUsername_WithExistingUsername_ReturnsUser(){
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));

        User sut = userService.findByUsername("matheusf5");

        assertThat(sut).isEqualTo(USER);
    }

    @Test
    public void getProfileById_WithExistingId_ReturnsProfileDTO() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        when(profileMapperService.getPostsForUser(any())).thenReturn(List.of(POST_DTO));
        when(profileMapperService.toProfileDTO(any(User.class), any())).thenReturn(PROFILE_DTO);

        Optional<ProfileDTO> result = userService.getProfileById(1L);

        assertThat(result).isPresent().hasValue(PROFILE_DTO);
    }

    @Test
    public void update_WithValidData_ReturnsUpdatedUserResponseDTO() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        when(userRepository.save(any(User.class))).thenReturn(UPDATED_USER);
        when(passwordEncoder.encode("newPassword123")).thenReturn("newEncodedPassword");

        UserResponseDTO result = userService.update(1L, USER_REQUEST_DTO);

        assertThat(result).isEqualTo(userMapperService.toResponseDTO(UPDATED_USER));
    }

    @Test
    public void delete_WithValidData_DeletesUser(){
        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void follow_WithValidData_FollowsUser(){
        when((userRepository.findById(1L))).thenReturn(Optional.of(USER));
        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_TO_FOLLOW));

        userService.follow(1L, 2L);

        assertThat(USER.getFollowing()).contains(USER_TO_FOLLOW);
        assertThat(USER_TO_FOLLOW.getFollowers()).contains(USER);
    }

    @Test
    public void unfollow_WithValidData_UnfollowsUser(){
        USER.getFollowing().add(USER_TO_FOLLOW);
        USER_TO_FOLLOW.getFollowers().add(USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));
        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_TO_FOLLOW));

        userService.unfollow(1L, 2L);

        assertThat(USER.getFollowing()).doesNotContain(USER_TO_FOLLOW);
        assertThat(USER_TO_FOLLOW.getFollowers()).doesNotContain(USER);
    }
}
