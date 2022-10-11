package com.example.board.dto.response;

import com.example.board.dto.CommentDto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
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
public class CommentReadResponse {
    private Long id;
    private String content;
    private String username;
    private Long postId;
    private List<CommentReadResponse> child;
    private Timestamp registerAt;

    public CommentReadResponse(Long id, String content, String username, Long postId, Timestamp registerAt) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.postId = postId;
        this.registerAt = registerAt;
    }

    public static CommentReadResponse fromCommentDto(CommentDto comment) {
        List<CommentReadResponse> convertedChild = comment.getChild().stream()
                .map(e -> new CommentReadResponse(e.getId(), e.getContent(), e.getUser().getUsername(), e.getPost().getId(), convertChild(e.getChild()), e.getRegisterAt()))
                .collect(Collectors.toList());
        return new CommentReadResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getPost().getId(),
                convertedChild,
                comment.getRegisterAt()
        );
    }

    private static List<CommentReadResponse> convertChild(List<CommentDto> comments) {
        if (comments.size() == 0)
            return List.of();
        return comments.stream().map(e ->
                new CommentReadResponse(
                        e.getId(),
                        e.getContent(),
                        e.getUser().getUsername(),
                        e.getPost().getId(),
                        convertChild(e.getChild()),
                        e.getRegisterAt()
                )).collect(Collectors.toList());
    }
}
