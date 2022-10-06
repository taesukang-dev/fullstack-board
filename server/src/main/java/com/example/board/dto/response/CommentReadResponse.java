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
    private Long userId;
    private Long postId;
    private List<CommentReadResponse> child;
    private Timestamp registerAt;

    public CommentReadResponse(Long id, String content, Long userId, Long postId, Timestamp registerAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.registerAt = registerAt;
    }

    public static CommentReadResponse fromCommentDto(CommentDto comment) {

        if (comment.getChild().size() > 0) {
            List<CommentReadResponse> convertedChild = comment.getChild().stream()
                    .map(e -> new CommentReadResponse(e.getId(), e.getContent(), e.getUser().getId(), e.getPost().getId(), convertChild(e.getChild()), e.getRegisterAt()))
                    .collect(Collectors.toList());
            return new CommentReadResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getId(),
                    comment.getPost().getId(),
                    convertedChild,
                    comment.getRegisterAt()
            );
        } else {
            return new CommentReadResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getId(),
                    comment.getPost().getId(),
                    List.of(),
                    comment.getRegisterAt()
            );
        }
    }

    private static List<CommentReadResponse> convertChild(List<CommentDto> comments) {
        if (comments.size() == 0)
            return List.of();
        return comments.stream().map(e ->
                new CommentReadResponse(
                        e.getId(),
                        e.getContent(),
                        e.getUser().getId(),
                        e.getPost().getId(),
                        convertChild(e.getChild()),
                        e.getRegisterAt()
                )).collect(Collectors.toList());
    }
}
