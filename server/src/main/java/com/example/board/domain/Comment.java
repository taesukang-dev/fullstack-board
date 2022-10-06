package com.example.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"comment\"")
@SQLDelete(sql = "UPDATE comment SET DELETED_AT = NOW() where id = ?")
@Where(clause = "deleted_at is null")
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> child = new ArrayList<>();

    @Column(name = "registered_at")
    private Timestamp registerAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;


    public void addChild(Comment comment) {
        this.child.add(comment);
    }

    public Comment(String content, Post post, User user, Comment parent) {
        this.content = content;
        this.post = post;
        this.user = user;
        this.parent = parent;
    }

    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public static Comment of(String content, Post post, User user, Comment parent) {
        return new Comment(content, post, user, parent);
    }

    public static Comment of(String content, Post post, User user) {
        return new Comment(content, post, user);
    }

    @PrePersist void registeredAt() { this.registerAt = Timestamp.from(Instant.now()); }
    @PreUpdate void updatedAt() { this.updatedAt = Timestamp.from(Instant.now()); }
}
