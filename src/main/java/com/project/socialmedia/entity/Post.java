package com.project.socialmedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "post")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "text")
    private String text;

    @Column(name = "likes")
    private int likes;

    @Column(name = "reposts")
    private int reposts;

    @Column(name = "number_comments")
    private int numberComments;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User authorDetails;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Retweet> retweets = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }
}
