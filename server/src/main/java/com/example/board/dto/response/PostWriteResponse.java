package com.example.board.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWriteResponse {
    private Long id;

    public static PostWriteResponse of(Long id) {
        return new PostWriteResponse(id);
    }
}
