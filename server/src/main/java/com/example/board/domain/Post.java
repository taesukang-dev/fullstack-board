package com.example.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"post\"",
        indexes = {
            @Index(columnList = "title"),
            @Index(columnList = "registered_at")
        }
)
@SQLDelete(sql = "UPDATE post SET DELETED_AT = NOW() where id =?")
@Where(clause = "deleted_at is null")
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "registered_at")
    private Timestamp registerAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public void updatePost(String title, String content) {
        if (StringUtils.hasText(title) && StringUtils.hasText(content)) {
            this.title = title;
            this.content = content;
        } else if (StringUtils.hasText(title)) {
            this.title = title;
        } else if (StringUtils.hasText(content)) {
            this.content = content;
        }
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    protected Post(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public static Post makeFixture(Long id, User user) {
        return new Post(id, user);
    }

    public static Post of(String username, String password, User user) {
        return new Post(username, password, user);
    }

    @PrePersist void registeredAt() { this.registerAt = Timestamp.from(Instant.now()); }
    @PreUpdate void updatedAt() { this.updatedAt = Timestamp.from(Instant.now()); }
}
