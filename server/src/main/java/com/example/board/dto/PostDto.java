package com.example.board.dto;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private User user;
    private Timestamp registerAt;

    public static PostDto fromPost(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser(),
                post.getRegisterAt()
        );
    }
}
