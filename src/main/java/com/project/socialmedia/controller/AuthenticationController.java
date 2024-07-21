package com.project.socialmedia.controller;

import com.project.socialmedia.dto.UserLoginDTO;
import com.project.socialmedia.jwt.JwtToken;
import com.project.socialmedia.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor @Slf4j
public class AuthenticationController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authentic(@RequestBody @Valid UserLoginDTO userLoginDTO, HttpServletRequest request){
        log.info("Authentication process");
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());

            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(userLoginDTO.getUsername());

            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex){
            log.warn("Bad Credentials");
        }
        return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }
}
