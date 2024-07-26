package com.project.socialmedia.controller;

import com.project.socialmedia.dto.*;
import com.project.socialmedia.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Endpoints for Posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "Create a post", description = "Create a post", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = PostResponseDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO postRequestDTO){
        PostResponseDTO postResponseDTO = postService.create(postRequestDTO);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PostMapping("/{postId}/comment")
    @Operation(summary = "Comment a post", description = "Comment a post", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> comment(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO){
        postService.comment(postId, postRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/like")
    @Operation(summary = "Like a post", description = "Like a post", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> like(@PathVariable Long postId) {
        postService.like(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/repost")
    @Operation(summary = "Repost a post", description = "Repost a post", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> repost(@PathVariable Long postId, @RequestBody RetweetRequestDTO retweetRequestDTO){
        postService.repost(retweetRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find post by id", description = "Find post by id", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PostResponseDTO.class))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id){
        return postService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/home")
    @Operation(summary = "Show a user home", description = "Show a user home", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = PostResponseDTO.class)))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internat Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<PostResponseDTO>> findAll(){
        List<PostResponseDTO> home = postService.findAll();
        return ResponseEntity.ok(home);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a post", description = "Updates a post", tags = {"Posts"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = PostResponseDTO.class))),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PostResponseDTO> update(@PathVariable Long id, @RequestBody PostRequestDTO postRequestDTO){
        PostResponseDTO postResponseDTO = postService.update(id, postRequestDTO);
        return ResponseEntity.ok(postResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a post", description = "Deletes a post", tags = {"Posts"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
