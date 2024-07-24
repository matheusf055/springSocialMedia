package com.project.socialmedia.config;

import com.project.socialmedia.jwt.InvalidTokenService;
import com.project.socialmedia.jwt.JwtAuthorizationFilter;
import com.project.socialmedia.jwt.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final InvalidTokenService invalidTokenService;

    public SecurityConfig(@Lazy JwtUserDetailsService jwtUserDetailsService, InvalidTokenService invalidTokenService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.invalidTokenService = invalidTokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "user/{userId}/follow/{followId}").authenticated()
                        .requestMatchers(HttpMethod.POST, "user/{userId}/unfollow/{unfollowId}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/post").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/post").authenticated()
                        .requestMatchers(HttpMethod.POST, "/post/{postId}/repost").authenticated()
                        .requestMatchers(HttpMethod.POST, "/post/{postId}/comment").authenticated()
                        .requestMatchers(HttpMethod.POST, "/post/{postId}/like").authenticated()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/webjars/**").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthorizationFilter(jwtUserDetailsService, invalidTokenService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}