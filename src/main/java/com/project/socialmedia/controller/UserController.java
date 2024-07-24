package com.project.socialmedia.controller;

import com.project.socialmedia.dto.ProfileDTO;
import com.project.socialmedia.dto.UserRequestDTO;
import com.project.socialmedia.dto.UserResponseDTO;
import com.project.socialmedia.service.UserService;
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
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for Users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a user", description = "Register a user", tags = {"Users"}, responses = {
            @ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = UserRequestDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.register(userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/{userId}/follow/{followId}")
    @Operation(summary = "Follow a user", description = "Follow a user", tags = {"Users"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidenn", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> follow(@PathVariable Long userId, @PathVariable Long followId){
        userService.follow(userId, followId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/unfollow/{unfollowId}")
    @Operation(summary = "Unfollow a user", description = "Unfollow a user", tags = {"Users"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidenn", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> unfollow(@PathVariable Long userId, @PathVariable Long unfollowId){
        userService.unfollow(userId, unfollowId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by id", description = "Find user by id", tags = {"Users"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internat Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        return userService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "Finds a user profile", description = "Finds a user profile", tags = {"Users"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = ProfileDTO.class)))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internat Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long userId) {
        Optional<ProfileDTO> profile = userService.getProfileById(userId);
        return profile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Find all users", description = "Find all users", tags = {"Users"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a user by id", description = "Updates a user by id", tags = {"Users"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.update(id, userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a user", description = "Deletes a user", tags = {"Users"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
