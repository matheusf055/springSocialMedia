package com.project.socialmedia.jwt;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class JwtUserDetails extends User {

    private com.project.socialmedia.entity.User user;

    public JwtUserDetails(com.project.socialmedia.entity.User user) {
        super(user.getUsername(), user.getPassword(), Collections.emptyList());
        this.user = user;
    }

    public Long getId(){
        return this.user.getId();
    }

    public String getPassword(){
        return this.user.getPassword();
    }
}
