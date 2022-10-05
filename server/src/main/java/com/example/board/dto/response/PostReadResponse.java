package com.example.board.dto.response;

import com.example.board.dto.PostDto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostReadResponse {
    private Long id;
    private String title;
    private String content;
    private String username;
    private Timestamp registerAt;

    public static PostReadResponse fromPostDto(PostDto postDto) {
        return new PostReadResponse(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getUser().getUsername(),
                postDto.getRegisterAt()
        );
    }
}
