package com.project.socialmedia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "retweet")
@AllArgsConstructor @NoArgsConstructor @Data
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "retweeter_id")
    private Long retweeterId;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "retweeter_id", insertable = false, updatable = false)
    private User retweeter;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
