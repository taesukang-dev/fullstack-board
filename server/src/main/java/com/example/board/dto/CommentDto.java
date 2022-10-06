package com.example.board.dto;

import com.example.board.domain.Comment;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private User user;
    private Post post;
    private List<CommentDto> child;
    private Timestamp registerAt;

    public static CommentDto fromComment(Comment comment) {
        List<CommentDto> convertedChild = comment.getChild().stream()
                .map(e -> new CommentDto(e.getId(), e.getContent(), e.getUser(), e.getPost(), convertChild(e.getChild()), e.getRegisterAt()))
                .collect(Collectors.toList());
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser(),
                comment.getPost(),
                convertedChild,
                comment.getRegisterAt()
        );
    }

    private static List<CommentDto> convertChild(List<Comment> comments) {
        if (comments.size() == 0)
            return List.of();
        return comments.stream().map(e ->
                new CommentDto(
                        e.getId(),
                        e.getContent(),
                        e.getUser(),
                        e.getPost(),
                        convertChild(e.getChild()),
                        e.getRegisterAt()
                )).collect(Collectors.toList());
    }
}
