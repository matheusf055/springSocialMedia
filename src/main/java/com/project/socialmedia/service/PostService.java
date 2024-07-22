package com.project.socialmedia.service;

import com.project.socialmedia.dto.PostRequestDTO;
import com.project.socialmedia.dto.PostResponseDTO;
import com.project.socialmedia.dto.RetweetRequestDTO;
import com.project.socialmedia.dto.mapper.PostMapperService;
import com.project.socialmedia.entity.Comment;
import com.project.socialmedia.entity.Post;
import com.project.socialmedia.entity.User;
import com.project.socialmedia.repository.PostRepository;
import com.project.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // Adicione isso para buscar o usuário autenticado
    private final PostMapperService postMapperService;

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public PostResponseDTO create(PostRequestDTO postRequestDTO){
        Post post = postMapperService.toEntity(postRequestDTO);
        post.setUserId(getAuthenticatedUser().getId());
        Post createdPost = postRepository.save(post);
        return postMapperService.toResponseDTO(createdPost);
    }

    public Optional<PostResponseDTO> findById(Long id){
        return postRepository.findById(id).map(postMapperService::toResponseDTO);
    }

    public List<PostResponseDTO> findAll(){
        return postRepository.findAll().stream().map(postMapperService::toResponseDTO).toList();
    }

    public PostResponseDTO update(Long id, PostRequestDTO postRequestDTO){
        if (postRepository.existsById(id)){
            Post post = postMapperService.toEntity(postRequestDTO);
            post.setId(id);
            Post updatedPost = postRepository.save(post);
            return postMapperService.toResponseDTO(updatedPost);
        }
        return null;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void comment(Long postId, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = getAuthenticatedUser();
        Comment comment = new Comment();
        comment.setText(postRequestDTO.getText());
        comment.setPost(post);
        comment.setAuthor(user);
        post.getComments().add(comment);
        post.setNumberComments(post.getNumberComments() + 1);
        postRepository.save(post);
    }

    public void like(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    public void repost(RetweetRequestDTO retweetRequestDTO) {
        Post post = postRepository.findById(retweetRequestDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = getAuthenticatedUser();
        Post repost = new Post();
        repost.setUserId(user.getId());
        repost.setText(post.getText());
        repost.setReposts(0);
        repost.setLikes(0);
        repost.setNumberComments(0);
        repost.setCreatedAt(LocalDateTime.now());
        repost.setUpdatedAt(LocalDateTime.now());
        postRepository.save(repost);
        post.setReposts(post.getReposts() + 1);
        postRepository.save(post);
    }
}
