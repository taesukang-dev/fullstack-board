package com.example.board.dto.response;

import com.example.board.dto.CommentDto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentWriteResponse {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private Timestamp registerAt;

    public static CommentWriteResponse fromCommentDto(CommentDto comment) {
        return new CommentWriteResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getRegisterAt()
        );
    }
}
