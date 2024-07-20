package com.project.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "customer")
@AllArgsConstructor @NoArgsConstructor @Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fistname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "summary", length = 180)
    private String summary;

    @Column(name = "birthdate")
    private Date birthDate;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;
}
