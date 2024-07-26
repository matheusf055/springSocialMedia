package com.project.socialmedia.domain;

import static com.project.socialmedia.common.PostConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.project.socialmedia.dto.PostRequestDTO;
import com.project.socialmedia.dto.PostResponseDTO;
import com.project.socialmedia.dto.RetweetRequestDTO;
import com.project.socialmedia.dto.mapper.PostMapperService;
import com.project.socialmedia.entity.Post;
import com.project.socialmedia.repository.PostRepository;
import com.project.socialmedia.repository.UserRepository;
import com.project.socialmedia.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostMapperService postMapperService;

    private Post post;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, userRepository, postMapperService);
        post = new Post();
        post.setComments(new HashSet<>());
    }

    private void mockAuthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(USER.getUsername());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(USER.getUsername())).thenReturn(Optional.of(USER));
    }

    @Test
    public void createPost_WithValidData_ReturnsResponseDTO() {
        mockAuthenticatedUser();
        when(postMapperService.toEntity(any(PostRequestDTO.class))).thenReturn(POST);
        when(postRepository.save(any(Post.class))).thenReturn(POST);
        when(postMapperService.toResponseDTO(any(Post.class))).thenReturn(POST_RESPONSE_DTO);

        PostResponseDTO sut = postService.create(POST_REQUEST_DTO);

        assertThat(sut).isEqualTo(POST_RESPONSE_DTO);
    }

    @Test
    public void findById_WithExistingId_ReturnsResponseDTO() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(POST));
        when(postMapperService.toResponseDTO(any(Post.class))).thenReturn(POST_RESPONSE_DTO);

        Optional<PostResponseDTO> sut = postService.findById(1L);

        assertThat(sut).isPresent().hasValue(POST_RESPONSE_DTO);
    }

    @Test
    public void findAll_ReturnsListOfPostResponseDTOs() {
        when(postRepository.findAll()).thenReturn(List.of(POST));
        when(postMapperService.toResponseDTO(any(Post.class))).thenReturn(POST_RESPONSE_DTO);

        List<PostResponseDTO> sut = postService.findAll();

        assertThat(sut).containsExactly(POST_RESPONSE_DTO);
    }

    @Test
    public void updatePost_WithValidData_ReturnsUpdatedResponseDTO() {
        when(postRepository.existsById(anyLong())).thenReturn(true);
        when(postMapperService.toEntity(any(PostRequestDTO.class))).thenReturn(POST);
        when(postRepository.save(any(Post.class))).thenReturn(POST);
        when(postMapperService.toResponseDTO(any(Post.class))).thenReturn(POST_RESPONSE_DTO);

        PostResponseDTO sut = postService.update(1L, POST_REQUEST_DTO);

        assertThat(sut).isEqualTo(POST_RESPONSE_DTO);
    }

    @Test
    public void deletePost_WithValidId_DeletesPost() {
        postService.delete(1L);

        verify(postRepository).deleteById(1L);
    }

    @Test
    public void commentOnPost_WithValidData_AddsComment() {
        mockAuthenticatedUser();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.comment(1L, POST_REQUEST_DTO);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();
        assertThat(savedPost.getComments()).isNotEmpty();
        assertThat(savedPost.getNumberComments()).isEqualTo(1);
    }

    @Test
    public void likePost_WithValidId_IncrementsLikes() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(POST));
        when(postRepository.save(any(Post.class))).thenReturn(POST);

        postService.like(1L);

        verify(postRepository).save(POST);
    }

    @Test
    public void repost_WithValidData_IncrementsReposts() {
        mockAuthenticatedUser();

        Post originalPost = new Post();
        originalPost.setId(1L);
        originalPost.setReposts(4);

        when(postRepository.findById(1L)).thenReturn(Optional.of(originalPost));

        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            if (post.getId() == null) {
                post.setId(2L);
                post.setReposts(0);
            } else {
                post.setReposts(post.getReposts() + 1);
            }
            return post;
        });

        RetweetRequestDTO requestDTO = new RetweetRequestDTO(1L, 2L);
        postService.repost(requestDTO);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository, times(2)).save(postCaptor.capture());

        List<Post> savedPosts = postCaptor.getAllValues();

        Post updatedOriginalPost = savedPosts.stream()
                .filter(p -> p.getId() == 1L)
                .findFirst()
                .orElseThrow();
        assertThat(updatedOriginalPost.getReposts()).isEqualTo(6);

        Post repostedPost = savedPosts.stream()
                .filter(p -> p.getId() == 2L)
                .findFirst()
                .orElseThrow();
        assertThat(repostedPost.getReposts()).isEqualTo(0);
    }
}
