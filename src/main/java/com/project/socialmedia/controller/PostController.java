package com.project.socialmedia.controller;

import com.project.socialmedia.dto.PostRequestDTO;
import com.project.socialmedia.dto.PostResponseDTO;
import com.project.socialmedia.dto.RetweetRequestDTO;
import com.project.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO postRequestDTO){
        PostResponseDTO postResponseDTO = postService.create(postRequestDTO);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Void> comment(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO){
        postService.comment(postId, postRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/repost")
    public ResponseEntity<Void> repost(@PathVariable Long postId, @RequestBody RetweetRequestDTO retweetRequestDTO){
        postService.repost(retweetRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id){
        return postService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/home")
    public ResponseEntity<List<PostResponseDTO>> findAll(){
        List<PostResponseDTO> home = postService.findAll();
        return ResponseEntity.ok(home);
    }

    @PutMapping
    public ResponseEntity<PostResponseDTO> update(@PathVariable Long id, @RequestBody PostRequestDTO postRequestDTO){
        PostResponseDTO postResponseDTO = postService.update(id, postRequestDTO);
        return ResponseEntity.ok(postResponseDTO);
    }
}
